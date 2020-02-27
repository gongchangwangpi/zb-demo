package com.zb.shardingjdbc.exception.handler;

import com.zb.shardingjdbc.dto.ResultDTO;
import com.zb.shardingjdbc.exception.LogicException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author zhangbo
 * @date 2020-02-09
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = LogicException.class)
    public ResultDTO handler(LogicException e) {
        log.error("LogicException -> {}", e.getMessage());
        return ResultDTO.fail(e.getMessage());
    }

    @ExceptionHandler(value = NullPointerException.class)
    public ResultDTO handler(NullPointerException e) {
        log.error("NullPointerException -> ", e);
        return ResultDTO.fail(e.getMessage());
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResultDTO handler(IllegalArgumentException e) {
        log.error("IllegalArgumentException -> ", e);
        return ResultDTO.fail(e.getMessage());
    }

    @ExceptionHandler(value = Throwable.class)
    public ResultDTO handler(Throwable throwable) {
        log.error("Throwable -> ", throwable);
        return ResultDTO.fail();
    }
}
