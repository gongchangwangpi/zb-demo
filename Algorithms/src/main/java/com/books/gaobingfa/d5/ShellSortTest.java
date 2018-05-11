package com.books.gaobingfa.d5;

import java.util.Arrays;

/**
 * 希尔排序
 *
 * Created by books on 2017/5/2.
 */
public class ShellSortTest {

    public static void main(String[] args) {

        int[] arr = {123,11,1,89,999,44,8,-1,23,345};
        shellSort(arr);
        System.out.println(Arrays.toString(arr));

    }

    /**
     * 也是一种插入排序
     * 每次比较交换相隔 h 的值
     * 主要优点是，即使一个较小的元素在数组的末尾，由于每次元素移动都以h为间隔进行，
     * 因此数组末尾的小元素可以在很少的交换次数下，就被置换到最接近元素最终位置的地方。
     *
     * @param arr
     */
    public static void shellSort(int[] arr) {
        int h = 1;
        int length = arr.length;
        // 计算出最大的 h 值
        while (h <= length / 3) {
            h = h * 3 + 1;
        }

        while (h > 0) {
            for (int i = h; i < length; i++) {
                if (arr[i] < arr[i - h]) {
                    int tmp = arr[i];
                    int j = i - h;

                    while (j >= 0 && arr[j] > tmp) {
                        arr[j + h] = arr[j];
                        j -= h;
                    }
                    arr[j + h] = tmp;
                }
            }
            // 计算下一次的 h 值
            h = (h - 1) / 3;
        }
    }
}
