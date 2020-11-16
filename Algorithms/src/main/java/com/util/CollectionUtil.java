package com.util;

import cn.hutool.core.collection.CollUtil;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author zhangbo
 * @date 2020/11/16
 */
public class CollectionUtil {

    /**
     * 将一个大的list，拆分为多个小list，并执行相应的任务
     *
     * @param list
     * @param maxCountOnce
     * @param consumer
     * @param <T>
     */
    public static <T> void subConsume(List<T> list, int maxCountOnce, Consumer<List<T>> consumer) {
        List<List<T>> listList = CollUtil.split(list, maxCountOnce);
        listList.forEach(consumer::accept);
    }

}
