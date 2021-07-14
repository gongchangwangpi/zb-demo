package com.middlesoftware.dubbo.test.compiler;

import com.alibaba.dubbo.common.compiler.support.JdkCompiler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangbo
 */
public class JdkCompilerTest {

    public static void main(String[] args) throws Throwable {

        long begin = System.currentTimeMillis();

        String source = JavaSourceUtil.getSource();

        JdkCompiler compiler = new JdkCompiler();
        
        List<String> options = new ArrayList<>();
        options.add("-target");
        options.add("1.8");
        Field field = JdkCompiler.class.getDeclaredField("options");
        
        field.setAccessible(true);
        field.set(compiler, options);

        Class<?> clz = compiler.compile(source, Thread.currentThread().getContextClassLoader());
//       Class<?> clz = compiler.doCompile("Hello", source);

        Object o = clz.newInstance();
        Method method = clz.getDeclaredMethod("hello", null);

        System.out.println("class = " + o.getClass());
        Object result = method.invoke(o, null);
        System.out.println("result = " + result);

        System.out.println("total cost: " + (System.currentTimeMillis() - begin));
    }
    
}
