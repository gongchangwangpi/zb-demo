package com.jdksource.jvm;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 锁的膨胀升级示例
 *
 * 无锁 -> 偏向锁 -> 轻量级锁 -> 重量级锁
 *
 * 对象的内存布局：对象头（mark word、klass pointer，数组长度）、实例数据，对象填充（8 字节的整数倍）
 *
 * Bit‐format of an object header (most significant first, big endian layout below):
 *
 *  32 bits JVM :
 *  - - - - - -
 *  hash:25 bit                             | age:4 bit | biased_lock:1 bit(0) | lock:2 bit(01) (no lock)
 *  lock biased thread:23 bit | epoch:2 bit | age:4 bit | biased_lock:1 bit(1) | lock:2 bit(01) (biased lock)
 *  lock record pointer:30 bit                                                 | lock:2 bit(00) (light lock)
 *  os mutex pointer:30 bit                                                    | lock:2 bit(10) (heavy lock)
 *  empty                                                                      | lock:2 bit(11) (gc)
 *
 *  64 bits JVM :
 *  - - - - - -
 *  unused:25 | hash:31             | unused:1 | age:4 | biased_lock:1(0) | lock:2(01) (no lock)
 *  lock biased thread:54 | epoch:2 | unused:1 | age:4 | biased_lock:1(1) | lock:2(01) (biased lock)
 *  lock record pointer:62                                                | lock:2(00) (light lock)
 *  os mutex pointer:62                                                   | lock:2(10) (heavy lock)
 *  empty                                                                 | lock:2(11) (gc)
 *
 *  JDK 1.6+，偏向锁是默认开启的
 *  开启偏向锁：-XX:+UseBiasedLocking -XX:BiasedLockingStartupDelay=0
 *  关闭偏向锁：-XX:-UseBiasedLocking
 *
 * @author: Lyu Yang
 */
public class SynchronizedUpgradeSample {

    public static void main(String[] args) throws InterruptedException {

        // sample one：无锁状态
        sampleOne();

        // --------------------------------------------------------------------------------- //

        // sample 2：偏向锁。偏向锁默认是延迟加载的，大概延迟 4 - 5 秒。

        sampleTwo();

        // sample 3：轻量级锁
        // --------------------------------------------------------------------------------- //

        sampleFour();

        // sample 5：重量级锁

        sampleFive();
    }

    private static void sampleFive() {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        CountDownLatch latch = new CountDownLatch(1);
        Object sampleFive = new Object();
        // 模拟激烈的锁竞争
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (sampleFive) {
                    System.out.println("thread-" + Thread.currentThread().getId() + " is running...");
                    // ******** ******** ******** ******** ******** ******** ******** *****010
                    // 最末三位：010，即重量级锁
                    System.out.println(ClassLayout.parseInstance(sampleFive).toPrintable());
                }
            });

            thread.start();
        }

        latch.countDown();
    }

    private static void sampleFour() {
        sampleThree();

        // sample 4：显示调用 hashCode 方法，会导致锁升级（偏向锁 -> 轻量级锁）
        // --------------------------------------------------------------------------------- //

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Object sampleFour = new Object();

        synchronized (sampleFour) {
            // mark word：00000000 00000000 00000000 00000000 00000011 01001110 00101000 00000101
            // 最末三位：101，即偏向锁
            System.out.println(ClassLayout.parseInstance(sampleFour).toPrintable());
        }

        // 显示调用 hash code 方法
        sampleFour.hashCode();

        synchronized (sampleFour) {
            // 不显式调用 hash code 方法，锁未升级！！！
            // mark word：00000000 00000000 00000000 00000000 00000011 01001110 00101000 00000101

            // mark word：00000000 00000000 00000000 00000000 00000011 00001100 11110101 11000000
            // 最末三位：000，即轻量级锁。锁升级了！！！
            System.out.println(ClassLayout.parseInstance(sampleFour).toPrintable());
        }
    }

    private static void sampleThree() {
        Object sampleThree = new Object();

        synchronized (sampleThree) {
            // mark word：00000000 00000000 00000000 00000000 00000010 10110000 11110100 11001000
            // 最末三位：000，即轻量级锁。为什么明明只有一个线程需要访问同步代码块，根本不存在竞争，却需要使用轻量级锁？
            // 这是因为 JVM 在启动时本身需要启动多个线程，且每个线程访问的目标代码中存在临界区，JVM 为了避免无谓地锁升级，直接将偏向锁延迟加载，转而使用轻量级锁。
            System.out.println(ClassLayout.parseInstance(sampleThree).toPrintable());
        }
    }

    private static void sampleTwo() {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Object sampleTwo = new Object();

        /*
        java.lang.Object object internals:
         OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
              0     4        (object header)                           05 00 00 00 (00000101 00000000 00000000 00000000) (5)
              4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
              8     4        (object header)                           e5 01 00 f8 (11100101 00000001 00000000 11111000) (-134217243)
             12     4        (loss due to the next object alignment)
        Instance size: 16 bytes
        Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

        00000000 00000000 00000000 00000000 00000000 00000000 00000000 00000101
        101，这是匿名偏向。此时还未偏向任何线程，可以简单理解为可偏向。
         */
        System.out.println(ClassLayout.parseInstance(sampleTwo).toPrintable());

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 同步代码块
        synchronized (sampleTwo) {
            // 00000000 00000000 00000000 00000000 00000011 00111100 00101000 00000101，偏向锁
            System.out.println(ClassLayout.parseInstance(sampleTwo).toPrintable());
        }   // 当临界区代码执行完毕，可能会进行锁释放（仅仅是可能。锁释放跟锁降级不是一回事，切莫混淆。锁不可能降级！！！）


        // 这里可能是无锁亦可能仍然是之前的偏向锁
        System.out.println(ClassLayout.parseInstance(sampleTwo).toPrintable());
    }

    private static void sampleOne() {
        Object sampleOne = new Object();

        /*
        java.lang.Object object internals:
         OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
              Mark Word：64 位 JVM 中对象头的标记字段占 8 个字节
              0     4        (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
              4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
              Klass Pointer：默认开启了指针压缩，所以只占 4 个字节（指向元空间中类元信息对象的指针，如 User.class）
              8     4        (object header)                           e5 01 00 f8 (11100101 00000001 00000000 11111000) (-134217243)
              对象填充：保证是 8 字节的整数倍
             12     4        (loss due to the next object alignment)
        非数组类型的对象，对象头占两个字宽（64 bits JVM 中，一个字宽代表 8 bytes）；数组类型的对象其对象头占三个字宽
        Instance size: 16 bytes
        Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

        小端模式（倒序）：00000000 00000000 00000000 00000000 00000000 00000000 00000000 00000001

        a：最末三位：001，即标识是无锁状态
        b：hash code ******** ******** ******** *0000000 00000000 00000000 00000000 ...
        对象的 hash code 有一个懒初始化的过程。因此，新建对象的对象头的 mark word 中用以标识 hash code 的 bit 可能会如下所示（64 位的 JVM），
          是 ******** ******** ******** *0000000 00000000 00000000 00000000 ...，即 hash code 此刻尚未完成初始化
         */
        System.out.println(ClassLayout.parseInstance(sampleOne).toPrintable());

        System.out.println("hash code = " + sampleOne.hashCode());

        // mark word：00000000 00000000 00000000 00100111 01110000 01010000 11011100 00000001
        System.out.println(ClassLayout.parseInstance(sampleOne).toPrintable());
    }

}
