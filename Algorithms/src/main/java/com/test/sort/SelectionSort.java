package com.test.sort;

import java.util.Arrays;

/**
 * @author zhangbo
 */
public class SelectionSort {

    public static void main(String[] args) {
        
        int[] arr = {78, 12, 89, 44, 5, 159, 852, 1, 100};
        
        selectionSort(arr);

        System.out.println(Arrays.toString(arr));
        
    }
    
    public static void selectionSort(int[] arr) {
        int len = arr.length;
        int minIndex;

        for (int i = 0; i < len; i++) {
            minIndex = i;
            for (int j = i + 1; j < len; j++) {
                if (arr[minIndex] > arr[j]) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                int tem = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = tem;
            }
        }
    }
    
}
