package com.zb.springboot.demo.track;

import org.springframework.core.annotation.Order;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 埋点跟踪
 *
 * @author zhangbo
 * @date 2020/4/29
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
@Order(value = Integer.MIN_VALUE)
public @interface Track {

    String eventType();

    String application();

    boolean parameters() default true;

    boolean response() default true;

}
