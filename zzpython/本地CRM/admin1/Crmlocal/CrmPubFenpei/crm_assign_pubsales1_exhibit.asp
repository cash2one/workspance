<!-- #include file="../../include/adfsfs!@#.asp" -->
<%
dodayweek=weekday(date())
if request.Form("fenpei")<>"" then
 '****************8
	 sqluser="select id from users where userid=13 and assignpub=1 order by id desc"
	 set rsuser=server.CreateObject("adodb.recordset")
	 rsuser.open sqluser,conn,1,2
	 rsucout=rsuser.recordcount
	 i=0
	 userarr=""
	 if not rsuser.eof then
		 do while not rsuser.eof
		 userarr=userarr&rsuser(0)&","
		 rsuser.movenext
		 i=i+1
		 loop
	 end if
	 arrayuser=split(left(userarr,len(userarr)-1),",",-1,1)
	 redim arraycomp(ubound(arrayuser))
     rsuser.close
     set rsuser=nothing

                   i=0
				   comlist=""
				   n=0
	               sql1=""
	               sql1=sql1&" and  com_id in (select com_id from comp_Exhibit)"
				   sql1=sql1&" and com_id not in (select com_id from crm_assign)"
				   sqlcom="select com_id "
				   sqlcom=sqlcom&"from comp_info where 1=1 "&sql1&" order by com_id asc"
				   response.Write(sqlcom)
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

				   arraycom=split(comlist,"|")
				   for i=1 to ubound(arraycom)
				   litarraycom=split(arraycom(i),",")
					   if i<>ubound(arraycom) then
                              for j=0 to ubound(arrayuser)
								
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
				   response.Write("分配完成！")
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
      <input name="fenpei" type="hidden" id="fenpei" value="1">
      <input type="button" class=button name="Submit" value="开始分配" onClick="fenp(this.form)">    </td>
  </tr></form>
</table>
</body>
</html>
