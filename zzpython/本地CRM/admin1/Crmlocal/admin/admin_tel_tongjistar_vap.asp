<%
if session("userid")<>"10" then
'response.Write("暂时关闭")
'response.End()
end if
from_date=request("from_date")
to_date=request("to_date")
sfrom_date=request("sfrom_date")
sto_date=request("sto_date")
dotype=request("dotype")
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
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>联系情况统计</title>
<link href="../../css.css" rel="stylesheet" type="text/css">
<link href="../../inc/Style.css" rel="stylesheet" type="text/css">
<SCRIPT language=javascript src="../DatePicker.js"></SCRIPT>
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
%>
<br>

		  
          <table width="800" border="0" align="center" cellpadding="4" cellspacing="0">
            <form name="form1" method="get" action=""><tr>
              <td width="100" align="left" bgcolor="#FFFFFF">转换时间从</td>
              <td height="30" align="left" bgcolor="#FFFFFF">
    <input type="hidden" name="userClassId" id="userClassId" value="<%=request("userClassId")%>">
    <script language=javascript>createDatePicker("from_date",true,"<%=from_date%>",false,true,true,true)</script>
    <script language=javascript>createDatePicker("to_date",true,"<%=to_date%>",false,true,true,true)</script><input type="submit" name="Submit" value="搜索">
    <input type="hidden" name="dotype" id="dotype" value="<%=request("dotype")%>"></td>
            </tr>
             
            </form>
          </table>
<table width="800" border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#000000">
  <tr>
    <td colspan="2" align="center" bgcolor="#CCCCCC">转4\5星客户统计(此统计从2010-10-10开始统计)</td>
  </tr>
</table>
<table width="800" border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#000000">
  <tr>
    <td align="center" bgcolor="#FFFFFF">&nbsp;</td>
    <td align="center" bgcolor="#FFFFFF">&nbsp;</td>
    <td align="center" bgcolor="#FFFFFF">&nbsp;</td>
    
  </tr>
  <tr>
    <td align="center" bgcolor="#FFFFFF">Sales</td>
    <td align="center" bgcolor="#FFFFFF">转5星</td>
    <td align="center" bgcolor="#FFFFFF">转4星</td>
    
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
  end if
  if left(request("dotype"),3)="vap" then
  	userid="1315,13"
  end if
  sql="select code,meno from cate_adminuser"
  sql=sql&" where code in ("&userid&")"
  
  set rs=conn.execute(sql)
  if not rs.eof or not rs.bof then
  while not rs.eof 
  %>
  <tr>
    <td bgcolor="#f2f2f2"><%=rs("meno")%></td>
    <td bgcolor="#f2f2f2">&nbsp;</td>
    <td bgcolor="#f2f2f2">&nbsp;</td>
    
  </tr>
  <%
  parttongji_4=0
  parttongji_5=0
  parttongji_d=0
  parttongji_change=0
  sql1="select realname,id from users where userid="&rs("code")&" and closeflag=1"
  set rs1=conn.execute(sql1)
  if not rs1.eof or not rs1.bof then
  while not rs1.eof 
  %>
  <tr>
    <td bgcolor="#FFFFFF"><%=rs1("realname")%></td>
    <td align="center" bgcolor="#FFFFFF">
    <%
	'---------统计转5星客户
	sqlt=""
	sqlTemp=""
	sqlTemp=sqlTemp&" and not EXISTS(select null from comp_sales where com_type=13 and com_id=crm_Tostar_vap.com_id) "
	'sqlTemp=sqlTemp&" and not EXISTS(select null from Comp_ZSTinfo where com_id=crm_to5star.com_id)"
	sqlt=sqlt&sqlTemp
	if request("from_date")<>"" then
		sqlt=sqlt&" and fdate>='"&request("from_date")&"'"
	end if
	if request("to_date")<>"" then
		sqlt=sqlt&" and fdate<='"&CDate(request("to_date"))+1&"'"
	end if
	if request("from_date")="" and request("to_date")="" then
		sqlt=sqlt&" and fdate>='"&date&"'"
	end if
	cout5=0
	sqlc="select count(0) from crm_Tostar_vap where personid="&rs1("id")&sqlt&" and exists (select com_id from crm_assignVap where personid="&rs1("id")&" and com_id=crm_Tostar_vap.com_id) and star=5"
	set rsc=conn.execute(sqlc)
	if not rsc.eof or not rsc.bof then
		cout5=rsc(0)
	end if
	rsc.close
	set rsc=nothing
	parttongji_5=parttongji_5+cint(cout5)
	'----------------------------------------------
	
	if cout5>0 then
		response.Write("<a href='admin_tj_comp.asp?lo=1&personid="&rs1("id")&"&vapstar=5&from_date="&request("from_date")&"&to_date="&request("to_date")&"&dotype="&dotype&"' target=_blank><font color=#ff0000>"&cout5&"</font></a>")
	else
		response.Write(0)
	end if
	'------------------------------
	
	
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
		sqlt=sqlt&" and b.fdate<='"&CDate(request("to_date"))+1&"'"
	end if
	if request("from_date")="" and request("to_date")="" then
		sqlt=sqlt&" and b.fdate>='"&date&"'"
	end if
	
	
	
	sqlc="select count(0) from crm_Tostar_vap as b where b.personid="&rs1("id")&sqlt&" and b.com_id in (select com_id from crm_assignVap where personid="&rs1("id")&") and star=4"
	
	set rsc=conn.execute(sqlc)
	if not rsc.eof or not rsc.bof then
		cout4=rsc(0)
	end if
	rsc.close
	set rsc=nothing
	parttongji_4=parttongji_4+cint(cout4)
	
	
	
	if cout4>0 then
		response.Write("<a href='admin_tj_comp.asp?lo=1&personid="&rs1("id")&"&vapstar=4&from_date="&request("from_date")&"&to_date="&request("to_date")&"&sfrom_date="&request("sfrom_date")&"&sto_date="&request("sto_date")&"&dotype="&dotype&"' target=_blank><font color=#ff0000>"&cout4&"</font></a>")
	else
		response.Write(0)
	end if
	'---------------------------
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
    
    
  </tr>
  <%
  rs.movenext
  wend
  end if
  rs.close
  set rs=nothing
  %>
  
</table>
</body>
</html>
<%
conn.close
set conn=nothing
%>
