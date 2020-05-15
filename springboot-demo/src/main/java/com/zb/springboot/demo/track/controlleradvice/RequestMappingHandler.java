package com.zb.springboot.demo.track.controlleradvice;

import com.zb.springboot.demo.track.TrackEvent;
import com.zb.springboot.demo.track.TrackSender;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangbo
 * @date 2020/4/29
 */
public class RequestMappingHandler implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object resp = invocation.proceed();
        Object aThis = invocation.getThis();
        AccessibleObject staticPart = invocation.getStaticPart();

        Method method = invocation.getMethod();
        RequestMapping requestMapping = method.getDeclaredAnnotation(RequestMapping.class);
        if (requestMapping != null) {
            String path = requestMapping.value()[0];
            track(invocation, method, path, resp);
        }

        return resp;
    }

    private void track(MethodInvocation invocation, Method method, String path, Object resp) {
        TrackEvent trackEvent = new TrackEvent();
        trackEvent.setEventType(path);

        Parameter[] parameters = method.getParameters();
        if (parameters != null && parameters.length > 0) {
            Object[] arguments = invocation.getArguments();
            Map<String, Object> attach = new HashMap<>();
            for (int i = 0; i < parameters.length; i++) {
                if (arguments[i] != null) {
                    attach.put(parameters[i].getName(), arguments[i]);
                }
            }
            trackEvent.setParameters(attach);
        }

        trackEvent.setResponse(resp);

        TrackSender.send(trackEvent);
    }
}
