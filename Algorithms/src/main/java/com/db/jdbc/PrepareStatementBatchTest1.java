package com.db.jdbc;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.RandomStringUtils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * PreparedStatement 批处理
 *
 * 测试MySQL服务端 innodb_log_file_size 的影响
 *
 * 每次插入500条，总插入100W，innodb_log_file_size 分别为默认的48M和1G情况的耗时
 *                   1G             48M
 * getConnection took 1591        1542
 * batch insert took 1214625      1209346
 * total action took 1216216      1210888
 *
 * @author zhangbo
 * @date 2020/7/1
 */
public class PrepareStatementBatchTest1 {

    public static void main(String[] args) throws SQLException {

        long b1 = System.currentTimeMillis();

        String sql = "INSERT INTO `mytest`.`batch_insert_test`(`username`, `password`, `nickname`, `mobile`, `email`, `address`, `age`, `sex`, `jobs`, `create_time`) VALUES (?,?,?,?,?,?,?,?,?,?);";

        Connection connection = JdbcUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        long b2 = System.currentTimeMillis();

        for (int i = 0; i < 100_0000; i++) {
            preparedStatement.setString(1, "random_test_user_" + i);
            preparedStatement.setString(2, RandomStringUtils.randomAlphanumeric(64));
            preparedStatement.setString(3, RandomStringUtils.randomAlphanumeric(32));
            preparedStatement.setString(4, RandomStringUtils.randomAlphanumeric(11));
            preparedStatement.setString(5, RandomStringUtils.randomAlphanumeric(64));
            preparedStatement.setString(6, RandomStringUtils.randomAlphanumeric(120));
            preparedStatement.setInt(7, RandomUtils.nextInt(150));
            preparedStatement.setString(8, RandomStringUtils.randomAlphanumeric(1));
            preparedStatement.setString(9, RandomStringUtils.randomAlphanumeric(60));
            preparedStatement.setDate(10, new Date(System.currentTimeMillis()));

            preparedStatement.addBatch();

            if (i % 500 == 0) {
                preparedStatement.executeBatch();
            }
        }

        preparedStatement.executeBatch();
        long b3 = System.currentTimeMillis();

        System.out.println(b1);
        System.out.println(b2);
        System.out.println(b3);

        System.out.println("getConnection took " + (b2 - b1));
        System.out.println("batch insert took " + (b3 - b2));
        System.out.println("total action took " + (b3 - b1));

        JdbcUtil.close(connection);
    }

}
