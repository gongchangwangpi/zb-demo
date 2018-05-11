package com.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 重试
 * 
 * Created by books on 2017/12/22.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Retry {

    /**
     * 重试次数
     * 
     * @return
     */
    int count() default 1;
    
    /**
     * 重试时间间隔：毫秒
     * 
     * @return
     */
    long interval();
    
}
