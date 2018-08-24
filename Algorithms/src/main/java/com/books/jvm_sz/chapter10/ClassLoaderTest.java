package com.books.jvm_sz.chapter10;

/**
 * @author zhangbo
 */
public class ClassLoaderTest {

    public static void main(String[] args) {

        System.out.println("String ClassLoader is : " + String.class.getClassLoader());

        ClassLoader classLoader = ClassLoaderTest.class.getClassLoader();
        while (classLoader != null) {
            System.out.println(classLoader);
            classLoader = classLoader.getParent();
        }
        
    }
    
}
