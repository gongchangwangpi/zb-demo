package com.books.bingfayishu.d4;

/**
 * @see Thread#start() 
 * 
 * @author zhangbo
 */
public class ThreadJoin {

    public static void main(String[] args) {
        
        Thread previous = Thread.currentThread();
        
        for (int i = 0; i < 10; i++) {

            Thread thread = new Thread(new JoinRunner(previous), String.valueOf(i));
            
            thread.start();
            // 重新赋值previous
            previous = thread;
            
            SleepUtils.second(1);

        }

        System.out.println(Thread.currentThread().getName() + " terminate.");
    }
    
    static class JoinRunner implements Runnable {
        private Thread previous;

        public JoinRunner(Thread previous) {
            this.previous = previous;
        }

        @Override
        public void run() {
            try {
                previous.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " terminate.");
        }
    }
}
