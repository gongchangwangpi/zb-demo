<%@page import="com.alibaba.fastjson.JSON"%>
<%@page import="com.domain.PayParam"%>
<%@page import="com.service.PayService"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.component.*"%>
<%
String jsonStr = request.getParameter("jsonStr");
//小丹测试openid=o5w79v0IDRwcqE_rbgN_c3L5MDpw
//小代测试openid=o5w79v7g9ta-oKWNoU24RdoqsOP8
//String jsonStr="{\"appId\":\"wx5973191ec9ee5746\",\"bodyDesc\":\"惠易保\",\"inquiryNo\":\"20160317164509073739\",\"notifyUrl\":\"http://test.jhjhome.com/jhj-hyb-wechat/notify/wechat\",\"openId\":\"o5w79v7g9ta-oKWNoU24RdoqsOP8\",\"prePayResultUrl\":\"http://test.jhjhome.com/jhj-hyb-wechat/notify/fail?memberPin=hyb_1455775808042554&orderCode=20160305123228937899&reason=\",\"resultUrl\":\"http://test.jhjhome.com/jhj-hyb-wechat/notify/page\",\"totalFee\":\"0.01\"}";
PayService payService = new PayService();
//请求支付需要JSON和支付失败错误原因
System.out.println("----- dopay.jsp 调用微信预支付 start -----");
    long start = System.currentTimeMillis();
    Map<String, String> mapParam=payService.getPayParamsJson(jsonStr, request.getRemoteAddr().toString());
    long end = System.currentTimeMillis();
    System.out.println("调用微信预支付 返回：" + JSON.toJSONString(mapParam));
    System.out.println("----- dopay.jsp 调用微信预支付 end -----, useTime:" + (end - start) + "ms");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>微信支付</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  <input type="hidden" id="clickMe" onclick="callpay()">
  <input type="hidden" id="resultCode" value="<%=mapParam.get("resultCode")%>">
  <input type="hidden" id="reason" value="<%=mapParam.get("reason")%>">
  <div style="text-align: center;">正在发送请求。。。</div>
  </body>
  <script type="text/javascript">
  	function callpay(){
		 WeixinJSBridge.invoke('getBrandWCPayRequest',
			 <%=mapParam.get("payJsonStr")%>
   			,function(res){
				WeixinJSBridge.log(res.err_msg);
	            if(res.err_msg == "get_brand_wcpay_request:ok"){ 
	                window.location.href = "<%=mapParam.get("resultUrl")%>?attach=<%=mapParam.get("attach")%>&openid=<%=mapParam.get("openId")%>&inquiryNo=<%=mapParam.get("inquiryNo")%>&result=ok&token=<%=mapParam.get("token")%>";
	            }else if(res.err_msg == "get_brand_wcpay_request:cancel"){
	                window.location.href = "<%=mapParam.get("resultUrl")%>?attach=<%=mapParam.get("attach")%>&openid=<%=mapParam.get("openId")%>&inquiryNo=<%=mapParam.get("inquiryNo")%>&result=cancel&token=<%=mapParam.get("token")%>";
	            }else{  
	               window.location.href = "<%=mapParam.get("resultUrl")%>?attach=<%=mapParam.get("attach")%>&openid=<%=mapParam.get("openId")%>&inquiryNo=<%=mapParam.get("inquiryNo")%>&result=fail&token=<%=mapParam.get("token")%>";
	            } 
			});
		}
		
	//定时器
	setTimeout(function() {
		if(document.getElementById("resultCode").value=="SUCCESS"){
			if(document.all) {
				document.getElementById("clickMe").click();
			}else {
				var e = document.createEvent("MouseEvents");
				e.initEvent("click", true, true);
				document.getElementById("clickMe").dispatchEvent(e);
			}
		}else{
			alert(document.getElementById("reason").value);
			alert("<%=mapParam.get("prePayResultUrl")%><%=mapParam.get("reason")%>");
			window.location.href = "<%=mapParam.get("prePayResultUrl")%><%=mapParam.get("reason")%>";
		};
	}, 2500);
  </script>
  
</html>
