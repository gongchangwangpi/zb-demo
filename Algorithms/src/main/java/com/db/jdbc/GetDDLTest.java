package com.db.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author zhangbo
 * @date 2020/9/4
 */
public class GetDDLTest {

    public static void main(String[] args) throws SQLException {
        Connection connection = JdbcUtil.getConnection();

        String sql = "show create table t_region";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            // 表名
            System.out.println(resultSet.getString(1));
            // DDL
            System.out.println(resultSet.getString(2));
        }
    }

}
