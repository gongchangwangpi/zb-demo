package com.books.jvm.d7;

/**
 * 见 Page213
 *
 * 可通过 -XX:+TraceClassLoading 虚拟机参数观察类的加载
 * 通过子类引用父类的静态变量，会导致子类的加载，但不会触发子类的初始化
 *
 * [Loaded com.books.jvm.d7.SuperClass1 from file:/G:/Pros/Algorithms/target/classes/]
 * [Loaded com.books.jvm.d7.SubClass1 from file:/G:/Pros/Algorithms/target/classes/]
 *
 *  但如果是static final静态常量，则都不会触发父类和子类的加载，更不会初始化
 *  常量的引用是在编译阶段通过常量传播优化，直接将常量储存到引用的 ClassInitTest1 类的常量池中
 *  以后 ClassInitTest1 对 SuperClass1.value 的引用直接转换为对自身常量池的引用
 *  即 ClassInitTest1 的class文件中并没有 SuperClass1 类的符号引用入口，这两个类在编译后不存在和人的联系
 *
 *
 * Created by Administrator on 2017/4/27 0027.
 */
public class ClassInitTest1 {
    public static void main(String[] args) {
        System.out.println(SubClass1.value);
//        SuperClass1 init
//        123
    }
}

class SuperClass1 {
    static {
        System.out.println("SuperClass1 init");
    }
    public static final int value =123;
//    public static int value =123;
}

class SubClass1 extends SuperClass1 {
    static {
        System.out.println("SubClass1 init");
    }
}