package com.test;

import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * -Xmn50m -Xmx50m -XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCTimeStamps -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=E://openproj//heap.dump
 * 
 * @author zhangbo
 */
public class MemoryDumpTest {

    public static void main(String[] args) {
        List<User> list = new ArrayList<>();

        Random random = new Random();

        int i = 0;
        while (true) {
            User user = new User();
            user.setId((long) i);
            user.setUsername("username" + i);
            user.setRealName(RandomStringUtils.random(10));
            user.setAge(random.nextInt(150));
            user.setEmail(RandomStringUtils.randomAlphanumeric(10) + "@163.com");
            user.setBirthday(new Date());
            user.setAddress("四川省成都市高新区天府大道" + random.nextInt(2000) + "号");
            user.setIdCardNo(RandomStringUtils.randomNumeric(18));
            user.setCreateTime(new Date());
            
            list.add(user);
            
            System.out.println(i);
            i++;
        }
    }
    
    @Data
    private static class User implements Serializable {
        private static final long serialVersionUID = 5257157627898227906L;
        private Long id;
        private String username;
        private String realName;
        private Integer age;
        private String email;
        private Date birthday;
        private String address;
        private String idCardNo;
        private Date createTime;
    }
    
}
