package com.dubbo.test.compiler;

import com.alibaba.dubbo.common.compiler.support.JavassistCompiler;

import java.lang.reflect.Method;

/**
 * @author zhangbo
 */
public class JavassistCompilerTest {

    public static void main(String[] args) throws Throwable {

        String source = JavaSourceUtil.getSource();

        JavassistCompiler compiler = new JavassistCompiler();

        Class<?> clz = compiler.compile(source, Thread.currentThread().getContextClassLoader());
//        Class<?> clz = compiler.doCompile("Hello", source);

        Object o = clz.newInstance();
        Method method = clz.getDeclaredMethod("hello", null);

        System.out.println("class = " + o.getClass());
        Object result = method.invoke(o, null);
        System.out.println("result = " + result);

    }
    
}
