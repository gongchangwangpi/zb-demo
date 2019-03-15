package com.springapp.validate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.beanvalidation.BeanValidationPostProcessor;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author zhangbo
 */
@Slf4j
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BeanConfig.class);
        UserService userService = context.getBean(UserService.class);
        User user = userService.register(new User());
        System.out.println(user);
    }
    
    @Configuration
    static class BeanConfig {
        @Bean
        public MethodValidationPostProcessor methodValidationPostProcessor() {
            return new MethodValidationPostProcessor();
        }
        @Bean
        public BeanValidationPostProcessor beanValidationPostProcessor() {
            return new BeanValidationPostProcessor();
        }
        @Bean
        public UserService userService() {
            return new UserServiceImpl();
        }
    }

    @Validated
    static interface UserService {
        public User register(@Validated @NotNull(message = "用户信息不能为空") User user);
    }

    static class UserServiceImpl implements UserService {
        public User register(User user) {
            log.info("register user: {}", user);
            return user;
        }
    }
    
    @Getter
    @Setter
    @Validated
    @NoArgsConstructor
    @AllArgsConstructor
    static class User {
        @NotEmpty(message = "姓名不能为空")
        private String name;
        @NotNull(message = "年龄不能为空")
        @Min(value = 0, message = "最小0岁")
        private Integer age;
    }
}
