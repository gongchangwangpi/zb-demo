package com.books.gaobingfa.d3;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * 循环屏障
 *
 * 在各个线程执行到 await 后，会挂起等待，直到 await 的线程数达到构造函数的参数时，
 * 才会继续执行后面的代码。用 循环屏障 的时候要注意，如果是在线程池中使用，
 * 则线程池的线程数量一定要大于 CyclicBarrier 的构造函数的数量
 *
 * 如果使用下面的构造函数，那么最后一个执行 await 的线程，会执行这个 Runnable 的 run 方法
 * public CyclicBarrier(int parties, Runnable barrierAction)
 * 示例见 CyclicBarrierTest1.java
 *
 * CyclicBarrier 与 CountDownLatch 的还有一个区别是：
 * CyclicBarrier 可以循环使用，只要每次await的线程数量达到构造函数的要求时，便会唤醒这次await的所有线程，继续执行
 * CountDownLatch 只能使用一次，不能循环使用
 *
 * <p>
 * Created by books on 2017/4/21.
 */
public class CyclicBarrierTest {

    static int n = 5;

    public static void main(String[] args) throws InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(n);

        for (int i = 0; i < 15; i++) {
            Thread thread = new Thread(new Student(String.valueOf(i), cyclicBarrier));
            thread.start();
            if (i % 5 == 0) {
                TimeUnit.SECONDS.sleep(2);
            }
            /*if (i == 12) {
                thread.interrupt();
            }*/
        }
    }


    static class Student implements Runnable {
        String name;
        CyclicBarrier cyclicBarrier;

        public Student(String name, CyclicBarrier cyclicBarrier) {
            this.name = name;
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            try {
                System.out.println(name + "\tawait  " + cyclicBarrier.getNumberWaiting() + "  " + cyclicBarrier.getParties());
                cyclicBarrier.await();

                TimeUnit.SECONDS.sleep(1);
                System.out.println(name + "\tdone");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
