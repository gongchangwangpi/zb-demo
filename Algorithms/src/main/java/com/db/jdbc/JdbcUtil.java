package com.db.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 原生 JDBC
 * 
 * @author zhangbo
 */
public class JdbcUtil {
    
    private static Logger logger = LoggerFactory.getLogger(JdbcUtil.class);

    private static final String DEFAULT_URL = "jdbc:mysql://127.0.0.1:3306/mytest?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai";
    private static final String DEFAULT_USERNAME = "root";
    private static final String DEFAULT_PASSWORD = "123456";
    
    /*static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            logger.error("加载驱动失败", e);
            throw new RuntimeException("加载驱动失败");
        }
    }*/

    public static Connection getConnection() {
        return getConnection(DEFAULT_URL, DEFAULT_USERNAME, DEFAULT_PASSWORD);
    }
    
    public static Connection getConnection(String url, String username, String password) {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            logger.error("获取链接失败", e);
            throw new RuntimeException("获取链接失败");
        }
    }
    
    public static <T> List<T> parseResultSet(ResultSet resultSet, Class<T> type, Map<String, String> columnKeyMap) {
        List<T> list = new ArrayList<T>();
        if (resultSet == null) {
            return list;
        }
        try {
            while (resultSet.next()) {
                T instance = type.newInstance();

                for (Map.Entry<String, String> entry : columnKeyMap.entrySet()) {
                    type.getField(entry.getKey()).set(instance, resultSet.getObject(entry.getValue()));
                }

                list.add(instance);
            }
        } catch (SQLException e) {
            logger.error("解析结果集失败", e);
            throw new RuntimeException("解析结果集失败");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    
    public static void printResultSet(ResultSet resultSet) {
        if (resultSet == null) {
            System.out.println("没有查询到结果");
            return ;
        }

        try {
            while (resultSet.next()) {
                System.out.print("user: id = " + resultSet.getLong(1));
                System.out.print(", agentName = " + resultSet.getString(2));
                System.out.print(", age = " + resultSet.getInt(3));
                System.out.println();
            }
        } catch (SQLException e) {
            logger.error("解析结果集失败", e);
            throw new RuntimeException("解析结果集失败");
        }
    }
    
    public static void close(Connection connection, Statement statement, ResultSet resultSet) {
        close(connection);
        close(statement);
        close(resultSet);
    }

    public static void close(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                // ignore
            }
        }
    }

}
