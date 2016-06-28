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
<style type="text/css">
	.bb,.bb1{
		border-bottom:1px solid #999;
		text-align:center;
		padding:2px 0;
	}
	.bb1{
		border-bottom:none;
	}
</style>
<%
start=timer()
cYear=request("cYear")
cMonth=request("cMonth")
code=request("code")
if len(session("userid"))=4 and left(session("userid"),2)="13" then
	code =session("userid")
else
	if code="" then code="1301"
end if
 %>
<body>
<table width="60%" border="0" cellpadding="4" cellspacing="1" bgcolor="#999999" align="center">
	<tr>
	<% 	
	if len(session("userid"))=4 and left(session("userid"),2)="13" then
			sqlc="select code,meno from cate_adminuser where code ="&session("userid")
		else
			sqlc="select code,meno from cate_adminuser where code like '13__'"
		end if
  set rsc=conn.execute(sqlc)
  if not rsc.eof then
	  while not rsc.eof
	  	if rsc("code")=code then
			tdColor="#cccccc"
		else
			tdColor="#ffffff"
		end if
	   %>
			<td bgColor="<%= tdColor %>" align="center"><a href="?code=<%= rsc("code") %>&cYear=<%= cYear %>&cMonth=<%= cMonth %>"><%= rsc("meno") %></a></td>
		<% 
		rsc.movenext
		wend
end if
	rsc.close
	set rsc=nothing
	 %>
	</tr>
</table><hr size="1"/>
<% if not(len(session("userid"))=4 and left(session("userid"),2)="13") then %>
<table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#999999">
	<caption>
		<%= cyear %>年<%= cMonth %>月销售报表  <br />
<input type="button"   name="Print"   value="打印本页"   onclick="window.print();">
	</caption>
	<tr>
		<td width="22%" bgcolor="#FFFFFF">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td bgcolor="#66CC66" class="bb">&nbsp;</td>
	</tr>
	<tr>
		<td class="bb">总目标</td>
	</tr>
	<tr>
		<td bgcolor="#D9E6FF" class="bb">总完成量</td>
	</tr>
	<tr>
		<td bgcolor="#FFFFCC" class="bb">达成率</td>
	</tr>
	<tr>
		<td bgcolor="#CCCC99" class="bb1">差额</td>
	</tr>
</table>

		</td>
		<% 
		'取得业务部整月的统计数据
	sql0="select sum(weekTask),sum(finishedNum) from crm_weekTask where weekID in( select id from crm_weeks where cyear="&cYear&" and cMonth="&cMonth&")"
	set rs0=conn.execute(sql0)
	if not(rs0.eof and rs0.bof) then
		allTask=rs0(0)
		allFinish=rs0(1)
	end if
	if allTask="" then allTask=0
	if allFinish="" then allFinish=0
	if allTask=0 then
		allPercent=0
	else
		allPercent=formatNumber(allFinish/allTask,4)*100
	end if
	rs0.close
	set rs0=nothing
 %>
		<td width="13%" bgcolor="#FFFFFF">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td bgcolor="#66CC66" class="bb"><%= cMonth %>月</td>
				</tr>
				<tr>
					<td class="bb"><%= allTask %>单</td>
				</tr>
				<tr>
					<td bgcolor="#D9E6FF" class="bb"><%= allFinish %>单</td>
				</tr>
				<tr>
					<td bgcolor="#FFFFCC" class="bb"><%= allPercent %>%</td>
				</tr>
				<tr>
					<td bgcolor="#CCCC99" class="bb1"><%= allFinish-allTask %>单</td>
				</tr>
			</table>
		</td>

		<% 
		'取得业务部整周的统计数据
		sql="select id,cweek from crm_weeks where cyear="&cYear&" and cMonth="&cMonth&" order by cweek"
		set rs=conn.execute(sql)
		while not rs.eof
			sql0="select sum(weekTask),sum(finishedNum) from crm_weekTask where weekID="&rs("id")
			set rs0=conn.execute(sql0)
			if not(rs0.eof and rs0.bof) then
				allTask=rs0(0)
				allFinish=rs0(1)
			end if
			if allTask="" or isnull(allTask) then allTask=0
			if allFinish="" or isnull(allFinish) then allFinish=0
			'response.write allTask
			if allTask=0 then
				allPercent=0
			else
				allPercent=formatNumber(allFinish/allTask,4)*100
			end if
			rs0.close
			set rs0=nothing
		 %>
		<td width="13%" bgcolor="#FFFFFF">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td bgcolor="#66CC66" class="bb">第<%= rs("cweek") %>周</td>
				</tr>
				<tr>
					<td class="bb"><%= allTask %>单</td>
				</tr>
				<tr>
					<td bgcolor="#D9E6FF" class="bb"><%= allFinish %>单</td>
				</tr>
				<tr>
					<td bgcolor="#FFFFCC" class="bb"><%= allPercent %>%</td>
				</tr>
				<tr>
					<td bgcolor="#CCCC99" class="bb1"><%= allFinish-allTask %>单</td>
				</tr>
			</table>
		</td>
		<% rs.movenext
		wend %>
	</tr>
