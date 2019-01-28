package com.zb.fund.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 基金表
 * 
 * @version 1.0  2019-01-25
 */
@Getter
@Setter
public class Fund {
    
    /**
     * 主键
     */
    private Long id;

    /**
     * 基金名称
     */
    private String fundName;

    /**
     * 基金编码
     */
    private String fundCode;

    /**
     * 词首大写字母
     */
    private String initial;

    /**
     * 成立日期
     */
    private Date setUpDate;

    /**
     * 基金类型编码
     */
    private String fundTypeCode;

    /**
     * 基金类型名称
     */
    private String fundTypeName;

    /**
     * 
     */
    private Date createTime;

}