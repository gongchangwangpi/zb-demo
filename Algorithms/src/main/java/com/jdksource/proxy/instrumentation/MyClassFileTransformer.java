package com.jdksource.proxy.instrumentation;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import javassist.*;

/**
 * @author zhangbo
 */
public class MyClassFileTransformer implements ClassFileTransformer {

    /**
     * 字节码加载到虚拟机前会进入这个方法
     */
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        
        //如果加载Hello类才拦截
        if (!"com/jdksource/proxy/instrumentation/Hello".equals(className)) {
            return null;
        }
        
        //javassist的包名是用点分割的，需要转换下
        if (className.contains("/")) {
            className = className.replaceAll("/", ".");
        }
        
        try {
            //通过包名获取类文件
            CtClass cc = ClassPool.getDefault().get(className);
            
            //获得指定方法名的方法
            CtMethod m = cc.getDeclaredMethod("hello");
            
            //在方法执行前插入代码
            m.insertBefore("{ System.out.println(\" before  记录日志\"); }");
            
            m.insertAfter("{ System.out.println(\" after  记录日志\"); }");
            
            return cc.toBytecode();
            
        } catch (NotFoundException e) {
        } catch (CannotCompileException e) {
        } catch (IOException e) {
        //忽略异常处理
        }
        return null;
    }
}
