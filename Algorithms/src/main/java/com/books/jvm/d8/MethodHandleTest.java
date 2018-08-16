package com.books.jvm.d8;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;

import static java.lang.invoke.MethodHandles.lookup;

/**
 * java.lang.invoke包
 * jdk1.7新增指令
 * 
 * invokedynamic
 *
 * Created by Administrator on 2017/5/8 0008.
 */
public class MethodHandleTest {

    public static void main(String[] args) throws Throwable {

        Object obj = System.currentTimeMillis() % 2 == 0 ? System.out : new ClassA();

        getPrintlnMH(obj).invokeExact("icyfenix");

    }

    private static MethodHandle getPrintlnMH(Object obj) throws Exception {
        MethodType methodType = MethodType.methodType(void.class, String.class);
        return lookup().findVirtual(obj.getClass(), "println", methodType).bindTo(obj);
    }

    static class ClassA {
        public void println(String s) {
            System.out.println("ClassA : " + this);
            System.out.println("ClassA : " + s);
        }
    }
}
