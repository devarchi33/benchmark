package com.iruen.config;

import com.iruen.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by donghoon on 2016. 5. 18..
 */
@Component
public class JedisConfig {
    private Set<Jedis> connectionList = new HashSet<>();
    private JedisPool pool = null;

    @Autowired
    public JedisConfig(Properties properties) {
        Map<String, Map<String, String>> servers = properties.getServers();
        Map<String, String> redisInfo = servers.get("redis");
        int REDIS_MAX = Integer.parseInt(redisInfo.get("max"));
        int REDIS_PORT = Integer.parseInt(redisInfo.get("port"));
        int REDIS_TIMEOUT = Integer.parseInt(redisInfo.get("timeout"));
        boolean REDIS_BLOCKWHENEXHAUSTED = Boolean.parseBoolean(redisInfo.get("blockwhenexhausted"));
        String REDIS_HOST = redisInfo.get("host");

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(REDIS_MAX);
        jedisPoolConfig.setBlockWhenExhausted(REDIS_BLOCKWHENEXHAUSTED);

        this.pool = new JedisPool(jedisPoolConfig, REDIS_HOST, REDIS_PORT, REDIS_TIMEOUT);
    }

    public Jedis getConnection() {
        Jedis jedis = this.pool.getResource();
        connectionList.add(jedis);
        return jedis;
    }

    public void returnResource(Jedis jedis) {
        this.pool.returnResourceObject(jedis);
    }

    public void destroyPool() {
        Iterator<Jedis> jedisList = connectionList.iterator();
        while (jedisList.hasNext()) {
            Jedis jedis = jedisList.next();
            this.pool.returnResourceObject(jedis);
        }

        this.pool.destroy();
    }
}
