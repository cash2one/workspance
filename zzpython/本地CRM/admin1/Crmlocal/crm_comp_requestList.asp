<!-- #include file="../include/ad!$#5V.asp" -->
<!--#include file="../include/pagefunction.asp"-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>�ȼ��ͻ��б�</title>
<style type="text/css">
<!--
body {
	font-size: 12px;
}
-->
</style>
</head>

<body>
<%
	sqlTemp=" and EXISTS(select com_id from crm_assign_request where com_id=v_salescomp.com_id and assignflag=0)"
	sql=sqlTemp
%>
<table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#CCCCCC">
   <form id="form1" name="form1" method="post" action="<%=sear%>"><tr>
    <td align="center" bgcolor="#F2F2F2">��˾���ƣ�
     
        <input type="text" name="com_name" id="com_name" />
        EMAIL��
        <input type="text" name="com_email" id="com_email" />
            <select name="comporder" id="comporder">
              <option>Ĭ��</option>
              <option value="1" <%if request("comporder")="1" then response.Write("selected")%>>�ͻ��ȼ�</option>
              <option value="2" <%if request("comporder")="2" then response.Write("selected")%>>��¼����</option>
              <option value="3" <%if request("comporder")="3" then response.Write("selected")%>>�´���ϵʱ��</option>
              <option value="4" <%if request("comporder")="4" then response.Write("selected")%>>����ʱ��</option>
			  <option value="5" <%if request("comporder")="5" then response.Write("selected")%>>�����ϵʱ��</option>
			  <option value="6" <%if request("comporder")="6" then response.Write("selected")%>>ע��ʱ��</option>
			  <option value="7" <%if request("comporder")="7" then response.Write("selected")%>>�����½ʱ��</option>
			  <option value="8" <%if request("comporder")="8" then response.Write("selected")%>>����ʱ��</option>
            </select>
			<select name="ascdesc" id="ascdesc">
			  <option value="desc" <%if request("ascdesc")="desc" then response.Write("selected")%>>����</option><option value="asc" <%if request("ascdesc")="asc" then response.Write("selected")%>>����</option>
	        </select>
      <input type="submit" name="button" id="button" value=" �� �� " /></td>
  </tr> </form>
</table>
<%
	if request("del")<>"" then
		sqlaa="delete from crm_assign_request where com_id="&request("com_id")&""
		conn.execute(sqlaa)
		response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
		closeconn()
		response.End()
	end if
	
	if request("com_name")<>"" then
		sql=sql&" and com_name like '%"&request("com_name")&"%'"
		sear=sear&"&com_name="&request("com_name")
	end if
	if request("com_email")<>"" then
		sql=sql&" and com_email like '%"&request("com_email")&"%'"
		sear=sear&"&com_email="&request("com_email")
	end if
	'----------------end
	
	sqlorder="order by "
	ascdesc=request("ascdesc")
	select case request("comporder")
		case "1"
		sqlorder=sqlorder&"com_rank "&ascdesc&""
		case "2"
		sqlorder=sqlorder&" logincount "&ascdesc&""
		case "3"
		sqlorder=sqlorder&"contactnext_time "&ascdesc&""
		case "4"
		sqlorder=sqlorder&"vip_date "&ascdesc&""
		case "5"
		sqlorder=sqlorder&"teldate "&ascdesc&""
		case "6"
		sqlorder=sqlorder&"com_regtime "&ascdesc&""
		case "7"
		sqlorder=sqlorder&"lastlogintime "&ascdesc&""
		case "8"
		sqlorder=sqlorder&"vip_dateto "&ascdesc&""
		case else
		sqlorder=sqlorder&"com_regtime desc"
	end select
	sear=sear&"&comporder="&request("comporder")&"&ascdesc="&request("ascdesc")
	sql=sql&" and not EXISTS(select null from Comp_ZSTinfo where com_id=v_salescomp.com_id)"
	'response.Write(sql)
	Set oPage=New clsPageRs2
	With oPage
		.sltFld  = "com_id,com_name,com_email,com_rank,logincount,com_regtime,contactnext_time,lastlogintime,teldate"
		.FROMTbl = "v_salescomp"
		.sqlOrder= sqlorder
		.sqlWhere= "WHERE not EXISTS (select null from Agent_ClientCompany where com_id=v_salescomp.com_id) "&sql
		.keyFld  = "com_id"    '����ȱ��
		.pageSize= 10
		.getConn = conn
		Set Rs  = .pageRs
	End With
	total=oPage.getTotalPage
	oPage.pageNav "?"&sear,""
	totalpg=cint(total/10)
	if total/10 > totalpg then
		totalpg=totalpg+1
	end if
