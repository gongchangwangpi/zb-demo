package com.springapp.source.beanfactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinitionReader;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;

/**
 * 实现自定义格式的properties文件定义bean
 * 
 * @author zhangbo
 */
public class MyPropertiesBeanDefinitionReader extends AbstractBeanDefinitionReader {
    
    protected MyPropertiesBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    @Override
    public int loadBeanDefinitions(Resource resource) throws BeanDefinitionStoreException {
        Properties properties = new Properties();

        try {
            InputStream inputStream = new EncodedResource(resource).getInputStream();
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int count = 0;

        Set<Map.Entry<Object, Object>> entriesSet = properties.entrySet();
        for (Map.Entry<Object, Object> entry : entriesSet) {
            String beanName = entry.getKey().toString();
            String className = entry.getValue().toString();

            if (!beanName.contains(".")) {
                // bean名称和对应的class
                AbstractBeanDefinition bd = null;
                try {
                    bd = BeanDefinitionReaderUtils.createBeanDefinition(
                            null, className, getBeanClassLoader());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
//                bd.setScope(scope);
//                bd.setAbstract(isAbstract);
//                bd.setLazyInit(lazyInit);
//                bd.setConstructorArgumentValues(cas);
//                bd.setPropertyValues(pvs);
                getRegistry().registerBeanDefinition(beanName, bd);
                count++;
            } else {
                // bean的属性名和对应的class
                // TODO
            }
        }

        return count;
    }

    @Override
    public int loadBeanDefinitions(String locations) throws BeanDefinitionStoreException {

        Resource resource = getResourceLoader().getResource(locations);

        return loadBeanDefinitions(resource);
    }
}
