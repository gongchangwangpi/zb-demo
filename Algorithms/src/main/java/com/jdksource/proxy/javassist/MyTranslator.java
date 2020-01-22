package com.jdksource.proxy.javassist;

import javassist.*;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangbo
 */
@Slf4j
public class MyTranslator implements Translator {
    
    @Override
    public void start(ClassPool classPool) throws NotFoundException, CannotCompileException {
        
    }

    /**
     * 类装载到JVM前进行代码织入
     */
    @Override
    public void onLoad(ClassPool classPool, String classname) throws NotFoundException, CannotCompileException {
        if (!"com.jdksource.proxy.javassist.Hello".equals(classname)) {
            return;
        }
        
        //通过获取类文件
        try {
            CtClass cc = classPool.get(classname);
            
            //获得指定方法名的方法
            CtMethod m = cc.getDeclaredMethod("hello");

            m.insertAt(12, "log.info(\"Javassisst: {}\", name);");
            //在方法执行前插入代码
            m.insertBefore("{ System.out.println(\"before 记录日志\"); }");
            m.insertAfter("{ System.out.println(\"after 记录日志\"); }");
            
        } catch (NotFoundException e) {
            
        } catch (CannotCompileException e) {
            
        }
    }

    public static void main(String[] args) {

        Hello hello = new Hello();
        
        String res1 = hello.hello("test1");
        
        System.out.println("hello: " + res1);

    }
}
