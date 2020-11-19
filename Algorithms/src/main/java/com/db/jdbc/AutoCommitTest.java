package com.db.jdbc;

import org.apache.commons.lang3.RandomStringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangbo
 */
public class AutoCommitTest {

    public static void main(String[] args) throws Exception {

        Connection connection = JdbcUtil.getConnection();
        connection.setAutoCommit(false);

        Random random = new Random();
        String[] sexArr = {"MALE", null, "FEMALE", null, null};
        String[] emailArr = {"@163.com", "@gmail.com", "@126.com", "@sina.com", "@qq.com", "@jhjhome.com"};
        
        String selectSql = "INSERT INTO user (username, email, age, sex) VALUES (?, ?, ?, ?)";
        
        int i = 0;
        
        while (true) {

            String username = null;

            PreparedStatement statement = null;
            try {
                statement = connection.prepareStatement(selectSql);
                username = RandomStringUtils.randomAlphanumeric(12);
                statement.setString(1, username);
                int age = random.nextInt(150);
                statement.setString(2, RandomStringUtils.randomAlphanumeric(8) + emailArr[age % 6]);
                statement.setInt(3, age);
                statement.setString(4, sexArr[age % 5]);

                statement.execute();
                if (i++ == 100) {
                    break;
                }
                System.out.println(i);
            } catch (SQLException e) {
                // ignore
                System.out.println("username = " + username);
            } finally {
                JdbcUtil.close(statement);
            }

        }

        System.out.println("---------------sleep");
        TimeUnit.SECONDS.sleep(10);
        connection.commit();
        System.out.println("---------------commit");

        JdbcUtil.close(connection);
    }
    
}
