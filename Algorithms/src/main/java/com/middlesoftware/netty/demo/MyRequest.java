package com.middlesoftware.netty.demo;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.nio.ByteBuffer;
import java.time.LocalDateTime;

/**
 * @author bo6.zhang
 * @date 2021/2/9
 */
@Data
public class MyRequest {

    private Header header;

    private Body body;

    public static MyRequest decode(ByteBuffer byteBuffer) {
        int length = byteBuffer.limit();
        int oriHeaderLen = byteBuffer.getInt();
        int headerLength = getHeaderLength(oriHeaderLen);

        byte[] headerData = new byte[headerLength];
        byteBuffer.get(headerData);

        MyRequest myRequest = headerDecode(headerData);

        int bodyLength = length - 4 - headerLength;
        byte[] bodyData = null;
        if (bodyLength > 0) {
            bodyData = new byte[bodyLength];
            byteBuffer.get(bodyData);
        }
        myRequest.body = bodyDecode(bodyData);

        return myRequest;
    }

    private static MyRequest headerDecode(byte[] headerData) {
        MyRequest myRequest = new MyRequest();
        // JSON解码，与 encodeHeader 对应
        Header header = JSON.parseObject(headerData, Header.class);
        myRequest.setHeader(header);
        return myRequest;
    }

    private static Body bodyDecode(byte[] bodyData) {
        if (bodyData == null) {
            return null;
        }
        return JSON.parseObject(bodyData, Body.class);
    }

    public static int getHeaderLength(int length) {
        return length & 0xFFFFFF;
    }

    public byte[] bodyData() {
        return body == null ? new byte[0] : JSON.toJSONBytes(body);
    }

    public ByteBuffer encodeHeader() {
        return encodeHeader(this.body != null ? bodyData().length : 0);
    }

    public ByteBuffer encodeHeader(final int bodyLength) {
        // 1> header length size
        int length = 4;

        // 2> header data length
        byte[] headerData;
        // 此处简单使用JSON序列化，可自定义协议处理
        headerData = JSON.toJSONBytes(header);

        length += headerData.length;

        // 3> body data length
        length += bodyLength;

        ByteBuffer result = ByteBuffer.allocate(4 + length - bodyLength);

        // length
        result.putInt(length);

        // header length
        result.putInt(headerData.length);

        // header data
        result.put(headerData);

        result.flip();

        return result;
    }

    @Data
    static class Header {
        private Integer protocol;
        private Integer code;
        private Integer version;
    }

    @Data
    static class Body {

        private Long id;

        private String name;

        private String addr;

        private Integer age;

        private LocalDateTime createTime;
    }

}
