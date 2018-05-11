package com.books.bingfayishu.d8;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.books.bingfayishu.d4.SleepUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangbo
 */
@Slf4j
public class ExchangerTest {

    public static void main(String[] args) {

        Exchanger<String> exchanger = new Exchanger<>();

        ExecutorService executorService = Executors.newFixedThreadPool(4);
        
        executorService.execute(new Job("job1", exchanger));
        SleepUtils.second(5);
        executorService.execute(new Job("job2", exchanger));
//        executorService.execute(new Job("job3", exchanger));
//        executorService.execute(new Job("job4", exchanger));
        
        executorService.shutdown();

    }
    
    static class Job implements Runnable {
        String msg;
        Exchanger<String> exchanger;

        public Job(String msg, Exchanger<String> exchanger) {
            this.msg = msg;
            this.exchanger = exchanger;
        }

        @Override
        public void run() {
            log.info("exchange before: {}", msg);
            try {
                if ("job2".equals(msg)) {
                    // 模拟一个线程耗时操作
                    SleepUtils.second(3);
                }
                // 先达到exchange的线程在此阻塞，直到有另外的线程也达到exchange，完成数据交换
                // 先到的线程，总是和紧接着后到的线程交换数据
                String after = exchanger.exchange(msg);
                
                log.info("exchange after: {}", after);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
