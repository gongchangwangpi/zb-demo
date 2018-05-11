package com.spring.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by books on 2017/9/28.
 */
public class LoggerInterceptor extends HandlerInterceptorAdapter {
    private static Logger log = LoggerFactory.getLogger(LoggerInterceptor.class);

    //重写initial method ， 解决相同线程进入多次报exception
    private ThreadLocal<Long> startTimeThreadLocal = new ThreadLocal<Long>() {
        @Override protected Long initialValue() {
            return System.currentTimeMillis();
        }
    };

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {

        startTimeThreadLocal.set(System.currentTimeMillis());

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {
        long endTime = System.currentTimeMillis();//2、结束时间
        long beginTime = startTimeThreadLocal.get();//得到线程绑定的局部变量（开始时间）
        startTimeThreadLocal.remove();
        String accessUri = request.getRequestURI().substring(request.getContextPath().length());

        StringBuilder logs = new StringBuilder("\n");
        logs.append("**************** call method ****************");
        logs.append("\n").append("call method :" + accessUri + " , used time :" + (endTime - beginTime) + " ms.");
        Map<String, String[]> parameters = request.getParameterMap();

        for (String key : parameters.keySet()) {
            logs.append("\n").append(key + " = " + parameters.get(key)[0]);
        }
        if (e != null) {
            log.error(e.getMessage(), e);
        }
        logs.append("\n").append("**************** call method end ****************");
        log.info(logs.toString());
    }
}
