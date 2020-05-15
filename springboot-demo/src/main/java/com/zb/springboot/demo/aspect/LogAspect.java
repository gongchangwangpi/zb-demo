package com.zb.springboot.demo.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author zhangbo
 * @date 2020/5/15
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

//    @Pointcut(value = "execution(* com.zb.springboot.demo.service..*.*(..))")
    @Pointcut(value = "@annotation(MyAspect)")
    public void log(){

    }

    @Before(value = "@annotation(myAspect)")
    public void before(JoinPoint joinPoint, MyAspect myAspect) {
        log.info(" ===== before ===== {}", myAspect.value());
    }

    @After(value = "log()")
    public void after(JoinPoint joinPoint) {
        log.info(" ===== after ===== ");
    }

    @Around(value = "log()")
    public Object process(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        log.info(" ===== around before ===== ");

        Object result = proceedingJoinPoint.proceed();

        log.info(" ===== around after ===== ");
        return result;
    }
}
