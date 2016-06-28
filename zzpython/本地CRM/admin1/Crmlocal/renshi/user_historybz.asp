<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!--#include file="../../include/include.asp"-->
<!--#include file="../../include/pagefunction.asp"-->
<%
uid=request.QueryString("uid")
if request.Form("bz")<>"" then
	sql="select * from renshi_historyBz where id is null"
	set rs=server.CreateObject("adodb.recordset")
	rs.open sql,conn,1,3
	rs.addnew
	rs("personid")=session("personid")
	rs("uid")=request.Form("uid")
	rs("bz")=request.Form("bz")
	rs.update()
	rs.close
	set rs=nothing
	response.Redirect("user_historybz.asp?uid="&request.Form("uid")&"")
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
-->
</style></head>

<body><form id="form1" name="form1" method="post" action="">
<table border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>添加情况说明</td>
    <td>
      <textarea name="bz" id="bz" cols="45" rows="5"></textarea>
      <input name="uid" type="hidden" id="uid" value="<%=uid%>">
    </td>
    <td><input type="submit" name="button" id="button" value="提交" /></td>
  </tr>
</table></form>
<%

sear="uid="&uid

  Set oPage=New clsPageRs2
   With oPage
	 .sltFld  = "*"
	 .FROMTbl = "renshi_historyBz"
	 .sqlOrder= "order by id desc"
	 .sqlWhere= "WHERE  uid="&uid&" "
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
<table width="100%" border="0" cellspacing="0" cellpadding="4">
  <%

  if not rs.eof or not rs.bof then
  while not rs.eof
  %>
  <tr>
    <td><%=rs("bz")%></td>
    <td><%=rs("fdate")%></td>
    <td><%
	if rs("personid")<>"" then
  sqlu="select realname from users where id="&rs("personid")&""
  set rsu=conn.execute(sqlu)
  if not rsu.eof or not rsu.bof then
	response.Write(rsu(0))
  end if
  rsu.close
  set rsu=nothing
  end if
	%></td>
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
