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
 mpersonid=request("mpersonid")
 if cstr(mpersonid)<>"" and cstr(mpersonid)<>cstr(session("personid")) and session("userid")<>"13" and session("userid")<>"10" then
 	response.Write("��û��Ȩ�޲鿴��")
	response.End()
 end if
 dotype=request("dotype")
 'if mpersonid="" then mpersonid=session("personid")
%>
<%sear="n="%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>�ͻ�������־</title>
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
 <form id="form1" name="form1" method="post" action="salesHistory.asp"> <tr>
    <td colspan="8" align="center" bgcolor="f2f2f2">Email:
      
        <input name="com_email" type="text" id="com_email" size="15" />
        <select name="doperson" class="button" id="doperson" >
      <option value="" >������</option>
			  <% If session("personid")="227" Then %>
			  	<option value="227" >��&nbsp;&nbsp;��&nbsp;&nbsp;��������</option>
	    <% ElseIf session("personid")="93" Then %>
			  	<option value="93" >��&nbsp;&nbsp;��&nbsp;&nbsp;�����</option>
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
			  <option value="" <%=sle%>>��&nbsp;&nbsp;��<%response.Write(rsc("meno"))%></option>
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
              <option value="<%response.Write(rsu("id"))%>" <%=sle%>>��&nbsp;&nbsp;��&nbsp;&nbsp;��<%response.Write(rsu("realname"))%></option>
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
        <select name="mypersonid" class="button" id="mypersonid" >
          <option value="" >ԭ������</option>
			  <% If session("personid")="227" Then %>
			  	<option value="227" >��&nbsp;&nbsp;��&nbsp;&nbsp;��������</option>
	    <% ElseIf session("personid")="93" Then %>
			  	<option value="93" >��&nbsp;&nbsp;��&nbsp;&nbsp;�����</option>
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
			  <option value="" <%=sle%>>��&nbsp;&nbsp;��<%response.Write(rsc("meno"))%></option>
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
              <option value="<%response.Write(rsu("id"))%>" <%=sle%>>��&nbsp;&nbsp;��&nbsp;&nbsp;��<%response.Write(rsu("realname"))%></option>
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
       ��ʼʱ��<input name="fdate" type="text" id="fdate" size="10" value="<%=request("fdate")%>" />
       ����ʱ��<input name="tdate" type="text" id="tdate" size="10" value="<%=request("tdate")%>" />
       <%end if%>
       <select name="insh" id="insh">
       	 <option value=""></option>
         <option value="1">˽��</option>
         <option value="2">����</option>
       </select>
      <script>selectOption("insh","<%=request("insh")%>")</script>
      <input type="submit" name="button" id="button" value="��ѯ" />
      <input type="hidden" name="mpersonid" id="mpersonid" value="<%=mpersonid%>"/>
      <input type="hidden" name="dotype" id="dotype" value="<%=dotype%>"/>
      </td>
 </tr></form>
  
  <tr>
    <td colspan="8" bgcolor="f2f2f2">
    <%
	'------------ע����Դ
function showregFrom(fromid)
	sqlf="select meno from cate_compRegFrom where code ='"&fromid&"'"
	set rsf=conn.execute(sqlf)
	if not rsf.eof or not rsf.bof then
		showregFrom=rsf(0)
	else
		showregFrom=""
	end if
	rsf.close
	set rsf=nothing
