package com.iruen.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

/**
 * Created by donghoon on 2016. 8. 16..
 */
public class CubeoneBenchmarkAsyncUncaughtExceptionHandler implements AsyncUncaughtExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void handleUncaughtException(Throwable throwable, Method method, Object... objects) {
        logger.error("Method Name:: {}", method.getName());
        logger.error("Exception occurred:: {}", throwable);
    }
}
