package com.zb.fund.test;


import com.zb.commons.http.SimpleHttpClient;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangbo
 */
public class ListDataTest {

    public static void main(String[] args) throws Exception {

        SimpleHttpClient httpClient = new SimpleHttpClient();
        
        Class<SimpleHttpClient> httpClientClass = SimpleHttpClient.class;
        Method initMethod = httpClientClass.getDeclaredMethod("init");
        initMethod.setAccessible(true);
        initMethod.invoke(httpClient);

        String url = "http://127.0.0.1:10086/fund/trend/list/data";
        
        Map<String, String> params = new HashMap<>();
        params.put("fundName", "沪深300");
        params.put("combSort", "last_year_rate-ASC");

        String response = httpClient.get(url, params);

        System.out.println(response);

    }
    
}
