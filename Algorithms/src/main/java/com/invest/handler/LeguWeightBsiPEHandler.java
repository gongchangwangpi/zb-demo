package com.invest.handler;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.invest.entity.LeguWeightBsiDataDO;
import com.invest.legulegu.LeguMarketEnum;
import com.invest.legulegu.LeguWeightMarketIdEnum;
import com.invest.mapper.LeguWeightBsiDataMapper;
import com.invest.util.MybatisUtil;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class LeguWeightBsiPEHandler {

    public static void main(String[] args) {

        analyze(LeguWeightMarketIdEnum.SHEN_ZHEN_CHENG_ZHI);

    }

    public static void analyze(LeguWeightMarketIdEnum marketIdEnum) {
        LeguWeightBsiDataMapper mapper = MybatisUtil.getMapper(LeguWeightBsiDataMapper.class);
        List<LeguWeightBsiDataDO> dataList = mapper.selectList(new QueryWrapper<LeguWeightBsiDataDO>().lambda()
                .eq(LeguWeightBsiDataDO::getMarketId, marketIdEnum.getCode()));

        if (CollectionUtils.isEmpty(dataList)) {
            System.out.println("没有查询到数据");
            return;
        }

        PeAnalyzeData data = new PeAnalyzeData();

        data.setTotalDays(dataList.size());
        data.setMarket(dataList.get(0).getMarketId());
        data.setSource(dataList.get(0).getSource());

        calcPoint(dataList, data);
        calcPe(dataList, data);
        calcPb(dataList, data);

        // 打印结果
        data.print();
    }

    private static void calcPoint(List<LeguWeightBsiDataDO> peList, PeAnalyzeData data) {

        LeguWeightBsiDataDO maxPoint = peList.stream().max(Comparator.comparing(getPoint())).get();
        LeguWeightBsiDataDO minPoint = peList.stream().min(Comparator.comparing(getPoint())).get();

        data.setPointHighest(getPoint(maxPoint));
        data.setPointHighestMonth(maxPoint.getDate());

        data.setPointLowest(getPoint(minPoint));
        data.setPointLowestMonth(minPoint.getDate());

        // 当前
        LeguWeightBsiDataDO lastData = peList.get(peList.size() - 1);
        data.setCurrentDate(lastData.getDate());
        data.setCurrentPoint(getPoint(lastData));
    }

    private static void calcPe(List<LeguWeightBsiDataDO> dataList, PeAnalyzeData data) {
        LeguWeightBsiDataDO maxPe = dataList.stream().max(Comparator.comparing(getPe())).get();
        LeguWeightBsiDataDO minPe = dataList.stream().min(Comparator.comparing(getPe())).get();

        data.setPeHighest(getPe(maxPe));
        data.setPeHighestMonth(maxPe.getDate());

        data.setPeLowest(getPe(minPe));
        data.setPeLowestMonth(minPe.getDate());

        // 按PE大小排序
        List<LeguWeightBsiDataDO> peSortList = Lists.newArrayList(dataList);
        peSortList.sort(Comparator.comparing(getPe()));

        data.setAvgPe((data.getPeLowest().add(data.getPeHighest()).divide(new BigDecimal(2), new MathContext(2))));
        data.setAvgTimePe(dataList.stream().map(getPe()).reduce(BigDecimal.ZERO, BigDecimal::add).divide(new BigDecimal(dataList.size()), new MathContext(2)));
        data.setMedianPe(getPe(peSortList.get(peSortList.size() / 2)));

        int zoneCount = 10;

        // 估值带
        BigDecimal peZoneValue = getPe(maxPe).subtract(getPe(minPe)).divide(new BigDecimal(zoneCount), new MathContext(2));
        for (int i = 0; i < zoneCount; i++) {
            data.getPeZone().add(getPe(minPe).add(peZoneValue.multiply(new BigDecimal(String.valueOf(i)))));
        }
        data.getPeZone().add(getPe(maxPe));

        // 时间平均估值带
        int day = peSortList.size() / zoneCount;
        for (int i = 0; i < zoneCount; i++) {
            data.getPeTimeZone().add(getPe(peSortList.get(i * day)));
        }
        data.getPeTimeZone().add(getPe(peSortList.get(peSortList.size() - 1)));

        // 当前估值
        LeguWeightBsiDataDO lastData = dataList.get(dataList.size() - 1);

        data.setCurrentPe(getPe(lastData));
        data.setPeCurrentZone(getCurrentZone(data.getPeZone(), data.getCurrentPe()));
        data.setPeCurrentTimeZone(getCurrentZone(data.getPeTimeZone(), data.getCurrentPe()));

        data.setPeCurrentTimePercent(getPeCurrentTimePercent(dataList, data.getCurrentPe()));
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
            long count = dataList.stream().filter(pe -> getPe(pe).compareTo(zone.getLeft()) >= 0)
                    .filter(pe -> getPe(pe).compareTo(zone.getRight()) < 0)
                    .count();
            zone.setTimeDays((int) count);
            zone.setPercent(divide(count, dataList.size()));
        });

        // 时间估值带
        List<BigDecimal> peTimeZone = data.getPeTimeZone();
        BigDecimal s = data.getPeHighest().subtract(data.getPeLowest());
        for (int i = 0; i < zoneCount; i++) {
            BigDecimal p1 = (peTimeZone.get(i + 1).subtract(peTimeZone.get(i))).divide(s, new MathContext(2));
            data.getPeTimeZoneList().add(PeAnalyzeData.EightZone.builder()
                    .index(i + 1)
                    .left(peTimeZone.get(i))
                    .right(peTimeZone.get(i + 1))
                    .percent(p1)
                    .timeDays(dataList.size() / zoneCount)
                    .build());
        }
    }

    private static void calcPb(List<LeguWeightBsiDataDO> dataList, PeAnalyzeData data) {
        LeguWeightBsiDataDO maxPb = dataList.stream().max(Comparator.comparing(getPb())).get();
        LeguWeightBsiDataDO minPb = dataList.stream().min(Comparator.comparing(getPb())).get();

        data.setPbHighest(getPb(maxPb));
        data.setPbHighestMonth(maxPb.getDate());

        data.setPbLowest(getPb(minPb));
        data.setPbLowestMonth(minPb.getDate());

        // 按PE大小排序
        List<LeguWeightBsiDataDO> pbSortList = Lists.newArrayList(dataList);
        pbSortList.sort(Comparator.comparing(getPb()));

        data.setAvgPb((data.getPbLowest().add(data.getPbHighest()).divide(new BigDecimal(2), new MathContext(2))));
        data.setAvgTimePb(dataList.stream().map(getPb()).reduce(BigDecimal.ZERO, BigDecimal::add).divide(new BigDecimal(dataList.size()), new MathContext(2)));
        data.setMedianPb(getPb(pbSortList.get(pbSortList.size() / 2)));

        int zoneCount = 10;

        // 估值带
        BigDecimal pbZoneValue = getPb(maxPb).subtract(getPb(minPb)).divide(new BigDecimal(zoneCount), new MathContext(2));
        for (int i = 0; i < zoneCount; i++) {
            data.getPbZone().add(getPb(minPb).add(pbZoneValue.multiply(new BigDecimal(String.valueOf(i)))));
        }
        data.getPbZone().add(getPb(maxPb));

        // 时间平均估值带
        int day = pbSortList.size() / zoneCount;
        for (int i = 0; i < zoneCount; i++) {
            data.getPbTimeZone().add(getPb(pbSortList.get(i * day)));
        }
        data.getPbTimeZone().add(getPb(pbSortList.get(pbSortList.size() - 1)));

        // 当前估值
        LeguWeightBsiDataDO lastData = dataList.get(dataList.size() - 1);

        data.setCurrentPb(getPb(lastData));
        data.setPbCurrentZone(getCurrentZone(data.getPbZone(), data.getCurrentPb()));
        data.setPbCurrentTimeZone(getCurrentZone(data.getPbTimeZone(), data.getCurrentPb()));

        data.setPbCurrentTimePercent(getPbCurrentTimePercent(dataList, data.getCurrentPb()));
        data.setPbCurrentPercent(data.getCurrentPb().divide(data.getPbHighest(), new MathContext(2)));

        // 估值带
        List<BigDecimal> pbZone = data.getPbZone();
        for (int i = 0; i < zoneCount; i++) {
            data.getPbZoneList().add(PeAnalyzeData.EightZone.builder()
                    .index(i + 1)
                    .left(pbZone.get(i))
                    .right(pbZone.get(i + 1))
                    .build());
        }

        data.getPbZoneList().forEach(zone -> {
            long count = dataList.stream().filter(dataDO -> getPb(dataDO).compareTo(zone.getLeft()) >= 0)
                    .filter(dataDO -> getPb(dataDO).compareTo(zone.getRight()) < 0)
                    .count();
            zone.setTimeDays((int) count);
            zone.setPercent(divide(count, dataList.size()));
        });

        // 时间估值带
        List<BigDecimal> pbTimeZone = data.getPbTimeZone();
        BigDecimal s = data.getPbHighest().subtract(data.getPbLowest());
        for (int i = 0; i < zoneCount; i++) {
            BigDecimal p1 = (pbTimeZone.get(i + 1).subtract(pbTimeZone.get(i))).divide(s, new MathContext(2));
            data.getPbTimeZoneList().add(PeAnalyzeData.EightZone.builder()
                    .index(i + 1)
                    .left(pbTimeZone.get(i))
                    .right(pbTimeZone.get(i + 1))
                    .percent(p1)
                    .timeDays(dataList.size() / zoneCount)
                    .build());
        }
    }

    private static Function<LeguWeightBsiDataDO, BigDecimal> getPoint() {
        return LeguWeightBsiDataDO::getClose;
    }


    private static BigDecimal getPoint(LeguWeightBsiDataDO minPoint) {
        return minPoint.getClose();
    }

    private static Function<LeguWeightBsiDataDO, BigDecimal> getPe() {
        return LeguWeightBsiDataDO::getPe;
    }
    private static Function<LeguWeightBsiDataDO, BigDecimal> getPb() {
        return LeguWeightBsiDataDO::getPb;
    }

    private static BigDecimal getPe(LeguWeightBsiDataDO pe) {
        return pe.getPe();
    }

    private static BigDecimal getPb(LeguWeightBsiDataDO pe) {
        return pe.getPb();
    }

    /**
     * 当前PE的时间百分比
     * @param peList
     * @param currentPe
     * @return
     */
    private static BigDecimal getPeCurrentTimePercent(List<LeguWeightBsiDataDO> peList, BigDecimal currentPe) {
        long count = peList.stream().filter(data -> getPe(data).compareTo(currentPe) <= 0).count();
        return divide(count, peList.size());
    }

    private static BigDecimal getPbCurrentTimePercent(List<LeguWeightBsiDataDO> peList, BigDecimal currentPe) {
        long count = peList.stream().filter(data -> getPb(data).compareTo(currentPe) <= 0).count();
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
