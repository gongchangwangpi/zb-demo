package com.test.geek.javacore36;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

/**
 * @author zhangbo
 */
public class PhantomReferenceTest {

    public static void main(String[] args) throws InterruptedException {
        
        Object obj = new Object();

        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();

        PhantomReference<Object> phantomReference = new PhantomReference<>(obj, referenceQueue);
        // 去除强引用，仅保留虚引用
        obj = null;
        
        System.out.println(phantomReference.isEnqueued());
        System.out.println(phantomReference.enqueue());

        System.gc();

        System.out.println(phantomReference.isEnqueued());
        System.out.println(phantomReference.enqueue());
        
        Reference<?> reference = referenceQueue.remove(5000);
        
        System.out.println(reference);
        
        System.out.println(reference.isEnqueued());
        System.out.println(reference.enqueue());
        System.out.println(reference.get());

    }
    
}
