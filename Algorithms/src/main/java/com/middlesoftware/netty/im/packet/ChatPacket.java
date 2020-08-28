package com.middlesoftware.netty.im.packet;

import com.middlesoftware.netty.im.protocol.Command;
import com.middlesoftware.netty.im.protocol.Packet;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author zhangbo
 */
@Getter
@Setter
public class ChatPacket extends Packet {
    
    private String toUid;
    
    private String toUsername;
    
    private String fromUid;
    
    private String fromUsername;
    
    private String message;
    
    private Date sendTime;
    
    @Override
    public int command() {
        return Command.CHAT_REQUEST;
    }
}
