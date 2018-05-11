package com.books.cysfsc.d3;

/**
 * 鸡兔同笼，穷举法
 * 共有35个头，94只脚，求鸡兔各多少
 * 
 * @author zhangbo
 */
public class JiTuTongLongTest1 {

    public static void main(String[] args) {
        
        qiongJu(35, 94);
        
    }
    
    private static void qiongJu(int head, int foot) {
        int headJ = 0;
        for (; headJ < head; headJ++) {
            int headT = head - headJ;
            
            if (headJ * 2 + headT * 4 == foot) {
                System.out.println("鸡有： " + headJ);
                System.out.println("兔有： " + headT);
                break;
            }
            
        }
        
    }
}