end function
   sql=""
   if request("com_email")<>"" then
   	  sql=sql&" and exists (select com_id from comp_info where com_email like '%"&request("com_email")&"%' and com_id=crm_assignHistory.com_id)"
	  sear=sear&"&com_email="&request("com_email")
   end if
   if request("com_id")<>"" then
   	  sql=sql&" and com_id="&request("com_id")&""
	  sear=sear&"&com_id="&request("com_id")
   end if
   '------�ҵĲ�����־
   if mpersonid<>"" then
   	  sql=sql&" and (personid="&mpersonid&" or mypersonid="&mpersonid&")"
	  sear=sear&"&mpersonid="&request("mpersonid")
   end if
   if request("doperson")<>"" then
   	  sql=sql&" and personid="&request("doperson")&""
	  sear=sear&"&doperson="&request("doperson")
   end if
   if request("mypersonid")<>"" then
   	  sql=sql&" and mypersonid="&request("mypersonid")&""
	  sear=sear&"&mypersonid="&request("mypersonid")
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
   	  sql=sql&" and exists(select com_id from crm_assign where com_id=crm_assignHistory.com_id) "
	  sql=sql&" and com_id not in(select com_id from crm_assignHistory where sDetail='kangxianyueǿ�ƻָ��������Ŀͻ�' and fdate<'2010-3-5' and fdate>'2010-3-4') and com_id not in (138306,264386,455931,413592,166400,438622)"
	  sear=sear&"&insh="&request("insh")
   end if
   if request("insh")="2" then
   	  sql=sql&" and not exists(select com_id from crm_assign where com_id=crm_assignHistory.com_id)"
	  sear=sear&"&insh="&request("insh")
   end if
   Set oPage=New clsPageRs2
   With oPage
	 .sltFld  = "*"
	 .FROMTbl = "crm_assignHistory"
	 if request("insh")<>"" then
	 	.sqlOrder= "order by nowpersonid desc"
	 else
		.sqlOrder= "order by fdate desc"
	 end if
	 .sqlWhere= "WHERE not EXISTS (select id from test where com_id=crm_assignHistory.com_id) "&sql
	 .keyFld  = "id"    '����ȱ��
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
    <td colspan="8" bgcolor="#FFFFFF">
    <%
  if request("com_id")<>"" then
	sqlaa="select com_id,fdate from crm_dropedInsea where com_id="&request("com_id")&" and flag=0"
	set rsaa=conn.execute(sqlaa)
	if not  rsaa.eof or not rsaa.bof then
	while not rsaa.eof
		response.Write(rsaa("fdate")&"--����500�ͻ����빫��<br>")
	rsaa.movenext
	wend
	end if
	rsaa.close
	set rsaa=nothing
  end if
	%>
    </td>
  </tr>
  <tr>
    <td bgcolor="f2f2f2">������</td>
    <td bgcolor="f2f2f2">ԭ������</td>
    <td bgcolor="f2f2f2">ICD��������</td>
    <td bgcolor="f2f2f2">VAP��������</td>
    <td bgcolor="f2f2f2">��������˾</td>
    <td bgcolor="f2f2f2">����ʱ��</td>
    <td bgcolor="f2f2f2">��־</td>
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
	if rs("mypersonid")<>"" and not isnull(rs("mypersonid")) then
	sqluser="select realname from users where id="&rs("mypersonid")
	set rsu=conn.execute(sqluser)
	if not (rsu.eof or rsu.bof) then
		response.Write(rsu(0))
	end if
	rsu.close
	set rsu=nothing
	end if
	%>
    </td>
    <td bgcolor="#FFFFFF">
    <%
	sqluser="select a.realname from users as a,crm_assign as b where a.id=b.personid and b.com_id="&rs("com_id")
	set rsu=conn.execute(sqluser)
	if not (rsu.eof or rsu.bof) then
		response.Write(rsu(0))
	end if
	rsu.close
	set rsu=nothing
	%>
    </td>
    
    <td bgcolor="#FFFFFF"><%
	sqluser="select a.realname from users as a,crm_assignvap as b where a.id=b.personid and b.com_id="&rs("com_id")
	set rsu=conn.execute(sqluser)
	if not (rsu.eof or rsu.bof) then
		response.Write(rsu(0))
	end if
	rsu.close
	set rsu=nothing
	%></td>
    <td bgcolor="#FFFFFF">
    <%
	sqlL="select id,RegFrom from comp_regFrom where com_id="&rs("com_id")&" and RegFrom>19"
				  set rsL=conn.execute(sqlL)
				  if not (rsL.eof or rsL.bof) then
				  	regLyid=rsL(1)
				  	comLx="<font color=#CC9900>"&showregFrom(regLyid)&"</font>"
				  end if
				  rsL.close
				  set rsL=nothing
				  response.Write(comLx)
	%>
    <a href="/admin1/crmlocal/modaldealog_body.asp?../crmlocal/crm_cominfoedit.asp?idprod=<%=rs("com_id")%>&dotype=<%=dotype%>" target="_blank">
    <%sqluser="select com_name,com_email from comp_info where com_id="&rs("com_id")&""
	set rsu=conn.execute(sqluser)
	if not (rsu.eof or rsu.bof) then
		response.Write(rsu(0)&"("&rsu("com_email")&")")
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
<%
if session("userid")="10" then
%>
<input type="button" name="button2" id="button2" value="�ָ�" onclick="window.location='saleshistory.asp?<%=sear%>&hf=1'" />
<%
pp=0
sqluu=""
'sqly="update crm_assignHistory set nowpersonid=(select personid from crm_assign where com_id=crm_assignHistory.com_id) where 1=1 "&sql
'conn.execute(sqly)
response.Write(sql)
response.Write(session("lastcomid"))
if request("hf")="1" then
'sqltt=" and com_id in (select com_id from v_salescomp where not exists(select com_id from crm_3monthexpired_vipcomp where com_id=v_salescomp.com_id) and com_id not in (172765,100707,55432,160086,321746,416370,115495,184187,258539,433243,35006,258458,69010,277271,107675,171753,247814,295396,477404,326482,55843) and com_id not in (select com_id from temp_baoliucomp) and vip_datefrom>='2010-03-01 00:00:00' and exists(select com_id from comp_zstinfo where com_id=v_salescomp.com_id ) and personid=93)"
'sqlp="select * from crm_assignHistory where sdetail='��������ͨ�ָ�����' and not EXISTS (select id from test where com_id=crm_assignHistory.com_id) "&sqltt
if session("lastcomid")="" then
	
