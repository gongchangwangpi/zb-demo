package com.test.http;

import com.util.SimpleHttpClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * apache http client file upload test
 *
 * @author zhangbo
 * @date 2020/9/7
 */
public class SimpleHttpClientUploadTest {

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        SimpleHttpClient simpleHttpClient = new SimpleHttpClient();
        simpleHttpClient.init();

        String url = "http://127.0.0.1:8081/servlet/upload";

        Map<String, String> params = new HashMap<>();
        params.put("name", "张三");
        params.put("age", "18");

        File file = new File("D:\\images\\张玻.png");

        String response = simpleHttpClient.upload(url, file, params);

        System.out.println(response);

    }

}
