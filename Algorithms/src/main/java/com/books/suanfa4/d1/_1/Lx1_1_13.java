package com.books.suanfa4.d1._1;

import java.util.Arrays;

/**
 * 练习 1.1.13  Page 34
 *
 * 打印出一个M行N列的二维数组的转置
 * 交换行和列
 *
 * Created by Administrator on 2017/6/13 0013.
 */
public class Lx1_1_13 {

    public static void main(String[] args) {
        int[][] arr = {{1,2,3,4}, {4,5,6,7}, {7,8,9,0}};
        int[][] swap = swap(arr);
        for (int[] a: swap) {
            System.out.println(Arrays.toString(a));
        }
    }

    private static int[][] swap(int[][] arr) {
        int row = arr.length;
        int col = arr[0].length;

        int[][] tmp = new int[col][row];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                tmp[j][i] = arr[i][j];
            }
        }
        return tmp;
    }

}
