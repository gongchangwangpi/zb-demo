package com.middlesoftware.netty.qwzn.chapter2.bio;

import com.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

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

    static class TimeServerHandler implements Runnable {

        private Socket socket;

        private static AtomicInteger count = new AtomicInteger();

        public TimeServerHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {

            InputStream inputStream = null;
            PrintStream printStream = null;
            try {
//            log.info("handler request");
                // 获取请求报文
                inputStream = socket.getInputStream();
                String request = IOUtils.toString(inputStream, "UTF-8");

                // 响应当前时间
                printStream = new PrintStream(socket.getOutputStream(), true);
                String dateTime = DateUtil.defaultFormatDateTime(new Date());
                String response = dateTime + " --- " + count.getAndIncrement();
                printStream.print(response);
                printStream.flush();
                log.info("bio server read request: {}, write response: {}", request, response);

            } catch (IOException e) {
                log.error("出错啦", e);
            } finally {
                IOUtils.closeQuietly(printStream);
                IOUtils.closeQuietly(inputStream);
                IOUtils.closeQuietly(socket);
            }
        }
    }

}
