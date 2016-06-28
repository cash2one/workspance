<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!-- #include file="../../../cn/function.asp" -->
<!--#include file="../../include/include.asp"-->
<%
fromdate=request("fromdate")
todate=request("todate")
if fromdate="" then
fromdate=date()-2
end if
if todate="" then
todate=date()
end if
'----------------放到公海
if request("out")="1" then
  sqlp="select com_id from crm_assign where personid="&request("id")&""
  set rsp=conn.execute(sqlp)
  while not rsp.eof
	sqlpp="select * from crm_publiccomp where com_id in ("& trim(rsp(0)) &")"
	set rspp=conn.execute(sqlpp)
	if rspp.eof then
	sqlppp="insert into crm_publiccomp(com_id) values("& trim(rsp(0)) &")"
	conn.execute(sqlppp)
	end if
	rspp.close
	set rspp=nothing
 rsp.movenext
 wend
 rsp.close
 set rsp=nothing
	sqldel="delete from crm_assign where personid="&request("id")
	conn.execute(sqldel)
	response.Redirect("admin_assign_out.asp")
end if
'----------------------------------------
'---------------------------------计算客户数
if request("doperson")<>"" then
	sql1=""
	sql1=sql1&" and com_id in (select com_id from crm_assign where personid="&request.Form("doperson")&")"
	if request("zst")<>"" then
		sql1=sql1&" and com_id in (select com_id from comp_zstinfo)"
	end if
	sqlmain="select count(com_id) from v_salescomp where not EXISTS (select com_id from Agent_ClientCompany where com_id=v_salescomp.com_id) "&sql1
	set rsmain=conn.execute(sqlmain)
	compcount=rsmain(0)
	rsmain.close
	set rsmain=nothing
end if
if request("user")<>"" then
 '****************8
 arrayuser=split(request.Form("user"),",")
 rsucout=ubound(arrayuser)+1
 redim arraycomp(cint(rsucout))
 i=0

comlist=""
n=0
sql1=""
sql1=sql1&" and com_id in (select com_id from crm_assign where personid="&request.Form("doperson")&")"
if request("zst")<>"" then
	sql1=sql1&" and com_id in (select com_id from comp_zstinfo)"
end if
sqlcom=""
sqlcom="select com_id from v_salescomp where not EXISTS (select com_id from Agent_ClientCompany where com_id=v_salescomp.com_id) "&sql1&" order by com_rank DESC,vipflag desc,com_id DESC"
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
	'response.Write(comlist)
    arraycom=split(comlist,"|")
	for i=1 to ubound(arraycom)
	   litarraycom=split(arraycom(i),",")
	   if i<>ubound(arraycom) then
		  for j=0 to ubound(arrayuser)
			sqlc="select com_id,personid from crm_assign where com_id="&trim(litarraycom(j))
			set rsc=conn.execute(sqlc)
			if rsc.eof or rsc.bof then
			   sqlu="insert into crm_assign(com_id,personid,adminpersonid) values("&trim(litarraycom(j))&","&trim(arrayuser(j))&","&trim(arrayuser(j))&")"
			   conn.execute(sqlu)
			else
			   sqlu="update crm_assign set personid="&trim(arrayuser(j))&",fdate=getdate() where com_id="&trim(litarraycom(j))&""
			   conn.execute(sqlu)
			end if
			rsc.close
			set rsc=nothing
		 next
	   else
		 if i=ubound(arraycom) then
			for j=0 to ubound(litarraycom)-1
				sqlc="select com_id,personid from crm_assign where com_id="&trim(litarraycom(j))
				set rsc=conn.execute(sqlc)
				if rsc.eof or rsc.bof then
				   sqlu="insert into crm_assign(com_id,personid,adminpersonid) values("&trim(litarraycom(j))&","&trim(arrayuser(j))&","&trim(arrayuser(j))&")"
				   conn.execute(sqlu)
				else
				   sqlu="update crm_assign set personid="&trim(arrayuser(j))&",fdate=getdate() where com_id="&trim(litarraycom(j))&""
				   conn.execute(sqlu)
				end if
				rsc.close
				set rsc=nothing
			next
		 end if
	   end if
	next
end if
sqluser="select ywadminid from users where id="&session("personid")
set rsuser=conn.execute(sqluser)
if not rsuser.eof or not rsuser.bof then
	ywadminid=rsuser(0)
else
	ywadminid="0"
end if
rsuser.close
set rsuser=nothing
lz=request("lz")
if lz="" then lz="1"
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
<table width="500"  border="0" align="center" cellpadding="5" cellspacing="1" bgcolor="#000000">
  <tr>
    <td align="center" <%if lz="3" then response.Write("bgcolor=#FFCCCC") else response.Write("bgcolor=#ffffff")%>><a href="?lz=3">全部</a></td>
    <td height="30" align="center" <%if lz="1" then response.Write("bgcolor=#FFCCCC") else response.Write("bgcolor=#ffffff")%>><a href="?lz=1">已离职</a></td>
    <td align="center" <%if lz="0" then response.Write("bgcolor=#FFCCCC") else response.Write("bgcolor=#ffffff")%>><a href="?lz=0">在职</a></td>
  </tr>
  <tr>
    <td height="30" colspan="3" bgcolor="#FFFFFF"><strong>客户分配管理</strong></td>
  </tr>
