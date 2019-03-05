package com.test.beancopy;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.beans.BeanCopier;

import java.math.BigDecimal;

/**
 * @author zhangbo
 */
public class BeanCopyTest {

    public static void main(String[] args) {
        
        User user = new User("zhangsan", new BigDecimal("20.23544"), Sex.F);

        UserVo userVo = new UserVo();

        BeanCopier beanCopier = BeanCopier.create(User.class, UserVo.class, true);
        beanCopier.copy(user, userVo, new EnumStringConverter());

        System.out.println(JSON.toJSONString(user));
        System.out.println(JSON.toJSONString(userVo));

        user = new User();
        userVo = new UserVo("zhangsan", new BigDecimal("20.23544"), "M");

        BeanCopier beanCopier2 = BeanCopier.create(UserVo.class, User.class, true);
        beanCopier2.copy(userVo, user, new EnumStringConverter());

        System.out.println(JSON.toJSONString(user));
        System.out.println(JSON.toJSONString(userVo));

        Sex x = Enum.valueOf(Sex.class, "X");
        System.out.println(x);
    }
    

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class User {
        private String name;
        private BigDecimal amount;
        private Sex sex;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class UserVo {
        private String name;
        private BigDecimal amount;
        private String sex;
    }
    enum Sex {
        M,
        F
    }
    
}
