package com.spring.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by books on 2017/12/28.
 */
public class ParameterRequestFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(ParameterRequestFilter.class);
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

//        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//
//        MyHttpServletRequestWrapper newRequest = new MyHttpServletRequestWrapper(httpServletRequest);
////
//        ServletInputStream inputStream = request.getInputStream();
//
//        logger.info("原request: " + convertStreamToString(inputStream));
//
//        User user = new User();
//        user.setId(99L);
//        user.setAge(99);
//        user.setAgentName("zhang");
//
//        String jsonString = JSON.toJSONString(user);
//
        
        // 设置到attribute里面，然后在Controller里面用 @RequestAttribute注解获取参数
//        request.setAttribute("user", jsonString);

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

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
