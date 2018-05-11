package com.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by books on 2017/4/28.
 */
@ControllerAdvice
public class CustomerExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(CustomerExceptionHandler.class);

    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public final Object handleGeneralException(Exception ex, HttpServletRequest request) {
        logger.error("出错啦...", ex);
        return "{\"code\":\"500\",\"message\":\"出错啦...\"}";
    }

}
