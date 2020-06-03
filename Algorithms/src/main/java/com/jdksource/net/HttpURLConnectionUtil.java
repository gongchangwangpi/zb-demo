package com.jdksource.net;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * @author zhangbo
 * @date 2020/6/3
 */
@Slf4j
public class HttpURLConnectionUtil {

    private static String ENCODING = "UTF-8";
    private static int connectionTimeout = 1000;
    private static int readTimeout = 3000;

    public static void post(String url, Object params) throws Exception {
        byte[] data = JSON.toJSONBytes(params);

        HttpURLConnection httpConn = (HttpURLConnection)(new URL(url)).openConnection();
        httpConn.setUseCaches(false);
        httpConn.setRequestMethod("POST");
        httpConn.setConnectTimeout(connectionTimeout);
        httpConn.setReadTimeout(readTimeout);
        httpConn.setRequestProperty("Content-Length", String.valueOf(data.length));
        httpConn.setRequestProperty("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        httpConn.setDoOutput(true);
        DataOutputStream outputStream = null;
        InputStream inputStream = null;

        try {
            httpConn.connect();
            outputStream = new DataOutputStream(httpConn.getOutputStream());
            outputStream.write(data);
            outputStream.flush();
            inputStream = httpConn.getInputStream();
            List<String> response = IOUtils.readLines(inputStream, ENCODING);
            log.info("response = {}", JSON.toJSONString(response));
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }

            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

}
