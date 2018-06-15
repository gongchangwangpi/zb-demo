package com.jdksource.nio.chapter3;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 文件空洞
 * 由{@link java.io.RandomAccessFile#seek(long)} 或者
 * {@link java.nio.channels.FileChannel#position(long)} 设置超过文件长度的位置
 * 然后在继续写入造成
 *
 * @author zhangbo
 */
public class FileHole {


    public static void main(String[] argv) throws IOException {
        // Create a temp file, open for writing, and get a FileChannel
        File temp = File.createTempFile("holy", null);
        RandomAccessFile file = new RandomAccessFile(temp, "rw");
        FileChannel channel = file.getChannel();
        // Create a working buffer
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(100);
        putData(0, byteBuffer, channel);
        putData(5000000, byteBuffer, channel);
        putData(50000, byteBuffer, channel);
        // Size will report the largest position written, but
        // there are two holes in this file. This file will
        // not consume 5 MB on disk (unless the filesystem is74
        // extremely brain-damaged)
        System.out.println("Wrote temp file '" + temp.getPath()
                + "', size=" + channel.size());
        channel.close();
        file.close();
    }

    private static void putData(int position, ByteBuffer buffer, FileChannel channel) throws IOException {
        String string = "*<-- location " + position;
        buffer.clear();
        buffer.put(string.getBytes("US-ASCII"));
        buffer.flip();
        channel.position(position);
        channel.write(buffer);
    }

}
