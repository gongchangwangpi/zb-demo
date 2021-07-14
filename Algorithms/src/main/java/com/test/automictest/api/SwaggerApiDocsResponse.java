package com.test.automictest.api;

import lombok.Data;

import java.util.Map;

/**
 * @author bo6.zhang
 * @date 2021/7/1
 */
@Data
public class SwaggerApiDocsResponse {

    private String basePath;

    private String host;

    private String swagger;

    private Map<String, SwaggerApiPaths> paths;

    private Map<String, SwaggerApiDefinitions> definitions;

}
