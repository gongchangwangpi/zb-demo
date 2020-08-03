package com.jdksource.proxy.cglib;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;

/**
 * @author zhangbo
 */
public class CglibTest {

    public static void main(String[] args) {

        // 保存生成的字节码
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, System.getProperty("user.dir"));

        // 创建一个织入器
        Enhancer enhancer = new Enhancer();
        // 设置父类
        // 如果父类HelloServiceImpl是final修饰的，报错
        // 如果父类的方法是final修饰的，可以执行被代理方法的逻辑，但额外的代理逻辑不会执行
        enhancer.setSuperclass(HelloServiceImpl.class);
        // 设置需要织入的逻辑
        enhancer.setCallback(new LogInterceptor());
        // 使用织入器创建子类
//        HelloService helloService = (HelloService) enhancer.create();
        HelloService helloService = (HelloService) enhancer.create();
        // 实际调用的是子类的hello()方法，里面调用了LogInterceptor.intercept()方法
        // methodProxy.invokeSuper(target, args)
        // 最终调用生成类的CGLIB$hello$0(){super.hello();}
        String res = helloService.hello("test");

        System.out.println("return = " + res);

    }
    
    
    // 生成的子类核心逻辑如下：
    
    //    final String CGLIB$hello$0(String var1) {
    //        return super.hello(var1);
    //    }
    //
    //    public final String hello(String var1) {
    //      var10000 为自己编写的LogInterceptor
    //        MethodInterceptor var10000 = this.CGLIB$CALLBACK_0;
    //        if (var10000 == null) {
    //            CGLIB$BIND_CALLBACKS(this);
    //            var10000 = this.CGLIB$CALLBACK_0;
    //        }
    //
    //        return var10000 != null ? (String)var10000.intercept(this, CGLIB$hello$0$Method, new Object[]{var1}, CGLIB$hello$0$Proxy) : super.hello(var1);
    //    }
    
    // 


    // ----------------------------------------
    // MethodProxy.invokeSuper
    // public Object invokeSuper(Object obj, Object[] args) throws Throwable {
    //        try {
    //            init();
    //            FastClassInfo fci = fastClassInfo;
    //      fci.f2 也是动态生成的，如下
    //            return fci.f2.invoke(fci.i2, obj, args);
    //        } catch (InvocationTargetException e) {
    //            throw e.getTargetException();
    //        }
    //    }
    
    // ----------------------------------------
    // public class HelloServiceImpl$$EnhancerByCGLIB$$4cf0a51$$FastClassByCGLIB$$28ee97dd extends FastClass
    
    // public Object invoke(int var1, Object var2, Object[] var3) throws InvocationTargetException {
    //        4cf0a51 var10000 = (4cf0a51)var2;
    //        int var10001 = var1;
    //
    //        try {
    //            switch(var10001) {
    //            case 0:
    //                return new Boolean(var10000.equals(var3[0]));
    //            case 1:
    //                return var10000.toString();
    //            case 2:
    //                return new Integer(var10000.hashCode());
    //            case 3:
    //                return var10000.newInstance((Class[])var3[0], (Object[])var3[1], (Callback[])var3[2]);
    //            case 4:
    //                return var10000.newInstance((Callback)var3[0]);
    //            case 5:
    //                return var10000.newInstance((Callback[])var3[0]);
    //            case 6:
    //                var10000.setCallback(((Number)var3[0]).intValue(), (Callback)var3[1]);
    //                return null;
    //            case 7:
    //                return var10000.hello((String)var3[0]);
    //            case 8:
    //                4cf0a51.CGLIB$SET_STATIC_CALLBACKS((Callback[])var3[0]);
    //                return null;
    //            case 9:
    //                4cf0a51.CGLIB$SET_THREAD_CALLBACKS((Callback[])var3[0]);
    //                return null;
    //            case 10:
    //                var10000.setCallbacks((Callback[])var3[0]);
    //                return null;
    //            case 11:
    //                return var10000.getCallback(((Number)var3[0]).intValue());
    //            case 12:
    //                return var10000.getCallbacks();
    //            case 13:
    //                return 4cf0a51.CGLIB$findMethodProxy((Signature)var3[0]);
    //            case 14:
    //                4cf0a51.CGLIB$STATICHOOK1();
    //                return null;
    //            case 15:
    //                return var10000.CGLIB$clone$5();
    //            case 16:
    //          此处调用代理子类的CGLIB$hello$0方法，在里面调用父类的hello() // super.hello()
    //                return var10000.CGLIB$hello$0((String)var3[0]);
    //            case 17:
    //                return new Boolean(var10000.CGLIB$equals$2(var3[0]));
    //            case 18:
    //                return new Integer(var10000.CGLIB$hashCode$4());
    //            case 19:
    //                return var10000.CGLIB$toString$3();
    //            case 20:
    //                var10000.CGLIB$finalize$1();
    //                return null;
    //            case 21:
    //                var10000.wait();
    //                return null;
    //            case 22:
    //                var10000.wait(((Number)var3[0]).longValue(), ((Number)var3[1]).intValue());
    //                return null;
    //            case 23:
    //                var10000.wait(((Number)var3[0]).longValue());
    //                return null;
    //            case 24:
    //                return var10000.getClass();
    //            case 25:
    //                var10000.notify();
    //                return null;
    //            case 26:
    //                var10000.notifyAll();
    //                return null;
    //            }
    //        } catch (Throwable var4) {
    //            throw new InvocationTargetException(var4);
    //        }
    //
    //        throw new IllegalArgumentException("Cannot find matching method/constructor");
    //    }
}
