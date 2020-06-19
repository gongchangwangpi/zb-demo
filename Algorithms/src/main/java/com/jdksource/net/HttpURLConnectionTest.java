package com.jdksource.net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangbo
 * @date 2020/6/3
 */
public class HttpURLConnectionTest {

    public static void main(String[] args) throws Exception {
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("appid", "dmf_appid");
        map.put("uid", "http_test");

        list.add(map);
//        HttpURLConnectionUtil.post("https://tracecollect.mydataway.com/trace/collect?appid=dmf_appid", list);
        HttpURLConnectionUtil.post("http://127.0.0.1:16080/trace/collect?appid=dmf_appid", list);
    }

}
