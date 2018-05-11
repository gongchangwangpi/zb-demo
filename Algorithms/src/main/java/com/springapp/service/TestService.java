package com.springapp.service;

import org.springframework.stereotype.Service;

/**
 * Created by books on 2017/9/4.
 */
@Service(value = "testService")
public class TestService {



    public TestService() {
        System.out.println("------------- testService init ---------------");
    }
}
