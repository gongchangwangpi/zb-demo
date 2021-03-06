package com.util.http;


import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Joiner;
import com.util.HttpRemoteException;
import com.util.JacksonJsonMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * SIMPLE HTTP CLIENT 工具类
 *
 * @author zhangbo
 *
 */
@Getter
@Setter
@Slf4j
public class SimpleHttpClient {

    protected static final String DEFAULT_CHARSET = "UTF-8";

    protected static int socketTimeout = 30000;
    protected static int connectTimeout = 30000;
    protected static int readTimeout = 30000;

    protected static int maxConnTotal = 30;
    protected static int maxConnPerRoute = 30;

    protected static LogLevel logLevel = LogLevel.INFO;

    protected static CloseableHttpClient closeableHttpClient;

    /**
     * 自定义日志级别枚举
     */
    public enum LogLevel {
        NONE {
            @Override
            void log(String header, Object msg) {
                // 不打印日志
            }
        },
        DEBUG {
            @Override
            void log(String header, Object msg) {
                log.debug("【HTTP】{} = {}", header, msg);
            }
        },
        INFO {
            @Override
            void log(String header, Object msg) {
                log.info("【HTTP】{} = {}", header, msg);
            }
        },
        WARN {
            @Override
            void log(String header, Object msg) {
                log.warn("【HTTP】{} = {}", header, msg);
            }
        },
        ERROR {
            @Override
            void log(String header, Object msg) {
                log.error("【HTTP】{} = {}", header, msg);
            }
        };

        abstract void log(String header, Object msg);
    }

    /**
     * 初始化CloseableHttpClient
     */
    static {
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(socketTimeout)
                .setConnectTimeout(connectTimeout)
                .setConnectionRequestTimeout(readTimeout)
                .setStaleConnectionCheckEnabled(true)
                .build();

        closeableHttpClient = HttpClientBuilder.create().setMaxConnTotal(maxConnTotal).setMaxConnPerRoute(maxConnPerRoute)
                .setDefaultRequestConfig(requestConfig).build();
    }

    /**
     * POST请求
     *
     * @param url          URL地址
     * @param requestParam 请求参数,通常是基于JSON格式的字符串
     * @param charset      编码
     * @return response
     * @throws HttpRemoteException
     */
    public static String postByJsonString(String url, String requestParam, String charset) {
        return postByJsonString(url, requestParam, null, charset);
    }

    /**
     * POST请求
     *
     * @param url          URL地址
     * @param requestParam 请求参数,通常是基于JSON格式的字符串
     * @param header        请求头
     * @param charset      编码
     * @return response
     * @throws HttpRemoteException
     */
    public static String postByJsonString(String url, String requestParam, Map<String, String> header, String charset) {

        customLog("url", url);

        CloseableHttpClient httpClient = closeableHttpClient;
        HttpPost httpPost = new HttpPost(url);
        String body = null;
        httpPost.addHeader("Content-Type", "application/json");
        httpPost.addHeader("Accept", "application/json");

        if (!CollectionUtils.isEmpty(header)) {
            header.forEach(httpPost::addHeader);
            customLog("header", header);
        }

        try {
            if (!StringUtils.isEmpty(requestParam)) {
                httpPost.setEntity(new StringEntity(requestParam, charset));

                customLog("params", requestParam);
            }

            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity entity = httpResponse.getEntity();

            body = EntityUtils.toString(entity);

            customLog("response", body);

            EntityUtils.consumeQuietly(entity);
        } catch (IOException e) {
            httpPost.abort();
            customLog("error", e);
            throw new HttpRemoteException(e);
        }
        return body;
    }

    public static String upload(String url, File file, Map<String, String> paramMap) throws UnsupportedEncodingException {
        customLog("url", url);

        CloseableHttpClient httpClient = closeableHttpClient;
        HttpPost httpPost = new HttpPost(url);
//        httpPost.addHeader("Content-Type", "multipart/form-data");
        String body = null;

        try {
            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create().setCharset(StandardCharsets.UTF_8).addPart("file", new FileBody(file, ContentType.IMAGE_PNG))/*.addPart("filename", new StringBody(file.getName(), Charset.forName("ISO8859-1")))*/;

            if (paramMap != null && !paramMap.isEmpty()) {
                customLog("params", paramMap);
                paramMap.forEach(multipartEntityBuilder::addTextBody);
            }

            httpPost.setEntity(multipartEntityBuilder.build());

            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity entity = httpResponse.getEntity();

            body = EntityUtils.toString(entity);

            customLog("response", body);

            EntityUtils.consumeQuietly(entity);
        } catch (IOException e) {
            httpPost.abort();
            customLog("error", e);
            throw new HttpRemoteException(e);
        }
        return body;
    }

