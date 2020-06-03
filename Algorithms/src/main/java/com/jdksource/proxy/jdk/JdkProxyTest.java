package com.jdksource.proxy.jdk;

import lombok.extern.slf4j.Slf4j;
import sun.misc.ProxyGenerator;

import java.lang.reflect.Proxy;

/**
 * @author zhangbo
 */
@Slf4j
public class JdkProxyTest {

    public static void main(String[] args) throws Exception {
        
        // 保存生成的代理类字节码文件
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        byte[] $Proxy0s = ProxyGenerator.generateProxyClass("$Proxy0", new Class[]{HelloService.class});

        Class[] proxyInterface = {IHelloService.class, IHelloService2.class};

        HelloService helloService = new HelloService();

        LogInvocationHandler logInvocationHandler = new LogInvocationHandler(helloService);

        ClassLoader classLoader = JdkProxyTest.class.getClassLoader();

        Object proxyInstance = Proxy.newProxyInstance(classLoader, proxyInterface, logInvocationHandler);
        
        String res = ((IHelloService) proxyInstance).hello("zhangsan");
        String res2 = ((IHelloService2) proxyInstance).hello2("zhangsan");
        
        log.info("helloService: {}", helloService);
        log.info("proxyInstance: {}", proxyInstance);
        log.info("result: {}", res);
        log.info("result2: {}", res2);

    }
    
    // 生成代理类如下： 
    
    //package com.sun.proxy;
    //
    //import com.jdksource.proxy.jdk.IHelloService;
    //import com.jdksource.proxy.jdk.IHelloService2;
    //import java.lang.reflect.InvocationHandler;
    //import java.lang.reflect.Method;
    //import java.lang.reflect.Proxy;
    //import java.lang.reflect.UndeclaredThrowableException;
    //
    //public final class $Proxy0 extends Proxy implements IHelloService, IHelloService2 {
    //    private static Method m1;
    //    private static Method m5;
    //    private static Method m2;
    //    private static Method m3;
    //    private static Method m4;
    //    private static Method m0;
    //
    //    public $Proxy0(InvocationHandler var1) throws  {
    //        super(var1);
    //    }
    //
    //    public final boolean equals(Object var1) throws  {
    //        try {
    //            return (Boolean)super.h.invoke(this, m1, new Object[]{var1});
    //        } catch (RuntimeException | Error var3) {
    //            throw var3;
    //        } catch (Throwable var4) {
    //            throw new UndeclaredThrowableException(var4);
    //        }
    //    }
    //
    //    public final String hello2(String var1) throws  {
    //        try {
    //          此处调用自定义的LogInvocationHandler的invoke方法，里面使用反射调用目标方法
    //            return (String)super.h.invoke(this, m5, new Object[]{var1});
    //        } catch (RuntimeException | Error var3) {
    //            throw var3;
    //        } catch (Throwable var4) {
    //            throw new UndeclaredThrowableException(var4);
    //        }
    //    }
    //
    //    public final String toString() throws  {
    //        try {
    //            return (String)super.h.invoke(this, m2, (Object[])null);
    //        } catch (RuntimeException | Error var2) {
    //            throw var2;
    //        } catch (Throwable var3) {
    //            throw new UndeclaredThrowableException(var3);
    //        }
    //    }
    //
    //    public final String hello(String var1) throws  {
    //        try {
    //            return (String)super.h.invoke(this, m3, new Object[]{var1});
    //        } catch (RuntimeException | Error var3) {
    //            throw var3;
    //        } catch (Throwable var4) {
    //            throw new UndeclaredThrowableException(var4);
    //        }
    //    }
    //
    //    public final void defaultSayHi() throws  {
    //        try {
    //            super.h.invoke(this, m4, (Object[])null);
    //        } catch (RuntimeException | Error var2) {
    //            throw var2;
    //        } catch (Throwable var3) {
    //            throw new UndeclaredThrowableException(var3);
    //        }
    //    }
    //
    //    public final int hashCode() throws  {
    //        try {
    //            return (Integer)super.h.invoke(this, m0, (Object[])null);
    //        } catch (RuntimeException | Error var2) {
    //            throw var2;
    //        } catch (Throwable var3) {
    //            throw new UndeclaredThrowableException(var3);
    //        }
    //    }
    //
    //    static {
    //        try {
    //            m1 = Class.forName("java.lang.Object").getMethod("equals", Class.forName("java.lang.Object"));
    //            m5 = Class.forName("com.jdksource.proxy.jdk.IHelloService2").getMethod("hello2", Class.forName("java.lang.String"));
    //            m2 = Class.forName("java.lang.Object").getMethod("toString");
    //            m3 = Class.forName("com.jdksource.proxy.jdk.IHelloService").getMethod("hello", Class.forName("java.lang.String"));
    //            m4 = Class.forName("com.jdksource.proxy.jdk.IHelloService").getMethod("defaultSayHi");
    //            m0 = Class.forName("java.lang.Object").getMethod("hashCode");
    //        } catch (NoSuchMethodException var2) {
    //            throw new NoSuchMethodError(var2.getMessage());
    //        } catch (ClassNotFoundException var3) {
    //            throw new NoClassDefFoundError(var3.getMessage());
    //        }
    //    }
    //}
}
