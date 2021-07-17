package com.invest.util;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.invest.entity.MarketTransDataDO;
import com.invest.mapper.MarketTransDataMapper;
import org.apache.ibatis.logging.log4j2.Log4j2Impl;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import java.util.Objects;

/**
 * @author bo6.zhang
 * @date 2021/7/14
 */
public class MybatisUtil {

    private static SqlSessionFactory sqlSessionFactory;
    private static MybatisConfiguration configuration;
    private static ThreadLocal<SqlSession> sqlSession = new ThreadLocal<>();

    static {
        initConfiguration();
        initSqlSessionFactory();
    }

    public static void main(String[] args) throws Exception {
        MarketTransDataMapper mapper = getMapper(MarketTransDataMapper.class);
        MarketTransDataDO marketTransDataDO = mapper.selectById(1);
        System.out.println(marketTransDataDO);
    }

    public static <T> T getMapper(Class<T> type) {
        return getSqlSession().getMapper(type);
    }

    public static SqlSession getSqlSession() {
        SqlSession sqlSession = MybatisUtil.sqlSession.get();
        if (Objects.isNull(sqlSession)) {
            sqlSession = sqlSessionFactory.openSession();
            MybatisUtil.sqlSession.set(sqlSession);
        }
        return sqlSession;
    }

    public static SqlSession getSqlSession(ExecutorType type) {
        SqlSession sqlSession = MybatisUtil.sqlSession.get();
        if (Objects.isNull(sqlSession)) {
            sqlSession = sqlSessionFactory.openSession(type);
            MybatisUtil.sqlSession.set(sqlSession);
        }
        return sqlSession;
    }

    private static void initSqlSessionFactory() {
        SqlSessionFactoryBuilder builder = new MybatisSqlSessionFactoryBuilder();
        sqlSessionFactory = builder.build(configuration);
    }

    private static void initConfiguration() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/investment?characterEncoding=utf8&useSSL=false&useUnicode=true");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);

        configuration = new MybatisConfiguration(environment);
        configuration.addMappers("com.invest.mapper");
        configuration.setLogImpl(Log4j2Impl.class);



        GlobalConfig globalConfig = new GlobalConfig();
        configuration.setGlobalConfig(globalConfig);
        configuration.setMapUnderscoreToCamelCase(true);
    }

}
