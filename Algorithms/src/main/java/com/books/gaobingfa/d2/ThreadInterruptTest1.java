package com.books.gaobingfa.d2;

import java.util.concurrent.TimeUnit;

/**
 * interrupt方法在线程没有阻塞的情况下，不会抛出{@link InterruptedException},仅设置interrupt标志位
 * 如需正确的中断线程，需要线程自身周期性的检查interrupt标志位，做出合理的中断响应。
 * 具体详见{@link Thread#interrupt()}的注释。interrupt方法只会在线程sleep/join/wait/IO操作/阻塞Selector上时，
 * 会抛出{@link InterruptedException}
 *
 *
 * Created by books on 2017/4/17.
 */
public class ThreadInterruptTest1 {

    private static int i = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                while (true) {
                    i++;
                    if (i % 100_000 == 0) {
                        System.out.println(i);
                    }
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println("interrupted ..----- ");
                        break;
                    }
                }
            }
        };

        t1.start();
        TimeUnit.MILLISECONDS.sleep(20);
        t1.interrupt();

        System.out.println("main interrupt thread ............");

    }
}