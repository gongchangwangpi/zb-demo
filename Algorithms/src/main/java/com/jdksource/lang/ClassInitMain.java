package com.jdksource.lang;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author zhangbo
 */
public class ClassInitMain {

    public static void main(String[] args) throws Exception {

        // 会执行类的初始化操作
//        Class<?> clz = Class.forName("com.jdksource.lang.ClassInitTest");
//        Class<?> clz = Class.forName("com.jdksource.lang.ClassInitTest", true, ClassInitMain.class.getClassLoader());

        // 不初始化class，不会执行static代码块等
//        Class<?> clz = Class.forName("com.jdksource.lang.ClassInitTest", false, ClassInitMain.class.getClassLoader());

        // 不会执行类初始化操作
//        Class<ClassInitTest> clz = ClassInitTest.class;

        // 不会执行类初始化操作
        URLClassLoader classLoader = new URLClassLoader(new URL[]{new URL("file:\\D:\\IdeaProjects\\github\\zb-demo\\Algorithms\\target\\classes")});
        Class<?> clz = classLoader.loadClass("com.jdksource.lang.ClassInitTest");


        System.out.println(clz);

        clz.newInstance();

    }
    
}