    /**
     * POST请求
     *
     * @param url      URL地址
     * @param paramMap 请求参数映射
     * @param charset  编码
     * @return response
     * @throws HttpRemoteException
     */
    public static String postByKeyValuePair(String url, Map<String, String> paramMap, String charset) {

        customLog("url", url);

        CloseableHttpClient httpClient = closeableHttpClient;
        HttpPost httpPost = new HttpPost(url);
        String body = null;

        try {
            if (paramMap != null && !paramMap.isEmpty()) {

                customLog("params", paramMap);

                List<NameValuePair> params = parseFromParamMap(paramMap);
                if (!params.isEmpty()) {
                    httpPost.setEntity(new UrlEncodedFormEntity(params, charset));
                }
            }

            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity entity = httpResponse.getEntity();

            body = EntityUtils.toString(entity);

            customLog("response", body);

            EntityUtils.consumeQuietly(entity);
        } catch (IOException e) {
            httpPost.abort();
            customLog("error", e);
            throw new HttpRemoteException(e);
        }
        return body;
    }

    public static String post(String url, byte[] params, String charset) {

        customLog("url", url);

        CloseableHttpClient httpClient = closeableHttpClient;
        HttpPost httpPost = new HttpPost(url);
        String response = null;

        try {
            if (params != null && params.length > 0) {
//                customLog("params", params);
                HttpEntity httpEntity = EntityBuilder.create().setBinary(params).setContentEncoding(charset).build();
                httpPost.setEntity(httpEntity);
            }

            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpResponseEntity = httpResponse.getEntity();

            response = EntityUtils.toString(httpResponseEntity, charset);

            customLog("response", response);

            EntityUtils.consumeQuietly(httpResponseEntity);
        } catch (IOException e) {
            httpPost.abort();
            customLog("error", e);
            throw new HttpRemoteException(e);
        }
        return response;
    }

    public static String post(String url, byte[] params) {
        return post(url, params, DEFAULT_CHARSET);
    }

    public static <T> T post(String url, byte[] params, Class<T> resultType) {
        String response = post(url, params);
        return parseResult(response, resultType);
    }

    public static <T> T post(String url, byte[] params, TypeReference<T> resultType) {
        String response = post(url, params);
        return parseResult(response, resultType);
    }

    public static String post(String url, String param) {
        return postByJsonString(url, param, DEFAULT_CHARSET);
    }

    public static String post(String url, Map<String, String> paramMap) {
        return postByKeyValuePair(url, paramMap, DEFAULT_CHARSET);
    }

    public static String post(String url) {
        return postByKeyValuePair(url, null, DEFAULT_CHARSET);
    }

    public static <T> T post(String url, TypeReference<T> typeReference) {
        return post(url, (Map<String, String>) null, typeReference);
    }

    public static <T> T post(String url, Class<T> type) {
        return post(url, (Map<String, String>) null, type);
    }

    public static <T> T post(String url, Map<String, String> paramMap, Class<T> type) {
        return postByKeyValuePair(url, paramMap, DEFAULT_CHARSET, type);
    }

    public static <T> T post(String url, Map<String, String> paramMap, TypeReference<T> typeReference) {
        return postByKeyValuePair(url, paramMap, DEFAULT_CHARSET, typeReference);
    }

    public static <T> T postByKeyValuePair(String url, Map<String, String> paramMap, String charset, Class<T> type) {
        String result = postByKeyValuePair(url, paramMap, charset);
        return parseResult(result, type);
    }

    public static <T> T postByKeyValuePair(String url, Map<String, String> paramMap, String charset, TypeReference<T> typeReference) {
        String result = postByKeyValuePair(url, paramMap, charset);
        return parseResult(result, typeReference);
    }

    // ----- GET ----- //

    public static InputStream getImage(String url, Map<String, String> headers) {
        customLog("url", url);

        CloseableHttpClient httpClient = closeableHttpClient;
        HttpGet httpGet = null;

        try {
            //创建HTTP GET请求
            httpGet = new HttpGet(url);

            //添加请求头,非必须
            if (headers != null && !headers.isEmpty()) {

                customLog("headers", headers);

                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpGet.addHeader(entry.getKey(), entry.getValue());
                }
            }

            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);

