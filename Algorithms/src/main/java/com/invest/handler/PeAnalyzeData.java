package com.invest.handler;

import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.util.List;

/**
 * @author bo6.zhang
 * @date 2021/7/15
 */
@Data
public class PeAnalyzeData {

    private String market;
    private String source;

    private LocalDate peHighestMonth;
    private BigDecimal peHighest;

    private LocalDate peLowestMonth;
    private BigDecimal peLowest;

    private LocalDate pbHighestMonth;
    private BigDecimal pbHighest;

    private LocalDate pbLowestMonth;
    private BigDecimal pbLowest;

    private LocalDate pointLowestMonth;
    private BigDecimal pointLowest;

    private LocalDate pointHighestMonth;
    private BigDecimal pointHighest;
    /**
     * 简单算术平均数: (最大值+最小值) / 2
     */
    private BigDecimal avgPe;
    private BigDecimal avgPb;
    /**
     * 按时间平均: 每天的PE相加和 / 天数
     */
    private BigDecimal avgTimePe;
    private BigDecimal avgTimePb;
    /**
     * 中位数。按大小排序，取中间的一个
     */
    private BigDecimal medianPe;
    private BigDecimal medianPb;
    /**
     * pe pb的8/10个估值带数值.按数值等分
     */
    private List<BigDecimal> peZone = Lists.newArrayList();
    private List<BigDecimal> pbZone = Lists.newArrayList();
    /**
     * pe pb的8/10个估值带数值.按时间等分
     */
    private List<BigDecimal> peTimeZone = Lists.newArrayList();
    private List<BigDecimal> pbTimeZone = Lists.newArrayList();

    private List<EightZone> peZoneList = Lists.newArrayList();
    private List<EightZone> pbZoneList = Lists.newArrayList();

    private List<EightZone> peTimeZoneList = Lists.newArrayList();
    private List<EightZone> pbTimeZoneList = Lists.newArrayList();
    /**
     * 当前估值处于哪个带
     */
    private LocalDate currentDate;
    private BigDecimal currentPoint;
    private BigDecimal currentPe;
    private int peCurrentZone;
    private int peCurrentTimeZone;
    private BigDecimal currentPb;
    private int pbCurrentZone;
    private int pbCurrentTimeZone;

    /**
     * 当前PE比 x% 的时间高
     */
    private BigDecimal peCurrentTimePercent;
    private BigDecimal pbCurrentTimePercent;
    /**
     * 当前PE / 最大PE 的百分比
     */
    private BigDecimal peCurrentPercent;
    private BigDecimal pbCurrentPercent;

    private int totalDays;

    @Data
    @Builder
    public static class EightZone {
        private int index;              // 估值带
        private BigDecimal left;        // PE开始值
        private BigDecimal right;       // PE结束值
        private BigDecimal percent;     // 百分比
        private int timeDays;           // 天数
    }

    public void print() {
        int peZoneValue = this.peZone.size() - 1;
        System.out.println(String.format("PE %s等分估值(相等差值): %s", peZoneValue, getPeZoneStr(peZoneList)));
        System.out.println(String.format("PE %s等分估值(相等时间): %s", peZoneValue, getPeZoneStr(peTimeZoneList)));
        System.out.println("===========  ===========");
        System.out.println(String.format("%s. 数据来源: %s. 总计算天数: %s", market, source, totalDays));
        System.out.println(String.format("点数最高月份: %s. 点数最高值: %s", pointHighestMonth, pointHighest));
        System.out.println(String.format("点数最低月份: %s. 点数最低值: %s", pointLowestMonth, pointLowest));
        System.out.println(String.format("PE最高月份: %s. PE最高值: %s", peHighestMonth, peHighest));
        System.out.println(String.format("PE最低月份: %s. PE最低值: %s", peLowestMonth, peLowest));
        System.out.println("===========  ===========");
        System.out.println(String.format("最大最小算术平均PE: %s. 总和平均PE: %s. 中位数PE: %s", avgPe, avgTimePe, medianPe));
        System.out.println(String.format("最后记录时间: %s. 当前点数: %s. 当前PE: %s", currentDate, currentPoint, currentPe));
        System.out.println(String.format("当前PE值估值带: %s. 当前PE值处于历史PE值 %s. 当前PE时间估值带: %s. 当前PE比 %s 的时间高.", peCurrentZone, percent(peCurrentPercent), peCurrentTimeZone, percent(peCurrentTimePercent)));
        System.out.println("===========  ===========");
        System.out.println();
        System.out.println("===========  ===========");
        int pbZoneValue = this.pbZone.size() - 1;
        System.out.println(String.format("PB %s等分估值(相等差值): %s", pbZoneValue, getPeZoneStr(pbZoneList)));
        System.out.println(String.format("PB %s等分估值(相等时间): %s", pbZoneValue, getPeZoneStr(pbTimeZoneList)));
        System.out.println("===========  ===========");
        System.out.println(String.format("%s. 数据来源: %s. 总计算天数: %s", market, source, totalDays));
        System.out.println(String.format("点数最高月份: %s. 点数最高值: %s", pointHighestMonth, pointHighest));
        System.out.println(String.format("点数最低月份: %s. 点数最低值: %s", pointLowestMonth, pointLowest));
        System.out.println(String.format("PB最高月份: %s. PB最高值: %s", pbHighestMonth, pbHighest));
        System.out.println(String.format("PB最低月份: %s. PB最低值: %s", pbLowestMonth, pbLowest));
        System.out.println("===========  ===========");
        System.out.println(String.format("最大最小算术平均PB: %s. 总和平均PB: %s. 中位数PB: %s", avgPb, avgTimePb, medianPb));
        System.out.println(String.format("最后记录时间: %s. 当前点数: %s. 当前PB: %s", currentDate, currentPoint, currentPb));
        System.out.println(String.format("当前PB值估值带: %s. 当前PB值处于历史PB值 %s. 当前PB时间估值带: %s. 当前PB比 %s 的时间高.", pbCurrentZone, percent(pbCurrentPercent), pbCurrentTimeZone, percent(pbCurrentTimePercent)));

    }

    private String getPeZoneStr(List<EightZone> peZoneList) {
        StringBuilder sb = new StringBuilder();
        peZoneList.forEach(zone -> {
            sb.append(zone.getIndex()).append(": [")
                    .append(zone.left).append("-").append(zone.right)
                    .append("], 百分比: ").append(percent(zone.percent))
                    .append(", 天数: ").append(zone.getTimeDays()).append("。 ");
            if (zone.getIndex() == (peZoneList.size() / 2)) {
                sb.append("\n                     ");
            }
        });
        return sb.toString();
    }


    private String percent(BigDecimal b) {
        return b.multiply(new BigDecimal(100), new MathContext(2)) + "%";
    }
}
