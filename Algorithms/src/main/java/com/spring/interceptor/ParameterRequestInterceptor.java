package com.spring.interceptor;

import com.alibaba.fastjson.JSON;
import com.spring.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.DelegatingServletInputStream;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 
 * 
 * Created by books on 2017/12/27.
 */
public class ParameterRequestInterceptor extends HandlerInterceptorAdapter {
    
    private Logger logger = LoggerFactory.getLogger(ParameterRequestInterceptor.class);
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ParameterRequestWrapper newRequest = new ParameterRequestWrapper(request);

        ServletInputStream inputStream = request.getInputStream();

        logger.info("原request: " + convertStreamToString(inputStream));

        User user = new User();
        user.setId(99L);
        user.setAge(99);
        user.setUsername("zhang");

        String jsonString = JSON.toJSONString(user);

        newRequest.setInputStream(new DelegatingServletInputStream(new ByteArrayInputStream(jsonString.getBytes())));

        return super.preHandle(newRequest, response, handler);
    }


    public String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "/n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }
}
