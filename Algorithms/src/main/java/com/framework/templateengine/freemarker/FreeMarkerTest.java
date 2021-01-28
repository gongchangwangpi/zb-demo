package com.framework.templateengine.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author bo6.zhang
 * @date 2021/1/28
 */
public class FreeMarkerTest {

    public static void main(String[] args) throws Exception {

        String templateDir = "D:\\IdeaProjects\\github\\zb-demo\\Algorithms\\src\\main\\java\\com\\framework\\templateengine\\freemarker\\template\\";
        // 获取配置
        Configuration cfg = getConfiguration(templateDir);
        // 获取模板
        Template temp = cfg.getTemplate("Controller.flt");
        // 准备数据
        for (int i = 0; i < 30; i++) {
            String className = "A";
            String path = "a";
            if (i < 10) {
                className = className + "0" + i;
                path = path + "0" + i;
            } else {
                className = className + i;
                path = path + i;
            }
            Map<String, String> root = new HashMap<>();
            root.put("className", className);
            root.put("path", path);
            // 合成
            Writer out = new FileWriter("D:\\IdeaProjects\\github\\zb-demo\\springboot-demo\\src\\main\\java\\com\\zb\\springboot\\demo\\controller\\test\\" + className + "Controller.java");
            temp.process(root, out);
        }
    }

    private static Configuration getConfiguration(String templateDir) throws IOException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);

        cfg.setDirectoryForTemplateLoading(new File(templateDir));

        cfg.setDefaultEncoding("UTF-8");

        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        return cfg;
    }

}
