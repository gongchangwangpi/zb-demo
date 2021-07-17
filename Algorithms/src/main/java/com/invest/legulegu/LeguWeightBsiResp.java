package com.invest.legulegu;

import cn.hutool.core.date.DateUtil;
import com.invest.entity.LeguAvgBsiDataDO;
import com.invest.entity.LeguWeightBsiDataDO;
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
public class LeguWeightBsiResp {

    private List<BsiData> data;

    public static LeguWeightBsiDataDO convert(BsiData bsiData, LeguWeightMarketIdEnum marketIdEnum) {
        LeguWeightBsiDataDO leguAvgBsiDataDO = new LeguWeightBsiDataDO();
        BeanUtils.copyProperties(bsiData, leguAvgBsiDataDO);
        leguAvgBsiDataDO.setDate(DateUtil.toLocalDateTime(bsiData.date).toLocalDate());
        leguAvgBsiDataDO.setCreateTime(LocalDateTime.now());
        leguAvgBsiDataDO.setSource(marketIdEnum.getCode());
        return leguAvgBsiDataDO;
    }

    @Data
    public static class BsiData {
        private Long id;
        private Date date;
        private String marketId;
        private BigDecimal close;
        private BigDecimal pe;
        private BigDecimal pb;
        private BigDecimal peTtm;
        private BigDecimal quantileInAllHistoryPb;
        private BigDecimal quantileInAllHistoryPe;
        private BigDecimal quantileInAllHistoryPeTtm;
        private BigDecimal quantileInRecent10YearsPb;
        private BigDecimal quantileInRecent10YearsPe;
        private BigDecimal quantileInRecent10YearsPeTtm;
    }

}
