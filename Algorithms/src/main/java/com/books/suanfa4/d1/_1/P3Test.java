package com.books.suanfa4.d1._1;

/**
 * page 3
 * 欧几里得算法，算两个非负整数p/q的最大公约数
 *
 * Created by Administrator on 2017/6/12 0012.
 */
public class P3Test {

    public static void main(String[] args) {
        int gcd = gcd(6, 2);
        System.out.println(gcd);

        double c = 256;
        double sqrt = sqrt(c);
        System.out.println(sqrt);
    }

    /**
     * 若q为0，则最大公约数为p,否则将p除以q得到余数r，
     * p和q的最大公约数即为q和r的最大公约数
     *
     * @param p
     * @param q
     * @return
     */
    public static int gcd(int p, int q) {
        if (q == 0) return p;
        int r = p % q;
        return gcd(q, r);
    }

    /**
     * Page13 计算平方根（牛顿迭代法）
     *
     * @param c
     * @return
     */
    public static double sqrt(double c) {
        if (c < 0) return Double.NaN;
        double err = 1e-15;
        double t = c;
        while (Math.abs(t - c/t) > err * t) {
            t = (c/t + t) / 2.0;
        }
        return t;
    }
}
