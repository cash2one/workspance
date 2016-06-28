<!-- #include file="../../include/adfsfs!@#.asp" -->
<%
fromdate=request("fromdate")
todate=request("todate")
if request("user")<>"" then
 '****************8
 sqluser="select id from users where userid=13 and assignpub=1 order by id desc"
 set rsuser=server.CreateObject("adodb.recordset")
 rsuser.open sqluser,conn,1,2
 rsucout=rsuser.recordcount
 redim arrayuser(cint(rsucout))
 redim arraycomp(cint(rsucout))
 i=0
	 if not rsuser.eof then
		 do while not rsuser.eof
		 arrayuser(i)=rsuser("id")
		 rsuser.movenext
		 i=i+1
		 loop
	 end if
 rsuser.close
 set rsuser=nothing
 
    i=0
	comlist=""
	n=0
	sql1=""
	sql1=sql1&" and  com_id not in (select com_id from comp_info where vipflag=2 and vip_check=1) and com_id not in (select com_id from crm_assign where Cushion<>-1) and com_id not in (select com_id from comp_sales where com_type='13' ) and (adminuser=0 or com_email in (select com_email from comp_login)) and com_id not in (select com_id from comp_sales where com_Especial='1' ) and com_regtime>'"&date()-1&"' and com_regtime<'"&date()&"'"

				   sqlcom=""
				   sqlcom="select com_id from comp_info where com_id not in (select com_id from test) "&sql1&" order by vipflag DESC, viptype DESC, adminuser DESC, com_id DESC"
				   'response.Write(sqlcom)
				   set rscom=server.CreateObject("adodb.recordset")
				   rscom.open sqlcom,conn,1,2
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
				   'response.Write(comlist)
				   arraycom=split(comlist,"|")
				   'response.Write(comlist)
				   for i=1 to ubound(arraycom)
				   litarraycom=split(arraycom(i),",")
					   if i<>ubound(arraycom) then
                              for j=0 to ubound(arrayuser)-1
								
								sqlc="select com_id,personid from crm_assign where com_id="&trim(litarraycom(j))
								set rsc=conn.execute(sqlc)
								if rsc.eof then
									   sqlu="insert into crm_assign(com_id,personid,adminpersonid,Cushion) values("&trim(litarraycom(j))&","&arrayuser(j)&","&arrayuser(j)&",0)"
									   conn.execute sqlu,ly
									   if ly then
									   response.Write("分配给"&arrayuser(j)&"成功！—"&trim(litarraycom(j))&"<br>")
									   end if
								else
								       sqlu="update crm_assign set Cushion=0,personid="&arrayuser(j)&",fdate=getdate() where com_id="&trim(litarraycom(j))&""
									   conn.execute sqlu,ly
									   if ly then
									   response.Write("分配给"&arrayuser(j)&"成功！—"&trim(litarraycom(j))&"<br>")
									   end if
								end if
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
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#000000">
<form name="form1" method="post" action="">
  <tr>
    <td bgcolor="#FFFFFF">
	<%
	sql="select id,realname,assignpub from users where userid=13 and closeflag=1"
    set rs=server.CreateObject("adodb.recordset")
	rs.open sql,conn,1,2
	if not rs.eof then
	do while not rs.eof
	%>
	<input name="user" type="checkbox" <%if rs("assignpub")="1" then response.Write("checked")%> id="user" value="<%response.Write(rs("id"))%>"><%response.Write(rs("realname"))%>
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
      <input type="button" class=button name="Submit" value="开始分配" onClick="fenp(this.form)">    </td>
  </tr></form>
</table>
</body>
</html>
