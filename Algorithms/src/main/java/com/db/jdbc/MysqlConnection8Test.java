package com.db.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author zhangbo
 */
public class MysqlConnection8Test {

    public static void main(String[] args) throws SQLException {
        
        String url = "jdbc:mysql://127.0.0.1:3306/brokerage?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai";
        String username = "root";
        String passowrd = "123456";

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
