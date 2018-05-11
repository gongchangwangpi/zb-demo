package com.books.bingfayishu.d4;

/**
 * @author zhangbo
 */
public class Synchronized {

    public static void main(String[] args) {
        
        synchronized (Synchronized.class) {
            
        }
        
        get();
    }

    public synchronized static void get() {

    }
    
}
