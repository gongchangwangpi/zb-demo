package com.test;

/**
 * 复利计算
 *
 * @author zhangbo
 * @date 2019-11-11
 */
public class FuliTest {

    public static void main(String[] args) {
        double month = month(1000, 0.01, 360);
        System.out.println(month);

        double year = year(1200, 0.12, 30);
        System.out.println(year);
    }

    /**
     *
     * @param monthAmount 月投资金额
     * @param monthRate 月收益率
     * @param periods  期数
     * @return
     */
    public static double month(double monthAmount, double monthRate, int periods) {
        if (periods <= 0) {
            return monthAmount;
        }
        if (periods == 1) {
            return monthAmount * (1 + monthRate);
        }
        double total = 0;
        for (int i = 0; i < periods; i++) {
            total += monthAmount * Math.pow(1 + monthRate, i);
        }
        return total;
    }

    /**
     *
     * @param yearAmount 年投资金额
     * @param yearRate 年收益率
     * @param periods  期数
     * @return
     */
    public static double year(double yearAmount, double yearRate, int periods) {
        if (periods <= 0) {
            return yearAmount;
        }
        if (periods == 1) {
            return yearAmount * (1 + yearRate);
        }
        double total = 0;
        for (int i = 0; i < periods; i++) {
            total += yearAmount * Math.pow(1 + yearRate, i);
        }
        return total;
    }

}
