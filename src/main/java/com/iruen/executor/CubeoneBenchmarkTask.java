package com.iruen.executor;

import com.iruen.domain.CubeoneTest;
import com.iruen.service.CubeoneTestService;
import com.iruen.service.JedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by donghoon on 2016. 8. 17..
 */
@Component
public class CubeoneBenchmarkTask implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CubeoneTestService cubeoneTestService;
    @Autowired
    private JedisService jedisService;

    @Override
    @Async
    public void run() {

        /**
         * Oracle 로 부터 데이터를 가져와서 Redis 에 load.
         */
        List<CubeoneTest> users = cubeoneTestService.findAllUsers();
        for (CubeoneTest user : users) {
            jedisService.sendLog(user.getJumin(), user.getJumin_origin());
        }
        logger.info("Data Load Complete!");

        /**
         * Redis 에서 key 별로 data 꺼내기.
         */
        for (CubeoneTest user : users) {
            logger.info("Found Val: {}", jedisService.getLog(user.getJumin()));
        }
        logger.info("Find Data From Redis Complete!");
        logger.info("Total Count: {}", users.size());

    }
}
