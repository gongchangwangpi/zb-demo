package com.test.geek.jvm;

/**
 * -XX:+UnlockDiagnosticVMOptions -XX:+PrintBiasedLockingStatistics -XX:TieredStopAtLevel=1
 * 
 * // step1
 * -XX:+UseBiasedLocking
 * 
 * 1.通过UseBiasedLocking查看开关偏向锁时的输出
 * 2.在main方法钱调用 hashCode 查看输出
 * 3.覆写Lock.hashCode 方法查看输出
 * 4.在main方法钱调用  System.identityHashCode() 查看输出
 * 
 * 
 * @author zhangbo
 */
public class SynchronizedTest {

    static Lock lock = new Lock();
    static int counter = 0;
    
    public static void main(String[] args) {
        
        lock.hashCode(); // step 2
        System.identityHashCode(lock); // step 4
        
        for (int i = 0; i < 1_000_000; i++) {
            foo();
        }
        
    }
    
    private static void foo() {
        synchronized (lock) {
            counter++;
        }
    }
    
    static class Lock {
        // step 3
        @Override
        public int hashCode() {
            return 0;
        }
    }
}
