package com.zb.fund.domain.query;

import com.google.common.base.Joiner;
import com.zb.fund.query.PageQuery;
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
public class FundTrendQuery extends PageQuery {
    
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

    /**
     * 组合排序
     */
    private String[]  combSort;
    
    public String orderBy() {
        if (combSort == null || combSort.length == 0) {
            return getSortColumn() + getSortType();
        } else {
            return Joiner.on(",").join(combSort).replaceAll("-", " ");
        }
    }
    
}