package com.test.geek.javacore36;

/**
 * {@link NoClassDefFoundError} 和 {@link ClassNotFoundException}的区别
 * NoClassDefFoundError 是程序在编译时候可以顺利找到所需要依赖的类的，但是在运行时依赖的类找不到或者可以找到多个，就会抛出这个Error
 * 可以在启动jvm后，手动将NoClassDefFoundErrorTest1从classpath下删除，导致 NoClassDefFoundError
 * 或者直接用命令行 java com.test.geek.javacore36.NoClassDefFoundErrorTest 启动，同样导致 NoClassDefFoundError
 * 
 * 
 * ClassNotFoundException是在classpath下面找不到class文件
 * 
 * @author zhangbo
 */
public class NoClassDefFoundErrorTest {

    public static void main(String[] args) throws Exception {

//        SleepUtils.second(5);
        
        // ClassNotFoundException，编译时不需要验证NoClassDefFoundErrorTest1是否存在
//        Class.forName("com.test.geek.javacore36.NoClassDefFoundErrorTest1");
        Class<?> cls = NoClassDefFoundErrorTest.class.getClassLoader().loadClass("com.test.geek.javacore36.NoClassDefFoundErrorTest1");

        // NoClassDefFoundError，编译时需要显示显示验证是否存在
//        new NoClassDefFoundErrorTest1();
//        Class<NoClassDefFoundErrorTest1> clz = NoClassDefFoundErrorTest1.class;

    }
    
}
