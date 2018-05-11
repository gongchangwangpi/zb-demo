package com.util.test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.util.FTPPoolUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author books
 */
public class FTPPoolUtilTest {
    
    static AtomicInteger startCount = new AtomicInteger(0);
    
    static AtomicInteger uploadSuccess = new AtomicInteger(0);
    static AtomicInteger downloadSuccess = new AtomicInteger(0);
    
    static ExecutorService executorService = Executors.newFixedThreadPool(40);

    static int count = 120;
    static CountDownLatch totalCountDownLatch = new CountDownLatch(count);

    public static void main(String[] args) throws Exception {
        
        // 预加载FTPPoolUtil, 提前初始化FTP client
        Class.forName("com.util.FTPPoolUtil");
        
        work(new CountDownLatch(40), 40);
        TimeUnit.HOURS.sleep(7);
        
        work(new CountDownLatch(40), 40);
        TimeUnit.HOURS.sleep(9);
        
        work(new CountDownLatch(40), 40);

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
                String middleDir = "private/hyb/vehicle2/partner/2018/";

                success = FTPPoolUtil.uploadFile(realFileName, inputStream, middleDir);
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
                String realFileName = "F:\\img\\ftp\\" + fileNo + ".jpg";
                String remoteFileName = "private/hyb/vehicle2/partner/2018/02/02/623630f6-efdf-4c08-8fae-0f58706a24c5.jpg";

                success = FTPPoolUtil.loadFile(remoteFileName, realFileName);
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
