//package com.books.jvm_sz.chapter2;
//
//import java.lang.reflect.Method;
//
//import jdk.internal.org.objectweb.asm.ClassWriter;
//import jdk.internal.org.objectweb.asm.MethodVisitor;
//import jdk.internal.org.objectweb.asm.Opcodes;
//
///**
// * -verbose:class
// * -XX:TraceClassLoading -XX:TraceClassUnloading
// *
// *
// * @author zhangbo
// */
//public class UnloadClassLoader implements Opcodes {
//
//    public static void main(String[] args) throws Exception {
//
//        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
//
//        classWriter.visit(V1_7, ACC_PUBLIC, "Example", null, "java/lang/Object", null);
//
//        MethodVisitor constructor = classWriter.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
//
//        constructor.visitVarInsn(ALOAD, 0);
//        constructor.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
//        constructor.visitInsn(RETURN);
//        constructor.visitMaxs(0, 0);
//        constructor.visitEnd();
//
//        MethodVisitor main = classWriter.visitMethod(ACC_PUBLIC + ACC_STATIC, "main", "(Ljava/lang/String;)V", null, null);
//        main.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
//        main.visitLdcInsn("Hello World...");
//        main.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");
//        main.visitInsn(RETURN);
//        main.visitMaxs(0, 0);
//        main.visitEnd();
//
//        byte[] code = classWriter.toByteArray();
//
//        for (int i = 0; i < 10; i++) {
//            ClassLoader unloadClassLoader = new ClassLoader() {};
//
//            Method defineClassMethod = ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class, int.class, int.class);
//            defineClassMethod.setAccessible(true);
//            Object example = defineClassMethod.invoke(unloadClassLoader, "Example", code, 0, code.length);
//            defineClassMethod.setAccessible(false);
//
//            Class clz = (Class) example;
//            clz.getDeclaredMethods()[0].invoke(null, new String[] {null});
//
//            System.gc();
//        }
//
//    }
//
//}
