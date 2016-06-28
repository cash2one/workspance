<%@page language="java" contentType="text/html;charset=utf-8"%>
<%@page import="com.yeepay.*"%>
<%!	String formatString(String text){ 
			if(text == null) {
				return ""; 
			}
			return text;
		}
%>
<%
	request.setCharacterEncoding("utf-8");
	// 商家设置用户购买商品的支付信息
	String    p2_Order           	= formatString(request.getParameter("p2_Order"));           					// 商户订单号
	String	  p3_Amt           	 	= formatString(request.getParameter("p3_Amt"));      	   						// 支付金额
	String	  p5_Pid 		     	= formatString(request.getParameter("p5_Pid"));	       	   						// 商品名称
	String	  p6_Pcat  		     	= formatString(request.getParameter("p6_Pcat"));	       	   					// 商品种类
	String 	  p7_Pdesc   		 	= formatString(request.getParameter("p7_Pdesc"));		   						// 商品描述
	String 	  p8_Url 	         	= formatString(request.getParameter("p8_Url")); 		       					// 商户接收支付成功数据的地址
	if(p8_Url==null||p8_Url==""){
		p8_Url = "http://localhost:8080/yeepaydemo/yeepayCommon/JAVA/callback.jsp";
	}
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>易宝支付产品通用接口演示</title>
	</head>
	<body onload="load()">
		<div>易宝支付产品通用接口测试</div>
		<div id="input">
			<form method="post" id="form" action="reqpay.jsp">
				<div>商家订单号:<input type="text" name="p2_Order" id="p2_Order" value="<%=p2_Order%>" /></div>
				<div>支付金额:<input type="text" name="p3_Amt" id="p3_Amt" value="<%=p3_Amt%>" />&nbsp;<span style="color:#FF0000;font-weight:100;">*</span></div>
				<div>商品名称:<input type="text" name="p5_Pid" id="p5_Pid" value="<%=p5_Pid%>" /></div>
				<div>商品种类:<input type="text" name="p6_Pcat" id="p6_Pcat" value="<%=p6_Pcat%>" /></div>
				<div>商品描述:<input type="text" name="p7_Pdesc" id="p7_Pdesc" value="<%=p7_Pdesc%>" /></div>
				<div>商户接收支付成功数据的地址:<input type="text" name="p8_Url" id="p8_Url" value="<%=p8_Url%>"/>&nbsp;<span style="color:#FF0000;font-weight:100;">*</span></div>
				<div><input type="submit" value="结帐" /></div>
			</form>
			<script type="text/javascript">
				function load(){
					var vv = document.getElementById("form");
					vv.submit();
				}
			</script>
		</div>
	</body>
</html>