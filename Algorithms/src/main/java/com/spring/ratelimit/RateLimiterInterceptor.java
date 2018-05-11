package com.spring.ratelimit;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 流程控制拦截器
 * 
 * Created by books on 2017/12/22.
 */
public class RateLimiterInterceptor extends HandlerInterceptorAdapter {

    /**
     * 每秒最多5个请求
     */
    private static final RateLimiter RATE_LIMITER = RateLimiter.create(5);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (RATE_LIMITER.tryAcquire()) {
            // 获取到权限的，放行
            return super.preHandle(request, response, handler);
        } else {
            // 没有获取权限的，直接返回
            response.getOutputStream().print("{\"statusCode\":\"503\",\"body\":\"请求频率超过限制\"}");
            return false;
        }
    }
}
