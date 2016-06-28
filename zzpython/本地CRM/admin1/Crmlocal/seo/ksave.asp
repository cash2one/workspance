<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!--#include file="../../include/include.asp"-->
<!-- #include file="../../../cn/function.asp" -->
<!--#include file="../inc.asp"-->
<%
keywords=request.Form("keywords")
kid=request("kid")
sid=request.Form("sid")
target_assure=request.Form("target_assure")
seo_start=request.Form("seo_start")
target_require=request.Form("target_require")
target_time=request.Form("target_time")
expire_time=request.Form("expire_time")
com_msb=request.Form("com_msb")
price=request.Form("price")
action=request("action")
personid=request.Form("personid")
if action="edit" then
	sql="update seo_keywordslist set keywords='"&keywords&"',price='"&price&"',target_require="&target_require&",target_time='"&target_time&"',expire_time='"&expire_time&"' where id="&kid&""
	conn.execute(sql)
end if
if action="add" then
	sql="insert into seo_keywordslist(sid,keywords,com_msb,price,target_require,target_time,expire_time,personid) values("&sid&",'"&keywords&"','"&com_msb&"','"&price&"','"&target_require&"','"&target_time&"','"&expire_time&"',"&personid&")"
	conn.execute(sql)
end if
if action="del" then
	sql="delete from seo_keywordslist where id="&kid&""
	conn.execute(sql)
end if
response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
conn.close
set conn=nothing
%>