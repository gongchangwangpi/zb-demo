package com.middlesoftware.es.highlevel.trace;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

/**
 * @author zhangbo
 * @date 2020/8/4
 */
public class BaseTest {

    static String hostname = "vpc-cdp-plrh7u444fiq5dm4llwtrrpzi4.cn-northwest-1.es.amazonaws.com.cn";
    static int port = 443;
    static String schemaName = "https";

    static String index = "trace_collect_mh3jei48ds0dpekr_sdk_view";

    public static RestHighLevelClient client;

    public void execute() throws Exception {

        try {
            initClient();
            process();
        } finally {
            closeClient();
        }

    }

    public void process() throws Exception {

        SearchRequest searchRequest = new SearchRequest(index);

//        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("openId", "o5K9guKGhB5IDdroFReLb89jvZdw");

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//        sourceBuilder.from(0);
//        sourceBuilder.size(1000);
//        sourceBuilder.query(matchQueryBuilder);
        sourceBuilder.aggregation(AggregationBuilders.terms("count").field("openId"));

        searchRequest.source(sourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

//        SearchHits hits = searchResponse.getHits();
//        SearchHit[] searchHits = hits.getHits();
//        for (SearchHit searchHit : searchHits) {
//            System.out.println(searchHit.getSourceAsString());
//        }

        System.out.println("==============================");
        Aggregations aggregations = searchResponse.getAggregations();
        aggregations.forEach(aggregation -> {
            System.out.println(aggregation.getName());
            System.out.println(aggregation.getType());
            System.out.println(aggregation.getMetaData());
        });

    }

    public void initClient() {
        HttpHost httpHost = new HttpHost(hostname, port, schemaName);
        client = new RestHighLevelClient(RestClient.builder(httpHost));
    }

    public void closeClient() {
        if (client != null) {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
