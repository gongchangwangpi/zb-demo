package com.test;

/**
 * assert 运行需要添加JVM参数 : -ea
 *
 *
 * Created by books on 2017/3/30.
 */
public class T {

    public static String s = "xxx";

    static int i = 5;

    public void hello() {
       System.out.println("我是由" + getClass().getClassLoader().getClass() + "加载的");
   }

    public static void main(String[] args) throws Exception {

        Short s = 4;
        short s1 = 4;
//        test(s);
//        test(s1);

    }

//    public static void test(Short s) {
//    }
    public static void test(Integer s) {
    }

}
