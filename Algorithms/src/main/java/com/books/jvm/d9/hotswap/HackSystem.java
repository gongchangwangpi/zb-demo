//package com.books.jvm.d9.hotswap;
//
//import java.io.*;
//import java.nio.channels.Channel;
//import java.util.Properties;
//
///**
// * 为JavaClass劫持 java.lang.System提供支持
// * 除了 out 和 err 外，其余的都直接转发给System处理
// *
// * Created by Administrator on 2017/5/15 0015.
// */
//public class HackSystem {
//
//    public static final InputStream in = System.in;
//
//    private static ByteArrayOutputStream buffer = new ByteArrayOutputStream();
//
//    public static final PrintStream out = new PrintStream(buffer);
//
//    public static final PrintStream err = out;
//
//    public static String getBufferString() {
//        return buffer.toString();
//    }
//
//    public static void clearBuffer() {
//        buffer.reset();
//    }
//
//    public static void setSecurityManager(final SecurityManager s) {
//        System.setSecurityManager(s);
//    }
//
//    public static SecurityManager getSecurityManager() {
//        return System.getSecurityManager();
//    }
//
//    public static long currentTimeMillis() {
//        return System.currentTimeMillis();
//    }
//
//    public static void aeeayCopy(Object src, int srcPos, Object dest, int destPos, int length) {
//        System.arraycopy(src, srcPos, dest, destPos, length);
//    }
//
//    public static int identityHashCOde(Object x) {
//        return System.identityHashCode(x);
//    }
//
//    // 补齐其他System的方法，都是转发给System来具体操作
//    public static Channel inheritedChannel() throws IOException {
//        return System.inheritedChannel();
//    }
//    private static volatile Console cons = null;
//    public static Console console() {
//        if (cons == null) {
//            synchronized (System.class) {
//                cons = sun.misc.SharedSecrets.getJavaIOAccess().console();
//            }
//        }
//        return cons;
//    }
//
//    public static long nanoTime() {
//        return System.nanoTime();
//    }
//
//    public static Properties getProperties() {
//        return System.getProperties();
//    }
//
//    public static String lineSeparator() {
//        return System.lineSeparator();
//    }
//
//    public static void setProperties(Properties props) {
//        System.setProperties(props);
//    }
//
//    public static String getProperty(String key) {
//        return System.getProperty(key);
//    }
//
//    public static String getProperty(String key, String def) {
//        return System.getProperty(key, def);
//    }
//
//    public static String setProperty(String key, String value) {
//        return System.setProperty(key, value);
//    }
//
//    public static String clearProperty(String key) {
//        return System.clearProperty(key);
//    }
//
//    public static String getenv(String name) {
//        return System.getenv(name);
//    }
//
//    public static java.util.Map<String,String> getenv() {
//        return System.getenv();
//    }
//
//    public static void exit(int status) {
//        System.exit(status);
//    }
//
//    public static void gc() {
//        System.gc();
//    }
//
//    public static void runFinalization() {
//        System.runFinalization();
//    }
//
//    public static void runFinalizersOnExit(boolean value) {
//        System.runFinalizersOnExit(value);
//    }
//
//    public static void load(String filename) {
//        System.load(filename);
//    }
//
//    public static void loadLibrary(String libname) {
//        System.loadLibrary(libname);
//    }
//
//    public static String mapLibraryName(String libname) {
//        return System.mapLibraryName(libname);
//    }
//
//    public static void setIn(InputStream in) {
//        System.setIn(in);
//    }
//
//}
