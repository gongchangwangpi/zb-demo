package com.jdksource.proxy.javassist;

import javassist.ClassPool;
import javassist.Loader;

/**
 * @author zhangbo
 */
public class Test {

    public static void main(String[] args) throws Throwable {

        //获取存放CtClass的容器ClassPool
        ClassPool cp = ClassPool.getDefault();
        
        //创建一个类加载器
        Loader cl = new Loader();
        
        //增加一个转换器
        cl.addTranslator(cp, new MyTranslator());
        
        //启动MyTranslator的main函数
        cl.run("com.jdksource.proxy.javassist.MyTranslator", args);
        
    }
    
}
