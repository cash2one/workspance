<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!-- #include file="../../../cn/function.asp" -->
<!--#include file="../../include/include.asp"-->
<!--#include file="../../include/pagefunction.asp"-->
<%
sear="n="
function showPerson(id)
	sqlu="select realname from users where id="&id
	set rsu=conn.execute(sqlu)
	if not (rsu.eof and rsu.bof) then
		showPerson=rsu(0)
	end if
	rsu.close
	set rsu=nothing
end function
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
<style type="text/css">
<!--
body,td,th {
	font-size: 12px;
}
-->
</style></head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="5">
 <form id="form1" name="form1" method="post" action=""> <tr>
    <td align="center">email:
      
        <input type="text" name="com_email" id="com_email" />
      <input type="submit" name="button" id="button" value="搜索" /></td>
  </tr></form>
</table>
<%
sqlt=""
if request("com_email")<>"" then
	sqlt=sqlt&" and exists (select com_id from comp_info where com_email like '%"&request("com_email")&"%' and com_id=shop_viewCount.com_id)"
	sear=sear&"&com_email="&request("com_email")
end if
sear=sear&"&ord="&request("ord")
Set oPage=New clsPageRs2
With oPage
 .sltFld  = "viewCount,com_id"
 .FROMTbl = "shop_viewCount"
 if request("ord")="1" then
 .sqlOrder= "order by viewcount desc"
 elseif request("ord")="0" then
 .sqlOrder= "order by viewcount asc"
 else
 .sqlOrder= "order by com_id asc"
 end if
 .sqlWhere= "WHERE com_id>0 "&sqlt
 .keyFld  = "com_id"    '不可缺少
 .pageSize= 20
 .getConn = conn
 Set Rs  = .pageRs
End With
total=oPage.getTotalPage
oPage.pageNav "?"&sear,""
totalpg=int(total/20)
%>
<table width="100%" border="0" cellspacing="1" cellpadding="4" bgcolor="#666666">
  <tr>
    <td bgcolor="#f2f2f2">公司名称</td>
    <td bgcolor="#f2f2f2">EMAIL</td>
    <td bgcolor="#f2f2f2">
    流量量
    <a href="?ord=1">降序</a>
    
    <a href="?ord=0">升序</a>
    </td>
    <td bgcolor="#f2f2f2">所属者</td>
    <td bgcolor="#f2f2f2">&nbsp;</td>
  </tr>
  <%
  if not rs.eof or not rs.bof then
  while not rs.eof 
  sqlc="select com_name,com_email,com_subname from comp_info where com_id="&rs("com_id")&""
  set rsc=conn.execute(sqlc)
  if not rsc.eof or not rsc.bof then
  	com_name=rsc(0)
	com_email=rsc(1)
	com_subname=rsc(2)
  else
  	com_name=""
	com_email=""
	com_subname=""
  end if
  rsc.close
  set rsc=nothing
  sqlp="select personid from crm_assign where com_id="&rs("com_id")&""
  set rsp=conn.execute(sqlp)
  if not rsp.eof or not rsp.bof then
  	personid=rsp(0)
  else
  	personid="0"
  end if
  rsp.close
  set rsp=nothing
  %>
  <tr>
    <td bgcolor="#FFFFFF"><a href="http://192.168.1.2/admin1/crmlocal/modaldealog_body.asp?../crmlocal/crm_cominfoedit.asp?idprod=<%=rs("com_id")%>" target="_blank"><%=com_name%></a></td>
    <td bgcolor="#FFFFFF"><%=com_email%></td>
    <td bgcolor="#FFFFFF"><%=rs("viewCount")%></td>
    <td bgcolor="#FFFFFF">
    <%
	if personid<>"0" then
		response.Write(showPerson(personid))
	end if
	%>
    </td>
    <td bgcolor="#FFFFFF"><a href="http://<%=com_subname%>.zz91.com" target="_blank">流量门市部</a></td>
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
<%
conn.close
set conn=nothing
%>