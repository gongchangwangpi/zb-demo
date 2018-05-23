package com.netty.qwzn.chapter2.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

/**
 * 基于BIO的客户端
 * 
 * @author zhangbo
 */
@Slf4j
public class TimeClient {

    public static void main(String[] args) {

        for (int i= 0; i < 10; i++) {
            Socket socket = null;
            PrintStream  printStream = null;
            InputStream inputStream = null;

            try {
                socket = new Socket("127.0.0.1", 8080);
                
                // 请求报文
                printStream = new PrintStream(socket.getOutputStream(), true);
                printStream.println("QUERY TIME ORDER");
                log.info("client request");
                IOUtils.closeQuietly(printStream);
                
                // 获取响应
                inputStream = socket.getInputStream();
                String response = IOUtils.toString(inputStream, "UTF-8");
                log.info("response: {}", response);
    
            } catch (IOException e) {
                log.error("出错啦", e);
            } finally {
                IOUtils.closeQuietly(inputStream);
                IOUtils.closeQuietly(printStream);
                IOUtils.closeQuietly(socket);
            }
        }

    }
    
}
