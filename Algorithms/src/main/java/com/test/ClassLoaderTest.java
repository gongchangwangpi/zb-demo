package com.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * ClassLoader测试
 *
 * 注意测试时，需要将加载的class文件及源文件在本项目中删除
 * 不然根据双亲委托原则，一般由 sun.misc.Launcher$AppClassLoader加载的
 *
 * Created by books on 2017/4/1.
 */
public class ClassLoaderTest {

    public static void main(String[] args) throws Exception {

        MyClassLoader myClassLoader = new MyClassLoader("E:/test/");
        Class<?> clazz = myClassLoader.loadClass("com.test.T");
        Class<?> clazz2 = myClassLoader.loadClass("com.test.TT");

        Object obj = clazz.newInstance();

        Method helloMethod = clazz.getDeclaredMethod("hello", null);

        helloMethod.invoke(obj, null); //我是由class com.test.ClassLoaderTest$MyClassLoader加载的
    }

    /**
     * 自定义类加载器
     */
    static class MyClassLoader extends ClassLoader {
        private String path;

        public MyClassLoader(String path) {
            this.path = path;
        }

        private byte[] loadByte(String name) throws IOException {
            name = name.replaceAll("\\.", "/");
            FileInputStream inputStream = new FileInputStream(path + name + ".class");
            int len = inputStream.available();
            byte[] bytes = new byte[len];

            inputStream.read(bytes);

            inputStream.close();

            return bytes;
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            try {
                byte[] bytes = loadByte(name);
                return defineClass(name, bytes, 0, bytes.length);
            } catch (Exception e) {
                throw new ClassNotFoundException();
            }
        }
    }

}
