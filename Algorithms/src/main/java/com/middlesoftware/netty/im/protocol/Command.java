package com.middlesoftware.netty.im.protocol;

import com.middlesoftware.netty.im.packet.ChatPacket;
import com.middlesoftware.netty.im.packet.LoginRequestPacket;
import com.middlesoftware.netty.im.packet.LoginResponsePacket;

/**
 * @author zhangbo
 */
public abstract class Command {

    /**
     * 登陆
     */
    public static final int LOGIN_REQUEST = 1;
    public static final int LOGIN_RESPONSE = 2;

    /**
     * 登出
     */
    public static final int LOGOUT_REQUEST = 3;
    public static final int LOGOUT_RESPONSE = 4;

    /**
     * 单聊
     */
    public static final int CHAT_REQUEST = 5;
    
    /**
     * 群聊
     */
    public static final int GROUP_REQUEST = 6;

    public static Class<? extends Packet> getRequestType(byte command) {
        switch (command) {
            case LOGIN_REQUEST: 
                return LoginRequestPacket.class;
            case LOGIN_RESPONSE: 
                return LoginResponsePacket.class;
            case CHAT_REQUEST: 
                return ChatPacket.class;
            case GROUP_REQUEST: 
                return ChatPacket.class;
            default:
                return Packet.class;
        }
    }
}
