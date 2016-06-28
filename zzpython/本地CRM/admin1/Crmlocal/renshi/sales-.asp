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
    </td>
  </tr></form>
</table>
<form id="form1" name="form1" target="mb<%=personid%>" method="post" action="" onSubmit="complitesave()">
  <%
bmcodelist=""
if ywadminid<>"" and not isnull(ywadminid)  then
  sqlc="select code,meno from cate_adminuser where code in("&ywadminid&") and code like '____' and closeflag=1 "
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
sqlp="select count(0) from users where closeflag=1 and userid="&rsc("code")&""
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
    <td width="120" bgcolor="#ebebeb"><%response.Write(rsc("meno"))%>（<%=ucount%>人）</td>
    <td width="80" nowrap="nowrap" bgcolor="#ebebeb"><%=month(nowmonth)%>月目标</td>
    <td width="80" nowrap="nowrap" bgcolor="#ebebeb">已完成(折算)</td>
    <td width="60" nowrap="nowrap" bgcolor="#ebebeb">新签+广告</td>
    <td width="60" nowrap="nowrap" bgcolor="#ebebeb">续签</td>
  </tr>
  <%
	sqlu="select realname,id from users where closeflag=1 and userid="&rsc("code")&""
	set rsu=server.CreateObject("ADODB.recordset")
	rsu.open sqlu,conn,1,1
	if not rsu.eof then
	do while not rsu.eof
	personid=rsu("id")
	%>
  <tr>
    <td bgcolor="#FFFFFF"><%response.Write(rsu("realname"))%></td>
    <td align="left" bgcolor="#FFFFFF">
    
      <input name="monthmb" type="text" title="<%=rsc("code")%>" class="txt" id="monthmb<%=personid%>" onKeyPress="changesave(<%=personid%>,this.form)" onKeyUp="changesave(<%=personid%>,this.form)" value="0" size="10" /><a href="sales_save.asp?personid=" onClick="return complitesave(<%=personid%>,this)" target="mb<%=personid%>"><img name="putmb<%=personid%>" id="putmb<%=personid%>" src="../images/save.jpg" style="display:none"/></a>
      <input name="monthmb_old" type="hidden" id="monthmb_old<%=personid%>"/>
      <input type="hidden" name="nowmonth" id="nowmonth" value="<%=nowmonth%>" />
      
    <iframe src="" WIDTH="0" HEIGHT="0" scrolling="no" name="mb<%=personid%>"></iframe>
    </td>
    <td align="right" bgcolor="#FFFFFF"><div id="wangcheng<%=personid%>"></div></td>
    <td align="right" bgcolor="#FFFFFF"><div id="xinqian<%=personid%>"></div></td>
    <td align="right" bgcolor="#FFFFFF"><div id="xuqian<%=personid%>"></div></td>
  </tr>
  <%
    personidlist=personidlist&personid&","
	rsu.movenext
	loop
	end if
	rsu.close()
	set rsu=nothing
  %>
  <tr>
    <td bgcolor="#FFFFFF">小计</td>
    <td align="left" bgcolor="#FFFFFF"><input  name="monthmb<%=rsc("code")%>" type="text" class="txt" id="monthmb<%=rsc("code")%>" value="0" size="10" /></td>
    <td bgcolor="#FFFFFF">&nbsp;</td>
    <td bgcolor="#FFFFFF">&nbsp;</td>
    <td bgcolor="#FFFFFF">&nbsp;</td>
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
    <td width="130" bgcolor="#FFFFFF">总计</td>
    <td width="100" align="left" bgcolor="#FFFFFF"><input  name="monthmb_all" type="text" class="txt" id="monthmb_all" value="0" size="10" /></td>
    
  </tr>
