package com.books.jvm.d2_4;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Java方法区OOM
 *
 * Created by Administrator on 2017/3/21 0021.
 */
public class JavaMethodAreaOOM {

    public static void main(final String[] args) {

        while (true) {

            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(OOMObject.class);
            enhancer.setUseCache(false);
            enhancer.setCallback(new MethodInterceptor() {
                @Override
                public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                    return methodProxy.invokeSuper(o, args);
                }
            });

            enhancer.create();

        }

    }

    static class OOMObject {

    }

}
