package com.books.javap;

/**
 * @author zhangbo
 */
public class SynchronizedTest {

    public static void main(String[] args) {
        // synchronized 代码块由monitorenter和moniterexit指令包围
        synchronized (new Object()) {
            System.out.println("block");
        }
        say();
        
//        9: monitorenter
//        10: getstatic     #3                  // Field java/lang/System.out:Ljava/io/PrintStream;
//        13: ldc           #4                  // String block
//        15: invokevirtual #5                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
//        18: aload_1
//        19: monitorexit
//        20: goto          28

    }
    
    // ========== 
    // synchronized方法则由虚拟机判断是否有ACC_SYNCHRONIZED标记的flags,
    // 由虚拟机自动加锁和释放锁

//    private static synchronized void say();
//    descriptor: ()V
//    flags: ACC_PRIVATE, ACC_STATIC, ACC_SYNCHRONIZED
//    Code:
//    stack=2, locals=0, args_size=0
//            0: getstatic     #3                  // Field java/lang/System.out:Ljava/io/PrintStream;
//            3: ldc           #7                  // String synchronized say
//            5: invokevirtual #5                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
//            8: return
//    LineNumberTable:
//    line 15: 0
//    line 16: 8

    private static synchronized void say() {
        System.out.println("synchronized say");
    }
}
