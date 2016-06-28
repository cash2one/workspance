<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!-- #include file="../../../cn/function.asp" -->
<!-- #include file="../../include/include.asp" -->
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
-->
</style>
<link href="../../css.css" rel="stylesheet" type="text/css">

<link href="../../inc/Style.css" rel="stylesheet" type="text/css">
</head>

<body>
<%
		sear=sear&"adminuser="&request.QueryString("adminuser")&"&contacttype="&request.QueryString("contacttype")
		    sql="select * from comp_tel where personid="&request.QueryString("adminuser")
			if request.QueryString("contacttype")<>"" then
			sql=sql&" and contacttype='"&request.QueryString("contacttype")&"' "
			end if
			if request.QueryString("com_rank")<>"" then
			sql=sql&" and com_rank='"&request.QueryString("com_rank")&"'"
			end if
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
		<table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td><!--#include file="../../include/pagetop.asp"--></td>
          </tr>
</table>
          <table width="90%" border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#7386A5">
          <tr>
            <td bgcolor="#CED7E7"><a href="../../?adminuser=<%=request.QueryString("adminuser")%>&contacttype=<%=request.QueryString("contacttype")%>&from_date=<%=request.QueryString("from_date")%>&to_date=<%=request.QueryString("to_date")%>&orderaction=com_id">公司名称</a></td>
            <td bgcolor="#CED7E7"><a href="../../?adminuser=<%=request.QueryString("adminuser")%>&contacttype=<%=request.QueryString("contacttype")%>&from_date=<%=request.QueryString("from_date")%>&to_date=<%=request.QueryString("to_date")%>&orderaction=teldate">联系时间</a></td>
            <td bgcolor="#CED7E7">有效无效</td>
            <td bgcolor="#CED7E7">星级</td>
            <td bgcolor="#CED7E7">联系情况</td>
            </tr>
		  <%
		  
		  if not rs.eof then
		  do while not rs.eof and RowCount>0
		  sqlc="select com_id,com_name,com_email from temp_salescomp where com_id="&rs("com_id")
		  set rsc=conn.execute(sqlc)
		  if not rsc.eof then
		  com_name=rsc("com_name")
		  com_email=rsc("com_email")
		  end if
		  %>
          <tr>
            <td bgcolor="#FFFFFF"><a href="../crm_cominfoedit.asp?idprod=<%=rs("com_id")%>&cemail=<%=com_email%>" target="_blank"><%=com_name%></a></td>
            <td bgcolor="#FFFFFF"><%=rs("teldate")%></td>
            <td bgcolor="#FFFFFF">
			<%
			select case rs("contacttype")
			case "12"
			response.Write("无效")
			case "13"
			response.Write("有效")
			end select
			%></td>
            <td nowrap bgcolor="#FFFFFF"><%=rs("com_rank")%>星</td>
            <td bgcolor="#FFFFFF"><%=rs("detail")%></td>
            </tr>
         
		  <%
		  rs.movenext
		  RowCount=RowCount-1
		  loop
		  end if
		  %>
		   <tr>
            <td colspan="5" align="right" bgcolor="#FFFFFF"><!-- #include file="../../include/page.asp" --></td>
            </tr>
</table>
</body>
</html>
