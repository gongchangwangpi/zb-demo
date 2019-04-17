package com.dubbo.test.serialize.json;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhangbo
 */
public class FastJsonTest {

    public static void main(String[] args) throws Exception {
        // 准备数据
        User<Job> user = new User<>();
        Job job = new Job();
        job.setJobName("coder");
        user.setData(new Job[]{job});

        String jsonString = JSON.toJSONString(user);
//        System.out.println(jsonString);
//
//        User<Job> jobUser = JSON.parseObject(jsonString, new TypeReference<User<Job>>() {
//        });

        User user1 = JSON.parseObject(jsonString, User.class);

        ObjectMapper objectMapper = new ObjectMapper();
        String string = objectMapper.writeValueAsString(user);

        user = objectMapper.readValue(string, new TypeReference<User<Job>>() {
        });

        System.out.println(user.getData());

    }
    
    @Data
    private static class User<T> implements Serializable {
        private static final long serialVersionUID = 3119824898507746327L;
        private T[] data;
    }
    @Data
    private static class Job implements Serializable {
        private static final long serialVersionUID = 5037271666077630796L;
        private String jobName;
    }
}
