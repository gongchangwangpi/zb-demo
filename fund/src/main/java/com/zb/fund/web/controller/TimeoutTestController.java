package com.zb.fund.web.controller;

import com.zb.commons.dto.RestfulResultDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author zhangbo
 */
@Slf4j
@RestController
public class TimeoutTestController {
    
    private static final AtomicLong count = new AtomicLong();
    
    @GetMapping(value = "/timeout")
    public RestfulResultDto timeout() throws InterruptedException {
        long i = count.getAndIncrement();
        log.info("-------- {}", i);
        TimeUnit.SECONDS.sleep(15);
        return RestfulResultDto.succeed(i);
    }
    
}
