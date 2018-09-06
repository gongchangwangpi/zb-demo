package com.books.programming_pearls.chapter1;

import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Random;

/**
 * -Xms600M -Xmx600M
 * 
 * 位图
 * 
 * 给出大约5亿个整数，且整数范围小于等于{@link Integer#MAX_VALUE}
 * 然后在随机给出一个整数，怎么快速查找这个数是否在那5亿的整数中
 * 
 * 在测试时，可以取消计算重复数量和读取数量的代码
 * 
 * @author zhangbo
 */
public class BitMapTest {

    private static String fileName = "E:\\workspaceIDEA\\zb-demo\\Algorithms\\src\\main\\java\\com\\books\\programming_pearls\\chapter1\\random.file";
    // 重复的数量
    private static int duplicate = 0;
    // 写入文件中整数的数量
    private static int count = 500_000_000;
    // 读取的总数量
    private static int readCount = 0;

    public static void main(String[] args) {

        long s1 = System.currentTimeMillis();

//        writeRandomFile(fileName);

        long e1 = System.currentTimeMillis();

        System.out.println("write random number file use time " + (e1 - s1) + "ms.");

        long s = System.currentTimeMillis();

        byte[] bitMap = new byte[count];

        fillBitMapWithRandomFile(fileName, bitMap);

        Random random = new Random();
        int ran = random.nextInt(count);

        boolean exist = bitMap[ran] == 1;

        long e = System.currentTimeMillis();

        System.out.println("random number is " + ran + ", it's" + (exist ? " " : " not ") + "in the random file.");
        System.out.println("use time " + (e - s) + "ms.");
        System.out.println("in the random file, there have " + duplicate + " duplicate numbers.");
        System.out.println("total read " + readCount);
    }
    
    private static void fillBitMapWithRandomFile(String fileName, byte[] bitMap) {

        FileInputStream fis = null;
        BufferedReader bufferedReader = null;
        try {
            fis = new FileInputStream(fileName);
            bufferedReader = new BufferedReader(new InputStreamReader(fis));

            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                int i = Integer.parseInt(line);
                /*readCount++;
                if (bitMap[i] == 1) {
                    duplicate++;
                    continue;
                }*/
                bitMap[i] = 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(fis);
            IOUtils.closeQuietly(bufferedReader);
        }
    }
    
    private static void writeRandomFile(String fileName) {
        FileOutputStream fos = null;
        
        try {
            fos = new FileOutputStream(fileName, true);
            FileChannel channel = fos.getChannel();
            
            ByteBuffer buffer = ByteBuffer.allocate(100_000);

            Random random = new Random();

            for (int i = 0; i < count; i++) {

                int ran = random.nextInt(count);
                // 每个数字独占一行,方便读取
                buffer.put((ran + "\n").getBytes());
                
                if (buffer.remaining() < 1000) {
                    buffer.flip();
                    channel.write(buffer);
                    buffer.clear();
                }
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(fos);
        }
    }
    
}
