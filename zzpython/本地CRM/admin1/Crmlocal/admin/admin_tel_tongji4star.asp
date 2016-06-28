<%
if session("userid")<>"10" then
'response.Write("暂时关闭")
'response.End()
end if
from_date=request("from_date")
to_date=request("to_date")
sfrom_date=request("sfrom_date")
sto_date=request("sto_date")
%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!-- #include file="../../../cn/function.asp" -->
<!-- #include file="../../include/include.asp" -->
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
 response.Write(ywadminid)
%>
<html><head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>联系情况统计</title>
<link href="../../css.css" rel="stylesheet" type="text/css">
<link href="../../inc/Style.css" rel="stylesheet" type="text/css">
<SCRIPT language=javascript src="../DatePicker.js"></SCRIPT>
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
<link href="../datepicker.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
.STYLE6 {color: #FFFFFF}
.STYLE7 {color: #FF6600}
.STYLE10 {
	font-size: 14px;
	color: #FF0000;
	font-weight: bold;
}
-->
</style>
</head>

<body bgcolor="7386A5" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<br>
<% 
userid=session("userid")
if ywadminid<>"" then
	userid=ywadminid
end if

sql2="select * from cate_adminuser where code in ("&userid&") and closeflag=1 order by id desc" 
		  set rsClass=conn.execute(sql2)
		  if not (rsClass.eof and rsClass.bof) then
		  %>
          <table width="800" border="0" align="center" cellpadding="4" cellspacing="1" bgcolor="#7386A5">
            <tr>
              <td align="center" bgcolor="#FFFFFF"><a href="?">全部</a></td>
            <% while not rsClass.eof %>
              <td align="center" bgcolor="#FFFFFF"><a href="?userClassId=<% =rsClass("code") %>&weekflag=<%= request("weekflag") %>&moth=<%= request("moth") %>&dotype=<%=request("dotype")%>"><%= rsClass("meno") %></a></td>
              <% 
			 rsClass.movenext
			 wend
			 %>
            </tr>
</table>
<br>
<% End If 
  rsClass.close
  set rsClass=nothing
  %>
		  
          <table width="800" border="0" align="center" cellpadding="4" cellspacing="0">
            <form name="form1" method="get" action=""><tr>
              <td width="100" align="left" bgcolor="#FFFFFF">转换时间从</td>
              <td height="30" align="left" bgcolor="#FFFFFF">
    <input type="hidden" name="userClassId" id="userClassId" value="<%=request("userClassId")%>">
    <script language=javascript>createDatePicker("from_date",true,"<%=from_date%>",false,false,true,true)</script>
    <script language=javascript>createDatePicker("to_date",true,"<%=to_date%>",false,false,true,true)</script>
    <select name="regdate" id="regdate">
    	<option value="">注册时间</option>
        <option value="3">3天内注册</option>
        <option value="7">15天内注册</option>
        <option value="15">15天内注册</option>
        <option value="30">1个月内注册</option>
        <option value="60">2个月内注册</option>
        <option value="90">3个月内注册</option>
        <option value="180">6个月内注册</option>
        <option value="365">1年内注册</option>
        <option value="365">1年以上注册</option>
    </select>    
    <script>selectOption("regdate","<%=request("regdate")%>")</script>
    <input type="submit" name="Submit" value="搜索">
    <a href="/admin1/Crmlocal/renshi/sales.asp?lmcode=2797" target="_blank"><font style="font-size:18px">销售到单统计</font></a></td>
            </tr>
             <!-- <tr>
                <td align="left" bgcolor="#FFFFFF">到单时间(1)从</td>
                <td height="30" align="left" bgcolor="#FFFFFF">
    <script language=javascript>createDatePicker("sfrom_date",true,"<%=sfrom_date%>",false,true,true,true)</script>
    <script language=javascript>createDatePicker("sto_date",true,"<%=sto_date%>",false,true,true,true)</script>
    </td>
              </tr>
              <tr>
                <td align="left" bgcolor="#FFFFFF">到单时间(2)从</td>
                <td height="30" align="left" bgcolor="#FFFFFF">
    <script language=javascript>createDatePicker("sfrom_date1",true,"<%=sfrom_date1%>",false,true,true,true)</script>
    <script language=javascript>createDatePicker("sto_date1",true,"<%=sto_date1%>",false,true,true,true)</script>
    </td>
              </tr>
              <tr>
                <td align="left" bgcolor="#FFFFFF">到单时间(3)从</td>
                <td height="30" align="left" bgcolor="#FFFFFF">
    <script language=javascript>createDatePicker("sfrom_date2",true,"<%=sfrom_date2%>",false,true,true,true)</script>
    <script language=javascript>createDatePicker("sto_date2",true,"<%=sto_date2%>",false,true,true,true)</script>
    </td>
              </tr>
              <tr>
                <td align="left" bgcolor="#FFFFFF">到单时间(4)从</td>
                <td height="30" align="left" bgcolor="#FFFFFF">
    <script language=javascript>createDatePicker("sfrom_date3",true,"<%=sfrom_date3%>",false,true,true,true)</script>
    <script language=javascript>createDatePicker("sto_date3",true,"<%=sto_date3%>",false,true,true,true)</script></td>
              </tr>-->
            </form>
          </table>
<table width="800" border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#000000">
  <tr>
    <td colspan="2" align="center" bgcolor="#CCCCCC"><%=from_date%> 到 <%=to_date%></td>
  </tr>
</table>
<table width="800" border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#000000">
  <tr>
    <td align="center" bgcolor="#FFFFFF">&nbsp;</td>
    <td align="center" bgcolor="#FFFFFF">&nbsp;</td>
    <td align="center" bgcolor="#FFFFFF">&nbsp;</td>
    <td colspan="2" align="center" bgcolor="#FFFFFF">到单情况</td>
  </tr>
  <tr>
    <td align="center" bgcolor="#FFFFFF">Sales</td>
    <td align="center" bgcolor="#FFFFFF">转5星</td>
    <td align="center" bgcolor="#FFFFFF">转4星</td>
    <td align="center" bgcolor="#FFFFFF">4、5星到单情况</td>
    <td align="center" bgcolor="#FFFFFF">到单情况</td>
  </tr>
  <%
  code=1301
  if request("userClassId")<>"" then
  	code=request("userClassId")
	userid=code
  else
	  if session("userid")<>"" and session("userid")<>"10" then
		code=session("userid")
	  end if
	  if session("personid")="93" or session("userid")="10" then
	  	code="13"
	  end if
	  userid=code
  end if
  if left(request("dotype"),3)="vap" then
  	userid="1315"
  end if
  sql="select code,meno from cate_adminuser"
  sql=sql&" where code in ("&userid&") and closeflag=1 "
  set rs=conn.execute(sql)
  if not rs.eof or not rs.bof then
  while not rs.eof 
  %>
  <tr>
    <td bgcolor="#f2f2f2"><%=rs("meno")%></td>
    <td bgcolor="#f2f2f2">&nbsp;</td>
    <td bgcolor="#f2f2f2">&nbsp;</td>
    <td bgcolor="#f2f2f2">&nbsp;</td>
    <td bgcolor="#f2f2f2">&nbsp;</td>
  </tr>
  <%
  parttongji_4=0
  parttongji_5=0
  parttongji_d=0
  parttongji_change=0
  sql1="select realname,id from users where userid="&rs("code")&" and closeflag=1"
  if ywadminid="0" then
  	sql1=sql1&" and id="&session("personid")
  end if
  set rs1=conn.execute(sql1)
  if not rs1.eof or not rs1.bof then
  while not rs1.eof 
  %>
  <tr>
    <td bgcolor="#FFFFFF"><%=rs1("realname")%></td>
    <td align="center" bgcolor="#FFFFFF">
    <%
	'---------统计转5星客户
	sqlreg=""
	regdate=request("regdate")
	if request("regdate")<>"" then
		sqlreg=sqlreg&" and  DATEDIFF(DD, com_regtime, GETDATE()) <= "&request("regdate")&""
	end if
	sqlt=""
	sqlTemp=""
	sqlTemp=sqlTemp&" and not EXISTS(select null from comp_sales where com_type=13 and com_id=crm_to5star.com_id) "
	'sqlTemp=sqlTemp&" and not EXISTS(select null from Comp_ZSTinfo where com_id=crm_to5star.com_id)"
	sqlt=sqlt&sqlTemp
	if request("from_date")<>"" then
		sqlt=sqlt&" and fdate>='"&request("from_date")&"'"
	end if
	if request("to_date")<>"" then
		sqlt=sqlt&" and fdate<='"&CDate(request("to_date"))&"'"
	end if
	if request("from_date")="" and request("to_date")="" then
		sqlt=sqlt&" and fdate>='"&date&"'"
	end if
	cout5=0
	sqlc="select count(0) from v_salescomp where com_id in (select com_id from  crm_to5star where personid="&rs1("id")&sqlt&") "&sqlreg

	set rsc=conn.execute(sqlc)
	if not rsc.eof or not rsc.bof then
		cout5=rsc(0)
	end if
	rsc.close
	set rsc=nothing
	parttongji_5=parttongji_5+cint(cout5)
	'----------------------------------------------
	
	if cout5>0 then
		response.Write("<a href='admin_tj_comp.asp?lo=1&personid="&rs1("id")&"&star=&5star=1&from_date="&request("from_date")&"&to_date="&request("to_date")&"&regdate="&regdate&"' target=_blank><font color=#ff0000>"&cout5&"</font></a>")
	else
		response.Write(0)
	end if
	'------------------------------
	'---------------到单统计1
	sqlt=""
	if request("sfrom_date")<>"" then
		sqlt=sqlt&" and sales_date>='"&request("sfrom_date")&"'"
	end if
	if request("sto_date")<>"" then
		sqlt=sqlt&" and sales_date<='"&CDate(request("sto_date"))+1&"'"
	end if
	if request("sfrom_date")="" and request("sto_date")="" then
		sqlt=sqlt&" and sales_date>='"&date&"'"
	end if
	dcount=""
	sqlc="select count(0) from renshi_salesIncome where personid="&rs1("id")&" and com_id>0 "&sqlt
	set rsc=conn.execute(sqlc)
	if not rsc.eof or not rsc.bof then
		dcount=rsc(0)
	end if
	rsc.close
	set rsc=nothing
	'------------------------------------
	'---------------到单统计2
	sqlt=""
	sfromdate1=request("sfrom_date1")
	stodate1=request("sto_date1")
	if sfromdate1<>"" and stodate1<>"" then
		if request("sfrom_date1")<>"" then
			sqlt=sqlt&" and sales_date>='"&sfromdate1&"'"
		end if
		if request("sto_date1")<>"" then
			sqlt=sqlt&" and sales_date<='"&CDate(stodate1)+1&"'"
		end if
		'dcount1=""
'		sqlc="select count(0) from renshi_salesIncome where personid="&rs1("id")&" and com_id>0 "&sqlt
'		set rsc=conn.execute(sqlc)
'		if not rsc.eof or not rsc.bof then
'			dcount1=rsc(0)
'		end if
'		rsc.close
'		set rsc=nothing
	else
		dcount1=""
	end if
	'------------------------------------
	'---------------到单统计3
	sqlt=""
	sfromdate1=request("sfrom_date2")
	stodate1=request("sto_date2")
	if sfromdate1<>"" and stodate1<>"" then
		if request("sfrom_date1")<>"" then
			sqlt=sqlt&" and sales_date>='"&sfromdate1&"'"
		end if
		if request("sto_date1")<>"" then
			sqlt=sqlt&" and sales_date<='"&CDate(stodate1)+1&"'"
		end if
		'dcount2=""
'		sqlc="select count(0) from renshi_salesIncome where personid="&rs1("id")&" and com_id>0 "&sqlt
'		set rsc=conn.execute(sqlc)
'		if not rsc.eof or not rsc.bof then
'			dcount2=rsc(0)
'		end if
'		rsc.close
'		set rsc=nothing
	else
		dcount2=""
	end if
	'------------------------------------
	'---------------到单统计3
	sqlt=""
	sfromdate1=request("sfrom_date3")
	stodate1=request("sto_date3")
	if sfromdate1<>"" and stodate1<>"" then
		if request("sfrom_date1")<>"" then
			sqlt=sqlt&" and sales_date>='"&sfromdate1&"'"
		end if
		if request("sto_date1")<>"" then
			sqlt=sqlt&" and sales_date<='"&CDate(stodate1)+1&"'"
		end if
		'dcount3=""
'		sqlc="select count(0) from renshi_salesIncome where personid="&rs1("id")&" and com_id>0 "&sqlt
'		set rsc=conn.execute(sqlc)
'		if not rsc.eof or not rsc.bof then
'			dcount3=rsc(0)
'		end if
'		rsc.close
'		set rsc=nothing
	else
		dcount3=""
	end if
	'------------------------------------
	
	
	'response.Write("|"&d4count)
	
	%>
    </td>
    <td align="center" bgcolor="#FFFFFF">
	<%
	'-----------------统计转4星客户
	cout4=0
	sqlt=""
	sqlTemp=""
	sqlTemp=sqlTemp&" and not EXISTS(select null from comp_sales where com_type=13 and com_id=b.com_id) "
	'sqlTemp=sqlTemp&" and not EXISTS(select null from Comp_ZSTinfo where com_id=crm_to4star.com_id)"
	sqlt=sqlt&sqlTemp
	if request("from_date")<>"" then
		sqlt=sqlt&" and b.fdate>='"&request("from_date")&"'"
	end if
	if request("to_date")<>"" then
		sqlt=sqlt&" and b.fdate<='"&CDate(request("to_date"))&"'"
	end if
	if request("from_date")="" and request("to_date")="" then
		sqlt=sqlt&" and b.fdate>='"&date&"'"
	end if
	
	'---------------到单统计
	sqlts=""
	if request("sfrom_date")<>"" then
		sqlts=sqlts&" and a.sales_date>='"&request("sfrom_date")&"'"
	end if
	if request("sto_date")<>"" then
		sqlts=sqlts&" and a.sales_date<='"&CDate(request("sto_date"))+1&"'"
	end if
	if request("sfrom_date")="" and request("sto_date")="" then
		sqlts=sqlts&" and a.sales_date>='"&date&"'"
	end if
	
	sqlc="select count(0) from v_salescomp where com_id in (select com_id from crm_to4star as b where b.personid="&rs1("id")&sqlt&") "&sqlreg
	
	set rsc=conn.execute(sqlc)
	if not rsc.eof or not rsc.bof then
		cout4=rsc(0)
	end if
	rsc.close
	set rsc=nothing
	parttongji_4=parttongji_4+cint(cout4)
	
	'----------------
	d4count=0
	sqlc="select count(0) from renshi_salesIncome as a where a.personid="&rs1("id")&" and a.com_id>0 and (exists(select b.com_id from crm_to5star as b where b.com_id=a.com_id "&sqlt&") or exists(select b.com_id from crm_to4star as b where b.com_id=a.com_id "&sqlt&")) "&sqlts
	'response.Write(sqlc)
	set rsc=conn.execute(sqlc)
	if not rsc.eof or not rsc.bof then
		d4count=rsc(0)
	end if
	rsc.close
	set rsc=nothing
	
	if cout4>0 then
		response.Write("<a href='admin_tj_comp.asp?lo=1&personid="&rs1("id")&"&star=&4star=1&from_date="&request("from_date")&"&to_date="&request("to_date")&"&sfrom_date="&request("sfrom_date")&"&sto_date="&request("sto_date")&"&regdate="&regdate&"' target=_blank><font color=#ff0000>"&cout4&"</font></a>")
	else
		response.Write(0)
	end if
	'---------------------------
	%></td>
    <td align="center" bgcolor="#FFFFFF">
    <%
	if d4count>0 then
	response.Write("<a href='admin_tj_comp.asp?lo=1&personid="&rs1("id")&"&star=&daodan=4&from_date="&request("sfrom_date")&"&to_date="&request("sto_date")&"&sfrom_date="&request("sfrom_date")&"&sto_date="&request("sto_date")&"&regdate="&regdate&"' target=_blank><font color=#ff0000>"&d4count&"</font></a>")
	else
	response.Write(0)
	end if
	parttongji_change=parttongji_change+cint(d4count)
	%>
    </td>
    <td align="center" bgcolor="#FFFFFF">
    <%
	if dcount>0 then
	response.Write("<a href='admin_tj_comp.asp?lo=1&personid="&rs1("id")&"&star=&daodan=1&from_date="&request("sfrom_date")&"&to_date="&request("sto_date")&"&regdate="&regdate&"' target=_blank><font color=#ff0000>"&dcount&"</font></a>")
	else
	response.Write(0)
	end if
	parttongji_d=parttongji_d+cint(dcount)
	%></td>
  </tr>
  <%
  rs1.movenext
  wend
  end if
  rs1.close
  set rs1=nothing
  %>
  <tr>
    <td bgcolor="#FFFFCC">小计</td>
    <td align="center" bgcolor="#FFFFCC"><%=parttongji_5%></td>
    <td align="center" bgcolor="#FFFFCC"><%=parttongji_4%></td>
    <td align="center" bgcolor="#FFFFCC"><%=parttongji_change%></td>
    <td align="center" bgcolor="#FFFFCC"><%=parttongji_d%></td>
  </tr>
  <%
  rs.movenext
  wend
  end if
  rs.close
  set rs=nothing
  %>
  
</table>
<br>
<table width="800" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>注明：转4星客户统计(此统计从2008-8-19开始统计) <font color="#FF0000">转5星客户2009-12-23 18：00开始统计 </font></td>
  </tr>
</table>
</body>
</html>
<%
conn.close
set conn=nothing
%>
