package com.util;


import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;

import javax.net.ssl.SSLContext;


/**
 * SIMPLE HTTPS CLIENT 工具类
 *
 * @author zhangbo
 */
@Getter
@Setter
@Slf4j
public class SimpleHttpsClient extends SimpleHttpClient {

    /**
     * 初始化CloseableHttpClient
     */
    @Override
    public void init() {
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(socketTimeout)
                .setConnectTimeout(connectTimeout)
                .setConnectionRequestTimeout(readTimeout)
                .setStaleConnectionCheckEnabled(true)
                .build();
        SSLContext sslContext = null;
        try {
            sslContext = SSLContextBuilder.create().useProtocol(SSLConnectionSocketFactory.SSL)
                    .loadTrustMaterial((x, y) -> true).build();
        } catch (Exception e) {
            //
            log.warn("加载HTTPS Client失败", e);
        }
        this.closeableHttpClient = HttpClientBuilder.create()
                .setMaxConnTotal(maxConnTotal)
                .setMaxConnPerRoute(maxConnPerRoute)
                .setSSLContext(sslContext)
                .setDefaultRequestConfig(requestConfig).build();
    }

}
