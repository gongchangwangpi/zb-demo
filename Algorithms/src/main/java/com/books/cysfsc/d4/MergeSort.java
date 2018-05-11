package com.books.cysfsc.d4;

import java.util.Arrays;

/**
 * 合并排序
 *
 * 将有N个数据的数组，看成是N个长度为1有序子表，
 * 然后将他们两两合并，得到若干长度为2的有序子表，
 * 在重复两两合并，一直到最后，得到长度为N的有序子表，完成排序过程
 * 
 * 需要较大的内存空间，通常为原始数组的一倍
 * 
 * @author zhangbo
 */
public class MergeSort {

    public static void main(String[] args) {
        
        int[] arr = {123,58,89,264,5,45,3,99};

        System.out.println("堆排序之前的数据：" + Arrays.toString(arr));

        mergeSort(arr);

        System.out.println("堆排序之后的数据：" + Arrays.toString(arr));
    }

    private static void mergeSort(int[] a) {
        int n = a.length;
        int count = 0; // 排序步骤
        int f = 0; // 标志
        int len = 1; // 有序子表的长度
        int h; // 
        
        int[] p = new int[n];
        while (len < n) {
            // 交替在原数组a和辅助数组p之间合并
            if (f == 1) {
                mergeOne(p, a, n, len); // 将p合并到a
            } else {
                mergeOne(a, p, n, len); // 将a合并到p
            }
            len = len * 2; // 增加有序序列的长度
            f = 1 - f; // 使 f 在 0 1 之间交替
            
            count++;
            System.out.println("第 " + count + " 次排序结果为：" + Arrays.toString(a));
        }
        
        if (f == 1) { // 如果进行了排序
            // 将p中的数据复制回a
            System.arraycopy(p, 0, a, 0, p.length);
        }
        
    }

    private static void mergeOne(int[] a, int[] b, int n, int len) {
        
        int i,j,k,s,e;
        s = 0;
        while (s + len < n) {
            e = s + 2 * len - 1;
            if (e >= n) { // 最后一段可能少于len个节点
                e = n - 1;
            }
            // 相邻两段进行核保
            k = i = s;
            j = s + len;
            while (i < s + len && j <= e) {
                if (a[i] <= a[j]) {
                    b[k++] = a[i++];
                } else {
                    b[k++] = a[j++];
                }
            }
            while (i < s + len) {
                b[k++] = a[i++];
            }
            while (j <= e) {
                b[k++] = a[j++];
            }
            s = e +1;
        }
        
        if (s < n) {
            for (; s < n; s++) {
                b[s] = a[s];
            }
        }
    }
    
}
