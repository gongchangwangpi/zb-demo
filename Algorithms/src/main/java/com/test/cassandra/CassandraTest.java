package com.test.cassandra;

import com.alibaba.fastjson.JSON;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ColumnDefinition;
import com.datastax.oss.driver.api.core.cql.ColumnDefinitions;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.internal.core.metadata.DefaultEndPoint;
import org.springframework.util.CollectionUtils;

import java.net.InetSocketAddress;
import java.util.*;

/**
 * @author zhangbo
 * @date 2020/5/18
 */
public class CassandraTest {

    private static final String host = "127.0.0.1";
    private static final int port = 9042;
    private static final String dataCenter = "datacenter1";
    private static final String keySpace = "customer";

    private static CqlSession session;

    public static void main(String[] args) {

        try {
            session = getSession();
//            ResultSet resultSet = session.execute("select * from customer where id = 3 limit 1");
//            ResultSet resultSet = session.execute("select * from customer");
            String cql = "select * from customer";
            List<Map<String, Object>> result = null;
            Integer lastId = null;
            do {
                ResultSet resultSet = page(cql, lastId, 6);
                result = parseResultSet(resultSet);
                System.out.println(JSON.toJSONString(result));
                if (!CollectionUtils.isEmpty(result)) {
                    lastId = (Integer) result.get(result.size() - 1).get("id");
                }

            } while (!CollectionUtils.isEmpty(result));

            // 返回结果的列信息，可根据此返回通用的map
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    private static ResultSet page(String cql, Integer lastId, int pageSize) {
        pageSize = pageSize < 1 ? 10 : pageSize;
        if (lastId == null) {
            // 第一页
            cql = cql + " limit " + pageSize;
        } else {
            cql = cql + " where token(id) > token(" + lastId + ") limit " + pageSize;
        }
        return session.execute(cql);
    }

    public static CqlSession getSession() {
        return CqlSession.builder().addContactEndPoint(new DefaultEndPoint(new InetSocketAddress(host, port))).withKeyspace(keySpace).withLocalDatacenter(dataCenter).build();
    }

    public static List<Map<String, Object>> parseResultSet(ResultSet resultSet) {
        // 返回结果的列信息，可根据此返回通用的map
        ColumnDefinitions columnDefinitions = resultSet.getColumnDefinitions();
        List<String> columnNameList = new ArrayList<>();
        columnDefinitions.forEach(columnDefinition -> {
            columnNameList.add(columnDefinition.getName().asInternal());
        });

        List<Map<String, Object>> result = new ArrayList<>();
        resultSet.forEach(row -> {
            // 取出结果集
            Map<String, Object> map = new HashMap<>();
            columnNameList.forEach(columnName -> {
                map.put(columnName, row.getObject(columnName));
            });
            result.add(map);
        });
        return result;
    }
}
