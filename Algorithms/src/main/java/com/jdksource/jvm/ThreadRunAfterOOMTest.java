package com.jdksource.jvm;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *
 * 测试在JVM OOM后，其他线程是否可以正常工作
 * -Xmx50M
 *
 *
 * @author zhangbo
 * @date 2019-09-20
 */
@Slf4j
public class ThreadRunAfterOOMTest {

    private static final int K = 1024;
    private static final int M = 1024 * K;

    public static void main(String[] args) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    log.info("---- work ----");
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "worker thread").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<byte[]> bytesList = new ArrayList<>();
                int i = 1;
                while (true) {
//                    try {
                        bytesList.add(new byte[10 * M]);
                        log.info(" -- OOM -- " + i++);
                        try {
                            TimeUnit.SECONDS.sleep(2);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
//                    } catch (Error e) {
                    // catch后，线程可以继续执行
//                        log.error("--- OOM --- --- ---", e);
//                        if (i <= 10) {
//                            log.warn("--- OOM --- clear ---");
//                            bytesList.clear();
//                        }
//                    }
                }
            }
        }, "OOM Thread").start();
    }

}
