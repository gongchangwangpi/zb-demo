package com.zb.fund.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author zhangbo
 * @date 2019-08-16
 */
@Slf4j
@Component
public class DateConversionService implements ConversionService {

    @Override
    public boolean canConvert(Class<?> sourceType, Class<?> targetType) {
        return sourceType.isAssignableFrom(String.class) && targetType.isAssignableFrom(Date.class);
    }

    @Override
    public boolean canConvert(TypeDescriptor sourceType, TypeDescriptor targetType) {
        return canConvert(sourceType.getType(), targetType.getType());
    }

    @Override
    public <T> T convert(Object source, Class<T> targetType) {
        String str = (String) source;
        return (T) new Date(Long.parseLong(str));
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        String str = (String) source;
        return new Date(Long.parseLong(str));
    }
}
