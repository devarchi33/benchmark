package com.iruen.service;

import com.iruen.config.JedisConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * Created by donghoon on 2016. 5. 18..
 */
@Component
public class JedisService {
    private JedisConfig jedisConfig;

    @Autowired
    public JedisService(JedisConfig jedisHelper) {
        this.jedisConfig = jedisHelper;
    }

    @SuppressWarnings("Duplicates")
    public Integer sendLog(byte[] logKey, byte[] logValue) {
        Jedis jedis = jedisConfig.getConnection();
        long countLog = 0;
        try {
            countLog = jedis.rpush(logKey, logValue);
            jedisConfig.returnResource(jedis);
            return (int) countLog;
        } catch (JedisConnectionException e) {
            jedisConfig.returnResource(jedis);
            return (int) countLog;
        }
    }

    @SuppressWarnings("Duplicates")
    public Integer sendLog(String logKey, String logValue) throws JedisConnectionException {
        Jedis jedis = jedisConfig.getConnection();
        long countLog = 0;
        try {
            countLog = jedis.rpush(logKey, logValue);
            jedisConfig.returnResource(jedis);
            return (int) countLog;
        } catch (JedisConnectionException e) {
            jedisConfig.returnResource(jedis);
            return (int) countLog;
        }
    }


    //단위 Test 확인용.
    public String getLog(byte[] logKey) {
        Jedis jedis = jedisConfig.getConnection();
        byte[] getLog = null;
        try {
            getLog = jedis.lpop(logKey);
            jedisConfig.returnResource(jedis);
            return getLog.toString();
        } catch (JedisConnectionException e) {
            jedisConfig.returnResource(jedis);
            assert getLog != null;
            return getLog.toString();
        }
    }

    public String getLog(String logKey) {
        Jedis jedis = jedisConfig.getConnection();
        String getLog = null;
        try {
            getLog = jedis.lpop(logKey);
            jedisConfig.returnResource(jedis);
            return getLog;
        } catch (JedisConnectionException e) {
            jedisConfig.returnResource(jedis);
            return getLog;
        }
    }
}
