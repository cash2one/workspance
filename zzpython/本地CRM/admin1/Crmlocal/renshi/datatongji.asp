<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!-- #include file="../../../cn/sources/Md5.asp"-->
<!-- #include file="../../include/include.asp"-->
<!-- #include file="../../include/pagefunction.asp"-->
<!-- #include file="../inc.asp"-->
<%
fromdate=request("fromdate")
todate=request("todate")
if todate="" then todate=date
if fromdate="" then fromdate=date
tongji=request("tongji")
if tongji="" then tongji=1
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
.bon
{
	font-size: 14px;
	line-height: 22px;
	background-color: #CCC;
	border: 1px solid #096;	
}
-->
</style>
<SCRIPT language=javascript src="../DatePicker.js"></SCRIPT>
<SCRIPT language=JavaScript src="../../main.js"></SCRIPT>
<link href="../datepicker.css" rel="stylesheet" type="text/css">
</head>

<body>
<table width="100%" border="0" cellspacing="1" cellpadding="3" bgcolor="#999999">
  <tr>
    <td align="center" bgcolor="#FFFFFF" <%if tongji=1 then response.Write("class='bon'")%>><a href="?tongji=1">按公司统计</a></td>
    <td align="center" bgcolor="#FFFFFF" <%if tongji=2 then response.Write("class='bon'")%>><a href="?tongji=2">按区统计</a></td>
    <td align="center" bgcolor="#FFFFFF" <%if tongji=3 then response.Write("class='bon'")%>><a href="?tongji=3">按连队统计</a></td>
    <td align="center" bgcolor="#FFFFFF" <%if tongji=4 then response.Write("class='bon'")%>><a href="?tongji=4">员工统计</a></td>
  </tr>
</table>
<br />
<table width="100%" border="0" cellspacing="1" cellpadding="3" bgcolor="#999999">
  <form name="form1" method="post" action=""><tr>
    <td bgcolor="#FFFFFF">开始时间：<script language=javascript>createDatePicker("fromdate",false,"<%=fromdate%>",false,true,true,true)</script>结束时间：<script language=javascript>createDatePicker("todate",false,"<%=todate%>",false,true,true,true)</script>
      
        <input type="hidden" name="tongji" id="tongji" value="<%=tongji%>" />
        <input type="submit" name="button" id="button" value="查询">
    </td>
  </tr></form>
</table>
<br />
<%if tongji=1 then%>
<table border="0" cellpadding="3" cellspacing="1" bgcolor="#666666">
  <tr>
    <td width="72" nowrap="nowrap" bgcolor="#f2f2f2">公司</td>
    <td width="76" nowrap="nowrap" bgcolor="#f2f2f2">所属年月</td>
    <td width="77" nowrap="nowrap" bgcolor="#f2f2f2">团队人数</td>
    <td width="93" nowrap="nowrap" bgcolor="#f2f2f2">再生通新签</td>
    <td width="93" nowrap="nowrap" bgcolor="#f2f2f2">品牌通新签</td>
    <td width="63" nowrap="nowrap" bgcolor="#f2f2f2">广告</td>
    <td width="77" nowrap="nowrap" bgcolor="#f2f2f2">其他</td>
    <td width="93" nowrap="nowrap" bgcolor="#f2f2f2">新签小计</td>
    <td width="72" nowrap="nowrap" bgcolor="#f2f2f2">再生通续签</td>
    <td width="93" nowrap="nowrap" bgcolor="#f2f2f2">品牌通续签</td>
    <td width="93" nowrap="nowrap" bgcolor="#f2f2f2">广告</td>
    <td width="72" nowrap="nowrap" bgcolor="#f2f2f2">其他</td>
    <td width="72" nowrap="nowrap" bgcolor="#f2f2f2">续签小计</td>
  </tr>
  <%
  f=cdate(fromdate)
  t=cdate(todate)
  datechazhi=datediff("m",f,t)
  for i=0 to datechazhi-1
  fmonth=month(dateadd("m",i,f))
  if len(fmonth)=1 then fmonth="0"&fmonth
  fdate=year(dateadd("m",i,f))&fmonth
  fd=dateadd("m",i,f)
  td=dateadd("m",i+1,f)
  %>
  <tr>
    <td bgcolor="#FFFFFF">公司</td>
    <td bgcolor="#FFFFFF"><%=fdate%></td>
    <td bgcolor="#FFFFFF"><%=getusercount(fd,td)%></td>
    <td bgcolor="#FFFFFF"><%=getzstNew(fd,td,"1401","1301")%></td>
    <td bgcolor="#FFFFFF"><%=getzstNew(fd,td,"1401","1302")%></td>
    <td bgcolor="#FFFFFF"><%=getzstNew(fd,td,"1401","1303")%></td>
    <td align="right" bgcolor="#FFFFFF"><%=getzstNew(fd,td,"1401","1304")%></td>
    <td align="right" bgcolor="#FFFFFF"><%=getNew(fd,td,"1401")%></td>
    <td bgcolor="#FFFFFF"><%=getzstNew(fd,td,"1402","1301")%></td>
    <td bgcolor="#FFFFFF"><%=getzstNew(fd,td,"1402","1302")%></td>
    <td bgcolor="#FFFFFF"><%=getzstNew(fd,td,"1402","1303")%></td>
    <td bgcolor="#FFFFFF"><%=getzstNew(fd,td,"1402","1304")%></td>
    <td align="right" bgcolor="#FFFFFF"><%=getNew(fd,td,"1402")%></td>
  </tr>
  <%
  next
  %>
