package com.spring.controller.websocket;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import com.books.bingfayishu.d4.SleepUtils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * @author zhangbo
 */
@Slf4j
@Component(value = "websocketEndpoint")
public class WebsocketEndpoint extends TextWebSocketHandler {

    private static AtomicInteger receiveCount = new AtomicInteger();
    private static AtomicInteger sendCount = new AtomicInteger();
    
    private WebSocketSession webSocketSession;
    
    @Override
    protected void handleTextMessage(WebSocketSession session,
                                     TextMessage message) throws Exception {
        int c = receiveCount.incrementAndGet();
        
        webSocketSession = session;
        
        super.handleTextMessage(session, message);
        TextMessage returnMessage = new TextMessage("server: " + c);
        session.sendMessage(returnMessage);
        log.info("WebSocketSession: {}", session);
        log.info("TextMessage: {}", message);
        log.info("receive client count: {}, message: {}", c, message.getPayload());
        send();
    }

    public void send() throws IOException {
        for (int i = 0; i < 10; i++) {
            int c = sendCount.incrementAndGet();
            webSocketSession.sendMessage(new TextMessage("auto send : " + c));
            SleepUtils.millis(500);
        }
//        webSocketSession.close();
        webSocketSession = null;
    }
}
