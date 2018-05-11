package com.books.gaobingfa.d4;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * SimpleDateFormat多线程竞争不安全
 *
 * Created by books on 2017/4/27.
 */
public class SimpleDateFormatTest {

    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(20);

        for (int i = 0; i < 1000; i++) {
            executorService.submit(new Parser(i));
        }

        executorService.shutdown();
    }

    static class Parser implements Runnable {
        int i;

        public Parser(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            try {
//                Date date = sdf.parse("2017-04-27 14:22:" + i % 60);
                Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-04-27 14:22:" + i % 60);
                System.out.println(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

}
