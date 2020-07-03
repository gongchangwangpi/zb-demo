package com.test.cassandra;

import lombok.Data;

import java.util.List;

/**
 * @author zhangbo
 * @date 2020/7/3
 */
@Data
public class PageResultDto<T> {

    private String pageKey;

    private List<T> data;

}
