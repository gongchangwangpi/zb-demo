package com.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cglib.core.Converter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author zhangbo
 * @date 2020/4/23
 */
public class Dto2EntityConverter implements Converter {

    private static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    @Override
    public Object convert(Object propertyValue, Class targetPropertyClass, Object property) {

        if (LocalDateTime.class.equals(targetPropertyClass)) {
            return convertLocalDateTime(propertyValue, property);
        } else if (BigDecimal.class.equals(targetPropertyClass)) {
            return convertBigDecimal(propertyValue, property);
        } else if (Integer.class.equals(targetPropertyClass)) {
            return convertInteger(propertyValue, property);
        }

        return propertyValue;
    }

    private Object convertInteger(Object propertyValue, Object property) {
        if (propertyValue instanceof String) {
            String v = (String) propertyValue;
            if (StringUtils.isNotEmpty(v)) {
                return Integer.valueOf(v);
            }
        }
        return null;
    }

    private Object convertBigDecimal(Object propertyValue, Object property) {
        if (propertyValue instanceof String) {
            String v = (String) propertyValue;
            if (StringUtils.isNotEmpty(v)) {
                return new BigDecimal(v);
            }
        }
        return null;
    }

    private Object convertLocalDateTime(Object propertyValue, Object property) {
        if (propertyValue instanceof String) {
            String v = (String) propertyValue;
            if (StringUtils.isNotEmpty(v)) {
                return LocalDateTime.parse(v, DateTimeFormatter.ofPattern(DATETIME_PATTERN));
            }
        }
        return null;
    }
}
