package com.springapp.beanlifecycle;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangbo
 */
@Configuration
public class BeanConfig {
    
    @Bean(initMethod = "initMethod", destroyMethod = "destroyMethod")
    public TestBean2 testBean2(TestBean testBean) {
        TestBean2 testBean2 = new TestBean2();
        testBean2.setTestBean(testBean);
        return testBean2;
    }

    @Bean(initMethod = "initMethod", destroyMethod = "destroyMethod")
    public TestBean testBean() {
        return new TestBean();
    }
 
    @Bean
    public MyBeanPostProcessor myBeanPostProcessor() {
        return new MyBeanPostProcessor();
    }
}
