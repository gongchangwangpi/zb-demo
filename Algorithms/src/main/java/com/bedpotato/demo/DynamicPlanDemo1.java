package com.bedpotato.demo;

/**
 * 动态规划，
 * 计算1-100的值
 *
 * 解法：不用使用穷举法
 * 要计算1-100的值，如果先知道1-99的值，在加上100就可以了。
 * F(X) = F(X-1)+X;
 *
 * 动态规划3要素：最优子结构，状态转移方程，边界。
 * 其中，F(X-1) 和 F(X-2) 被称为 F(X)的最优子结构
 * F(X) = F(X-1) + F(X-2) 就是状态转移方程
 * F(1) = 1; F(2) = 2; 就是问题的边界。
 *
 * 只要找到这3个要素，就可以使用动态规划来解决问题了。
 *
 * @author bo6.zhang
 * @date 2021/2/26
 */
public class DynamicPlanDemo1 {

    public static void main(String[] args) {

        int x = 100;
        System.out.println(dynamic(x));
        System.out.println(dynamic1(x));

    }

    private static int dynamic(int x) {
        int sum = 0;
        for (int i = 0; i <= x; i++) {
            sum += i;
        }
        return sum;
    }

    private static int dynamic1(int x) {
        if (x == 1) {
            return 1;
        }
        return dynamic1(x - 1) + x;
    }

}
