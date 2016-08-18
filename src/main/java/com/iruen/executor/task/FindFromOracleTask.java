package com.iruen.executor.task;

import com.iruen.domain.CubeoneTestUser;
import com.iruen.service.CubeoneTestService;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by donghoon on 2016. 8. 17..
 */
@Component
public class FindFromOracleTask implements Callable {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CubeoneTestService cubeoneTestService;

    @Getter
    private List<CubeoneTestUser> usersKey;

    /**
     * Oracle 로 부터 key 를 가져오기.
     */
    @Override
    public Object call() {

        long startTime = System.currentTimeMillis();
        usersKey = cubeoneTestService.findKey();
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        logger.info("Total User Key Count: {}", usersKey.size());
        logger.info("Find Key from Oracle Time: {}", elapsedTime);

        return usersKey;
    }
}
