package com.books.gaobingfa.d3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 重入锁和Condition
 *
 * 在Condition.signal()方法调用时，也要求线程先获得相关的锁。
 * 在signal()方法调用后，系统会从当前Condition对象的等待队列中，唤醒一个线程。
 * 一旦线程被唤醒，它会重新尝试获得与之绑定的重入锁，一旦成功获取，就可以继续执行了。
 * 因此，在signal()方法调用之后，一般需要释放相关的锁，谦让给被唤醒的线程，让它可以继续执行。
 * 比如，在本例中，第48行代码就释放了重入锁，如果省略第48行，那么，虽然已经唤醒了线程t1，
 * 但是由于它无法重新获得锁，因而也就无法真正的继续执行。
 *
 * Created by books on 2017/4/20.
 */
public class ReenterLockConditionTest implements Runnable {

    static ReentrantLock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();

    @Override
    public void run() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread() + "await");
            condition.await();
            System.out.println(Thread.currentThread() + "go");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new ReenterLockConditionTest());

        thread.start();

        Thread.sleep(200);

        lock.lock();
        // condition所在的线程,必须获取对应lock的锁,不然会IllegalMonitorStateException
        // condition必须在lock.lock()和lock.unlock()之间,保证锁被释放
        condition.signal();
        lock.unlock();
        System.out.println("main end");
    }
}
