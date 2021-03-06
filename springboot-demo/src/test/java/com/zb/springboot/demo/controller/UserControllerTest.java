package com.zb.springboot.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.testable.core.annotation.MockMethod;
import com.zb.springboot.demo.entity.User;
import com.zb.springboot.demo.service.user.UserService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author bo6.zhang
 * @date 2021/2/26
 */
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerTest {

    @Resource
    private MockMvc mockMvc;

    @Resource
    private UserService userService;

    String saveUrl = "/user/save";

    @Test
    public void get() {
        User user = userService.get(1L);
        User zhangsan = userService.get("name2");
        System.out.println(user);
        System.out.println(zhangsan);
    }

    @MockMethod
    private boolean isTest() {
        System.out.println(" ======= mock isTest ======");
        return true;
    }

    @Test
    @Rollback
    @Transactional
    public void saveTest() throws Exception {
        User user = new User();
        user.setAge(18);
        user.setUsername("user_test18");

        Long id = post(saveUrl, user, Long.class);
        Assertions.assertTrue(id > 0);
    }

    protected <T> T post(String url, Object request, Class<T> kls) throws Exception {
        String content = post(url, request);

        return JSON.parseObject(content, kls);
    }

    protected <T> T post(String url, Object request, TypeReference<T> typeReference) throws Exception {
        String content = post(url, request);

        return JSON.parseObject(content, typeReference);
    }

    private String post(String url, Object request) throws Exception {
        return post(MockMvcRequestBuilders.post(url)
                .content(JSON.toJSONStringWithDateFormat(request, "yyyy-MM-dd HH:mm:ss"))
                .contentType(MediaType.APPLICATION_JSON_UTF8));
    }


    protected String post(RequestBuilder requestBuilder) throws Exception {
        ResultActions resultAction = this.mockMvc.perform(requestBuilder);
        resultAction.andReturn().getResponse().setCharacterEncoding("UTF-8");
        return resultAction.andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString(StandardCharsets.UTF_8);
    }

}
