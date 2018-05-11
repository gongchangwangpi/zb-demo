package com.books.jvm;

import java.util.concurrent.TimeUnit;

/**
 * 构造函数this逃逸
 *
 *
 * Created by books on 2017/6/21.
 */
public class ConstructorEscape {

    final int i;

    static ConstructorEscape obj;

    public ConstructorEscape() {
        this.obj = this;
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.i = 11;
    }

    public static void writer() {
        new ConstructorEscape();
    }

    public static void reader() {
        if (obj != null) {
            System.out.println(obj.i);
        }
    }

    public static void main(String[] args) {

        Thread thread1 = new Thread(){

            @Override
            public void run() {
                writer();
            }
        };
        thread1.start();


        Thread thread2 = new Thread(){

            @Override
            public void run() {
                reader();
            }
        };
        thread2.start();


    }
}
