<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!-- #include file="../../../cn/function.asp" -->
<!-- #include file="../../include/include.asp" -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>�ޱ����ĵ�</title>
<link href="../../css.css" rel="stylesheet" type="text/css">
<link href="../../inc/Style.css" rel="stylesheet" type="text/css">

<script language="javascript" src="../../include/calendar.js"></script>
</head>
<% 
'ִ����ӻ��޸�
aa=request("action")
if aa<>"" then
	cYear=request("cYear")
	cMonth=request("cMonth")
	cWeek=request("cWeek")
	from_date=request("from_date")
	to_date=request("to_date")
	if aa="add" then
		sql="select * from crm_weeks where cYear="&cyear&" and cMonth="&cMonth&" and cWeek="&cWeek
		set rs0=server.createobject("adodb.recordset")
		rs0.open sql,conn,1,3
		if not(rs0.eof and rs0.bof) then
			response.Write("<script>alert('�����Ѿ���ӹ�');history.back();</script>")
			response.end
		else
			rs0.addnew
			rs0("cYear")=cYear
			rs0("cMonth")=cMonth
			rs0("cWeek")=cWeek
			rs0("from_date")=cdate(from_date)
			rs0("to_date")=cdate(to_date)
			rs0.update
		end if
		rs0.close
		set rs0=nothing
	elseif aa="edit" then
		sql="select * from crm_weeks where id="&request("id")
		set rs0=server.createobject("adodb.recordset")
		rs0.open sql,conn,1,3
		if not(rs0.eof and rs0.bof) then
			rs0("cYear")=cYear
			rs0("cMonth")=cMonth
			rs0("cWeek")=cWeek
			rs0("from_date")=cdate(from_date)
			rs0("to_date")=cdate(to_date)
			rs0.update
		end if
		rs0.close
		set rs0=nothing
		'�޸�crm_daysales��,��weekID����ʱ�䷶Χ��Ϊ��ȷ��ID
		conn.execute("update crm_daySale set weekID="&request("id")&" where saleDate>='"&cdate(from_date)&"' and saleDate<='"&cdate(to_date)&"'")
	end if
	response.write "<script>alert('�ύ�ɹ�!');location.href='weeksList.asp'</script>"
	response.end
end if

'�жϴ��ϸ�ҳ�����������ӻ����޸�
cmd=request("cmd")
if cmd="" then cmd="add"
if cmd="edit" then
	sql="select * from crm_weeks where id="&request("id")
	set rs=conn.execute(sql)
	if not(rs.eof and rs.bof) then
		cYear=rs("cYear")
		cMonth=rs("cMonth")
		cWeek=rs("cWeek")
		from_date=rs("from_date")
		to_date=rs("to_date")
	end if
elseif cmd="add" then
end if
if cYear="" then cYear=year(now)
if cMonth="" then cMonth=month(now)
if from_date="" then from_date=date()
if to_date="" then to_date=date()+7
 %>
<body>
<form id="form1" name="form1" method="post" action="">
	<table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#999999">
		<caption>
			���ʱ����
		</caption>
		<tr>
			<td bgcolor="#F2F2F2">���</td>
			<td bgcolor="#FFFFFF"><select name="cYear" id="cYear">
				<% for i=2008 to year(now)+5 %>
				<option value="<%= i %>" <% If i=cyear Then response.write "selected" %>><%= i %></option>
				<% next %>
			</select>
				��			</td>
		</tr>
		<tr>
			<td width="12%" bgcolor="#F2F2F2">�·�</td>
			<td width="88%" bgcolor="#FFFFFF"><select name="cMonth" id="cMonth">
			<% for i=1 to 12 %>
				<option value="<%= i %>" <% If i=cMonth Then response.write "selected" %>><%= i %></option>
				<% next %>
			</select>
				��			</td>
		</tr>
		<tr>
			<td bgcolor="#F2F2F2">����</td>
			<td bgcolor="#FFFFFF">��
				<input name="cWeek" type="text" id="cWeek" size="5" value="<%= cWeek %>" />
				��</td>
		</tr>
		<tr>
			<td bgcolor="#F2F2F2">ʱ���</td>
			<td bgcolor="#FFFFFF"> <input name="from_date" type="text" class="wenbenkuang" id="from_date" value="<%=from_date%>" size="15">
    <a onClick="event.cancelBubble=true;" href="javascript:showCalendar('dimg1',false,'from_date')"><img id=dimg1 align=absmiddle width=34 height=21 src="../../newimages/button.gif" border=0> </a>��
    <input name="to_date" type="text" class="wenbenkuang" id="to_date" value="<%=to_date%>" size="15">
    <a onClick="event.cancelBubble=true;" href="javascript:showCalendar('dimg2',false,'to_date')"><img id=dimg2 align=absmiddle width=34 height=21 src="../../newimages/button.gif" border=0></a> </td>
		</tr>
		<tr>
			<td bgcolor="#F2F2F2">&nbsp;</td>
			<td bgcolor="#FFFFFF"><input type="submit" name="button" id="button" value="�ύ" /><input type="hidden" name="action" value="<%= cmd %>" /></td>
		</tr>
	</table>
</form>
</body>
</html>
<%
conn.close
set conn=nothing
%>