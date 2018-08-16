package com.test.geek.jvm;

import java.lang.reflect.Method;

/**
 * {@link java.lang.reflect.Method#invoke(Object, Object...)}的具体实现有2种方式
 * 代理实现{@link sun.reflect.NativeMethodAccessorImpl}作为中间层，底层调用本地实现和动态实现
 * 一种本地实现{@link sun.reflect.DelegatingMethodAccessorImpl}
 * 一种动态实现，即动态生成字节码
 * 
 * 本地实现效率较低，动态实现执行效率高，但动态生成字节码消耗大。由于一般反射调用次数少，所以默认采用本地实现。
 * 默认反射调用超过15次，则生成字节码，切换为动态实现。可通过如下参数调整
 * 
 * -Dsun.reflect.inflationThreshold=10
 * -Dsun.reflect.noInflation=true    关闭本地实现
 * 
 * @author zhangbo
 */
public class Test {
    
    public static void t(int i) {
        new Exception("#" + i).printStackTrace();
    }

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 20; i++) {
            
            Class<?> clz = Class.forName("com.test.geek.jvm.Test");

            Method method = clz.getDeclaredMethod("t", int.class);
            
            method.invoke(null, i);

        }
    }
    
}
