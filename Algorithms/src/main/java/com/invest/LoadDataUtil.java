package com.invest;

import com.db.jdbc.JdbcUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author bo6.zhang
 * @date 2021/3/2
 */
public class LoadDataUtil {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/investment?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456";

    public static List<XqDO> load(String code) throws SQLException {
        Connection connection = JdbcUtil.getConnection(URL, USERNAME, PASSWORD);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from t_xueqiu where symbol = '" + code + "'");
        return JdbcUtil.parseResultSet(resultSet, XqDO.class);
    }

}
