package com.test.http;

import com.util.SimpleHttpClient;

import java.util.Map;

/**
 * @author zhangbo
 * @date 2020/3/16
 **/
public class HttpUtil {

    private static SimpleHttpClient simpleHttpClient;

    static {
        simpleHttpClient = new SimpleHttpClient();
        simpleHttpClient.init();
    }

    public static String get(String url) {
        return simpleHttpClient.get(url);
    }

    public static <T> T get(String url, Class<T> responseType) {
        return simpleHttpClient.get(url, responseType);
    }

    public static <T> T get(String url, Map<String, String> headers, Class<T> responseType) {
        return simpleHttpClient.getByHeaders(url, headers, responseType);
    }

}
