package com.iruen.config;

import com.iruen.Properties;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Map;

/**
 * Created by donghoon on 2016. 5. 18..
 */
@Component
public class JedisConfig implements InitializingBean, DisposableBean {
    private JedisPool pool = null;
    private Map<String, Map<String, String>> servers;
    private Map<String, String> redisInfo;
    private int REDIS_MAX;
    private int REDIS_PORT;
    private int REDIS_TIMEOUT;
    private boolean REDIS_BLOCKWHENEXHAUSTED;
    private String REDIS_HOST;

    @Autowired
    public JedisConfig(Properties properties) {
        servers = properties.getServers();
        redisInfo = servers.get("redis");
        REDIS_MAX = Integer.parseInt(redisInfo.get("max"));
        REDIS_PORT = Integer.parseInt(redisInfo.get("port"));
        REDIS_TIMEOUT = Integer.parseInt(redisInfo.get("timeout"));
        REDIS_BLOCKWHENEXHAUSTED = Boolean.parseBoolean(redisInfo.get("blockwhenexhausted"));
        REDIS_HOST = redisInfo.get("host");
    }

    public Jedis getConnection() {
        Jedis jedis = this.pool.getResource();
        return jedis;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(REDIS_MAX);
        jedisPoolConfig.setBlockWhenExhausted(REDIS_BLOCKWHENEXHAUSTED);

        this.pool = new JedisPool(jedisPoolConfig, REDIS_HOST, REDIS_PORT, REDIS_TIMEOUT);
    }

    @Override
    public void destroy() throws Exception {
        this.pool.destroy();
    }
}
