package com.springapp.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author zhangbo
 * @date 2020/4/28
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    // com包下demo子包的所有public方法
//    @Pointcut(value = "execution(* com.springapp.aop..*.*(..))")
    @Pointcut(value = "@annotation(DDD)")
    public void log() {
    }

    // 可以直接指定execution，也可以指定Pointcut
    @Around(value = "log()")
//    @Around(value = "execution(* com.springapp.aop..*.*(..))")
    public Object process(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        log.info(" ===== Around before ===== ");

        Object result = proceedingJoinPoint.proceed();
        log.info("result = {}", result);

        log.info(" ===== Around after ===== ");

        return result;
    }

}
