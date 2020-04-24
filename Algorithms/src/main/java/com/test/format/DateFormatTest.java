package com.test.format;

import com.util.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by books on 2017/11/23.
 */
public class DateFormatTest extends Thread {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private String name;
    private String dateStr;
    private boolean sleep;

    public DateFormatTest(String name, String dateStr, boolean sleep) {
        this.name = name;
        this.dateStr = dateStr;
        this.sleep = sleep;
    }

    @Override
    public void run() {

        Date date = null;

        if (sleep) {
            try {
                TimeUnit.MILLISECONDS.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
//            date = sdf.parse(dateStr);
            date = DateUtil.defaultParseDate(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(name + " : date: " + date);
    }

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executor = Executors.newCachedThreadPool();

        executor.execute(new DateFormatTest("1", "1991-09-13", false));
        executor.execute(new DateFormatTest("2", "1992-09-13", false));
        executor.execute(new DateFormatTest("3", "1993-09-13", false));
        executor.execute(new DateFormatTest("4", "1994-09-13", false));
        executor.execute(new DateFormatTest("5", "1995-09-13", false));
        executor.execute(new DateFormatTest("6", "1996-09-13", false));
        executor.execute(new DateFormatTest("7", "1997-09-13", false));
        executor.execute(new DateFormatTest("8", "1998-09-13", false));
        executor.execute(new DateFormatTest("9", "1999-09-13", false));

        executor.shutdown();
    }
    
}
