<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/ad!$#5V.asp" -->
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
sqla=""
sqlTemp="select count(0) from temp_salescomp WHERE "
sqlTemp=sqlTemp&" not EXISTS(select null from comp_sales where (com_type=13) and com_id=temp_salescomp.com_id) "
'sqlTemp=sqlTemp&" and not EXISTS(select null from comp_sales where com_Especial=1 and com_id=temp_salescomp.com_id)"
sqlTemp=sqlTemp&" and not EXISTS(select com_id from crm_OtherComp where com_id=temp_salescomp.com_id)"
sqlTemp=sqlTemp&" and not EXISTS(select com_id from crm_InsertCompWeb where com_id=temp_salescomp.com_id and saletype=2)"
sqlTemp=sqlTemp&" and not EXISTS(select com_id from crm_webPipeiComp where com_id=temp_salescomp.com_id )"
'sqlTemp=sqlTemp&" and not EXISTS(select com_id from Crm_Active_CompAll where com_id=temp_salescomp.com_id and ActivePublic=0 and not exists (select null from Crm_Active_ActiveComp where com_id=Crm_Active_CompAll.com_id))"
			
				sqla=sqla&" and teldate>='"&date&"' and teldate<='"&cdate(date)+1&"'"
				sqla=sqla&" and contacttype =13"
sqlTemp=sqlTemp&" and EXISTS(select com_id from comp_tel where com_id=temp_salescomp.com_id "
sqlTemp=sqlTemp&" and personid="&request.QueryString("personid")

sqlTemp=sqlTemp&"  "&sqla&")"
sql=sqlTemp
'response.Write(sql)
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
      <option value="2008">2008</option>
      <option value="2009">2009</option>
      <option value="2010">2010</option>
      <option value="2011">2011</option>
      <option value="2012">2012</option>
      <option value="2013">2013</option>
      <option value="2014">2014</option>
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
	  <%
	  redate=""
	  sqln="select top 1 redate from crm_EveryContactCount where fdate='"&date()&"'"
	  set rsn=conn.execute(sqln)
	  if not rsn.eof or not rsn.bof then
	  redate=rsn(0)
	  end if
	  rsn.close
	  set rsn=nothing
	  %>
	<input type="submit" name="Submit" value="确定" />
	<input name="personid" type="hidden" id="personid" value="<%=personid%>" />
	<!--<input type="button" name="Submit2" value="更新数据" onclick="window.location='?re=1&personid=<%=personid%>'" /> <font color="#CC0000">(不要经常去更新，系统每天会在晚上自动更新)</font>-->
	最近更新时间：<%=redate%></td>
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
	
	<%
	sql="select ContactCount from crm_EveryContactCount where fdate='"&a&"' and personid='"&personid&"'"
	set rs=conn.execute(sql)
	if not rs.eof or not rs.bof then
	%>
	<a href="../admin/admin_tel_comp1.asp?personid=<%=personid%>&contacttype=13&fromdate=<%=a%>&todate=<%=a%>" target="_blank">
	<%
		response.Write("联系量：<font color=#ff0000>"&rs(0)&"</font></br>")
	%></a>
	<%
	end if
	rs.close
	set rs=nothing
	%>
    <%
	sql="select count(0) from v_salescomp WHERE com_id in (select com_id from crm_To4star where fdate>='"&a&"' and fdate<='"&cdate(a)+1&"' and personid='"&personid&"')"
	set rs=conn.execute(sql)
	if not rs.eof or not rs.bof then
	%>
	
	<%
		response.Write("开发量：<br>4星<a href='/admin1/crmlocal/admin/admin_tj_comp.asp?lo=1&personid="&personid&"&star=&4star=1&userid=&from_date="&a&"&to_date="&cdate(a)+1&"' target=_blank><font color=#ff0000>"&rs(0)&"</font></a>")
	%>
	<%
	end if
	rs.close
	set rs=nothing
	%>
    <%
	sql="select count(0) from v_salescomp WHERE com_id in (select com_id from crm_To5star where fdate>='"&a&"' and fdate<='"&cdate(a)+1&"' and personid='"&personid&"')"
	set rs=conn.execute(sql)
	if not rs.eof or not rs.bof then
	%>
	
	<%
		response.Write(" 5星 <a href='/admin1/crmlocal/admin/admin_tj_comp.asp?lo=1&personid="&personid&"&star=&5star=1&userid=&from_date="&a&"&to_date="&cdate(a)+1&"' target=_blank><font color=#ff0000>"&rs(0)&"</font></a></br>")
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
