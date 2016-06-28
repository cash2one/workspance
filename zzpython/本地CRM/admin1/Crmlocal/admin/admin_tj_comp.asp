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
dotype=request("dotype")
lmaction=request("lmaction")
action=request("action")
sqlTemp=""
    'sqlTemp="select com_id,com_name from v_salescomp WHERE "
	'sqlTemp=sqlTemp&" not EXISTS (select null from Agent_ClientCompany where com_id=v_salescomp.com_id) "
	sqlTemp=sqlTemp&" and not EXISTS(select null from comp_sales where (com_type=13) and com_id=v_salescomp.com_id) "
	'sqlTemp=sqlTemp&" and not EXISTS(select com_id from crm_OtherComp where com_id=v_salescomp.com_id)"
	'sqlTemp=sqlTemp&" and not EXISTS(select com_id from crm_InsertCompWeb where com_id=v_salescomp.com_id and saletype=2)"
	'sqlTemp=sqlTemp&" and not EXISTS(select com_id from crm_webPipeiComp where com_id=v_salescomp.com_id )"
	'sqlTemp=sqlTemp&" and not EXISTS(select com_id from Crm_Active_CompAll where com_id=v_salescomp.com_id and ActivePublic=0 and not exists (select null from Crm_Active_ActiveComp where com_id=Crm_Active_CompAll.com_id))"
	if request.QueryString("personid")<>"" then
		if request.QueryString("daodan")="1" or request.QueryString("daodan")="4" then
		else
		if dotype="vapcomp" then
			sql=sqlTemp&" and vpersonid="&request.QueryString("personid")
		else
			if request.QueryString("4star")="1" or request.QueryString("5star")="1" then
			else
			sql=sqlTemp&" and personid="&request.QueryString("personid")
			end if
		end if
		end if
	elseif request.QueryString("code")<>"" then
		sql=sqlTemp&" and personid in (select id from users where userid='"&request.QueryString("code")&"')"
	end if
	sear=sear&"dotype="&dotype
	sear=sear&"&personid="&request.QueryString("personid")&"&code="&request("code")
	if request.QueryString("star")<>"" then
		if request.QueryString("star")="0" then
			sql=sql&" and com_id in (select com_id from crm_tuo_hui_comp where personid="&request.QueryString("personid")&" )"
		else
			if dotype="vapcomp" then
				sql=sql&" and vcom_rank="&request.QueryString("star")&""
			else
				sql=sql&" and com_rank="&request.QueryString("star")&""
			end if
		end if
		sear=sear&"&star="&request.QueryString("star")
	end if
	'----------公海挑入未联系
	if request.QueryString("gonghai")<>"" then
		sql=sql&" and EXISTS(select com_id from v_groupcomid where com_id=v_salescomp.com_id and telid in (select id from comp_tel where personid<>"&request.QueryString("personid")&""
		if dotype="vapcomp" then
			sql=sql&" and telflag=4 "
		else
			sql=sql&" and telflag<4 "
		end if
		sql=sql&"))"
		sear=sear&"&gonghai="&request.QueryString("gonghai")
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
		if dotype="vapcomp" then
			sql=sql&" and vcontactnext_time<'"&date()&"' and contactnext_time<>'1900-1-1'"
		else
			sql=sql&" and contactnext_time<'"&date()&"' and contactnext_time<>'1900-1-1'"
		end if
		sear=sear&"&NoCon="&request.QueryString("NoCon")
	end if
	'----------------begin
	'--------明日安排联系
	if request.QueryString("Tomotor")<>"" then
		if dotype="vapcomp" then
			sql=sql&" and vcontactnext_time >='"&date()+1&"' and vcontactnext_time<='"&date()+2&"'"
		else
			sql=sql&" and contactnext_time >='"&date()+1&"' and contactnext_time<='"&date()+2&"'"
		end if
		sear=sear&"&Tomotor="&request.QueryString("Tomotor")
	end if
	'--------今日安排联系
	if request.QueryString("today")<>"" then
		if dotype="vapcomp" then
			sql=sql&" and vcontactnext_time >='"&date()&"' and vcontactnext_time<='"&date()+1&"'"
		else
			sql=sql&" and contactnext_time >='"&date()&"' and contactnext_time<='"&date()+1&"'"
		end if
		sear=sear&"&Tomotor="&request.QueryString("Tomotor")
	end if
	if request.QueryString("4star")="1" then
		sqlt=""
		if request("from_date")<>"" then
			sqlt=sqlt&" and fdate>='"&request("from_date")&"'"
		end if
		if request("to_date")<>"" then
			sqlt=sqlt&" and fdate<='"&CDate(request("to_date"))+1&"'"
		end if
		if request("from_date")="" and request("to_date")="" then
			sqlt=sqlt&" and fdate>='"&date&"'"
		end if
		if request.QueryString("userid")="1315" then
			sql=sql&" and exists(select com_id from crm_Tostar_vap where com_id=v_salescomp.com_id and star=4 and personid="&request.QueryString("personid")&" "&sqlt&")"
		else
			sql=sql&" and exists(select com_id from crm_to4star where com_id=v_salescomp.com_id and personid="&request.QueryString("personid")&" "&sqlt&")"
		end if
		sear=sear&"&4star="&request.QueryString("4star")
		sear=sear&"&from_date="&request.QueryString("from_date")
		sear=sear&"&to_date="&request.QueryString("to_date")
	end if
	if request.QueryString("5star")="1" then
		sqlt=""
		if request("from_date")<>"" then
			sqlt=sqlt&" and fdate>='"&request("from_date")&"'"
		end if
		if request("to_date")<>"" then
			sqlt=sqlt&" and fdate<='"&CDate(request("to_date"))+1&"'"
		end if
		if request("from_date")="" and request("to_date")="" then
			sqlt=sqlt&" and fdate>='"&date&"'"
		end if
		sql=sql&" and exists(select com_id from crm_to5star where com_id=v_salescomp.com_id and personid="&request.QueryString("personid")&" "&sqlt&")"
		sear=sear&"&5star="&request.QueryString("5star")
		sear=sear&"&from_date="&request.QueryString("from_date")
		sear=sear&"&to_date="&request.QueryString("to_date")
	end if
	if request.QueryString("vapstar")<>"" then
		sqlt=""
		if request("from_date")<>"" then
			sqlt=sqlt&" and fdate>='"&request("from_date")&"'"
		end if
		if request("to_date")<>"" then
			sqlt=sqlt&" and fdate<='"&CDate(request("to_date"))+1&"'"
		end if
		if request("from_date")="" and request("to_date")="" then
			sqlt=sqlt&" and fdate>='"&date&"'"
		end if
		sql=sql&" and exists(select com_id from crm_Tostar_vap where com_id=v_salescomp.com_id and personid="&request.QueryString("personid")&" "&sqlt&" and star="&request.QueryString("vapstar")&")"
		sear=sear&"&vapstar="&request.QueryString("vapstar")
		sear=sear&"&from_date="&request.QueryString("from_date")
		sear=sear&"&to_date="&request.QueryString("to_date")
		
	end if
	if request.QueryString("daodan")="1" then
		sqlt=""
		if request("from_date")<>"" then
			sqlt=sqlt&" and sales_date>='"&request("from_date")&"'"
		end if
		if request("to_date")<>"" then
			sqlt=sqlt&" and sales_date<='"&CDate(request("to_date"))+1&"'"
		end if
		if request("from_date")="" and request("to_date")="" then
			sqlt=sqlt&" and sales_date>='"&date&"'"
		end if
		sql=sql&" and exists(select com_id from renshi_salesIncome where com_id=v_salescomp.com_id and personid="&request.QueryString("personid")&" "&sqlt&")"
		sear=sear&"&daodan="&request.QueryString("daodan")
		sear=sear&"&from_date="&request.QueryString("from_date")
		sear=sear&"&to_date="&request.QueryString("to_date")
	end if
	if request.QueryString("daodan")="4" then
		sqlt=""
		if request("from_date")<>"" then
			sqlt=sqlt&" and sales_date>='"&request("from_date")&"'"
		end if
		if request("to_date")<>"" then
			sqlt=sqlt&" and sales_date<='"&CDate(request("to_date"))+1&"'"
		end if
		if request("from_date")="" and request("to_date")="" then
			sqlt=sqlt&" and sales_date>='"&date&"'"
		end if
		sqlts=""
		if request("sfrom_date")<>"" then
			sqlts=sqlts&" and fdate>='"&request("sfrom_date")&"'"
		end if
		if request("sto_date")<>"" then
			sqlts=sqlts&" and fdate<='"&CDate(request("sto_date"))+1&"'"
		end if
		if request("sfrom_date")="" and request("sto_date")="" then
			sqlts=sqlts&" and fdate>='"&date&"'"
		end if
		sql=sql&" and exists(select com_id from renshi_salesIncome where com_id=v_salescomp.com_id and personid="&request.QueryString("personid")&" "&sqlt&") and not exists(select com_id from renshi_salesIncome where com_id=v_salescomp.com_id and personid="&request.QueryString("personid")&" and not exists(select com_id from crm_to5star where com_id=renshi_salesIncome.com_id "&sqlts&") and not exists(select com_id from crm_to4star where com_id=renshi_salesIncome.com_id "&sqlts&"))"
		sear=sear&"&daodan="&request.QueryString("daodan")
		sear=sear&"&from_date="&request.QueryString("from_date")
		sear=sear&"&to_date="&request.QueryString("to_date")
		sear=sear&"&dotype="&dotype
	end if
	'------大客户
	if lmaction="ad_dakehu" then
		sql=sql&" and exists(select com_id from crm_vap_complist where v_salescomp.com_id=com_id) and exists(select com_id from comp_vapinfo where v_salescomp.com_id=com_id) and (exists(select com_id from comp_payinfo where v_salescomp.com_id=com_id and money>=8000)) and not EXISTS(select com_id from crm_notBussiness where com_id=v_salescomp.com_id)"
	end if
	'------必杀期
	if lmaction="ad_bisha" then
		sql=sql&" and exists(select com_id from crm_vap_complist where v_salescomp.com_id=com_id) and exists(select com_id from comp_vapinfo where v_salescomp.com_id=com_id) and (exists(select com_id from AdvKeyWords where v_salescomp.com_id=com_id and DATEDIFF(MM, fromDate, GETDATE()) <= 12 and DATEDIFF(MM, fromDate, GETDATE()) >= 10)) and not EXISTS(select com_id from crm_notBussiness where com_id=v_salescomp.com_id)"
	end if
	'------小广告
	if lmaction="ad_xiaoguangao" then
		sql=sql&" and (exists(select com_id from comp_payinfo where v_salescomp.com_id=com_id and money<2000))"
	end if
	'------黄金广告
	if lmaction="ad_huangjin" then
		sql=sql&" and exists(select com_id from crm_vap_complist where v_salescomp.com_id=com_id) and exists(select com_id from comp_vapinfo where v_salescomp.com_id=com_id) and (exists(select com_id from AdvKeyWords where v_salescomp.com_id=com_id and DATEDIFF(MM, fromDate, GETDATE()) <= 9 and DATEDIFF(MM, fromDate, GETDATE()) >= 2)) and (exists(select com_id from AdvKeyWords where v_salescomp.com_id=com_id and DATEDIFF(MM, fromDate, GETDATE()) <= 9 and DATEDIFF(MM, fromDate, GETDATE()) >= 2)) and not EXISTS(select com_id from crm_notBussiness where com_id=v_salescomp.com_id)"
	end if
	'------新客户
	if lmaction="ad_newcomp" then
		sql=sql&" and exists(select com_id from crm_vap_complist where v_salescomp.com_id=com_id) and exists(select com_id from comp_vapinfo where v_salescomp.com_id=com_id) and (exists(select com_id from AdvKeyWords where v_salescomp.com_id=com_id and DATEDIFF(MM, fromDate, GETDATE()) <= 1 and DATEDIFF(MM, fromDate, GETDATE()) >= 0)) and not EXISTS(select com_id from crm_notBussiness where com_id=v_salescomp.com_id)"
	end if
	'------过期客户
	if lmaction="ad_guoqi" then
		sql=sql&" and exists(select com_id from crm_vap_complist where v_salescomp.com_id=com_id) and exists(select com_id from comp_vapinfo where v_salescomp.com_id=com_id) and (exists(select com_id from AdvKeyWords where v_salescomp.com_id=com_id and DATEDIFF(DD, toDate, GETDATE()) > 0 )) and not EXISTS(select com_id from crm_notBussiness where com_id=v_salescomp.com_id)"
	end if
	if action="huangzhan" then
		sql=sql&" and exists(select com_id from advkeywords where  v_salescomp.com_id=com_id and typeid='11' )"
	end if
	if action="biaowang" then
		sql=sql&" and exists(select com_id from advkeywords where  v_salescomp.com_id=com_id and typeid='13' )"
	end if
	if action="dujia" then
		sql=sql&" and exists(select com_id from advkeywords where  v_salescomp.com_id=com_id and typeid in(18,10,19,23,20,24,12,22,14,15) )"
	end if	
	sear=sear&"&lmaction="&lmaction
	sear=sear&"&action="&action
