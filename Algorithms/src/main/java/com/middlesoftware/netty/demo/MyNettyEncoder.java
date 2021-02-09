package com.middlesoftware.netty.demo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.common.RemotingUtil;

import java.nio.ByteBuffer;

/**
 * 自定义编解码器
 * 参考 rocketmq
 *
 * @author bo6.zhang
 * @date 2021/2/9
 */
@Slf4j
public class MyNettyEncoder extends MessageToByteEncoder<MyRequest> {

    @Override
    protected void encode(ChannelHandlerContext ctx, MyRequest myRequest, ByteBuf out) throws Exception {

        try {
            // 编码header
            ByteBuffer header = myRequest.encodeHeader();
            out.writeBytes(header);
            // 编码body
            byte[] body = myRequest.bodyData();
            if (body != null) {
                out.writeBytes(body);
            }
        } catch (Exception e) {
            log.error("encode exception, " + RemotingHelper.parseChannelRemoteAddr(ctx.channel()), e);
            if (myRequest != null) {
                log.error(myRequest.toString());
            }
            RemotingUtil.closeChannel(ctx.channel());
        }

    }

}
