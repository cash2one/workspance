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
saleDate=request("saleDate")
if saleDate="" then 
	saleDate=date()
else
	saleDate=cdate(saleDate)
end if
sqld="select id from crm_weeks where from_date<='"&saleDate&"' and to_date>='"&saleDate&"'"
set rsd=conn.execute(sqld)
if not(rsd.eof and rsd.bof) then	
	weekID=rsd(0)
else
	weekID=0
end if 
rsd.close
set rsd=nothing

if request("update")="1" then	

	
	saleDate=cdate(request("saleDate"))
	saleNum=request("saleNum")
	personID=request("personIDList")
	departID=request("departIDList")
	
	saleNumArr=split(saleNum,",")
	personIDArr=split(personID,",")
	departIDArr=split(departID,",")
	for i=0 to ubound(saleNumArr)
		sql0="select id from crm_daySale where saleDate='"&saleDate&"' and personID="&personIDArr(i)&" and departID='"&trim(departIDArr(i))&"'"
		set rs0=conn.execute(sql0)
		if trim(saleNumArr(i))="" or isnull(saleNumArr(i)) then
			sNum=0
		else
			sNum=saleNumArr(i)
		end if
		if rs0.eof and rs0.bof then
			conn.execute("insert into crm_daySale(saleNum,weekID,personID,departID,saleDate) values("&sNum&","&weekID&","&personIDArr(i)&",'"&trim(departIDArr(i))&"','"&saleDate&"')")
		else
			conn.execute("update crm_daySale set saleNum="&sNum&" where weekID="&weekID&" and personID="&personIDArr(i)&" and departID='"&trim(departIDArr(i))&"' and saleDate='"&saleDate&"'")
		end if 
		rs0.close
		set rs0=nothing
		'更新周目标完成量
		sql0="select sum(saleNum) from crm_daySale where weekID="&weekID&" and personid="&personIDArr(i)
		set rs0=conn.execute(sql0)
		if isnull(rs0(0)) then	
			fNum=0
		else
			fNum=rs0(0)
		end if
			conn.execute("update crm_weekTask set finishedNum="&fNum&" where weekID="&weekID&" and personid="&personIDArr(i))
		rs0.close
		set rs0=nothing
		'更新完毕
	next
	
	response.write "<script>alert('修改成功！');location.href='addDayReport.asp?saleDate="&saleDate&"'</script>"
	response.end
	
end if


 %>
<body>
<form id="form2" name="form2" method="post" action="">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td align="center">选择要添加或修改的日期： <input name="saleDate" type="text" class="wenbenkuang" id="saleDate" value="<%=saleDate%>" size="15">
    <a onClick="event.cancelBubble=true;" href="javascript:showCalendar('dimg1',false,'saleDate')"><img id=dimg1 align=absmiddle width=34 height=21 src="../../newimages/button.gif" border=0> 
     <input type="submit" name="button2" id="button2" value="选择" />
    </a></td>
		</tr>
	</table>
</form>

<form id="form1" name="form1" method="post" action="">
	<table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#999999">
		<caption>
			<span style="font-size:14px;">添加当天销售额 - <%= saleDate %>			</span>
		</caption>
		<tr>
			<td bgcolor="#D9E6FF">销售人员</td>
			<td bgcolor="#D9E6FF">业绩
			  <% 
			sqlw="select sum(saleNum) from crm_daySale where weekID="&weekID&" and saleDate='"&saleDate&"'"
			set rsw=conn.execute(sqlw)
				response.write "<strong>"&rsw(0)&"元</strong>"
			rsw.close
			set rsw=nothing
			 %>
			</td>
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
			sqlw="select sum(saleNum) from crm_daySale where weekID="&weekID&" and departID='"&rsc("code")&"' and saleDate='"&saleDate&"'"
			set rsw=conn.execute(sqlw)
				response.write rsw(0)&"元"
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
				sqlo="select saleNum from crm_daySale where personid="&rsu("id")&" and weekID="&weekID&" and departID='"&rsc("code")&"' and saleDate='"&saleDate&"'"
				set rso=conn.execute(sqlo)
				if not (rso.eof and rso.bof) then
					saleNum=rso(0)
				else
					saleNum=""
				end if
				rso.close
				set rso=nothing
		%>
		<tr>
			<td width="17%" bgcolor="#FFFFFF"><%=rsu("realname")%></td>
			<td width="83%" bgcolor="#FFFFFF"><input name="saleNum" type="text" id="saleNum<%= rsu("id") %>" size="10"  value="<%= saleNum %>"/> 
			元
              <input name="personIDList" type="hidden" id="personIDList<%= rsu("id") %>" size="5" value="<%= rsu("id") %>" />
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
			<td colspan="2" align="center" bgcolor="#F2F2F2"><input type="submit" name="button" id="button" value="提交" /><input name="wID" type="hidden" value="<%= wID %>" /><input name="update" type="hidden" value="1" /><input name="saleDate" type="hidden" value="<%= saleDate %>" /></td>
		</tr>
	</table>
</form>
</body>
</html>
<%
conn.close
set conn=nothing
%>