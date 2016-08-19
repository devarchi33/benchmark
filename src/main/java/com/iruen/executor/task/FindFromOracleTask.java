package com.iruen.executor.task;

import com.iruen.domain.CubeoneTestUser;
import com.iruen.service.CubeoneTestService;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by donghoon on 2016. 8. 17..
 */
@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
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

        usersKey = cubeoneTestService.findKey();
        logger.info("Total User Key Count: {}", usersKey.size());

        return usersKey;
    }
}