</table>

<% End If %>
<br />
	<table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#999999">

		<tr>
			<td align="center" bgcolor="#66CC66" width="11%">销售人员</td>
			<td width="11%" bgcolor="#66CC66">&nbsp;</td>
			<td width="13%" bgcolor="#66CC66" align="center">&nbsp;<%= cMonth %>月</td>
		<% 
		
		task=""
		finish=""
		sql="select id,cweek from crm_weeks where cyear="&cYear&" and cMonth="&cMonth&" order by cweek"
		set rs=conn.execute(sql)
		while not rs.eof
			 %>
	    <td width="13%" bgcolor="#66CC66" align="center">&nbsp;第<%= rs("cweek") %>周</td>
			 <% 
			 rs.movenext
		 wend
		rs.close
		set rs=nothing%>
		</tr>
<%
'=============================部门统计
	sqlc="select code,meno from cate_adminuser where code ='"&code&"'"
  set rsc=conn.execute(sqlc)
  if not rsc.eof then
  while not rsc.eof
  %>
		
		<tr>
			<td bgcolor="#CCCCCC" align="center"><%=(rsc("meno"))%></td>
			<td bgcolor="#CCCCCC">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="bb">目标</td>
		</tr>
		<tr>
			<td bgcolor="#D9E6FF" class="bb">完成量</td>
		</tr>
		<tr>
			<td bgcolor="#FFFFCC" class="bb">达成率</td>
		</tr>
		<tr>
			<td bgcolor="#CCCC99" class="bb1">差额</td>
		</tr>
	</table>
	
			</td>
			<%'取得某个部门整月的统计数据
			sql0="select sum(weekTask),sum(finishedNum) from crm_weekTask where weekID in( select id from crm_weeks where cyear="&cYear&" and cMonth="&cMonth&") and departID='"&rsc("code")&"'"
			set rs0=conn.execute(sql0)
			if not(rs0.eof and rs0.bof) then
				allTask=rs0(0)
				allFinish=rs0(1)
			end if
			if allTask="" or isnull(allTask) then allTask=0
			if allFinish="" or isnull(allFinish) then allFinish=0
			if allTask=0 then
				allPercent=0
			else
				allPercent=formatNumber(allFinish/allTask,4)*100
			end if
			rs0.close
			set rs0=nothing
		 %>
		<td  bgcolor="#CCCCCC">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="bb"><%= allTask %>单</td>
				</tr>
				<tr>
					<td bgcolor="#D9E6FF" class="bb"><%= allFinish %></td>
				</tr>
				<tr>
					<td bgcolor="#FFFFCC" class="bb"><%= allPercent %>%</td>
				</tr>
				<tr>
					<td bgcolor="#CCCC99" class="bb1"><%= allFinish-allTask %>单</td>
				</tr>
			</table>
		</td>
		<% 
		'取得某个部门整周的统计数据
		sql="select id,cweek from crm_weeks where cyear="&cYear&" and cMonth="&cMonth&" order by cweek"
		set rsw=conn.execute(sql)
		while not rsw.eof
			sql0="select sum(weekTask),sum(finishedNum) from crm_weekTask where weekID ="&rsw("id")&" and departID='"&rsc("code")&"'"
			set rs0=conn.execute(sql0)
			if not(rs0.eof and rs0.bof) then
				allTask=rs0(0)
				allFinish=rs0(1)
			end if
			if allTask="" or isnull(allTask) then allTask=0
			if allFinish="" or isnull(allFinish) then allFinish=0
			if allTask=0 then
				allPercent=0
			else
				allPercent=formatNumber(allFinish/allTask,4)*100
			end if
			rs0.close
			set rs0=nothing

		 %>
		<td bgcolor="#CCCCCC">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="bb"><%= allTask %>单</td>
				</tr>
				<tr>
					<td bgcolor="#D9E6FF" class="bb"><%= allFinish %>单</td>
				</tr>
				<tr>
					<td bgcolor="#FFFFCC" class="bb"><%= allPercent %>%</td>
				</tr>
				<tr>
					<td bgcolor="#CCCC99" class="bb1"><%= allFinish-allTask %>单</td>
				</tr>
			</table>
		</td>
		<% rsw.movenext
		wend 
		rsw.close
		set rsw=nothing%>
		</tr>
		<%
		'人员统计=============================================
			sqlu="select realname,id from users where closeflag=1 and userid="&rsc("code")&""
			set rsu=server.CreateObject("ADODB.recordset")
			rsu.open sqlu,conn,1,1
			if not rsu.eof then
			cc=1
			do while not rsu.eof
			
			if cc mod 2 = 1 then
				trColor="#ffffff"
			else
				trColor="#efefef"
			end if
		%>
		<tr bgcolor="<%= trColor %>">
			<td width="171" align="center"><%=rsu("realname")%></td>
			<td >
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="bb">目标</td>
					</tr>
					<tr>
						<td bgcolor="#D9E6FF" class="bb">完成量</td>
					</tr>
					<tr>
						<td bgcolor="#FFFFCC" class="bb">达成率</td>
					</tr>
					<tr>
						<td bgcolor="#CCCC99" class="bb1">差额</td>
					</tr>
				</table>
			</td>
			<%'取得某人整月的统计数据
			sql0="select sum(weekTask),sum(finishedNum) from crm_weekTask where weekID in( select id from crm_weeks where cyear="&cYear&" and cMonth="&cMonth&")  and personid="&rsu("id")
			set rs0=conn.execute(sql0)
			if not(rs0.eof and rs0.bof) then
				allTask=rs0(0)
				allFinish=rs0(1)
			end if
			if allTask="" or isnull(allTask) then allTask=0
			if allFinish="" or isnull(allFinish) then allFinish=0
			if allTask=0 then
				allPercent=0
			else
				allPercent=formatNumber(allFinish/allTask,4)*100
			end if
			rs0.close
			set rs0=nothing
		 %>
		<td >
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="bb"><%= allTask %>单</td>
				</tr>
				<tr>
					<td bgcolor="#D9E6FF" class="bb"><%= allFinish %>单</td>
				</tr>
				<tr>
					<td bgcolor="#FFFFCC" class="bb"><%= allPercent %>%</td>
				</tr>
				<tr>
					<td bgcolor="#CCCC99" class="bb1"><%= allFinish-allTask %>单</td>
				</tr>
			</table>
		</td>
		<% 
		'取得某人整周的统计数据
		sql="select id,cweek from crm_weeks where cyear="&cYear&" and cMonth="&cMonth&" order by cweek"
		set rsw=conn.execute(sql)
		while not rsw.eof
			sql0="select sum(weekTask),sum(finishedNum) from crm_weekTask where weekID ="&rsw("id")&" and personid="&rsu("id")
			set rs0=conn.execute(sql0)
			if not(rs0.eof and rs0.bof) then
				allTask=rs0(0)
				allFinish=rs0(1)
			end if
			if allTask="" or isnull(allTask) then allTask=0
			if allFinish="" or isnull(allFinish) then allFinish=0
			if allTask=0 then
				allPercent=0
			else
				allPercent=formatNumber(allFinish/allTask,4)*100
			end if
			rs0.close
			set rs0=nothing

		 %>
		<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="bb"><%= allTask %>单</td>
				</tr>
				<tr>
					<td bgcolor="#D9E6FF" class="bb"><%= allFinish %>单</td>
				</tr>
				<tr>
					<td bgcolor="#FFFFCC" class="bb"><%= allPercent %>%</td>
				</tr>
				<tr>
					<td bgcolor="#CCCC99" class="bb1"><%= allFinish-allTask %>单</td>
				</tr>
			</table>
		</td>
		<% rsw.movenext
		wend 
		rsw.close
		set rsw=nothing%>
		</tr>
<%
	rsu.movenext
	cc=cc+1
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
	</table>
                            
  <input type="button"   name="Print"   value="打印本页"   onclick="window.print();">
</body>
</html>
<%

conn.close
set conn=nothing
endt=timer
response.write (endt-start)*1000&"ms"
%>