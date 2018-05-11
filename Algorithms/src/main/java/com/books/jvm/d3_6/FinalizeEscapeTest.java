package com.books.jvm.d3_6;

import com.books.bingfayishu.d4.SleepUtils;

/**
 * 在GC时，会判断是否执行该对象的finalize()方法，如果该方法没有被覆盖或者已经被执行过了，
 * 那么就不会再执行，一个对象的finalize()最多会被执行一次
 * 
 * @author zhangbo
 */
public class FinalizeEscapeTest {
    
    private static FinalizeEscapeTest GC_ESCAPE = null;

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize");
        
        GC_ESCAPE = this;
    }

    public static void main(String[] args) {

        GC_ESCAPE = new FinalizeEscapeTest();
        GC_ESCAPE = null;
        
        System.gc();
        // gc优先级较低，等待1秒后再判断
        SleepUtils.second(1);
        
        if (GC_ESCAPE == null) {
            System.out.println("Yes, GC success");
        } else {
            System.out.println("No, ...");
        }

        GC_ESCAPE = null;
        
        System.gc();
        // gc优先级较低，等待1秒后再判断
        SleepUtils.second(1);

        if (GC_ESCAPE == null) {
            System.out.println("Yes, GC success");
        } else {
            System.out.println("No, ...");
        }
        
    }
    
}
