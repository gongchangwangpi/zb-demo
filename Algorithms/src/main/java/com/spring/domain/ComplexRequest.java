package com.spring.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 负责请求参数测试
 *
 * Created by Administrator on 2018/5/2 0002.
 */
@Getter
@Setter
public class ComplexRequest implements Serializable {

    private static final long serialVersionUID = 3780675275282234637L;

    private Long id;

    private String username;

    private int age;

    private User user;

    private List<Job> jobs;

}
