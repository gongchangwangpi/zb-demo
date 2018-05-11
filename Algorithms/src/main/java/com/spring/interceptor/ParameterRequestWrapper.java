package com.spring.interceptor;

import org.apache.catalina.util.ParameterMap;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * 重写HttpServletRequest,便于在拦截器中添加自定义的参数
 * 
 * Created by books on 2017/12/27.
 */
public class ParameterRequestWrapper extends HttpServletRequestWrapper {

    private ParameterMap<String, String[]> params;
    
    private ServletInputStream inputStream;

    public ParameterRequestWrapper(HttpServletRequest request) {
        super(request);
        this.params = (ParameterMap<String, String[]>) request.getParameterMap();
        try {
            this.inputStream = request.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addParameter(String name, Object value) {//增加参数
        if (value != null) {
            params.setLocked(false);
            if (value instanceof String[]) {
                params.put(name, (String[]) value);
            } else if (value instanceof String) {
                params.put(name, new String[]{(String) value});
            } else {
                params.put(name, new String[]{String.valueOf(value)});
            }
            params.setLocked(true);
        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return inputStream;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return super.getReader();
    }

    public void setInputStream(ServletInputStream newInputStream) {
        if (newInputStream != null) {
            inputStream = newInputStream;
        }
    }
}
