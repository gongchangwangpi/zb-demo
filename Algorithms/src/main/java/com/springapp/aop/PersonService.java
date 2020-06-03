package com.springapp.aop;

import org.springframework.stereotype.Service;

/**
 * @author zhangbo
 * @date 2020/5/15
 */
@Service
public class PersonService {

    @DDD
    public long getTimestamp() {
        return System.currentTimeMillis();
    }

}
