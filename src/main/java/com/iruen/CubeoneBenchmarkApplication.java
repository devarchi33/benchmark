package com.iruen;

import com.iruen.executor.CubeoneBenchmarkTask;
import com.iruen.executor.AsyncTask;
import com.iruen.executor.CubeoneBenchmarkAsyncUncaughtExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.StopWatch;

import java.util.concurrent.Executor;

@SpringBootApplication
public class CubeoneBenchmarkApplication implements CommandLineRunner, AsyncConfigurer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AsyncTask asyncTask;
    @Autowired
    private CubeoneBenchmarkTask cubeoneTask;

    public static void main(String[] args) {
        SpringApplication.run(CubeoneBenchmarkApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {

        Executor executor = getAsyncExecutor();

        cubeoneTask.run();

    }

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(10);
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new CubeoneBenchmarkAsyncUncaughtExceptionHandler();
    }

    @Bean
    public StopWatch stopWatch() {
        return new StopWatch();
    }
}