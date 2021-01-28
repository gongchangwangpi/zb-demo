package com.framework.templateengine.freemarker.template;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author bo6.zhang
 * @date 2021/1/28
 */
@Slf4j
@RestController
public class A01Controller {

    @PostMapping(value = "a01")
    public Date save() {
        return new Date();
    }
    @PutMapping(value = "a01/{id}")
    public Date update(@PathVariable Long id) {
        return new Date();
    }
    @DeleteMapping(value = "a01/{id}")
    public Date delete(@PathVariable Long id) {
        return new Date();
    }
    @GetMapping(value = "a01")
    public Date list() {
        return new Date();
    }
    @GetMapping(value = "a01/{id}")
    public Date get(@PathVariable Long id) {
        return new Date();
    }

}
