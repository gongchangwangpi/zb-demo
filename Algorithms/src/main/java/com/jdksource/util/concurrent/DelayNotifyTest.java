package com.jdksource.util.concurrent;

import cn.hutool.core.util.RandomUtil;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.FastDateFormat;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhangbo
 * @date 2020/12/9
 */
@Slf4j
public class DelayNotifyTest {

    private AtomicInteger count = new AtomicInteger();

    private DelayQueue<ShushuoNotify> delayQueue = new DelayQueue<>();

    private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(new DefaultThreadFactory("shushuo-notify"));

    public static void main(String[] args) throws InterruptedException {

        DelayNotifyTest delayNotifyTest = new DelayNotifyTest();
        delayNotifyTest.init();

        for (int i = 0; i < 5; i++) {
            delayNotifyTest.notify(null);
            TimeUnit.MILLISECONDS.sleep(300);
        }
    }


    @PostConstruct
    public void init() {
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            ShushuoNotify peek = delayQueue.peek();
            if (peek != null && peek.nextNotifyTime <= System.currentTimeMillis()) {
                ShushuoNotify shushuoNotify = delayQueue.poll();
                if (shushuoNotify != null) {
                    shushuoNotify.delay();
                    log.info("开始异步通知：{}", shushuoNotify);
                    notify(shushuoNotify);
                    return;
                }
            }
            log.info("当前没有异步通知任务");
        }, 10, 10, TimeUnit.SECONDS);
    }

    public boolean notify(ShushuoNotify notify) {
        try {
            if (notify == null) {
                notify = new ShushuoNotify(count.getAndIncrement(), 1, System.currentTimeMillis());
            }
            log.info("开始进行第{}次通知", notify.getRetryCount());
            // todo 模拟耗时
            TimeUnit.MILLISECONDS.sleep(100);
            int i = RandomUtil.randomNumber();
            if (i % 5 < 3) {
                throw new IllegalArgumentException("notify error");
            }
            log.info("通知成功");
            return true;
        } catch (Exception e) {
            if (notify.retryCount < 5) {
                log.error("通知失败，稍后将再次异步通知。notify = {}", notify, e);
                delayQueue.add(notify);
            } else {
                log.error("通知失败，达到最大通知次数，后续不在通知。notify = {}", notify, e);
            }
        }
        return false;
    }

    @Data
    @AllArgsConstructor
    public static class ShushuoNotify implements Delayed {

        private int id;
        private int retryCount;
        private long nextNotifyTime;

        public int delay() {
            retryCount++;
            nextNotifyTime = System.currentTimeMillis() + retryCount * 10 * 1000;
            return retryCount;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.toNanos(nextNotifyTime) - unit.toNanos(System.currentTimeMillis());
        }

        @Override
        public int compareTo(Delayed o) {
            if (this == o) {
                return 0;
            }
            ShushuoNotify other = (ShushuoNotify) o;
            return (int) (this.nextNotifyTime - other.nextNotifyTime);
        }

        @Override
        public String toString() {
            return "ShushuoNotify{" +
                    "id=" + id +
                    ", retryCount=" + retryCount +
                    ", nextNotifyTime=" + nextNotifyTime +
                    ", " + FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date(nextNotifyTime)) +
                    '}';
        }
    }

}
