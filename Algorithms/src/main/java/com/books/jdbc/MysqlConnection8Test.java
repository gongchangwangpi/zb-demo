package com.books.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author zhangbo
 */
public class MysqlConnection8Test {

    public static void main(String[] args) throws SQLException {
        
        String url = "jdbc:mysql://172.18.8.35:3306/brokerage?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai";
//        String url = "jdbc:mysql://172.18.8.35:3306/brokerage?useUnicode=true&characterEncoding=UTF-8&useSSL=false";
        String username = "root";
        String passowrd = "kl84fc1ah5d3";

        Connection connection = JdbcUtil.getConnection(url, username, passowrd);
        
        String sql = "SELECT id, produce_time FROM t_brokerage_batch;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        ResultSet resultSet = preparedStatement.executeQuery();
        
        while (resultSet.next()) {
            System.out.print(resultSet.getObject(1) + "\t");
            System.out.println(resultSet.getObject(2));
        }
        
    }
    
}
