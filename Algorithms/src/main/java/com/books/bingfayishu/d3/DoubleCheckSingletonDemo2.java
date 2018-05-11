package com.books.bingfayishu.d3;

/**
 * 双重检查，原因在于 new DoubleCheckSingletonDemo() 分3步执行
 * 1.分配内存
 * 2.初始化对象
 * 3.将instance指向分配的内存区域
 * 
 * 此处将instance声明为volatile，由jvm插入内存屏障来保证1.2.3.顺序执行
 * 
 * @author zhangbo
 */
public class DoubleCheckSingletonDemo2 {
    
    // 
    private static volatile DoubleCheckSingletonDemo2 instance;
    
    public static DoubleCheckSingletonDemo2 getInstance() {
        if (instance == null) {
            synchronized (DoubleCheckSingletonDemo2.class) {
                if (instance == null) {
                    instance = new DoubleCheckSingletonDemo2();
                }
            }
        }
        return instance;
    }
    
    public static void main(String[] args) {
        
        
        
    }
    
}
