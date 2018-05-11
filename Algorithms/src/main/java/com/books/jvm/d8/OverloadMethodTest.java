package com.books.jvm.d8;

/**
 * 引用类型 overload 重载方法调用时静态分配，编译期决定
 *
 * 引用类型本身 --> 父类 --> 父类的父类 --> 实现接口(多个同级接口同时存在的overload方法编译不通过) -->
 * 父接口 --> Object --> 本类型的变长参数 --> 父类型变长 --> 父接口变长 --> Object变长
 *
 * Created by books on 2017/5/5.
 */
public class OverloadMethodTest {

    public static void main(String[] args) {
        ParentInterface parent = new SubClass();
        test(parent);
        SubInterface sub = new SubClass();
        test(sub);
        SubClass t = new SubClass();
        test(t);
        SubClass1 t1 = new SubClass1();
        test(t1);
    }
    /*public static void test(SubClass1... t1s) {
        System.out.println("SubClass1...");
    }*/
//    public static void test(SubClass t) {
//        System.out.println("SubClass");
//    }
//    public static void test(ParentClass t2) {
//        System.out.println("ParentClass");
//    }
//    public static void test(SubInterface sub) {
//        // 与Parent1同级,如不存在第一个方法且传入参数类型为T,则编译报错Ambiguous method call
//        System.out.println("SubInterface");
//    }
//    public static void test(ParentInterface parent) {
//        System.out.println("ParentInterface");
//    }
//    public static void test(ParentInterface1 parent1) {
//        System.out.println("ParentInterface1");
//    }
    /*public static void test(Object object) {
        System.out.println("Object");
    }*/

//    public static void test(SubClass... ts) {
//        System.out.println("SubClass...");
//    }
//    public static void test(ParentInterface... parents) {
//        System.out.println("ParentInterface...");
//    }
    public static void test(Object... objects) {
        System.out.println("Object...");
    }
}
class SubClass1 extends SubClass implements ParentInterface1 {

}
class SubClass extends ParentClass implements SubInterface, ParentInterface1 {

}
class ParentClass {

}
interface ParentInterface {

}
interface SubInterface extends ParentInterface {

}
interface ParentInterface1 {

}
