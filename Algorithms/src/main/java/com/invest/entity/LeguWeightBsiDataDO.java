package com.invest.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author bo6.zhang
 * @date 2021/7/16
 */
@Data
@TableName("legu_weight_bsi_data")
public class LeguWeightBsiDataDO {

    @TableId("id")
    private Long id;

    @TableField(value = "date")
    private LocalDate date;

    @TableField(value = "marketId")
    private String marketId;

    @TableField(value = "close")
    private BigDecimal close;

    @TableField(value = "pe")
    private BigDecimal pe;

    @TableField(value = "pb")
    private BigDecimal pb;

    @TableField(value = "peTtm")
    private BigDecimal peTtm;

    @TableField(value = "quantileInAllHistoryPb")
    private BigDecimal quantileInAllHistoryPb;

    @TableField(value = "quantileInAllHistoryPe")
    private BigDecimal quantileInAllHistoryPe;

    @TableField(value = "quantileInAllHistoryPeTtm")
    private BigDecimal quantileInAllHistoryPeTtm;

    @TableField(value = "quantileInRecent10YearsPb")
    private BigDecimal quantileInRecent10YearsPb;

    @TableField(value = "quantileInRecent10YearsPe")
    private BigDecimal quantileInRecent10YearsPe;

    @TableField(value = "quantileInRecent10YearsPeTtm")
    private BigDecimal quantileInRecent10YearsPeTtm;

    @TableField(value = "source")
    private String source;

    @TableField(value = "createTime")
    private LocalDateTime createTime;
}
