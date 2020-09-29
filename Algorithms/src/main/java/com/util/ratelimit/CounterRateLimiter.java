package com.util.ratelimit;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 限流算法：计数器、滑动窗口，漏桶，令牌桶
 *
 * 计数器：实现简单，但可能会有恶意请求，造成每秒刚开始就耗尽请求数，导致正常请求进不来
 * 滑动窗口：相当于在每秒限制的基础上，切分更小的时间片，如每10毫秒等，这样将请求尽量平均到每个细分的时间片，尽量减少恶意请求占用
 * 漏桶：请求更加平滑，匀速的放过每个请求，但不能应对小的流量突刺等
 * 令牌桶：预先存放一些令牌，在匀速的向桶中存放令牌，可以应对流量突刺，请求也会比较平滑
 *
 * @author zhangbo
 * @date 2020/9/28
 */
@Slf4j
public class CounterRateLimiter {

    // 计时
    private static long time = System.currentTimeMillis();
    // 计数器
    private static final AtomicInteger count = new AtomicInteger(0);
    // 每秒允许的请求数
    private static final int permit = 100;

    public static void main(String[] args) throws InterruptedException {
        int totalReq = 201;

        CountDownLatch countDownLatch = new CountDownLatch(totalReq);
        ExecutorService executorService = Executors.newFixedThreadPool(8);

        long begin = System.currentTimeMillis();

        for (int i = 0; i < totalReq; i++) {
            final int j = i;
            executorService.execute(() -> {
                try {
                    if (!tryAcquire(1, TimeUnit.SECONDS)) {
                        System.out.println("tryAcquire fail: " + j);
                    } else {
                        log.info("acquire success: {}", j);
                    }
                } catch (InterruptedException e) {
                    //
                } finally {
                    countDownLatch.countDown();
                }
            });
        }

        countDownLatch.await();
        long end = System.currentTimeMillis();
        System.out.println(end - begin);

        executorService.shutdown();
    }

    private static boolean tryAcquire(long time, TimeUnit timeUnit) throws InterruptedException {
        long millis = timeUnit.toMillis(time);
        long begin = System.currentTimeMillis();
        do {
            if (acquire()) {
                return true;
            } else {
                long l = System.currentTimeMillis() - begin - millis;
                if (l > 0) {
                    Thread.sleep(l);
                }
            }
        } while (System.currentTimeMillis() - begin <= millis);
        return false;
    }

    private static synchronized boolean acquire() {
        long now = System.currentTimeMillis();
        if (now - time > 1000) {
            log.info("clean time and count");
            count.set(0);
            time = now;
        }
        return count.incrementAndGet() <= permit;
    }

}
