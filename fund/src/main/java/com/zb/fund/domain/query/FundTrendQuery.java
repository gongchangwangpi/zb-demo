package com.zb.fund.domain.query;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 基金收益趋势
 * 
 * @version 1.0  2019-01-25
 */
@Getter
@Setter
public class FundTrendQuery {
    
    /**
     * 主键
     */
    private Long id;

    /**
     * 
     */
    private String fundCode;

    /**
     * 
     */
    private String fundName;

    /**
     * 统计日期
     */
    private Date statisticsDate;

    /**
     * 
     */
    private Date createTime;
    
}