package com.middlesoftware.es.highlevel.trace;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.List;

/**
 * 统计各个渠道source的PV及UV及UV明细
 *
 * @author zhangbo
 * @date 2020/8/6
 */
@Slf4j
public class PvAndUvAndDetailTest extends BaseTest {

    public static void main(String[] args) throws Exception {

        new PvAndUvAndDetailTest().execute();

    }

    @Override
    public void process() throws IOException {

        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 只返回聚合数据，不返回对应的结果
        searchSourceBuilder.size(0);
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("timestamp");
        // 21-22 22-10
        rangeQueryBuilder.gte("2020-08-05 21:00:00");
        rangeQueryBuilder.lte("2020-08-06 10:30:00");

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(rangeQueryBuilder);

        searchSourceBuilder.query(rangeQueryBuilder);

        //text类型不能用于索引或排序，必须转成keyword类型
        TermsAggregationBuilder pvTermsAggregationBuilder = AggregationBuilders.terms("pv").field("source.keyword");
        pvTermsAggregationBuilder.subAggregation(AggregationBuilders.terms("uv").field("openId"));

        searchSourceBuilder.aggregation(pvTermsAggregationBuilder);

        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        System.out.println("total : " + searchResponse.getHits().getTotalHits().value);

        Aggregations aggregations = searchResponse.getAggregations();
        Terms pv = aggregations.get("pv");

        List<? extends Terms.Bucket> buckets = pv.getBuckets();
        buckets.forEach(bucket -> {

            Terms uv = bucket.getAggregations().get("uv");
            List<? extends Terms.Bucket> uvBuckets = uv.getBuckets();
            System.out.println(bucket.getKey() + " : count = " + bucket.getDocCount() + ", uv = " + uvBuckets.size());

        });


    }
}
