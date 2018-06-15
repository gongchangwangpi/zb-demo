package com.jdksource.nio.chapter3;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author zhangbo
 */
public class FileChannelTest {

    public static void main(String[] args) throws Exception {
        
        String path = "E:/jute";

        FileInputStream fis = new FileInputStream(path);

        FileChannel fileChannel = fis.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        
        // 在FileInputStream打开的FileChannel，是以只读权限打开的，所以不能写
        // 同理FileOutputStream打开的FileChannel也不能读
        fileChannel.write(byteBuffer);

        FileOutputStream fos = new FileOutputStream(path);

        FileChannel fosChannel = fos.getChannel();
        
        fosChannel.read(byteBuffer);

    }
    
}
