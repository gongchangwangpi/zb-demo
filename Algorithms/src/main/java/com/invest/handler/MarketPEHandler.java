package com.invest.handler;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.invest.entity.MarketTransDataDO;
import com.invest.legulegu.LeguMarketEnum;
import com.invest.mapper.MarketTransDataMapper;
import com.invest.util.MybatisUtil;
import org.apache.commons.collections4.CollectionUtils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Comparator;
import java.util.List;

/**
 * @author bo6.zhang
 * @date 2021/7/15
 */
public class MarketPEHandler {

    public static void main(String[] args) {

        MarketTransDataMapper marketTransDataMapper = MybatisUtil.getMapper(MarketTransDataMapper.class);
        List<MarketTransDataDO> peList = marketTransDataMapper.selectList(new QueryWrapper<MarketTransDataDO>().lambda()
                .eq(MarketTransDataDO::getMarket, LeguMarketEnum.SHEN_ZHEN.getDesc()));

        if (CollectionUtils.isEmpty(peList)) {
            System.out.println("没有查询到数据");
            return;
        }

        PeAnalyzeData data = new PeAnalyzeData();

        data.setMarket(peList.get(0).getMarket());
        data.setSource(peList.get(0).getSource());

        MarketTransDataDO maxPe = peList.stream().max(Comparator.comparing(MarketTransDataDO::getPe)).get();
        MarketTransDataDO minPe = peList.stream().min(Comparator.comparing(MarketTransDataDO::getPe)).get();

        MarketTransDataDO maxPoint = peList.stream().max(Comparator.comparing(MarketTransDataDO::getPoint)).get();
        MarketTransDataDO minPoint = peList.stream().min(Comparator.comparing(MarketTransDataDO::getPoint)).get();

        data.setPeHighest(maxPe.getPe());
        data.setPeHighestMonth(maxPe.getMarkDate());

        data.setPeLowest(minPe.getPe());
        data.setPeLowestMonth(minPe.getMarkDate());

        data.setPointHighest(maxPoint.getPoint());
        data.setPointHighestMonth(maxPoint.getMarkDate());

        data.setPointLowest(minPoint.getPoint());
        data.setPointLowestMonth(minPoint.getMarkDate());

        data.setTotalDays(peList.size());

        // 按PE大小排序
        List<MarketTransDataDO> peSortList = Lists.newArrayList(peList);
        peSortList.sort(Comparator.comparing(MarketTransDataDO::getPe));

        data.setAvgPe((data.getPeLowest().add(data.getPeHighest()).divide(new BigDecimal(2), new MathContext(2))));
        data.setAvgTimePe(peList.stream().map(MarketTransDataDO::getPe).reduce(BigDecimal.ZERO, BigDecimal::add).divide(new BigDecimal(peList.size()), new MathContext(2)));
        data.setMedianPe(peSortList.get(peSortList.size() / 2).getPe());

        int zoneCount = 10;

        // 8估值带
        BigDecimal peZone = maxPe.getPe().subtract(minPe.getPe()).divide(new BigDecimal(zoneCount), new MathContext(2));
        for (int i = 0; i < zoneCount; i++) {
            data.getPeZone().add(minPe.getPe().add(peZone.multiply(new BigDecimal(String.valueOf(i)))));
        }
        data.getPeZone().add(maxPe.getPe());

        // 8个时间平均估值带
        int day = peSortList.size() / zoneCount;
        for (int i = 0; i < zoneCount; i++) {
            data.getPeTimeZone().add(peSortList.get(i * day).getPe());
        }
        data.getPeTimeZone().add(peSortList.get(peSortList.size() - 1).getPe());

        // 当前估值
        MarketTransDataDO lastData = peList.get(peList.size() - 1);

        data.setCurrentDate(lastData.getMarkDate());
        data.setCurrentPe(lastData.getPe());
        data.setCurrentPoint(lastData.getPoint());
        data.setPeCurrentZone(getCurrentZone(data.getPeZone(), data.getCurrentPe()));
        data.setPeCurrentTimeZone(getCurrentZone(data.getPeTimeZone(), data.getCurrentPe()));

        data.setPeCurrentTimePercent(getPeCurrentTimePercent(peList, data.getCurrentPe()));
        data.setPeCurrentPercent(data.getCurrentPe().divide(data.getPeHighest(), new MathContext(2)));

        // 估值带
        List<BigDecimal> peEightZone = data.getPeZone();
        for (int i = 0; i < zoneCount; i++) {
            data.getPeZoneList().add(PeAnalyzeData.EightZone.builder()
                    .index(i + 1)
                    .left(peEightZone.get(i))
                    .right(peEightZone.get(i + 1))
                    .build());
        }

        data.getPeZoneList().forEach(zone -> {
            long count = peList.stream().filter(pe -> pe.getPe().compareTo(zone.getLeft()) >= 0)
                    .filter(pe -> pe.getPe().compareTo(zone.getRight()) < 0)
                    .count();
            zone.setTimeDays((int) count);
            zone.setPercent(divide(count, peList.size()));
        });

        // 时间估值带
        List<BigDecimal> peTimeEightZone = data.getPeTimeZone();
        BigDecimal s = data.getPeHighest().subtract(data.getPeLowest());
        for (int i = 0; i < zoneCount; i++) {
            BigDecimal p1 = (peTimeEightZone.get(i + 1).subtract(peTimeEightZone.get(i))).divide(s, new MathContext(2));
            data.getPeTimeZoneList().add(PeAnalyzeData.EightZone.builder()
                    .index(i + 1)
                    .left(peTimeEightZone.get(i))
                    .right(peTimeEightZone.get(i + 1))
                    .percent(p1)
                    .timeDays(peList.size() / zoneCount)
                    .build());
        }


        System.out.println(data);
        data.print();
    }

    /**
     * 当前PE的时间百分比
     * @param peList
     * @param currentPe
     * @return
     */
    private static BigDecimal getPeCurrentTimePercent(List<MarketTransDataDO> peList, BigDecimal currentPe) {
        long count = peList.stream().filter(data -> data.getPe().compareTo(currentPe) <= 0).count();
        return divide(count, peList.size());
    }

    private static BigDecimal divide(long d1, long d2) {
        return new BigDecimal(String.valueOf(d1)).divide(new BigDecimal(String.valueOf(d2)), new MathContext(2));
    }

    /**
     * 当前PE所在估值带
     * @param peEightZone
     * @param currentPe
     * @return
     */
    private static int getCurrentZone(List<BigDecimal> peEightZone, BigDecimal currentPe) {
        for (int i = 0; i < peEightZone.size(); i++) {
            if (currentPe.compareTo(peEightZone.get(i)) <= 0) {
                return i;
            }
        }
        return 0;
    }

}
