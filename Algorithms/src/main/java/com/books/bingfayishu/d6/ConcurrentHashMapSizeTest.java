package com.books.bingfayishu.d6;

/**
 * @author zhangbo
 */
public class ConcurrentHashMapSizeTest {

    private static final int MAXIMUM_CAPACITY = 1 << 30;
    static final int MOVED     = -1; // hash for forwarding nodes
    static final int TREEBIN   = -2; // hash for roots of trees
    static final int RESERVED  = -3; // hash for transient reservations
    static final int HASH_BITS = 0x7fffffff; // usable bits of normal node hash
    static final int RESIZE_STAMP_BITS = 16; // 

    public static void main(String[] args) {
//        System.out.println(tableSizeFor(6));

        int n = 102401;
        
        System.out.println(n |= n >>> 1);
        System.out.println(n |= n >>> 2);
        System.out.println(n |= n >>> 4);
        System.out.println(n |= n >>> 8);
        System.out.println(n |= n >>> 16);

        System.out.println(spread("dfee".hashCode()));

        System.out.println(resizeStamp(16));
        
    }

    static final int resizeStamp(int n) {
        return Integer.numberOfLeadingZeros(n) | (1 << (RESIZE_STAMP_BITS - 1));
    }
    
    private static final int tableSizeFor(int c) {
        int n = c - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    static final int spread(int h) {
        return (h ^ (h >>> 16)) & HASH_BITS;
    }
    
}
