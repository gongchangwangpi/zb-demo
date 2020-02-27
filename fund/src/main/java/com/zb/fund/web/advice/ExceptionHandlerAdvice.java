package com.zb.fund.web.advice;

import com.zb.commons.dto.RestfulResultDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author zhangbo
 * @date 2019-08-15
 */
@Slf4j
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(value = Exception.class)
    public RestfulResultDto handler(Exception e) {
        log.error("未知异常", e);
        return RestfulResultDto.fail("未知异常");
    }

}
