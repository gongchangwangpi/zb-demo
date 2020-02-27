package com.zb.mongo.demo.websocket;

import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangbo
 * @date 2020-02-15
 */
@ClientEndpoint
public class WebsocketTest {

    private String deviceId;

    private Session session;

    public WebsocketTest () {

    }

    public WebsocketTest (String deviceId) {
        this.deviceId = deviceId;
    }

    protected boolean start() {
        WebSocketContainer Container = ContainerProvider.getWebSocketContainer();
        String uri = "ws://localhost:8080/ws/test";
        System.out.println("Connecting to " + uri);
        try {
            session = Container.connectToServer(WebsocketTest.class, URI.create(uri));
            System.out.println("count: " + deviceId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void main(String[] args) throws Exception {
        List<WebsocketTest> list = new ArrayList<>(1000);
        for (int i = 1; i< 1000; i++) {
            WebsocketTest wSocketTest = new WebsocketTest(String.valueOf(i));
            if (!wSocketTest.start()) {
                System.out.println("测试结束！");
                break;
            }
            list.add(wSocketTest);
        }

        while (true) {
            for (WebsocketTest websocketTest : list) {
                if (websocketTest.session.isOpen()) {
                    websocketTest.session.getBasicRemote().sendText("hello");
                }
            }
            TimeUnit.SECONDS.sleep(1);
        }
    }

}
