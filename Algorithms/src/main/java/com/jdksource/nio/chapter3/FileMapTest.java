package com.jdksource.nio.chapter3;

import java.io.FileInputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * {@link FileChannel.MapMode#READ_ONLY} 要求打开该文件通道时具有读或者读写权限
 * {@link FileChannel.MapMode#READ_WRITE} 要求打开该文件通道时具有读权限
 * {@link FileChannel.MapMode#PRIVATE} 要求打开该文件通道时具有读写权限
 * 文件内容的修改，都会映射到该内存缓冲区，直到PRIVATE的缓冲区修改了该内存缓冲区的拷贝
 * 
 * PRIVATE模式，在put(修改)该缓冲区内容时，会copy一份内容出来，所以在修改只会，
 * 该PRIVATE缓冲区的修改不会影响文件本身的内容，文件内容本身的修改也不会再映射到该缓冲区
 * 
 * 使用PRIVATE映射的内存缓冲区的修改，不会真的改动底层的文件
 * 只会改动该内存缓冲区中的内容，通常用于写时复制(copy-on-write)
 * 
 * @author zhangbo
 */
public class FileMapTest {

    public static void main(String[] args) throws Exception {
        
        String path = "E:/test/test.txt";
        FileInputStream fis = new FileInputStream(path);

        FileChannel fileChannel = fis.getChannel();
        
        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size() + 100);
        
        

    }
    
}
