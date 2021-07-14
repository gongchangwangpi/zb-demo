package com.invest;

/**
 * 复利计算
 *
 * @author zhangbo
 * @date 2019-11-11
 */
public class FuliTest {

    public static void main(String[] args) {
        double month = month(4000, 0.015, 200);
        System.out.println(month);

        double year = year(50000, 0.1, 20);
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
            System.out.print((long) total + "\t");
        }
        return total;
    }

    /**
     * 计算利率
     *
     * @param loan          贷款总额
     * @param monthRepay    每月还款金额
     * @param year          贷款期限
     * @return
     */
    public static double lilv(double loan, double monthRepay, int year) {
        if (loan <= 0 || monthRepay <= 0 || year <= 0) {
            throw new IllegalArgumentException("param must > 0");
        }
        // 总还款金额
        double totalRepay = monthRepay * year * 12;
        return 0;
    }

}
