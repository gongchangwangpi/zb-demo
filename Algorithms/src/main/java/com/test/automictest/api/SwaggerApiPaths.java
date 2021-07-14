package com.test.automictest.api;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author bo6.zhang
 * @date 2021/7/1
 */
@Data
public class SwaggerApiPaths {

    private ParamDesc post;

    private ParamDesc get;

    private ParamDesc delete;

    private ParamDesc put;

    @Data
    public static class ParamDesc {
        private String[] consumes;
        private boolean deprecated;
        private String description;
        private String operationId;
        private String summary;
        private String[] produces;
        private String[] tags;
        private Parameters[] parameters;
    }

    @Data
    public static class Parameters {
        private String in;
        private String name;
        private boolean required;
        private String description;
        private Schema schema;
    }

    @Data
    public static class Schema {
        @JSONField(name = "$ref")
        private String ref;
    }
}