%>
<table width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#CCCCCC">
<tr>
    <td height="25" bgcolor="#f2f2f2">��˾��</td>
    <td bgcolor="#F2F2F2">Email</td>
    <td bgcolor="#F2F2F2">�ͻ��ȼ�</td>
    <td bgcolor="#F2F2F2">�����¼</td>
    <td bgcolor="#F2F2F2">��¼����</td>
    <td bgcolor="#F2F2F2">�´���ϵʱ��</td>
    <td bgcolor="#F2F2F2">�����ϵʱ��</td>
    <td bgcolor="#F2F2F2">ע��ʱ��</td>
    <td bgcolor="#F2F2F2">������</td>
    <td bgcolor="#F2F2F2">������</td>
    <td colspan="2" bgcolor="#F2F2F2">&nbsp;</td>
  </tr><%
while not rs.eof %>
  
  <tr>
    <td height="25" bgcolor="#FFFFFF"><a target="_blank" href="/admin1/crmlocal/modaldealog_body.asp?../crmlocal/crm_cominfoedit.asp?idprod=<%=rs("com_id")%>"><%=rs("com_name")%></a></td>
    <td bgcolor="#FFFFFF"><%=rs("com_email")%></td>
    <td bgcolor="#FFFFFF"><%=rs("com_rank")%></td>
    <td bgcolor="#FFFFFF"><%=rs("lastlogintime")%></td>
    <td bgcolor="#FFFFFF"><%=rs("logincount")%></td>
    <td bgcolor="#FFFFFF">
	<%
	if rs("contactnext_time")="1900-1-1" then
	else
	response.Write(rs("contactnext_time"))
	end if
	
	%></td>
    <td bgcolor="#FFFFFF"><%=rs("teldate")%></td>
    <td bgcolor="#FFFFFF"><%=rs("com_regtime")%></td>
    <td nowrap="nowrap" bgcolor="#FFFFFF">
    <%
				  sqluser="select a.personid,b.realname from crm_assign as a,users as b where a.com_id="&rs("com_id")&" and a.personid=b.id"
				  set rsuser=conn.execute(sqluser)
				  if not rsuser.eof then
				  	realnamelr=rsuser("realname")
				  else
				  	realnamelr=""
				  end if
				  rsuser.close
				  set rsuser=nothing
				  response.write(realnamelr)
				  %>
    </td>
    <td nowrap="nowrap" bgcolor="#FFFFFF">
    <%
				  sqluser="select a.personid,b.realname from crm_assign_request as a,users as b where a.com_id="&rs("com_id")&" and a.personid=b.id"

				  set rsuser=conn.execute(sqluser)
				  if not rsuser.eof then
				  	realname=rsuser("realname")
					personid=rsuser("personid")
				  else
				  	realname=""
				  end if
				  response.Write(realname)
				  %></td>
    <td nowrap="nowrap" bgcolor="#FFFFFF"><a href="crm_comp_requestSH.asp?com_id=<%=rs("com_id")%>&amp;personid=<%=personid%>" onClick="return confirm('ȷʵҪ�������������')">ȷ������</a></td>
    <td nowrap="nowrap" bgcolor="#FFFFFF"><%if session("userid")="10" then%><p><a href="?del=1&com_id=<%=rs("com_id")%>" onclick="return confirm('ȷʵҪɾ����')">ɾ��</a></p><%end if%></td>
  </tr>
<%
rs.movenext
wend
rs.close
set rs=nothing
conn.close
set conn=nothing
%>
</table>

</body>
</html>
