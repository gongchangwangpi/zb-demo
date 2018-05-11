package com.zb.tcc.config;

import javax.sql.DataSource;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Order's Datasource/TransactionManager Config
 * 
 * @author zhangbo
 */
@Configuration
@MapperScan(value = "com.zb.tcc.mapper.order")
public class OrderDataSourceConfig {
    
    @Bean(value = "orderDataSource")
    @ConfigurationProperties(prefix = "datasource.order")
    public DataSource orderDataSource() {
        return DruidDataSourceBuilder.create().build();
    }
    
    @Bean(value = "orderSqlSessionFactory")
    public SqlSessionFactory orderSqlSessionFactory(DataSource orderDataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(orderDataSource);
        factoryBean.setTypeAliasesPackage("com.zb.tcc.domain.order");
        PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        factoryBean.setMapperLocations(patternResolver.getResources("classpath:mappers/order/*.xml"));
        return factoryBean.getObject();
    }
    
    @Bean(value = "orderTransactionManager")
    public PlatformTransactionManager orderTransactionManager(DataSource orderDataSource) {
        return new DataSourceTransactionManager(orderDataSource);
    }
}
