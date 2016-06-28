<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<%
response.Redirect("http://192.168.2.200:8081/bbs/chatbbslist.asp")
response.End()
%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../../cn/function.asp" -->
<!-- #include file="../../include/include.asp" -->
<%
frompagea=split(request.servervariables("script_name"),"/")
frompage=lcase(frompagea(UBound(frompagea)))
frompagequrstr=Request.ServerVariables("QUERY_STRING")
frompagequrstr=replace(frompagequrstr,"&","~amp~")
sqlt="select max(fdate) from crm_UploadFile where ftype=1"
set rst=conn.execute(sqlt)
if not rst.eof or rst.bof then
	maxlytime=rst(0)
end if
rst.close
set rst=nothing
%>
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
td
{
	padding-top:2px;
	padding-bottom:2px;
}
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
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
.nobg
{
	background-color:#FFF;
}
a
{
	color:#039;
}
a:hover
{
	color:#900;
}
.tbtop
{
	background-color: #090;
	color: #FFF;
}
.tbtop a
{
	color:#fff;
}
.tbtop a:hover
{
	color:#FF0;
}
-->
</style></head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center"><a href="llb_detail.asp" target="_blank">查看详细</a> 录音更新时间：<%=maxlytime%></td>
  </tr>
</table>
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
          <table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#006600">
  
  <tr>
    <td width="30" align="center" bgcolor="#FFFFFF" class="tbtop">排名</td>
    <td width="40" align="center" bgcolor="#FFFFFF" class="tbtop">销售</td>
    <td width="70" align="center" bgcolor="#FFFFFF" class="tbtop"><a href="?ord=1&amp;col=8&userClassId=<%=code%><%=sear%>">当月部门</a><%if col=8 then%><b style="color:#F00"><%if ord="1" then%>↓<%else%>↑<%end if%></b><%end if%></td>
    <%if col="8" then%>
    <td align="center" bgcolor="#FFFFFF" class="tbtop">&nbsp;</td>
    <%end if%>
    <td align="center" bgcolor="#FFFFFF" class="tbtop"><a href="?ord=<%=Nord%>&amp;col=9&userClassId=<%=code%><%=sear%>">累计到单</a><%if col=9 then%><b style="color:#F00"><%if ord="1" then%>↓<%else%>↑<%end if%></b><%end if%></td>
    <%if col="9" then%>
    <td align="center" bgcolor="#FFFFFF" class="tbtop">差额</td>
    <%end if%>
    <td align="center" bgcolor="#FFFFFF" class="tbtop"><a href="?ord=<%=Nord%>&amp;col=6&userClassId=<%=code%><%=sear%>">当月累计</a>
    <%if col="6" then%><b style="color:#F00"><%if ord="1" then%>↓<%else%>↑<%end if%></b><%end if%></td>
    
    
    <td align="center" bgcolor="#FFFFFF" class="tbtop"><a href="?ord=<%=Nord%>&amp;col=1&userClassId=<%=code%><%=sear%>">日到单量</a><%if col=1 then%><b style="color:#F00"><%if ord="1" then%>↓<%else%>↑<%end if%></b><%end if%></td>
    <td align="center" bgcolor="#FFFFFF" class="tbtop"><a href="?ord=<%=Nord%>&amp;col=5&userClassId=<%=code%><%=sear%>">呼叫量</a><%if col=5 then%><b style="color:#F00"><%if ord="1" then%>↓<%else%>↑<%end if%></b><%end if%></td>
    <td align="center" bgcolor="#FFFFFF" class="tbtop"><a href="?ord=<%=Nord%>&amp;col=7&amp;userClassId=<%=code%><%=sear%>">昨日在线</a>
      <%if col=7 then%>
      <b style="color:#F00">
      <%if ord="1" then%>
      &darr;
      <%else%>
      &uarr;
      <%end if%>
      </b>
    <%end if%></td>
    <td align="center" bgcolor="#FFFFFF" class="tbtop"><a href="?ord=<%=Nord%>&amp;col=4&userClassId=<%=code%><%=sear%>">今日在线</a>
    <%if col=4 then%><b style="color:#F00"><%if ord="1" then%>↓<%else%>↑<%end if%></b><%end if%></td>
    <td align="center" bgcolor="#FFFFFF" class="tbtop"><a href="?ord=<%=Nord%>&amp;col=3&userClassId=<%=code%><%=sear%>">联系量</a><%if col=3 then%><b style="color:#F00"><%if ord="1" then%>↓<%else%>↑<%end if%></b><%end if%></td>
    <td align="center" bgcolor="#FFFFFF" class="tbtop"><a href="?ord=<%=Nord%>&amp;col=2&userClassId=<%=code%><%=sear%>">开发量</a><%if col=2 then%><b style="color:#F00"><%if ord="1" then%>↓<%else%>↑<%end if%></b><%end if%></td>
    
    
    
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
	case "7"
		sqlb=sqlb&" order by YCallonlineCount "&ordstr&""
	case "8"
		sqlb=sqlb&" order by PMonthCompCount "&ordstr&",userid desc,MonthCompCount desc"
	case "9"
		sqlb=sqlb&" order by CompCount "&ordstr&""
	case else
		sqlb=sqlb&" order by MonthCompCount "&ordstr&""
	end select
  else
  	sqlb=sqlb&" order by PMonthCompCount "&ordstr&",userid desc,MonthCompCount desc"
  end if
  sql1="select count(0) from v_salesCompTongji where userid like '13__' and closeflag=1 and chatclose=1 and tdate>='"&fromdate&"' and tdate<='"&todate&"'"
  set rs1=conn.execute(sql1)
  ucount=rs1(0)
  rs1.close
  set rs1=nothing
  sql1="select * from v_salesCompTongji where userid like '13__' and closeflag=1 and chatclose=1 and tdate>='"&fromdate&"' and tdate<='"&todate&"'"&sqlb
  set rs1=conn.execute(sql1)
  ii=1
  t=1
  '-----差额
  compcountpro=0
  if not rs1.eof or not rs1.bof then
  while not rs1.eof
  if ii<=5 then
  	ncolor="top3"
	if col="8" then
		ncolor="nobg"
	end if
  else
  	ncolor="nobg"
  end if
  if ii>=ucount-4 then
  	ncolor="last3"
	if col="8" then
		ncolor="nobg"
	end if
  end if
  userid=rs1("userid")
  if col="8" and cstr(puserid)<>cstr(userid) then
	  sqlcc="select count(0) from v_salesCompTongji where userid like '13__' and userid="&userid&" and closeflag=1 and chatclose=1 and tdate>='"&fromdate&"' and tdate<='"&todate&"'"
	  set rscc=conn.execute(sqlcc)
	  rowcout1=rscc(0)
	  rscc.close
	  set rscc=nothing
  end if
  %>
  <tr class="<%=ncolor%>">
    <td align="center" class="<%=ncolor%>"><%=ii%></td>
    <td align="center" class="<%=ncolor%>"><%=rs1("realname")%></td>
    <%if col="8" then%>
		<%
		if cstr(puserid)<>cstr(userid) then%>
        <td width="70" align="center" class="<%=ncolor%>" rowspan="<%=rowcout1%>"><%'=userid&rowcout1%><%if t<=3 and ord="1" then%><font color="#FF0000">TOP<%=t%></font><%end if%><br><%=rs1("meno")%></td>
        <%
		t=t+1
		end if%>
    <%else%>
    <td width="60" class="<%=ncolor%>" align="center"><%=rs1("meno")%></td>
    
    <%end if%>
    
    <%if col="8"  and cstr(puserid)<>cstr(userid) then%>
    <td align="right" rowspan="<%=rowcout1%>" class="<%=ncolor%>">&nbsp;<%=rs1("PMonthCompCount")%>&nbsp;</td>
    <%end if%>
    <td align="right" class="<%=ncolor%>">&nbsp;<%
	if not isnull(rs1("CompCount")) or rs1("CompCount")<>"" then
	response.Write(formatnumber(rs1("CompCount"),2))
	end if
	%>&nbsp;</td>
    <%if col=9 then%>
    <%
	if not isnull(rs1("CompCount")) and rs1("CompCount")<>"" then
	chaer=compcountpro-rs1("CompCount")
	if chaer="" or isnull(chaer) then
	chaer=0
	end if
	chaer=formatnumber(chaer,2)
	if compcountpro=0 then
		chaer=""
	end if
	end if
	compcountpro=rs1("CompCount")
	
	%>
    <td align="right" class="<%=ncolor%>">&nbsp;<%=chaer%>&nbsp;</td>
    <%end if%>
    <td align="right" class="<%=ncolor%>">&nbsp;<%
	if not isnull(rs1("MonthCompCount")) or rs1("MonthCompCount")<>"" then
	response.Write(formatnumber(rs1("MonthCompCount"),2))
	end if
	%>&nbsp;</td>
    <td align="right" class="<%=ncolor%>">&nbsp;<%=rs1("vipCompCount")%>&nbsp;</td>
    <td align="right" class="<%=ncolor%>">&nbsp;<%=rs1("CallCount")%>&nbsp;</td>
    <td align="right" class="<%=ncolor%>">
    &nbsp;<%
	a=rs1("YCallonlineCount")
	cc=""
	if not isnull(a) then
		cc=DateAdd("s", a, 0)
		response.Write(cc)
	end if
	%>&nbsp;
    </td>
    <td align="center" class="<%=ncolor%>">
	&nbsp;<%
	a=rs1("CallonlineCount")
	cc=""
	if not isnull(a) then
		cc=DateAdd("s", a, 0)
		response.Write(cc)
	end if
	%>&nbsp;</td>
    <td align="center" class="<%=ncolor%>">&nbsp;<%=rs1("ContactCount")%>&nbsp;</td>
    <td align="center" class="<%=ncolor%>">&nbsp;<%=rs1("developCount")%>&nbsp;</td>
    
    
    
    </tr>
  <%
  puserid=userid
  userid=0
  rowcout1=0
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
frompagea=split(request.servervariables("script_name"),"/")
frompage=lcase(frompagea(UBound(frompagea)))
frompagequrstr=Request.ServerVariables("QUERY_STRING")
response.Write("<script>setTimeout(""window.location='"&frompage&"?"&frompagequrstr&"'"",60000*5)</script>")
%>