</table>
</form>
<%
personidlist=left(personidlist,len(personidlist)-1)
'bmcodelist=left(bmcodelist,len(bmcodelist)-1)
%>
<script>
function getxiaoji(pid)
{
	var obj=document.getElementById("monthmb"+pid)
	var objcode=document.getElementById("monthmb"+obj.title)
	objcode.value=parseFloat(objcode.value)+parseFloat(obj.value)
}
</script>
<%
'----------到单目标统计
sql="select personid,plan_price from renshi_salesplan where personid in ("&personidlist&") and plan_month="&nowmonth&""
set rs=conn.execute(sql)
if not rs.eof or not rs.bof then
while not rs.eof
	response.Write("<script>document.getElementById('monthmb"&rs("personid")&"').value='"&rs("plan_price")&"';document.getElementById('monthmb_old"&rs("personid")&"').value='"&rs("plan_price")&"'</script>")
	response.Write("<script>document.getElementById('monthmb_all').value=parseFloat(document.getElementById('monthmb_all').value)+parseFloat("&rs("plan_price")&")</script>")
	response.Write("<script>getxiaoji("&rs("personid")&");</script>")
rs.movenext
wend
end if
'--------------完成情况统计
arrpersonlist=split(personidlist,",")
for i=0 to ubound(arrpersonlist)
	sqlm="select sum(sales_price) from renshi_salesIncome where personid="&arrpersonlist(i)&" and sales_date>='"&fromdate&"' and sales_date<='"&todate&"'"
	set rsm=conn.execute(sqlm)
	response.Write("<script>document.getElementById('wangcheng"&arrpersonlist(i)&"').innerHTML='"&rsm(0)&"';</script>")
	rsm.close
	set rsm=nothing
	sqlm="select sum(sales_price) from renshi_salesIncome where personid="&arrpersonlist(i)&" and sales_date>='"&fromdate&"' and sales_date<='"&todate&"' and sales_type='1401'"
	set rsm=conn.execute(sqlm)
	response.Write("<script>document.getElementById('xinqian"&arrpersonlist(i)&"').innerHTML='"&rsm(0)&"';</script>")
	rsm.close
	set rsm=nothing
	sqlm="select sum(sales_price) from renshi_salesIncome where personid="&arrpersonlist(i)&" and sales_date>='"&fromdate&"' and sales_date<='"&todate&"' and sales_type='1402'"
	set rsm=conn.execute(sqlm)
	response.Write("<script>document.getElementById('xuqian"&arrpersonlist(i)&"').innerHTML='"&rsm(0)&"';</script>")
	rsm.close
	set rsm=nothing
next

partmonth=datediff("ww",nowmonth,now)
nowpartmonth=datediff("ww",nowmonth,todate)
%>
<br />
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="left"><input type="button" name="button2" id="button2" value="添加到单数据" onClick="window.open('dataadd.asp','_blank','width=500,height=600')" />
    <br /></td>
  </tr>
</table>
<br />
<table width="100%" border="0" cellspacing="1" cellpadding="3" bgcolor="#000000">
 
  <tr>
  <%
 sqlp="select code,meno from cate_adminuserPart"
 set rsp=conn.execute(sqlp)
 if not rsp.eof or not rsp.bof then
 while not rsp.eof 
 %> 
    <td bgcolor="#f2f2f2"><%=rsp("meno")%></td>
    <%
	rsp.movenext
	wend
	end if
	rsp.close
	set rsp=nothing
	%>
  </tr>
  <tr>
  <%
 sqlp="select code,meno from cate_adminuserPart"
 set rsp=conn.execute(sqlp)
 if not rsp.eof or not rsp.bof then
 while not rsp.eof 
 %> 
    <td bgcolor="#FFFFFF">
    <input type="hidden" id="adminpartvalue<%=rsp("code")%>" value="0"/><div id="adminpart<%=rsp("code")%>"></div>
    </td>
    <%
	rsp.movenext
	wend
	end if
	rsp.close
	set rsp=nothing
	%>
  </tr>
</table>
<%for i=0 to nowpartmonth%>

