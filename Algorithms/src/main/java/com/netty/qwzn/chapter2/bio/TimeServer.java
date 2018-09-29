package com.netty.qwzn.chapter2.bio;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 基于传统BIO的时间服务器
 * 
 * @author zhangbo
 */
@Slf4j
public class TimeServer {

    public static void main(String[] args) {
        
        int port = 8080;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            log.info("ServerSocket start at port: {}", port);
            
            while (true) {
                Socket socket = serverSocket.accept();
//                log.info("ServerSocket accept connection");
                new Thread(new TimeServerHandler(socket)).start();
            }
            
        } catch (IOException e) {
            log.error("出错啦", e);
        } finally {
            IOUtils.closeQuietly(serverSocket);
            log.info("ServerSocket shutdown");
        }

    }
    
}
