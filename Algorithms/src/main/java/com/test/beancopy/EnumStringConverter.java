package com.test.beancopy;

import org.springframework.cglib.core.Converter;

/**
 * @author zhangbo
 */
public class EnumStringConverter implements Converter {

    @Override
    public Object convert(Object value, Class targetPropertyType, Object property) {
        if (value == null) {
            return null;
        }
        if (value instanceof Enum) {
            if (targetPropertyType.equals(String.class)) {
                return ((Enum) value).name();
            }
            return value;
        }
        if (targetPropertyType.isEnum()) {
            if (value instanceof String) {
                return Enum.valueOf(targetPropertyType, (String) value);
            }
            return value;
        }
        return value;
    }
    
}
