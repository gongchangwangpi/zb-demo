package com.es;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;

import java.io.IOException;

/**
 * ES操作基类，子类只需调用{@link com.es.BaseESOperater#request()}即可
 * 如有其它请求，则可根据需要覆写对应的方法
 *
 * @author zhangbo
 * @date 2020/6/9
 */
@Slf4j
public class BaseESOperater {

    protected String hostname = "127.0.0.1";
    protected int port = 9200;
    protected String scheme = "http";

    protected RestClient restClient;
    protected Request request;
    protected Response response;

    public void request() throws IOException {
        try {
            initClient();
            getRequest();
            execute();
            consume();
        } finally {
            close();
        }
    }

    public HttpHost getHttpHost() {
        return new HttpHost(hostname, port, scheme);
    }

    public void initClient() {
        restClient = RestClient.builder(getHttpHost()).build();
    }

    public Request getRequest() {
        this.request = new Request("GET", "test");
        return request;
    }

    public Response execute() throws IOException {
        this.response = restClient.performRequest(request);
        return response;
    }

    public String consume() throws IOException {
        log.info("response code: {}, {}", response.getStatusLine().getStatusCode(), response.getStatusLine().getReasonPhrase());
        String s = EntityUtils.toString(response.getEntity());
        log.info("response body: {}", s);
        return s;
    }

    public void close() {
        if (restClient != null) {
            try {
                restClient.close();
            } catch (IOException e) {
                //
                log.error("close restClient error", e);
            }
        }
    }
}
