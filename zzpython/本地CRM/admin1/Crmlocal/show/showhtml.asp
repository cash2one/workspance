<!--#include file="../../include/include.asp"-->
<%
Curl=request.QueryString("fromurl")
read=getHTTPPage(Curl) 
response.Write(read)
%>