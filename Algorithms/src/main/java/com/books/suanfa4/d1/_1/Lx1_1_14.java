package com.books.suanfa4.d1._1;

/**
 * 练习 1.1.14  Page 34
 *
 * 返回 log2N 的最大整数
 *
 * Created by Administrator on 2017/6/13 0013.
 */
public class Lx1_1_14 {

    public static void main(String[] args) {
        int log = lg(1024);
        System.out.println(log);

        int x = pow(10, 3);
        System.out.println(x);
    }

    public static int lg(int n) {
        int result = 0;
        while (pow(2, result) < n) {
            result ++;
        }
        return  result;
    }

    /**
     * 计算 k 的 m 次方
     *
     * @param k
     * @param m
     * @return
     */
    public static int pow(int k, int m) {
        if (k == 0) {
            return 0;
        }
        if (m == 0) {
            return 1;
        }
        return k * pow(k, m - 1);
    }
}
