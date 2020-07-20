package com.books.jdbc;

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
public class CreateDatabaseTest {

    private static final String DEFAULT_URL = "jdbc:mysql://127.0.0.1:3306/?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai";
    private static final String DEFAULT_USERNAME = "root";
    private static final String DEFAULT_PASSWORD = "123456";

    public static void main(String[] args) throws Exception {

        Connection connection = JdbcUtil.getConnection(DEFAULT_URL, DEFAULT_USERNAME, DEFAULT_PASSWORD);
        // 创建database
        PreparedStatement preparedStatement = connection.prepareStatement("create database create_test");
        preparedStatement.execute();
        // 切换database
        PreparedStatement useDatabase = connection.prepareStatement("use create_test");
        useDatabase.execute();
        // 建表
        PreparedStatement tablePreparedStatement = connection.prepareStatement("create table test_table (id int(11) AUTO_INCREMENT, name varchar(32), primary key (id))");
        tablePreparedStatement.execute();
        // 插入数据
        PreparedStatement insertPreparedStatement = connection.prepareStatement("insert into test_table (name) values (?)");
        insertPreparedStatement.setString(1, "name1");
        insertPreparedStatement.execute();
        // 查询数据
        PreparedStatement selectPreparedStatement = connection.prepareStatement("select * from test_table");
        ResultSet resultSet = selectPreparedStatement.executeQuery();
        while (resultSet.next()) {
            log.info("id = {}, name = {}", resultSet.getInt("id"), resultSet.getString("name"));
        }
        //
        JdbcUtil.close(connection);

    }
    
}
