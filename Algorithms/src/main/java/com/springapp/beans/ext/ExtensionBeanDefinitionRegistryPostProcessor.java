package com.springapp.beans.ext;

import com.alibaba.fastjson.JSON;
import com.ext.ExtensionPointI;
import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author bo6.zhang
 * @date 2021/4/21
 */
public class ExtensionBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    private ConfigurableListableBeanFactory beanFactory;

    private BeanDefinitionRegistry registry;

    @SneakyThrows
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        this.registry = registry;

        Map<Class<?>, List<Class<?>>> clzMap = new HashMap<>();
        // 扫描所有的bean，并将ExtensionPointI归类
        String[] beanDefinitionNames = registry.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = registry.getBeanDefinition(beanDefinitionName);
            String beanClassName = beanDefinition.getBeanClassName();

            Class<?> clz = Class.forName(beanClassName);
            if (ExtensionPointI.class.isAssignableFrom(clz)) {
                Class<?>[] interfaces = clz.getInterfaces();
                for (Class<?> anInterface : interfaces) {
                    if (ExtensionPointI.class.isAssignableFrom(anInterface)) {
                        List<Class<?>> list = clzMap.get(anInterface);
                        if (list == null) {
                            list = new ArrayList<>();
                        }
                        list.add(anInterface);
                    }
                }
            }
        }

        // 生成ExtensionPointI的代理并注册


    }

    interface A{}
    interface B extends A{}
    class C implements B {}

    public static void main(String[] args) {
        Class<?>[] interfaces = C.class.getInterfaces();
        System.out.println(JSON.toJSONString(interfaces));
        System.out.println(A.class.isAssignableFrom(C.class));
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
