package com.zb.springboot.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * restful api 文档自动生成组件配置
 */
//@EnableSwagger2
@EnableOpenApi
@Configuration
//@Profile({"qa", "test" })
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .select()
                // 扫描的路径包
                .apis(RequestHandlerSelectors.basePackage("com.zb.springboot.demo"))
                // 指定路径处理PathSelectors.any()代表所有的路径
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("API文档")
                .description("Demo文档")
                .termsOfServiceUrl("http://127.0.0.1/")
                .contact(new Contact("张皮皮", null, "363230482@qq.com"))
                .version("1.0")
                .build();
    }
}
