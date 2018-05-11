package com.books.cysfsc.d4;

import java.util.Arrays;

/**
 * 快速排序
 * 和冒泡排序类似，基于交换排序思想，
 * 对冒泡排序做了改进，有更高的执行效率
 * 
 * 
 * 
 * 
 * @author zhangbo
 */
public class QuickSort {

    public static void main(String[] args) {

        int[] arr = {123, 58, 89, 264, 5, 45, 3, 99};

        System.out.println("排序之前排序结果为：" + Arrays.toString(arr));

        quickSort(arr, 0, arr.length - 1);

        System.out.println("排序之后排序结果为：" + Arrays.toString(arr));
    }
    
    private static void quickSort(int[] arr, int leftIndex, int rightIndex) {
        int f, temp;
        int lTempIndex = leftIndex;
        int rTempIndex = rightIndex;
        // 分界值
        f = arr[(lTempIndex + rTempIndex) / 2];
        
        while (lTempIndex < rTempIndex) {
            while (arr[lTempIndex] < f) {
                lTempIndex++;
            }
            while (arr[rTempIndex] > f) {
                rTempIndex--;
            }
            if (lTempIndex <= rTempIndex) {
                temp = arr[lTempIndex];
                arr[lTempIndex] = arr[rTempIndex];
                arr[rTempIndex] = temp;
                rTempIndex--;
                lTempIndex++;
            }
        }
        if (lTempIndex == rTempIndex) {
            lTempIndex++;
        }
        if (leftIndex < rTempIndex) {
            // 递归调用
            quickSort(arr, leftIndex, lTempIndex - 1);
        }
        if (rightIndex > lTempIndex) {
            // 递归调用
            quickSort(arr, rTempIndex + 1, rightIndex);
        }
    }
}
