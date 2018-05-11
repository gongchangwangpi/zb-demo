package com.books.jvm.d7;

/**
 * Created by books on 2017/5/9.
 */
public class ClassInitTest3 {

    public static void main(String[] args) throws ClassNotFoundException {
        // 类名.class,JVM只会加载类,不会执行类初始化
        Class<Test> testClass = Test.class;
        System.out.println(testClass);

        Class<?> aClass = Class.forName("com.books.jvm.d7.Test");
        System.out.println(aClass);
    }

}

class Test {
    static {
        System.out.println("Test");
    }
}
