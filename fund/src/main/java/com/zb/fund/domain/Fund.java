package com.zb.fund.domain;

import java.util.Date;

/**
 * 基金表
 * 
 * @version 1.0  2019-01-25
 */
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName == null ? null : fundName.trim();
    }

    public String getFundCode() {
        return fundCode;
    }

    public void setFundCode(String fundCode) {
        this.fundCode = fundCode == null ? null : fundCode.trim();
    }

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial == null ? null : initial.trim();
    }

    public Date getSetUpDate() {
        return setUpDate;
    }

    public void setSetUpDate(Date setUpDate) {
        this.setUpDate = setUpDate;
    }

    public String getFundTypeCode() {
        return fundTypeCode;
    }

    public void setFundTypeCode(String fundTypeCode) {
        this.fundTypeCode = fundTypeCode == null ? null : fundTypeCode.trim();
    }

    public String getFundTypeName() {
        return fundTypeName;
    }

    public void setFundTypeName(String fundTypeName) {
        this.fundTypeName = fundTypeName == null ? null : fundTypeName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}