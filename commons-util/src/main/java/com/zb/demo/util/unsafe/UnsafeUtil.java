package com.zb.demo.util.unsafe;

import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Unsafe实例
 *
 * Created by Administrator on 2018/5/25 0025.
 */
@Slf4j
public class UnsafeUtil {

    /**
     * 通过反射获取Unsafe
     *
     * @return
     * @throws Exception
     */
    public static Unsafe getUnsafe() {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            //因为 Unsafe 的 theUnsafe 字段是private 的，所以这里需要设置成可访问的
            field.setAccessible(true);
            //Unsafe 的这个属性 theUnsafe 是静态的所以这里的get参数就是null
            Unsafe unsafe = (Unsafe) field.get(null);

            return unsafe;
        } catch (Exception e) {
            log.error("获取Unsafe失败", e);
        }
        return null;
    }

}
