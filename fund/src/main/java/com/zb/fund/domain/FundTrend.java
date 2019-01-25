package com.zb.fund.domain;

import java.util.Date;

/**
 * 基金收益趋势
 * 
 * @version 1.0  2019-01-25
 */
public class FundTrend {
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
    private String last3TearRate;

    /**
     * 
     */
    private String thisYearRate;

    /**
     * 
     */
    private String sinceInceptionRate;

    /**
     * 
     */
    private String serviceCharge;

    /**
     * 
     */
    private Date createTime;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFundCode() {
        return fundCode;
    }

    public void setFundCode(String fundCode) {
        this.fundCode = fundCode == null ? null : fundCode.trim();
    }

    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName == null ? null : fundName.trim();
    }

    public Date getStatisticsDate() {
        return statisticsDate;
    }

    public void setStatisticsDate(Date statisticsDate) {
        this.statisticsDate = statisticsDate;
    }

    public String getUnitNetWorth() {
        return unitNetWorth;
    }

    public void setUnitNetWorth(String unitNetWorth) {
        this.unitNetWorth = unitNetWorth == null ? null : unitNetWorth.trim();
    }

    public String getTotalNetWorth() {
        return totalNetWorth;
    }

    public void setTotalNetWorth(String totalNetWorth) {
        this.totalNetWorth = totalNetWorth == null ? null : totalNetWorth.trim();
    }

    public String getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(String dailyRate) {
        this.dailyRate = dailyRate == null ? null : dailyRate.trim();
    }

    public String getLastWeekRate() {
        return lastWeekRate;
    }

    public void setLastWeekRate(String lastWeekRate) {
        this.lastWeekRate = lastWeekRate == null ? null : lastWeekRate.trim();
    }

    public String getLastMonthRate() {
        return lastMonthRate;
    }

    public void setLastMonthRate(String lastMonthRate) {
        this.lastMonthRate = lastMonthRate == null ? null : lastMonthRate.trim();
    }

    public String getLast3MonthRate() {
        return last3MonthRate;
    }

    public void setLast3MonthRate(String last3MonthRate) {
        this.last3MonthRate = last3MonthRate == null ? null : last3MonthRate.trim();
    }

    public String getLast6MonthRate() {
        return last6MonthRate;
    }

    public void setLast6MonthRate(String last6MonthRate) {
        this.last6MonthRate = last6MonthRate == null ? null : last6MonthRate.trim();
    }

    public String getLastYearRate() {
        return lastYearRate;
    }

    public void setLastYearRate(String lastYearRate) {
        this.lastYearRate = lastYearRate == null ? null : lastYearRate.trim();
    }

    public String getLast2YearRate() {
        return last2YearRate;
    }

    public void setLast2YearRate(String last2YearRate) {
        this.last2YearRate = last2YearRate == null ? null : last2YearRate.trim();
    }

    public String getLast3TearRate() {
        return last3TearRate;
    }

    public void setLast3TearRate(String last3TearRate) {
        this.last3TearRate = last3TearRate == null ? null : last3TearRate.trim();
    }

    public String getThisYearRate() {
        return thisYearRate;
    }

    public void setThisYearRate(String thisYearRate) {
        this.thisYearRate = thisYearRate == null ? null : thisYearRate.trim();
    }

    public String getSinceInceptionRate() {
        return sinceInceptionRate;
    }

    public void setSinceInceptionRate(String sinceInceptionRate) {
        this.sinceInceptionRate = sinceInceptionRate == null ? null : sinceInceptionRate.trim();
    }

    public String getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(String serviceCharge) {
        this.serviceCharge = serviceCharge == null ? null : serviceCharge.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getExt17() {
        return ext17;
    }

    public void setExt17(String ext17) {
        this.ext17 = ext17 == null ? null : ext17.trim();
    }

    public String getExt18() {
        return ext18;
    }

    public void setExt18(String ext18) {
        this.ext18 = ext18 == null ? null : ext18.trim();
    }

    public String getExt19() {
        return ext19;
    }

    public void setExt19(String ext19) {
        this.ext19 = ext19 == null ? null : ext19.trim();
    }

    public String getExt21() {
        return ext21;
    }

    public void setExt21(String ext21) {
        this.ext21 = ext21 == null ? null : ext21.trim();
    }

    public String getExt22() {
        return ext22;
    }

    public void setExt22(String ext22) {
        this.ext22 = ext22 == null ? null : ext22.trim();
    }

    public String getExt23() {
        return ext23;
    }

    public void setExt23(String ext23) {
        this.ext23 = ext23 == null ? null : ext23.trim();
    }
}