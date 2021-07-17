package com.invest.legulegu;

import cn.hutool.core.date.DateUtil;
import com.invest.entity.LeguAvgBsiDataDO;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 乐咕指数
 *
 * @author bo6.zhang
 * @date 2021/7/16
 */
@Data
public class LeguAvgBsiResp {

    private List<BsiData> data;

    public static LeguAvgBsiDataDO convert(BsiData bsiData) {
        LeguAvgBsiDataDO leguAvgBsiDataDO = new LeguAvgBsiDataDO();
        BeanUtils.copyProperties(bsiData, leguAvgBsiDataDO);
        leguAvgBsiDataDO.setDate(DateUtil.toLocalDateTime(bsiData.date).toLocalDate());
        leguAvgBsiDataDO.setCreateTime(LocalDateTime.now());
        leguAvgBsiDataDO.setSource(LeguMarketEnum.HU_SHEN_300.getCode());
        return leguAvgBsiDataDO;
    }

    @Data
    public static class BsiData {
        private BigDecimal averagePELYR;
        private BigDecimal averagePETTM;
        private BigDecimal close;
        private Date date;
        private String marketId;
        private BigDecimal middlePELYR;
        private BigDecimal middlePETTM;
        private BigDecimal quantileInAllHistoryAveragePeLyr;
        private BigDecimal quantileInAllHistoryAveragePeTtm;
        private BigDecimal quantileInAllHistoryMiddlePeLyr;
        private BigDecimal quantileInAllHistoryMiddlePeTtm;
        private BigDecimal quantileInRecent10YearsAveragePeLyr;
        private BigDecimal quantileInRecent10YearsAveragePeTtm;
        private BigDecimal quantileInRecent10YearsMiddlePeLyr;
        private BigDecimal quantileInRecent10YearsMiddlePeTtm;
    }

}
