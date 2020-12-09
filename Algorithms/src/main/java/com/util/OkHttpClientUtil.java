package com.util;

import okhttp3.OkHttpClient;

import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangbo
 * @date 2020/12/9
 */
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

}
