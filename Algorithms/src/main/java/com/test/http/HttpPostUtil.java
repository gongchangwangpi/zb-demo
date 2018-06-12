package com.test.http;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.util.SimpleHttpClient;

import lombok.extern.slf4j.Slf4j;

/**
 * @author books
 */
@Slf4j
public class HttpPostUtil {
    
    public static void postJson(String url, Map<String, String> params) {
        String jsonParam = JSON.toJSONString(params);
        postJson(url, jsonParam);
    }
    
    public static void postJson(String url, String jsonString) {
        
        SimpleHttpClient simpleHttpClient = new SimpleHttpClient();
        simpleHttpClient.init();
        log.info("request url : {}", url);

        log.info("request param : {}", jsonString);

        String res = simpleHttpClient.post(url, jsonString);

        log.info("response result : {}", res);
    }
    
}
