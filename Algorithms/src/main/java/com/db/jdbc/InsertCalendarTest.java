package com.db.jdbc;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Week;
import com.alibaba.fastjson.JSON;
import com.mysql.cj.jdbc.result.ResultSetImpl;
import com.mysql.cj.protocol.ColumnDefinition;
import com.mysql.cj.result.Field;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * @author zhangbo
 * @date 2020/7/2
 */
@Slf4j
public class InsertCalendarTest {

    public static void main(String[] args) throws Exception {

        long s = System.currentTimeMillis();
        Connection connection = JdbcUtil.getConnection("jdbc:mysql://cdp-maidian.crwinywugwak.rds.cn-northwest-1.amazonaws.com.cn:3306/promo_center_dev?characterEncoding=UTF-8&serverTimezone=Asia/Shanghai", "c_platform_ourdev", "C_platform_ourdev*");

        String sql = "insert into t_workday (date, workday) values (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        DateTime start = DateUtil.beginOfDay(new Date());

        Date end = FastDateFormat.getInstance("yyyy-MM-dd").parse("2021-12-31");

        com.util.DateUtil.startEndConsume(start, end, (date -> DateUtils.addDays(date, 1)), (b, e) -> {

            try {
                preparedStatement.setDate(1, new java.sql.Date(b.getTime()));
                Week week = DateUtil.dayOfWeekEnum(b);
                int workday = 1;
                if (week == Week.SATURDAY || week == Week.SUNDAY) {
                    workday = 2;
                }
                preparedStatement.setInt(2, workday);
                preparedStatement.addBatch();
            } catch (SQLException throwables) {
                //
            }
        });

        preparedStatement.executeBatch();

        JdbcUtil.close(preparedStatement);
        JdbcUtil.close(connection);

    }

}
