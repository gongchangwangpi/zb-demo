package com.books.bingfayishu.d5;

/**
 * 读写锁状态
 * 
 * 一个32位的整型变量，高16位表示读锁状态，低16位表示写锁状态
 * 
 * @author zhangbo
 */
public class ReentrantReadWriteLockState {

    static final int SHARED_SHIFT   = 16;
    static final int SHARED_UNIT    = (1 << SHARED_SHIFT);
    static final int MAX_COUNT      = (1 << SHARED_SHIFT) - 1;
    static final int EXCLUSIVE_MASK = (1 << SHARED_SHIFT) - 1;
    
    public static void main(String[] args) {
        
        int b = 65539;

        // 抹去高16位数（读锁）,即写锁的状态
        System.out.println(b & 0x0000FFFF);
        // 抹去低16位数（写锁），无符号右移16位，即读锁的状态
        System.out.println(b >>> 16);

        System.out.println(1 & EXCLUSIVE_MASK);
    }
    
}
