package com.jdksource.lang;

/**
 * @author zhangbo
 */
public class ClassInitMain {

    public static void main(String[] args) throws Exception {

//        Class<?> clz = Class.forName("com.jdksource.lang.ClassInitTest");
        Class<?> clz = Class.forName("com.jdksource.lang.ClassInitTest", true, ClassInitMain.class.getClassLoader());
        // 不初始化class，不会执行static代码块等
//        Class<?> clz = Class.forName("com.jdksource.lang.ClassInitTest", false, ClassInitMain.class.getClassLoader());
        
        System.out.println(clz);

//        clz.newInstance();

    }
    
}
