package com.zb.tcc.web.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author zhangbo
 */
@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {
    
    @ExceptionHandler(value = Exception.class)
    public Object handler(Exception e) {
        
        String errorMessage = "系统开小差啦";
        
        if (e instanceof BindException) {
            BindingResult result = (BindingResult) e;

            errorMessage = result.getAllErrors().get(0).getDefaultMessage();
            
            log.error("参数绑定失败: {}", errorMessage);

        } else {

            log.error("出错啦", e);
        }
        
        return errorMessage;
    }
    
}
