package com.books.gaobingfa.d2;

/**
 *
 *
 * Created by books on 2017/4/20.
 */
public class NoVisibility {

//    private static volatile boolean ready;
    private static boolean ready;

    private static int num;

    public static class ReadRun implements Runnable {

        @Override
        public void run() {
            while (!ready);
            // 只有准备好了才打印
            System.out.println(num);
        }
    }

    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(new ReadRun());
        thread.start();
//        thread.sleep(1000);

        num = 12;
        ready = true;

        Thread.sleep(1000);
    }

}
