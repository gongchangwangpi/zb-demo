package com.books.jvm.d9.hotswap;

import java.lang.reflect.Method;

/**
 * JavaClass执行工具
 *
 * Created by Administrator on 2017/5/15 0015.
 */
public class JavaClassExecuter {

    /**
     * 执行外部传过来的代表一个Java类的byte数组
     * 将输入了的byte数组中代表java.lang.System的CONSTANT_UTF8_INFO 常量修改为劫持后的HackSystem类
     * 执行方法为该类的 static main(String[] args)方法，输出结果Eric该类向System.out/err输出的信息
     *
     * @param classByte 代表一个Java类的byte数组
     * @return 执行结果
     */
    public static String execute(byte[] classByte) {
//        HackSystem.clearBuffer();
        ClassModifier cm = new ClassModifier(classByte);
        byte[] modiBytes = cm.modifyUTF8Constant("java/lang/System", "com/books/jvm/d9/hotswap/HackSystem");

        HotSwapClassLoader loader = new HotSwapClassLoader();
        Class clazz = loader.loadByte(modiBytes);

        try {
            Method method = clazz.getMethod("main", new Class[]{String[].class});
            method.invoke(null, new String[]{null});
        } catch (Exception e) {
//            e.printStackTrace(HackSystem.out);
        }
//        return HackSystem.getBufferString();
        return null;
    }

}
