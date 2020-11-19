package com.db.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangbo
 */
public class WaitTimeoutTest {

    public static void main(String[] args) throws Exception {
        
//        Connection connection = JdbcUtil.getConnection();
        Connection connection = JdbcUtil.getConnection("jdbc:mysql://172.18.8.35:3306/brokerage?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai", "root", "kl84fc1ah5d3");
        
        while (true) {

            String selectSql = "SELECT * FROM admin_user WHERE id = 1";
            PreparedStatement statement = connection.prepareStatement(selectSql);
            
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                System.out.println(resultSet.getLong("id"));
            }

            TimeUnit.SECONDS.sleep(60 * 1);
        }
        
    }
    
}
