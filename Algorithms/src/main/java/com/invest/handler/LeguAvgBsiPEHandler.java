package com.invest.handler;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.invest.entity.LeguAvgBsiDataDO;
import com.invest.legulegu.LeguMarketEnum;
import com.invest.mapper.LeguAvgBsiDataMapper;
import com.invest.util.MybatisUtil;
import org.apache.commons.collections4.CollectionUtils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

/**
 * @author bo6.zhang
 * @date 2021/7/15
 */
public class LeguAvgBsiPEHandler {

    public static void main(String[] args) {

        LeguAvgBsiDataMapper leguAvgBsiDataMapper = MybatisUtil.getMapper(LeguAvgBsiDataMapper.class);
        List<LeguAvgBsiDataDO> peList = leguAvgBsiDataMapper.selectList(new QueryWrapper<LeguAvgBsiDataDO>().lambda()
                .eq(LeguAvgBsiDataDO::getMarketId, LeguMarketEnum.HU_SHEN_300.getCode()));

        if (CollectionUtils.isEmpty(peList)) {
            System.out.println("没有查询到数据");
            return;
        }

        PeAnalyzeData data = new PeAnalyzeData();

        data.setMarket(peList.get(0).getMarketId());
        data.setSource(peList.get(0).getSource());

        LeguAvgBsiDataDO maxPe = peList.stream().max(Comparator.comparing(getPe())).get();
        LeguAvgBsiDataDO minPe = peList.stream().min(Comparator.comparing(getPe())).get();

        LeguAvgBsiDataDO maxPoint = peList.stream().max(Comparator.comparing(LeguAvgBsiDataDO::getClose)).get();
        LeguAvgBsiDataDO minPoint = peList.stream().min(Comparator.comparing(LeguAvgBsiDataDO::getClose)).get();

        data.setPeHighest(getPE(maxPe));
        data.setPeHighestMonth(maxPe.getDate());

        data.setPeLowest(getPE(minPe));
        data.setPeLowestMonth(minPe.getDate());

        data.setPointHighest(getPoint(maxPoint));
        data.setPointHighestMonth(maxPoint.getDate());

        data.setPointLowest(getPoint(minPoint));
        data.setPointLowestMonth(minPoint.getDate());

        data.setTotalDays(peList.size());

        // 按PE大小排序
        List<LeguAvgBsiDataDO> peSortList = Lists.newArrayList(peList);
        peSortList.sort(Comparator.comparing(getPe()));

        data.setAvgPe((data.getPeLowest().add(data.getPeHighest()).divide(new BigDecimal(2), new MathContext(2))));
        data.setAvgTimePe(peList.stream().map(getPe()).reduce(BigDecimal.ZERO, BigDecimal::add).divide(new BigDecimal(peList.size()), new MathContext(2)));
        data.setMedianPe(getPE(peSortList.get(peSortList.size() / 2)));

        int zoneCount = 10;

        // 估值带
        BigDecimal peZone = getPE(maxPe).subtract(getPE(minPe)).divide(new BigDecimal(zoneCount), new MathContext(2));
        for (int i = 0; i < zoneCount; i++) {
            data.getPeZone().add(getPE(minPe).add(peZone.multiply(new BigDecimal(String.valueOf(i)))));
        }
        data.getPeZone().add(getPE(maxPe));

        // 时间平均估值带
        int day = peSortList.size() / zoneCount;
        for (int i = 0; i < zoneCount; i++) {
            data.getPeTimeZone().add(getPE(peSortList.get(i * day)));
        }
        data.getPeTimeZone().add(getPE(peSortList.get(peSortList.size() - 1)));

        // 当前估值
        LeguAvgBsiDataDO lastData = peList.get(peList.size() - 1);

        data.setCurrentDate(lastData.getDate());
        data.setCurrentPe(getPE(lastData));
        data.setCurrentPoint(getPoint(lastData));
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
            long count = peList.stream().filter(pe -> getPE(pe).compareTo(zone.getLeft()) >= 0)
                    .filter(pe -> getPE(pe).compareTo(zone.getRight()) < 0)
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
        // 打印结果
        data.print();
    }


    private static BigDecimal getPoint(LeguAvgBsiDataDO minPoint) {
        return minPoint.getClose();
    }

    private static Function<LeguAvgBsiDataDO, BigDecimal> getPe() {
//        return BsiDataDO::getAveragePETTM;
        return LeguAvgBsiDataDO::getMiddlePETTM;
    }

    private static BigDecimal getPE(LeguAvgBsiDataDO pe) {
//        return pe.getAveragePETTM();
        return pe.getMiddlePETTM();
    }

    /**
     * 当前PE的时间百分比
     * @param peList
     * @param currentPe
     * @return
     */
    private static BigDecimal getPeCurrentTimePercent(List<LeguAvgBsiDataDO> peList, BigDecimal currentPe) {
        long count = peList.stream().filter(data -> getPE(data).compareTo(currentPe) <= 0).count();
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
