package com.test;

/**
 *  多线程情况下，可能会存在 i != i 的情况
 *  != 操作, 是先读取左边的 i, 然后在读取右边的 i
 *  整个操作进行了两次读取操作, 所以可能会存在两次 i 的值不一致
 *  而导致 i != i 为true
 *
 *  单线程情况下 i != i 永远为 false
 *
 * Created by books on 2017/4/26.
 */
public class MulThreadUpdateTest {

    static int i = 0;

    public static void main(String[] args) {

        Thread thread = new Thread(new UpdateRun());
        thread.start();
        for (int j = 0; j < 10000000; j++) {

            if (i != i) {
                System.out.println("i = " + i);
            }
        }
    }

    static class UpdateRun implements Runnable {

        @Override
        public void run() {
            for (int j = 0; j < 10000000; j++) {
                i = j;
            }
        }
    }

}
