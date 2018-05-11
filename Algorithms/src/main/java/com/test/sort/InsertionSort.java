package com.test.sort;

import java.util.Arrays;

/**
 * @author zhangbo
 */
public class InsertionSort {

    public static void main(String[] args) {

        int[] arr = {78, 12, 89, 44, 5, 159, 852, 1, 100};

        insertionSort(arr);

        System.out.println(Arrays.toString(arr));
        
    }
    
    public static void insertionSort(int[] arr) {
        int len = arr.length;

        for (int i = 0; i < len; i++) {
            int tem = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > tem) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = tem;
        }
    }
}
