package com.books.jvm.d13;


import java.util.Vector;

/**
 * 多线程环境Vector线程安全测试
 *
 * Created by Administrator on 2017/6/5 0005.
 */
public class VectorThreadSafeTest {

    private static Vector<Integer> vector = new Vector<>();

    public static void main(String[] args) {

        while (true) {
            for (int i = 0; i < 100; i++) {
                vector.add(i);
            }

            /*Thread removeThread = new Thread(() -> {
                for (int i = 0; i < vector.size(); i++) {
                    vector.remove(i);
                }
            });

            Thread printThread = new Thread(() -> {
                for (int i = 0; i < vector.size(); i++) {
                    System.out.println(vector.get(i));
                }
            });

            removeThread.setUncaughtExceptionHandler((Thread t, Throwable e) -> {
                e.printStackTrace();
                System.exit(-1);
            });
            printThread.setUncaughtExceptionHandler((Thread t, Throwable e) -> {
                e.printStackTrace();
                System.exit(-1);
            });
            removeThread.start();
            printThread.start();*/
            // 不要同时产生过多的线程，否则会导致操作系统假死
            while (Thread.activeCount() > 40);
        }

    }

}
