<%@LANGUAGE="VBSCRIPT" CODEPAGE="65001"%>
<!-- #include file="include/adfsfs!@#.asp" -->
<!-- #include file="localjumptolog.asp" -->
<!-- #include file="../cn/function.asp" -->
<!--#include file="include/include.asp"-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<script>
function UTF8UrlEncode(input){    
        var output = "";    
        var currentChar = '';    
        for(var counter = 0; counter < input.length; counter++){    
            currentChar = input.charCodeAt(counter);    
            if((0 <= currentChar) && (currentChar <= 127))    
                output = output + UTF8UrlEncodeChar(currentChar);    
            else   
                output = output + encodeURIComponent(input.charAt(counter));    
        }    
        var reslut = output.toUpperCase();    
        return reslut.replace(/%26/, "%2526");     
    } 
function UTF8UrlEncodeChar(input){    
	if(input <= 0x7F) return "%" + input.toString(16);    
	var leadByte = 0xFF80;    
	var hexString = "";    
	var leadByteSpace = 5;    
	while(input > (Math.pow(2, leadByteSpace + 1) - 1)){    
		hexString = "%" + ((input & 0x3F) | 0x80).toString(16) + hexString;    
		leadByte = (leadByte >> 1);    
		leadByteSpace--;    
		input = input >> 6;    
	}    
	return ("%" + (input | (leadByte & 0xFF)).toString(16) + hexString).toUpperCase();    
}
function wl(text){
	window.location="baoming.asp?title="+UTF8UrlEncode(text)
}
</script>
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="30"><table border="0" cellspacing="0" cellpadding="5">
      <tr>
        <td><a href="baoming.asp?p=1">手机问题反馈</a></td>
        <td><a href="baoming.asp?p=2">申请再生通</a></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
    </table></td>
  </tr>
</table>
<%
p=request("p")
page=request.QueryString("page")
title=request.QueryString("title")
if page="" then
	page=1
end if
Curl="http://adminasto.zz91.com/subjectbaoming_ldb/?page="&page&"&p="&p
read=getHTTPPage(Curl)
response.Write(read)
%>
</body>
</html>
