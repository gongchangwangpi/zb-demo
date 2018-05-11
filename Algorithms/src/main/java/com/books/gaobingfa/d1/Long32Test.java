package com.books.gaobingfa.d1;

/**
 * 测试32位虚拟机对64位Long值的读取和修改
 *
 * Created by books on 2017/4/17.
 */
public class Long32Test {

    static long l;

    public static class Update implements Runnable {

        long temp;

        public Update(long temp) {
            this.temp = temp;
        }

        @Override
        public void run() {
            while (true) {
                Long32Test.l = temp;
                Thread.yield();
            }
        }
    }

    public static class Read implements Runnable {

        @Override
        public void run() {
            while (true) {
                long temp = Long32Test.l;
                if (temp != 111 && temp != 222 && temp != -333 && temp != -444) {
                    System.out.println(temp);
                }
                Thread.yield();
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new Update(111L)).start();
        new Thread(new Update(222L)).start();
        new Thread(new Update(-333L)).start();
        new Thread(new Update(-444L)).start();

        new Thread(new Read()).start();
    }

}
