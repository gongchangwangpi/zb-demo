package com.ext;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * ExtensionPointI is the parent interface of all ExtensionPoints
 * 扩展点表示一块逻辑在不同的业务有不同的实现，使用扩展点做接口申明，然后用Extension（扩展）去实现扩展点。
 *
 * @author fulan.zjf 2017-10-22
 */
public interface ExtensionPointI {

    /**
     * 支持的bizId
     *
     * @return
     */
    default Set<String> supportBizId() {
        return Sets.newHashSet();
    }

}
