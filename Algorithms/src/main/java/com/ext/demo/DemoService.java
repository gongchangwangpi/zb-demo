package com.ext.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author bo6.zhang
 * @date 2021/4/20
 */
@Service
public class DemoService {

    @Autowired
    private UserServiceExtPt userServiceExtPt;

    public void test() {
        String name = userServiceExtPt.getName(1L);
        System.out.println("====>>> " + name);
    }

}
