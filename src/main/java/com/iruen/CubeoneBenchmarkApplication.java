package com.iruen;

import com.iruen.domain.CubeoneBenchmark;
import com.iruen.domain.CubeoneTestUser;
import com.iruen.executor.CubeoneBenchmarkDataFindTask;
import com.iruen.executor.CubeoneBenchmarkDataLoadTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.StopWatch;

import java.util.List;
import java.util.concurrent.Executor;

@SpringBootApplication
public class CubeoneBenchmarkApplication implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Properties properties;
    @Autowired
    private CubeoneBenchmarkDataLoadTask cubeoneDataLoadTask;
    @Autowired
    private CubeoneBenchmarkDataFindTask cubeoneDataFindTask;
    @Autowired
    private StopWatch stopWatch;

    private int workerCnt;

    public static void main(String[] args) {
        SpringApplication.run(CubeoneBenchmarkApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        workerCnt = Integer.parseInt(properties.getWorkerCnt());

        /**
         * Data Load Task
         */
//        CubeoneBenchmark cubeoneBenchmark = (CubeoneBenchmark) cubeoneDataLoadTask.call();

        /**
         * Data Load Task 이후 return 받은 객체 설정.
         */
//        List<CubeoneTestUser> users = cubeoneBenchmark.getUsers();
//        long loadDataTime = cubeoneBenchmark.getTime();
//        cubeoneDataFindTask.setUsers(users);
//        cubeoneDataFindTask.setLoadDataTime(loadDataTime);

        /**
         * 멀티 스레드로 Data Find Task 실행.
         */
        Executor executor = getAsyncExecutor();
        stopWatch.start();
        for (int i = 0; i < workerCnt; i++) {
            executor.execute(cubeoneDataFindTask);
        }
        stopWatch.stop();

        /**
         * 테스트 시간 출력
         */
        long totalTime = stopWatch.getTotalTimeMillis();
//        logger.info("Total Count: {}", users.size());
//        logger.info("Data Load Benchmark Time : {} mils.", loadDataTime);
//        logger.info("Find Data Benchmark Time : {} mils.", totalTime - loadDataTime);
        logger.info("Total Benchmark Time : {} mils.", totalTime);

    }

    public ThreadPoolTaskExecutor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(10);
        executor.initialize();
        return executor;
    }

    @Bean
    public StopWatch stopWatch() {
        return new StopWatch();
    }
}