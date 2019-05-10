package com.dubbo.test.compiler;

/**
 * @author zhangbo
 */
public class JavaSourceUtil {
    
    public static final String ln = "\r\n";
    
    public static String getSource() {
        StringBuilder sb = new StringBuilder();
        sb.append("public class Hello {").append(ln);
        sb.append("public String hello() {").append(ln);
        sb.append("System.out.println(\"hello,world\");").append(ln);
        sb.append("return \"hello,world\";").append(ln);
        sb.append("}").append(ln);
        sb.append("}");
        
        return sb.toString();
    }
    
}
