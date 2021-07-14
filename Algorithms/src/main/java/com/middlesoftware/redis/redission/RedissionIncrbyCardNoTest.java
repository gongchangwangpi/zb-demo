package com.middlesoftware.redis.redission;

import org.apache.commons.lang3.time.FastDateFormat;
import org.redisson.Redisson;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 生成指定数量的卡号。利用Redis的 incrby 原子指令
 *
 * @author bo6.zhang
 * @date 2021/7/6
 */
public class RedissionIncrbyCardNoTest {

    static RedissonClient redissonClient;

    private static final String card_no_incr_key = "m_giftcard_incr:%s";

    public static void main(String[] args) {

        init();

        List<String> cardNoList = generateCardNoList(100);
        System.out.println(cardNoList);

        cardNoList = generateCardNoList(10);
        System.out.println(cardNoList);

        cardNoList = generateCardNoList(99);
        System.out.println(cardNoList);

        cardNoList = generateCardNoList(5);
        System.out.println(cardNoList);

        cardNoList = generateCardNoList(1000);
        System.out.println(cardNoList);

        cardNoList = generateCardNoList(10);
        System.out.println(cardNoList);

        cardNoList = generateCardNoList(10);
        System.out.println(cardNoList);

    }


    /**
     * 生成指定数量的卡号。利用Redis的 incrby 原子指令
     *
     * 卡号规则：6位日期(yyMMdd) + 6位数字(从1开始，不足补0，多余6位则递增)
     * 如： 210706000001 210706000010 210706999999 2107060000001
     *
     * @param count
     * @return
     */
    public static List<String> generateCardNoList(int count) {
        String dateString = getDateString();
        String key = String.format(card_no_incr_key, dateString);

        RAtomicLong atomicLong = redissonClient.getAtomicLong(key);
        long incrby = atomicLong.addAndGet(count);
        if (incrby == count) {
            expire(key, 24 * 60 * 60 * 1000);
        }

        List<String> cardNoList = new ArrayList<>(count);
        for (long i = incrby - count + 1; i <= incrby; i++) {
            cardNoList.add(dateString + padding(i));
        }
        return cardNoList;
    }

    /**
     * 不足6位的在前面补0
     *
     * @param i
     * @return
     */
    private static String padding(long i) {
        return String.format("%06d", i);
    }

    public static void expire(String key, int expire) {
        RBucket<Object> bucket = redissonClient.getBucket(key);
        bucket.expire(expire, TimeUnit.MILLISECONDS);
    }

    private static String getDateString() {
        return FastDateFormat.getInstance("yyMMdd").format(new Date());
    }

    private static void init() {
        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig.setAddress("redis://127.0.0.1:6379");
//        singleServerConfig.setPassword("jintoufs");

        redissonClient = Redisson.create(config);
    }

}
