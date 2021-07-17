package com.invest.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 市场交易数据
 * </p>
 *
 * @author zhangbo
 * @since 2021-07-14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@TableName("market_trans_data")
public class MarketTransDataDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 市场
     */
    private String market;

    /**
     * 日期
     */
    @TableField()
    private LocalDate markDate;

    /**
     * 收盘PE
     */
    private BigDecimal pe;

    /**
     * 收盘PB
     */
    private BigDecimal pb;

    /**
     * 收盘点数
     */
    private BigDecimal point;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 数据来源
     */
    private String source;


}
