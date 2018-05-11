package com.spring.controller;

import com.spring.service.PrototypeService;
import com.spring.service.PrototypeService2;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by books on 2017/7/24.
 */
@Controller
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SingletonPrototypeTestController {

    @Resource
    private PrototypeService prototypeService;

    @Resource
    private PrototypeService2 prototypeService2;

    @RequestMapping(value = "/prototype")
    @ResponseBody
    public String prototype() {
        prototypeService.sayWorld("2");
        prototypeService2.sayWorld2("2");
        return "prototype";
    }

}
