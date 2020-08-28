package com.middlesoftware.netty.qwzn.chapter2.bio;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 基于BIO的客户端
 * 
 * @author zhangbo
 */
@Slf4j
public class TimeClient {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 10; i++) {
            executorService.execute(new Client(i));
        }
        
        executorService.shutdown();
    }
    
    static class Client implements Runnable {
        
        private int no;

        public Client(int no) {
            this.no = no;
        }

        @Override
        public void run() {
            Socket socket = null;
            PrintStream  printStream = null;
            InputStream inputStream = null;

            try {
                socket = new Socket("localhost", 8080);
                
                // 请求报文
                printStream = new PrintStream(socket.getOutputStream(), true);
                String request = "query time order " + no;
                printStream.print(request);
                printStream.flush();

                // 获取响应
                inputStream = socket.getInputStream();
                String response = IOUtils.toString(inputStream, "UTF-8");
                log.info("bio client request: {}, response: {}", request, response);

            } catch (IOException e) {
                log.error("出错啦", e);
            } finally {
                
                IOUtils.closeQuietly(inputStream);
                IOUtils.closeQuietly(socket);
            }
        }
    }
}
