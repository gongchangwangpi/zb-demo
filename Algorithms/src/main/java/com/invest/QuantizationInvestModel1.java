package com.invest;

import java.time.LocalDate;
import java.util.List;

/**
 * @author bo6.zhang
 * @date 2021/2/20
 */
public class QuantizationInvestModel1 {

    public static void main(String[] args) throws Exception {

        //
        List<XqDO> list = LoadDataUtil.load("SZ000895");
        System.out.println(list.size());

        avgDays(list);

        //
        list.removeIf(xqDO -> xqDO.getDate().isBefore(LocalDate.of(2010, 1, 1)));

        int num = 100;
        double m = 0;
        double yl = 0;
        boolean buy = false;
        int buyCount = 0;
        double avgBefore = 0;
        double avgAfter = 0;
        for (int i = 0; i < list.size(); i++) {
            XqDO xqDO = list.get(i);
            avgBefore = xqDO.getAvgMap().get(3);
//            avgBefore = xqDO.getClose();
            avgAfter = xqDO.getAvgMap().get(10);
            if (avgBefore > avgAfter) {
                if (!buy) {
                    // buy
                    XqDO nextDay = list.get(i + 1);
                    double nextAvg = (nextDay.getOpen() + nextDay.getClose()) / 2;
                    if (nextAvg < xqDO.getClose() * 1.03) {
                        m = num * nextAvg;
                        buy = true;
                        buyCount++;
                    }
                }
            } else {
                // sell
                if (buy) {
                    yl += num * list.get(i + 1).getOpen() - m;
                    buy = false;
                }
            }
        }

        System.out.println(yl);
        System.out.println(buyCount);

    }

    private static void avgDays(List<XqDO> dataList) {
        for (int i = 0; i < dataList.size(); i++) {
            XqDO xqDO = dataList.get(i);
            xqDO.getAvgMap().put(3, calcAvg(dataList, 3, i));
            xqDO.getAvgMap().put(5, calcAvg(dataList, 5, i));
            xqDO.getAvgMap().put(10, calcAvg(dataList, 10, i));
            xqDO.getAvgMap().put(20, calcAvg(dataList, 20, i));
            xqDO.getAvgMap().put(30, calcAvg(dataList, 30, i));
            xqDO.getAvgMap().put(60, calcAvg(dataList, 60, i));
            xqDO.getAvgMap().put(120, calcAvg(dataList, 120, i));
            xqDO.getAvgMap().put(250, calcAvg(dataList, 250, i));
        }
    }

    private static double calcAvg(List<XqDO> list, int days, int index) {
        if (days > index + 1) {
            return 0;
        }
        return list.subList(index + 1 - days, index + 1).stream().mapToDouble(XqDO::getClose).average().getAsDouble();
    }

}
