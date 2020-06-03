package com.springapp.maidian;

import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhangbo
 * @date 2020/4/29
 */
@Slf4j
public class TrackSender {

    private static ExecutorService executorService = Executors.newFixedThreadPool(2, new DefaultThreadFactory("track-pool", true));

    public static void send(TrackEvent trackEvent) {
        executorService.execute(() -> {
            log.info("Track --->>> {}", trackEvent);
        });
    }

}
