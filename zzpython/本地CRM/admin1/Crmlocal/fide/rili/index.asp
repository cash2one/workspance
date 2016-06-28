<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../../include/ad!$#5V.asp" -->
<%
if request("re")<>"" then
	response.Write("正在更新...")
	conn.execute "exec zz91_updateContactcount",ly
	if ly then
		response.Write("更新成功！")
	else
		response.Write("正在更新...")
		response.End()
	end if
end if
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
<style type="text/css">
<!--
.kuang {
	width: 100px;
	border: 1px solid #999999;
	height: 25px;
	padding: 2px;
	background-color: #f2f2f2;
	line-height: 25px;
	text-align: center;
	float: left;
}
body,td,th {
	font-size: 12px;
}
.kuang1 {
	width: 100px;
	border: 1px solid #999999;
	height: 70px;
	padding: 2px;
	background-color: #FFFFCC;
	float: left;
}
.kuang2 {
	width: 100px;
	border: 1px solid #999999;
	height: 70px;
	padding: 2px;
	background-color: #FFFFFF;
	line-height: 50px;
	text-align: center;
	float: left;
}
-->
</style>
<script>
function selectOption(menuname,value)
{
    var menu = document.getElementById(menuname);
	if (menu)
	{
	for(var i=0;i<=menu.options.length;i++){
		if(menu.options[i].value==value)
		{
			menu.options[i].selected = true;
			break;
		}
	}
	}
}
</script>
</head>

<body>
<%
function getcontactcount(datestr,personid)
	sqlTemp="select count(0) from ybp_company where "
	sqlTemp=sqlTemp&" EXISTS(select id from ybp_tel where contactstat=1 and cid=ybp_company.id and personid="&personid&" "
	sqlTemp=sqlTemp&" and fdate>='"&datestr&"' and fdate<='"&cdate(datestr)+1&"')"
	set rs=conn.execute(sqlTemp)
	getcontactcount=rs(0)
end function




sql=sqlTemp
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
if request("year")="" and request("month")="" then 
nowdate=cdate(now)
else
nowdate=cdate(request("year")&"-"&request("month")&"-1")
end if
personid=request("personid")
fromdate=cdate(year(nowdate)&"-"&month(nowdate)&"-1")
todate=cdate(year(nowdate)&"-"&month(nowdate)&"-"&tian_sum(year(nowdate)&"-"&month(nowdate)))
fromweek=DatePart("w",cdate(fromdate))
nyear=request("year")
if nyear="" then
	nyear=year(now)
end if
%>
<table width="760" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#333333">
  <form id="form1" name="form1" method="post" action="">
  <tr>
    <td bgcolor="#FFFFFF">
	<select name="year" id="year">
      <option value="2013">2013</option>
      <option value="2014">2014</option>
      <option value="2015">2015</option>
      <option value="2016">2016</option>
      <option value="2017">2017</option>
      <option value="2018">2018</option>
      <option value="2019">2019</option>
    </select>
    <script>selectOption("year","<%=nyear%>")</script>
	<select name="month" id="month">
	<%
	months=request("month")
	if months="" then months=month(now)
	%>
	<%for i=1 to 12%>
	  <option value="<%=i%>" <%if cstr(i)=cstr(months) then response.Write("selected")%>><%=i%></option>
	<%next%>
	  </select>
	  
	<input type="submit" name="Submit" value="确定" />
	<input name="personid" type="hidden" id="personid" value="<%=personid%>" />
    </td>
  </tr>
  </form>
  <tr>
    <td bgcolor="#FFFFFF">
    <div style="font-size:14px; font-weight:bold"><%=getrealnamet(personid)%><%=request("year")%> - <%=month(nowdate)%>月份有效联系客户统计</div>    </td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">
    <div class="kuang">星期日</div><div class="kuang">星期一</div>
    <div class="kuang">星期二</div>
    <div class="kuang">星期三</div>
    <div class="kuang">星期四</div>
    <div class="kuang">星期五</div>
    <div class="kuang">星期六</div>
    
    <%for i=1 to cint(fromweek)-1%>
    <div class="kuang2"></div>
    <%next%>
    <%for a=fromdate to todate%>
    <div class="kuang1" align="left">
	<%=day(a)%><br>
	<div style="font-size:12px; color:#FF0000; line-height:20px; font-weight:bold" align="center">
	
	
	<a href="../admintellist.asp?personid=<%=personid%>&fromdate=<%=a%>&todate=<%=a%>" target="_blank">
	<%
		response.Write("联系量：<font color=#ff0000>"&getcontactcount(a,personid)&"</font></br>")
	%></a>
	
    <%
	sql="select count(0) from ybp_company WHERE id in (select cid from  ybp_tostar where star=4 and fdate<='"&cdate(a)+1&"' and fdate>='"&a&"' and personid='"&personid&"')"
	set rs=conn.execute(sql)
	if not rs.eof or not rs.bof then
	%>
	<%
		response.Write("开发量：<br>4星<a href='../tj_list.asp?lo=1&personid="&personid&"&star=&4star=1&userid=&from_date="&a&"&to_date="&cdate(a)+1&"' target=_blank><font color=#ff0000>"&rs(0)&"</font></a>")
	%>
	<%
	end if
	rs.close
	set rs=nothing
	%>
    <%
	sql="select count(0) from ybp_company WHERE id in (select cid from  ybp_tostar where star=5 and fdate>='"&a&"' and fdate<='"&cdate(a)+1&"' and personid='"&personid&"')"
	set rs=conn.execute(sql)
	if not rs.eof or not rs.bof then
	%>
	
	<%
		response.Write(" 5星 <a href='../tj_list.asp?lo=1&personid="&personid&"&star=&5star=1&userid=&from_date="&a&"&to_date="&cdate(a)+1&"' target=_blank><font color=#ff0000>"&rs(0)&"</font></a></br>")
	%>
	<%
	end if
	rs.close
	set rs=nothing
	%>
    </div></div>
    <%next%></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">&nbsp;</td>
  </tr>
</table>
</body>
</html>
<%
function getrealnamet(id)
	sqlu="select realname from users where id="&id&""
	set rsu=conn.execute(sqlu)
	if not rsu.eof or not rsu.bof then
		getrealnamet="<font color=#6756ff>"&rsu(0)&"</font>"
	end if
	rsu.close
	set rsu=nothing
end function
conn.close
set conn=nothing
%>
