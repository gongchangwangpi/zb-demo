package com.test.cassandra;

import com.datastax.oss.driver.api.mapper.annotations.Mapper;

/**
 * @author zhangbo
 * @date 2020/5/25
 */
@Mapper
public interface CustomerMapper {

    Customer findById(Integer id);

}
