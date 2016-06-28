<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!--#include file="../../include/pagefunction.asp"-->
<!-- #include file="../../localjumptolog.asp" -->
<%
 sqluser="select realname,ywadminid from users where id="&session("personid")
 set rsuser=conn.execute(sqluser)
 userName=rsuser(0)
 ywadminid=rsuser(1)
 rsuser.close
 set rsuser=nothing
 sear=sear&"n="
 if request.QueryString("del")="1" and request.QueryString("id")<>"" then
 	sql="delete from crm_compLink_main where id="&request.QueryString("id")&""
	conn.execute(sql)
	sql="delete from crm_complink where groupid="&request.QueryString("groupid")&""
	conn.execute(sql)
 end if
 if request("delgl")="1" then
 	sql="delete from crm_complink where groupid in (select groupid from v_complink1 where countPerson=0)"
	conn.execute(sql)
	sql="delete from crm_complink_main where groupid not in (select groupid from crm_complink)"
	conn.execute(sql)
 end if
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
.lmon
{
	background-color: #0C0;
	font-size: 14px;
	font-weight: bold;
}
.lmoff
{
	background-color: #CCC;	
}
-->
</style></head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <form id="form1" name="form1" method="post" action=""><tr>
    <td>
      <input type="button" name="button" id="button" value="提交关联客户" onClick="window.location='p_add.asp'" />
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
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="100" height="30" align="center" <%if request("zd")="0" then response.Write("class=lmon")%>><a href="?zd=0">关联客户</a></td>
        <td width="100" align="center" <%if request("zd")="1" then response.Write("class=lmon")%>><a href="?zd=1" >撞单客户</a></td>
        <td width="100" align="center" <%if request("zd")="2" then response.Write("class=lmon")%>><a href="?zd=2">关联在公海</a></td>
        <td>
        <%if request("zd")="2" then%>
        <input type="button" name="button3" id="button3" value="删除在都在公海里的关联" onClick="window.location='?delgl=1'"/>
        <%end if%>
        &nbsp;</td>
      </tr>
    </table>
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
	if request("zd")="0" then
		sqlt=sqlt&" and countPerson<=1"
		sear=sear&"&zd="&request("zd")
	end if
	if request("zd")="1" then
		sqlt=sqlt&" and countPerson>1"
		sear=sear&"&zd="&request("zd")
	end if
	if request("zd")="2" then
		sqlt=sqlt&" and countPerson=0"
		sear=sear&"&zd="&request("zd")
	end if
	response.Write(sqlt)
    Set oPage=New clsPageRs2
	With oPage
		.sltFld  = "*"
		.FROMTbl = "v_compLinkmain"
		.sqlOrder= "order by id desc"
		if session("userid")="10" then
		.sqlWhere="where 1=1 "&sqlt
		else
		.sqlWhere= "where personid in (select id from users where userID in("&ywadminid&"))"&sqlt
		end if
		.keyFld  = "id"    '不可缺少
		.pageSize= 20
		.getConn = conn
		Set Rs  = .pageRs
	End With
	total=oPage.getTotalPage
	oPage.pageNav "?"&sear,""
	totalpg=int(total/20)
	'response.Write(session("Partadmin"))
  %></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="100" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="5">
      <tr>
        <td align="center" <%if request("tjflag")="" and request("ssflag")="" and request("ysflag")="" then response.Write(" bgcolor='#FFCC00'")%>><a href="?zd=<%=request("zd")%>">全部</a></td>
      </tr>
      <tr>
        <td align="center" <%if request("tjflag")="0" then response.Write(" bgcolor='#FFCC00'")%>><a href="?tjflag=0&zd=<%=request("zd")%>">未提交</a></td>
      </tr>
      <tr>
        <td align="center" <%if request("tjflag")="1" then response.Write(" bgcolor='#FFCC00'")%>><a href="?tjflag=1&zd=<%=request("zd")%>">已提交</a></td>
      </tr>
      
      <tr>
        <td align="center" <%if request("ysflag")="0" then response.Write(" bgcolor='#FFCC00'")%>><a href="?ysflag=0&zd=<%=request("zd")%>">预审后未处理</a></td>
      </tr>
      <tr>
        <td align="center" <%if request("ysflag")="1" then response.Write(" bgcolor='#FFCC00'")%>><a href="?ysflag=1&zd=<%=request("zd")%>">预审已审</a></td>
      </tr>
      <tr>
        <td align="center" <%if request("ssflag")="1" then response.Write(" bgcolor='#FFCC00'")%>><a href="?ssflag=1&zd=<%=request("zd")%>">申述</a></td>
      </tr>
    </table></td>
    <td valign="top" bgcolor="#f2f2f2"><table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#666666">
  <tr>
    <td bgcolor="#f2f2f2">编号</td>
    <td bgcolor="#f2f2f2">提交时间</td>
    <td bgcolor="#f2f2f2">提交人</td>
    <td bgcolor="#f2f2f2">预审情况</td>
    <td bgcolor="#f2f2f2">提交情况</td>
    <td bgcolor="#f2f2f2">服务部审核</td>
  </tr>
 <%
while not rs.eof %>
  <tr>
    <td bgcolor="#FFFFFF"><a href="p_list_comp.asp?groupid=<%=rs("groupid")%>&page=<%=request("page")%>"><%=rs("GroupID")%></a></td>
    <td bgcolor="#FFFFFF"><%=rs("fdate")%></td>
    <td bgcolor="#FFFFFF"><%=getusername(rs("personid"))%></td>
    <td bgcolor="#FFFFFF"><%
		    if rs("YSFlag")="1" then
				response.Write("已审")
			else
				response.Write("<font color=#ff0000>未</font>")
			end if
		%></td>
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
	%> 
    <a href="?del=1&amp;id=<%=rs("id")%>&groupid=<%=rs("groupid")%>" onClick="return confirm('确实要删除吗？')">删除</a></td>
  </tr>
  <%
    rs.movenext
	wend
	rs.close
	set rs=nothing
	conn.close
	set conn=nothing
  %>
</table></td>
  </tr>
</table>


</body>
</html>
