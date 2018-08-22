package com.books.jvm_sz.chapter2;

import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

/**
 * -verbose:class
 * -XX:TraceClassLoading -XX:TraceClassUnloading
 * 
 * 
 * @author zhangbo
 */
public class UnloadClass implements Opcodes {

    public static void main(String[] args) {

        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        
        classWriter.visit(V1_7, ACC_PUBLIC, "Example", null, "java/lang/Object", null);

        MethodVisitor mv = classWriter.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
        


    }
    
}
