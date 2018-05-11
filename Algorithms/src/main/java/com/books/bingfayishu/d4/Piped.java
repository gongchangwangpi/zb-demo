package com.books.bingfayishu.d4;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * PipedInputStream/PipedOutputStream/ PipedReader/PipedWriter
 * 主要用于线程之间的数据传输，而传输的媒介为内存
 * 
 * 对于Piped类型的流，必须先要进行绑定，也就是调用connect()方法，
 * 如果没有将输入/输出流绑定起来，对于该流的访问将会抛出异常
 * 
 * @author zhangbo
 */
public class Piped {

    public static void main(String[] args) throws Exception {

        PipedReader reader = new PipedReader();
        PipedWriter writer = new PipedWriter();
        
        writer.connect(reader);

        Thread printThread = new Thread(new PrintThread(reader), "PrintThread");
        printThread.start();

        int receive = 0;

        try {
            // 读取控制台的输入
            while ((receive = System.in.read()) != -1) {
                // 将控制台的输入，输出到PipedReader
                writer.write(receive);
            }
        } finally {
            writer.close();
        }

    }
    
    static class PrintThread implements Runnable {
        private PipedReader reader;

        public PrintThread(PipedReader reader) {
            this.reader = reader;
        }

        @Override
        public void run() {
            int receive = 0;
            try {
                // 读取PipedReader的内容
                while ((receive = reader.read()) != -1) {
                    System.out.print((char) receive);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
}
