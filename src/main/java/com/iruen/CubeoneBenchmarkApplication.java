package com.iruen;

import com.iruen.domain.CubeoneTestUser;
import com.iruen.executor.task.FindFromOracleTask;
import com.iruen.executor.task.FindFromRedisTask;
import com.iruen.executor.task.LoadToRedisTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.List;

@SpringBootApplication
public class CubeoneBenchmarkApplication implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FindFromOracleTask findFromOracleTask;
    @Autowired
    private LoadToRedisTask loadToRedisTask;
    @Autowired
    private FindFromRedisTask findFromRedisTask;

    @Value("${workerCnt:1}")
    private int workerCnt;

    public static void main(String[] args) {
        SpringApplication.run(CubeoneBenchmarkApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {

        logger.info("Worker Count: {}", workerCnt);

        List<CubeoneTestUser> users = (List<CubeoneTestUser>) findFromOracleTask.call();
        loadToRedisTask.setUsers(users);
        findFromRedisTask.setUsers(users);

        loadToRedisTask.run();
        findFromRedisTask.run();

//        ThreadPoolTaskExecutor loadToRedisTaskExecutor = getExecutorPool();
//        ThreadPoolTaskExecutor findFromRedisTaskExecutor = getExecutorPool();
//
//        for (int i = 0; i < workerCnt; i++)
//            loadToRedisTaskExecutor.execute(loadToRedisTask);
//        for (int i = 0; i < workerCnt; i++)
//            findFromRedisTaskExecutor.execute(findFromRedisTask);

    }

    public ThreadPoolTaskExecutor getExecutorPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(10);
        executor.initialize();
        return executor;
    }


}