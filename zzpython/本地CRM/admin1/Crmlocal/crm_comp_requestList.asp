<!-- #include file="../include/ad!$#5V.asp" -->
<!--#include file="../include/pagefunction.asp"-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>等级客户列表</title>
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
    <td align="center" bgcolor="#F2F2F2">公司名称：
     
        <input type="text" name="com_name" id="com_name" />
        EMAIL：
        <input type="text" name="com_email" id="com_email" />
            <select name="comporder" id="comporder">
              <option>默认</option>
              <option value="1" <%if request("comporder")="1" then response.Write("selected")%>>客户等级</option>
              <option value="2" <%if request("comporder")="2" then response.Write("selected")%>>登录次数</option>
              <option value="3" <%if request("comporder")="3" then response.Write("selected")%>>下次联系时间</option>
              <option value="4" <%if request("comporder")="4" then response.Write("selected")%>>申请时间</option>
			  <option value="5" <%if request("comporder")="5" then response.Write("selected")%>>最后联系时间</option>
			  <option value="6" <%if request("comporder")="6" then response.Write("selected")%>>注册时间</option>
			  <option value="7" <%if request("comporder")="7" then response.Write("selected")%>>最近登陆时间</option>
			  <option value="8" <%if request("comporder")="8" then response.Write("selected")%>>到期时间</option>
            </select>
			<select name="ascdesc" id="ascdesc">
			  <option value="desc" <%if request("ascdesc")="desc" then response.Write("selected")%>>降序</option><option value="asc" <%if request("ascdesc")="asc" then response.Write("selected")%>>升序</option>
	        </select>
      <input type="submit" name="button" id="button" value=" 搜 索 " /></td>
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
		.keyFld  = "com_id"    '不可缺少
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
    <td height="25" bgcolor="#f2f2f2">公司名</td>
    <td bgcolor="#F2F2F2">Email</td>
    <td bgcolor="#F2F2F2">客户等级</td>
    <td bgcolor="#F2F2F2">最近登录</td>
    <td bgcolor="#F2F2F2">登录次数</td>
    <td bgcolor="#F2F2F2">下次联系时间</td>
    <td bgcolor="#F2F2F2">最后联系时间</td>
    <td bgcolor="#F2F2F2">注册时间</td>
    <td bgcolor="#F2F2F2">所属者</td>
    <td bgcolor="#F2F2F2">申请人</td>
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
    <td nowrap="nowrap" bgcolor="#FFFFFF"><a href="crm_comp_requestSH.asp?com_id=<%=rs("com_id")%>&amp;personid=<%=personid%>" onClick="return confirm('确实要分配给申请者吗？')">确定分配</a></td>
    <td nowrap="nowrap" bgcolor="#FFFFFF"><%if session("userid")="10" then%><p><a href="?del=1&com_id=<%=rs("com_id")%>" onclick="return confirm('确实要删除吗？')">删除</a></p><%end if%></td>
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
