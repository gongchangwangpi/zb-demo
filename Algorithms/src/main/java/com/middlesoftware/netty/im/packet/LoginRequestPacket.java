package com.middlesoftware.netty.im.packet;

import com.middlesoftware.netty.im.protocol.Command;
import com.middlesoftware.netty.im.protocol.Packet;
import lombok.Getter;
import lombok.Setter;

/**
 * 登陆请求
 * 
 * @author zhangbo
 */
@Getter
@Setter
public class LoginRequestPacket extends Packet {
    
    private String uid;
    
    private String username;
    
    private String password;

    @Override
    public int command() {
        return Command.LOGIN_REQUEST;
    }
    
}
