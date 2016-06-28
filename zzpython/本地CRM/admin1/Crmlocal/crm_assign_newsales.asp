<!-- #include file="../include/adfsfs!@#.asp" -->
<!-- #include file="../localjumptolog.asp" -->
<!-- #include file="../../cn/function.asp" -->
<!--#include file="../include/include.asp"-->
<%
fromdate=request("fromdate")
todate=request("todate")
if fromdate="" then
fromdate=date()-2
end if
if todate="" then
todate=date()
end if
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
    response.Redirect("crm_assign_newsales.asp")
	
end if
if request("doperson")<>"" then
	sql1=""
	sql1=sql1&" and com_id in (select com_id from crm_assign where personid='"&request.Form("doperson")&"')"
	sqlmain="select count(com_id) from v_salescomp where not EXISTS (select com_id from Agent_ClientCompany where com_id=v_salescomp.com_id) "&sql1
	'response.Write(sql1)
	set rsmain=conn.execute(sqlmain)
	compcount=rsmain(0)
	rsmain.close
	set rsmain=nothing
end if
if request("user")<>"" then
 '****************8
 		sql="update users set assignflag=1 where id in ("&trim(request.Form("user"))&")"
		conn.execute(sql)
		sql="update users set assignflag=0 where id not in ("&trim(request.Form("user"))&")"
		conn.execute(sql)
 sqluser="select id from users where userid=13 and assignflag=1 order by id desc"
 set rsuser=server.CreateObject("adodb.recordset")
 rsuser.open sqluser,conn,1,1
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
 'response.Write(arrayuser(0))
 'response.End()
 i=0

				   comlist=""
				   n=0
				   	sql1=""
	                sql1=sql1&" and com_id in (select com_id from crm_assign where personid='"&request.Form("doperson")&"')"

				   sqlcom=""
				   sqlcom="select com_id from v_salescomp where 1=1 "&sql1&" order by com_rank DESC,vipflag desc,com_id DESC"
				   'response.Write(sqlcom)
				   set rscom=server.CreateObject("adodb.recordset")
				   rscom.open sqlcom,conn,1,1
				   if not rscom.eof then
					   do while not rscom.eof 
					   if rsucout<>"0" then
						   if n mod cint(rsucout)=0 then
							  comlist=comlist&"|"&rscom("com_id")&","
						   else
							  comlist=comlist&rscom("com_id")&","
						   end if
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
				   response.Write(comlist)
				   'response.End()
				   for i=0 to ubound(arraycom)
				   litarraycom=split(arraycom(i),",")
					   if i<>ubound(arraycom) then
                              for j=0 to ubound(arrayuser)-1
								sqlcount="select count(id) from crm_assign where personid="&arrayuser(j)&" and Cushion=1"
								'response.Write(sqlcount)
								set rscount=conn.execute(sqlcount)
								if not rscount.eof then
									arraycomp(j)=rscount(0)
								else
									arraycomp(j)=0
								end if 
								sqlc="select com_id,personid from crm_assign where com_id="&trim(litarraycom(j))
								set rsc=conn.execute(sqlc)
								if rsc.eof then
								   'if cint(arraycomp(j))<=300 then
									  ' sqlu="insert into crm_assign(com_id,personid,adminpersonid,Cushion) values("&trim(litarraycom(j))&","&arrayuser(j)&","&arrayuser(j)&",0)"
									   'conn.execute(sqlu)
									   'arraycomp(j)=arraycomp(j)+1
								   'else
								       
								       sqlu="insert into crm_assign(com_id,personid,adminpersonid,Cushion) values("&trim(litarraycom(j))&","&arrayuser(j)&","&arrayuser(j)&",0)"
									   conn.execute(sqlu)
									  ' response.Write("失败！ID号为"&trim(litarraycom(j))&"放入缓冲客户区！")
								   'end if
								else
								   'if cint(arraycomp(j))<=300 then
									   sqlu="update crm_assign set Cushion=0,personid="&arrayuser(j)&",fdate=getdate() where com_id="&trim(litarraycom(j))&""
									   conn.execute(sqlu)
									   'if rsc("cushion")="0" then
									   	  'arraycomp(j)=arraycomp(j)+1
									   'end if
								   'else
								       'sqlu="update crm_assign set Cushion=0,personid="&arrayuser(j)&",fdate=getdate() where com_id="&trim(litarraycom(j))&""
									   'conn.execute(sqlu)
								   	   'response.Write("失败！ID号为"&trim(litarraycom(j))&"客户已经被其他人分配！")
								   'end if
								end if
								rsc.close
								set rsc=nothing
							 next
					   end if
				   next
				   
				   'response.Redirect("crm_assign_newsales.asp?suc=1")
end if
if request.Form("user")<>"" then
		sql="update users set assignflag=1 where id in ("&trim(request.Form("user"))&")"
		conn.execute(sql)
		sql="update users set assignflag=0 where id not in ("&trim(request.Form("user"))&")"
		conn.execute(sql)
