package com.books.bingfayishu.d3;

/**
 * 内部类方式
 * 静态内部类，由于jvm的类加载机制，保证了只有一个线程能正确的加载一个class
 * 
 * @author zhangbo
 */
public class InnerClassHolderSingletonDemo {
    
    static class InstanceHolder {
        public InstanceHolder() {
            System.out.println("InstanceHolder");
        }

        public static InnerClassHolderSingletonDemo instance = new InnerClassHolderSingletonDemo();
    }
    
    public static InnerClassHolderSingletonDemo getInstance() {
        return InstanceHolder.instance ; // 这里将导致InstanceHolder类被初始化
    }

    public static void main(String[] args) {

        System.out.println("main");
        
    }
}
