<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!--#include file="../../include/include.asp"-->
<!--#include file="../inc.asp"-->
<%
 sqluser="select realname,ywadminid,xuqianFlag,adminuserid from users where id="&session("personid")
 set rsuser=conn.execute(sqluser)
 userName=rsuser(0)
 ywadminid=rsuser(1)
 xuqianFlag=rsuser(2)
 partuserid=rsuser(3)
 adminuserid=rsuser("adminuserid")
 rsuser.close
 set rsuser=nothing
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
function getpricetongji(datestr,useridstr)
	sqld="select sum(sales_price) from renshi_salesIncome where "&datestr&" "&useridstr&""
	set rsd=conn.execute(sqld)
	if not rsd.eof or not rsd.bof then
		salesprice=rsd(0)
		if not isnull(salesprice) and salesprice<>"" then
			getpricetongji=FormatNumber(rsd(0),2)
		else
			getpricetongji="0.00"
		end if
	else
		getpricetongji="0.00"
	end if
	rsd.close
	set rsd=nothing
end function
function getpriceplan(personid,playmonth)
	sqlp="select plan_price from renshi_salesplan where personid="&cstr(personid)&" and plan_month="&cstr(playmonth)&""
	set rsp=conn.execute(sqlp)
	if not rsp.eof or not rsp.bof then
		getpriceplan=rsp(0)
	end if
	rsp.close
	set rsp=nothing
end function
function getpriceplan_lv(personidstr,playmonth)
	sqlp="select plan_price from renshi_salesplan where "&cstr(personidstr)&" and plan_month='"&cstr(playmonth)&"'"
	set rsp=conn.execute(sqlp)
	if not rsp.eof or not rsp.bof then
		planprice=rsp(0)
	else
		planprice=0
	end if
	rsp.close
	set rsp=nothing
	fromdate=playmonth
	todate=cdate(year(fromdate)&"-"&month(fromdate)&"-"&tian_sum(year(fromdate)&"-"&month(fromdate)))
	sqlp="select sum(sales_price) from renshi_salesIncome where sales_date>='"&fromdate&"' and sales_date<='"&todate&"' and "&personidstr&""
	set rsp=conn.execute(sqlp)
	if not rsp.eof or not rsp.bof then
		salesprice=rsp(0)
	else
		salesprice=0
	end if
	rsp.close
	set rsp=nothing
	
	if planprice<>0 and not isnull(planprice) and salesprice<>0 and not isnull(salesprice) then
		getpriceplan_lv=formatnumber((salesprice/planprice)*100,2)
	end if
	
end function
function getpriceplan_p(personid,playmonth)
	sqlp="select plan_price from renshi_salesplan where userid="&cstr(personid)&" and plan_month="&cstr(playmonth)&""
	set rsp=conn.execute(sqlp)
	if not rsp.eof or not rsp.bof then
		getpriceplan_p=rsp(0)
	end if
	rsp.close
	set rsp=nothing
end function
function getprice(sqlstr,fromdate,todate)
	sqlp="select sum(sales_price) from renshi_salesIncome where sales_date>='"&fromdate&"' and sales_date<='"&todate&"' "&sqlstr&""
	set rsp=conn.execute(sqlp)
	if not rsp.eof or not rsp.bof then
		salesprice=rsp(0)
		if not isnull(salesprice) and salesprice<>"" then
			getprice=FormatNumber(salesprice,2)
		else
			getprice="0.00"
		end if 
	else
		getprice="0.00"
	end if
	rsp.close
	set rsp=nothing
end function
 nowdate=now
 if request.Form("fmonth")<>"" then 
 	nowdate=request.Form("fmonth")
	response.Write(nowdate)
 end if
 nowmonth=year(nowdate)&"-"&month(nowdate)&"-1"
 fromdate=cdate(year(nowdate)&"-"&month(nowdate)&"-1")
 todate=cdate(year(nowdate)&"-"&month(nowdate)&"-"&tian_sum(year(nowdate)&"-"&month(nowdate)))
 fromweek=DatePart("w",cdate(fromdate))
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
.txt
{
	border: 1px solid #000000;
	text-align:right;
}
.havesave
{
	filter: Gray;	
}
img
{
	border:0;
}
-->
</style>
<script>
function validateNum(obj){
    	var str = document.getElementById(obj.id).value;
    	var patn1 =   /^[0-9]+$/;
    	if(!patn1.test(str) ) return false;
    	return true; 
	}
