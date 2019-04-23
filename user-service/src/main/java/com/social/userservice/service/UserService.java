package com.social.userservice.service;

import com.social.userservice.async.Consumer;
import com.social.userservice.dao.TicketDao;
import com.social.userservice.dao.UserDao;
import common.user.Ticket;
import common.user.User;
import common.util.JedisUtil;
import common.util.MD5;
import common.util.MailSend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class UserService {
    @Autowired
    UserDao userDao;
    @Autowired
    MailSend mailSend;
    @Autowired
    TicketDao ticketDao;
    @Autowired
    Consumer consumer;
    @Autowired
    JedisUtil jedisUtil;

    //根据用户Id查询用户
    public User getUserById(int userId) {
        return userDao.selectUserById(userId);
    }

    //登录
    public int login(String account, String password) {

        Ticket ticket = new Ticket();
        User user = userDao.selectUserByAccount(account);
        if (user != null) {
            //获取salt
            String salt = user.getSalt();
            //通过登录验证
            if (MD5.string2MD5(password + salt).equals(user.getPassword())) {
                //更新ticket有效时间
                Date date = new Date();
                date.setTime(date.getTime() + 1000 * 60 * 60 * 24 * 7);
                ticket = ticketDao.selectTicketByUserId(user.getUserId());
                ticket.setValidTime(date);
                ticketDao.updateTicket(ticket);
            }
        }
        return ticket.getTicketId();
    }

    //注册
    public Map<String, Object> reg(String account, String password, String mailbox, String activeCode) {
        Map<String, Object> map = new HashMap();
        if (StringUtils.isEmpty(account)) {
            map.put("error", "用户名不能为空");
            return map;
        }
        if (StringUtils.isEmpty(password)) {
            map.put("error", "密码不能为空");
            return map;
        }
        if (StringUtils.isEmpty(mailbox)) {
            map.put("error", "邮箱不能为空");
            return map;
        }
        User user = userDao.selectUserByAccount(account);
        if (user != null) {
            map.put("error", "用户名已存在");
            return map;
        }
        //在redis中获取验证码
        String code = jedisUtil.getActiveCode(account);
        if (!code.equals(activeCode)) {
            map.put("error", "验证码不正确");
            return map;
        }
        Ticket ticket = new Ticket();
        Date date = new Date();
        date.setTime(date.getTime() + 1000 * 60 * 60 * 24 * 7);
        user = new User();
        user.setAccount(account);
        user.setMailbox(mailbox);
        user.setHeadUrl("../static/img/head/default.jpg");
        user.setSalt(UUID.randomUUID().toString().substring(0, 4));
        user.setPassword(MD5.string2MD5(password + user.getSalt()));
        user.setUserName(UUID.randomUUID().toString().substring(0, 15));
        userDao.insertUser(user);
        //设置用户的ticket
        ticket.setUserId(user.getUserId());
        ticket.setValidTime(date);
        ticketDao.insertTicket(ticket);
        map.put("ticketId", ticket.getTicketId());
        return map;
    }

    //生成验证码,发到邮箱里，存入redis
    public void setActiveCode(String account, String mailbox) {
        String activeCode = UUID.randomUUID().toString().substring(0, 4);
        jedisUtil.setActiveCode(activeCode, account);
    }

    //更新用户的信息
    public int updateUser(User user) {
        return userDao.updateUser(user);
    }

}
