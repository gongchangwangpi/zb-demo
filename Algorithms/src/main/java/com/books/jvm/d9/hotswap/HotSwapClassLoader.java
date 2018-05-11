package com.books.jvm.d9.hotswap;

/**
 * 为了多次载入执行类而加入的加载器
 * 把defineClass开发出来，只有外部显式调用的时候才会使用到loadByte方法
 * 由虚拟机调用时，仍然按照原有的双亲委派贵族使用loadClass方法进行类加载
 *
 * Created by Administrator on 2017/5/15 0015.
 */
public class HotSwapClassLoader extends ClassLoader {

    public HotSwapClassLoader() {
        super(HotSwapClassLoader.class.getClassLoader());
    }

    public Class loadByte(byte[] classByte) {
        return defineClass(null, classByte, 0, classByte.length);
    }
}
