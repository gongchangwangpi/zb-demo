package com.mybatis.test;

import com.alibaba.druid.pool.DruidDataSource;
import com.mybatis.test.user.MybatisUserEntity;
import com.mybatis.test.user.MybatisUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import java.util.*;

/**
 * @author zhangbo
 * @date 2020/5/25
 */
@Slf4j
public class MybatisMain {

    public static void main(String[] args) throws Exception {

        // 配置文件
//        String resource = "mybatis-config.xml";
//        InputStream inputStream = Resources.getResourceAsStream(resource);
//        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
//        SqlSessionFactory factory = builder.build(inputStream);
//        Configuration configuration = factory.getConfiguration();

        // 编程式
        Configuration configuration = getConfiguration();

        SqlSessionFactory factory = getSqlSessionFactory(configuration);
//        System.out.println(configuration);
//
        Collection<MappedStatement> mappedStatements = configuration.getMappedStatements();
        System.out.println(mappedStatements.size());
//
//        MappedStatement mappedStatement = configuration.getMappedStatement("com.mydataway.cdpservice.api.mapper.CassandraConfigMapper.selectById");
//        System.out.println(mappedStatement.getSqlSource());
//        System.out.println(mappedStatement.getBoundSql(1).getSql());

        SqlSession sqlSession = factory.openSession();
        MybatisUserMapper mybatisUserMapper = sqlSession.getMapper(MybatisUserMapper.class);

        long begin = System.currentTimeMillis();

//        batchInsert(sqlSession, mybatisUserMapper);
        update(sqlSession, mybatisUserMapper);

        System.out.println("=======");
        System.out.println(System.currentTimeMillis() - begin);
    }

    private static void update(SqlSession sqlSession, MybatisUserMapper mybatisUserMapper) {
        MybatisUserEntity userEntity = new MybatisUserEntity();
        userEntity.setId(1);
        userEntity.setUsername("name1");
        userEntity.setAge(10);
        int update = mybatisUserMapper.updateById(userEntity);
        System.out.println("update rows " + update);
        sqlSession.commit();
        sqlSession.close();
    }

    private static void batchInsert(SqlSession sqlSession, MybatisUserMapper mybatisUserMapper) {
        Random random = new Random();
        String[] sexArray = {"M", "F"};
        List<MybatisUserEntity> list = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            MybatisUserEntity userEntity = new MybatisUserEntity();
            userEntity.setUsername(RandomStringUtils.randomAlphanumeric(16));
            userEntity.setAge(random.nextInt(100));
            userEntity.setCreateTime(new Date());
            userEntity.setRegisterTime(new Date());
            userEntity.setSex(sexArray[random.nextInt(2)]);
            list.add(userEntity);
        }
        int insert = mybatisUserMapper.batchInsert(list);
        System.out.println("batch insert rows " + insert);
        sqlSession.commit();
        sqlSession.close();
    }

    private static SqlSessionFactory getSqlSessionFactory(Configuration configuration) {
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        return builder.build(configuration);
    }

    private static Configuration getConfiguration() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/mytest?characterEncoding=utf8&useSSL=false&useUnicode=true");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMappers("com.mybatis.test.user");
        return configuration;
    }

}
