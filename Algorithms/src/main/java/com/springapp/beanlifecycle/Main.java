package com.springapp.beanlifecycle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Spring Bean 生命周期
 * new 实例化 -> set property注入依赖 -> Aware接口 ->
 * BeanPostProcessor.beforeInit.. -> @PostConstruct ->
 * InitializingBean.afterPropertiesSet -> 自定义init-method ->
 * BeanPostProcessor.afterInit... -> 初始化完成，可以使用 ->
 * @PreDestroy --> DisposableBean.destroy -> 自定义destroy-method
 *
 * @author zhangbo
 */
@Slf4j
public class Main {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BeanConfig.class);

        TestBean bean = context.getBean(TestBean.class);
        
//        log.info("-------- {}", bean);

        context.destroy();
    }
    
}
