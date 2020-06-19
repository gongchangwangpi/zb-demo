package com.mybatis.test.cassandra;

import com.alibaba.fastjson.JSON;
import com.mybatis.test.cassandra.adpater.CDataSource;
import com.mybatis.test.cassandra.entity.Customer;
import com.mybatis.test.cassandra.entity.CustomerQuery;
import com.mybatis.test.cassandra.mapper.CustomerMapper;
import com.test.cassandra.CassandraPageTest;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import java.util.Collection;
import java.util.List;

/**
 * @author zhangbo
 * @date 2020/5/25
 */
public class CassandraMain {

    public static void main(String[] args) throws Exception {

        // 编程式
        CDataSource cDataSource = new CDataSource(CassandraPageTest.getSession());
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, cDataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMappers("com.mybatis.test.cassandra.mapper");

        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(configuration);
//        System.out.println(configuration);

        Collection<MappedStatement> mappedStatements = configuration.getMappedStatements();
        System.out.println(mappedStatements.size());

//        MappedStatement mappedStatement = configuration.getMappedStatement("com.mybatis.test.cassandra.mapper.CustomerMapper.selectById");
//        System.out.println(mappedStatement.getSqlSource());
//        System.out.println(mappedStatement.getBoundSql(1).getSql());

        SqlSession sqlSession = factory.openSession();
        CustomerMapper customerMapper = sqlSession.getMapper(CustomerMapper.class);
//        customerMapper.selectById(1);

        CustomerQuery query = new CustomerQuery();
//        query.setId(5);
//        query.setName("name5");
//        query.setAge(19);

        query.setLastId(4);
        query.setPageSize(2);
        // 列表
//        List<Customer> customers = customerMapper.selectList(query);
        // 分页
        List<Customer> customers = customerMapper.selectPage(query);

        customers.forEach(System.out::println);

        System.out.println("=======");

        cDataSource.close();
    }

}
