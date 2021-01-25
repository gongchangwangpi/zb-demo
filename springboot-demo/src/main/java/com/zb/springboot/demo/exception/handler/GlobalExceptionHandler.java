package com.zb.springboot.demo.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author zhangbo
 * @date 2020/4/29
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Object handle(Exception e) {
        log.error(" ===== Exception =====", e);
//        throw new IllegalArgumentException(e);
        return e.getMessage();
    }

}
