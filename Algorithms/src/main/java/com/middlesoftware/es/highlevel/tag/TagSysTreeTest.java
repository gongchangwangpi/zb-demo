package com.middlesoftware.es.highlevel.tag;

import com.middlesoftware.es.highlevel.trace.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.List;

/**
 * 聚合tag的类型树
 *
 * @author zhangbo
 * @date 2020/8/12
 */
@Slf4j
public class TagSysTreeTest extends BaseTest {

    static String index = "hx_dw_new.dim_tags";

    public static void main(String[] args) throws Exception {

        new TagSysTreeTest().execute();

    }


    @Override
    public void process() throws IOException {

//        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//        sourceBuilder.from(0);
//        sourceBuilder.size(10);
//
//        SearchRequest searchRequest = new SearchRequest(index);
//        searchRequest.source(sourceBuilder);
//
//        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
//
//        System.out.println(searchResponse.getHits().getHits().length);
//
//        SearchHit[] hitsValue = searchResponse.getHits().getHits();
//
//        List<Map<String, Object>> list = Stream.of(hitsValue).map(SearchHit::getSourceAsMap).collect(Collectors.toList());
//
//        list.forEach(map -> {
//            System.out.println(map);
//        });

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.size(0);

        TermsAggregationBuilder sys1AggsBuilder = AggregationBuilders.terms("sys1").field("tag_sys1.keyword").size(100);
        TermsAggregationBuilder sys2AggsBuilder = AggregationBuilders.terms("sys2").field("tag_sys2.keyword").size(100);
        TermsAggregationBuilder sys3AggsBuilder = AggregationBuilders.terms("sys3").field("tag_sys3.keyword").size(100);

        sys2AggsBuilder.subAggregation(sys3AggsBuilder);
        sys1AggsBuilder.subAggregation(sys2AggsBuilder);

        searchSourceBuilder.aggregation(sys1AggsBuilder);

        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.source(searchSourceBuilder);

        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

            Aggregations aggregations = searchResponse.getAggregations();
            Terms sys1 = aggregations.get("sys1");
            List<? extends Terms.Bucket> sys1Buckets = sys1.getBuckets();

//            sys1Buckets.forEach(sys1Bucket -> {
//                System.out.println(sys1Bucket.getKey());
//                System.out.println("-------");
//                Terms sys2 = sys1Bucket.getAggregations().get("sys2");
//                sys2.getBuckets().forEach(sys2Bucket -> {
//                    System.out.println(sys2Bucket.getKey());
//                });
//                System.out.println("=======");
//                sys2.getBuckets().forEach(sys2Bucket -> {
//                    Terms sys3 = sys2Bucket.getAggregations().get("sys3");
//                    sys3.getBuckets().forEach(sys3Bucket -> {
//                        System.out.println(sys3Bucket.getKey());
//                    });
//                });
//                System.out.println("===============================");
//            });
            sys1Buckets.forEach(sys1Bucket -> {
                Terms sys2 = sys1Bucket.getAggregations().get("sys2");
                sys2.getBuckets().forEach(sys2Bucket -> {
                    Terms sys3 = sys2Bucket.getAggregations().get("sys3");
                    sys3.getBuckets().forEach(sys3Bucket -> {
                        System.out.println(sys1Bucket.getKey() + " - " + sys2Bucket.getKey() + " - " + sys3Bucket.getKey());
                    });
                });
            });


        } catch (IOException e) {
            //
            log.error("query from es error", e);
        }

    }
}
