package com.books.cysfsc.d3;

/**
 * 蒙特卡罗π算法 Page 110
 * 近视法求解圆周率
 *
 * 圆面积 S = π * R*R,假定 R = 1,将圆心置于坐标原点,将圆切成4份
 * 每份的面积 S1 = π / 4; 边长为 1 的正方形的面积也为 1;
 * 此时，往正方形上随机足够多的点, 则1/4份圆上的点 D1 与整个正方形上的点 D 的比为 
 * 1/4份圆的面积 比 正方形的面积
 * 即 D1 / D = S1 / 1 = π / 4; 
 * 即 π = 4 * D1 / D;
 *
 *
 * @author zhangbo
 */
public class MontePITest {

    public static void main(String[] args) {

        double pi = montePI(500000000L);

        System.out.println(pi);

    }

    /**
     *
     * @param n 总共要撒的点数
     * @return
     */
    private static double montePI(long n) {
        double PI;
        double x, y;
        long sum = 0;

        for (int i = 0; i < n; i++) {
            x = Math.random(); // 随机撒点的X坐标
            y = Math.random(); // 随机撒点的Y坐标

            if (x*x + y*y <= 1) {
                sum++; // 撒在圆中的点数
            }
        }
        PI = 4.0 * sum / n; // 计算PI
        return PI;
    }
}
