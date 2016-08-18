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

    public Integer sendLog(byte[] logKey, byte[] logValue) {
        long countLog;
        try (Jedis jedis = jedisConfig.getConnection()) {
            countLog = jedis.rpush(logKey, logValue);
            return (int) countLog;
        }
    }

    public Integer sendLog(String logKey, String logValue) throws JedisConnectionException {
        long countLog;
        try (Jedis jedis = jedisConfig.getConnection()) {
            countLog = jedis.rpush(logKey, logValue);
            return (int) countLog;
        }
    }

    public String getLog(byte[] logKey) {
        byte[] getLog;
        try (Jedis jedis = jedisConfig.getConnection()) {
            getLog = jedis.lpop(logKey);
            return getLog.toString();
        }
    }

    public String setLog(String key, String value) {
        try (Jedis jedis = jedisConfig.getConnection()) {
            return jedis.set(key, value);
        }
    }

    public String getLog(String key) {
        try (Jedis jedis = jedisConfig.getConnection()) {
            return jedis.get(key);
        }
    }

    public boolean existsKey(String key) {
        try (Jedis jedis = jedisConfig.getConnection()) {
            return jedis.exists(key);
        }
    }
}
