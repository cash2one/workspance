<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!--#include file="../../../cn/sources/Md5.asp"-->
<!--#include file="../../include/include.asp"-->
<!--#include file="../inc.asp"-->
<%
 selectcb=request("selectcb")
 dostay=request("dostay")
 doflag=request("doflag")
 topersonid=request("topersonid")
 dotype=request("dotype")
 arrcom=split(selectcb,",")
 if dostay="assignto" then
	 'for i=0 to ubound(arrcom)
		sql="update seo_list set personid="&topersonid&" where id in ("&selectcb&")"
		conn.execute(sql)
	 'next
	response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
 end if
 if dostay="waste" then
	sql="update seo_list set waste=1 where id in ("&selectcb&")"
	conn.execute(sql)
	response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
 end if
 if dostay="nowaste" then
	sql="update seo_list set waste=0 where id in ("&selectcb&")"
	conn.execute(sql)
	response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
 end if
%>
