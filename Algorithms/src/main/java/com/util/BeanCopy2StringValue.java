package com.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.core.ReflectUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于BeanCopier copy对象属性时，将Date和BigDecimal转换为String
 * 
 * 
 * @author zhangbo
 */
public class BeanCopy2StringValue {

    private static Logger logger = LoggerFactory.getLogger(BeanCopy2StringValue.class);
    
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");
    
    private static final String PERCENT = "%";
    
    public static void copy(Object source, Object target) {
        if (source == null) {
            throw new IllegalArgumentException("bean copy source is null");
        }
        if (target == null) {
            throw new IllegalArgumentException("bean copy target is null");
        }
        
        PropertyDescriptor[] getters = ReflectUtils.getBeanGetters(source.getClass());
        PropertyDescriptor[] setters = ReflectUtils.getBeanSetters(target.getClass());

        Map<String, PropertyDescriptor> getterMap = new HashMap<>(getters.length);

        for (PropertyDescriptor getter : getters) {
            getterMap.put(getter.getName(), getter);
        }

        for (int i = 0; i < setters.length; i++) {
            PropertyDescriptor setter = setters[i];
            String name = setter.getName();
            PropertyDescriptor getter = getterMap.get(name);
            if (getter == null) {
                continue;
            }
            try {
                Method readMethod = getter.getReadMethod();
                Object value = readMethod.invoke(source);
                if (value == null) {
                    continue;
                }

                Method writeMethod = setter.getWriteMethod();
                Field field = source.getClass().getDeclaredField(name);
                
                if (value instanceof Date) {
                    JsonFormat jsonFormat = field.getAnnotation(JsonFormat.class);
                    String pattern = jsonFormat.pattern();
                    value = new SimpleDateFormat(pattern).format(value);
                } else if (value instanceof BigDecimal) {
                    JsonSerialize jsonSerialize = field.getAnnotation(JsonSerialize.class);
                    Class<? extends JsonSerializer> using = jsonSerialize.using();
//                    if (using == BigDecimalPercentSerializer.class) {
//                        value = DECIMAL_FORMAT.format(value) + PERCENT;
//                    } else {
                        value = DECIMAL_FORMAT.format(value);
//                    }
                } else if (value instanceof Enum) {
                    value = value.toString();
                }
                writeMethod.invoke(target, value);
            } catch (Exception e) {
                logger.error("bean copy error", e);
                throw new IllegalArgumentException("数据转换失败");
            }
        }
    }

    public static void main(String[] args) throws Exception {

    }
}
