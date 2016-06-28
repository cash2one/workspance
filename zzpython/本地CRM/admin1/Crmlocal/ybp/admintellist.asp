<!-- #include file="../../include/adfsfs!@#.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!-- #include file="../../../cn/function.asp" -->
<!-- #include file="../../include/include.asp" -->
<%
frompagequrstr=Request.ServerVariables("QUERY_STRING")
dotype=request.QueryString("dotype")
lmaction=request("lmaction")
action=request("action")
adminuser=request("adminuser")
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>联系记录</title>
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
<link href="../../css.css" rel="stylesheet" type="text/css">

<link href="../../inc/Style.css" rel="stylesheet" type="text/css">
</head>

<body>
<br>
<%

		comtype=request.QueryString("type")
		bankcheck=request.QueryString("bankcheck")
		sear="personid="&request.QueryString("personid")
		sql="select * from ybp_tel where id>0"
		if request.QueryString("contactstat")<>"" then
			sql=sql&" and contactstat='"&request.QueryString("contactstat")&"'"
			sear=sear&"&contactstat="&request.QueryString("contactstat")
		end if
		if request.QueryString("personid")<>"" then
			sql=sql&" and personid="&request.QueryString("personid")
		end if
		if request.QueryString("star")<>"" then
			sql=sql&" and star='"&request.QueryString("star")&"'"
			sear=sear&"&star="&request.QueryString("star")
		end if
		
		if bankcheck<>"" then
			sql=sql&" and bankcheck='"&request.QueryString("bankcheck")&"'"
			sear=sear&"&bankcheck="&bankcheck
		end if
		

		if cstr(request("fromdate"))<>cstr(request("todate")) then
			if request("fromdate")<>"" then
				sql=sql&" and fdate>='"&request("fromdate")&"'"
				sear=sear&"&fromdate="&request("fromdate")
			end if
			if request("todate")<>"" then
				sql=sql&" and fdate<='"&cdate(request("todate"))+1&"'"
				sear=sear&"&todate="&request("todate")
			end if
		else
			if request("fromdate")<>"" and request("todate")<>"" then
				sql=sql&" and fdate>='"&cdate(request("fromdate"))&"' and fdate<='"&cdate(request("todate"))+1&"'"
				sear=sear&"&fromdate="&request("fromdate")
				sear=sear&"&todate="&request("todate")
			end if
		end if
		
		if tomotor="1" then
			sqla=sqla&" and interviewTime>='"&cdate(todate)+1&"' and interviewTime<='"&cdate(todate)+2&"'"
			sear=sear&"&tomotor="&tomotor
		end if
		sql=sql&" order by fdate desc"
	    set rs=server.CreateObject("ADODB.recordset")
	    rs.open sql,conn,1,1
		%>
		<table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td><!--#include file="../../include/pagetop.asp"--></td>
          </tr>
		</table>
        <table width="90%" border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#7386A5">
          <tr>
            <td bgcolor="#CED7E7">店铺名称</td>
            <td bgcolor="#CED7E7">联系时间</td>
            <td bgcolor="#CED7E7">有效无效</td>
            <td bgcolor="#CED7E7">星级</td>
            <td bgcolor="#CED7E7">小计</td>
            </tr>
		  <%
		  
		  if not rs.eof then
		  do while not rs.eof and RowCount>0
			  sqlc="select shop_name from ybp_company where id="&rs("cid")
			  set rsc=conn.execute(sqlc)
			  if not rsc.eof then
				  shop_name=rsc("shop_name")
			  end if
			  rsc.close
			  set rsc=nothing
		  %>
          <tr>
            <td bgcolor="#FFFFFF"><%=shop_name%><a href="companyshow.asp?cid=<%=rs("cid")%>" target="_blank">查看</a></td>
            <td bgcolor="#FFFFFF"><%=rs("fdate")%></td>
            <td bgcolor="#FFFFFF">
			<%
			select case rs("contactstat")
				case "0"
					response.Write("无效")
				case "1"
					response.Write("有效")
			end select
			%></td>
            <td nowrap bgcolor="#FFFFFF"><%=rs("star")%>星</td>
            <td bgcolor="#FFFFFF"><%=rs("bz")%></td>
            </tr>
		  <%
		  rs.movenext
		  RowCount=RowCount-1
		  loop
		  end if
		  rs.close
		  set rs=nothing
		  
		  %>
		  <tr>
            <td colspan="5" align="right" bgcolor="#FFFFFF"><!-- #include file="../../include/page.asp" --></td>
          </tr>
</table>
<%
conn.close
set conn=nothing
%>
</body>
</html>
