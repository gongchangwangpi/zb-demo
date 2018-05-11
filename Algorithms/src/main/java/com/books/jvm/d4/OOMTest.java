package com.books.jvm.d4;

import java.util.ArrayList;
import java.util.List;

import com.books.bingfayishu.d4.SleepUtils;

/**
 * 设置虚拟机参数为：
 * -Xms100m -Xmx100m -XX:+UseSerialGC
 * 
 * 然后运行jconsole，查看堆内存情况
 * 
 * @author zhangbo
 */
public class OOMTest {

    public static void main(String[] args) {

        List<T> list = new ArrayList<>(1000);
        
        for (int i = 0; i < 1000; i++) {
            SleepUtils.millis(100);
            list.add(new T());
        }
        
        System.gc();
        
    }
    
    private static class T {
        // 每个对象默认分配64K内存
        private byte[] b = new byte[64 * 1024];
    }
}
