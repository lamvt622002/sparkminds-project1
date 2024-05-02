package com.example.project1.aop;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Log4j2
public class LoggingAspect {
    @Before("execution(* com.example.project1.controllers..*(..))")
    public void logBeforeController(JoinPoint joinPoint){
        log.info("In method {}, at class {}", joinPoint.getSignature().getName(), joinPoint.getTarget().getClass().getSimpleName());
    }
    @AfterReturning(pointcut = "execution(* com.example.project1.controllers..*(..))", returning = "result")
    public void logAfterController(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();

        log.info("Out method {}, at class {}. Returned: {}", methodName, className, result);
    }
}
