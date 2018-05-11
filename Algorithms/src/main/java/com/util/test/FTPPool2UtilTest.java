package com.util.test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.util.pool2.FTPUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author books
 */
public class FTPPool2UtilTest {

    private static FTPUtil ftpUtil = new FTPUtil();

    private static AtomicInteger startCount = new AtomicInteger(0);

    private static AtomicInteger uploadSuccess = new AtomicInteger(0);
    private static AtomicInteger downloadSuccess = new AtomicInteger(0);

    private static int threadCount = 10;

    private static ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

    private static int count = 480;
    private static CountDownLatch totalCountDownLatch = new CountDownLatch(count);

    public static void main(String[] args) throws Exception {

        for (int i = 0; i < 48; i++) {
            work(new CountDownLatch(threadCount), threadCount);
            TimeUnit.HOURS.sleep(1);
        }

        work(new CountDownLatch(threadCount), threadCount);

        // 阻塞等待线程池执行
        totalCountDownLatch.await();
        
        executorService.shutdown();

        System.out.println("------------------------ shutdown");
    }
    
    private static void work(CountDownLatch countDownLatch, int count) throws Exception {
        
        System.out.println("------------------------ start");
        long start = System.currentTimeMillis();

        int begin = startCount.get();
        int total = begin + count;

        for (int i = begin; i < total; i++) {
            startCount.incrementAndGet();
            if (i % 2 == 0) {
                // 上传
                FileInputStream fis = new FileInputStream("F:\\img\\1.jpg");
                executorService.execute(new FtpUploadRunner(i, fis, countDownLatch));
            } else {
                // 下载
                executorService.execute(new FtpDownloadRunner(i, countDownLatch));
            }
        }

        countDownLatch.await();
        System.out.println("------------------------ end");
        long end = System.currentTimeMillis();

        System.out.println("upload success: " + uploadSuccess);
        System.out.println("download success: " + downloadSuccess);
        System.out.println("total use time：" + (end - start));
    }
    
    static class FtpUploadRunner implements Runnable {
        
        private static final Logger LOGGER = LoggerFactory.getLogger(FtpUploadRunner.class);

        final int fileNo;
        
        final InputStream inputStream;
        
        final CountDownLatch countDownLatch;

        public FtpUploadRunner(int fileNo, InputStream inputStream, CountDownLatch countDownLatch) {
            this.fileNo = fileNo;
            this.inputStream = inputStream;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            boolean success = false;
            long start = System.currentTimeMillis();
            try {
                String realFileName = fileNo + ".jpg";
                String middleDir = "private/hyb/vehicle2/partner/2018/pool/";

                success = ftpUtil.uploadFile(realFileName, inputStream, middleDir);
                if (success) {
                    uploadSuccess.incrementAndGet();
                }
            } finally {
                countDownLatch.countDown();
                totalCountDownLatch.countDown();
                long end = System.currentTimeMillis();
                LOGGER.info("-----*****>>>>> upload: {}, use time : {}", success, (end - start));
            }

        }
    }
    
    static class FtpDownloadRunner implements Runnable {
        
        private static final Logger LOGGER = LoggerFactory.getLogger(FtpDownloadRunner.class);

        final int fileNo;
        final CountDownLatch countDownLatch;

        public FtpDownloadRunner(int fileNo, CountDownLatch countDownLatch) {
            this.fileNo = fileNo;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            boolean success = false;
            long start = System.currentTimeMillis();
            try {
                String realFileName = "F:\\img\\ftppool\\" + fileNo + ".jpg";
                String remoteFileName = "private/hyb/vehicle2/partner/2018/02/02/623630f6-efdf-4c08-8fae-0f58706a24c5.jpg";

                success = ftpUtil.loadFile(remoteFileName, realFileName);
                if (success) {
                    downloadSuccess.incrementAndGet();
                }
            } finally {
                countDownLatch.countDown();
                totalCountDownLatch.countDown();
                long end = System.currentTimeMillis();
                LOGGER.info("-----*****>>>>> download: {}, use time : {}", success, (end - start));
            }

        }
    }
}
