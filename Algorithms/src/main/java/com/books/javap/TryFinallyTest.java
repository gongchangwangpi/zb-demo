package com.books.javap;

/**
 * @author zhangbo
 */
public class TryFinallyTest {

    public static void main(String[] args) {

        try {
            int i = 1 / 0;
            return;
        } finally {
            System.out.println("finally");
        }

        
        // 也会生成一个异常表,包含所有的异常类型,注意4-12为finally代码块的逻辑,
        // 13为编译器自动生成的异常处理, 14-19同样为finally代码块的逻辑,
        // major大于50的版本中(即从JDK7开始),
        // 编译器会为每个代码分支的出口都复制一份finally的逻辑,确保finally被执行
        
//        0: iconst_1
//        1: iconst_0
//        2: idiv
//        3: istore_1
//        4: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
//        7: ldc           #3                  // String finally
//        9: invokevirtual #4                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
//        12: return
//        13: astore_2
//        14: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
//        17: ldc           #3                  // String finally
//        19: invokevirtual #4                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
//        22: aload_2
//        23: athrow
//        Exception table:
//        from    to  target type
//        0     4    13   any

    }
    
}