<br />
<table width="100%" border="0" cellspacing="1" cellpadding="3" bgcolor="#000000">
  <tr>
    <td width="100" align="center" bgcolor="#FFFFFF">W<%=i+1%><br />
    到单
    <input name="alldaodan<%=i%>" type="text" id="alldaodan<%=i%>" value="0" class="txt" size="8" />
    </td>
    <td bgcolor="#FFFFFF">
    <%
	if ywadminid<>"" and not isnull(ywadminid)  then
	  sqlc="select code,meno from cate_adminuser where code in("&ywadminid&") and code like '____' and closeflag=1 "
	else
	  if session("userid")="10" then
		 sqlc="select code,meno from cate_adminuser where code like '13__' and closeflag=1 "
	  else
		 sqlc="select code,meno from cate_adminuser where code like '"&session("userid")&"%' and closeflag=1 "
	  end if
	end if
	set rsc=conn.execute(sqlc)
	if not rsc.eof or not rsc.bof then
	while not rsc.eof
	%>
    <div style="background-color:#ebebeb"><input type="hidden" id="partdaodan<%=rsc("code")%>-<%=i%>" value="0"/>(<span id="part<%=rsc("code")%>-<%=i%>"></span>)<%=rsc("meno")%></div>
    <div id="partw<%=i%>-<%=rsc("code")%>"></div>
    <%
	rsc.movenext
	wend
	end if
	rsc.close
	set rsc=nothing
	%>
    </td>
  </tr>
</table>
<br />
<%next%>
<%
'----------周到单情况
aaa=0
sql="select * from renshi_salesIncome where sales_date>='"&fromdate&"' and sales_date<='"&todate&"' and personid in ("&personidlist&") order by sales_date asc"
'response.Write(sql)
set rs=conn.execute(sql)
if not rs.eof or not rs.bof then
	while not rs.eof
		'--------读取部门编号
		userid=0
		sqlu="select userid from users where id="&rs("personid")&""
		set rsu=conn.execute(sqlu)
		if not rsu.eof or not rsu.bof then
			userid=rsu(0)
		end if
		rsu.close
		set rsu=nothing
		'----------读取区编号
		partID=0
		sqlp="select partID from cate_adminuser where code='"&userid&"'"
		set rsp=conn.execute(sqlp)
		if not rsp.eof or not rsp.bof then
			partID=rsp(0)
		end if
		rsp.close
		set rsp=nothing
		'------------
		aaa=datediff("ww",nowmonth,cdate(rs("sales_date")))
		str=""
		str=str&"<table border=0 cellspacing=0 cellpadding=3>"
        str=str&"<tr>"
        str=str&"<td width=60>"&rs("sales_date")&"</td>"
        str=str&"<td width=60>"&rs("realname")&"</td>"
        str=str&"<td width=60>"&rs("sales_price")&"</td>"
        str=str&"<td width=200>"&rs("sales_email")&"</td>"
		str=str&"<td width=80>"&rs("sales_mobile")&"</td>"
        str=str&"<td width=30><a href=dataedit.asp?id="&rs("id")&" target=_blank>编辑</a></td>"
		str=str&"<td>"&rs("sales_bz")&"</td>"
        str=str&"</tr>"
        str=str&"</table>"
		response.Write("<script>document.getElementById('partw"&aaa&"-"&userid&"').innerHTML+='"&str&"';document.getElementById('alldaodan"&aaa&"').value=parseFloat(document.getElementById('alldaodan"&aaa&"').value)+parseFloat("&rs("sales_price")&");"&chr(13))
		response.Write("document.getElementById('partdaodan"&userid&"-"&aaa&"').value=parseFloat(document.getElementById('partdaodan"&userid&"-"&aaa&"').value)+parseFloat("&rs("sales_price")&");"&chr(13))
		response.Write("document.getElementById('part"&userid&"-"&aaa&"').innerHTML=document.getElementById('partdaodan"&userid&"-"&aaa&"').value;")
		
		response.Write("document.getElementById('adminpartvalue"&partID&"').value=parseFloat(document.getElementById('adminpartvalue"&partID&"').value)+parseFloat("&rs("sales_price")&");"&chr(13))
		response.Write("document.getElementById('adminpart"&partID&"').innerHTML=document.getElementById('adminpartvalue"&partID&"').value;")
		response.Write("</script>")
	rs.movenext
	wend
end if
rs.close
set rs=nothing
%>
</body>
</html>
