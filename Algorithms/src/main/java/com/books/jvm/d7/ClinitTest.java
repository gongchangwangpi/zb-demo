package com.books.jvm.d7;

/**
 * Page 226
 * clinit 类初始化，主要是初始化类字段，静态代码块
 * 在类的构造器之前被虚拟机调用
 *
 * Created by Administrator on 2017/5/2 0002.
 */
public class ClinitTest {

    public static void main(String[] args) {
        System.out.println(Sub.b);
    }
}
class Parent {
    static {
        a = 2;
        // System.out.println(a); // illegal forward reference
    }
    // 静态代码块可以给之后的变量赋值，但不能访问或操作
    public static int a = 1;
}
class Sub extends Parent {
    public static int b = a;
}
