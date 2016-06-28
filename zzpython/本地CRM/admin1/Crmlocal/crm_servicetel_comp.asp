<!-- #include file="../include/adfsfs!@#.asp" -->
<!-- #include file="../../cn/function.asp" -->
<!-- #include file="../jumptolog.asp" -->
<!-- #include file="../include/include.asp" -->
<%
if request("del")="1" then
sqldel="delete from comp_tel where id="&request("id")
conn.execute(sqldel)
response.Redirect("crm_tel_comp.asp?com_id="&request("com_id")&"&page="&request("page"))
end if
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>销售服务记录</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
<link href="../css.css" rel="stylesheet" type="text/css">

<link href="../inc/Style.css" rel="stylesheet" type="text/css">
</head>

<body  scroll=yes>
<%
		sear=sear&"adminuser="&request.QueryString("adminuser")&"&contacttype="&request.QueryString("contacttype")&"&com_id="&request.QueryString("com_id")
		sql="select * from comp_tel where  com_id="&request("com_id")&" and serviceType=0"
		   if cstr(request("from_date"))<>cstr(request("to_date")) then
				if request("from_date")<>"" then
					sql=sql&" and teldate>='"&request("from_date")&"'"
					sear=sear&"&from_date="&request("from_date")
				end if
				if request("to_date")<>"" then
					sql=sql&" and teldate<='"&request("to_date")&"'"
					sear=sear&"&to_date="&request("to_date")
				end if
			else
			    if request("from_date")<>"" and request("to_date")<>"" then
					sql=sql&" and year(teldate)="&year(request("from_date"))&" and month(teldate)="&month(request("from_date"))&" and day(teldate)="&day(request("from_date"))&""
				    sear=sear&"&from_date="&request("from_date")
					sear=sear&"&to_date="&request("to_date")
				end if
			end if
			if request.QueryString("orderaction")<>"" then
			sql=sql&" order by "&request.QueryString("orderaction")&" desc"
			sear=sear&"&orderaction="&request("orderaction")
			else
			sql=sql&" order by teldate desc"
			end if
			
			
		  set rs=server.CreateObject("ADODB.recordset")
		  rs.open sql,conn,1,1
		%>
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td><!--#include file="../include/pagetop_notop.asp"--></td>
          </tr>
</table>
          <table width="100%" border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#7386A5">
          <tr>
            <td bgcolor="#CED7E7">联系时间</td>
            <td nowrap bgcolor="#CED7E7">联系情况</td>
            <td nowrap bgcolor="#CED7E7">联系人</td>
            <td nowrap bgcolor="#CED7E7">客户等级</td>
            <td bgcolor="#CED7E7">联系内容</td>
            </tr>
		  <%
		  
		  if not rs.eof then
		  do while not rs.eof and RowCount>0
		  sqlc="select com_id,com_name,com_email from comp_info where  com_id="&rs("com_id")&" "
		  set rsc=conn.execute(sqlc)
		  if not rsc.eof then
		  com_name=rsc("com_name")
		  com_email=rsc("com_email")
		  end if
		  %>
          <tr>
            <td bgcolor="#FFFFFF"><%=rs("teldate")%></td>
            <td bgcolor="#FFFFFF"><%call shows("cate_contact_about",rs("contacttype"))%></td>
            <td bgcolor="#FFFFFF">
			<%
			call selet_("realname","users","id",rs("personid"))
			%></td>
            <td bgcolor="#FFFFFF">
			<%
			if rs("com_rank")<>"-1" then
			response.Write(rs("com_rank")&"星")
			end if
			 %>
			
			</td>
            <td bgcolor="#FFFFFF"><%=rs("detail")%></td>
            </tr>
         
		  <%
		  rs.movenext
		  RowCount=RowCount-1
		  loop
		  end if
		  %>
		   <tr>
            <td colspan="5" align="right" bgcolor="#FFFFFF"><!-- #include file="../include/page.asp" --></td>
            </tr>
</table>
</body>
</html>
