package com.books.jvm_sz.chapter10;

import com.books.bingfayishu.d4.SleepUtils;

/**
 * 
 * 
 * @author zhangbo
 */
public class ClassInitDeadLockTest {

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Class.forName("com.books.jvm_sz.chapter10.ClassB");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Class.forName("com.books.jvm_sz.chapter10.ClassA");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    
}

class ClassA {
    static {
        SleepUtils.second(1);
        try {
            Class.forName("com.books.jvm_sz.chapter10.ClassB");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("ClassA init");
    }
}
class ClassB {
    static {
        SleepUtils.second(1);
        try {
            Class.forName("com.books.jvm_sz.chapter10.ClassA");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("ClassB init");
    }
}
