package com.test.chexian.api.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 报价查询请求数据交互对象
 * 
 * @author books
 */
@Getter
@Setter
public class QuoteQueryRequestDto extends RequestBodyDto implements Serializable {
    
    private static final long serialVersionUID = -1652236013721478820L;

    /**
     * 操作人手机
     */
    private String operatorMobile;
    /**
     * 报价请求标识符
     */
    private Long quoteReqId;
    
}
