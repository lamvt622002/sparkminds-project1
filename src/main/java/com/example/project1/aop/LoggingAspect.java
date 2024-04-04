package com.example.project1.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class LoggingAspect {
    private final Logger logger = Logger.getLogger(LoggingAspect.class.getName());
    @Around("execution(* com.example.project1.services.*.*(..))")
    public void log(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        logger.info("method will execute");
        proceedingJoinPoint.proceed();
        logger.info("method executed");
    }
}
