<!-- #include file="../../include/adfsfs!@#.asp" -->
<%
dodayweek=weekday(date())
'response.Write(dodayweek)
if request.Form("fenpei")<>"" then
 '****************8
 sqlw="select * from crm_zhibanflag where id=1"
 set rsw=conn.execute(sqlw)
 if not rsw.eof then
 zhibanflag=rsw("zhibanflag")
 end if
 rsw.close
 set rsw=nothing
 if cstr(zhibanflag)="0" then
 dodayweek=0
 end if
		select case dodayweek
		case 1
		sqluser="select id from users where userid=13 and assignzhiban1=1 order by id desc"
		case 7
		sqluser="select id from users where userid=13 and assignzhiban=1 order by id desc"
		case else
		sqluser="select id from users where userid=13 and assignpub=1 order by id desc"
		end select
 otherzhiban=0
 sqlo="select * from crm_other_zhiban where fdate='"&date()&"'"
 set rso=conn.execute(sqlo)
 if not rso.bof or not rso.eof then
 sqluser="select personid from crm_other_zhiban where fdate='"&date()&"' order by personid desc"
 otherzhiban=1
 end if
 rso.close
 set rso=nothing
 'response.Write(sqluser)
 set rsuser=server.CreateObject("adodb.recordset")
 rsuser.open sqluser,conn,1,2
 rsucout=rsuser.recordcount
 'response.Write(rsucout)
 'redim arrayuser(cint(rsucout))

 		sqla=" and not EXISTS(select com_id from comp_sales where com_type='13' and com_id=v_salescomp.com_id) and Cushion<>-1 and not EXISTS(select com_id from Comp_ZSTinfo where com_id=v_salescomp.com_id) and not EXISTS(select com_id from comp_sales where com_Especial='1' and com_id=v_salescomp.com_id) and not EXISTS(select com_id from c1 where com_id>44025 and com_id<44233 and com_id=v_salescomp.com_id)"
		sqlas=" and EXISTS(select com_id from Crm_Assign where com_id=v_salescomp.com_id and fdate<'"&date()-2&"') and not EXISTS(select com_id from v_groupcomid where com_id=v_salescomp.com_id) "&sqla&" "

 i=0
 userarr=""
	 if not rsuser.eof then
		 do while not rsuser.eof
		 'sqlcom="select com_id from v_salescomp where personid="&rsuser("id")&" and not EXISTS (select com_id from Agent_ClientCompany where com_id=v_salescomp.com_id) "&sqlas
		 'set rscom=conn.execute(sqlcom)
		 'if rscom.eof then
		 'response.Write("a")
		 'end if
		 userarr=userarr&rsuser(0)&","
		 'arrayuser(i)=rsuser("id")
		 rsuser.movenext
		 i=i+1
		 loop
	 end if
	 'response.Write(userarr&"<br>")
	 arrayuser=split(left(userarr,len(userarr)-1),",",-1,1)
	 redim arraycomp(ubound(arrayuser))
	 'response.Write(arrayuser(0))
 rsuser.close
 set rsuser=nothing
 '****************申请再生通分配/start
	sqlc="select com_id from comp_info where viprequest=2 and com_id not in (select com_id from crm_assign) and vip_date>'"&date()&"' and vip_date<'"&date()+1&"' and com_id not in (select com_id from Crm_PublicComp)"
	sqlc=sqlc&" and not EXISTS(select com_id from crm_InsertCompWeb where com_id=comp_info.com_id )"
	sqlc=sqlc&" and not EXISTS(select com_id from crm_webPipeiComp where com_id=comp_info.com_id )"
	set rsc=server.CreateObject("adodb.recordset")
	rsc.open sqlc,conn,1,2
	if not rsc.eof then
	do while not rsc.eof 
	    select case dodayweek
		  case 1
		    sqlu="select top 1 id from users where assignzhiban1=1 order by vipcount asc,id desc"
		  case 7
		    sqlu="select top 1 id from users where assignzhiban=1 order by vipcount asc,id desc"
		  case else
		    sqlu="select top 1 id from users where assignflag=1 order by vipcount asc,id desc"
		end select
		if otherzhiban=1 then
		sqlu="select top 1 personid from crm_other_zhiban where fdate='"&date()&"' order by personid desc"
		end if
		set rsu=conn.execute(sqlu)
		if not rsu.eof then
		  
		  sqls="select com_id from crm_assign where com_id="&rsc("com_id")
		  set rss=conn.execute(sqls)
		  if rss.eof then
			 sqlin="insert into crm_assign (personid,Cushion,com_id) values("&rsu(0)&",0,"&rsc("com_id")&")"
			 conn.execute(sqlin)
			 sqlup="update users set vipcount=vipcount+1 where id="&rsu(0)
			 conn.execute(sqlup)
		  end if
		  rss.close
		  set rss=nothing
		end if
		rsu.close
		set rsu=nothing
	rsc.movenext
	loop
	end if
	rsc.close
	set rsc=nothing
	'****************申请再生通分配/end
                   i=0
				   comlist=""
				   n=0
	               sql1=""
				   '(adminuser=0 or com_email in (select com_email from comp_login)) and
	               sql1=sql1&" and com_id not in (select com_id from test) and com_id not in (select com_id from comp_info where vipflag=2 and vip_check=1) and com_id not in (select com_id from crm_assign where Cushion<>-1) and com_id not in (select com_id from comp_sales where com_type='13' ) and  com_id not in (select com_id from comp_sales where com_Especial='1' )  and com_id not in (select com_id from Crm_PublicComp)"
                   sql1=sql1&" and not EXISTS(select com_id from crm_InsertCompWeb where com_id=comp_info.com_id )"
				   sql1=sql1&" and not EXISTS(select com_id from crm_webPipeiComp where com_id=comp_info.com_id )"
				   if dodayweek=1 or dodayweek=7 then
				   		sqlcom="select top "&cint(rsucout)*30&" com_id "
				   else
					   sqlcom="select com_id "
					   if otherzhiban=1 then
					   	  sqlcom="select top "&cint(rsucout)*30&" com_id "
					   end if
				   end if
				   sqlcom=sqlcom&"from comp_info where adminuser<>10000 "&sql1&" order by com_id asc"
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
				   response.Write(comlist)
				   arraycom=split(comlist,"|")
				   'response.Write(comlist)
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
					   else
					       lastlitarraycom=split(arraycom(ubound(arraycom)),",")
						   for m=0 to ubound(lastlitarraycom)-1
						            'response.Write(arrayuser(m))
									sqlc="select com_id,personid from crm_assign where com_id="&trim(lastlitarraycom(m))
									set rsc=conn.execute(sqlc)
									if rsc.eof then
										   sqlu="insert into crm_assign(com_id,personid,adminpersonid,Cushion) values("&trim(lastlitarraycom(m))&","&arrayuser(m)&","&arrayuser(m)&",0)"
										   'response.Write(sqlu)
										   'conn.execute sqlu,ly
										   if ly then
										   response.Write("分配给"&arrayuser(m)&"成功！—"&trim(lastlitarraycom(m))&"<br>")
										   end if
									else
										   sqlu="update crm_assign set Cushion=0,personid="&arrayuser(m)&",fdate=getdate() where com_id="&trim(lastlitarraycom(m))&""
										   'conn.execute sqlu,ly
										   if ly then
										   response.Write("分配给"&arrayuser(m)&"成功！—"&trim(lastlitarraycom(m))&"<br>")
										   end if
									end if
									rsc.close
									set rsc=nothing
						   next
					   end if
				   next
				   response.Write("分配完成！")
				   
'其它客户分配

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
	               sql1=sql1&" and com_id not in (select com_id from test) and com_id not in (select com_id from comp_info where vipflag=2 and vip_check=1) and com_id not in (select com_id from crm_assign where Cushion<>-1) and com_id not in (select com_id from comp_sales where com_type='13' ) and com_id not in (select com_id from comp_sales where com_Especial='1' )"
                   sql1=sql1&" and not EXISTS(select com_id from crm_InsertCompWeb where com_id=comp_info.com_id )"
				   sql1=sql1&" and not EXISTS(select com_id from crm_webPipeiComp where com_id=comp_info.com_id )"
				   sqlcom="select com_id "
				   sqlcom=sqlcom&"from comp_info where adminuser<>10000 "&sql1&" order by com_id asc"
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
				   response.Write(comlist)
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
				   response.Write("<script>window.location='crm_assign_pubsales1.asp'</script>")
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
