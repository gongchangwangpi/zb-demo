package com.mybatis.test;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSON;
import com.mydataway.cdpservice.api.entity.CassandraConfig;
import com.mydataway.cdpservice.api.mapper.CassandraConfigMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;

/**
 * @author zhangbo
 * @date 2020/5/25
 */
public class Main {

    public static void main(String[] args) throws Exception {

        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(inputStream);

//        DataSource dataSource = new DruidDataSource();
//        TransactionFactory transactionFactory = new JdbcTransactionFactory();

//        Environment environment = new Environment("development", transactionFactory, dataSource);
//
//        Configuration configuration = new Configuration(environment);

        Configuration configuration = factory.getConfiguration();

        System.out.println(configuration);

        Collection<MappedStatement> mappedStatements = configuration.getMappedStatements();

        System.out.println(mappedStatements.size());
        MappedStatement mappedStatement = configuration.getMappedStatement("com.mydataway.cdpservice.api.mapper.CassandraConfigMapper.selectByEntId");
        System.out.println(mappedStatement.getSqlSource());
        System.out.println(mappedStatement.getBoundSql(1).getSql());

        SqlSession sqlSession = factory.openSession();
        CassandraConfigMapper cassandraConfigMapper = sqlSession.getMapper(CassandraConfigMapper.class);
        List<CassandraConfig> cassandraConfigs = cassandraConfigMapper.selectByEntity(new CassandraConfig());

        System.out.println(JSON.toJSONString(cassandraConfigs));
        System.out.println("=======");
//        System.out.println(selectPersonSql(null));
    }

    private static String selectPersonSql(String name) {
        return new SQL() {{
            SELECT("P.ID, P.USERNAME, P.PASSWORD, P.FULL_NAME");
            SELECT("P.LAST_NAME, P.CREATED_ON, P.UPDATED_ON");
            FROM("PERSON P");
            FROM("ACCOUNT A");
            INNER_JOIN("DEPARTMENT D on D.ID = P.DEPARTMENT_ID");
            INNER_JOIN("COMPANY C on D.COMPANY_ID = C.ID");
            WHERE("P.ID = A.ID");
            if (StringUtils.isNotEmpty(name)) {
                WHERE("P.FIRST_NAME like ?");
            }
            OR();
            WHERE("P.LAST_NAME like ?");
            GROUP_BY("P.ID");
            HAVING("P.LAST_NAME like ?");
            OR();
            HAVING("P.FIRST_NAME like ?");
            ORDER_BY("P.ID");
            ORDER_BY("P.FULL_NAME");
        }}.toString();
    }
}
