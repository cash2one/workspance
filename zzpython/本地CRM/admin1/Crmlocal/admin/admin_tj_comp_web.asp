<!-- #include file="../../include/ad!$#5V.asp" -->
<!--#include file="../../include/pagefunction.asp"-->
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
    'sqlTemp="select com_id,com_name from v_salescomp WHERE "
	'sqlTemp=sqlTemp&" not EXISTS (select null from Agent_ClientCompany where com_id=v_salescomp.com_id) "
	'sqlTemp=sqlTemp&" and not EXISTS(select null from comp_sales where (com_type=13) and com_id=v_salescomp.com_id) "
	sqlTemp=sqlTemp&" and not EXISTS(select com_id from crm_OtherComp where com_id=v_salescomp.com_id)"
	'sqlTemp=sqlTemp&" and not EXISTS(select com_id from crm_InsertCompWeb where com_id=v_salescomp.com_id and saletype=2)"
	sqlTemp=sqlTemp&" and not EXISTS(select com_id from crm_webPipeiComp where com_id=v_salescomp.com_id )"
	sqlTemp=sqlTemp&" and not EXISTS(select com_id from Crm_Active_CompAll where com_id=v_salescomp.com_id and ActivePublic=0 and not exists (select null from Crm_Active_ActiveComp where com_id=Crm_Active_CompAll.com_id))"
	if request.QueryString("personid")<>"" then
		sql=sqlTemp&" and wpersonid="&request.QueryString("personid")
	elseif request.QueryString("code")<>"" then
		sql=sqlTemp&" and wpersonid in (select id from users where userid='"&request.QueryString("code")&"')"
	end if
	sear=sear&"personid="&request.QueryString("personid")&"&code="&request("code")
	if request.QueryString("star")<>"" then
		sql=sql&" and com_rank="&request.QueryString("star")&""
		sear=sear&"&star="&request.QueryString("star")
	end if
	'----------------begin
	'--------从未联系
	if request.QueryString("Never")<>"" then
		sql=sql&" and not EXISTS (select null from v_groupcomid where  com_id=v_salescomp.com_id)"
		sear=sear&"&Never="&request.QueryString("Never")
	end if
	'----------------begin
	'--------安排但未联系
	if request.QueryString("NoCon")<>"" then
		sql=sql&" and contactnext_time<'"&date()&"' and contactnext_time<>'1900-1-1'"
		sear=sear&"&NoCon="&request.QueryString("NoCon")
	end if
	'----------------begin
	'--------明日安排联系
	if request.QueryString("Tomotor")<>"" then
		sql=sql&" and contactnext_time >='"&date()+1&"' and contactnext_time<='"&date()+2&"'"
		sear=sear&"&Tomotor="&request.QueryString("Tomotor")
	end if
	if request.QueryString("4star")="1" then
		sqlt=""
		if request("from_date")<>"" then
			sqlt=sqlt&" and fdate>='"&request("from_date")&"'"
		end if
		if request("to_date")<>"" then
			sqlt=sqlt&" and fdate<='"&request("to_date")&"'"
		end if
		if request("from_date")="" and request("to_date")="" then
			sqlt=sqlt&" and fdate='"&date&"'"
		end if
		sql=sql&" and exists(select com_id from crm_to4star where com_id=v_salescomp.com_id and personid="&request.QueryString("personid")&" "&sqlt&")"
		sear=sear&"&4star="&request.QueryString("4star")
		sear=sear&"&from_date="&request.QueryString("from_date")
		sear=sear&"&to_date="&request.QueryString("to_date")
	end if
%>
<table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#CCCCCC">
   <form id="form1" name="form1" method="post" action="?<%=sear%>"><tr>
    <td align="center" bgcolor="#F2F2F2"><input type="hidden" name="4star" id="4star" value="<%=request("4star")%>" />
      <input type="hidden" name="from_date" id="from_date" value="<%=request("from_date")%>" />
      <input type="hidden" name="to_date" id="to_date"  value="<%=request("to_date")%>" />
      公司名称：
     
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
	
	if request("com_name")<>"" then
		sql=sql&" and com_name like '%"&request("com_name")&"%'"
		sear=sear&"&com_name="&request("com_name")
	end if
	if request("com_email")<>"" then
		sql=sql&" and com_email like '%"&request("com_email")&"%'"
		sear=sear&"&com_email="&request("com_email")
	end if
	'----------------end
	'----------------begin
	'--------明日安排联系
	if request.QueryString("contacttype")<>"" then
		sql=sql&" and contacttype ="&request.QueryString("contacttype")&""
		sear=sear&"&contacttype="&request.QueryString("contacttype")
	end if
	
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
		sqlorder=sqlorder&"teldate asc"
	end select
	sear=sear&"&comporder="&request("comporder")&"&ascdesc="&request("ascdesc")
	'sql=sql&" and not EXISTS(select null from Comp_ZSTinfo where com_id=v_salescomp.com_id)"
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
    <td bgcolor="#F2F2F2">拥有者</td>
    <td bgcolor="#F2F2F2">&nbsp;</td>
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
    <td bgcolor="#FFFFFF">
    <%
				  sqluser="select a.personid,b.realname from crm_assignweb as a,users as b where a.com_id="&rs("com_id")&" and a.personid=b.id"

				  set rsuser=conn.execute(sqluser)
				  if not rsuser.eof then
				  realname=rsuser("realname")
				  else
				  realname=""
				  end if
				  response.Write(realname)
				  %>    </td>
    <td bgcolor="#FFFFFF">&nbsp;</td>
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
