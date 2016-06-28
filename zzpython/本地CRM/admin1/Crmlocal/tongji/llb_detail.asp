<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../../cn/function.asp" -->
<!-- #include file="../../include/include.asp" -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>销售统计</title>

<SCRIPT language=javascript src="../DatePicker.js"></SCRIPT>
<link href="../datepicker.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body,td,th {
	font-size: 12px;
}
body {
	margin-left: 2px;
	margin-top: 2px;
	margin-right: 2px;
	margin-bottom: 2px;
}
.top3
{
	background-color: #6CF;
	font-weight: bold;
}
.last3
{
	background-color:#FC3;
	font-weight:bold;
}
-->
</style></head>

<body>
<% 
sear=""
fromdate=request("fromdate")
  if fromdate="" then
	  fromdate=date()
	  todate=date()
  else
  	  fromdate=fromdate
	  todate=fromdate
  end if
  
  sear=sear&"&fromdate="&fromdate&"&todate="&todate&""
  if request("userClassId")<>"" then
  	code=request("userClassId")
  else
	  if session("userid")<>"" and session("userid")<>"10" and session("userid")<>"13" then
		code=session("userid")
	  end if
  end if

		  ord=request.QueryString("ord")
		  if ord="" then ord="1"
		  col=request.QueryString("col")
		  if ord="1" then  Nord=0 else Nord=1
		  %>
          <table width="100%" border="1" align="center" cellpadding="3" cellspacing="0" bordercolordark="#ccc" bordercolorlight="#FFFFFF">
  
  <tr>
    <td width="30" align="center" bgcolor="#FFFFFF">排名</td>
    <td width="40" align="center" bgcolor="#FFFFFF">销售</td>
    <td width="40" align="center" bgcolor="#FFFFFF">部门</td>
    <td align="center" bgcolor="#FFFFFF"><a href="?ord=<%=Nord%>&amp;col=6&userClassId=<%=code%><%=sear%>">月累计到单</a><%if col=6 then%><b style="color:#F00"><%if ord="1" then%>↓<%else%>↑<%end if%></b><%end if%></td>
    <td align="center" bgcolor="#FFFFFF"><a href="?ord=<%=Nord%>&amp;col=1&userClassId=<%=code%><%=sear%>">日到单量</a><%if col=1 then%><b style="color:#F00"><%if ord="1" then%>↓<%else%>↑<%end if%></b><%end if%></td>
    <td align="center" bgcolor="#FFFFFF"><a href="?ord=<%=Nord%>&amp;col=2&userClassId=<%=code%><%=sear%>">开发量</a><%if col=2 then%><b style="color:#F00"><%if ord="1" then%>↓<%else%>↑<%end if%></b><%end if%></td>
    <td align="center" bgcolor="#FFFFFF"><a href="?ord=<%=Nord%>&amp;col=3&userClassId=<%=code%><%=sear%>">联系量</a><%if col=3 then%><b style="color:#F00"><%if ord="1" then%>↓<%else%>↑<%end if%></b><%end if%></td>
    <td align="center" bgcolor="#FFFFFF"><a href="?ord=<%=Nord%>&amp;col=4&userClassId=<%=code%><%=sear%>">在线时间</a><%if col=4 then%><b style="color:#F00"><%if ord="1" then%>↓<%else%>↑<%end if%></b><%end if%></td>
    <td align="center" bgcolor="#FFFFFF"><a href="?ord=<%=Nord%>&amp;col=5&userClassId=<%=code%><%=sear%>">呼叫量</a><%if col=5 then%><b style="color:#F00"><%if ord="1" then%>↓<%else%>↑<%end if%></b><%end if%></td>
    </tr>
  <%
  sqlb=""
  
  if code<>"" then
  	sqlb=sqlb&" and userid='"&code&"'"
  end if
  if ord="1" then
  	ordstr=" desc"
  else
  	ordstr=" asc"
  end if
  if col<>"" then
  	select case col
	case "1"
		sqlb=sqlb&" order by vipCompCount "&ordstr&""
	case "2"
		sqlb=sqlb&" order by developCount "&ordstr&""
	case "3"
		sqlb=sqlb&" order by ContactCount "&ordstr&""
	case "4"
		sqlb=sqlb&" order by CallonlineCount "&ordstr&""
	case "5"
		sqlb=sqlb&" order by CallCount "&ordstr&""
	case "6"
		sqlb=sqlb&" order by MonthCompCount "&ordstr&""
	case else
		sqlb=sqlb&" order by vipCompCount "&ordstr&""
	end select
  else
  	sqlb=sqlb&" order by vipCompCount "&ordstr&""
  end if
  sql1="select count(0) from v_salesCompTongji where userid like '13__' and closeflag=1 and tdate>='"&fromdate&"' and tdate<='"&todate&"'"
  set rs1=conn.execute(sql1)
  ucount=rs1(0)
  rs1.close
  set rs1=nothing
  sql1="select * from v_salesCompTongji where userid like '13__' and closeflag=1 and tdate>='"&fromdate&"' and tdate<='"&todate&"'"&sqlb
  set rs1=conn.execute(sql1)
  ii=1
  if not rs1.eof or not rs1.bof then
  while not rs1.eof
  if ii<=3 then
  	ncolor="top3"
  else
  	ncolor=""
  end if
  if ii>=ucount-2 then
  	ncolor="last3"
  end if
  %>
  <tr class="<%=ncolor%>">
    <td align="center"><%=ii%></td>
    <td align="center"><%=rs1("realname")%></td>
    <td width="40" align="center"><%=rs1("meno")%></td>
    <td align="center"><%=rs1("MonthCompCount")%></td>
    <td align="center">&nbsp;<%=rs1("vipCompCount")%>&nbsp;</td>
    <td align="center">&nbsp;<a href="/admin1/crmlocal/admin/admin_tj_comp.asp?lo=1&personid=<%=rs1("personid")%>&star=&4star=1<%=sear%>" target="_blank"><%=rs1("developCount")%></a>&nbsp;</td>
    <td align="center">&nbsp;<a href="contactCountList.asp?personid=<%=rs1("personid")%><%=sear%>" target="_blank"><%=rs1("ContactCount")%></a>&nbsp;</td>
    <td align="center">
	&nbsp;<a href="onlinelist.asp?localtel=<%=rs1("usertel")%><%=sear%>" target="_blank"><%
	a=rs1("CallonlineCount")
	cc=""
	if not isnull(a) then
		cc=DateAdd("s", a, 0)
		response.Write(cc)
	end if
	%></a>&nbsp;</td>
    <td align="center">&nbsp;<%=rs1("CallCount")%>&nbsp;</td>
    </tr>
  <%
  ii=ii+1
  rs1.movenext
  wend
  end if
  rs1.close
  set rs1=nothing
  %>
</table>
</body>
</html>
<%
conn.close
set conn=nothing
%>
