package com.jdksource.proxy.instrumentation;

/**
 * 需要告诉 JVM 在启动 main 函数之前，需要先执行 premain 函数。 
 * 首先需要将 premain函数所在的类打成 jar 包。
 * 并修改该 jar 包里的 META-INF\MANIFEST.MF 文件。
 *    Manifest-Version: 1.0
 *    Premain-Class: com.jdksource.proxy.instrumentation.MyAgent
 * 然后在 JVM 的启动参数里加上。
 *    -javaagent:E:\workspaceIDEA\Algorithms\target\Algorithms-0.0.1-SNAPSHOT.jar
 * 
 * 然后在运行本main方法
 * 
 * @author zhangbo
 */
public class Test {

    public static void main(String[] args) {

        Hello hello = new Hello();

        String res = hello.hello("instrumentation");

        System.out.println("----->>> " + res);

    }
    
}
