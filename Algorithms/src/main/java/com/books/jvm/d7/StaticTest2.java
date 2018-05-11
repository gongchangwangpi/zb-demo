package com.books.jvm.d7;

/**
 * 类的初始化阶段，执行<clinit>()方法
 * 该方法由编译器自动收集类中的类变量(static变量)和static代码块生成
 * 顺序为源代码中声明的顺序
 * 
 * 如果一个类中没有类变量和static代码块，那么编译器可以不生成该类的<clinit>()方法
 *
 * 接口也会生成<clinit>()方法，接口中不能有static代码块，但当一个接口初始化时，其父接口不一定会初始化
 * 只有当使用到父接口中的类变量时才会初始化父接口
 * 接口的实现类在执行类初始化时，其父接口也同样不会被初始化
 * 
 * @author zhangbo
 */
public class StaticTest2 {

    public static void main(String[] args) {
        System.out.println(Parent.i);
        System.out.println(Sub.i);
        System.out.println(Sub.j);
    }
    
    static class Parent {
        static int i = 1;
        static {
            i = 2;
        }
//        static int i = 1;
    }
    
    static class Sub extends Parent {
        static int j = i;
    }
}
