package com.middlesoftware.es.highlevel;

import cn.hutool.core.io.IoUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;

import java.io.IOException;

/**
 * @author zhangbo
 * @date 2020/10/10
 */
@Slf4j
public class HighEsClientTest {

    public static void main(String[] args) throws IOException {


        HttpHost httpHost = new HttpHost("vpc-cdp-tag-test-n5y7w6cvh3buqrxawdvnck2zne.cn-northwest-1.es.amazonaws.com.cn", 443, "https");

        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("admin", "Wxws2020!"));
        RestClientBuilder restClientBuilder = RestClient.builder(httpHost)
                .setHttpClientConfigCallback(httpAsyncClientBuilder -> httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider));

        RestHighLevelClient restHighLevelClient = null;
        try {
            restHighLevelClient = new RestHighLevelClient(restClientBuilder);
            CreateIndexRequest createIndexRequest = new CreateIndexRequest("cdp_test");
            CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
            log.info("create index response: {}", createIndexResponse.isAcknowledged());
        } finally {
            IoUtil.close(restHighLevelClient);
        }
    }

}
