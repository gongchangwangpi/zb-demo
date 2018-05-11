<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/9/19
  Time: 11:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cookie</title>
    <script src="../js/jquery-1.9.1.min.js"></script>
</head>
<body>
<div id="cookieDiv"></div>

<a href="/cookie/t?orderCode=20170925&goBack=/carins/order/detail?orderCode=2017&status=3">go back</a>
<a href="/cookie/t?orderCode=20170925&goBack=http%3A//127.0.0.1/cookie/test%3ForderCode%3D2017%26status%3D3">go back</a>

<script>
    var cookies = document.cookie;
    $("#cookieDiv").html(cookies);
    
    var url = location.href + '?orderCode=2017&status=3';
    url = escape(url);
    console.log(url);
</script>
</body>
</html>
