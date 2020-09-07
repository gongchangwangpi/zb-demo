package com.books.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author zhangbo
 * @date 2020/9/7
 */
public class JdbcUpdateRowsCountTest {

    private static final String DEFAULT_URL = "jdbc:mysql://127.0.0.1:3306/mytest?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai&useAffectedRows=true";
    private static final String DEFAULT_USERNAME = "root";
    private static final String DEFAULT_PASSWORD = "123456";

    public static void main(String[] args) throws SQLException {

        // 设置useAffectedRows=true，返回实际修改的行数
        // 默认是返回where条件匹配的行数
        Connection connection = JdbcUtil.getConnection(DEFAULT_URL, DEFAULT_USERNAME, DEFAULT_PASSWORD);

        String sql = "update t_user set username = ? where id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, "name1");
        preparedStatement.setLong(2, 1);

        int update = preparedStatement.executeUpdate();
        System.out.println("update = " + update);

        int updateCount = preparedStatement.getUpdateCount();
        System.out.println("updateCount = " + updateCount);

    }

}
