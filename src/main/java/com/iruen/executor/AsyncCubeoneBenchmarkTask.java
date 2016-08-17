package com.iruen.executor;

import com.iruen.domain.CubeoneTest;
import com.iruen.service.CubeoneTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by donghoon on 2016. 8. 17..
 */
@Component
public class AsyncCubeoneBenchmarkTask implements Runnable {

    @Autowired
    private CubeoneTestService cubeoneTestService;

    @Override
    @Async
    public void run() {
        List<CubeoneTest> users = cubeoneTestService.findAllUsers();

        for (CubeoneTest user : users) {
            System.out.println(user.getJumin());
            System.out.println(user.getJumin_origin());
        }
    }
}
