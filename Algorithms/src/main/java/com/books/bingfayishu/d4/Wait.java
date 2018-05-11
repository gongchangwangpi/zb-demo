package com.books.bingfayishu.d4;

/**
 * @author zhangbo
 */
public class Wait {

    private static Object lock = new Object();
    
    public static void main(String[] args) throws Exception {
        
        new Runner1().start();
        
        SleepUtils.second(2);
        
        new Runner2().start();
        
    }
    
    static class Runner1 extends Thread {
        @Override
        public void run() {
            synchronized (lock) {
                System.out.println("Runner1 wait");

                try {
                    // wait在超时后，会继续获取对象锁，如果获取到则继续执行，
                    // 没有获取到则一直阻塞(BLOCKED)到该对象锁释放时再次尝试获取
                    lock.wait(1000 * 5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("Runner1 ......");
            }
        }
    }
    
    static class Runner2 extends Thread {
        @Override
        public void run() {
            synchronized (lock) {
                System.out.println("Runner2 sleep");
                SleepUtils.second(60);
            }
        }
    }
}
