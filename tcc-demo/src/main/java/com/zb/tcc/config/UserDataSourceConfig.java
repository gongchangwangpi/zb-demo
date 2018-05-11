package com.zb.tcc.config;

import javax.sql.DataSource;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * User's Datasource/TransactionManager Config
 * 
 * @author zhangbo
 */
@Configuration
@MapperScan(value = "com.zb.tcc.mapper.user")
public class UserDataSourceConfig {
    
    @Bean(value = "userDataSource")
    @Primary
    @ConfigurationProperties(prefix = "datasource.user")
    public DataSource userDataSource() {
        return DruidDataSourceBuilder.create().build();
    }
    
    @Bean(value = "userSqlSessionFactory")
    @Primary
    public SqlSessionFactory userSqlSessionFactory(DataSource userDataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(userDataSource);
        factoryBean.setTypeAliasesPackage("com.zb.tcc.domain.user");
        PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        factoryBean.setMapperLocations(patternResolver.getResources("classpath:mappers/user/*.xml"));
        return factoryBean.getObject();
    }
    
    @Bean(value = "userTransactionManager")
    @Primary
    public PlatformTransactionManager userTransactionManager(DataSource userDataSource) {
        return new DataSourceTransactionManager(userDataSource);
    }
}