%>
<table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#CCCCCC">
   <form id="form1" name="form1" method="get" action="admin_tj_comp.asp"><tr>
    <td align="center" bgcolor="#F2F2F2"><input type="hidden" name="4star" id="4star" value="<%=request("4star")%>" />
      <input type="hidden" name="from_date" id="from_date" value="<%=request("from_date")%>" />
      <input type="hidden" name="to_date" id="to_date"  value="<%=request("to_date")%>" />
      <input type="hidden" name="dotype" id="dotype"  value="<%=request("dotype")%>" />
      <input type="hidden" name="NoCon" id="NoCon"  value="<%=request("NoCon")%>" />
      <input type="hidden" name="gonghai" id="gonghai"  value="<%=request("gonghai")%>" />
      <input type="hidden" name="star" id="star"  value="<%=request("star")%>" />
      <input type="hidden" name="Tomotor" id="Tomotor"  value="<%=request("Tomotor")%>" />
      <input type="hidden" name="Never" id="Never"  value="<%=request("Never")%>" />
      <input type="hidden" name="personid" id="personid"  value="<%=request("personid")%>" />
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
		if dotype="vapcomp" then
			sqlorder=sqlorder&"vcom_rank "&ascdesc&""
		else
			sqlorder=sqlorder&"com_rank "&ascdesc&""
		end if
		case "2"
		sqlorder=sqlorder&" logincount "&ascdesc&""
		case "3"
		if dotype="vapcomp" then
			sqlorder=sqlorder&"vcontactnext_time "&ascdesc&""
		else
			sqlorder=sqlorder&"contactnext_time "&ascdesc&""
		end if
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
	if request("type")<>"zst" then 
		if request("daodan")<>"1" and request("daodan")<>"4" and request.QueryString("5star")<>"1" and request.QueryString("4star")<>"1" and dotype<>"vapcomp" then
			if left(dotype,3)="vap" then
			
			else
				sql=sql&" and not EXISTS(select null from Comp_ZSTinfo where com_id=v_salescomp.com_id)"
			end if
		end if
	else
		
		sql=sql&" and EXISTS(select null from Comp_ZSTinfo where com_id=v_salescomp.com_id)"
		
	end if
	regdate=request("regdate")
	if regdate<>"" then
		sql=sql&" and  DATEDIFF(DD, com_regtime, GETDATE()) <= "&regdate&""
		sear=sear&"&regdate="&request("regdate")
	end if
	sear=sear&"&type="&request("type")
	if session("userid")="10" then
	response.Write(sql)
	end if
	Set oPage=New clsPageRs2
	With oPage
		.sltFld  = "com_id,com_name,com_email,com_rank,vcom_rank,logincount,com_regtime,contactnext_time,contactnext_time,lastlogintime,teldate,vcontactnext_time"
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
    <td bgcolor="#F2F2F2">vap销售</td>
  </tr><%
