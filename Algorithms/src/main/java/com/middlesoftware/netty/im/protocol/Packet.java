package com.middlesoftware.netty.im.protocol;

/**
 * 数据包基类
 * 
 * @author zhangbo
 */
public abstract class Packet {

    /**
     * 采用的协议版本(预留，以后协议升级使用)
     * @return
     */
    public int version() {
        return ZBVersion.VERSION_0;
    }

    /**
     * 魔数
     * @return
     */
    public int magicNum() {
        return MagicNum.MAGIC_NUM;
    }

    /**
     * 当前数据报代表的操作类型
     * @return
     */
     public abstract int command();
    
}
