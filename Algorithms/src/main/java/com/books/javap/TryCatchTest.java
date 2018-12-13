package com.books.javap;

/**
 * @author zhangbo
 */
public class TryCatchTest {

    public static void main(String[] args) {

        try {
            int i = 1 / 0;
        } catch (ArithmeticException e) {
            System.out.println("ArithmeticException");
        } catch (Exception e) {
            System.out.println("Exception");
        }

        // 生成的异常表, 异常包含的字节码范围 [from, to), 异常后的处理逻辑, 异常类型
//        Exception table:
//        from    to  target type
//        0     4     7   Class java/lang/ArithmeticException
//        0     4    19   Class java/lang/Exception


    }
    
}
