package com.iruen.executor;

import com.iruen.Properties;
import com.iruen.domain.CubeoneTest;
import com.iruen.service.CubeoneTestService;
import com.iruen.service.JedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.List;

/**
 * Created by donghoon on 2016. 8. 17..
 */
@Component
public class CubeoneBenchmarkTask implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Properties properties;
    @Autowired
    private CubeoneTestService cubeoneTestService;
    @Autowired
    private JedisService jedisService;
    @Autowired
    private StopWatch stopWatch;

    @Override
    @Async
    public void run() {
        Boolean isPrint = Boolean.parseBoolean(properties.getLogPrint());

        /**
         * Oracle 로 부터 데이터를 가져와서 Redis 에 load.
         */
        stopWatch.start();
        List<CubeoneTest> users = cubeoneTestService.findAllUsers();
        for (CubeoneTest user : users) {
            jedisService.sendLog(user.getJumin(), user.getJumin_origin());
        }
        stopWatch.stop();
        long loadDataTime = stopWatch.getTotalTimeMillis();

        /**
         * Redis 에서 key 별로 data 꺼내기.
         */
        stopWatch.start();
        for (CubeoneTest user : users) {
            String findVal = jedisService.getLog(user.getJumin());

            if (isPrint) {
                logger.info("Found Val: {}", findVal);
            }
        }
        stopWatch.stop();
        long totalTime = stopWatch.getTotalTimeMillis();

        logger.info("Total Count: {}", users.size());
        logger.info("Data Load Benchmark Time : {} mils.", loadDataTime);
        logger.info("Find Data Benchmark Time : {} mils.", totalTime - loadDataTime);
        logger.info("Total Benchmark Time : {} mils.", totalTime);

    }
}
