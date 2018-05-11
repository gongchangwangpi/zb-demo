package com.books.jvm.d7;

/**
 * 可通过添加 -XX:+TraceClassLoading
 * 观察到，Super和Sub都被加载了
 * 
 * @author zhangbo
 */
public class SuperSubStaticInitTest {

    public static void main(String[] args) {
        // 只打印了Super,但父类和子类都被加载了,但子类不会被初始化
//        System.out.println(SubClass.i);
        
        // 父类和子类都没有被加载，加载的是由虚拟机自动生成的数组类型
        // [Loaded com.books.jvm.d7.SuperSubStaticInitTest$SuperClass
        // [Loaded com.books.jvm.d7.SuperSubStaticInitTest$SubClass
//        SubClass[] arr = new SubClass[1];
        
        // SuperClass.class不会被加载，加载的是虚拟机自动生成的数组类型
        // [Loaded com.books.jvm.d7.SuperSubStaticInitTest$SuperClass
//        SuperClass[] a = new SuperClass[1];

        // SuperClass，SubClass没有被加载，也不会被初始化
        System.out.println(SubClass.i2);
    }
    
    
    static class SuperClass {
        static {
            System.out.println("Super");
        }
        static int i = 123;
        static final int i2 = 222;
    }
    
    static class SubClass extends SuperClass {
        static {
            System.out.println("Sub");
        }
    }
}
