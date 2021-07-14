//package com.db.lock;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.annotation.MapperScan;
//import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
//import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Import;
//
//import javax.sql.DataSource;
//
///**
// * @author zhangbo
// * @date 2020/12/31
// */
//@MapperScan(basePackages = "com.db.lock.mapper")
//@ComponentScan(basePackages = "com.db.lock")
//@Import({MybatisPlusAutoConfiguration.class, MybatisAutoConfiguration.class})
//@Configuration
//public class LockConfig {
//
//    @Bean
//    public DruidDataSource dataSource() {
//        DruidDataSource dataSource = new DruidDataSource();
//        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/mytest");
//        dataSource.setUsername("root");
//        dataSource.setPassword("123456");
//        dataSource.setInitialSize(4);
//        dataSource.setMaxActive(10);
//        return dataSource;
//    }
//
//    @Bean
//    public MybatisProperties mybatisProperties() {
//        MybatisProperties properties = new MybatisProperties();
//        properties.setMapperLocations(new String[]{"classpath:/mappers/*.xml"});
//        return properties;
//    }
//
////    @Bean
////    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource) {
////        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
////        sqlSessionFactoryBean.setDataSource(dataSource);
////        return sqlSessionFactoryBean;
////    }
//
//}
