package com.zb.fund.dto;

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
public class FundTrendDto {

    /**
     * 编码
     */
    private String fundCode;

    /**
     * 名称
     */
    private String fundName;

    /**
     * 词首大写字母
     */
    private String initial;

    /**
     * 统计日期
     */
    private Date statisticsDate;

    /**
     * 单位净值
     */
    private String unitNetWorth;

    /**
     * 累计净值
     */
    private String totalNetWorth;

    /**
     * 日增长率
     */
    private String dailyRate;

    /**
     * 
     */
    private String lastWeekRate;

    /**
     * 
     */
    private String lastMonthRate;

    /**
     * 
     */
    private String last3MonthRate;

    /**
     * 
     */
    private String last6MonthRate;

    /**
     * 
     */
    private String lastYearRate;

    /**
     * 
     */
    private String last2YearRate;

    /**
     * 
     */
    private String last3YearRate;

    /**
     * 
     */
    private String thisYearRate;

    /**
     * 
     */
    private String sinceInceptionRate;

    /**
     * 成立日期
     */
    private Date setUpDate;

    /**
     * 
     */
    private String serviceCharge;

    /**
     * 
     */
    private String ext17;

    /**
     * 
     */
    private String ext18;

    /**
     * 
     */
    private String ext19;

    /**
     * 
     */
    private String ext21;

    /**
     * 
     */
    private String ext22;

    /**
     * 
     */
    private String ext23;

}