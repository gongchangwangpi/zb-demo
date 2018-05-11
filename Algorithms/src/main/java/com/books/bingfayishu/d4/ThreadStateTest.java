package com.books.bingfayishu.d4;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 测试线程的各个状态
 * 
 * @see Thread.State
 * 
 * @author zhangbo
 */
public class ThreadStateTest {
    

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        executorService.submit(new Runner());
        
        Thread1 thread1 = new Thread1();


        // 获取Java线程管理MXBean
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        // 不需要获取同步的monitor和synchronizer信息，仅获取线程和线程堆栈信息
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(true, true);
        // 遍历线程信息，仅打印线程ID和线程名称信息
        for (ThreadInfo threadInfo : threadInfos) {
            System.out.println("[" + threadInfo.getThreadId() + "] " + threadInfo.
                    getThreadName() + " : " + threadInfo.getThreadState());
        }
        
    }
    
    
    static class Thread1 extends Thread {
        @Override
        public void run() {
            System.out.println("Thread1");
        }
    }
    
    static class Runner implements Runnable {

        @Override
        public void run() {
            System.out.println("Runner");
        }
    }
}
