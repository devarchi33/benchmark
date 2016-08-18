package com.iruen.executor.task;

import com.iruen.domain.CubeoneBenchmark;
import com.iruen.domain.CubeoneTestUser;
import com.iruen.service.CubeoneTestService;
import com.iruen.service.JedisService;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by donghoon on 2016. 8. 17..
 */
@Component
public class CubeoneBenchmarkDataLoadTask implements Callable {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CubeoneTestService cubeoneTestService;
    @Autowired
    private JedisService jedisService;
    @Autowired
    private StopWatch stopWatch;

    @Getter
    private List<CubeoneTestUser> users;

    @Override
    @Async
    public Object call() throws Exception {

        /**
         * Oracle 로 부터 데이터를 가져와서 Redis 에 load.
         */
        stopWatch.start();
        users = cubeoneTestService.findAllUsers();
        for (CubeoneTestUser user : users) {
            String key = user.getJumin();
            if (!(jedisService.existsKey(key)))
                jedisService.setLog(key, user.getJumin_origin());
        }
        stopWatch.stop();
        long loadDataTime = stopWatch.getTotalTimeMillis();

        return new CubeoneBenchmark(loadDataTime);

    }
}
