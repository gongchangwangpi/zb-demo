package com.books.suanfa4.d1._1;

import java.util.Arrays;

/**
 * 练习 1.1.15  Page 34
 *
 * 接受一个整形数组 a[] 和整数 m，返回一个长度为m的数组
 * 其中第 i 个元素的值为整数 i 在参数数组中出现的次数
 * 如果 a[] 中的值均在 0 至 m-1 之间，返回数组中所有元素之和等于 a.length
 *
 * Created by Administrator on 2017/6/13 0013.
 */
public class Lx1_1_15 {

    public static void main(String[] args) {
        int[] a = {0,1,5,7,2,0,6,9,4,5,3,1,4};
        int[] histogram = histogram(a, 10);
        System.out.println(Arrays.toString(histogram));
    }

    public static int[] histogram(int[] a, int m) {
        int[] result = new int[m];
        for (int i = 0; i < m; i++) {
            int tmp = 0;
            for (int j: a) {
                if (i == j) {
                    tmp ++;
                }
            }
            result[i] = tmp;
        }
        return result;
    }

}
