package com.mybatis.test.cassandra.mapper;

import com.mybatis.test.cassandra.entity.Customer;
import com.mybatis.test.cassandra.entity.CustomerQuery;

import java.util.List;

/**
 * @author zhangbo
 * @date 2020/6/18
 */
public interface CustomerMapper {

    Customer selectById(Integer id);

    List<Customer> selectList(CustomerQuery query);

    List<Customer> selectPage(CustomerQuery query);

}
