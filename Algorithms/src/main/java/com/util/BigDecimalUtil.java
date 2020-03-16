package com.util;

import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * @description: BigDecimal工具类
 * @author: zhangbo
 * @create: 2019-09-10
 **/
public class BigDecimalUtil {

    /**
     * 最多2位小数
     * @param total
     * @param rateNum
     * @return
     */
    public static String calcRate(long total, long rateNum) {
        BigDecimal rate = calc(total, rateNum, 4);
        return getPercentNumberFormat(2, 0).format(rate);
    }

    /**
     * 最多2位小数
     * @param total
     * @param rateNum
     * @return
     */
    public static String calcRateFill0(long total, long rateNum) {
        BigDecimal rate = calc(total, rateNum, 4);
        return getPercentNumberFormat(2, 2).format(rate);
    }

    /**
     * 最多fraction位小数
     * @param total
     * @param rateNum
     * @param maxFraction
     * @return
     */
    public static String calcRate(long total, long rateNum, int maxFraction) {
        BigDecimal rate = calc(total, rateNum, maxFraction);
        NumberFormat percentNumberFormat = getPercentNumberFormat(maxFraction, 0);
        return percentNumberFormat.format(rate);
    }

    /**
     * 一定有fraction位小数，不足的补0
     * @param total
     * @param rateNum
     * @param fraction
     * @return
     */
    public static String calcRateFill0(long total, long rateNum, int fraction) {
        BigDecimal rate = calc(total, rateNum, fraction);
        NumberFormat percentNumberFormat = getPercentNumberFormat(fraction, fraction);
        return percentNumberFormat.format(rate);
    }

    /**
     * 一定有minFraction位小数，不足的补0
     * @param total
     * @param rateNum
     * @param maxFraction
     * @param minFraction
     * @return
     */
    public static String calcRate(long total, long rateNum, int maxFraction, int minFraction) {
        BigDecimal rate = calc(total, rateNum, maxFraction);
        NumberFormat percentNumberFormat = getPercentNumberFormat(maxFraction, minFraction);
        return percentNumberFormat.format(rate);
    }

    private static NumberFormat getPercentNumberFormat(int maxFraction, int minFraction) {
        NumberFormat percentNumberFormat = NumberFormat.getPercentInstance();
        percentNumberFormat.setMaximumFractionDigits(maxFraction);
        percentNumberFormat.setMinimumFractionDigits(minFraction);
        return percentNumberFormat;
    }

    private static BigDecimal calc(long total, long rateNum, int maxFraction) {
        if (total == 0) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(rateNum).divide(new BigDecimal(total), maxFraction + 2, BigDecimal.ROUND_DOWN);
    }

    public static BigDecimal calc(BigDecimal total, BigDecimal rateNum, int maxFraction) {
        if (total == null) {
            return BigDecimal.ZERO;
        }
        return rateNum.divide(total, maxFraction + 2, BigDecimal.ROUND_DOWN);
    }

    public static void main(String[] args) {
        System.out.println(calcRate(0, 1));
        System.out.println(calcRateFill0(0, 1));
        System.out.println(calcRate(3, 1, 2));
        System.out.println(calcRateFill0(0, 1, 2));
        System.out.println(calcRate(0, 1, 2, 1));
    }
}
