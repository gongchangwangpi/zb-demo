package com.netty.qwzn.chapter2.bio;

import com.books.bingfayishu.d4.SleepUtils;
import com.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * TimeServer处理器，负责处理来自客户端的连接
 * 
 * @author zhangbo
 */
@Slf4j
public class TimeServerHandler implements Runnable {
    
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
            log.info("read from socket: {}, response: {}", request, response);
            
        } catch (IOException e) {
            log.error("出错啦", e);
        } finally {
            IOUtils.closeQuietly(printStream);
            IOUtils.closeQuietly(inputStream);
            SleepUtils.millis(100);
            IOUtils.closeQuietly(socket);
        }
    }
}
