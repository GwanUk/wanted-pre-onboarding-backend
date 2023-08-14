package com.example.wantedpreonboardingbackend.common.aop;

import com.example.wantedpreonboardingbackend.common.annotation.Retry;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;

@Aspect
@Order(1)
@Component
public class RetryAspect {

    @Around("@annotation(retry)")
    public Object retry(ProceedingJoinPoint joinPoint, Retry retry) throws Throwable {
        int maxCnt = retry.value();
        Exception exceptionHolder = null;

        for (int i = 0; i < maxCnt; i++) {
            try {
                return joinPoint.proceed();
            } catch (ObjectOptimisticLockingFailureException exception) {
                exceptionHolder = exception;
            }
        }
        
        throw exceptionHolder;
    }
}
