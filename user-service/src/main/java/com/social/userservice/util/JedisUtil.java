package com.social.userservice.util;

import com.social.userservice.service.UserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


@Service
public class JedisUtil implements InitializingBean {
    private JedisPool pool;
    @Autowired
    UserService userService;

    @Override
    public void afterPropertiesSet() throws Exception {
        pool = new JedisPool("redis://127.0.0.1:6379/0");
    }

    //缓存activeCode,设置3分钟过期时间
    public void setActiveCode(String activeCode, String account) {
        Jedis jedis = pool.getResource();
        jedis.setex(RedisKey.ACTIVE_CODE + account, 60 * 3, activeCode);
    }

    //获取activeCode
    public String getActiveCode(String account) {
        Jedis jedis = pool.getResource();
        String activeCode = jedis.get(RedisKey.ACTIVE_CODE + account);
        return activeCode;
    }
}
