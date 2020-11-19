package com.db.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.books.bingfayishu.d4.SleepUtils;

/**
 * 幻读
 * 
 * @author zhangbo
 */
public class PhantomReadTest {

    public static void main(String[] args) {

        try {
            Connection updateConn = JdbcUtil.getConnection();
            updateConn.setAutoCommit(false);

            SleepUtils.millis(50);
            new Thread(new Job()).start();
            
            
            String updateSql = "update user set username = 'update'";
            PreparedStatement updateStatement = updateConn.prepareStatement(updateSql);
            int updateRowNum = updateStatement.executeUpdate();
            System.out.println("update row num: " + updateRowNum);
            
            SleepUtils.second(2);
            
            String select = "select * from user";
            PreparedStatement selectStatement = updateConn.prepareStatement(select);
            ResultSet resultSet = selectStatement.executeQuery();
            updateConn.commit();
            
            JdbcUtil.printResultSet(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    
    static class Job implements Runnable {

        @Override
        public void run() {
            try {
                Connection insertConn = JdbcUtil.getConnection();
                insertConn.setAutoCommit(false);
                String insertSql = "insert into user (username, age) values ('user2', 22);";
                PreparedStatement insertStatement = insertConn.prepareStatement(insertSql);
                insertStatement.execute();
                insertConn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
