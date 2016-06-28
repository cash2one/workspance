<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../include/adfsfs!@#.asp" -->
<!-- #include file="../localjumptolog.asp" -->
<!-- #include file="../../cn/function.asp" -->
<!--#include file="../include/include.asp"-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
</head>

<body>
<%
mobile=right(request("mobile"),6)
tel=right(request("tel"),6)
com_id=request.QueryString("com_id")
%>
<table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#666666">
  <tr>
    <td bgcolor="#CCCCCC">公司名称</td>
    <td bgcolor="#CCCCCC">邮箱</td>
    <td bgcolor="#CCCCCC">手机</td>
    <td bgcolor="#CCCCCC">电话</td>
    <td bgcolor="#CCCCCC">&nbsp;</td>
  </tr>
<%
sql="select com_id,com_name,com_mobile,com_tel,com_email from comp_info where (com_mobile like '%"&mobile&"'"
if tel<>"" then
sql=sql&" or com_tel like '%"&tel&"'"
end if
sql=sql&") and com_id<>"&com_id&""
'response.Write(sql)
set rs=conn.execute(sql)
if not rs.eof then
while not rs.eof
%>

  <tr>
    <td bgcolor="#FFFFFF"><a href="modaldealog_body.asp?../crmlocal/webcrm_cominfoedit.asp?idprod=<%=rs("com_id")%>&cemail=<%=rs("com_email")%>&dotype=<%=request("dotype")%>" target="_blank"><%=rs("com_name")%></a></td>
    <td bgcolor="#FFFFFF"><%=rs("com_email")%></td>
    <td bgcolor="#FFFFFF"><%=rs("com_mobile")%></td>
    <td bgcolor="#FFFFFF"><%=rs("com_tel")%></td>
    <td bgcolor="#FFFFFF">
	<%
				  sqluser="select a.personid,b.realname from crm_assign as a,users as b where a.com_id="&rs("com_id")&" and a.personid=b.id"

				  set rsuser=conn.execute(sqluser)
				  if not rsuser.eof then
				  realname=rsuser("realname")
				  else
				  realname=""
				  end if
				  
				  %>
				 <%response.write(realname)%>
	</td>
  </tr>
<%
rs.movenext
wend
end if
rs.close
set rs=nothing
%>
</table>
</body>
</html>
