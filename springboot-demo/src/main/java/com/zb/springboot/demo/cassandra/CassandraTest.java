//package com.zb.springboot.demo.cassandra;
//
//import com.datastax.driver.core.*;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
///**
// * @author zhangbo
// * @date 2020/5/18
// */
//public class CassandraTest {
//
//    public static void main(String[] args) {
//
//        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").withPort(9042).build();
//
//        Metadata metadata = cluster.getMetadata();
//        for (Host host : metadata.getAllHosts()) {
//            System.out.println("------" + host.getAddress());
//        }
//
//        System.out.println("======================");
//
////        for (KeyspaceMetadata keyspaceMetadata : metadata.getKeyspaces()) {
////            System.out.println("--------" + keyspaceMetadata.getName());
////        }
//
//        Session session = cluster.connect("customer");
//
//        ResultSet resultSet = session.execute("select * from customer where id = 3 limit 1");
//
//        // 返回结果的列信息，可根据此返回通用的map
//        ColumnDefinitions columnDefinitions = resultSet.getColumnDefinitions();
//        System.out.println(columnDefinitions);
//        List<String> columnNames = columnDefinitions.asList().stream().map(ColumnDefinitions.Definition::getName).collect(Collectors.toList());
//        columnDefinitions.forEach(column -> {
//            System.out.println(column.getName());
//        });
//
//        List<Map<String, Object>> result = new ArrayList<>();
//        resultSet.forEach(row -> {
////            Object map0 = row.getObject(0);
////            Object map1 = row.getObject(1);
////            Object map2 = row.getObject(2);
////            System.out.println(map0);
////            System.out.println(map1);
////            System.out.println(map2);
////            System.out.println(row.getInt("id") + row.get("name", String.class) + row.get("age", Integer.class));
//            // 取出结果集
//            Map<String, Object> map = new HashMap<>();
//            columnNames.forEach(columnName -> {
//                map.put(columnName, row.getObject(columnName));
//            });
//            result.add(map);
//        });
//
//        System.out.println(result);
//
//        session.close();
//
//        cluster.close();
//    }
//
//}
