<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- #include file="../../include/adfsfs!@#.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!--#include file="../../include/pagefunction.asp"-->

<%
 sqluser="select realname,ywadminid,xuqianFlag from users where id="&session("personid")
 set rsuser=conn.execute(sqluser)
 userName=rsuser(0)
 ywadminid=rsuser(1)
 xuqianFlag=rsuser(2)
 rsuser.close
 set rsuser=nothing
%>
<%sear="n="%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>客户操作日志</title>
<style type="text/css">
<!--
td {
	font-size: 12px;
}
-->
</style>
<script>
function selectOption(menuname,value)
{
    var menu = document.getElementById(menuname);
	if (menu)
	{
	for(var i=0;i<=menu.options.length;i++){
		if(menu.options[i].value==value)
		{
			menu.options[i].selected = true;
			break;
		}
	}
	}
}
</script>
</head>

<body>
<table width="100%" border="0" cellpadding="4" cellspacing="1" bgcolor="#999999">
 <form id="form1" name="form1" method="post" action="history.asp"> <tr>
    <td colspan="6" align="center" bgcolor="f2f2f2"><select name="doperson" class="button" id="doperson" >
      <option value="" >操作者</option>
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
      <script>selectOption("mypersonid","<%=request("mypersonid")%>")</script>
       <%if session("userid")="10" then%>
       <input name="sDetail" type="text" id="sDetail" size="20" value="<%=request("sDetail")%>" />
       开始时间<input name="fdate" type="text" id="fdate" size="10" value="<%=request("fdate")%>" />
       结束时间<input name="tdate" type="text" id="tdate" size="10" value="<%=request("tdate")%>" />
       <%end if%>
       <select name="insh" id="insh">
       	 <option value=""></option>
         <option value="1">私海</option>
         <option value="2">公海</option>
       </select>
      <script>selectOption("insh","<%=request("insh")%>")</script>
      <input type="submit" name="button" id="button" value="查询" />
      <input type="hidden" name="mpersonid" id="mpersonid" value="<%=mpersonid%>"/>
      <input type="hidden" name="dotype" id="dotype" value="<%=dotype%>"/>
      </td>
 </tr></form>
  
  <tr>
    <td colspan="6" bgcolor="f2f2f2">
    <%

   sql=""
   if request("com_email")<>"" then
   	  sql=sql&" and exists (select com_id from comp_info where com_email like '%"&request("com_email")&"%' and com_id=crm_assignHistory.com_id)"
	  sear=sear&"&com_email="&request("com_email")
   end if
   if request("cid")<>"" then
   	  sql=sql&" and cid="&request("cid")&""
	  sear=sear&"&cid="&request("cid")
   end if
   '------我的操作日志
   if mpersonid<>"" then
   	  sql=sql&" and (personid="&mpersonid&")"
	  sear=sear&"&mpersonid="&request("mpersonid")
   end if
   if request("doperson")<>"" then
   	  sql=sql&" and personid="&request("doperson")&""
	  sear=sear&"&doperson="&request("doperson")
   end if

   if request("sDetail")<>"" then
   	  sql=sql&" and sDetail='"&request("sDetail")&"'"
	  sear=sear&"&sDetail="&request("sDetail")
   end if
   if request("fdate")<>"" then
   	  sql=sql&" and fdate>'"&request("fdate")&"'"
	  sear=sear&"&fdate="&request("fdate")
   end if
   if request("tdate")<>"" then
   	  sql=sql&" and fdate<'"&cdate(request("tdate"))+1&"' "
	  sear=sear&"&tdate="&request("tdate")
   end if
   if request("insh")="1" then
   	  sql=sql&" and exists(select cid from ybp_assign where cid=ybp_dohistory.cid) "
	  sql=sql&" and cidot in(select com_id from ybp_dohistory where sDetail='kangxianyue强制恢复掉公海的客户' and fdate<'2010-3-5' and fdate>'2010-3-4') and cid not in (138306,264386,455931,413592,166400,438622)"
	  sear=sear&"&insh="&request("insh")
   end if
   if request("insh")="2" then
   	  sql=sql&" and not exists(select cid from ybp_assign where cid=ybp_dohistory.cid)"
	  sear=sear&"&insh="&request("insh")
   end if
   Set oPage=New clsPageRs2
   With oPage
	 .sltFld  = "*"
	 .FROMTbl = "ybp_dohistory"
	 if request("insh")<>"" then
	 	.sqlOrder= "order by personid desc"
	 else
		.sqlOrder= "order by fdate desc"
	 end if
	 .sqlWhere= "WHERE id > 0 "&sql
	 .keyFld  = "id"    '不可缺少
	 .pageSize= 10
	 .getConn = conn
	 Set Rs  = .pageRs
   End With
   total=oPage.getTotalPage
   oPage.pageNav "?"&sear,""
   totalpg=total/10
   if total/10 > totalpg then
	  totalpg=totalpg+1
   end if
  %>    </td>
  </tr>

  <tr>
    <td bgcolor="f2f2f2">操作者</td>
    <td bgcolor="f2f2f2">现所有者</td>
    <td bgcolor="f2f2f2">被操作店铺</td>
    <td bgcolor="f2f2f2">操作时间</td>
    <td bgcolor="f2f2f2">日志</td>
  </tr>
  <%
  if not rs.eof or not rs.bof then
  while not rs.eof 
  %>
  <tr>
    <td bgcolor="#FFFFFF">
      <%sqluser="select realname from users where id="&rs("personid")
	set rsu=conn.execute(sqluser)
	if not (rsu.eof or rsu.bof) then
		response.Write(rsu(0))
	end if
	rsu.close
	set rsu=nothing
	%>    </td>
    <td bgcolor="#FFFFFF">
      <%
	sqluser="select a.realname from users as a,ybp_assign as b where a.id=b.personid and b.cid="&rs("cid")
	set rsu=conn.execute(sqluser)
	if not (rsu.eof or rsu.bof) then
		response.Write(rsu(0))
	end if
	rsu.close
	set rsu=nothing
	%>
    </td>
    
    <td bgcolor="#FFFFFF">
      
      <a href="/admin1/crmlocal/ybp/companyshow.asp?cid=<%=rs("cid")%>" target="_blank">
    <%sqluser="select shop_name from ybp_company where id="&rs("cid")&""
	set rsu=conn.execute(sqluser)
	if not (rsu.eof or rsu.bof) then
		response.Write(rsu(0))
	end if
	rsu.close
	set rsu=nothing
	%></a></td>
    <td bgcolor="#FFFFFF"><%=rs("fdate")%></td>
    <td bgcolor="#FFFFFF"><%=rs("sDetail")%></td>
  </tr>
  <%
  rs.movenext
  wend
  rs.close
  set rs=nothing
  end if
  %>
</table>

<br />

<br />
<br />

</body>
</html>

<%
conn.close
set conn=nothing
%>