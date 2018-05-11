package com.zookeeper.keyvalue;

import com.books.bingfayishu.d4.SleepUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangbo
 */
@Slf4j
public class Updater {
    
    public static final String KEY = "keyTest";

    public static void main(String[] args) throws Exception {
        ActiveKeyValueStore keyValueStore = new ActiveKeyValueStore();
        keyValueStore.connect("172.18.8.22");
        
        for (int i = 0; i < 5; i++) {
            String value = String.valueOf(Math.random());
            keyValueStore.put(KEY, value);
            log.info("put value: {}", value);
            SleepUtils.second(2);
        }

    }
    
}
