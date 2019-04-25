package common.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.UUID;


@Component
public class JedisUtil implements InitializingBean {
    private JedisPool pool;

    @Override
    public void afterPropertiesSet() throws Exception {
        pool = new JedisPool("redis://127.0.0.1:6379/0");
    }

    //生成并返回activeCode,设置2分钟过期时间
    public String setActiveCode(String account) {
        Jedis jedis = pool.getResource();
        String activeCode = UUID.randomUUID().toString().substring(4);
        jedis.setex(RedisKey.ACTIVE_CODE + account, 60 * 2, activeCode);
        return activeCode;
    }

    //获取activeCode
    public String getActiveCode(String account) {
        Jedis jedis = pool.getResource();
        String activeCode = jedis.get(RedisKey.ACTIVE_CODE + account);
        return activeCode;
    }
}
