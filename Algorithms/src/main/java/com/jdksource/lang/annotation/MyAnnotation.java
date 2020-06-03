package com.jdksource.lang.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * 声明注解的类型，有8种基本类型，String，Enum，Class，Annotation 以及这些类型的数组
 *
 * @author zhangbo
 * @date 2020-05-07
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation {

    int value();

}
