<%@ page import="java.lang.*" %>
<%@ page import="java.io.*" %>
<%@ page import="com.books.jvm.d9.hotswap.*" %>

<%
    InputStream is = new FileInputStream("F:/test.class");
    byte[] bytes = new byte[is.available()];
    is.read(bytes);
    is.close();

//    out.println("------------------- 执行结果 -------------------");
//    out.println(JavaClassExecuter.execute(bytes));
%>

<%= "------------------- 执行结果 -------------------"%>
<%= JavaClassExecuter.execute(bytes)%>