function chknum(NUM) 
{ 
	var i,j,strTemp; 
	strTemp=".0123456789"; 
	if ( NUM.length== 0) 
	return 0 
	for (i=0;i<NUM.length;i++) 
	{ 
		j=strTemp.indexOf(NUM.charAt(i)); 
		if (j==-1) 
		{ 
			//说明有字符不是数字 
			return false; 
		} 
	} 
	//说明是数字 
	return true; 
}
function changesave(pid,frm)
{
	
	var obj=document.getElementById("monthmb"+pid)
	var obj_old=document.getElementById("monthmb_old"+pid)
	var obj_save=document.getElementById("putmb"+pid)
	if (!chknum(obj.value) && obj.value!="")
	{
		alert("请输入数字！");
		obj.value=""
		return false;
		
	}
	if (obj.value!=obj_old.value)
	{
		obj_save.style.display=""
		obj_save.className=""
	}else
	{
		obj_save.style.display="none"
	}
	
}
function changesave_p(pid,frm)
{
	
	var obj=document.getElementById("monthpm"+pid)
	var obj_old=document.getElementById("monthpm_old"+pid)
	var obj_save=document.getElementById("putpm"+pid)
	if (!chknum(obj.value) && obj.value!="")
	{
		alert("请输入数字！");
		obj.value=""
		return false;
		
	}
	if (obj.value!=obj_old.value)
	{
		obj_save.style.display=""
		obj_save.className=""
	}else
	{
		obj_save.style.display="none"
	}
	
}
function changembround(pid)
{
	mbvalue=Math.round(obj.value)
	var obj=document.getElementById("monthmb"+pid)
	
}
function complitesave(pid,aobj)
{
	var obj=document.getElementById("monthmb"+pid)
	var obj_save=document.getElementById("putmb"+pid)
	obj_save.className="havesave"
	obj_save.style.display="none"
	aobj.href="sales_save.asp?personid="+pid+"&monthmb="+obj.value+"&nowmonth=<%=nowmonth%>"
	//alert(aobj.href)
}
function complitesave_p(pid,aobj)
{
	var obj=document.getElementById("monthpm"+pid)
	var obj_save=document.getElementById("putpm"+pid)
	obj_save.className="havesave"
	obj_save.style.display="none"
	
	aobj.href="sales_save.asp?personid=0&userid="+pid+"&monthmb="+obj.value+"&nowmonth=<%=nowmonth%>"
}
function saveall()
{
	var obj=document.getElementsByTagName("form");
	for (i=0;i<=obj.length;i++)
	{
		obj.item(i).submit()
	}
}
</script>
<SCRIPT language=javascript src="../DatePicker.js"></SCRIPT>
<link href="../datepicker.css" rel="stylesheet" type="text/css">
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <form id="form2" name="form2" method="post" action=""><tr>
    <td>选择月份：<script language=javascript>createDatePicker("fmonth",false,"<%=fmonth%>",true,true,true,true)</script>
      
        <input type="submit" name="button" id="button" value="提交" />
        <a href="dataadd0.asp">添加到单数据</a> <a href="daytongji.asp">查看日到单统计</a></td>
  </tr></form>
</table>
<form id="form1" name="form1" target="mb<%=personid%>" method="post" action="" onSubmit="complitesave()">
  <%
bmcodelist=""
if ywadminid<>"" and not isnull(ywadminid)  then
  sqlc="select code,meno from cate_adminuser where code in("&ywadminid&")  and closeflag=1 "
else
  if session("userid")="10" then
	 sqlc="select code,meno from cate_adminuser where code like '13__' and closeflag=1 "
  else
	 sqlc="select code,meno from cate_adminuser where code like '"&session("userid")&"%' and closeflag=1 "
  end if
