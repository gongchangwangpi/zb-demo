package com.books.jvm_sz.chapter10;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import com.books.bingfayishu.d4.SleepUtils;

/**
 * 启动main方法后，修改 {@link HotSweepTarget}源码，在命令行重新手动编译javac xxx
 * 然后观察控制台打印情况
 * 
 * @author zhangbo
 */
public class HotSweepTest {

    public static void main(String[] args) throws Exception {
        
        while (true) {
            MyClassLoader loader = new MyClassLoader();
            Class<?> clz = loader.loadClass("com.books.jvm_sz.chapter10AAAHotSweepTarget");

            Object instance = clz.newInstance();

            Method method = clz.getDeclaredMethod("hot", null);
            method.invoke(instance, null);

            SleepUtils.second(5);
        }
        
    }
    
    static class MyClassLoader extends ClassLoader {
        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {

            name = name.replaceAll("AAA", ".");
            String path = "E:\\workspaceIDEA\\zb-demo\\Algorithms\\src\\main\\java\\com\\books\\jvm_sz\\chapter10\\HotSweepTarget.class";
            
            
            try (FileInputStream fis = new FileInputStream(path)) {
                FileChannel channel = fis.getChannel();

                ByteBuffer byteBuffer = ByteBuffer.allocate(fis.available());
                
                channel.read(byteBuffer);

                byte[] bytes = byteBuffer.array();

                return defineClass(name, bytes, 0, bytes.length);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return super.findClass(name);
        }
    }
}
