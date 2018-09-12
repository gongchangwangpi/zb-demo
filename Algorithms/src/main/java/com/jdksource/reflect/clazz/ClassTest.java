package com.jdksource.reflect.clazz;

import java.lang.reflect.Constructor;

/**
 * @author zhangbo
 */
public class ClassTest {

    public static void main(String[] args) {

        boolean numFormObj = Object.class.isAssignableFrom(Number.class);
        System.out.println(numFormObj);
        
        boolean intFromNum = Number.class.isAssignableFrom(Integer.class);
        System.out.println(intFromNum);

        boolean interFromObj = Object.class.isAssignableFrom(I.class);
        System.out.println(interFromObj);
        
        boolean objFromObj = Object.class.isAssignableFrom(Object.class);
        System.out.println(objFromObj);

        boolean implFormI = I.class.isAssignableFrom(Impl.class);
        System.out.println(implFormI);

        // ----- 数组 ----- //
        Class<?> componentType = int[].class.getComponentType();
        System.out.println(componentType);
        // 数组，原始类型，Void.class返回长度为0的构造器数组
        Constructor<?>[] constructors = int[].class.getConstructors();
        System.out.println(constructors);
    }
    
    static interface I {
        
    }
    static class Impl implements I {
        
    }
}
