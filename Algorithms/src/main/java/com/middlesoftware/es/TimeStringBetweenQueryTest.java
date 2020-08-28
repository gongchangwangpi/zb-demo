package com.middlesoftware.es;

import org.elasticsearch.client.Request;

import java.io.IOException;

/**
 * @author zhangbo
 * @date 2020/6/9
 */
public class TimeStringBetweenQueryTest extends BaseESOperater {

    public static void main(String[] args) throws IOException {

        TimeStringBetweenQueryTest queryTest = new TimeStringBetweenQueryTest();
        queryTest.request();

    }

    @Override
    public Request getRequest() {
        this.request = new Request("POST", "test");
//        request.setJsonEntity();
        return request;
    }
}
