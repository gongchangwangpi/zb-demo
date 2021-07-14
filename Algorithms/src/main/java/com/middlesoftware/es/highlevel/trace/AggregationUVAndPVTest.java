package com.middlesoftware.es.highlevel.trace;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.metrics.Cardinality;
import org.elasticsearch.search.aggregations.metrics.CardinalityAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

/**
 * 单一渠道的UV & PV
 *
 * @author zhangbo
 * @date 2020/8/6
 */
@Slf4j
public class AggregationUVAndPVTest extends BaseTest {

    public static void main(String[] args) throws Exception {

        new AggregationUVAndPVTest().execute();

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
        // 查询指定的字段值
        TermQueryBuilder termQuery = QueryBuilders.termQuery("source", "002");

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(rangeQueryBuilder);
        boolQueryBuilder.must(termQuery);

        searchSourceBuilder.query(boolQueryBuilder);

        //text类型不能用于索引或排序，必须转成keyword类型
        CardinalityAggregationBuilder cardinalityAggregationBuilder = AggregationBuilders.cardinality("openIdCount").field("openId.keyword");
        searchSourceBuilder.aggregation(cardinalityAggregationBuilder);

        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        System.out.println("total : " + searchResponse.getHits().getTotalHits().value);

        Aggregations aggregations = searchResponse.getAggregations();
        Cardinality openIdCount = aggregations.get("openIdCount");
        System.out.println(openIdCount.getValue());

    }
}
