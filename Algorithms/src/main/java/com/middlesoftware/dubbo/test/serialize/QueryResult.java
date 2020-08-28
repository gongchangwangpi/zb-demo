package com.middlesoftware.dubbo.test.serialize;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhangbo
 */
@Getter
@Setter
public class QueryResult<T> implements Serializable {

    private static final long serialVersionUID = -4279079056210787717L;

    /**
     * 数据
     */
    private T[] data;
    
    private T body;
    
    private List<T> list;
    
}

