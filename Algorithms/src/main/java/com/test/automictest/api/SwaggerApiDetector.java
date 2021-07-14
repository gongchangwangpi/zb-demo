package com.test.automictest.api;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Sets;
import com.test.automictest.HttpMethod;
import com.util.SimpleHttpClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author bo6.zhang
 * @date 2021/7/1
 */
@Slf4j
public class SwaggerApiDetector {

    static String host = "http://10.160.138.58:8687";
    static String api_docs = "/v2/api-docs";

    static SimpleHttpClient httpClient = new SimpleHttpClient();

    public static void main(String[] args) {

        httpClient.init();

        String docResponse = httpClient.get(host + api_docs);

        SwaggerApiDocsResponse swaggerApiDocsResponse = JSON.parseObject(docResponse, SwaggerApiDocsResponse.class);

        Map<String, SwaggerApiDefinitions> definitions = swaggerApiDocsResponse.getDefinitions();

        Map<String, SwaggerApiPaths> paths = swaggerApiDocsResponse.getPaths();

//        SwaggerApiPaths swaggerApiPaths = paths.get("/c/pre-login");
//        request(definitions, "/c/pre-login", swaggerApiPaths);

        Set<String> excludeUrl = excludeUrl();
        paths.forEach((path, apiPaths) -> {
            if (!excludeUrl.contains(path)) {
                request(definitions, path, apiPaths);
            }
        });

    }

    private static Set<String> excludeUrl() {
        return Sets.newHashSet("/verify/getCode/{phone}", "/c/pre-login", "/b/pre-login");
    }

    private static void request(Map<String, SwaggerApiDefinitions> definitions, String path, SwaggerApiPaths apiPaths) {
        HttpMethod httpMethod = HttpMethod.GET;

        System.out.println(path + "\t" + apiPaths);
        SwaggerApiPaths.ParamDesc paramDesc = apiPaths.getGet();
        if (Objects.isNull(paramDesc)) {
            paramDesc = apiPaths.getPost();
            httpMethod = HttpMethod.POST;
        }
        if (Objects.isNull(paramDesc)) {
            paramDesc = apiPaths.getPut();
            httpMethod = HttpMethod.PUT;
        }
        if (Objects.isNull(paramDesc)) {
            paramDesc = apiPaths.getDelete();
            httpMethod = HttpMethod.DELETE;
        }
        if (Objects.isNull(paramDesc)) {
            log.warn("path param is null: {}", path);
            return;
        }
        // 构建参数
        Map<String, Object> param = buildReqParam(definitions, paramDesc);
//            param.put("orderNo", "707499087845965824001s");

        // 发送请求
        Map<String, String> header = getApiHeader();
        String response = execute(header, path, httpMethod, param);

        log.info("=====>>> test req path: {}, response: {}", path, response);
    }

    private static Map<String, String> getApiHeader() {
        Map<String, String> header = new HashMap<>();
        header.put("token", "5efec9c9b1e3c14f3aab7cb6");
        header.put("Cookie", "SessionToken=105d6fc0-eb01-4009-af5d-0fbba324e73a01;OpenId=o7upo5N5glwZ4V71FF_gpeW56O6I");
        return header;
    }

    private static String execute(Map<String, String> header, String path, HttpMethod httpMethod, Map<String, Object> param) {
        String apiUrl = host + path;
        String response = null;
        switch (httpMethod) {
            case GET:
//                    response = httpClient.get(host + path, param);
                break;
            case POST:
                response = httpClient.postByJsonString(apiUrl, JSON.toJSONString(param), header, "UTF-8");
                break;
            case PUT:
                break;
            case DELETE:
                break;
            default:
                log.error("暂无{}请求： {}", httpMethod, path);
        }
        return response;
    }

    private static Map<String, Object> buildReqParam(Map<String, SwaggerApiDefinitions> definitions, SwaggerApiPaths.ParamDesc paramDesc) {
        Map<String, Object> param = new HashMap<>();
        SwaggerApiPaths.Parameters[] parameters = paramDesc.getParameters();
        for (SwaggerApiPaths.Parameters parameter : parameters) {
            String parameterName = parameter.getName();
            String in = parameter.getIn();
            if ("body".equals(in)) {
                SwaggerApiPaths.Schema schema = parameter.getSchema();
                if (Objects.isNull(schema) || StringUtils.isEmpty(schema.getRef())) {
                    param.put(parameterName, RandomStringUtils.randomAlphanumeric(5));
                } else {
                    buildRefParam(definitions, param, parameterName, schema.getRef());
                }
            } else {
                param.put(parameterName, RandomStringUtils.randomAlphanumeric(5));
            }
        }
        return param;
    }

    private static void buildRefParam(Map<String, SwaggerApiDefinitions> definitions, Map<String, Object> param, String parameterName, String refStr) {
        Map<String, Object> innerParam = new HashMap<>();

        String[] ref = refStr.split("/");
        SwaggerApiDefinitions swaggerApiDefinitions = definitions.get(ref[ref.length - 1]);

        Map<String, SwaggerApiDefinitions.Properties> properties = swaggerApiDefinitions.getProperties();
        String[] required = swaggerApiDefinitions.getRequired();
        Set<String> requiredSet = Sets.newHashSet(required == null ? new String[0] : required);
        properties.forEach((k, p) -> {

            if (requiredSet.contains(k)) {
                if (!ArrayUtils.isEmpty(p.getEnums())) {
                    if ("actionFrom".equals(k)) {
                        innerParam.put(k, "C");
                    } else {
                        innerParam.put(k, p.getEnums()[RandomUtil.randomInt(p.getEnums().length)]);
                    }
                } else if (Objects.nonNull(p.getItems()) && StringUtils.isNotEmpty(p.getItems().getRef())) {
                    buildRefParam(definitions, innerParam, k, p.getItems().getRef());
                } else {
                    if ("business".equals(k)) {
                        innerParam.put(k, "100174");
                    } else {
                        innerParam.put(k, RandomStringUtils.randomAlphanumeric(5));
                    }
                }
            } else {
                // 不是必须的
                if (!ArrayUtils.isEmpty(p.getEnums())) {
                    if ("actionFrom".equals(k)) {
                        innerParam.put(k, "C");
                    } else {
                        innerParam.put(k, p.getEnums()[RandomUtil.randomInt(p.getEnums().length)]);
                    }
                } else if (Objects.nonNull(p.getItems()) && StringUtils.isNotEmpty(p.getItems().getRef())) {
                    buildRefParam(definitions, innerParam, k, p.getItems().getRef());
                } else {
                    if ("templateId".equals(k)) {
                        innerParam.put(k, "100174");
                    } else if ("loginType".equals(k)) {
                        innerParam.put(k, "distributor");
                    } else {
                        innerParam.put(k, RandomStringUtils.randomAlphanumeric(6));
                    }
                }

            }
        });

        param.putAll(innerParam);
    }

}
