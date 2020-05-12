package com.jdksource.java8;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * @author zhangbo
 */
public class FlatMapTest3 {

    public static void main(String[] args) {

        List<User> users = new ArrayList<>();

        List<Job> jobs = new ArrayList<>();
        jobs.add(new Job("java"));
        jobs.add(new Job("mysql"));

        users.add(new User(1L, jobs));

        List<Job> jobs2 = new ArrayList<>();
        jobs2.add(new Job("spring"));
        jobs2.add(new Job("mybatis"));

        users.add(new User(2L, jobs2));

        List<Job> list = users.stream().map(user -> {
            return user.getJobs();
        }).flatMap(Collection::stream).collect(toList());

        System.out.println(JSON.toJSONString(list));

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class User {
        private Long id;
        private List<Job> jobs;
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class Job {
        private String name;
    }

}
