package com.db.jdbc;

import java.sql.ResultSet;
import java.sql.Statement;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;

/**
 * @author zhangbo
 */
public class JdbcTest {

    public static void main(String[] args) throws Exception {

//        String url = "";
//        String username = "";
//        String password = "";

//        Connection connection = JdbcUtil.getConnection(url, username, password);
        Connection connection = JdbcUtil.getConnection();

        Statement statement = connection.createStatement();

        String sql = "insert into t_test(name, age) values ('test1', 11), ('test2', 12)";
        statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        ResultSet resultSet = statement.getGeneratedKeys();

        while (resultSet.next()) {
            System.out.println("return id " + resultSet.getObject(1));
        }

        JdbcUtil.close(connection, statement, null);

    }
    
}