else
	'sql=sql&" and com_id<"&session("lastcomid")&""
end if
sqlp="select top 1000 com_id,mypersonid from crm_assignHistory where 1=1 "&sql &" order by com_id desc"
response.Write(sqlp)
set rsp=conn.execute(sqlp)
if not rsp.eof or not rsp.bof then
while not rsp.eof
	sqlt="select com_id from crm_assign where com_id="&rsp("com_id")&""
	set rst=conn.execute(sqlt)
	if not rst.eof or not rst.bof then
		sqluu="delete from crm_assign where com_id="&rsp("com_id")&""
		conn.execute(sqluu)
		sqlu="insert into crm_assign(personid,com_id) values("&rsp("mypersonid")&","&rsp("com_id")&")"
		conn.execute(sqlu)
	else
		sqluu=""
		sqlu="insert into crm_assign(personid,com_id) values("&rsp("mypersonid")&","&rsp("com_id")&")"
		conn.execute(sqlu)
	end if
	
	'------------д��ͻ������¼
	com_id=rsp("com_id")
	'sDetail=request.Cookies("admin_user")&"ǿ�ƶ�������ͨ�ָ�����Ŀͻ�"
	sDetail=request.Cookies("admin_user")&"�ָ�"
	sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
	conn.execute(sqlp)
	'response.Write(sqluu&"***"&sqlu&"<br>")
	pp=pp+1
	response.Write(session("lastcomid")&"<br>")
	session("lastcomid")=com_id
rsp.movenext
wend
end if
rsp.close
set rsp=nothing
response.Write(pp)
end if
%>
<%
end if
%>
<br />
<br />
<div style="font-size:12px; color:#ff0000">
��ע��<br />
��־ 2009-6-4 ���� 14�㿪ʼ��¼ <br>
ԭ������ 2009-9-11��ʼ��¼<br>
ϵͳ�Զ����� 2010-2-24  ��ʼ��¼
</div>
</body>
</html>

<%
conn.close
set conn=nothing
%>