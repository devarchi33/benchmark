package com.iruen.executor.task;

import com.iruen.domain.CubeoneTestUser;
import com.iruen.service.JedisService;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by donghoon on 2016. 8. 18..
 */
@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LoadToRedisTask implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JedisService jedisService;
    @Autowired
    private FindFromOracleTask findFromOracleTask;

    @Setter
    @Getter
    private List<CubeoneTestUser> users;

    /**
     * key 를 Redis 에 load 하기.
     */
    @Override
    public void run() {
        List<CubeoneTestUser> usersKey = findFromOracleTask.getUsersKey();

        for (CubeoneTestUser user : usersKey) {
            String key = user.getJumin();
            if (!jedisService.existsKey(key))
                jedisService.setLog(key, user.getJumin_origin());
        }

    }
}
