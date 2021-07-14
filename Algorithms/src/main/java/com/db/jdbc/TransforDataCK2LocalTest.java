//package com.db.jdbc;
//
//import com.alibaba.fastjson.JSON;
//import com.mysql.cj.jdbc.result.ResultSetImpl;
//import com.mysql.cj.protocol.ColumnDefinition;
//import com.mysql.cj.result.Field;
//import lombok.extern.slf4j.Slf4j;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.util.*;
//import java.util.stream.Collectors;
//
//import static java.util.stream.Collectors.groupingBy;
//
///**
// * @author zhangbo
// * @date 2020/7/2
// */
//@Slf4j
//public class TransforDataCK2LocalTest {
//
//    public static void main(String[] args) throws Exception {
//
//        long s = System.currentTimeMillis();
//        Connection ckConnection = JdbcUtil.getConnection("jdbc:mysql://mydatawaycdp-clone.crwinywugwak.rds.cn-northwest-1.amazonaws.com.cn:3306/cdp_ck?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai", "cdp", "Mydataway2003!!");
//
////        PreparedStatement ckPreparedStatement = ckConnection.prepareStatement("select customer_id, tag_code, tag_name, tag_value from dm_customer_tags");
//        PreparedStatement ckPreparedStatement = ckConnection.prepareStatement("select customer_id, tag_code, tag_name, tag_value from dm_customer_tags limit 7520000, 1000000");
//        ResultSet resultSet = ckPreparedStatement.executeQuery();
//
//        log.info("query ck took {} ms", System.currentTimeMillis() - s);
//
//        ResultSetImpl resultSetImpl = (ResultSetImpl) resultSet;
//        List<Map<String, Object>> mapList = new ArrayList<>();
//        while (resultSetImpl.next()) {
//            ColumnDefinition columnDefinition = resultSetImpl.getColumnDefinition();
//            Field[] fields = columnDefinition.getFields();
//            Map<String, Object> map = new HashMap<>();
//            for (int j = 0; j < fields.length; j++) {
//                String name = fields[j].getName();
//                map.put(name, resultSetImpl.getObject(name));
//            }
//            mapList.add(map);
//        }
//
//        Map<Object, List<Map<String, Object>>> customerIdMap = mapList.stream().collect(groupingBy(map -> {
//            return map.get("customer_id");
//        }));
//
//        log.info("query ck result count {}, customer count {}", mapList.size(), customerIdMap.size());
//
//        Connection localConnection = JdbcUtil.getConnection();
//        PreparedStatement preparedStatement = localConnection.prepareStatement("update dm_customer_basic_info set tags = ? where customer_id = ?");
//
//        long updateBegin = System.currentTimeMillis();
//        int i = 0;
//        Set<Map.Entry<Object, List<Map<String, Object>>>> entrySet = customerIdMap.entrySet();
//        for (Map.Entry<Object, List<Map<String, Object>>> entry : entrySet) {
//            i++;
//            Object customerId = entry.getKey();
//            List<Map<String, Object>> tagList = entry.getValue();
//            tagList = tagList.stream().map(map -> {
//                map.remove("customer_id");
//                return map;
//            }).collect(Collectors.toList());
//
//            preparedStatement.setString(1, JSON.toJSONString(tagList));
//            preparedStatement.setInt(2, (Integer) customerId);
//            preparedStatement.addBatch();
//
//            if (i % 5000 == 0) {
//                preparedStatement.executeBatch();
//                log.info("batch update i = {}", i);
//            }
//        }
//
//        preparedStatement.executeBatch();
////        localConnection.commit();
//        log.info("update took {} ms", System.currentTimeMillis() - updateBegin);
//
//        JdbcUtil.close(resultSet);
//        JdbcUtil.close(ckPreparedStatement);
//        JdbcUtil.close(preparedStatement);
//
//        JdbcUtil.close(ckConnection);
//        JdbcUtil.close(localConnection);
//
//
//    }
//
//}
