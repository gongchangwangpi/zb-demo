package com.db.jdbc;

import org.apache.commons.lang3.RandomStringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhangbo
 */
public class InsertUserTest {

    public static void main(String[] args) throws Exception {

        int threadCount = 1;
        
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.execute(new InsertJob());
        }

//        Connection connection = JdbcUtil.getConnection();
//
//        Random random = new Random();
//        String[] sexArr = {"MALE", null, "FEMALE", null, null};
//        String[] emailArr = {"@163.com", "@gmail.com", "@126.com", "@sina.com", "@qq.com", "@jhjhome.com"};
//
//        while (true) {
//
//            String selectSql = "INSERT INTO user (username, email, age, sex) VALUES (?, ?, ?, ?)";
//            PreparedStatement statement = connection.prepareStatement(selectSql);
//            statement.setString(1, RandomStringUtils.randomAlphanumeric(12));
//            int age = random.nextInt(150);
//            statement.setString(2, RandomStringUtils.randomAlphanumeric(8) + emailArr[age % 6]);
//            statement.setInt(3, age);
//            statement.setString(4, sexArr[age % 5]);
//
//            try {
//                statement.execute();
//            } catch (SQLException e) {
//                // ignore
//            }
//        }
        
    }
    
    static class InsertJob implements Runnable {

        @Override
        public void run() {
            PreparedStatement statement = null;
            Connection connection = null;
            try {
                connection = JdbcUtil.getConnection();

                Random random = new Random();
                String[] sexArr = {"MALE", null, "FEMALE", null, null};
                String[] emailArr = {"@163.com", "@gmail.com", "@126.com", "@sina.com", "@qq.com", "@jhjhome.com"};

                StringBuilder sql = new StringBuilder("INSERT INTO user (username, email, age, sex) VALUES (?, ?, ?, ?)");

                int count = 1000;

                while (true) {

                    for (int i = 0; i < count; i++) {
                        sql.append(",(?, ?, ?, ?)");
                    }

                    statement = connection.prepareStatement(sql.toString());

                    for (int i = 0; i < count + 1; i++) {

                        String username = RandomStringUtils.randomAlphanumeric(12);
                        int age = random.nextInt(150);
                        
                        statement.setString(1 + (i * 4), username);
                        statement.setString(2 + (i * 4), RandomStringUtils.randomAlphanumeric(8) + emailArr[age % 6]);
                        statement.setInt(3 + (i * 4), age);
                        statement.setString(4 + (i * 4), sexArr[age % 5]);
                    }

                    try {
//                        statement.execute();
                        statement.executeBatch();
                    } catch (SQLException e) {
                        // ignore
                        System.out.println(e.getMessage());
                    }
                }
            } catch (SQLException e) {
                // ignore
                System.out.println(e.getMessage());
            } finally {
                JdbcUtil.close(connection, statement, null);
            }

            System.out.println("------ end");
        }
    }
    
}
