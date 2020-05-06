package com.zb.springboot.demo.track;

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

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        Method method = invocation.getMethod();
        Track track = method.getDeclaredAnnotation(Track.class);
        if (track != null) {
            track(invocation, method, track);
        }

        return invocation.proceed();
    }

    private void track(MethodInvocation invocation, Method method, Track track) {
        TrackEvent trackEvent = new TrackEvent();
        trackEvent.setApplication(track.application());
        trackEvent.setEventType(track.eventType());

        if (track.parameters()) {
            Parameter[] parameters = method.getParameters();

            if (parameters != null && parameters.length > 0) {
                Object[] arguments = invocation.getArguments();
                Map<String, Object> attach = new HashMap<>();
                for (int i = 0; i < parameters.length; i++) {
                    if (arguments[i] != null) {
                        attach.put(parameters[i].getName(), arguments[i]);
                    }
                }
                trackEvent.setAttach(attach);
            }
        }

        TrackSender.send(trackEvent);
    }
}
