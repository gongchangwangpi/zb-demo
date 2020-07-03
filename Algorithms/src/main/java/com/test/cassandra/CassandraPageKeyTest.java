package com.test.cassandra;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bedpotato.alg4.ST;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.*;
import com.datastax.oss.driver.internal.core.metadata.DefaultEndPoint;
import com.datastax.oss.protocol.internal.util.Bytes;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.*;

/**
 * @author zhangbo
 * @date 2020/5/18
 */
@Slf4j
public class CassandraPageKeyTest {

    private static final String host = "127.0.0.1";
    private static final int port = 9042;
    private static final String dataCenter = "datacenter1";
    private static final String keySpace = "test";
    private static final String username = "test";
    private static final String password = "test";

    private static CqlSession session;

    public static void main(String[] args) {

        try {
            session = getSession();
            String cql = "select * from test";
            PageResultDto<Map<String, Object>> pageResultDto = null;

            int pageSize = 50;
//            String pageKey = "0x06484a3030363500f07fffffaf00";
            String pageKey = "0x06484a3030343800f07fffffcd00";
            ResultSet resultSet = pageQuery(cql, pageKey, pageSize);
            pageResultDto = parseResultSet(resultSet);

            log.info(JSON.toJSONString(pageResultDto));
            log.info("pageKey = {}", pageResultDto.getPageKey());
            log.info("fetch size = {}", pageResultDto.getData().size());
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    private static ResultSet pageQuery(String cql, String pageKey, int pageSize) {
//        PreparedStatement prepare = session.prepare(cql);
//        BoundStatement boundStatement = prepare.bind(new Object[0]);
//        boundStatement = boundStatement.setPageSize(pageSize);
//        if (StringUtils.isNotBlank(pageKey)) {
//            boundStatement = boundStatement.setPagingState(Bytes.fromHexString(pageKey));
//        }
//        return session.execute(boundStatement);

        SimpleStatement simpleStatement = SimpleStatement.builder(cql).build();
        simpleStatement = simpleStatement.setPageSize(pageSize);
        if (StringUtils.isNotBlank(pageKey)) {
            simpleStatement = simpleStatement.setPagingState(Bytes.fromHexString(pageKey));
        }
        return session.execute(simpleStatement);
    }

    public static CqlSession getSession() {
        return CqlSession.builder()
                .withAuthCredentials(username, password)
                .addContactEndPoint(new DefaultEndPoint(new InetSocketAddress(host, port)))
                .withKeyspace(keySpace)
                .withLocalDatacenter(dataCenter).build();
    }

    public static PageResultDto<Map<String, Object>> parseResultSet(ResultSet resultSet) {
        PageResultDto<Map<String, Object>> pageResultDto = new PageResultDto<>();
        // 分页key
        ByteBuffer pagingState = resultSet.getExecutionInfo().getPagingState();
        pageResultDto.setPageKey(Bytes.toHexString(pagingState));
        if (pagingState != null) {
            log.info("pagingState = {}", new String(pagingState.array()));
        }
        // 解析结果
        ColumnDefinitions columnDefinitions = resultSet.getColumnDefinitions();
        List<String> columnNameList = new ArrayList<>();
        columnDefinitions.forEach(columnDefinition -> {
            columnNameList.add(columnDefinition.getName().asInternal());
        });

        List<Map<String, Object>> result = new ArrayList<>();
        while (resultSet.getAvailableWithoutFetching() > 0) {
            Row row = resultSet.one();
            // 取出结果集
            Map<String, Object> map = new HashMap<>();
            columnNameList.forEach(columnName -> {
                map.put(columnName, row.getObject(columnName));
            });
            result.add(map);
        }

        pageResultDto.setData(result);
        return pageResultDto;
    }
}
