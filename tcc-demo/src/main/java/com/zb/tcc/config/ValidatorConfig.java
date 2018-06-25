package com.zb.tcc.config;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangbo
 */
@Configuration
public class ValidatorConfig {

    /**
     * 设置参数验证快速失败
     * 
     * @return
     */
    @Bean
    public Validator validator() {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                .failFast(true)
//                .addProperty("hibernate.validator.fail_fast", "true")
                .buildValidatorFactory();

        return validatorFactory.getValidator();
    }
    
}
