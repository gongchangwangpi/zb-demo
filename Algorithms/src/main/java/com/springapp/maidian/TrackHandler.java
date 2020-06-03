package com.springapp.maidian;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangbo
 * @date 2020/4/29
 */
public class TrackHandler implements MethodInterceptor {

    private String application;
    private String eventType;

    public TrackHandler(String application, String eventType) {
        this.application = application;
        this.eventType = eventType;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        TrackEvent trackEvent = new TrackEvent();
        trackEvent.setApplication(application);
        trackEvent.setEventType(eventType);

        Method method = invocation.getMethod();
        Parameter[] parameters = method.getParameters();
        if (parameters != null && parameters.length > 0) {
            Object[] arguments = invocation.getArguments();
            Map<String, Object> attach = new HashMap<>();
            for (int i = 0; i < parameters.length; i++) {
                attach.put(parameters[i].getName(), arguments[i]);
            }
            trackEvent.setAttach(attach);
        }

        TrackSender.send(trackEvent);

        return invocation.proceed();
    }
}
