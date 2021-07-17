package com.invest.legulegu;

import cn.hutool.core.date.DateUtil;
import com.invest.SourceEnum;
import com.invest.entity.MarketTransDataDO;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 *
 * 乐咕主板
 *
 * @author bo6.zhang
 * @date 2021/7/14
 */
@Data
public class LeguPeMarketResp {

    private List<LeguPeData> data;


    @Data
    public static class LeguPeData {
        private Long id;
        private BigDecimal close;
        private Date date;
        /**
         * @see LeguMarketEnum
         */
        private String market;
        private String marketId;
        private BigDecimal pe;
    }

    public static MarketTransDataDO convert(LeguPeData data) {
        return MarketTransDataDO.builder()
                .createTime(LocalDateTime.now())
                .markDate(DateUtil.toLocalDateTime(data.date).toLocalDate())
                .market(LeguMarketEnum.get(data.market).getDesc())
                .pe(data.pe)
                .point(data.close)
                .source(SourceEnum.LEGU.name())
                .build();
    }

}
