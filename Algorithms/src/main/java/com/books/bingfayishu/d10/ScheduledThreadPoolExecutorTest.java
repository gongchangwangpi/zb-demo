package com.books.bingfayishu.d10;

import java.util.concurrent.*;

import com.books.bingfayishu.d4.SleepUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangbo
 */
@Slf4j
public class ScheduledThreadPoolExecutorTest {

    public static void main(String[] args) throws Exception {

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);

        ScheduledFuture<Integer> result = scheduledExecutorService.schedule(() -> {
                    log.info("schedule delay");
                    return 1;
                }
                , 3, TimeUnit.SECONDS);


        log.info("---->>> {}", result.get());
        
        // scheduleWithFixedDelay, 是在任务执行完毕后，在延迟delay时间开始执行下一此任务
        // 中间会多延迟了每次任务执行所需的时间9
      scheduledExecutorService.scheduleWithFixedDelay(() -> {
                        SleepUtils.second(2);
                        log.info("scheduleWithFixedDelay");}
                , 3, 3, TimeUnit.SECONDS);
        
        // scheduleAtFixedRate, 是在任务开始执行后，在延迟delay时间开始执行下一此任务
        // 以固定的时间间隔开始每次任务，不考虑每次任务所花费的时间
        scheduledExecutorService.scheduleAtFixedRate(() -> {
                        SleepUtils.second(2);
                        log.info("scheduleAtFixedRate");}
                , 3, 3, TimeUnit.SECONDS);
        
//      scheduledExecutorService.shutdown();
    }
    
}
