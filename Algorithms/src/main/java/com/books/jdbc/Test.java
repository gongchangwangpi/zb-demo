package com.books.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by books on 2017/12/15.
 */
public class Test {

    public static void main(String[] args) {
        
        try {
//            Connection insertConn = JdbcUtil.getConnection();
//            insertConn.setAutoCommit(false);
//            String insertSql = "insert into user (agentName, age) values ('user2', 22);";
//            
            Connection readConn = JdbcUtil.getConnection();
            readConn.setAutoCommit(false);

            System.out.println(readConn.getClass());
//
//            // 写事务提交
//            PreparedStatement insertStatement = insertConn.prepareStatement(insertSql);
//            insertStatement.execute();
//            insertConn.commit();

            // 读
            String selectSql = "SELECT * FROM user";
            PreparedStatement readStatement = readConn.prepareStatement(selectSql);
            
            ResultSet resultSet = readStatement.executeQuery();

            JdbcUtil.printResultSet(resultSet);
            
            readConn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    
}
