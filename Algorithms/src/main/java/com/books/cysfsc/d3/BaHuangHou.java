package com.books.cysfsc.d3;

import java.util.Arrays;

/**
 * 国际象棋中的八皇后问题
 * 棋盘为 8 * 8的格子，要在棋盘上摆8个皇后，
 * 每个皇后都不能在同一直线和斜线上
 * 
 * 1-0-0-0-0-0-0-0
 * 0-0-1-0-0-0-0-0
 * 0-0-0-0-1-0-0-0
 * 
 * 
 * @author zhangbo
 */
public class BaHuangHou {
    
    private static int[][] arr = new int[8][8];

    public static void main(String[] args) {
        // TODO

        
        for (int i = 0; i < arr.length; i++) {
            System.out.println(Arrays.toString(arr[i]));
        }
        
    }
    
    // 0表示初始化，1表示皇后位置，-1表示冲突，不能被占用
    private static void cal(int[][] arr) {

        out:
        for (int i = 0; i < arr.length; i++) {
            int[] row = arr[i];

            for (int j = 0; j < row.length; j++) {
                row[j] = 1;
                
                
                
            }


        }
        
        
    }
    
}
