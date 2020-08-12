package com.books.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * PreparedStatement 批处理
 *
 *
 * @author zhangbo
 * @date 2020/7/1
 */
public class PrepareStatementBatchTest {

    public static void main(String[] args) throws SQLException, InterruptedException {

        String sql = "insert into t_user (username, age, create_time, register_time) values (?, ?, ?, ?);";

        Connection connection = JdbcUtil.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        for (int i = 0; i < 5; i++) {
            preparedStatement.setString(1, "user" + i);
            preparedStatement.setInt(2, i);
            preparedStatement.setDate(3, new Date(System.currentTimeMillis()));
            preparedStatement.setDate(4, new Date(System.currentTimeMillis()));
            preparedStatement.addBatch();
            if (i % 3 == 0) {
                // executeBatch 在设置手动提交后，需要配置commit才能提交数据
                // 如果是自动提交，则每次 executeBatch 都会提交成功。
                preparedStatement.executeBatch();
                TimeUnit.SECONDS.sleep(1);
            }
        }

        int[] result = preparedStatement.executeBatch();

        connection.commit();

        System.out.println(Arrays.toString(result));

        JdbcUtil.close(connection);
    }

}
