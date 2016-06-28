<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<%
function tian_sum(nianyue) 
	nian=cint(left(nianyue,instr(nianyue,"-")-1)) 
	yue=cint(right(nianyue,len(nianyue)-instr(nianyue,"-"))) 
	if yue=1 or yue=3 or yue=5 or yue=7 or yue=8 or yue=10 or yue=12 then 
		tian_sum=31 
	elseif yue=4 or yue=6 or yue=9 or yue=11 then 
		tian_sum=30 
	else 
	if ryear(nian)=true then 
		tian_sum=29 
	elseif ryear(nian)=false then 
		tian_sum=28 
	else 
		tian_sum="Error" 
	end if 
	end if 
end function 
function ryear(years) 
	If years Mod 400 = 0 Or (years Mod 4 = 0 And years Mod 100 <> 0) Then 
		ryear=true 
	else 
		ryear=false 
	end If 
end function
nowdate1=now
if request("fmonth")<>"" then
	nowdate=request("fyear")&"-"&request("fmonth")&"-1"
	fromdate=cdate(year(nowdate)&"-"&month(nowdate)&"-1")
	todate=cdate(year(nowdate)&"-"&month(nowdate)&"-"&tian_sum(year(nowdate)&"-"&month(nowdate)))
end if
if request("fjd")<>"" then
	nowdate=request("fyear")&"-"&getjdof(request("fjd"))&"-1"
	fromdate=cdate(year(nowdate)&"-"&month(nowdate)&"-1")
	'todate=cdate()
	todate=year(nowdate)&"-"&month(nowdate)+2&"-"&tian_sum(year(nowdate)&"-"&month(nowdate)+2)
end if




codelist=""
menolist=""
sql="select code,meno from category where code like '15__'"
set rs=conn.execute(sql)
if not rs.eof or not rs.bof then
	codelist=codelist&rs(0)&","
	menolist=menolist&rs(1)&","
end if
rs.close
set rs=nothing
codelist=left(codelist,len(codelist)-1)
menolist=left(menolist,len(menolist)-1)
function getjd(nosdate)
	if cint(nosdate)<=3 then getjd=1
	if cint(nosdate)<=6 and cint(nosdate)>3 then getjd=2
	if cint(nosdate)<=9 and cint(nosdate)>6 then getjd=3
	if cint(nosdate)<=12 and cint(nosdate)>9 then getjd=4
end function
function getjdof(nosdate)
	if cint(nosdate)=1 then getjdof=1
	if cint(nosdate)=2 then getjdof=4
	if cint(nosdate)=3 then getjdof=7
	if cint(nosdate)=4 then getjdof=10
end function
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
form
{
	padding:0px;
	margin:0px;
}
-->
</style>
<SCRIPT language=JavaScript src="../../main.js"></SCRIPT>
</head>

<body>
<form id="form1" name="form1" method="get" action="usertongji.asp">月统计表 
 <select name="fyear" id="fyear">
 <%
 for i=year(now)-3 to year(now)
 %>
   <option value="<%=i%>"><%=i%></option>
 <%
 next
 %>
 </select>
 月份<select name="fmonth" id="fmonth">
   <option value="">选择月份</option>
   <option value="1">1</option>
   <option value="2">2</option>
   <option value="3">3</option>
   <option value="4">4</option>
   <option value="5">5</option>
   <option value="6">6</option>
   <option value="7">7</option>
   <option value="8">8</option>
   <option value="9">9</option>
   <option value="10">10</option>
   <option value="11">11</option>
   <option value="12">12</option>
 </select>
 <script>selectOption("fmonth","<%=request("fmonth")%>")</script>
 <script>selectOption("fyear","<%=request("fyear")%>")</script>
季度<select name="fjd" id="fjd">
   <option value="">选择季度</option>
   <option value="1">1</option>
   <option value="2">2</option>
   <option value="3">3</option>
   <option value="4">4</option>
 </select>
  <script>selectOption("fjd","<%=request("fjd")%>")</script>
  <input type="submit" name="button" id="button" value="提交" />
</form>
<br />
<table border="0" cellspacing="1" cellpadding="4" bgcolor="#333333">
  <tr>
    <td width="82" valign="top" bgcolor="#f2f2f2"><p align="center">招聘岗位 </p></td>
    <td width="82" valign="top" bgcolor="#f2f2f2"><p align="center">新增记录 </p></td>
    <td width="82" valign="top" bgcolor="#f2f2f2"><p align="center">成功邀约人次 </p></td>
    <td width="80" valign="top" bgcolor="#f2f2f2"><p align="center">录用数 </p></td>
    <td width="78" valign="top" bgcolor="#f2f2f2"><p align="center">报到数 </p></td>
    <td width="80" valign="top" bgcolor="#f2f2f2"><p align="center">储备人才 </p></td>
  </tr>
  <%
  arrcodelist=split(codelist,",")
  arrmenolist=split(menolist,",")
  for i=0 to ubound(arrcodelist)
  %>
  <tr>
    <td width="82" valign="top" bgcolor="#FFFFFF"><%=arrmenolist(i)%></td>
    <td width="82" valign="top" bgcolor="#FFFFFF">
    <%
	sql="select count(0) from renshi_user where gmt_created>='"&fromdate&"' and gmt_created<='"&cdate(todate)+1&"' and station='"&arrcodelist(i)&"' "
	set rs=conn.execute(sql)
	response.Write(rs(0))
	rs.close
	set rs=nothing
	%>
    </td>
    <td width="82" valign="top" bgcolor="#FFFFFF">
      <%
	sql="select count(0) from renshi_user where gmt_created>='"&fromdate&"' and gmt_created<'"&cdate(todate)+1&"' and station='"&arrcodelist(i)&"' and jl1=1701"
	set rs=conn.execute(sql)
	response.Write(rs(0))
	rs.close
	set rs=nothing
	%>
    </td>
    <td width="80" valign="top" bgcolor="#FFFFFF">
      <%
	sql="select count(0) from renshi_user where gmt_created>='"&fromdate&"' and gmt_created<'"&cdate(todate)+1&"' and station='"&arrcodelist(i)&"' and jl3=1901"
	set rs=conn.execute(sql)
	response.Write(rs(0))
	rs.close
	set rs=nothing
	%>
    </td>
    <td width="78" valign="top" bgcolor="#FFFFFF">
    <%
	sql="select count(0) from renshi_user where gmt_created>='"&fromdate&"' and gmt_created<'"&cdate(todate)+1&"' and station='"&arrcodelist(i)&"' and jl4=2002"
	set rs=conn.execute(sql)
	response.Write(rs(0))
	rs.close
	set rs=nothing
	%>
    </td>
    <td width="80" valign="top" bgcolor="#FFFFFF">
    <%
	sql="select count(0) from renshi_user where gmt_created>='"&fromdate&"' and gmt_created<'"&cdate(todate)+1&"' and station='"&arrcodelist(i)&"' and jl5=2102"
	set rs=conn.execute(sql)
	response.Write(rs(0))
	rs.close
	set rs=nothing
	%>
    </td>
  </tr>
  <%
  next
  %>
</table>
</body>
</html>
