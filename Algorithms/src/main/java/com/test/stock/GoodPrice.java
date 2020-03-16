package com.test.stock;

import com.util.BigDecimalUtil;

import java.math.BigDecimal;

/**
 * @author zhangbo
 * @date 2020/3/16
 **/
public class GoodPrice {

    private static final int GOOD_TTM_PE = 15;
    public static final double DEFAULT_NATIONAL_DEBT = 0.03;

    /**
     * 根据股息算好价格
     * @param dividend 股息
     * @param nationalDebt 10年期国债收益率
     * @return
     */
    public static BigDecimal goodPriceByDividend(BigDecimal dividend, BigDecimal nationalDebt) {
        return BigDecimalUtil.calc(nationalDebt, dividend, 0);
    }

    public static BigDecimal goodPriceByPeRatio(BigDecimal nowPrice, BigDecimal peRatio) {
        return BigDecimalUtil.calc(peRatio, nowPrice, 0).multiply(new BigDecimal(GOOD_TTM_PE));
    }

    /**
     * 计算是否时好价格
     *
     * @param stock
     */
    public static void isGoodPrice(Stock stock) {
        System.out.println(" ===== " + stock.getCode() + "(" + stock.getName() + ")" + " ===== ");
        isGoodPrice(new BigDecimal(stock.getDividend()), new BigDecimal(stock.getNationalDebt()), new BigDecimal(stock.getNowPrice()), new BigDecimal(stock.getTtmPeRatio()));
        System.out.println(" ===== " + stock.getCode() + "(" + stock.getName() + ")" + " ===== ");
    }

    /**
     * 计算是否时好价格
     * 默认 10年期国债收益率 为3%
     *
     * @param dividend 股息
     * @param nowPrice 现在价格
     * @param peRatio 现在的TTM市盈率
     */
    public static void isGoodPrice(double dividend, double nowPrice, double peRatio) {
        isGoodPrice(new BigDecimal(dividend), new BigDecimal(DEFAULT_NATIONAL_DEBT), new BigDecimal(nowPrice), new BigDecimal(peRatio));
    }

    /**
     * 计算是否时好价格
     *
     * @param dividend 股息
     * @param nationalDebt 10年期国债收益率
     * @param nowPrice 现在价格
     * @param peRatio 现在的TTM市盈率
     */
    public static void isGoodPrice(double dividend, double nationalDebt, double nowPrice, double peRatio) {
        isGoodPrice(new BigDecimal(dividend), new BigDecimal(nationalDebt), new BigDecimal(nowPrice), new BigDecimal(peRatio));
    }

    public static void isGoodPrice(BigDecimal dividend, BigDecimal nationalDebt, BigDecimal nowPrice, BigDecimal peRatio) {
        System.out.println("现在价格为：" + nowPrice);
        BigDecimal goodPriceByDividend = goodPriceByDividend(dividend, nationalDebt);
        System.out.println("股息/国债好价格为：" + goodPriceByDividend);
        BigDecimal goodPriceByPeRatio = goodPriceByPeRatio(nowPrice, peRatio);
        System.out.println("TTM市盈率好价格为：" + goodPriceByPeRatio);

        BigDecimal goodPrice = goodPriceByDividend.min(goodPriceByPeRatio);
        System.out.println("综合好价格为：" + goodPrice);

        BigDecimal min = goodPrice.min(nowPrice);
        if (min.equals(nowPrice)) {
            System.out.println("现在是买入时机，现价(" + nowPrice + ") 低于 好价格(" + goodPrice + ")");
        } else {
            System.out.println("现在还不是好时候，现价(" + nowPrice + ") 高于 好价格(" + goodPrice + ")");
        }
    }

    public static void main(String[] args) {

        double dividend = 1.5;
        double nationalDebt = 2.742 / 100;

//        BigDecimal goodPriceByDividend = goodPriceByDividend(new BigDecimal(dividend), new BigDecimal(nationalDebt));
//        System.out.println("股息/国债好价格 === " + goodPriceByDividend);

        double nowPrice = 54.4;
        double peRatio = 12.03;
//        BigDecimal goodPriceByPeRatio = goodPriceByPeRatio(new BigDecimal(nowPrice), new BigDecimal(peRatio));
//        System.out.println("TTM市盈率好价格 === " + goodPriceByPeRatio);

        isGoodPrice(dividend, nationalDebt, nowPrice, peRatio);
        isGoodPrice(dividend, nowPrice, peRatio);

    }

}
