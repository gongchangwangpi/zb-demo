package com.mybatis.test;

import com.db.jdbc.JdbcUtil;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.internal.core.metadata.DefaultEndPoint;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author zhangbo
 * @date 2020/5/25
 */
@Slf4j
public class Mybatis2CassandraTest {

    private static final String host = "127.0.0.1";
    private static final int port = 9042;
    private static final String dataCenter = "datacenter1";
    private static final String keySpace = "cdp_hj";


    public static void main(String[] args) throws Exception {

        //
        String myqlUrl = "jdbc:mysql://mydatawaycdp-clone.crwinywugwak.rds.cn-northwest-1.amazonaws.com.cn:3306/cdp_hj?characterEncoding=utf8&useSSL=false&useUnicode=true";
        String username = "cdp";
        String password = "Mydataway2003!!";

        int count = 4007324;
        int pageNo = 263; // 263
        int pageSize = 10000;
        int startRow = 0;
        Connection connection = JdbcUtil.getConnection(myqlUrl, username, password);

        CqlSession cqlSession = getSession();

        while (startRow <= count) {
            startRow = (pageNo - 1) * pageSize;
            pageNo++;
            PreparedStatement preparedStatement = connection.prepareStatement("select * from app_customer_tag_info_v2 limit ?, ?");
            preparedStatement.setInt(1, startRow);
            preparedStatement.setInt(2, pageSize);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                StringBuilder cql = new StringBuilder("insert into app_customer_tag_info_v2 (customer_id, short_id, vip_code, customer_iphone, " +
                        "wx_nick_name, residential_quarters, open_id, wx_id, " +
                        "tag_codes, tag_value, tag_value1, tag_value2) values ");
                cql.append("('")
                    .append(resultSet.getInt("customer_id")).append("','")
                    .append(resultSet.getString("short_id")).append("','")
                    .append(resultSet.getString("vip_code")).append("','")
                    .append(resultSet.getString("customer_iphone")).append("','")
                    .append(resultSet.getString("wx_nick_name")).append("','")
                    .append(resultSet.getString("residential_quarters")).append("','")
                    .append(resultSet.getString("open_id")).append("','")
                    .append(resultSet.getString("wx_id")).append("','")
                    .append(resultSet.getString("tag_codes")).append("','")
                    .append(resultSet.getString("tag_value")).append("','")
                    .append(resultSet.getString("tag_value1")).append("','")
                    .append(resultSet.getString("tag_value2")).append("'")
                    .append("),");
                cql.deleteCharAt(cql.length() - 1);
//                log.info("cql = {}", cql);
                cqlSession.execute(cql.toString());
            }
            log.info("pageNo = {}, pageSize = {}, startRow = {}", pageNo, pageSize, startRow);
        }
        log.info("===== end");
    }

    private static CqlSession getSession() {
        return CqlSession.builder().addContactEndPoint(new DefaultEndPoint(new InetSocketAddress(host, port))).withKeyspace(keySpace).withLocalDatacenter(dataCenter).build();
    }

}
