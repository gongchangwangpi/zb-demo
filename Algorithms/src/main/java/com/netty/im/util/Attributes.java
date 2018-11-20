package com.netty.im.util;

import com.netty.im.packet.LoginRequestPacket;
import io.netty.util.AttributeKey;

/**
 * @author zhangbo
 */
public class Attributes {
    
    public static final AttributeKey<LoginRequestPacket> USER = AttributeKey.newInstance("user");
    
}
