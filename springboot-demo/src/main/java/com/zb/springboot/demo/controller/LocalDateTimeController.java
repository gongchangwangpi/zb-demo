package com.zb.springboot.demo.controller;

import com.zb.springboot.demo.dto.LocalDateTimeQueryReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author zhangbo
 * @date 2019-11-29
 */
@Slf4j
@RestController
public class LocalDateTimeController {

    @GetMapping(value = "/local_date_time")
    public Object localDateTime(LocalDateTime localDateTime) {
        log.info("localDateTime = {}", localDateTime);
        return localDateTime;
    }

    @GetMapping(value = "/query/local_date_time")
    public Object localDateTime(LocalDateTimeQueryReq queryReq) {
        log.info("queryReq = {}", queryReq);
        return queryReq;
    }

}
