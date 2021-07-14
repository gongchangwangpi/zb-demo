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

import java.io.IOException;
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
public class EsDataSync {

    public static void main(String[] args) throws Exception {

        String[] appids = {"huiji38riwoxm7fp", "ck34idkwlrx89rpg", "mh3jei48ds0dpekr", "wxws0r8pezu7r0en", "mh23jdeo9eu84os0", "wy13isani89ejsk9", "hx2kdo0wis8l1erm"};
//        String[] appids = {"huiji38riwoxm7fp"};

//        String[] methods = {"sdk_info", "sdk_launch", "sdk_view","sdk_share", "sdk_buttom", "sdk_input", "sdk_item", "sdk_order", "sdk_coupon", "sdk_point"};
        String[] methods = {"sdk_request"};

        String oldEs = "vpc-cdp-plrh7u444fiq5dm4llwtrrpzi4.cn-northwest-1.es.amazonaws.com.cn";
        String newEs = "vpc-cdp-maidian-2ewhx6rxw66metk3fxtqfy42oy.cn-northwest-1.es.amazonaws.com.cn";

        String beginTime = "2020-06-30 00:00:00";
//        String beginTime = "2020-10-23 00:00:00";
        String endTime = "2020-10-29 00:00:00";

        FastDateFormat dateFormat = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
        Date begin = dateFormat.parse(beginTime);
        Date end = dateFormat.parse(endTime);

        RestHighLevelClient oldClient = initClient(oldEs, 443, "https");
        RestHighLevelClient newClient = initClient(newEs, 443, "https");

        for (String appid : appids) {
            for (String method : methods) {
                if (appid.equalsIgnoreCase("ck34idkwlrx89rpg") && method.equalsIgnoreCase("sdk_info")) {
                    continue;
                }
                String index = "trace_collect_" + appid + "_" + method;
                while (begin.compareTo(end) < 0) {
                    Date tmpEnd = DateUtils.addDays(begin, 1);
                    List<Map<String, Object>> search = search(oldClient, index, dateFormat.format(begin), dateFormat.format(tmpEnd));

                    if (!search.isEmpty()) {
                        save(newClient, search, index);
                    }
                    begin = tmpEnd;
                }
                begin = dateFormat.parse(beginTime);
            }
        }

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
                    log.error("save error", e.getMessage());
                }
                request = new BulkRequest();
                count = 0;
            }
        }
        try {
            client.bulk(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            //
            log.error("save error", e.getMessage());
        }
    }

    public static List<Map<String, Object>> search(RestHighLevelClient client, String index, String begin, String end) {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.from(0);
        sourceBuilder.size(10000);
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("timestamp");
        rangeQueryBuilder.gte(begin);
        rangeQueryBuilder.lte(end);
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        boolBuilder.must(rangeQueryBuilder);
        sourceBuilder.query(boolBuilder);
        // 排序
        sourceBuilder.sort("timestamp", SortOrder.ASC);
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.source(sourceBuilder);

        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            log.info("search es index = {}, begin = {}, end = {}, hit count {}", index, begin, end, searchResponse.getHits().getHits().length);
            return Stream.of(searchResponse.getHits().getHits()).map(SearchHit::getSourceAsMap).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("search es index = {}, begin = {}, end = {}, error: {}", index, begin, end, e.getMessage());
        }
        return Collections.emptyList();
    }


    public static RestHighLevelClient initClient(String hostname, int port, String schemaName) {
        HttpHost httpHost = new HttpHost(hostname, port, schemaName);
        return new RestHighLevelClient(RestClient.builder(httpHost));
    }
}
