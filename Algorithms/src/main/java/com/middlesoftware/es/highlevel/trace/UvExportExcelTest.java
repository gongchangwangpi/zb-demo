package com.middlesoftware.es.highlevel.trace;

import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.util.CollectionUtils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

/**
 * 所有的view事件，导出Excel
 *
 * @author zhangbo
 * @date 2020/8/6
 */
@Slf4j
public class UvExportExcelTest extends BaseTest {

    static String index = "trace_collect_huiji0irjfkdoewk_sdk_launch";
    // 麦合积分商城
//    static String index = "trace_collect_mh3jei48ds0dpekr_sdk_view";
    // 麦合电商
//    static String index = "trace_collect_mh23jdeo9eu84os0_sdk_view";

    static boolean filterSource = true;

    static String startTime = "2020-09-13 00:00:00";
    static String endTime = "2020-09-22 12:00:00";
    static String queryColumn = "@timestamp";

    static String filePath = "D:\\test\\麦合PV&UV\\";
    static String fileName = "麦合电商";

    public static void main(String[] args) throws Exception {

        new UvExportExcelTest().execute();

    }

    @Override
    public void process() throws Exception {
        // 查询原始数据
        SearchHits hits = search();
        TotalHits totalHits = hits.getTotalHits();
        SearchHit[] hitsValue = hits.getHits();

        log.info("totalHits: {}", totalHits.value);

        // 过滤source渠道
        List<Map<String, Object>> list = filterSource(hitsValue);

        replaceUuid(list);

        log.info("after filter source, total count: {}", list.size());
        if (CollectionUtils.isEmpty(list)) {
            log.warn("所选渠道没有数据");
            return;
        }

        // 移除不用的列
        List<Map<String, Object>> sourcePvList = new ArrayList<>(list.size());
        List<Map<String, Object>> originPvList = new ArrayList<>(list.size());
        filterNoUseColumns(list, sourcePvList, originPvList);

        // 统计数据
        Map<Object, List<Map<String, Object>>> sourceMap = calcStats(startTime, endTime, sourcePvList, originPvList);

        // ===== 写Excel =====
        writeExcel(startTime, endTime, originPvList, sourceMap);
    }

