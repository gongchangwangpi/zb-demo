package com.books.bingfayishu.d8;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import com.books.bingfayishu.d4.SleepUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 
 * @author zhangbo
 */
@Slf4j
public class CyclicBarrierTest {

    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(3, () -> 
            // 最后一个达到循环阑珊的线程会先执行该代码，然后执行await后的代码
        log.info("cyclicBarrier --- Run")
    );
    
    private static AtomicInteger counter = new AtomicInteger(0);
    
    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 3; i++) {
            executorService.execute(new Job(cyclicBarrier));
        }

        executorService.shutdown();
    }
    
    static class Job implements Runnable {

        CyclicBarrier cyclicBarrier;

        public Job(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            try {
                log.info(" ---->>> await ");
                int count = counter.incrementAndGet();
                if (count % 3 == 0) {
                    log.info(" ---*** sleep 3s");
                    SleepUtils.second(3);
                }
                // 线程在此阻塞，直到阻塞的线程数达到构造器传入的参数时，
                // 所有阻塞的线程都全部唤醒，继续执行await后面的代码
                cyclicBarrier.await();
                
                log.info("============= ");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
}
