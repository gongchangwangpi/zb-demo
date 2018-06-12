package com.jdksource.lang;

import com.books.bingfayishu.d4.SleepUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * interrupt只能是线程自身打断自己
 * 如果当前线程在活跃运行中，只是设置打断标志，不会真实的打断
 * 如果是在阻塞中(wait，join，sleep)中，则会收到InterruptedException异常
 * 不论是设置标志位还是InterruptedException异常，线程都不会立即终止，直到任务结束
 * 
 * @author zhangbo
 */
@Slf4j
public class InterruptThreadTest {
    
    private static final Object lock = new Object();

    public static void main(String[] args) {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                boolean flag = true;
                long start = System.currentTimeMillis();
                Thread currentThread = Thread.currentThread();
                while (flag) {
                    /*synchronized (lock) {
                        try {
//                            log.info("----->>> wait");
//                            lock.wait();
                            log.info("----->>> sleep");
                            TimeUnit.SECONDS.sleep(5);
                        } catch (InterruptedException e) {
                            boolean interrupted = currentThread.isInterrupted();
                            log.error("InterruptedException --->>> {}, {}",interrupted, e);
                        }
                    }*/
                    
                    long now = System.currentTimeMillis();
                    if (now - start > 1000) {
                        log.info(">>>>>>>>>>>>>>>>> interrupt current thread");
                        currentThread.interrupt();
                    }
                    boolean interrupted = currentThread.isInterrupted();
                    if (interrupted) {
                        flag = false;
                        log.info(">>>>> interrupted");
                    }
                    log.info("----- while");
                }
            }
        });
        
        t.start();

        SleepUtils.second(1);
        
//        t.interrupt();

    }
    
}
