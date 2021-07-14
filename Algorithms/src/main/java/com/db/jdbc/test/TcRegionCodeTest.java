package com.db.jdbc.test;

import com.db.jdbc.JdbcUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author bo6.zhang
 * @date 2021/6/28
 */
@Slf4j
public class TcRegionCodeTest {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/tc_test?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456";

    public static void main(String[] args) throws Exception {

        Connection connection = JdbcUtil.getConnection(URL, USERNAME, PASSWORD);

        PreparedStatement selectRegionCode = connection.prepareStatement("select * from region_code");

        ResultSet resultSet = selectRegionCode.executeQuery();

        // 检查是否有前缀同名的省市区名称  朝阳：4个
        while (resultSet.next()) {
            String regionName = resultSet.getString("region_name");
//            log.info(" =====>>> regionName: {}", regionName);
            Statement statement = connection.createStatement();
            regionName = StringUtils.truncate(regionName, 2);
            ResultSet countResult = statement.executeQuery("select count(*) from region_code where region_name like '" + regionName + "%'");

            if (countResult.next()) {
                long count = countResult.getLong(1);
                if (count > 1) {
                    log.info(" =====>>> regionName: {}, count: {}", regionName, count);
                    break;
                }
            }
        }

    }

}
