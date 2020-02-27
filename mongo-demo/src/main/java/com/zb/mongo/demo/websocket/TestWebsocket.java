package com.zb.mongo.demo.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangbo
 * @date 2020-02-15
 */
@Slf4j
@Component
@ServerEndpoint(value = "/ws/test")
public class TestWebsocket {

    private Session session;

    private static final CopyOnWriteArrayList<TestWebsocket> connections = new CopyOnWriteArrayList<>();

//    @PostConstruct
    public void start() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            log.info(" ----->>> connections size = {}", connections.size());
        }, 1, 1, TimeUnit.SECONDS);
    }

    @OnOpen
    public void open(Session session) {
        this.session = session;
        connections.add(this);
    }

    @OnMessage
    public void message(String msg) {
        log.info("session id = {}, received msg = {}", session.getId(), msg);
    }

    @OnClose
    public void end() {
        connections.remove(this);
        log.info(" close : {}", session.getId());
    }

    @OnError
    public void error(Throwable t) {
        log.error("error : {}", t.getMessage());
    }
}
