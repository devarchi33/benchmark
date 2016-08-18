package com.iruen.executor;

import com.iruen.Properties;
import com.iruen.domain.CubeoneTestUser;
import com.iruen.service.CubeoneTestService;
import com.iruen.service.JedisService;
import lombok.Getter;
import lombok.Setter;
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
public class CubeoneBenchmarkDataFindTask implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Properties properties;
    @Autowired
    private CubeoneTestService cubeoneTestService;
    @Autowired
    private JedisService jedisService;

    @Setter
    @Getter
    private long loadDataTime;

    @Override
    @Async
    public void run() {
        Boolean isPrint = Boolean.parseBoolean(properties.getLogPrint());

        /**
         * Redis 에서 key 별로 data 꺼내기.
         */
        List<CubeoneTestUser> users = cubeoneTestService.findAllUsers();

        for (CubeoneTestUser user : users) {
            String findVal = jedisService.getLog(user.getJumin());

            if (isPrint) {
                logger.info("Found Val: {}", findVal);
            }
        }

    }
}