while not rs.eof %>
  
  <tr>
    <td height="25" bgcolor="#FFFFFF"><a target="_blank" href="/admin1/crmlocal/modaldealog_body.asp?../crmlocal/crm_cominfoedit.asp?idprod=<%=rs("com_id")%>&dotype=<%=dotype%>"><%=rs("com_name")%></a></td>
    <td bgcolor="#FFFFFF"><%=rs("com_email")%></td>
    <td bgcolor="#FFFFFF">
	<%
	if dotype="vapcomp" then
		response.Write(rs("vcom_rank"))
	else
		response.Write(rs("com_rank"))
	end if
	%></td>
    <td bgcolor="#FFFFFF"><%=rs("lastlogintime")%></td>
    <td bgcolor="#FFFFFF"><%=rs("logincount")%></td>
    <td bgcolor="#FFFFFF">
	<%
	if dotype="vapcomp" then
		if rs("vcontactnext_time")="1900-1-1" then
		else
			response.Write(rs("vcontactnext_time"))
		end if
	else
		if rs("contactnext_time")="1900-1-1" then
		else
			response.Write(rs("contactnext_time"))
		end if
	end if
	
	%></td>
    <td bgcolor="#FFFFFF"><%=rs("teldate")%></td>
    <td bgcolor="#FFFFFF"><%=rs("com_regtime")%></td>
    <td bgcolor="#FFFFFF">
    <%
				  sqluser="select a.personid,b.realname from crm_assign as a,users as b where a.com_id="&rs("com_id")&" and a.personid=b.id"

				  set rsuser=conn.execute(sqluser)
				  if not rsuser.eof then
				  realname=rsuser("realname")
				  else
				  realname=""
				  end if
				  response.Write(realname)
				  rsuser.close
				  set rsuser=nothing
				  %>    </td>
    <td bgcolor="#FFFFFF"><%
	sqluser="select a.personid,b.realname from crm_assignvap as a,users as b where a.com_id="&rs("com_id")&" and a.personid=b.id"

				  set rsuser=conn.execute(sqluser)
				  if not rsuser.eof then
					  realname=rsuser("realname")
					  response.Write(realname)
				  else
				  realname=""
				  end if
				  rsuser.close
				  set rsuser=nothing
				  %>
	</td>
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
<%
if request("daodan")<>"" then
	response.Write("广告到单和再生一起的公司只算一家")
end if
%>

</body>
</html>
