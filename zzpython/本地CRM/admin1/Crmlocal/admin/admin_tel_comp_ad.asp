<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!-- #include file="../../../cn/function.asp" -->
<!-- #include file="../../include/include.asp" -->
<%
frompagequrstr=Request.ServerVariables("QUERY_STRING")
%>
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
<br>
<table width="90%" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#666666">
  <tr>
    <td height="25" align="center" bgcolor="#FFFFFF"><a href="admin_tel_comp1.asp?<%=frompagequrstr%>">再生通销售</a></td>
    <td align="center" bgcolor="#FFCC00"><a href="admin_tel_comp_ad.asp?<%=frompagequrstr%>">广告销售电话记录</a></td>
  </tr>
</table>
<%
		sear=sear&"personid="&request.QueryString("personid")&"&contacttype="&request.QueryString("contacttype")
		sql="select * from comp_telad where contacttype="&request.QueryString("contacttype")&""
		if request.QueryString("personid")<>"" then
			sql=sql&" and personid="&request.QueryString("personid")
		end if
		if request.QueryString("star")<>"" then
			sql=sql&" and com_rank="&request.QueryString("star")&""
			sear=sear&"&star="&request.QueryString("star")
		end if
		if cstr(request("fromdate"))<>cstr(request("todate")) then
				if request("fromdate")<>"" then
					sql=sql&" and teldate>='"&request("fromdate")&"'"
					sear=sear&"&fromdate="&request("fromdate")
				end if
				if request("todate")<>"" then
					sql=sql&" and teldate<='"&request("todate")&"'"
					sear=sear&"&todate="&request("todate")
				end if
			else
			    if request("fromdate")<>"" and request("todate")<>"" then
					sql=sql&" and teldate>'"&request("fromdate")&"' and teldate<'"&cdate(request("fromdate"))+1&"'"
				    sear=sear&"&fromdate="&request("fromdate")
					sear=sear&"&todate="&request("todate")
				end if
			end if
		if request("code")<>"" then
		  	sql=sql&" and personid in (select id from users where userid="&request("code")&"  and closeflag=1)"
			sear=sear&"&code="&request("code")
		end if
			if request.QueryString("orderaction")<>"" then
				sql=sql&" order by "&request.QueryString("orderaction")&" desc"
				sear=sear&"&orderaction="&request("orderaction")
			else
				sql=sql&" order by teldate desc"
			end if
			'response.Write(sql)
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
		  rsc.close
		  set rsc=nothing
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
<%
rs.close
set rs=nothing
%>
</body>
</html>
