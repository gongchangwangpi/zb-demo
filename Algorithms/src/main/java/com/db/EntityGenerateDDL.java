package com.db;

import com.baomidou.mybatisplus.annotation.TableName;
import com.google.common.base.CaseFormat;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author zhangbo
 * @date 2020/10/12
 */
@Slf4j
public class EntityGenerateDDL {

    public static void main(String[] args) {

        Class<TableTest> clz = TableTest.class;

        String tableName = "";
        String tableDesc = "用户信息表";
//
//        ddl2Redshift(clz, tableName, tableDesc);
        ddl2Mysql(clz, tableName, tableDesc);

    }

    private static String ddl2Mysql(Class<?> clz, String tableName, String tableDesc) {
        tableName = validateTableName(clz, tableName);
        StringBuilder sb = new StringBuilder();
        sb.append("create table if not exists ")
                .append(tableName)
                .append(" (\n");

        boolean hadId = false;
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            fieldName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldName);
            sb.append("`").append(fieldName).append("` ").append(getDbColumnType(field.getType()));
            if ("id".equals(fieldName)) {
                hadId = true;
                sb.append(" not null auto_increment comment '自增ID'");
            }
            sb.append(",\n");
        }
        if (hadId) {
            sb.append("primary key (id) \n");
        } else {
            sb.deleteCharAt(sb.length() - 2);
        }

        if (StringUtils.isEmpty(tableDesc)) {
            sb.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;");
        } else {
            sb.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '")
                    .append(tableDesc)
                    .append("';");
        }

        System.out.println(sb);
        return sb.toString();
    }

    private static String getDbColumnType(Class<?> fieldClass) {
        String simpleName = fieldClass.getSimpleName();
        boolean primitive = fieldClass.isPrimitive();
        if (primitive) {
            if ("long".equalsIgnoreCase(simpleName)) {
                return "bigint";
            } else if ("double".equalsIgnoreCase(simpleName)) {
                return "double";
            } else if ("float".equalsIgnoreCase(simpleName)) {
                return "float";
            } else if ("char".equalsIgnoreCase(simpleName)) {
                return "varchar(64)";
            } else {
                return "int";
            }
        } else {
            if (Long.class.equals(fieldClass)) {
                return "bigint";
            } else if (Double.class.equals(fieldClass)) {
                return "double";
            } else if (Float.class.equals(fieldClass)) {
                return "float";
            } else if (fieldClass.isAssignableFrom(Number.class)) {
                return "decimal(20,2)";
            } else if (Date.class.equals(fieldClass)) {
                return "datetime";
            } else if (java.sql.Date.class.equals(fieldClass)) {
                return "datetime";
            } else if (LocalDate.class.equals(fieldClass)) {
                return "date";
            } else if (LocalDateTime.class.equals(fieldClass)) {
                return "datetime";
            } else {
                return "varchar(64)";
            }
        }
    }

    private static <T> String ddl2Redshift(Class<T> clz, String tableName, String tableDesc) {
        tableName = validateTableName(clz, tableName);

        StringBuilder sb = new StringBuilder();
        sb.append("create table ")
                .append(tableName)
                .append(" (\n");

        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            sb.append("\"").append(fieldName).append("\" varchar(1024),\n");
        }

        sb.append("\"record_time\" timestamp DEFAULT ('now'::character varying)::timestamp with time zone\n")
                .append(")WITH OIDS;\n");

        /*for (Field field : fields) {
            TableField tableField = field.getAnnotation(TableField.class);
            if (tableField != null && StringUtils.isNotEmpty(tableField.value())) {
                sb.append("comment on column ")
                        .append(field.getName())
                        .append(" is \"")
                        .append(tableField.value())
                        .append("\";\n");
            }
        }*/

        if (StringUtils.isNotEmpty(tableDesc)) {
            sb.append("comment on table ")
                    .append(tableName)
                    .append(" is \"")
                    .append(tableDesc)
                    .append("\";");
        }

        System.out.println(sb);
        return sb.toString();
    }

    private static <T> String validateTableName(Class<T> clz, String tableName) {
        if (StringUtils.isBlank(tableName)) {
            TableName tableNameAnno = clz.getDeclaredAnnotation(TableName.class);
            if (tableNameAnno != null) {
                tableName = tableNameAnno.value();
            }
        }

        if (StringUtils.isBlank(tableName)) {
            tableName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, clz.getSimpleName());
            log.warn("table name is empty, default class name to lower underscore: {}", tableName);
        }
        return tableName;
    }

    @Data
    public static class TableTest {
//        private Long id;
        private String username;
        private String password;
        private Integer age;
        private LocalDate birthday;
        private double score;
        private LocalDateTime createTime;
    }

}