            //获得响应体
            HttpEntity entity = httpResponse.getEntity();
            return entity.getContent();

        } catch (IOException e) {
            httpGet.abort();
            customLog("error", e);
            throw new HttpRemoteException(e);
        }
    }

    public static Map<String, String> getResponseHeader(String url, Map<String, String> headers) {
        customLog("url", url);

        CloseableHttpClient httpClient = closeableHttpClient;
        HttpGet httpGet = null;

        try {
            //创建HTTP GET请求
            httpGet = new HttpGet(url);

            //添加请求头,非必须
            if (headers != null && !headers.isEmpty()) {

                customLog("headers", headers);

                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpGet.addHeader(entry.getKey(), entry.getValue());
                }
            }

            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);

            //获得响应体
            HttpEntity entity = httpResponse.getEntity();

            String body = EntityUtils.toString(entity);

            customLog("response", body);

            EntityUtils.consumeQuietly(entity);

            // 获取响应header
            Map<String, String> respHeaders = new HashMap<>();
            for (Header header : httpResponse.getAllHeaders()) {
                respHeaders.put(header.getName(), header.getValue());
            }
            customLog("respHeaders", respHeaders);

            return respHeaders;

        } catch (IOException e) {
            httpGet.abort();
            customLog("error", e);
            throw new HttpRemoteException(e);
        }
    }

    /**
     *
     * @param url
     * @param headers
     * @return
     * @throws HttpRemoteException
     */
    public static String getByHeaders(String url, Map<String, String> headers) {

        customLog("url", url);

        CloseableHttpClient httpClient = closeableHttpClient;
        HttpGet httpGet = null;
        String body = null;

        try {
            //创建HTTP GET请求
            httpGet = new HttpGet(url);

            //添加请求头,非必须
            if (headers != null && !headers.isEmpty()) {

                customLog("headers", headers);

                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpGet.addHeader(entry.getKey(), entry.getValue());
                }
            }

            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            Header[] allHeaders = httpResponse.getAllHeaders();
            //获得响应体
            HttpEntity entity = httpResponse.getEntity();

            body = EntityUtils.toString(entity);

            customLog("response", body);

            EntityUtils.consumeQuietly(entity);
        } catch (IOException e) {
            httpGet.abort();
            customLog("error", e);
            throw new HttpRemoteException(e);
        }
        return body;
    }

    /**
     *
     * @param url
     * @param headers
     * @return
     * @throws HttpRemoteException
     */
    public static HResp getHRespByHeaders(String url, Map<String, String> headers) {

        customLog("url", url);

        CloseableHttpClient httpClient = closeableHttpClient;
        HttpGet httpGet = null;
        HResp hResp = null;

        try {
            //创建HTTP GET请求
            httpGet = new HttpGet(url);

            //添加请求头,非必须
            if (headers != null && !headers.isEmpty()) {

                customLog("headers", headers);

                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpGet.addHeader(entry.getKey(), entry.getValue());
                }
            }

            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            Header[] allHeaders = httpResponse.getAllHeaders();
            //获得响应体
            HttpEntity entity = httpResponse.getEntity();

            String body = EntityUtils.toString(entity);

            hResp = new HResp(body, allHeaders);

            customLog("response", body);

            EntityUtils.consumeQuietly(entity);
        } catch (IOException e) {
            httpGet.abort();
            customLog("error", e);
            throw new HttpRemoteException(e);
        }
        return hResp;
    }

    public static <T> T getByHeaders(String url, Map<String, String> headers, Class<T> resultType) {
        String response = getByHeaders(url, headers);
        return parseResult(response, resultType);
    }

    public static <T> T getByHeaders(String url, Map<String, String> headers, TypeReference<T> resultType) {
        String response = getByHeaders(url, headers);
        return parseResult(response, resultType);
    }

    public static String get(String url) {
        return getByHeaders(url, null);
    }

    public static String get(String url, Map<String, String> params) {
        url = appendQueryString(url, params);
        return get(url);
    }

    public static <T> T get(String url, Class<T> type) {
        String result = get(url);
        return parseResult(result, type);
    }

    public static <T> T get(String url, Map<String, String> params, Class<T> type) {
        url = appendQueryString(url, params);
        return get(url, type);
    }

    public static <T> T get(String url, TypeReference<T> typeReference) {
        String result = get(url);
        return parseResult(result, typeReference);
    }

    public static <T> T get(String url, Map<String, String> params, TypeReference<T> typeReference) {
        url = appendQueryString(url, params);
        return get(url, typeReference);
    }

    private static <T> T parseResult(String result, TypeReference<T> typeReference) {
        if (StringUtils.isEmpty(result)) {
            return null;
        }
        return JacksonJsonMapper.INSTANCE.fromJson(result, typeReference);
    }

    private static <T> T parseResult(String result, Class<T> type) {
        if (StringUtils.isEmpty(result)) {
            return null;
        }
        return JacksonJsonMapper.INSTANCE.fromJson(result, type);
    }

    private static String appendQueryString(String url, Map<String, String> params) {
        if (params != null && !params.isEmpty()) {
            String queryString = Joiner.on("&").withKeyValueSeparator("=").join(params);
            url = url + "?" + queryString;
        }
        return url;
    }

    private static List<NameValuePair> parseFromParamMap(Map<String, String> paramMap) {
        List<NameValuePair> nameValuePairs = new ArrayList<>(paramMap.size());
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {

            String paramName = entry.getKey();
            String paramValue = entry.getValue();

            BasicNameValuePair basicNameValuePair = new BasicNameValuePair(paramName, paramValue);
            nameValuePairs.add(basicNameValuePair);
        }
        return nameValuePairs;
    }


    /**
     * 关闭CloseableHttpClient Instance
     */
    private static void destroy() {
        try {
            closeableHttpClient.close();
        } catch (IOException e) {
            customLog("destroy httpClient error", e);
        }
    }

    private static void customLog(String header, Object msg) {
        logLevel.log(header, msg);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HResp {
        private String body;
        private Header[] headers;
    }
}
