package com.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.IoUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okio.Buffer;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @author zhangbo
 * @date 2020/12/9
 */
@Slf4j
public class OkHttpClientUtil {


    private static final long CONNECTION_TIMEOUT = 1000;
    private static final long READ_TIMEOUT = 60000;
    private OkHttpClient httpClient;

    private static final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }};

    public void init () {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS);
        builder.readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS);
//        builder.connectionPool()

        SSLContext trustAllSslContext = null;
        try {
            trustAllSslContext = SSLContext.getInstance("SSL");
            trustAllSslContext.init((KeyManager[]) null, trustAllCerts, new SecureRandom());
        } catch (KeyManagementException | NoSuchAlgorithmException ignored) {
        }

        SSLSocketFactory trustAllSslSocketFactory = Objects.requireNonNull(trustAllSslContext).getSocketFactory();
        builder.sslSocketFactory(trustAllSslSocketFactory, (X509TrustManager) trustAllCerts[0]).hostnameVerifier((s, sslSession) -> true);
        httpClient = builder.build();
    }

    public String get(String url, Map<String, String> params, Map<String, String> headers) {
        url = appendQueryString(url, params);
        Request request = new Request.Builder().url(url).get().headers(getHeaders(headers)).build();
        return call(request);
    }

    public String postForm(String url, Map<String, String> params, Map<String, String> headers) {
        FormBody formBody = getFormBody(params);
        Request request = new Request.Builder().url(url).post(formBody).headers(getHeaders(headers)).build();
        return call(request);
    }

    public String reqJson(String url, String method, Object jsonBody, Map<String, String> headers) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), JSON.toJSONBytes(jsonBody));
        Request request = new Request.Builder().url(url).method(method, requestBody).headers(getHeaders(headers)).build();
        return call(request);
    }

    public String postJson(String url, Object jsonBody, Map<String, String> headers) {
        return reqJson(url, "POST", jsonBody, headers);
    }

    public String postJson(String url, Object jsonBody) {
        return postJson(url, jsonBody, (Map<String, String>) null);
    }

    public <T> T postJson(String url, Object jsonBody, Function<String, T> responseParser) {
        return responseParser.apply(postJson(url, jsonBody));
    }

    private String call(Request request) {
        log.info("ok http request method: {}, url: {}", request.method(), request.url().toString());
        log.info("ok http request headers: {}", request.headers().toString());
        if (request.body() != null) {
            Buffer buffer = new Buffer();
            try {
                request.body().writeTo(buffer);
            } catch (IOException e) {
                //
            }
            log.info("ok http request body: {}", buffer.readUtf8());
        }

        Call call = httpClient.newCall(request);
        Response response = null;
        try {
            response = call.execute();
            if (response != null && response.body() != null) {
                String resp = response.body().string();
                log.info("ok http response code: {}, body: {}", response.code(), resp);
                return resp;
            }
        } catch (IOException e) {
            log.error("ok http client request error", e);
        } finally {
            IoUtil.close(response);
        }
        return null;
    }

    private FormBody getFormBody(Map<String, String> params) {
        FormBody.Builder builder = new FormBody.Builder();
        if (CollectionUtil.isNotEmpty(params)) {
            params.forEach(builder::add);
        }
        return builder.build();
    }

    private Headers getHeaders(Map<String, String> headers) {
        Headers.Builder builder = new Headers.Builder();
        if (CollectionUtil.isNotEmpty(headers)) {
            headers.forEach(builder::add);
        }
        return builder.build();
    }

    private String appendQueryString(String url, Map<String, String> params) {
        if (CollectionUtil.isNotEmpty(params)) {
            url = url + "?" + Joiner.on("&").withKeyValueSeparator("=").join(params);
        }
        return url;
    }

}
