package com.books.tjsjjg.d2;

import java.util.Arrays;

/**
 * 右上三角矩阵
 * 一种对角线以下的元素皆为0的 n * n 的矩阵
 * 1 2 3 4 5
 * 0 1 2 3 4
 * 0 0 1 2 3
 * 0 0 0 1 2
 * 0 0 0 0 1
 *
 * 可以转换为一个一维矩阵，分为以行为主和以列为主
 * 以行为主： 1 2 3 4 5 1 2 3 4 1 2 3 1 2 1
 * 以列为主： 1 2 1 3 2 1 4 3 2 1 5 4 3 2 1
 *
 * Created by Administrator on 2017/7/6 0006.
 */
public class CH02_06 {

    public static void main(String[] args) {
        int[][] arr = {
                {11, 22, 33, 44, 55},
                {0,  55, 66, 77, 88},
                {0,   0, 88, 99, 11},
                {0,   0,  0, 11, 22},
                {0,   0,  0,  0,  22}
        };

        row(arr);
        col(arr);

    }

    public static int[] row(int[][] arr) {
        int length = arr.length;
        int[] res = new int[(length * (1 + length) / 2)];
        int idx = 0;
        System.out.println("========= 输入数组 ========");
        for (int i = 0; i < length; i++) {
            int[] tmp = arr[i];
            for (int j = 0; j < length; j++) {
                int i1 = tmp[j];
                System.out.print("\t" + i1);
                if (i1 != 0) {
                    res[idx] = i1;
                    idx++;
                }
            }
            System.out.println();
        }

        System.out.println("========= 输出数组 ========");
        System.out.println(Arrays.toString(res));
        return res;
    }

    public static int[] col(int[][] arr) {
        int length = arr.length;
        int[] res = new int[(length * (1 + length) / 2)];
        int idx = 0;
        System.out.println("========= 输入数组 ========");
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                System.out.print("\t" + arr[i][j]);
                int k = arr[j][i];
                if (k != 0) {
                    res[idx] = k;
                    idx++;
                }
            }
            System.out.println();
        }
        System.out.println("========= 输出数组 ========");
        System.out.println(Arrays.toString(res));
        return res;
    }

}
