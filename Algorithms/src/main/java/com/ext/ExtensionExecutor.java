/*
 * Copyright 2017 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.ext;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ExtensionExecutor
 *
 * @author fulan.zjf 2017-11-05
 */
@Component
@Slf4j
public class ExtensionExecutor extends AbstractComponentExecutor {
    private static final BizScenario DO_NOTHING_BIZ_SCENARIO = BizScenario.newDefault();
    @Autowired
    private ExtensionRepository extensionRepository;

    @Override
    protected <C> C locateComponent(Class<C> targetClz, BizScenario bizScenario) {
        C extension = locateExtension(targetClz, bizScenario);
        log.debug("[Located Extension]: " + extension.getClass().getSimpleName());
        return extension;
    }

    /**
     * if the bizScenarioUniqueIdentity is "ali.tmall.supermarket"
     * <p>
     * the search path is as below:
     * 1、first try to get extension by "ali.tmall.supermarket", if get, return it.
     * 2、loop try to get extension by "ali.tmall", if get, return it.
     * 3、loop try to get extension by "ali", if get, return it.
     * 4、if not found, try the default extension
     *
     * @param targetClz
     */
    protected <Ext> Ext locateExtension(Class<Ext> targetClz, BizScenario bizScenario) {
        checkNull(bizScenario);

        Ext extension;
        String bizScenarioUniqueIdentity = bizScenario.getUniqueIdentity();
        log.debug("BizScenario in locateExtension is : " + bizScenarioUniqueIdentity);

        // first try
        extension = firstTry(targetClz, bizScenarioUniqueIdentity);
        if (extension != null) {
            return extension;
        }

        // loop try
        extension = loopTry(targetClz, bizScenarioUniqueIdentity);
        if (extension != null) {
            return extension;
        }

        extension = lastTry(targetClz);
        if (extension != null) {
            return extension;
        }

        throw new RuntimeException("Can not find extension with ExtensionPoint: " + targetClz + " BizScenario:" + bizScenarioUniqueIdentity);
    }

    private <Ext> Ext lastTry(Class<Ext> targetClz) {
        return (Ext) extensionRepository.getExtensionRepo().get(new ExtensionCoordinate(targetClz.getName(), DO_NOTHING_BIZ_SCENARIO.getUniqueIdentity()));
    }

    private <Ext> Ext firstTry(Class<Ext> targetClz, String bizScenario) {
        return (Ext) extensionRepository.getExtensionRepo().get(new ExtensionCoordinate(targetClz.getName(), bizScenario));
    }

    private <Ext> Ext loopTry(Class<Ext> targetClz, String bizScenario) {
        Ext extension;
        if (bizScenario == null) {
            return null;
        }
        int lastDotIndex = bizScenario.lastIndexOf(".");
        while (lastDotIndex != -1) {
            bizScenario = bizScenario.substring(0, lastDotIndex);
            extension = (Ext) extensionRepository.getExtensionRepo().get(new ExtensionCoordinate(targetClz.getName(), bizScenario));
            if (extension != null) {
                return extension;
            }
            lastDotIndex = bizScenario.lastIndexOf(".");
        }
        return null;
    }


    private void checkNull(BizScenario bizScenario) {
        if (bizScenario == null) {
            throw new RuntimeException("BizScenario can not be null for extension");
        }
    }
}
