<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
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
	  if session("userid")<>"" and session("userid")<>"10" and session("userid")<>"13" and left(session("userid"),2)="13" then
		code=session("userid")
	  end if
  end if
 sqluser="select realname,ywadminid from users where id="&session("personid")
 set rsuser=conn.execute(sqluser)
 userName=rsuser(0)
 ywadminid=rsuser(1)
 rsuser.close
 set rsuser=nothing
 
 function getvap4star(personid,fromdate,todate)
 	sqls="select count(0) from v_salescomp where exists(select com_id from crm_Tostar_vap where com_id=v_salescomp.com_id and personid="&personid&" and star=4 and fdate>='"&fromdate&"' and fdate<'"&cdate(todate)+1&"') and vpersonid="&personid&""
	set rss=conn.execute(sqls)
	getvap4star=rss(0)
	rss.close
	set rss=nothing
 end function
 ywadmin=""
 if ywadminid="" or isnull(ywadminid) or ywadminid="0" then 
 	ywadminid=session("userid")
 else
    'ywadminid=session("userid")
 	ywadmin=session("userid")
 end if

 
 sqla=""
 if session("userid")="10" then
 	sqla=" where code like '13__'"
 else
 	if left(session("userid"),2)="13" then
		sqla=" where code in ("&ywadminid&")"
	else
		sqla=" where code like '13__'"
	end if
 end if
		sql2="select code,meno from cate_adminuser "&sqla&" and closeflag=1 order by id desc" 
		  set rsClass=conn.execute(sql2)
		  if not (rsClass.eof and rsClass.bof) then
		  %>
          <table width="800" border="0" align="center" cellpadding="0" cellspacing="0">
            <form id="form1" name="form1" method="get" action=""><tr>
              <td height="30">时间<script language=javascript>createDatePicker("fromdate",false,"<%=fromdate%>",false,true,true,true)</script>
                
                  <input type="submit" name="button" id="button" value="搜索" />
              </td>
            </tr></form>
          </table>
          <table width="800" border="0" align="center" cellpadding="4" cellspacing="1" bgcolor="#7386A5">
            <tr>
            <td align="center" <% If code="" Then response.write "bgcolor='#cccccc'" else response.write "bgcolor='#FFFFFF'" end if %>><a href="?">全部</a></td>
            <% while not rsClass.eof 
				%>
              <td align="center" <% If code=rsClass("code") Then response.write "bgcolor='#cccccc'" else response.write "bgcolor='#FFFFFF'" end if %>><a href="?userClassId=<% =rsClass("code") %><%=sear%>"><%= rsClass("meno") %></a></td>
              <% 
			  	
			 rsClass.movenext
			 wend
			 %>
             <!--<td align="center" <% If code="24" Then response.write "bgcolor='#cccccc'" else response.write "bgcolor='#FFFFFF'" end if %>><a href="?userClassId=24<%=sear%>">CS-核心客服</a></td>-->
            </tr>
</table>
		  <br>
