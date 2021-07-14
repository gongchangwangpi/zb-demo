package com.ext;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.FactoryBean;

/**
 * @author bo6.zhang
 * @date 2021/4/20
 */
@Slf4j
public class ExtensionPointFactoryBean<T> implements FactoryBean<T> {

    private Class<T> extPtClz;

    public ExtensionPointFactoryBean(Class<T> extPtClz) {
        this.extPtClz = extPtClz;
    }

    @Override
    public T getObject() throws Exception {
        log.info(" =====>>> getObject");
        return null;
    }

    @Override
    public Class<T> getObjectType() {
        log.info(" =====>>> getObjectType: {}", extPtClz);
        return extPtClz;
    }
}
