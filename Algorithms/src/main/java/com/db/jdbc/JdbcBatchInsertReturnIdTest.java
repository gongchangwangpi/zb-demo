package com.db.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author zhangbo
 */
public class JdbcBatchInsertReturnIdTest {

    public static void main(String[] args) throws Exception {

        Connection connection = JdbcUtil.getConnection();

        Statement statement = connection.createStatement();

        String sql = "insert into t_test(name, age) values ('test1', 11), ('test2', 12)";

        statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);

        ResultSet resultSet = statement.getGeneratedKeys();
        while (resultSet.next()) {
            System.out.println("return id " + resultSet.getObject(1));
        }

        JdbcUtil.close(connection, statement, resultSet);

    }
    
}
