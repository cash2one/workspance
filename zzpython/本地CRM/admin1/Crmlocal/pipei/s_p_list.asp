<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!--#include file="../../include/pagefunction.asp"-->
<%
sear=sear&"n="
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
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <form id="form1" name="form1" method="post" action=""><tr>
    <td>
      
      email
<input type="text" name="com_email" id="com_email" />
编号
<input type="text" name="GroupID" id="GroupID" />
<input type="submit" name="button2" id="button2" value="查询" />
    </td>
  </tr></form>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
    <%
	function getusername(id)
		sqlu="select realname from users where id="&id
		set rsu=conn.execute(sqlu)
		if not rsu.eof then
		getusername=rsu(0)
		end if
		rsu.close
		set rsu=nothing
	end function
	sqlt=""
	if request("com_email")<>"" then
		sqlt=sqlt&" and groupid in (select groupid from crm_compLink where exists(select com_id from v_salescomp where com_email like '%"&request("com_email")&"%' and com_id=crm_compLink.com_id))"
	end if
	if request("tjflag")<>"" then
		sqlt=sqlt&" and tjflag="&request("tjflag")&""
		sear=sear&"&tjflag="&request("tjflag")
	end if
	if request("ysflag")<>"" then
		sqlt=sqlt&" and ysflag="&request("ysflag")&""
		sear=sear&"&ysflag="&request("ysflag")
	end if
	if request("ssflag")<>"" then
		sqlt=sqlt&" and ssflag="&request("ssflag")&""
		sear=sear&"&ssflag="&request("ssflag")
	end if
	if request("groupid")<>"" then
		sqlt=sqlt&" and groupid='"&request("groupid")&"'"
		sear=sear&"&groupid="&request("groupid")
	end if
    Set oPage=New clsPageRs2
	With oPage
		.sltFld  = "*"
		.FROMTbl = "crm_compLink_main"
		.sqlOrder= "order by id desc"
		.sqlWhere= "where tjflag=1 "&sqlt
		.keyFld  = "id"    '不可缺少
		.pageSize= 10
		.getConn = conn
		Set Rs  = .pageRs
	End With
	total=oPage.getTotalPage
	oPage.pageNav "?"&sear,""
	totalpg=int(total/10)
  %></td>
  </tr>
</table>
<table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#666666">
  <tr>
    <td bgcolor="#f2f2f2">编号</td>
    <td bgcolor="#f2f2f2">提交时间</td>
    <td bgcolor="#f2f2f2">提交人</td>
    <td bgcolor="#f2f2f2">提交情况</td>
    <td bgcolor="#f2f2f2">服务部审核</td>
  </tr>
 <%
while not rs.eof %>
  <tr>
    <td bgcolor="#FFFFFF"><a href="s_list_comp.asp?groupid=<%=rs("groupid")%>&page=<%=request("page")%>"><%=rs("GroupID")%></a></td>
    <td bgcolor="#FFFFFF"><%=rs("fdate")%></td>
    <td bgcolor="#FFFFFF"><%=getusername(rs("personid"))%></td>
    <td bgcolor="#FFFFFF">
    <%
	if rs("tjflag")="1" then
	response.Write("已提交")
	else
	response.Write("<font color=#ff0000>未提交</font>")
	end if
	%>
    </td>
    <td bgcolor="#FFFFFF"><%
	if rs("shflag")="1" then
	response.Write("已审核")
	else
	response.Write("<font color=#ff0000>未审核</font>")
	end if
	%></td>
  </tr>
  <%
    rs.movenext
	wend
	rs.close
	set rs=nothing
	conn.close
	set conn=nothing
  %>
</table>
</body>
</html>
