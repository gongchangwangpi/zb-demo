package com.zb.fund.context;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.PropertiesPropertySource;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 自定义加载配置
 * 见 META-INF/spring.factories
 * 
 * @author zhangbo
 */
@Slf4j
public class CustomContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    
    private ConfigurableApplicationContext applicationContext;
    
    private static final String CONFIG_FILE = "config.file";
    
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        log.info(" ===== initialize ===== {}", applicationContext);
        this.applicationContext = applicationContext;
        String configFileUrl = applicationContext.getEnvironment().getProperty(CONFIG_FILE);
        
        if (StringUtils.isEmpty(configFileUrl)) {
            log.info("【加载远程配置文件】没有配置远程配置文件地址");
            return;
        }
        
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(configFileUrl));
            
            PropertiesPropertySource propertySource = new PropertiesPropertySource("Custom", properties);

            applicationContext.getEnvironment().getPropertySources().addFirst(propertySource);
            
        } catch (IOException e) {
            log.error("【加载远程配置文件失败】", e);
//            throw new IllegalStateException("加载远程配置文件失败：" + configFileUrl);
        }

    }

}
