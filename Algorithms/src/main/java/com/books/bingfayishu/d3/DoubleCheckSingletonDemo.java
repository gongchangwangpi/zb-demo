package com.books.bingfayishu.d3;

/**
 * 双重检查是错误的，原因在于 new DoubleCheckSingletonDemo() 分3步执行
 * 1.分配内存
 * 2.初始化对象
 * 3.将instance指向分配的内存区域
 * 
 * 由于处理器可能会存在指令重排，导致 2.3.的顺序不确定，实际执行可能会是 1.3.2
 * 此时，在19行判断instance不为null，但真正的instance还没有完成初始化
 * 只是指向分配的内存空间而已
 * 
 * @author zhangbo
 */
public class DoubleCheckSingletonDemo {

    private static DoubleCheckSingletonDemo instance;
    
    public static DoubleCheckSingletonDemo getInstance() {
        if (instance == null) {
            synchronized (DoubleCheckSingletonDemo.class) {
                if (instance == null) {
                    instance = new DoubleCheckSingletonDemo();
                }
            }
        }
        return instance;
    }
    
    public static void main(String[] args) {
        
        
        
    }
    
}
