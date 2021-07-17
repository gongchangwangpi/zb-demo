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
@TableName("legu_avg_bsi_data")
public class LeguAvgBsiDataDO {

    @TableId("id")
    private Long id;

    @TableField(value = "date")
    private LocalDate date;

    @TableField(value = "marketId")
    private String marketId;

    @TableField(value = "close")
    private BigDecimal close;

    @TableField(value = "averagePELYR")
    private BigDecimal averagePELYR;

    @TableField(value = "averagePETTM")
    private BigDecimal averagePETTM;

    @TableField(value = "middlePELYR")
    private BigDecimal middlePELYR;

    @TableField(value = "middlePETTM")
    private BigDecimal middlePETTM;

    @TableField(value = "quantileInAllHistoryAveragePeLyr")
    private BigDecimal quantileInAllHistoryAveragePeLyr;

    @TableField(value = "quantileInAllHistoryAveragePeTtm")
    private BigDecimal quantileInAllHistoryAveragePeTtm;

    @TableField(value = "quantileInAllHistoryMiddlePeLyr")
    private BigDecimal quantileInAllHistoryMiddlePeLyr;

    @TableField(value = "quantileInAllHistoryMiddlePeTtm")
    private BigDecimal quantileInAllHistoryMiddlePeTtm;

    @TableField(value = "quantileInRecent10YearsAveragePeLyr")
    private BigDecimal quantileInRecent10YearsAveragePeLyr;

    @TableField(value = "quantileInRecent10YearsAveragePeTtm")
    private BigDecimal quantileInRecent10YearsAveragePeTtm;

    @TableField(value = "quantileInRecent10YearsMiddlePeLyr")
    private BigDecimal quantileInRecent10YearsMiddlePeLyr;

    @TableField(value = "quantileInRecent10YearsMiddlePeTtm")
    private BigDecimal quantileInRecent10YearsMiddlePeTtm;

    @TableField(value = "source")
    private String source;

    @TableField(value = "createTime")
    private LocalDateTime createTime;
}
