package com.zb.springboot.demo.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author bo6.zhang
 * @date 2021/1/25
 */
@Slf4j
@Component
@WebFilter
public class LoggerFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        long beginTime = System.currentTimeMillis();
        try {
            super.doFilter(request, response, chain);
        } catch (Exception e) {
            throw e;
        } finally {
            log.info("HTTP {} {}, cost {}ms", request.getMethod(), request.getRequestURI(), System.currentTimeMillis() - beginTime);
        }
    }
}
