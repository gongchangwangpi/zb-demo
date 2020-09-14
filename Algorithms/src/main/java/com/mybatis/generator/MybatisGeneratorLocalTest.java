package com.mybatis.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.books.jdbc.JdbcUtil;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static java.util.stream.Collectors.groupingBy;

@Slf4j
public class MybatisGeneratorLocalTest {

    // postgresql/redshift等使用
//    private static final DbType DB_TYPE = DbType.POSTGRE_SQL;
//    private static final String SCHEMA_NAME = "schema_test";
//    private static final String DRIVER_CLASS_NAME = "com.amazon.redshift.jdbc.Driver";
//    private static final String JDBC_URL = "jdbc:redshift://127.0.0.1:5439/redshift_test?useSSL=false&useUnicode=true&characterEncoding=UTF-8";
//    private static final String JDBC_USERNAME = "root";
//    private static final String JDBC_PASSWORD = "123456";

    private static final DbType DB_TYPE = DbType.MYSQL;
    private static final String SCHEMA_NAME = null;
    private static final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";

    private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/baby_joy?useSSL=false&useUnicode=true&characterEncoding=UTF-8";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "123456";

    private static final String TABLE_NAMES = "dm_product_count_mx";
    private static final String TABLE_PREFIX = "dm_";

    private static final String[] SUPER_ENTITY_COLUMNS = {};
    private static final String PACKAGE_NAME = "com.zb.demo";

    private static final String AUTHOR = "zhangbo";
    private static final boolean SWAGGER_2 = false;

    public static void main(String[] args) throws Exception {

        // 生成全库所有表的entity，mapper，service等
        Connection connection = JdbcUtil.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);

        Statement statement = connection.createStatement();
        ResultSet tablesResultSet = statement.executeQuery("show tables");

        List<String> tableNameList = new ArrayList<>();
        while (tablesResultSet.next()) {
            log.info(tablesResultSet.getString(1));
            tableNameList.add(tablesResultSet.getString(1));
        }
        log.info("共获取到table {}个", tableNameList.size());

        Map<String, List<String>> prefixTableNameList = tableNameList.stream().sorted().collect(groupingBy(s -> {
            int i = s.indexOf("_");
            return s.substring(0, i + 1);
        }));

        prefixTableNameList.forEach(MybatisGeneratorLocalTest::generate);

    }

    private static void generate(String tablePrefix, List<String> tableNames) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir") + "/Algorithms";
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor(AUTHOR);
        gc.setOpen(false);
        gc.setBaseColumnList(true);
        gc.setBaseResultMap(true);
        gc.setSwagger2(SWAGGER_2);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(JDBC_URL);
        // POSTGRE_SQL的schema_name
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(SCHEMA_NAME)) {
            dsc.setSchemaName(SCHEMA_NAME);
        }
        dsc.setDbType(DB_TYPE);
        dsc.setDriverName(DRIVER_CLASS_NAME);
        dsc.setUsername(JDBC_USERNAME);
        dsc.setPassword(JDBC_PASSWORD);
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
//        pc.setModuleName(scanner("模块名"));
        pc.setParent(PACKAGE_NAME);
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";

        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mappers/" /*+ pc.getModuleName()*/
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        // 指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();

        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityTableFieldAnnotationEnable(true);
//        strategy.setSuperEntityClass("com.baomidou.ant.common.BaseEntity");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // 公共父类
//        strategy.setSuperControllerClass("com.baomidou.ant.common.BaseController");
        // 写于父类中的公共字段
        strategy.setSuperEntityColumns(SUPER_ENTITY_COLUMNS);
//        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        // 表名，多个表用,隔开
        strategy.setInclude(tableNames.toArray(new String[0]));
        strategy.setControllerMappingHyphenStyle(true);
        // 表名前缀
        strategy.setTablePrefix(tablePrefix);
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());

        mpg.execute();
    }

    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

}
