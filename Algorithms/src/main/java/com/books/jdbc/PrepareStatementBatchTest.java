package com.books.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * PreparedStatement 批处理
 *
 *
 * @author zhangbo
 * @date 2020/7/1
 */
public class PrepareStatementBatchTest {

    public static void main(String[] args) throws SQLException {

        String sql = "insert into t_user (username, age, create_time, register_time) values (?, ?, ?, ?);";

        Connection connection = JdbcUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        for (int i = 0; i < 5; i++) {
            preparedStatement.setString(1, "user" + i);
            preparedStatement.setInt(2, i);
            preparedStatement.setDate(3, new Date(System.currentTimeMillis()));
            preparedStatement.setDate(4, new Date(System.currentTimeMillis()));
            preparedStatement.addBatch();
        }

        int[] result = preparedStatement.executeBatch();

        System.out.println(Arrays.toString(result));

        JdbcUtil.close(connection);
    }

}
