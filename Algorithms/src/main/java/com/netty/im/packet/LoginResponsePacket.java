package com.netty.im.packet;

import com.netty.im.protocol.Command;
import com.netty.im.protocol.Packet;
import lombok.Getter;
import lombok.Setter;

/**
 * 登陆请求
 * 
 * @author zhangbo
 */
@Getter
@Setter
public class LoginResponsePacket extends Packet {
    
    private String uid;
    
    private String username;
    
    private String message;

    @Override
    public int command() {
        return Command.LOGIN_RESPONSE;
    }
    
}
