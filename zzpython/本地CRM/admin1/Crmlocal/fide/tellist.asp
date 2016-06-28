<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!--#include file="../../include/include.asp"-->
<!--#include file="../../include/pagefunction.asp"-->
<!--#include file="../inc.asp"-->
<%
cid=request.QueryString("cid")
id=cstr(request.QueryString("id"))
if request.QueryString("del")="1" and cid<>"" and id<>"" then
	sql="delete from ybp_tel where id="&cstr(request.QueryString("id"))
	conn.execute(sql)
end if
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>情况说明记录</title>
<style type="text/css">
<!--
body,td,th {
	font-size: 12px;
}
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style></head>

<body>
<%

sear="cid="&cid

  Set oPage=New clsPageRs2
   With oPage
	 .sltFld  = "contactstat,star,nextteltime,bz,fdate,personid,id"
	 .FROMTbl = "ybp_tel"
	 .sqlOrder= " order by id desc"
	 .sqlWhere= "WHERE  cid="&cid&" "
	 .keyFld  = "id"    '不可缺少
	 .pageSize= 15
	 .getConn = conn
	 Set Rs  = .pageRs
   End With
   total=oPage.getTotalPage
   oPage.pageNav "?"&sear,""
   totalpg=cint(total/15)
   if total/15 > totalpg then
	  totalpg=totalpg+1
   end if
%>
<table width="100%" border="0" cellspacing="1" cellpadding="2" bgcolor="#333333">
  <tr>
    <td nowrap="nowrap" bgcolor="#FFFFFF">联系状态</td>
    <td bgcolor="#FFFFFF">星级</td>
    <td bgcolor="#FFFFFF">下次联系时间</td>
    <td bgcolor="#FFFFFF">小计</td>
    <td nowrap="nowrap" bgcolor="#FFFFFF">小计时间</td>
    <td nowrap="nowrap" bgcolor="#FFFFFF">联系人</td>
  </tr>
  <%

  if not rs.eof or not rs.bof then
  while not rs.eof
  %>
  
  <tr>
  	<td nowrap="nowrap" bgcolor="#FFFFFF">
	<%
	contactstat=rs("contactstat")
	if contactstat="1" then
		response.Write("有效联系")
	end if
	if contactstat="0" then
		response.Write("无效联系")
	end if
	%>
    
    </td>
  	<td bgcolor="#FFFFFF"><%=rs("star")%></td>
  	
  	<td bgcolor="#FFFFFF"><%=rs("nextteltime")%></td>
    <td bgcolor="#FFFFFF"><%=rs("bz")%></td>
    <td nowrap="nowrap" bgcolor="#FFFFFF"><%=rs("fdate")%></td>
    <td nowrap="nowrap" bgcolor="#FFFFFF"><%
	if rs("personid")<>"" then
  sqlu="select realname from users where id="&rs("personid")&""
  set rsu=conn.execute(sqlu)
  if not rsu.eof or not rsu.bof then
	response.Write(rsu(0))
  end if
  rsu.close
  set rsu=nothing
  end if
	%>
    <%if session("userid")="10" then%><a href="?del=1&id=<%=rs("id")%>&cid=<%=cid%>" onclick="return confirm('确实要删除吗？')">删除</a><%end if%>
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
<%
conn.close
set conn=nothing
%>
