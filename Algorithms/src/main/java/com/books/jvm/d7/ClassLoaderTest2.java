package com.books.jvm.d7;

import java.io.InputStream;

/**
 * Page 228
 * 同一份class文件，由不同的ClassLoader加载，在JVM中是不同的
 * 对于任意一个类，都由 加载它的类加载器 和 这个类本身 来共同确定其在JVM中的唯一性
 *
 * Created by Administrator on 2017/5/2 0002.
 */
public class ClassLoaderTest2 {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        ClassLoader classLoader = new ClassLoader() {
            @Override
            public Class<?> findClass(String name) throws ClassNotFoundException {
                try {
                    String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
                    InputStream is = getClass().getResourceAsStream(fileName);
                    if (is == null) {
                        return super.loadClass(name);
                    }
                    byte[] bytes = new byte[is.available()];
                    is.read(bytes);
                    return defineClass(name, bytes, 0, bytes.length);
                } catch (Exception e) {
                    throw new ClassNotFoundException(name);
                }
            }
            @Override
            public String toString() {
                return "my custom classLoader";
            }
        };

        Class<?> loadClass = classLoader.loadClass("java.lang.Object");
        Object obj = loadClass.newInstance();
        
        System.out.println(obj.getClass());
        // null 说明Object类最终还是由Bootstrap类加载器加载的
        System.out.println(obj.getClass().getClassLoader());
    }

}
