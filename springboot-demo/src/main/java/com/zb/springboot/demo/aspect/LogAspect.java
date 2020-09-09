package com.zb.springboot.demo.aspect;

import com.zb.springboot.demo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author zhangbo
 * @date 2020/5/15
 */
@Slf4j
@Aspect
//@Component
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

        Object target = proceedingJoinPoint.getTarget();
        Object aThis = proceedingJoinPoint.getThis();

        String shortName = proceedingJoinPoint.getSignature().toShortString();
        String name = proceedingJoinPoint.getSignature().getName();
        log.info("method shortName = {}", shortName);
        log.info("methodName = {}", name);
        User user = new User();
        user.setUsername("user123");
        user.setAge(123);
        user.setCreateTime(new Date());
        user.setRegisterTime(LocalDateTime.now());

        Object result = null;
        try {
            result = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            log.error("aspect error", throwable);
            throw  throwable;
        }

        log.info(" ===== around after ===== ");
        return result;
    }
}
