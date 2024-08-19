package com.t0khyo.library.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Pointcut("execution(public * com.t0khyo.library.service.impl.*.*(..))")
    public void serviceMethodsLoggingPackage() {
        // This method is intentionally left blank
        // It's used only as a pointcut reference
    }

    @Pointcut("execution(public * com.t0khyo.library.controller.*.*(..))")
    public void controllerMethodsLoggingPackage() {
        // This method is intentionally left blank
        // It's used only as a pointcut reference
    }

    @Around(value = "serviceMethodsLoggingPackage()")
    public Object logAroundService(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        log.info(">> {}.{}() - {}", className, methodName, Arrays.toString(args));

        Object result = joinPoint.proceed();

        log.info("<< {}.{}() - {}", className, methodName, result);

        return result;
    }

    @Around(value = "controllerMethodsLoggingPackage()")
    public Object logAroundController(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        log.info(">> {}.{}() - {}", className, methodName, Arrays.toString(args));

        Object result = joinPoint.proceed();

        log.info("<< {}.{}() - {}", className, methodName, result);

        return result;
    }
}

