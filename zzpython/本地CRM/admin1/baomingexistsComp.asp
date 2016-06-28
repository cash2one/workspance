<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="include/adfsfs!@#.asp" -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
body,td,th {
	font-size: 12px;
}
-->
</style></head>

<body>
<%
email=request.QueryString("email")
comtel=request.QueryString("comtel")
com_id=""
if email<>"" then
	sql="select top 1 com_id from comp_info where com_email='"&email&"'"
	set rs=conn.execute(sql)
	if not rs.eof or not rs.bof then
		response.Write("邮箱在本地已经存在<br>")
		response.Write("<a href='/admin1/crmlocal/crm_cominfoedit.asp?idprod="&rs(0)&"' target=_blank>"&email&"</a>")
		com_id=rs(0)
	end if
	rs.close
	set rs=nothing
end if
if comtel<>"" then
	sql="select top 1 com_id from comp_info where com_mobile like '%"&right(comtel,8)&"%'"
	set rs=conn.execute(sql)
	if not rs.eof or not rs.bof then
		response.Write("邮箱在本地已经存在<br>")
		response.Write("<a href='/admin1/crmlocal/crm_cominfoedit.asp?idprod="&rs(0)&"' target=_blank>"&email&"</a>")
		com_id=rs(0)
	end if
	rs.close
	set rs=nothing
end if
if com_id<>"" then
	sql="select a.realname from users as a,crm_assign as b where a.id=b.personid and b.com_id="&com_id&""
	set rs=conn.execute(sql)
	if not rs.eof or not rs.bof then
		response.Write("<br><b>销售："&rs(0)&"</b>")
	end if
	rs.close
	set rs=nothing
end if
conn.close
set conn=nothing
%>
</body>
</html>
