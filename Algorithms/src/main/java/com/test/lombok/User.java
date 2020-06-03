package com.test.lombok;

import lombok.Builder;
import lombok.Data;

/**
 * @author zhangbo
 * @date 2020/5/19
 */
@Data
@Builder
public class User {

    private Long id;

    private String name;

    private long timestamp;

    public static void main(String[] args) {
        User user = User.builder().id(1L).build();
        System.out.println(user);
    }

}