</table>
<br>
<%end if%>
<%if tongji=2 then%>
<!--按区统计 begin-->
<table border="0" cellpadding="3" cellspacing="1" bgcolor="#666666">
  <tr>
    <td width="72" nowrap="nowrap" bgcolor="#f2f2f2">所属年月</td>
    <td width="76" nowrap="nowrap" bgcolor="#f2f2f2">区</td>
    <td width="77" nowrap="nowrap" bgcolor="#f2f2f2">团队人数</td>
    <td width="93" nowrap="nowrap" bgcolor="#f2f2f2">再生通新签</td>
    <td width="93" nowrap="nowrap" bgcolor="#f2f2f2">品牌通新签</td>
    <td width="63" nowrap="nowrap" bgcolor="#f2f2f2">广告</td>
    <td width="77" nowrap="nowrap" bgcolor="#f2f2f2">其他</td>
    <td width="93" nowrap="nowrap" bgcolor="#f2f2f2">新签小计</td>
    <td width="72" nowrap="nowrap" bgcolor="#f2f2f2">再生通续签</td>
    <td width="93" nowrap="nowrap" bgcolor="#f2f2f2">品牌通续签</td>
    <td width="93" nowrap="nowrap" bgcolor="#f2f2f2">广告</td>
    <td width="72" nowrap="nowrap" bgcolor="#f2f2f2">其他</td>
    <td width="72" nowrap="nowrap" bgcolor="#f2f2f2">续签小计</td>
  </tr>
  <%
  f=cdate(fromdate)
  t=cdate(todate)
  datechazhi=datediff("m",f,t)
  for i=0 to datechazhi-1
  fmonth=month(dateadd("m",i,f))
  if len(fmonth)=1 then fmonth="0"&fmonth
  fdate=year(dateadd("m",i,f))&fmonth
  fd=dateadd("m",i,f)
  td=dateadd("m",i+1,f)
  sqlq="select code,meno from cate_adminuserPart"
  set rsq=conn.execute(sqlq)
  if not rsq.eof or not rsq.bof then
  while not rsq.eof
  partid=rsq("code")
  %>
  <tr>
    <td bgcolor="#FFFFFF"><%=fdate%></td>
    <td bgcolor="#FFFFFF"><%=rsq("meno")%></td>
    <td bgcolor="#FFFFFF"><%=getusercount1(fd,td,partid)%></td>
    <td bgcolor="#FFFFFF"><%=getzstNew1(fd,td,"1401","1301",partid)%></td>
    <td bgcolor="#FFFFFF"><%=getzstNew1(fd,td,"1401","1302",partid)%></td>
    <td bgcolor="#FFFFFF"><%=getzstNew1(fd,td,"1401","1303",partid)%></td>
    <td align="right" bgcolor="#FFFFFF"><%=getzstNew1(fd,td,"1401","1304",partid)%></td>
    <td align="right" bgcolor="#FFFFFF"><%=getNew1(fd,td,"1401",partid)%></td>
    <td bgcolor="#FFFFFF"><%=getzstNew1(fd,td,"1402","1301",partid)%></td>
    <td bgcolor="#FFFFFF"><%=getzstNew1(fd,td,"1402","1302",partid)%></td>
    <td bgcolor="#FFFFFF"><%=getzstNew1(fd,td,"1402","1303",partid)%></td>
    <td bgcolor="#FFFFFF"><%=getzstNew1(fd,td,"1402","1304",partid)%></td>
    <td align="right" bgcolor="#FFFFFF"><%=getNew1(fd,td,"1402",partid)%></td>
  </tr>
  <%
  rsq.movenext
  wend
  end if
  rsq.close
  set rsq=nothing
  next
  %>
