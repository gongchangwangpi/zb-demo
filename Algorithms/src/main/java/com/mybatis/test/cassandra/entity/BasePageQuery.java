package com.mybatis.test.cassandra.entity;

import lombok.Data;

/**
 * @author zhangbo
 * @date 2020/6/19
 */
@Data
public class BasePageQuery {

    private Integer lastId;

    private Integer pageSize;

}
