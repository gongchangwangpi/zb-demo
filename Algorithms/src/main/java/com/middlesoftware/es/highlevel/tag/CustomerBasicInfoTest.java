package com.middlesoftware.es.highlevel.tag;

import com.middlesoftware.es.highlevel.trace.BaseTest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zhangbo
 * @date 2020/8/12
 */
public class CustomerBasicInfoTest extends BaseTest {

    static String index = "hx_dw_new.fact_customer_basic_info";

    public static void main(String[] args) throws Exception {

        new CustomerBasicInfoTest().execute();

    }


    @Override
    public void process() throws IOException {

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.from(0);
        sourceBuilder.size(10);


        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.source(sourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        System.out.println(searchResponse.getHits().getHits().length);

        SearchHit[] hitsValue = searchResponse.getHits().getHits();

        List<Map<String, Object>> list = Stream.of(hitsValue).map(SearchHit::getSourceAsMap).collect(Collectors.toList());

        list.forEach(System.out::println);

    }
}
