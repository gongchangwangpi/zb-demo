package com.middlesoftware.es.highlevel.trace;

import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.stream.Stream;

/**
 * 单一渠道的UV，在代码中distinct
 *
 * @author zhangbo
 * @date 2020/8/6
 */
@Slf4j
public class UVTest extends BaseTest {

    public static void main(String[] args) throws Exception {

        new UVTest().execute();

    }

    @Override
    public void process() throws Exception {
        //
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.from(0);
        sourceBuilder.size(1000);
//        sourceBuilder.fetchSource(new String[]{"title"}, new String[]{});
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("source", "003");
//        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("uid", "test");
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("timestamp");
        rangeQueryBuilder.gte("2020-08-05 21:00:00");
        rangeQueryBuilder.lte("2020-08-06 10:30:00");
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        boolBuilder.must(matchQueryBuilder);
//        boolBuilder.must(termQueryBuilder);
        boolBuilder.must(rangeQueryBuilder);
        sourceBuilder.query(boolBuilder);
        // 排序
        sourceBuilder.sort("timestamp", SortOrder.DESC);
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.source(sourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        SearchHits hits = searchResponse.getHits();
        TotalHits totalHits = hits.getTotalHits();

        log.info("totalHits: {}", totalHits.value);

        String path = "D:\\es0.xlsx";
        FileOutputStream fos = new FileOutputStream(path);

        SearchHit[] hitsValue = hits.getHits();

//        Stream.of(hitsValue).forEach(hit -> {
//            System.out.println(hit.getSourceAsMap());
//        });

        long count = Stream.of(hitsValue).map(hit -> {
            return hit.getSourceAsMap().get("openId");
        }).distinct().count();

        System.out.println(count);

//        List<TraceEntity> traceEntityList = new ArrayList<>(hitsValue.length);
//        Stream.of(hitsValue).forEach(searchHit -> {
////            log.info(searchHit.getSourceAsString());
//            String sourceAsString = searchHit.getSourceAsString();
//            TraceEntity traceEntity = JSON.parseObject(sourceAsString, TraceEntity.class);
//            traceEntityList.add(traceEntity);
//
////            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
////            TreeMap<String, Object> treeMap = new TreeMap<>(sourceAsMap);
////            List<String> content = treeMap.values().stream()
////                    .map(JSON::toJSONString)
////                    .map(str -> {
////                        // The maximum length of cell contents (text) is 32,767 characters
////                        if (StringUtils.length(str) > 32700) {
////                            return StringUtils.substring(str, 0, 32700);
////                        }
////                        return str;
////                    }).collect(Collectors.toList());
////            Set<String> h = treeMap.keySet();
////            hSet.addAll(h);
////            head.add(new ArrayList<>(h));
////            contents.add(content);
//        });
//
////        ExcelWriter excelWriter = new ExcelWriter(fos, ExcelTypeEnum.XLSX);
////        Sheet sheet = new Sheet(1);
////        sheet.setHead(head.subList(0, 1));
////        excelWriter.write0(content, sheet);
////        excelWriter.finish();
//
//        Field[] fields = TraceEntity.class.getDeclaredFields();
//        List<String> head = Stream.of(fields).map(Field::getName).collect(Collectors.toList());
//
//        ExcelWriter excelWriter = new ExcelWriter(true, "ES");
//        excelWriter.writeHeadRow(head);
//        traceEntityList.forEach(traceEntity -> excelWriter.writeRow(traceEntity, false));
//        excelWriter.flush(fos);
//        excelWriter.close();
    }
}
