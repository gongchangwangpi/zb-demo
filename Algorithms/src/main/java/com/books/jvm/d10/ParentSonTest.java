package com.books.jvm.d10;

/**
 * Created by Administrator on 2017/5/17 0017.
 */
public class ParentSonTest {

    public static void main(String[] args) {

//        Son son = new Son();
//        son.say();

//        Parent static
//        Son static
//        Parent {}
//        Parent 构造器
//        Son {}
//        Son 构造器
//        Son say
        //-----------------------------------
//        Parent parent = new Parent();
//        parent.say();

//        Parent static
//        Parent {}
//        Parent 构造器
//        Parent say

        TestInterface testInterface = new TestInterface() {
            @Override
            public void hello() {
                System.out.println("TestInterface");
            }
        };

        Object obj = testInterface;

        System.out.println(obj.getClass()); // class com.books.jvm.d10.ParentSonTest$1 自动生成 匿名内部类
        System.out.println(obj.getClass().getSuperclass()); // class java.lang.Object
    }

}
