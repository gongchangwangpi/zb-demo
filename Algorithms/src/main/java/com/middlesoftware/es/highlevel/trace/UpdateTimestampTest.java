package com.middlesoftware.es.highlevel.trace;

import org.apache.commons.lang3.time.FastDateFormat;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

/**
 * @author zhangbo
 * @date 2020/10/12
 */
public class UpdateTimestampTest extends BaseTest {

    static String index = "trace_collect_huiji38riwoxm7fp_sdk_request";

    public static void main(String[] args) throws Exception {
//        String t = "2020-10-07T12:49:09.716Z";
//        t = t.replace("T", " ").replace("Z", "");

//        Date date = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss.SSS").parse(t);
//        System.out.println(date);

        new UpdateTimestampTest().execute();
    }

    @Override
    public void process() throws Exception {
        //
        String startTime = "2020-10-13 00:49:09";
        String endTime = "2020-10-13 20:49:11";

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.from(0);
        sourceBuilder.size(1000);
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("timestamp");
        rangeQueryBuilder.gte(startTime);
        rangeQueryBuilder.lte(endTime);
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        boolBuilder.must(rangeQueryBuilder);
        sourceBuilder.query(boolBuilder);

        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.source(sourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        SearchHit[] hits = searchResponse.getHits().getHits();
        System.out.println("total " + hits.length);

        // update
        BulkRequest bulkRequest = new BulkRequest();
        for (SearchHit hit : hits) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            String time = (String) sourceAsMap.get("@timestamp");
            time = time.replace("T", " ").substring(0, 19);
            sourceAsMap.put("timestamp", time);
            UpdateRequest updateRequest = new UpdateRequest(index, hit.getId());
            updateRequest.doc(sourceAsMap);
            bulkRequest.add(updateRequest);

            System.out.println(hit.getId());
        }

        BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);


    }
}
