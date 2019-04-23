package com.social.userservice.controller;

import com.social.userservice.service.FollowService;
import com.social.userservice.service.TicketService;
import com.social.userservice.service.UserService;
import common.user.Follow;
import common.user.Ticket;
import common.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController

public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    TicketService ticketService;


    //登录
    @PostMapping("api/user/login")
    public int login(@RequestParam("account") String account, @RequestParam("password") String password) {
        int ticketId;
        ticketId = userService.login(account, password);
        return ticketId;
    }

    //注册
    @PostMapping("api/user/reg")
    public String reg(@RequestParam("account") String account, @RequestParam("password") String password, @RequestParam("mailbox") String mailbox,
                      @RequestParam("activeCode") String activeCode) {
        Map<String, Object> map = userService.reg(account, password, mailbox, activeCode);
        if (map.containsKey("error")) {
            return (String) map.get("error");
        }
        return (String) map.get("ticket");
    }

    //获取验证码
    @PostMapping("api/user/active-code")
    public void getActiveCode(@RequestParam("account") String account, @RequestParam("mailbox") String mailbox) {
        userService.setActiveCode(account, mailbox);
    }

    //查询用户
    @GetMapping("/api/user/{uid}")
    public User getUser(@PathVariable("uid") int userId) {
        return userService.getUserById(userId);
    }

    //通过ticketId查询用户
    @GetMapping("api/user/ticket/{tid}")
    public User getUserByTicketId(@PathVariable("tid") int ticketId) {
        Ticket ticket = ticketService.getTicketByTicketId(ticketId);
        return userService.getUserById(ticket.getUserId());
    }
}
