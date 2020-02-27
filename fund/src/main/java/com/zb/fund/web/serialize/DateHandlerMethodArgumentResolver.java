package com.zb.fund.web.serialize;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Date;

/**
 * @author zhangbo
 * @date 2019-08-15
 */
public class DateHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType() == Date.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String param = webRequest.getParameter(parameter.getParameterName());
        if (StringUtils.isNotEmpty(param)) {
            return new Date(Long.parseLong(param));
        }
        return null;
    }
}
