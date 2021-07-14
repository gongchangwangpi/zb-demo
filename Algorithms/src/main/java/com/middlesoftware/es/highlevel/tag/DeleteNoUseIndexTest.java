package com.middlesoftware.es.highlevel.tag;

import cn.hutool.core.io.IoUtil;
import com.db.jdbc.JdbcUtil;
import com.middlesoftware.es.highlevel.trace.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

/**
 * @author zhangbo
 * @date 2020/10/9
 */
@Slf4j
public class DeleteNoUseIndexTest extends BaseTest {

    public static void main(String[] args) throws Exception {

        new DeleteNoUseIndexTest().execute();

    }

    @Override
    public void process() throws Exception {
        List<String> companyCodeList = getCompanyCodes();
        log.info("total select company code count: {}", companyCodeList.size());

//        companyCodeList.clear();
//        companyCodeList.add("cdp0926ys_test");

        companyCodeList.forEach(companyCode -> {
            String index = companyCode + "*";
            DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(index);
            try {
                AcknowledgedResponse response = client.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
                log.info("delete index: [{}], response: {}", index, response.isAcknowledged());
            } catch (IOException e) {
                //
                log.error("delete index error", e);
            }
        });
    }

    private static List<String> getCompanyCodes() throws Exception {
        String jdbc_url = "jdbc:mysql://cdp-maidian.crwinywugwak.rds.cn-northwest-1.amazonaws.com.cn:3306/cdp_ods_manage_testing?characterEncoding=utf8&useSSL=false&useUnicode=true";
        String jdbc_username = "cdp_ods_manager";
        String jdbc_password = "TeR3lnzX5kuvd4ha";

        ResultSet resultSet = null;
        Statement statement = null;
        Connection connection = null;
        try {
            connection = JdbcUtil.getConnection(jdbc_url, jdbc_username, jdbc_password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select company_code from ods_company");

            List<String> companyCodeList = new ArrayList<>();
            while (resultSet.next()) {
                String companyCode = resultSet.getString(1);
                log.info("select company code: {}", companyCode);
                companyCodeList.add(companyCode);
            }
            return companyCodeList;
        } finally {
            IoUtil.close(resultSet);
            IoUtil.close(statement);
            IoUtil.close(connection);
        }
    }

}
