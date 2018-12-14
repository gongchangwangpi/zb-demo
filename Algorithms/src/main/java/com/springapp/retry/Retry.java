package com.springapp.retry;

import org.springframework.core.annotation.Order;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhangbo
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Order(value = Integer.MIN_VALUE)
public @interface Retry {

    /**
     * 重试次数
     * @return
     */
    int count() default 1;

    /**
     * 重试延迟,毫秒
     * @return
     */
    long delay() default -1;
    
}
