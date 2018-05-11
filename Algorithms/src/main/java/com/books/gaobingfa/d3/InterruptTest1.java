package com.books.gaobingfa.d3;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.books.bingfayishu.d4.SleepUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * {@link Thread#interrupt()}
 * 
 * interrupt 打断一个在wait，join，sleep，和阻塞在特定的I/O上的线程时，
 * 会收到一个 Interruption 异常；
 * 
 * 其他情况下，只会设置线程的状态为interrupt，可以通过{@link Thread#isInterrupted()}判断
 * 
 * @author zhangbo
 */
@Slf4j
public class InterruptTest1 {
    
    static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        Thread t1 = new Thread(new Runner());
        t1.start();

        SleepUtils.millis(100);
        
        // 
        t1.interrupt();
        log.info("t1 interrupt");

        SleepUtils.second(2);
        
        log.info("t1 isInterrupted: {}", t1.isInterrupted()); // true

    }
    
    static class Runner implements Runnable {

        @Override
        public void run() {
            log.info("Runner run...");
            
            // InterruptedException: sleep interrupted
            SleepUtils.second(5);
            
            // 无异常，相当于唤醒了线程，继续执行后面的代码,线程会标志为 isInterrupted
//            LockSupport.park();
            log.info("Runner end...");
            
            // 无异常，线程会标志为 isInterrupted
            while (true) {

            }

            /*try {
                // 尝试阻塞在网络IO上
                *//*URL url = new URL("http://test.pay.jhjhome.net/wxpay/h5pay.jsp");

                InputStream inputStream = url.openStream();
                
                log.info("返回结果：", inputStream);*//*
                
                // 阻塞在文件IO上，不会抛出异常，会标志该线程为 isInterrupted
                *//*FileInputStream inputStream = new FileInputStream("F:\\OpenSource\\CentOS-6.5-x86_64-minimal.iso");
                FileOutputStream outputStream = new FileOutputStream("F:\\OpenSource\\CentOS-6.5-x86_64-minimal(1).iso");
                
                IOUtils.copy(inputStream, outputStream);
                
                IOUtils.closeQuietly(outputStream);
                IOUtils.closeQuietly(inputStream);*//*

            } catch (Exception e) {
                log.error("链接失败", e);
            }

            log.info("Runner end...");*/
        }
    }
    
}
