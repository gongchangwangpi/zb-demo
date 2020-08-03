/*
 * BrokeragePolicyPo.java
 * Copyright(C) 20xx-2015 xxxxxx公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-02-26 Created
 */
package com.test.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 佣金保单实体
 *
 */
@Getter
@Setter
public class BrokeragePolicy extends BaseRowModel implements Serializable {

    private static final long serialVersionUID = 9168479617473186178L;
    /**
     * 出单机构名称
     */
    @ExcelProperty(index = 0)
    private String enterpriseName;
    /**
     * 承保公司名称defalt
     */
    @ExcelProperty(index = 1)
    private String insuranceCompanyName;
    /**
     * 渠道
     */
    @ExcelProperty(index = 2)
    private String channel;
    /**
     * 险种
     */
    @ExcelProperty(index = 3)
    private String coverage;
    /**
     * 保单号
     */
    @ExcelProperty(index = 4)
    private String policyNo;
    /**
     * 批单号
     */
    @ExcelProperty(index = 5)
    private String endorseNo;
    /**
     * 车牌号
     */
    @ExcelProperty(index = 6)
    private String licensePlate;
    /**
     * 投保人
     */
    @ExcelProperty(index = 7)
    private String policyHolder;
    /**
     * 被保险人
     */
    @ExcelProperty(index = 8)
    private String policyInsured;
    /**
     * 保险起期
     */
    @ExcelProperty(index = 9)
    @DateTimeFormat(value = "yyyy/MM/dd")
    private Date beginDate;
    /**
     * 保险止期
     */
    @ExcelProperty(index = 10)
    @DateTimeFormat(value = "yyyy/MM/dd")
    private Date endDate;
    /**
     * 出单日期
     */
    @ExcelProperty(index = 11)
    @DateTimeFormat(value = "yyyy/MM/dd")
    private Date issueDate;
    /**
     * 保费-保费
     */
    @ExcelProperty(index = 12)
    private BigDecimal premiumAmount;
    /**
     * 保费-车船税
     */
    @ExcelProperty(index = 13)
    private BigDecimal vehicleVesselTax;
    /**
     * 保费-保费合计=保费+车船税
     */
    @ExcelProperty(index = 14)
    private BigDecimal totalPremiumAmount;
    /**
     * 应收保险公司手续费--手续费比例
     */
    @ExcelProperty(index = 15)
    private String procedureFeeRate;
    /**
     * 应收保险公司手续费--手续费金额
     */
    @ExcelProperty(index = 16)
    private BigDecimal procedureFeeAmount;
    /**
     * 手续费(票外）签约主体
     */
    @ExcelProperty(index = 17)
    private String ticketOutsideProcedureFeeOrgName;
    /**
     * 手续费(票外）比例
     */
    @ExcelProperty(index = 18)
    private String ticketOutsideProcedureFeeRate;
    /**
     * 手续费（票外）金额
     */
    @ExcelProperty(index = 19)
    private BigDecimal ticketOutsideProcedureFeeAmount;
    /**
     * 应付佣金-代理人姓名
     */
    @ExcelProperty(index = 20)
    private String agentName;
    /**
     * 应付佣金-代理人身份证号
     */
    @ExcelProperty(index = 21)
    private String agentIdNo;
    /**
     * 应付佣金-代理人银行卡号
     */
    @ExcelProperty(index = 22)
    private String agentBankCardNo;
    /**
     * 应付佣金-代理人银行预留手机号
     */
    @ExcelProperty(index = 23)
    private String agentBankMobile;
    /**
     * 应付佣金-佣金比例
     */
    @ExcelProperty(index = 24)
    private String brokerageRate;
    /**
     * 应付佣金-佣金金额
     */
    @ExcelProperty(index = 25)
    private BigDecimal brokerageAmount;

}