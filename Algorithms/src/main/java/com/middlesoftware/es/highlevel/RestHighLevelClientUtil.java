package com.middlesoftware.es.highlevel;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * @author zhangbo
 * @date 2020/8/4
 */
public class RestHighLevelClientUtil {

    private static String hostname = "127.0.0.1";
    private static int port = 9200;

    public static RestHighLevelClient getClient() {
        HttpHost httpHost = new HttpHost(hostname, port);
        return new RestHighLevelClient(RestClient.builder(httpHost));
    }

    public static RestHighLevelClient getClient(String hostname, int port) {
        HttpHost httpHost = new HttpHost(hostname, port);
        return new RestHighLevelClient(RestClient.builder(httpHost));
    }

}
