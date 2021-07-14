package com.test.automictest.api;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Map;

/**
 * @author bo6.zhang
 * @date 2021/7/1
 */
@Data
public class SwaggerApiDefinitions {

    /**
     * string integer object array
     */
    private String type;

    private String title;

    private String description;

    /**
     * 必填的字段名称
     */
    private String[] required;

    private Map<String, Properties> properties;

    @Data
    public static class Properties {
        /**
         * string integer object array
         */
        private String type;
        private String example;
        /**
         * int64 int32
         */
        private String format;
        private String description;
        @JSONField(name = "enum")
        private String[] enums;
        /**
         * 引用其他类型
         */
        private Items items;
    }

    @Data
    public static class Items {
        @JSONField(name = "$ref")
        private String ref;
    }

}
