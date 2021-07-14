package com.bedpotato.demo;

/**
 * 动态规划，
 * 一共有10级楼梯，每次只能走1步或2步，共有多少种走法
 *
 * 解法：不用使用穷举法
 * 如果要到第10级，那么有两种走法，先到第9级，然后在走1步；或者先到第8级，然后走2步
 * 以此类推，如果要到第9级呢，也是一样的
 * 最后，如果要到1级，一共只有1种走法；要到2级，一共有2种
 *
 * 总结为公式，即：F(10) = F(9) + F(8); F(1) = 1; F(2) = 2;
 * F(X) = F(X-1) + F(X-2);
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
public class DynamicPlanDemo {

    public static void main(String[] args) {

        int j = 8;

        int i = dynamicPlan(j);
        System.out.println(i);
        int i1 = dynamicPlan1(j);
        System.out.println(i1);

    }

    /**
     * 时间复杂度太高了 O(2^N)，会重复计算很多次
     * 如 F(5)=F(4)+F(3); F(6)=F(5)+F(4);就会计算两次F(4)
     * 每计算一个新的值，都是把前两个的值相加即可
     *
     * @param x
     * @return
     */
    private static int dynamicPlan(int x) {
        if (x == 1) {
            return 1;
        }
        if (x == 2) {
            return 2;
        }
        return dynamicPlan(x - 1) + dynamicPlan(x - 2);
    }

    /**
     * 优化版本
     * 时间复杂度 O(N)
     * 空间复杂度 O(1)
     *
     * @param x
     * @return
     */
    private static int dynamicPlan1(int x) {
        if (x == 1) {
            return 1;
        }
        if (x == 2) {
            return 2;
        }
        int a = 1; // F(1)
        int b = 2; // F(2)
        int tmp = 0;
        for (int i = 3; i <= x; i++) {
            tmp = a + b;
            a = b;
            b = tmp;
        }
        return tmp;
    }

}