</table>
<!--按区统计 end-->
<%end if%>

<%if tongji=3 then%>
<!--按连队统计 begin-->
<table border="0" cellpadding="3" cellspacing="1" bgcolor="#666666">
  <tr>
    <td width="72" nowrap="nowrap" bgcolor="#f2f2f2">所属年月</td>
    <td width="76" nowrap="nowrap" bgcolor="#f2f2f2">连队</td>
    <td width="77" nowrap="nowrap" bgcolor="#f2f2f2">团队人数</td>
    <td width="93" nowrap="nowrap" bgcolor="#f2f2f2">再生通新签</td>
    <td width="93" nowrap="nowrap" bgcolor="#f2f2f2">品牌通新签</td>
    <td width="63" nowrap="nowrap" bgcolor="#f2f2f2">广告</td>
    <td width="77" nowrap="nowrap" bgcolor="#f2f2f2">其他</td>
    <td width="93" nowrap="nowrap" bgcolor="#f2f2f2">新签小计</td>
    <td width="72" nowrap="nowrap" bgcolor="#f2f2f2">再生通续签</td>
    <td width="93" nowrap="nowrap" bgcolor="#f2f2f2">品牌通续签</td>
    <td width="93" nowrap="nowrap" bgcolor="#f2f2f2">广告</td>
    <td width="72" nowrap="nowrap" bgcolor="#f2f2f2">其他</td>
    <td width="72" nowrap="nowrap" bgcolor="#f2f2f2">续签小计</td>
  </tr>
  <%
  f=cdate(fromdate)
  t=cdate(todate)
  datechazhi=datediff("m",f,t)
  for i=0 to datechazhi-1
  fmonth=month(dateadd("m",i,f))
  if len(fmonth)=1 then fmonth="0"&fmonth
  fdate=year(dateadd("m",i,f))&fmonth
  fd=dateadd("m",i,f)
  td=dateadd("m",i+1,f)
  sqlq="select code,meno from cate_adminuser where code like '13%' and closeflag=1"
  set rsq=conn.execute(sqlq)
  if not rsq.eof or not rsq.bof then
  while not rsq.eof
  partid=rsq("code")
  %>
  <tr>
    <td bgcolor="#FFFFFF"><%=fdate%></td>
    <td bgcolor="#FFFFFF"><%=rsq("meno")%></td>
    <td bgcolor="#FFFFFF"><%=getusercount2(fd,td,partid)%></td>
    <td bgcolor="#FFFFFF"><%=getzstNew2(fd,td,"1401","1301",partid)%></td>
    <td bgcolor="#FFFFFF"><%=getzstNew2(fd,td,"1401","1302",partid)%></td>
    <td bgcolor="#FFFFFF"><%=getzstNew2(fd,td,"1401","1303",partid)%></td>
    <td align="right" bgcolor="#FFFFFF"><%=getzstNew2(fd,td,"1401","1304",partid)%></td>
    <td align="right" bgcolor="#FFFFFF"><%=getNew2(fd,td,"1401",partid)%></td>
    <td bgcolor="#FFFFFF"><%=getzstNew2(fd,td,"1402","1301",partid)%></td>
    <td bgcolor="#FFFFFF"><%=getzstNew2(fd,td,"1402","1302",partid)%></td>
    <td bgcolor="#FFFFFF"><%=getzstNew2(fd,td,"1402","1303",partid)%></td>
    <td bgcolor="#FFFFFF"><%=getzstNew2(fd,td,"1402","1304",partid)%></td>
    <td align="right" bgcolor="#FFFFFF"><%=getNew2(fd,td,"1402",partid)%></td>
  </tr>
  <%
  rsq.movenext
  wend
  end if
  rsq.close
  set rsq=nothing
  next
  %>
</table>
<!--按连队统计 end-->
<%end if%>

<%if tongji=4 then%>
<!--按员工统计 begin-->
<%
userid=request("userid")
sqlt="select code,meno from cate_adminuser where code like '13__'"
set rst=conn.execute(sqlt)
if not rst.eof or not rst.bof then
	while not rst.eof
		response.Write("<a href='?tongji="&tongji&"&userid="&rst("code")&"&fromdate="&fromdate&"&todate="&todate&"'>"&rst("meno")&"</a> | ")
	rst.movenext
	wend
