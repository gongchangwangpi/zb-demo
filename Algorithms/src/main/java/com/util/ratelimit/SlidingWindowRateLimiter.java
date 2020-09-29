package com.util.ratelimit;

import com.google.common.util.concurrent.AtomicDouble;
import lombok.extern.slf4j.Slf4j;

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
 *
 * 滑动窗口实现有问题，再想想
 *
 * @author zhangbo
 * @date 2020/9/28
 */
@Slf4j
public class SlidingWindowRateLimiter {

    // 计时
    private static long time = System.currentTimeMillis();
    // 计数器
    private static final AtomicDouble count = new AtomicDouble(0);
    // 每秒允许的请求数
    private static final double permit = 100;

    public static void main(String[] args) throws InterruptedException {
        int totalReq = 201;
        long begin = System.currentTimeMillis();
        for (int i = 0; i < totalReq; i++) {
//            TimeUnit.MILLISECONDS.sleep(5);
//            if (!acquire()) {
//                System.out.println("not acquire: " + i);
//            }
            if (!tryAcquire(3, TimeUnit.SECONDS)) {
                System.out.println("tryAcquire fail: " + i);
            }
        }
        long end = System.currentTimeMillis();
        System.out.println(end - begin);
    }

    private static boolean tryAcquire(long time, TimeUnit timeUnit) {
        long millis = timeUnit.toMillis(time);
        long begin = System.currentTimeMillis();
        do {
            if (acquire(1)) {
                return true;
            } else {
                long l = System.currentTimeMillis() - begin - millis;
                if (l > 0) {
                    try {
                        Thread.sleep(l);
                    } catch (InterruptedException e) {
                        //
                    }
                }
            }
        } while (System.currentTimeMillis() - begin <= millis);
        return false;
    }

    private static synchronized boolean acquire(double acquire) {
        long now = System.currentTimeMillis();
        if (now - time > 1000) {
            log.info("clean time and count");
            count.set(0);
            time = now;
        }
        return count.addAndGet(acquire) <= permit;
    }

}
