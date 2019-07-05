/*
 * BrokeragePolicyPo.java
 * Copyright(C) 20xx-2015 xxxxxx公司
 * All rights reserved.
 * -----------------------------------------------
 * 2019-02-26 Created
 */
package com.test.easyexcel;

import com.alibaba.excel.annotation.ExcelColumnNum;
import com.alibaba.excel.annotation.ExcelProperty;
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
    @ExcelColumnNum(value = 0)
    private String enterpriseName;
    /**
     * 承保公司名称defalt
     */
    @ExcelColumnNum(value = 1)
    private String insuranceCompanyName;
    /**
     * 渠道
     */
    @ExcelColumnNum(value = 2)
    private String channel;
    /**
     * 险种
     */
    @ExcelColumnNum(value = 3)
    private String coverage;
    /**
     * 保单号
     */
    @ExcelColumnNum(value = 4)
    private String policyNo;
    /**
     * 批单号
     */
    @ExcelColumnNum(value = 5)
    private String endorseNo;
    /**
     * 车牌号
     */
    @ExcelColumnNum(value = 6)
    private String licensePlate;
    /**
     * 投保人
     */
    @ExcelColumnNum(value = 7)
    private String policyHolder;
    /**
     * 被保险人
     */
    @ExcelColumnNum(value = 8)
    private String policyInsured;
    /**
     * 保险起期
     */
    @ExcelColumnNum(value = 9)
    @ExcelProperty(format = "yyyy/MM/dd")
    private Date beginDate;
    /**
     * 保险止期
     */
    @ExcelColumnNum(value = 10)
    @ExcelProperty(format = "yyyy/MM/dd")
    private Date endDate;
    /**
     * 出单日期
     */
    @ExcelColumnNum(value = 11)
    @ExcelProperty(format = "yyyy/MM/dd")
    private Date issueDate;
    /**
     * 保费-保费
     */
    @ExcelColumnNum(value = 12)
    private BigDecimal premiumAmount;
    /**
     * 保费-车船税
     */
    @ExcelColumnNum(value = 13)
    private BigDecimal vehicleVesselTax;
    /**
     * 保费-保费合计=保费+车船税
     */
    @ExcelColumnNum(value = 14)
    private BigDecimal totalPremiumAmount;
    /**
     * 应收保险公司手续费--手续费比例
     */
    @ExcelColumnNum(value = 15)
    private String procedureFeeRate;
    /**
     * 应收保险公司手续费--手续费金额
     */
    @ExcelColumnNum(value = 16)
    private BigDecimal procedureFeeAmount;
    /**
     * 手续费(票外）签约主体
     */
    @ExcelColumnNum(value = 17)
    private String ticketOutsideProcedureFeeOrgName;
    /**
     * 手续费(票外）比例
     */
    @ExcelColumnNum(value = 18)
    private String ticketOutsideProcedureFeeRate;
    /**
     * 手续费（票外）金额
     */
    @ExcelColumnNum(value = 19)
    private BigDecimal ticketOutsideProcedureFeeAmount;
    /**
     * 应付佣金-代理人姓名
     */
    @ExcelColumnNum(value = 20)
    private String agentName;
    /**
     * 应付佣金-代理人身份证号
     */
    @ExcelColumnNum(value = 21)
    private String agentIdNo;
    /**
     * 应付佣金-代理人银行卡号
     */
    @ExcelColumnNum(value = 22)
    private String agentBankCardNo;
    /**
     * 应付佣金-代理人银行预留手机号
     */
    @ExcelColumnNum(value = 23)
    private String agentBankMobile;
    /**
     * 应付佣金-佣金比例
     */
    @ExcelColumnNum(value = 24)
    private String brokerageRate;
    /**
     * 应付佣金-佣金金额
     */
    @ExcelColumnNum(value = 25)
    private BigDecimal brokerageAmount;

}