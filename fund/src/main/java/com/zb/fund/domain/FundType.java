package com.zb.fund.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * 基金类型
 * 
 * @version 1.0  2019-01-25
 */
@Getter
@Setter
public class FundType {
    /**
     * 主键
     */
    private Long id;

    /**
     * 类型编码
     */
    private String typeCode;

    /**
     * 类型名称
     */
    private String typeName;

}