end if
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>客户分配</title>
<SCRIPT language=JavaScript src="../main.js"></SCRIPT>
<SCRIPT language=javascript src="../DatePicker.js"></SCRIPT>
<link href="../datepicker.css" rel="stylesheet" type="text/css">
<link href="../main.css" rel="stylesheet" type="text/css">
<link href="../color.css" rel="stylesheet" type="text/css">
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
  <form name="form2" method="post" action="">
    <tr>
      <td align="center" bgcolor="#FFFFFF"><a href="CrmPubFenpei/crm_assign_teshu.asp">特殊客户分配</a></td>
    </tr>
    <tr>
      <td align="center" bgcolor="#FFFFFF">选择要分配掉的销售人员客户<br>
<select name="doperson" class="button" id="doperson" >
              <option value="" >请选择--</option>
			  <% If session("personid")="227" Then %>
			  	<option value="227" >┆&nbsp;&nbsp;┆&nbsp;&nbsp;┿汤建云</option>
			  <% ElseIf session("personid")="93" Then %>
			  	<option value="93" >┆&nbsp;&nbsp;┆&nbsp;&nbsp;┿靳冲</option>
			  <% End If %>
			  <%
			  if ywadminid<>"" and not isnull(ywadminid)  then
			      sqlc="select code,meno from cate_adminuser where code in("&ywadminid&")"
			  else
				  if session("userid")="10" then
				  	 sqlc="select code,meno from cate_adminuser where code like '13%'"
				  else
				  	 sqlc="select code,meno from cate_adminuser where code like '"&session("userid")&"%'"
				  end if
			  end if
			  set rsc=conn.execute(sqlc)
			  if not rsc.eof then
			  while not rsc.eof
			  %>
			  <option value="" <%=sle%>>┆&nbsp;&nbsp;┿<%response.Write(rsc("meno"))%></option>
                        <%
						sqlu="select realname,id from users where closeflag=1 and userid="&rsc("code")&""
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
              <option value="<%response.Write(rsu("id"))%>" <%=sle%>>┆&nbsp;&nbsp;┆&nbsp;&nbsp;┿<%response.Write(rsu("realname"))%></option>
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
</td>
    </tr>
    <tr>
    <td align="center" bgcolor="#FFFFFF">
	<script>
	function outogh(frm)
	{
	if (confirm("确实要放到公海里吗？"))
	{
	window.location="crm_assign_newsales.asp?out=1&id="+frm.doperson.value
	}
	}
	</script>
      <input type="submit" name="Submit" class=button value="查询客户数">
      <input type="button" name="Submit" class=button value="放入公海" onClick="outogh(this.form)">
    </td>
  </tr></form>
</table>
<br>
<table width="500"  border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#000000">
  <tr>
    <td bgcolor="#FFFFFF">共有客户数（<%response.Write(compcount)%>）    </td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF"><%
if request("suc")="1" then
response.Write("分配成功！")
end if
%>

</td>
  </tr>
    <form name="form1" method="post" action="crm_assign_newsales.asp">

  <tr>
    <td bgcolor="#F2F2F2">选择你要分配掉的销售人员客户</td>
  </tr>
  <tr>
    <td bgcolor="#F2F2F2"><select name="doperson" class="button" id="doperson" >
              <option value="" >请选择--</option>
			  <% If session("personid")="227" Then %>
			  	<option value="227" >┆&nbsp;&nbsp;┆&nbsp;&nbsp;┿汤建云</option>
			  <% ElseIf session("personid")="93" Then %>
			  	<option value="93" >┆&nbsp;&nbsp;┆&nbsp;&nbsp;┿靳冲</option>
			  <% End If %>
			  <%
			  if ywadminid<>"" and not isnull(ywadminid)  then
			      sqlc="select code,meno from cate_adminuser where code in("&ywadminid&")"
			  else
				  if session("userid")="10" then
				  	 sqlc="select code,meno from cate_adminuser where code like '13%'"
				  else
				  	 sqlc="select code,meno from cate_adminuser where code like '"&session("userid")&"%'"
				  end if
			  end if
			  set rsc=conn.execute(sqlc)
			  if not rsc.eof then
			  while not rsc.eof
			  %>
			  <option value="" <%=sle%>>┆&nbsp;&nbsp;┿<%response.Write(rsc("meno"))%></option>
                        <%
						sqlu="select realname,id from users where closeflag=1 and userid="&rsc("code")&""
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
              <option value="<%response.Write(rsu("id"))%>" <%=sle%>>┆&nbsp;&nbsp;┆&nbsp;&nbsp;┿<%response.Write(rsu("realname"))%></option>
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
            </select></td>
  </tr>
  <tr>
    <td bgcolor="#F2F2F2">选择你要分配给那些销售人员    </td>
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
