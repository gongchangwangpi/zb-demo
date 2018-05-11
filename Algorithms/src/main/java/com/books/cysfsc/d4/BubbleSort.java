package com.books.cysfsc.d4;

import java.util.Arrays;

/**
 * 冒泡排序
 * 依次比较相邻的两个数，前面的数大于后面的数，则交换
 * 
 * 执行步骤较长，效率不高
 * 
 * @author zhangbo
 */
public class BubbleSort {

    public static void main(String[] args) {
        
        int[] arr = {123,58,89,264,5,45};
        
        bubbleSort(arr);

        System.out.println(Arrays.toString(arr));
    }
    
    private static void bubbleSort(int[] arr) {
        int temp;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j +1] = temp;
                }
            }
            System.out.println("第 " + i + " 次排序结果为：" + Arrays.toString(arr));
        }
    }
}
