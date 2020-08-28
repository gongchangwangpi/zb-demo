package com.middlesoftware.es;

import org.elasticsearch.client.Request;

import java.io.IOException;

/**
 * @author zhangbo
 * @date 2020/6/9
 */
public class IndexTest extends BaseESOperater {

    protected int port = 9300;

    public static void main(String[] args) throws IOException {
        IndexTest indexTest = new IndexTest();
        indexTest.request();
    }

    @Override
    public Request getRequest() {
        this.request = new Request("GET", "test");
        return request;
    }
}
