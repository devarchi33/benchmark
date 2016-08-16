package com.iruen.executor;

import com.iruen.domain.User;
import com.iruen.service.JedisService;
import com.iruen.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Created by donghoon on 2016. 8. 16..
 */
@Component
public class AsyncTask {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;
    @Autowired
    private JedisService jedisService;

    @Async
    public void doAsyncTask() {
        User user = userService.findUserByEmail("user1@gmail.com");
        logger.info("UserInfo - email: {}", user.getEmail());

        jedisService.sendLog("email1", user.getEmail());
        String emailFromRedis = jedisService.getLog("email1");
        logger.info("Redis Email: {}", emailFromRedis);
    }
}
