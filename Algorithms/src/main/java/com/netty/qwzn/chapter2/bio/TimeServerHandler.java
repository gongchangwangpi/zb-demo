package com.netty.qwzn.chapter2.bio;

import java.io.*;
import java.net.Socket;
import java.util.Date;

import com.books.bingfayishu.d4.SleepUtils;
import com.util.DateUtil;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

/**
 * TimeServer处理器，负责处理来自客户端的连接
 * 
 * @author zhangbo
 */
@Slf4j
public class TimeServerHandler implements Runnable {
    
    private Socket socket;

    public TimeServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        InputStream inputStream = null;
        PrintStream printStream = null;
        try {
            log.info("handler request");
            // 获取请求报文
            inputStream = socket.getInputStream();
            log.info("read from socket: {}", IOUtils.toString(inputStream, "UTF-8"));

            // 响应当前时间
            printStream = new PrintStream(socket.getOutputStream(), true);
            String dateTime = DateUtil.defaultFormatDateTime(new Date());
            printStream.println(dateTime);
            log.info("print response: {}", dateTime);
            
        } catch (IOException e) {
            log.error("出错啦", e);
        } finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(printStream);
            SleepUtils.millis(100);
            IOUtils.closeQuietly(socket);
        }
    }
}
