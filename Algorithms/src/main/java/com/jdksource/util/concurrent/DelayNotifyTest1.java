package com.jdksource.util.concurrent;

import cn.hutool.core.util.RandomUtil;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import java.util.concurrent.*;

/**
 * @author zhangbo
 * @date 2020/12/9
 */
@Slf4j
public class DelayNotifyTest1 {

    private LinkedBlockingQueue<NotifyDto> delayQueue = new LinkedBlockingQueue<>();

    private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(new DefaultThreadFactory("shushuo-notify"));

    public static void main(String[] args) {

        DelayNotifyTest1 test = new DelayNotifyTest1();
        test.init();

        test.notify("123", null);

    }

    public void init() {
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            NotifyDto notify;
            while ((notify = delayQueue.poll()) != null) {
                retry(notify);
            }
            log.info("没有通知任务");
        }, 5, 5, TimeUnit.SECONDS);
    }

    private void retry(NotifyDto notify) {
        notify.incrRetryCount();
        log.info("异步通知开始：{}", notify);
        if (!notify(notify.param, notify)) {
            if (notify.retryCount < 5) {
                log.info("当前重试次数为：{}，将在下次继续通知", notify.retryCount);
                return;
            }
            log.info("当前重试已达最大次数{}次", notify.retryCount);
        }
        delayQueue.remove(notify);
    }

    public boolean notify(Object param, NotifyDto notifyDto) {
        try {
            // todo 模拟耗时
            TimeUnit.MILLISECONDS.sleep(100);
            int i = RandomUtil.randomNumber();
            if (i % 5 < 4) {
                throw new IllegalArgumentException("notify error");
            }
            log.info("启用数据源通知成功");
            return true;
        } catch (Exception e) {
            //
            log.error("启用数据源通知失败, param = {}", param, e);
            // 加入队列，下次再通知
            notifyDto = notifyDto == null ? new NotifyDto(0, param) : notifyDto;
            delayQueue.add(notifyDto);
        }
        return false;
    }

    @Data
    @AllArgsConstructor
    public static class NotifyDto {
        private int retryCount;
        private Object param;
        public void incrRetryCount() {
            this.retryCount = retryCount + 1;
        }
    }

}
