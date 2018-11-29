package com.jdksource.java8;

/**
 * 只能有一个不是继承来的抽象方法
 * 
 * @author zhangbo
 */
@FunctionalInterface
public interface FirstFunctionalInterface {
    
    boolean test();
    
    // 添加多余的抽象方法,会报错
//    int add();
    
    // 此方法为Object中的继承来的方法
    boolean equals(Object obj);
    
    // 默认方法
    default String say() {
        return "hello";
    }
    
}
