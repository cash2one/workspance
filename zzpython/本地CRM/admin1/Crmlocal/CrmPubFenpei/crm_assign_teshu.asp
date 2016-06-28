<!-- #include file="../../include/adfsfs!@#.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!-- #include file="../../../cn/function.asp" -->
<!--#include file="../../include/include.asp"-->
<%
fromdate=request("fromdate")
todate=request("todate")

if request("user")<>"" then
 '****************8
 arrayuser=split(request("user"),",")
 
 rsucout=ubound(arrayuser)+1
 redim arraycomp(cint(rsucout))
 sql="update users set assignflag=1 where id in ("&trim(request.Form("user"))&")"
		conn.execute(sql)
		sql="update users set assignflag=0 where id not in ("&trim(request.Form("user"))&")"
		conn.execute(sql)
		
 i=0

				   comlist=""
				   n=0
				   sqlcom=request("sql")
				   application("sqlcom")=sqlcom
				   set rscom=server.CreateObject("adodb.recordset")
				   rscom.open sqlcom,conn,1,1
				   if not rscom.eof then
					   do while not rscom.eof 
					   if n mod cint(rsucout)=0 then
						  comlist=comlist&"|"&rscom("com_id")&","
					   else
						  comlist=comlist&rscom("com_id")&","
					   end if
					   n=n+1
					   rscom.movenext
					   loop
				   end if
				   rscom.close
				   set rscom=nothing
				   arraycom=split(comlist,"|")
				   
				   for i=1 to ubound(arraycom)
				       litarraycom=split(arraycom(i),",")
					   if i<>ubound(arraycom) then
                              for j=0 to ubound(arrayuser)
								sqlc="select com_id,personid from crm_assign where com_id="&trim(litarraycom(j))
								'response.Write(sqlc&"<br>")
								set rsc=conn.execute(sqlc)
								if rsc.eof or rsc.bof then
								       sqlu="insert into crm_assign(com_id,personid) values("&trim(litarraycom(j))&","&trim(arrayuser(j))&")"
									   conn.execute(sqlu)
								else
									   sqlu="update crm_assign set personid="&trim(arrayuser(j))&",fdate=getdate() where com_id="&trim(litarraycom(j))&""
									   conn.execute(sqlu)
								end if
								'------------写入客户分配记录
								com_id=trim(litarraycom(j))
								sDetail="莱州客户重新分配"
								sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&trim(arrayuser(j))&",'"&sDetail&"')"
								conn.execute(sqlp)
								
								rsc.close
								set rsc=nothing
							 next
					   end if
				   next
end if
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>客户分配</title>
<SCRIPT language=JavaScript src="../../main.js"></SCRIPT>
<SCRIPT language=javascript src="../../DatePicker.js"></SCRIPT>
<link href="../../datepicker.css" rel="stylesheet" type="text/css">
<link href="../../main.css" rel="stylesheet" type="text/css">
<link href="../../color.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
	margin-left: 5px;
	margin-top: 0px;
	margin-right: 5px;
	margin-bottom: 5px;
}
-->
</style>
</head>

<body>

<br>
<br>
<table width="500"  border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#000000">
  <tr>
    <td bgcolor="#FFFFFF"><%
if request("suc")="1" then
response.Write("分配成功！")
end if
%>

</td>
  </tr>
    <form name="form1" method="post" action="crm_assign_teshu.asp">

  <tr>
    <td bgcolor="#F2F2F2"><textarea name="sql" id="sql" cols="45" rows="5"><%=application("sqlcom")%></textarea></td>
  </tr>
  <tr>
    <td bgcolor="#F2F2F2">选择你要分配给那些销售人员</td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">
	<%
	sql="select id,realname,assignflag from users where userid like '13%' and closeflag=1"
    set rs=server.CreateObject("adodb.recordset")
	rs.open sql,conn,1,1
	if not rs.eof then
	do while not rs.eof
	%>
	<input name="user" type="checkbox" <%if rs("assignflag")="1" then response.Write("checked")%> id="user" value="<%response.Write(rs("id"))%>"><%response.Write(rs("realname"))%>
	<%
	rs.movenext
	loop
	end if
	rs.close
	set rs=nothing
	%>    	</td>
  </tr>

  <tr>
    <td align="center" bgcolor="#F2F2F2">
	<script>
	function fenp(frm)
	{
		if (confirm("确实开始分配吗？"))
		{
			frm.submit()
		}
	}
	</script>
      <input type="button" class=button name="Submit" value="开始分配" onClick="fenp(this.form)">
    </td>
  </tr></form>
</table>
</body>
</html>
<%
conn.close
set conn=nothing
%>
