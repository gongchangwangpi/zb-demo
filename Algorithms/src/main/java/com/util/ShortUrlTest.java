package com.util;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author zhangbo
 * @date 2020-01-19
 */
public class ShortUrlTest {

    private static final String[] URL_STR;
    private static final AtomicLong atomicLong = new AtomicLong(1);

    static {
        URL_STR = "qwertyuiopasdfghjklzxcvbnm_0123456789-MNBVCXZASDFGHJKLPOIUYTREWQ".split("");
    }

    public static void main(String[] args) {

        int count = 1_000_000_000;

//        long s1 = System.currentTimeMillis();
//        for (int i = 0; i < count; i++) {
//            shortUrl();
//        }
//        long s2 = System.currentTimeMillis();
//
//        System.out.println("shortUrl = " + (s2 - s1));

        System.out.println(scale((long) Math.pow(64, 3)));
        System.out.println(scale(10_000_000_000L, 64));
    }

    public static String shortUrl(String longUrl) {
        String shortUrl = shortUrl();
        // 存DB
        // 存Redis，大概10天过期
        return shortUrl;
    }

    public void redirect(String shortUrl) {
        // 查找对应的longUrl
        String longUrl = longUrl(shortUrl);
        // 存日志记录
        // response.sendRedirect(longUrl);
    }

    private String longUrl(String shortUrl) {
        String longUrl = null;
        // 从Redis取
        // if Redis没有，取DB，存Redis
        return longUrl;
    }

    private static String shortUrl() {
        long count = atomicLong.getAndIncrement();
        return scale(count, 64);
    }

    private static String scale(long number, int scale) {
        StringBuilder result = new StringBuilder();
        while (number > 0) {
            result.insert(0, URL_STR[(int) (number % scale)]);
            number /= scale;
        }
        return result.toString();
    }

    private static String scale(long number) {
        StringBuilder result = new StringBuilder();
        while (number > 0) {
            result.insert(0, URL_STR[(int) (63 & number)]);
            number = number >> 6;
        }
        return result.toString();
    }
}
