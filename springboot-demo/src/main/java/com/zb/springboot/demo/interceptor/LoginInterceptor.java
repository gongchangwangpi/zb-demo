package com.zb.springboot.demo.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author zhangbo
 * @date 2019-12-27
 */
@Slf4j
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        log.info(" --->>> requestUri = {}, sessionId = {}", request.getRequestURI(), session.getId());
        if (session.getAttribute("loginUser") == null) {
            log.error("登录超时或未登录，sessionId = {}", session.getId());
            return false;
        }
        return true;
    }
}
