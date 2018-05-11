package com.books.tjsjjg.d2;

import java.util.Arrays;

/**
 * 稀疏矩阵        Page 47
 *
 * 一个矩阵中大部分的元素为0, 使用传统二维数组表示，很占用内存空间
 * 因此改进，利用三项式（3-tuple）的数据结构
 * 如：
 * 【12, 0， 0, 22】
 * 【0， 33, 0,  0】
 * 【0， 0，55，77】 来表示的传统二维数组
 * 可以用压缩后的三项式
 * 【3， 4， 5】
 * 【1， 1，12】
 * 【1， 4, 22】
 * 【2， 2，33】
 * 【3， 3, 55】
 * 【3， 4, 77】来表示
 * 三项式的第一行第一个数据A(0,1)表示稀疏矩阵的行数
 * A(0,2) 表示稀疏矩阵的列数， A(0, 3) 表示稀疏矩阵中有不为0的元素的个数
 * 后面的每行分别表示 第几行，第几列的非零元素的值为A(i, 3);
 *
 * Created by Administrator on 2017/7/5 0005.
 */
public class CH02_05 {

    public static void main(String[] args) {
        int[][] arr = {
            {12, 0, 0, 22},
            {0, 33, 0, 0},
            {0, 0, 55, 77}
        };

        int[][] compress = compress(arr);

        for (int[] a: arr) {
            System.out.println(Arrays.toString(a));
        }
        for (int[] a: compress) {
            System.out.println(Arrays.toString(a));
        }
    }

    /**
     * 将传统的稀疏矩阵转换为压缩后的三项式
     *
     * @param arr
     * @return
     */
    public static int[][] compress(int[][] arr) {
        if (arr == null) {
            throw new NullPointerException("array must not be null");
        }
        int count = 0;
        for (int[] tmp: arr) {
            for (int i: tmp) {
                if (i != 0) {
                    count ++;
                }
            }
        }

        int[][] res = new int[count + 1][3];
        res[0][0] = arr.length;
        res[0][1] = arr[0].length;
        res[0][2] = count;

        int row = 1;
        for (int i = 0; i < arr.length; i ++) {
            int[] tmp = arr[i];
            for (int j = 0; j < tmp.length; j ++) {
                int k = tmp[j];
                if (k != 0) {
                    res[row][0] = i;
                    res[row][1] = j;
                    res[row][2] = k;
                    row++;
                }
            }
        }

        return res;
    }

}
