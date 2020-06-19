package com.test.cassandra;

import com.alibaba.fastjson.JSON;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.ColumnDefinitions;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.internal.core.metadata.DefaultEndPoint;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangbo
 * @date 2020/5/18
 */
public class CassandraPrepareStatementTest {

    private static final String host = "127.0.0.1";
    private static final int port = 9042;
    private static final String dataCenter = "datacenter1";
    private static final String keySpace = "customer";

    private static CqlSession session;

    public static void main(String[] args) {

        try {
            session = getSession();
            String cql = "select * from customer where id = ? and name = ?";

            PreparedStatement preparedStatement = session.prepare(cql);
            BoundStatement boundStatement = preparedStatement.bind(new Object[0]);
//            boundStatement = boundStatement.setInt("id", 5);
//            boundStatement = boundStatement.setString("name", "name5");
            boundStatement = boundStatement.setInt(0, 5);
            boundStatement = boundStatement.setString(1, "name5");

            ResultSet resultSet = session.execute(boundStatement);
            // 返回结果的列信息，可根据此返回通用的map
            List<Map<String, Object>> maps = parseResultSet(resultSet);
            System.out.println(JSON.toJSONString(maps));

        } finally {
            if (session != null) {
                session.close();
            }
        }
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
