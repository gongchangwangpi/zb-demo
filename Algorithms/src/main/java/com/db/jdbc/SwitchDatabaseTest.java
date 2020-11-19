package com.db.jdbc;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 建库建表
 *
 * @author zhangbo
 */
@Slf4j
public class SwitchDatabaseTest {

    private static final String DEFAULT_URL = "jdbc:mysql://127.0.0.1:3306/create_test?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai";
    private static final String DEFAULT_USERNAME = "root";
    private static final String DEFAULT_PASSWORD = "123456";

    public static void main(String[] args) throws Exception {

        Connection connection = JdbcUtil.getConnection(DEFAULT_URL, DEFAULT_USERNAME, DEFAULT_PASSWORD);
        // 查询数据
        PreparedStatement selectPreparedStatement = connection.prepareStatement("select * from test_table");
        ResultSet resultSet = selectPreparedStatement.executeQuery();
        while (resultSet.next()) {
            log.info("id = {}, name = {}", resultSet.getInt("id"), resultSet.getString("name"));
        }

        log.info(" ===== switch database ===== ");

        // 切换database
        PreparedStatement switchPrepareStatement = connection.prepareStatement("use mytest");
        switchPrepareStatement.execute();

        PreparedStatement selectMytestPreparedStatement = connection.prepareStatement("select * from t_user");
        ResultSet mytestResultSet = selectMytestPreparedStatement.executeQuery();
        while (mytestResultSet.next()) {
            log.info("id = {}, username = {}", mytestResultSet.getInt("id"), mytestResultSet.getString("username"));
        }

        //
        JdbcUtil.close(connection);

    }
    
}