end if
set rsc=conn.execute(sqlc)
personidlist=""
if not rsc.eof then
i=1
while not rsc.eof
bmcodelist=bmcodelist&rsc("code")&","
sqlp="select count(0) from users where closeflag=1 and userid='"&rsc("code")&"'"
set rsp=conn.execute(sqlp)
ucount=rsp(0)
rsp.close
set rsp=nothing
if i mod 2 = 1 then
response.Write("<div style='clear:both'></div>")
end if
%>
  <table border="0" cellspacing="1" cellpadding="1" bgcolor="#ebebeb" style="float:left; margin-left:5px">
    <tr>
    <td width="150" bgcolor="#ebebeb"><%response.Write(rsc("meno"))%>（<%=ucount%>人）</td>
    <td width="80" nowrap="nowrap" bgcolor="#ebebeb"><%=month(nowmonth)%>月目标</td>
    <td width="80" nowrap="nowrap" bgcolor="#ebebeb">已完成</td>
    <td width="60" nowrap="nowrap" bgcolor="#ebebeb">完成率</td>
    </tr>
  <%
	sqlu="select realname,id from users where closeflag=1 and userid='"&rsc("code")&"'"
	set rsu=server.CreateObject("ADODB.recordset")
	rsu.open sqlu,conn,1,1
	if not rsu.eof then
	do while not rsu.eof
	personid=rsu("id")
	%>
  <tr>
    <td bgcolor="#FFFFFF"><%response.Write(rsu("realname"))%></td>
    <td align="left" bgcolor="#FFFFFF">
    
      <input name="monthmb" type="text" title="<%=rsc("code")%>" class="txt" id="monthmb<%=personid%>" onKeyPress="changesave(<%=personid%>,this.form)" onKeyUp="changesave(<%=personid%>,this.form)" value="<%=getpriceplan("'"&personid&"'","'"&nowmonth&"'")%>" size="10" /><a href="sales_save.asp?personid=" onClick="return complitesave(<%=personid%>,this)" target="mb<%=personid%>"><img name="putmb<%=personid%>" id="putmb<%=personid%>" src="../images/save.jpg" style="display:none"/></a>
      <input name="monthmb_old" type="hidden" id="monthmb_old<%=personid%>"/>
      <input type="hidden" name="nowmonth" id="nowmonth" value="<%=nowmonth%>" />
      
    <iframe src="" WIDTH="0" HEIGHT="0" scrolling="no" name="mb<%=personid%>"></iframe>
    </td>
    <td align="right" bgcolor="#FFFFFF"><%=getprice(" and personid="&personid&"",fromdate,todate)%></td>
    <td align="right" bgcolor="#FFFFFF"><%=getpriceplan_lv(" personid="&personid&"",""&nowmonth&"")%>%</td>
    </tr>
  <%
	rsu.movenext
	loop
	end if
	rsu.close()
	set rsu=nothing
	userid=rsc("code")
  %>
  <tr>
    <td bgcolor="#FFFFFF">小计</td>
    <td align="left" bgcolor="#FFFFFF"><input name="monthpm" type="text" title="<%=userid%>" class="txt" id="monthpm<%=userid%>" onKeyPress="changesave_p(<%=userid%>,this.form)" onKeyUp="changesave_p(<%=userid%>,this.form)" value="<%=getpriceplan_p("'"&userid&"'","'"&nowmonth&"'")%>" size="10" /><a href="sales_save.asp?personid=" onClick="return complitesave_p(<%=userid%>,this)" target="pm<%=userid%>"><img name="putpm<%=userid%>" id="putpm<%=userid%>" src="../images/save.jpg" style="display:none"/></a></td>
    <td align="right" bgcolor="#FFFFFF">
    <iframe src="" WIDTH="0" HEIGHT="0" scrolling="no" name="pm<%=userid%>"></iframe>
    <input name="monthpm_old" type="hidden" id="monthpm_old<%=userid%>"/><%=getprice(" and userid='"&userid&"'",fromdate,todate)%></td>
    <td align="right" bgcolor="#FFFFFF"><%=getpriceplan_lv(" userid="&userid&"",""&nowmonth&"")%>%</td>
    </tr>
</table>
<%
i=i+1
rsc.movenext
wend
end if
rsc.close
set rsc=nothing
%>
<div style="clear:both"></div>
<table border="0" cellspacing="1" cellpadding="1" bgcolor="#ebebeb">
<tr>
    <td width="155" bgcolor="#FFFFFF">总计</td>
    <td width="100" align="left" bgcolor="#FFFFFF"><input  name="monthmb_all" type="text" class="txt" id="monthmb_all" value="0" size="10" /></td>
    
  </tr>
</table>
</form>

<br />
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="left"><br /></td>
  </tr>
</table>
</body>
</html>
