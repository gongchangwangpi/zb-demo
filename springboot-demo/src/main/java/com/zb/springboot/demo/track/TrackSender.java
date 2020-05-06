package com.zb.springboot.demo.track;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhangbo
 * @date 2020/4/29
 */
@Slf4j
public class TrackSender {

    private static ExecutorService executorService = Executors.newFixedThreadPool(2, new BasicThreadFactory.Builder().namingPattern("track-pool").daemon(true).build());

    public static void send(TrackEvent trackEvent) {
        executorService.execute(() -> {
            log.info("Track --->>> {}", trackEvent);
        });
    }

}