    private void replaceUuid(List<Map<String, Object>> list) throws IOException {
        List<Object> uuidList = list.stream().map(m -> {
            return m.get("openId");
        }).collect(Collectors.toList());

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.from(0);
        sourceBuilder.size(10000);

//        TermsQueryBuilder termsQuery = QueryBuilders.termsQuery("uuid", uuidList);
//        sourceBuilder.query(termsQuery);
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(queryColumn);
        rangeQueryBuilder.format("yyyy-MM-dd HH:mm:ss");
        rangeQueryBuilder.gte("2020-09-12 00:00:00");
        rangeQueryBuilder.lte("2020-09-23 00:00:00");

        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        boolBuilder.must(rangeQueryBuilder);

        sourceBuilder.query(boolBuilder);
        // 排序
        sourceBuilder.sort(queryColumn, SortOrder.ASC);

        SearchRequest searchRequest = new SearchRequest("trace_collect_huiji0irjfkdoewk_sdk_item");
        searchRequest.source(sourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        log.info("search uuid hits: {}", searchHits.length);

        Map<Object, Object> uuidOpenidMap = new HashMap<>();
        List<Map<String, Object>> uuidOpenidList = Stream.of(searchHits).map(searchHit -> {
            return searchHit.getSourceAsMap();
        }).collect(Collectors.toList());
        uuidOpenidList.forEach(map -> {
            uuidOpenidMap.put(map.get("token"), map.get("openId"));
        });

        list.forEach(map -> {
            map.put("openId", uuidOpenidMap.get(map.get("openId")));
        });

    }

    private SearchHits search() throws IOException {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.from(0);
        sourceBuilder.size(10000);
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(queryColumn);
        // 21-22 22-10 21:00-17:30
        // 043 045使用 trace_collect_huiji0irjfkdoewk_sdk_launch 至0914
        rangeQueryBuilder.format("yyyy-MM-dd HH:mm:ss");
        rangeQueryBuilder.gte(startTime);
        rangeQueryBuilder.lte(endTime);

        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        boolBuilder.must(rangeQueryBuilder);

        sourceBuilder.query(boolBuilder);
        // 排序
        sourceBuilder.sort(queryColumn, SortOrder.ASC);

        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.source(sourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        return searchResponse.getHits();
    }

    private void writeExcel(String startTime, String endTime, List<Map<String, Object>> originPvList, Map<Object, List<Map<String, Object>>> sourceMap) throws ParseException, FileNotFoundException {
        // 总PV UV sheet
        ExcelWriter excelWriter = new ExcelWriter(true, "原始数据");
        excelWriter.writeHeadRow(originPvList.get(0).keySet());
        originPvList.forEach(traceEntity -> excelWriter.writeRow(traceEntity, false));
        // 渠道sheet
        sourceMap.forEach((source, mapList) -> {
            excelWriter.setSheet((String) source);
            excelWriter.writeHeadRow(mapList.get(0).keySet());
            mapList.forEach(traceEntity -> excelWriter.writeRow(traceEntity, false));
        });

        FastDateFormat dateParser = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
        FastDateFormat dateFormat = FastDateFormat.getInstance("MMdd_HHmm");

        String start = dateFormat.format(dateParser.parse(startTime));
        String end = dateFormat.format(dateParser.parse(endTime));

        String path = filePath + fileName + start + "-" + end + ".xlsx";
        FileOutputStream fos = new FileOutputStream(path);

        excelWriter.flush(fos);
        excelWriter.close();
    }

    private Map<Object, List<Map<String, Object>>> calcStats(String startTime, String endTime, List<Map<String, Object>> sourcePvList, List<Map<String, Object>> originPvList) {
        List<Map<String, Object>> stat = new ArrayList<>();
        int totalUv = 0;

        // 分渠道source
        Map<Object, List<Map<String, Object>>> sourceMap = sourcePvList.stream().collect(groupingBy(map -> map.get("source")));
        // 设置UV及title
        for (Map.Entry<Object, List<Map<String, Object>>> entry : sourceMap.entrySet()) {
            Object key = entry.getKey();
            List<Map<String, Object>> sources = entry.getValue();
            List<Object> uvList = sources.stream().map(m -> {
                return m.get("openId");
            }).distinct().collect(Collectors.toList());

            String sourceUvTitle = "UV: " + uvList.size();
            String sourcePvTitle = "PV: " + sources.size();
            for (int i = 0; i < sources.size(); i++) {
                Object uv = i < uvList.size() ? uvList.get(i) : "";
                sources.get(i).put(sourcePvTitle, sources.get(i).get("openId"));
                sources.get(i).remove("openId");
                sources.get(i).put(sourceUvTitle, uv);
            }
            // 添加渠道统计数据
            Map<String, Object> statMap = new HashMap<>();
            statMap.put("source", key);
            statMap.put("PV", sources.size());
            statMap.put("UV", uvList.size());
            stat.add(statMap);
            totalUv += uvList.size();
        }

        // 原始数据
        // 设置PV UV 及title
        List<Object> originUvList = originPvList.stream().map(m -> {
            return m.get("openId");
        }).distinct().collect(Collectors.toList());

        String uvTitle = "UV: " + originUvList.size();
        String pvTitle = "PV: " + originPvList.size();

        Map<String, Object> statMap1 = new HashMap<>();
        statMap1.put("source", "合计");
        statMap1.put("PV", originPvList.size());
        statMap1.put("UV", totalUv);
        stat.add(statMap1);
        Map<String, Object> statMap2 = new HashMap<>();
        statMap2.put("UV", "去重后UV: " + originUvList.size());
        stat.add(statMap2);

        for (int i = 0; i < originPvList.size(); i++) {
            Map<String, Object> map = originPvList.get(i);
            Object uv = i < originUvList.size() ? originUvList.get(i) : "";
            map.put(pvTitle, map.get("openId"));
            map.remove("openId");
            map.put(uvTitle, uv);
            // 统计数据
            if (i < stat.size()) {
                Map<String, Object> statMap = stat.get(i);
                map.put("统计: " + startTime + " - " + endTime, "");
                map.put("SOURCE", statMap.get("source"));
                map.put("PV", statMap.get("PV"));
                map.put("UV", statMap.get("UV"));
            }
        }
        return sourceMap;
    }

    private void filterNoUseColumns(List<Map<String, Object>> list, List<Map<String, Object>> sourcePvList, List<Map<String, Object>> originPvList) {
        list.forEach(map -> {
            Map<String, Object> tmp = new LinkedHashMap<>();
            tmp.put("openId", map.get("openId"));
            tmp.put("source", map.get("source"));
            tmp.put("timestamp", map.get("timestamp"));
            tmp.put("token", map.get("token"));
            sourcePvList.add(tmp);
            Map<String, Object> tmp1 = new LinkedHashMap<>();
            tmp1.put("openId", map.get("openId"));
            tmp1.put("source", map.get("source"));
            tmp1.put("timestamp", map.get("timestamp"));
            tmp1.put("token", map.get("token"));
            originPvList.add(tmp1);
        });
    }

    private List<Map<String, Object>> filterSource(SearchHit[] hitsValue) {
        List<String> sourceList = new ArrayList<>();
//        sourceList.add("017");
//        sourceList.add("018");
//        sourceList.add("019");
//        sourceList.add("020");
//        sourceList.add("021");
//        sourceList.add("022");
//        sourceList.add("023");
//        sourceList.add("024");
//        sourceList.add("025");
//        sourceList.add("026");
//        sourceList.add("027");
//        sourceList.add("028");
//        sourceList.add("029");
//        sourceList.add("030");
//        sourceList.add("031");
//        sourceList.add("032");
//        sourceList.add("033");
//        sourceList.add("034");
//        sourceList.add("035");
//        sourceList.add("036");
//        sourceList.add("037");
//        sourceList.add("038");
//        sourceList.add("039");
//        sourceList.add("040");
        sourceList.add("041");
        sourceList.add("042");
        sourceList.add("043");
        sourceList.add("044");
        sourceList.add("045");

        return Stream.of(hitsValue)
                .map(SearchHit::getSourceAsMap)
                .filter(map -> {
                    if (filterSource) {
                        Object source = map.get("source");
                        if (source == null) {
                            source = JSON.parseObject(String.valueOf(map.get("query"))).get("LaunchSource");
                        }
                        if (source == null) {
                            source = JSON.parseObject(String.valueOf(map.get("options"))).get("LaunchSource");
                        }
                        if (source == null) {
                            JSONObject query = (JSONObject) JSON.parseObject(String.valueOf(map.get("options"))).get("query");
                            source = query.get("LaunchSource");
                        }
//                        String options = JSON.toJSONString(map.get("options"));
//                        if (options.contains("LaunchSource")) {
//                            System.out.println(options);
//                        }
//                        System.out.println(source);
                        if (sourceList.contains(source)) {
                            map.put("source", source);
                            map.put("openId", map.get("token"));
                        }
                        return sourceList.contains(source);
                    }
                    return false;
                })
                .collect(Collectors.toList());
    }
}
