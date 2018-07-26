package com.zb.demo.util.wechat;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 获取微信AccessToken的工具类，此类需单点部署，避免多线程同时更新token
 * 
 * @author zhangbo
 */
@Slf4j
public final class WechatAccessTokenCache {

    private WechatAccessTokenCache() {
    }
    
    private static ReentrantLock lock = new ReentrantLock();
    
    /**
     * 定时更新token的线程池
     */
    private static ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1, new BasicThreadFactory.Builder().namingPattern("wechat-token-updater-%d").daemon(true).build());
    /**
     * 用于存储更新token的定时任务,一个appid对应一个任务,避免多个线程更新同一个appid
     */
    private static final Map<String, TokenUpdater> updaterMap = new HashMap<>();
    /**
     * 缓存token
     */
    private static final Map<String, String> tokenCacheMap = new HashMap<>();
    /**
     * 默认90分钟更新
     */
//    private static final long period = 90 * 60 * 1000;
    private static final long period = 10 * 1000;
    
    /**
     * 获取AccessToken
     * 
     * @param appid
     * @return
     */
    public static String getAccessToken(String appid) {
        long start = System.currentTimeMillis();
        String token = tokenCacheMap.get(appid);
        
        if (StringUtils.isNotEmpty(token)) {
            log.info("【获取微信access_token】从缓存中获取token: {} --> {}, use time: {}", appid, token, System.currentTimeMillis() - start);
            return token;
        }
        
        lock.lock();
        try {
            token = tokenCacheMap.get(appid);
            if (StringUtils.isNotEmpty(token)) {
                log.info("【获取微信access_token】阻塞后从缓存中获取token: {} --> {}, use time: {}", appid, token, System.currentTimeMillis() - start);
                return token;
            }
            
            if (!updaterMap.containsKey(appid)) {
                // ---- 还没有该appid对应的定时任务,需要直接从微信获取token,然后添加定时任务 ---- //
                
                log.info("【获取微信access_token】首次获取token,等待直接从微信获取");
                // 从微信获取token并放入缓存
                token = getTokenFromWechat(appid);
                tokenCacheMap.put(appid, token);

                // 新增定时任务,定时更新token
                TokenUpdater tokenUpdater = new TokenUpdater(appid);
                scheduledExecutorService.scheduleWithFixedDelay(tokenUpdater, period, period, TimeUnit.MILLISECONDS);

                updaterMap.put(appid, tokenUpdater);
            } else {

                // ---- 当前有定时任务正在更新token,阻塞等待更新 ---- //
                
                log.info("【获取微信access_token】缓存token为空,需阻塞等待token更新");
                CountDownLatch countDownLatch = new CountDownLatch(1);
                TokenUpdater updater = updaterMap.get(appid);
                updater.setCountDownLatch(countDownLatch);
                try {
                    // 等待定时任务唤醒
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    log.error("【获取微信access_token】在阻塞等待更新缓存过程中被打断", e);
                }
                // 唤醒后直接从缓存中获取更新后的token
                token = tokenCacheMap.get(appid);
            }
            
        } finally {
            lock.unlock();
        }
        
        log.info("【获取微信access_token】等待定时任务更新后获取,appid: {} ---> {}, use time: {}", appid, token, System.currentTimeMillis() - start);
        return token;
    }

    private static String getTokenFromWechat(String appid) {
        /*String token = null;
        try {
            token = WechatAccessTokenUtil.getToken(appid);
            log.info("get token from wechat: {}", token);
        } catch (Exception e) {
            log.error("【获取微信access_token】获取失败", e);
        }
        return token;*/
        try {
            // 模拟耗时
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String token = appid + System.currentTimeMillis();
//        log.info("get token from wechat: {}", token);
        return token;
    }

    /**
     * 定时更新缓存Map中的token
     * <p>
     *     90分钟，微信token默认120分钟失效
     * </p>
     * 
     */
    static class TokenUpdater implements Runnable {

        private static Logger log = LoggerFactory.getLogger(TokenUpdater.class);
        
        private String appid;
        private CountDownLatch countDownLatch;

        public TokenUpdater(String appid) {
            this.appid = appid;
        }

        public void setCountDownLatch(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            // 先移除原token,避免缓存更新的时间差,导致客户端获取到过时的token
            // 客户端在缓存为空时,会阻塞等待,待缓存更新后会自动唤醒,返回最新的token
            long start = System.currentTimeMillis();
//            log.info("【更新微信access_token】start.appid:{}", appid);
            tokenCacheMap.remove(appid);
            String token = getTokenFromWechat(appid);
            tokenCacheMap.put(appid, token);
            if (countDownLatch != null) {
                countDownLatch.countDown();
                countDownLatch = null;
            }
            log.info("【更新微信access_token】end.appid:{},token:{},use time:{}", appid, token, System.currentTimeMillis() - start);
        }
    }
}
