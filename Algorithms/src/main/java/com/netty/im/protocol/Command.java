package com.netty.im.protocol;

import com.netty.im.request.LoginRequestPacket;

/**
 * @author zhangbo
 */
public abstract class Command {

    /**
     * 登陆
     */
    public static final int LOGIN = 1;

    /**
     * 登出
     */
    public static final int LOGOUT = 2;

    /**
     * 单聊
     */
    public static final int CHAT = 3;

    /**
     * 创建群聊
     */
    public static final int GROUP = 4;

    public static Class<? extends Packet> getRequestType(byte command) {
        switch (command) {
            case LOGIN: 
                return LoginRequestPacket.class;
            default:
                return Packet.class;
        }
    }
}