</table>
<br>
<table width="500"  border="0" align="center" cellpadding="5" cellspacing="1" bgcolor="#000000">
  <form name="form2" method="post" action="">
    <tr>
      <td bgcolor="#FFFFFF">放到公海</td>
    </tr>
    <tr>
    <td bgcolor="#FFFFFF">
	<script>
	function outogh(frm)
	{
		if (confirm("确实要放到公海里吗？"))
		{
			window.location="?out=1&id="+frm.doperson.value
		}
	}
	</script>
      <select name="doperson" class="button" id="doperson" >
        <option value="" >请选择--</option>
        <%
			  
			  sqlt=""
			  if lz="1" then
			  	sqlt=sqlt&" and closeflag=0"
			  end if
			  if lz="0" then
			  	sqlt=sqlt&" and closeflag=1"
			  end if
			  if ywadminid="" or isnull(ywadminid) then
			  	  if session("userid")="10" then
				  	 sqlc="select code,meno from cate_adminuser where code like '13%'"
				  else
				  	 sqlc="select code,meno from cate_adminuser where code like '"&session("userid")&"%'"
				  end if
			  else
				  sqlc="select code,meno from cate_adminuser where code in ("&ywadminid&")"
			  end if
			  set rsc=conn.execute(sqlc)
			  if not rsc.eof then
			  while not rsc.eof
			  %>
        <option value="" <%=sle%>>┆&nbsp;&nbsp;┿
          <%response.Write(rsc("meno"))%>
          </option>
        <%
						sqlu="select realname,id from users where userid="&rsc("code")&" "&sqlt
						set rsu=server.CreateObject("ADODB.recordset")
						rsu.open sqlu,conn,1,1
						if not rsu.eof then
						do while not rsu.eof
						if cstr(request("doperson"))=cstr(rsu("id")) then
							sle="selected"
						else
							sle=""
						end if
						%>
        <option value="<%response.Write(rsu("id"))%>" <%=sle%>>┆&nbsp;&nbsp;┆&nbsp;&nbsp;┿
          <%response.Write(rsu("realname"))%>
          </option>
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
      </select>
      <input name="zst" type="checkbox" id="zst" value="1" <%if request("zst")="1" then response.Write("checked")%>>
      再生通
      <input type="submit" name="Submit" class=button value="查询客户数">
      <input type="button" name="Submit" class=button value="放入公海" onClick="outogh(this.form)">
      <input type="hidden" name="lz" id="lz" value="<%=lz%>"></td>
  </tr></form>
</table>
<br>
<table width="500"  border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#000000">
  <tr>
    <td bgcolor="#FFFFFF">共有客户数（<%response.Write(compcount)%>）    </td>
  </tr>
</table>
<br>
<table  width="500"  border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#000000">
  
<form name="form1" method="post" action="admin_assign_out.asp">
  <tr>
    <td bgcolor="#F2F2F2">选择你要分配销售人员客户</td>
  </tr>
  <tr>
    <td bgcolor="#F2F2F2">
    <select name="doperson" class="button" id="doperson" >
        <option value="" >请选择--</option>
        <%
			  
			 
			  if ywadminid="" or isnull(ywadminid) then
			  	  if session("userid")="10" then
				  	 sqlc="select code,meno from cate_adminuser where code like '13%'"
				  else
				  	 sqlc="select code,meno from cate_adminuser where code like '"&session("userid")&"%'"
				  end if
			  else
				  sqlc="select code,meno from cate_adminuser where code in ("&ywadminid&")"
			  end if
			  set rsc=conn.execute(sqlc)
			  if not rsc.eof then
			  while not rsc.eof
			  %>
        <option value="" <%=sle%>>┆&nbsp;&nbsp;┿
          <%response.Write(rsc("meno"))%>
          </option>
        <%
						sqlu="select realname,id from users where userid="&rsc("code")&" "&sqlt
						set rsu=server.CreateObject("ADODB.recordset")
						rsu.open sqlu,conn,1,1
						if not rsu.eof then
						do while not rsu.eof
						if cstr(request("doperson"))=cstr(rsu("id")) then
							sle="selected"
						else
							sle=""
						end if
						%>
        <option value="<%response.Write(rsu("id"))%>" <%=sle%>>┆&nbsp;&nbsp;┆&nbsp;&nbsp;┿
          <%response.Write(rsu("realname"))%>
          </option>
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
      </select>
    <input name="zst" type="checkbox" id="zst" value="1" <%if request("zst")="1" then response.Write("checked")%>>
再生通</td>
  </tr>
  <tr>
    <td bgcolor="#F2F2F2">选择你要分配给那些销售人员    </td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">
    <%
	sql="select id,realname,assignflag from users where userid=13 and closeflag=1"
    set rs=server.CreateObject("adodb.recordset")
	rs.open sql,conn,1,1
	if not rs.eof then
	do while not rs.eof
	%>
	<input name="user" type="checkbox" id="user" value="<%response.Write(rs("id"))%>"><%response.Write(rs("realname"))%>
	<%
	rs.movenext
	loop
	end if
	rs.close
	set rs=nothing
	%><br>

	<%
	if ywadminid="" or isnull(ywadminid) then ywadminid=0
	sql="select id,realname,assignflag from users where userid in ("&ywadminid&") and closeflag=1"
	
    set rs=server.CreateObject("adodb.recordset")
	rs.open sql,conn,1,1
	if not rs.eof then
	do while not rs.eof
	%>
	<input name="user" type="checkbox" id="user" value="<%response.Write(rs("id"))%>"><%response.Write(rs("realname"))%>
	<%
	rs.movenext
	loop
	end if
	rs.close
	set rs=nothing
	%>
    </td>
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
