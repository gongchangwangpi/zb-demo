package com.middlesoftware.es;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zhangbo
 * @date 2020/10/28
 */
@Slf4j
public class EsTagDataSync {

    public static void main(String[] args) throws Exception {


//        String oldEs = "vpc-cdp-plrh7u444fiq5dm4llwtrrpzi4.cn-northwest-1.es.amazonaws.com.cn";
        String oldEs = "vpc-cdp-prod-rqrvjzezyowfe5fi7ckcd4rpdm.cn-northwest-1.es.amazonaws.com.cn";
        String newEs = "vpc-cdp-prod-rqrvjzezyowfe5fi7ckcd4rpdm.cn-northwest-1.es.amazonaws.com.cn";

        RestHighLevelClient oldClient = initClient(oldEs, 443, "https");
        RestHighLevelClient newClient = initClient(newEs, 443, "https");

        String sourceIndex = "scxxwwyyxgs_app.app_dim_tags";
        String targetIndex = "cgzbjtyxgs_staging_app.app_dim_tags";

        List<Map<String, Object>> search = search(oldClient, sourceIndex);
        save(newClient, search, targetIndex);

        System.out.println(" ==== end =====");
        newClient.close();
        oldClient.close();
    }

    private static void save(RestHighLevelClient client, List<Map<String, Object>> search, String index) {
        int count = 0;

        BulkRequest request = new BulkRequest();
        for (Map<String, Object> map : search) {
            request.add(new IndexRequest(index).opType("index").source(map));
            count++;
            if (count == 10000) {
                try {
                    client.bulk(request, RequestOptions.DEFAULT);
                } catch (Exception e) {
                    //
                    log.error("save error", e);
                }
                request = new BulkRequest();
                count = 0;
            }
        }
        try {
            client.bulk(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            //
            log.error("save error", e);
        }
    }

    public static List<Map<String, Object>> search(RestHighLevelClient client, String index) {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.from(0);
        sourceBuilder.size(10000);
        // 排序
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.source(sourceBuilder);

        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            log.info("search es index = {}, count {}", index, searchResponse.getHits().getHits().length);
            return Stream.of(searchResponse.getHits().getHits()).map(SearchHit::getSourceAsMap).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("search es index = {}", index, e);
        }
        return Collections.emptyList();
    }


    public static RestHighLevelClient initClient(String hostname, int port, String schemaName) {
        HttpHost httpHost = new HttpHost(hostname, port, schemaName);
        return new RestHighLevelClient(RestClient.builder(httpHost));
    }
}