end if
rst.close
set rst=nothing
%>
<table border="0" cellpadding="3" cellspacing="1" bgcolor="#666666">
  <tr>
    <td width="72" nowrap="nowrap" bgcolor="#f2f2f2">所属年月</td>
    <td width="76" nowrap="nowrap" bgcolor="#f2f2f2">员工名字</td>
    <td width="77" nowrap="nowrap" bgcolor="#f2f2f2">员工编号</td>
    <td width="93" nowrap="nowrap" bgcolor="#f2f2f2">再生通新签</td>
    <td width="93" nowrap="nowrap" bgcolor="#f2f2f2">品牌通新签</td>
    <td width="63" nowrap="nowrap" bgcolor="#f2f2f2">广告</td>
    <td width="77" nowrap="nowrap" bgcolor="#f2f2f2">其他</td>
    <td width="93" nowrap="nowrap" bgcolor="#f2f2f2">新签小计</td>
    <td width="72" nowrap="nowrap" bgcolor="#f2f2f2">再生通续签</td>
    <td width="93" nowrap="nowrap" bgcolor="#f2f2f2">品牌通续签</td>
    <td width="93" nowrap="nowrap" bgcolor="#f2f2f2">广告</td>
    <td width="72" nowrap="nowrap" bgcolor="#f2f2f2">其他</td>
    <td width="72" nowrap="nowrap" bgcolor="#f2f2f2">续签小计</td>
  </tr>
  <%
  f=cdate(fromdate)
  t=cdate(todate)
  datechazhi=datediff("m",f,t)
  for i=0 to datechazhi-1
  fmonth=month(dateadd("m",i,f))
  if len(fmonth)=1 then fmonth="0"&fmonth
  fdate=year(dateadd("m",i,f))&fmonth
  fd=dateadd("m",i,f)
  td=dateadd("m",i+1,f)
  sqlq="select id,realname,renshi_code from users where userid='"&userid&"' and chatflag=1 and chatclose=1"
  set rsq=conn.execute(sqlq)
  if not rsq.eof or not rsq.bof then
  while not rsq.eof
  personid=rsq("id")
  %>
  <tr>
    <td bgcolor="#FFFFFF"><%=fdate%></td>
    <td bgcolor="#FFFFFF"><%=rsq("realname")%></td>
    <td bgcolor="#FFFFFF"><%=rsq("renshi_code")%></td>
    <td bgcolor="#FFFFFF"><%=getzstNew3(fd,td,"1401","1301",personid)%></td>
    <td bgcolor="#FFFFFF"><%=getzstNew3(fd,td,"1401","1302",personid)%></td>
    <td bgcolor="#FFFFFF"><%=getzstNew3(fd,td,"1401","1303",personid)%></td>
    <td align="right" bgcolor="#FFFFFF"><%=getzstNew3(fd,td,"1401","1304",personid)%></td>
    <td align="right" bgcolor="#FFFFFF"><%=getNew3(fd,td,"1401",personid)%></td>
    <td bgcolor="#FFFFFF"><%=getzstNew3(fd,td,"1402","1301",personid)%></td>
    <td bgcolor="#FFFFFF"><%=getzstNew3(fd,td,"1402","1302",personid)%></td>
    <td bgcolor="#FFFFFF"><%=getzstNew3(fd,td,"1402","1303",personid)%></td>
    <td bgcolor="#FFFFFF"><%=getzstNew3(fd,td,"1402","1304",personid)%></td>
    <td align="right" bgcolor="#FFFFFF"><%=getNew3(fd,td,"1402",personid)%></td>
  </tr>
  <%
  rsq.movenext
  wend
  end if
  rsq.close
  set rsq=nothing
  next
  %>
</table>
<!--按连队统计 end-->
<%end if%>

<%
'-----团队人数
function getusercount(f,t)
	sqlc="select count(0) from users_history where gmt_created>='"&f&"' and gmt_created<='"&t&"'"
	set rsc=conn.execute(sqlc)
	if not rsc.eof or not rsc.bof then
		getusercount=rsc(0)
	end if
	rsc.close
	set rsc=nothing
end function
function getusercount1(f,t,partid)
	sqlc="select count(0) from users_history where gmt_created>='"&f&"' and gmt_created<='"&t&"' and userid in (select code from cate_adminuser where partid='"&partid&"')"
	set rsc=conn.execute(sqlc)
	if not rsc.eof or not rsc.bof then
		getusercount1=rsc(0)
	end if
	rsc.close
	set rsc=nothing
