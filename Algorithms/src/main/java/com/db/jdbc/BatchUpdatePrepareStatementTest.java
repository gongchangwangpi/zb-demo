package com.db.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author zhangbo
 * @date 2020/11/16
 */
public class BatchUpdatePrepareStatementTest {

    public static void main(String[] args) throws SQLException {
        Connection connection = JdbcUtil.getConnection();
        connection.setAutoCommit(false);

        PreparedStatement preparedStatement = null;
        try {
            String sql = "update t_test set name = ? where id = ?";
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, "name1");
            preparedStatement.setInt(2, 1);
            preparedStatement.addBatch();
            preparedStatement.setString(1, "name11");
            preparedStatement.setInt(2, 11);
            preparedStatement.addBatch();
            preparedStatement.setString(1, "name20");
            preparedStatement.setInt(2, 20);
            preparedStatement.addBatch();

            preparedStatement.executeBatch();
            connection.commit();
        } finally {
            JdbcUtil.close(preparedStatement);
            JdbcUtil.close(connection);
        }

    }

}
