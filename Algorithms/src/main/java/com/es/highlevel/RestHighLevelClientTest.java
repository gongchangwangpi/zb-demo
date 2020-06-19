package com.es.highlevel;

import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zhangbo
 * @date 2020/6/12
 */
@Slf4j
public class RestHighLevelClientTest {

    public static void main(String[] args) throws IOException {
        long s = System.currentTimeMillis();

        HttpHost httpHost = new HttpHost("127.0.0.1", 9200);

        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(RestClient.builder(httpHost));

//        String[] indices = {"test"};
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        searchSourceBuilder.from(0);
//        searchSourceBuilder.size(10);
//        SearchRequest searchRequest = new SearchRequest(indices, searchSourceBuilder);

        String index = "test";
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.from(0);
        sourceBuilder.size(1000);
//        sourceBuilder.fetchSource(new String[]{"title"}, new String[]{});
//        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("uid", "test");
//        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("uid", "test");
//        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("publishTime");
//        rangeQueryBuilder.gte("2018-01-26T08:00:00Z");
//        rangeQueryBuilder.lte("2018-01-26T20:00:00Z");
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
//        boolBuilder.must(matchQueryBuilder);
//        boolBuilder.must(termQueryBuilder);
//        boolBuilder.must(rangeQueryBuilder);
        sourceBuilder.query(boolBuilder);
        // 排序
        sourceBuilder.sort("timestamp", SortOrder.DESC);
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.source(sourceBuilder);

        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        SearchHits hits = searchResponse.getHits();
        TotalHits totalHits = hits.getTotalHits();

        log.info("totalHits: {}", totalHits.value);

        String path = "D:\\es0.xlsx";
        FileOutputStream fos = new FileOutputStream(path);

        SearchHit[] hitsValue = hits.getHits();

        List<TraceEntity> traceEntityList = new ArrayList<>(hitsValue.length);
        Stream.of(hitsValue).forEach(searchHit -> {
//            log.info(searchHit.getSourceAsString());
            String sourceAsString = searchHit.getSourceAsString();
            TraceEntity traceEntity = JSON.parseObject(sourceAsString, TraceEntity.class);
            traceEntityList.add(traceEntity);

//            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
//            TreeMap<String, Object> treeMap = new TreeMap<>(sourceAsMap);
//            List<String> content = treeMap.values().stream()
//                    .map(JSON::toJSONString)
//                    .map(str -> {
//                        // The maximum length of cell contents (text) is 32,767 characters
//                        if (StringUtils.length(str) > 32700) {
//                            return StringUtils.substring(str, 0, 32700);
//                        }
//                        return str;
//                    }).collect(Collectors.toList());
//            Set<String> h = treeMap.keySet();
//            hSet.addAll(h);
//            head.add(new ArrayList<>(h));
//            contents.add(content);
        });

//        ExcelWriter excelWriter = new ExcelWriter(fos, ExcelTypeEnum.XLSX);
//        Sheet sheet = new Sheet(1);
//        sheet.setHead(head.subList(0, 1));
//        excelWriter.write0(content, sheet);
//        excelWriter.finish();

        Field[] fields = TraceEntity.class.getDeclaredFields();
        List<String> head = Stream.of(fields).map(Field::getName).collect(Collectors.toList());

        ExcelWriter excelWriter = new ExcelWriter(true, "ES");
        excelWriter.writeHeadRow(head);
        traceEntityList.forEach(traceEntity -> excelWriter.writeRow(traceEntity, false));
        excelWriter.flush(fos);
        excelWriter.close();

        long e = System.currentTimeMillis();
        log.info("end >>>> {} ms", e - s);

        restHighLevelClient.close();

    }

}