end function
function getusercount2(f,t,partid)
	sqlc="select count(0) from users_history where gmt_created>='"&f&"' and gmt_created<='"&t&"' and userid='"&partid&"'"
	set rsc=conn.execute(sqlc)
	if not rsc.eof or not rsc.bof then
		getusercount2=rsc(0)
	end if
	rsc.close
	set rsc=nothing
end function
function getusercount3(f,t,partid)
	sqlc="select count(0) from users_history where gmt_created>='"&f&"' and gmt_created<='"&t&"' and personid='"&partid&"'"
	set rsc=conn.execute(sqlc)
	if not rsc.eof or not rsc.bof then
		getusercount3=rsc(0)
	end if
	rsc.close
	set rsc=nothing
end function
'-----新签
function getzstNew(f,t,sales_type,service_type)
	sqlc="select sum(sales_price) from renshi_salesIncome where sales_date>='"&f&"' and sales_date<='"&t&"' and sales_type='"&sales_type&"' and service_type='"&service_type&"'"
	set rsc=conn.execute(sqlc)
	if rsc(0)<>"" then
		getzstNew=formatnumber(cdbl(rsc(0)),2)
	end if
	rsc.close
	set rsc=nothing
end function
function getzstNew1(f,t,sales_type,service_type,partid)
	sqlc="select sum(sales_price) from renshi_salesIncome where sales_date>='"&f&"' and sales_date<='"&t&"' and sales_type='"&sales_type&"' and service_type='"&service_type&"' and personid in (select id from users where userid in (select code from cate_adminuser where partid='"&partid&"'))"
	set rsc=conn.execute(sqlc)
	if rsc(0)<>"" then
		getzstNew1=formatnumber(cdbl(rsc(0)),2)
	end if
	rsc.close
	set rsc=nothing
end function
function getzstNew2(f,t,sales_type,service_type,partid)
	sqlc="select sum(sales_price) from renshi_salesIncome where sales_date>='"&f&"' and sales_date<='"&t&"' and sales_type='"&sales_type&"' and service_type='"&service_type&"' and personid in (select id from users where userid='"&partid&"')"
	set rsc=conn.execute(sqlc)
	if rsc(0)<>"" then
		getzstNew2=formatnumber(cdbl(rsc(0)),2)
	end if
	rsc.close
	set rsc=nothing
end function

function getzstNew3(f,t,sales_type,service_type,partid)
	sqlc="select sum(sales_price) from renshi_salesIncome where sales_date>='"&f&"' and sales_date<='"&t&"' and sales_type='"&sales_type&"' and service_type='"&service_type&"' and personid="&partid&""
	set rsc=conn.execute(sqlc)
	if rsc(0)<>"" then
		getzstNew3=formatnumber(cdbl(rsc(0)),2)
	end if
	rsc.close
	set rsc=nothing
end function

'-----新签小计
function getNew(f,t,sales_type)
	sqlc="select sum(sales_price) from renshi_salesIncome where sales_date>='"&f&"' and sales_date<='"&t&"' and sales_type='"&sales_type&"' "
	set rsc=conn.execute(sqlc)
	if rsc(0)<>"" then
		getNew=formatnumber(cdbl(rsc(0)),2)
	end if
	rsc.close
	set rsc=nothing
end function
function getNew1(f,t,sales_type,partid)
	sqlc="select sum(sales_price) from renshi_salesIncome where sales_date>='"&f&"' and sales_date<='"&t&"' and sales_type='"&sales_type&"' and personid in (select id from users where userid in (select code from cate_adminuser where partid='"&partid&"'))"
	set rsc=conn.execute(sqlc)
	if rsc(0)<>"" then
		getNew1=formatnumber(cdbl(rsc(0)),2)
	end if
	rsc.close
	set rsc=nothing
end function

function getNew2(f,t,sales_type,partid)
	sqlc="select sum(sales_price) from renshi_salesIncome where sales_date>='"&f&"' and sales_date<='"&t&"' and sales_type='"&sales_type&"' and personid in (select id from users where userid ='"&partid&"')"
	set rsc=conn.execute(sqlc)
	if rsc(0)<>"" then
		getNew2=formatnumber(cdbl(rsc(0)),2)
	end if
	rsc.close
	set rsc=nothing
end function

function getNew3(f,t,sales_type,partid)
	sqlc="select sum(sales_price) from renshi_salesIncome where sales_date>='"&f&"' and sales_date<='"&t&"' and sales_type='"&sales_type&"' and personid="&partid&""
	set rsc=conn.execute(sqlc)
	if rsc(0)<>"" then
		getNew3=formatnumber(cdbl(rsc(0)),2)
	end if
	rsc.close
	set rsc=nothing
end function
%>
</body>
</html>
