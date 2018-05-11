package com.books.cysfsc.d4;

import java.util.Arrays;

/**
 * 选择排序法
 * 每次选择一个最小的数，将它和第1个数交换
 * 第二次在和第二个数交换，以此类推
 * 
 * 执行步骤较长，效率不高
 * 
 * @author zhangbo
 */
public class SelectSort {

    public static void main(String[] args) {
        int[] arr = {123,58,89,264,5,45};
        System.out.println("排序之前排序结果为：" + Arrays.toString(arr));

        selectSort(arr);

    }
    
    private static void selectSort(int[] arr) {
        int temp, minIndex;
        for (int i = 0; i < arr.length; i++) {
            minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIndex]) {
                    // 查找最小数的索引
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
            }
            System.out.println("第 " + i + " 次排序结果为：" + Arrays.toString(arr));
        }
    }
}
