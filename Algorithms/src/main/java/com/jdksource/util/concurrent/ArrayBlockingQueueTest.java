package com.jdksource.util.concurrent;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * ArrayBlockingQueue测试
 * 主要测试condition 的put 和take方法
 *
 * Created by books on 2017/4/10.
 */
public class ArrayBlockingQueueTest {

    public static void main(String[] args) throws Exception {

        ArrayBlockingQueue<String> abq = new ArrayBlockingQueue<>(2);

        new TakeThread(abq).start();

        for (int i = 0; i < 100; i++) {
            abq.put(String.valueOf(i));
            System.out.println("put ..." + abq);
        }
    }

}

class TakeThread extends Thread {

    ArrayBlockingQueue<String> abq;

    public TakeThread(ArrayBlockingQueue<String> abq) {
        this.abq = abq;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String take = abq.take();
                System.out.println("take ... " + take);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
