package com.books.cysfsc.d4;

import java.util.Arrays;

/**
 * 堆排序 二叉树结构见Page 68 
 * 完全二叉树：除最底层节点外，所有节点都有左右子树，且最底层的节点按照从左到右的顺序连续存在
 * 
 * 1. 构造一个完全二叉树，父节点的数据必须大于等于子节点的数据
 * 2. 堆排序输出
 * 2.1 将根节点(最大数)放在数组最后一位，最后一位和根节点交换
 * 2.2 此时，二叉树结构破坏，需要重新调整
 * 2.3 调整后的根节点又是剩下数据中最大的，在重复2.1，以此类推
 *
 * 
 * @author zhangbo
 */
public class HeapSort {
    
    public static void main(String[] args) {

        int[] arr = {123,58,89,264,5,45,3,99};
        
        System.out.println("堆排序之前的数据：" + Arrays.toString(arr));

        heapSort(arr);

        System.out.println("堆排序之后的数据：" + Arrays.toString(arr));
    }

    private static void heapSort(int[] a) {
        int n = a.length;
        int temp, j;
        for (int i = n/2-1; i >= 0 ; i--) {
            while (2*i+1 < n) { // 第i个节点有右子树
                j = 2 * i + 1;
                if (j + 1 < n) {
                    if (a[j] < a[j + 1]) { // 右子树小于左子树，需要比较右子树
                        j++; // 序号增加1，指向右子树
                    }
                }
                if (a[i] < a[j]) { //  比较序号为i,j的数据
                    temp = a[i]; // 交换数据
                    a[i] = a[j];
                    a[j] = temp;
                    i = j; // 堆被破坏，需要调整
                } else {
                    break; // 比较左右子节点均大，则堆未被破坏，无需调整
                }
            }
        }

        System.out.println("组成的二叉树结构：" + Arrays.toString(a));

        for (int i = n - 1; i >= 0; i--) {
            temp = a[0]; // 先将根节点(即最大的数)放在数组最后一个
            a[0] = a[i];
            a[i] = temp;
            
            int k = 0;
            while (2*k + 1 < i) {
                j = 2 * k + 1;
                if (j + 1 < i) {
                    if (a[j] <a[j+1]) {
                        j++;
                    }
                }
                if (a[k] < a[j]) {
                    temp = a[k];
                    a[k] = a[j];
                    a[j] = temp;
                    k = j;
                } else {
                    break;
                }
            }

            System.out.println("第N步调整后数据：" + Arrays.toString(a));
        }
        
    }
}
