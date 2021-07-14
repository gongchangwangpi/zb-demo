package com.springapp.beans.register;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author bo6.zhang
 * @date 2021/4/21
 */
@Slf4j
@Data
public class PersonService {

    private String name;

    public String hello() {
        log.info("===>> hello {}", name);
        return name;
    }

}
