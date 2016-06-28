<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!-- #include file="../../../cn/function.asp" -->
<!-- #include file="../../include/include.asp" -->
<!-- #include file="inc.asp" -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
<link href="../../css.css" rel="stylesheet" type="text/css">
<link href="../../inc/Style.css" rel="stylesheet" type="text/css">

<script language="javascript" src="../../include/calendar.js"></script>

</head>
<% 
if request("update")="1" then	
	weekID=request("wID")
	sql="select * from crm_weekTask where weekID="&weekID
	weekTask=request("weekTask")
	personID=request("personIDList")
	departID=request("departIDList")
	weekTaskArr=split(weekTask,",")
	personIDArr=split(personID,",")
	departIDArr=split(departID,",")
	for i=0 to ubound(weekTaskArr)
		sql0="select id from crm_weekTask where weekID="&weekID&" and personID="&personIDArr(i)&" and departID='"&trim(departIDArr(i))&"'"
		set rs0=conn.execute(sql0)
		if trim(weekTaskArr(i))="" or isnull(weekTaskArr(i)) then
			wTask=0
		else
			wTask=weekTaskArr(i)
		end if
		if rs0.eof and rs0.bof then
			conn.execute("insert into crm_weekTask(weekTask,weekID,personID,departID) values("&wTask&","&weekID&","&personIDArr(i)&",'"&trim(departIDArr(i))&"')")
		else
			conn.execute("update crm_weekTask set weekTask="&wTask&" where weekID="&weekID&" and personID="&personIDArr(i)&" and departID='"&trim(departIDArr(i))&"'")
		end if 
		rs0.close
		set rs0=nothing
	next
	response.write "<script>alert('修改成功！');location.href='addWeekTask.asp?wID="&weekID&"'</script>"
	response.end
end if

wID=request("wID")
sql="select * from crm_weeks where id="&wID
set rs=conn.execute(sql)
if not(rs.eof and rs.bof) then
	cYear=rs("cYear")
	cMonth=rs("cMonth")
	cWeek=rs("cWeek")
end if
rs.close
set rs=nothing
 %>
<body>
<form id="form1" name="form1" method="post" action="">
	<table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#999999">
		<caption>
			<span style="font-size:14px;">添加销售周目标 - <%= cYear %>年<%= cMonth %>月 第<%= cWeek %>周			</span>
		</caption>
		<tr>
			<td bgcolor="#D9E6FF">销售人员</td>
			<td bgcolor="#D9E6FF">周目标</td>
		</tr>
		<%
		if len(session("userid"))=4 and left(session("userid"),2)="13" then
			sqlc="select code,meno from cate_adminuser where code ="&session("userid")
		else
			sqlc="select code,meno from cate_adminuser where code like '13__'"
		end if
		  set rsc=conn.execute(sqlc)
		  if not rsc.eof then
		  while not rsc.eof
		  %>
		
		<tr>
			<td bgcolor="#CCCCCC"><%=(rsc("meno"))%></td>
			<td bgcolor="#CCCCCC">
			<% 
			sqlw="select sum(weekTask) from crm_weekTask where weekID="&wID&" and departID='"&rsc("code")&"'"
			set rsw=conn.execute(sqlw)
				response.write rsw(0)&"单"
			rsw.close
			set rsw=nothing
			 %>
			</td>
		</tr>
		<%
			sqlu="select realname,id from users where closeflag=1 and userid="&rsc("code")&""
			set rsu=server.CreateObject("ADODB.recordset")
			rsu.open sqlu,conn,1,1
			if not rsu.eof then
			do while not rsu.eof
				sqlo="select weekTask from crm_weekTask where personid="&rsu("id")&" and weekID="&wID&" and departID='"&rsc("code")&"'"
				set rso=conn.execute(sqlo)
				if not (rso.eof and rso.bof) then
					weekTask=rso(0)
				else
					weekTask=""
				end if
				rso.close
				set rso=nothing
		%>
		<tr>
			<td width="17%" bgcolor="#FFFFFF"><%=rsu("realname")%></td>
			<td width="83%" bgcolor="#FFFFFF"><input name="weekTask" type="text" id="weekTask<%= rsu("id") %>" size="5"  value="<%= weekTask %>"/>
单<input name="personIDList" type="hidden" id="personIDList<%= rsu("id") %>" size="5" value="<%= rsu("id") %>" />
<input name="departIDList" type="hidden" id="departIDList<%= rsc("code") %>" size="5" value="<%= rsc("code") %>" /></td>
		</tr>
		<%
			rsu.movenext
			loop
			end if
			rsu.close()
			set rsu=nothing
		rsc.movenext
		wend
		end if
		rsc.close
		set rsc=nothing
			 %>
		<tr>
			<td bgcolor="#FFFFFF">&nbsp;</td>
			<td bgcolor="#FFFFFF">&nbsp;</td>
		</tr>
		
		<tr>
			<td colspan="2" align="center" bgcolor="#F2F2F2"><input type="submit" name="button" id="button" value="提交" /><input name="wID" type="hidden" value="<%= wID %>" /><input name="update" type="hidden" value="1" /></td>
		</tr>
	</table>
</form>
</body>
</html>
<%
conn.close
set conn=nothing
%>