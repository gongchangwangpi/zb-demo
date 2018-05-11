package com.test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.UUID;

import com.util.FTPUtil;

/**
 * @author books
 */
public class FTPUtilTest {

    public static void main(String[] args) throws Exception {

        InputStream inputStream = new FileInputStream("F:\\img\\122.jpg");
        String fileName = UUID.randomUUID().toString() + ".jpg";
        String middleDir = getMiddlePath();

        long start = System.currentTimeMillis();

        FTPUtil ftpUtil = new FTPUtil();
        ftpUtil.setIp("172.18.8.22");
        ftpUtil.setPort(21);
        ftpUtil.setUserName("jhjHomeImage");
        ftpUtil.setPassword("jhjHomeImage");
        ftpUtil.setUploadUrl("/");

        boolean succeed = ftpUtil.uploadFile(fileName, inputStream, middleDir);

        System.out.println("ftp upload succeed: " + succeed);

        long end = System.currentTimeMillis();
        System.out.println("use time: " + (end - start));
    }


    private static String getMiddlePath(){
        Calendar c = Calendar.getInstance();// 获得系统当前日期
        int m = c.get(Calendar.MONTH) + 1;
        int d = c.get(Calendar.DATE);
        String month = ((m < 10) ? "0" : "") + m;
        String day = ((d < 10) ? "0" : "") + d;
        return "private/hyb/vehicle2/partner/" + c.get(Calendar.YEAR) + "/" + month+"/" + day + "/";
    }
}
