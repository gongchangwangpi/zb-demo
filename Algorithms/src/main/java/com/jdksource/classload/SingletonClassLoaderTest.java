package com.jdksource.classload;

/**
 * 初始化某个类型的数组，会加载该类型的class，但不会执行clinit，不会初始化该类
 * 
 * -verbose:class
 * 
 * @author zhangbo
 */
public class SingletonClassLoaderTest {

    private SingletonClassLoaderTest() {} 
    
    private static class LazyHolder {
        static final SingletonClassLoaderTest instance = new SingletonClassLoaderTest();
        static {
            System.out.println("LazyHolder <clinit>");
        }
    }
    
    public static Object get(boolean flag) {
        if (flag) {
            // 如为true，此时新建LazyHolder数组，会加载LazyHolder类，但不会初始化
            return new LazyHolder[2];
        }
        return LazyHolder.instance;
    }

    public static void main(String[] args) {
        
        get(true);
//        get(false);
        
    }
    
}