<% End If 
		  rsClass.close
		  set rsClass=nothing%>
          <%
		  ord=request.QueryString("ord")
		  col=request.QueryString("col")
		  if ord="1" then  Nord=0 else Nord=1
		  %>
          <table width="800" border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#000000">
  
  <tr>
    <td align="center" bgcolor="#FFFFFF">销售</td>
    <td align="center" bgcolor="#FFFFFF">部门</td>
    <td align="center" bgcolor="#FFFFFF"><a href="?ord=<%=Nord%>&amp;col=7&userClassId=<%=code%><%=sear%>">电话</a></td>
    <td align="center" bgcolor="#FFFFFF"><a href="?ord=<%=Nord%>&amp;col=6&userClassId=<%=code%><%=sear%>">月累计到单</a><%if col=6 then%><b style="color:#F00"><%if ord="1" then%>↓<%else%>↑<%end if%></b><%end if%></td>
    <td align="center" bgcolor="#FFFFFF"><a href="?ord=<%=Nord%>&amp;col=1&userClassId=<%=code%><%=sear%>">日到单量</a><%if col=1 then%><b style="color:#F00"><%if ord="1" then%>↓<%else%>↑<%end if%></b><%end if%></td>
    <td align="center" bgcolor="#FFFFFF">VAP开发量</td>
    <!--<td align="center" bgcolor="#FFFFFF"><a href="?ord=<%=Nord%>&amp;col=2&userClassId=<%=code%><%=sear%>">开发量</a><%if col=2 then%><b style="color:#F00"><%if ord="1" then%>↓<%else%>↑<%end if%></b><%end if%></td>-->
    <td align="center" bgcolor="#FFFFFF">ICD开发量</td>
    <td align="center" bgcolor="#FFFFFF"><a href="?ord=<%=Nord%>&amp;col=3&userClassId=<%=code%><%=sear%>">联系量</a><%if col=3 then%><b style="color:#F00"><%if ord="1" then%>↓<%else%>↑<%end if%></b><%end if%></td>
    <td align="center" bgcolor="#FFFFFF"><a href="?ord=<%=Nord%>&amp;col=4&userClassId=<%=code%><%=sear%>">在线时间</a><%if col=4 then%><b style="color:#F00"><%if ord="1" then%>↓<%else%>↑<%end if%></b><%end if%></td>
    <td align="center" bgcolor="#FFFFFF"><a href="?ord=<%=Nord%>&amp;col=5&userClassId=<%=code%><%=sear%>">呼叫量</a><%if col=5 then%><b style="color:#F00"><%if ord="1" then%>↓<%else%>↑<%end if%></b><%end if%></td>
    <td align="center" bgcolor="#FFFFFF">来源</td>
  </tr>
  <%
  sqlb=""
  
  if code<>"" then
  	sqlb=sqlb&" and userid='"&code&"'"
  end if
  if ywadmin<>"" then
  	if session("userid")="10" then
  	else
		sqlb=sqlb&" and userid in ("&ywadminid&")"
	end if
  else
  	sqlb=sqlb&" and personid="&session("personid")&""
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
	case "6"
		sqlb=sqlb&" order by usertel "&ordstr&""
	end select
  end if
  
  sql1="select * from v_salesCompTongji where userid like '13__' and closeflag=1 and tdate>='"&fromdate&"' and tdate<='"&todate&"'"&sqlb
  'response.Write(sql1)
  set rs1=conn.execute(sql1)
  if not rs1.eof or not rs1.bof then
  while not rs1.eof 
  %>
  <tr>
    <td bgcolor="#FFFFFF"><%'=rs1("tdate")%><%=rs1("realname")%></td>
    <td align="center" bgcolor="#FFFFFF"><%=rs1("meno")%></td>
    <td align="center" bgcolor="#FFFFFF"><%=rs1("usertel")%></td>
    <td align="center" bgcolor="#FFFFFF"><%=rs1("MonthCompCount")%></td>
    <td align="center" bgcolor="#FFFFFF"><%=rs1("vipCompCount")%></td>
    <td align="center" bgcolor="#FFFFFF"><a href="/admin1/crmlocal/admin/admin_tj_comp.asp?lo=1&personid=<%=rs1("personid")%>&star=&4star=1&userid=<%=rs1("userid")%><%=sear%>" target="_blank"><%response.Write(getvap4star(rs1("personid"),fromdate,todate))%></a></td>
    <td align="center" bgcolor="#FFFFFF"><a href="/admin1/crmlocal/admin/admin_tj_comp.asp?lo=1&personid=<%=rs1("personid")%>&star=&4star=1&userid=<%=rs1("userid")%><%=sear%>" target="_blank">
	<%
	response.Write(rs1("developCount"))
	%></a></td>
    <td align="center" bgcolor="#FFFFFF"><a href="contactCountList.asp?personid=<%=rs1("personid")%><%=sear%>" target="_blank"><%=rs1("ContactCount")%></a></td>
    <td align="center" bgcolor="#FFFFFF">
	<a href="onlinelist.asp?localtel=<%=rs1("usertel")%>&personid=<%=rs1("personid")%><%=sear%>" target="_blank"><%
	a=rs1("CallonlineCount")
	'response.Write(a)
	cc=""
	if not isnull(a) then
		cc=DateAdd("s", a, 0)
		response.Write(cc)
	end if
	%></a></td>
    <td align="center" bgcolor="#FFFFFF"><%=rs1("CallCount")%></td>
    <td align="center" bgcolor="#FFFFFF">&nbsp;</td>
  </tr>
  <%
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
