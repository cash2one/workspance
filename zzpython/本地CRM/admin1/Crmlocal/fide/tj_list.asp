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

	sear=sear&"dotype="&dotype
	if request.QueryString("star")<>"" then
		sql=sql&" and star="&request.QueryString("star")&""
		sear=sear&"&star="&request.QueryString("star")
	end if
	if request.QueryString("personid")<>"" then
		sql=sql&" and exists (select cid from ybp_assign where personid="&request.QueryString("personid")&" and cid=ybp_company.id)"
		sear=sear&"&personid="&request.QueryString("personid")
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
		sql=sql&" and not exists(select cid from ybp_tel where cid=ybp_company.id and personid="&request.QueryString("personid")&")"
		sear=sear&"&Never="&request.QueryString("Never")
	end if
	'----------------begin
	'--------安排但未联系
	if request.QueryString("NoCon")<>"" then
		sql=sql&" and interviewTime<'"&date()&"' and interviewTime is not null"
		sear=sear&"&NoCon="&request.QueryString("NoCon")
	end if
	'----------------begin
	'--------明日安排联系
	if request.QueryString("Tomotor")<>"" then
		sql=sql&" and interviewTime>='"&date()+1&"' and interviewTime<='"&date()+2&"'"
		sear=sear&"&Tomotor="&request.QueryString("Tomotor")
	end if
	'--------今日安排联系
	if request.QueryString("today")<>"" then
		sql=sql&" and interviewTime>='"&date()&"' and interviewTime<='"&date()+1&"'"
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
		sql=sql&" and exists(select cid from ybp_tostar where cid=ybp_company.id and star=4 and personid="&request.QueryString("personid")&" "&sqlt&")"
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
		sql=sql&" and exists(select cid from ybp_tostar where cid=ybp_company.id and star=5 and personid="&request.QueryString("personid")&" "&sqlt&")"
		sear=sear&"&5star="&request.QueryString("5star")
		sear=sear&"&from_date="&request.QueryString("from_date")
		sear=sear&"&to_date="&request.QueryString("to_date")
	end if
	
	if request.QueryString("daodan")="1" then
		sqlt=""
		if request("from_date")<>"" then
			sqlt=sqlt&" and contacttime>='"&request("from_date")&"'"
		end if
		if request("to_date")<>"" then
			sqlt=sqlt&" and contacttime<='"&CDate(request("to_date"))+1&"'"
		end if
		if request("from_date")="" and request("to_date")="" then
			sqlt=sqlt&" and contacttime>='"&date&"'"
		end if
		sql=sql&" and bankcheck=1 and exists(select com_id from ybp_assign where cid=ybp_company.com_id and personid="&request.QueryString("personid")&")"& sqlt
		sear=sear&"&daodan="&request.QueryString("daodan")
		sear=sear&"&from_date="&request.QueryString("from_date")
		sear=sear&"&to_date="&request.QueryString("to_date")
	end if
	if request.QueryString("daodan")="4" then
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
		sql=sql&" and exists(select cid from ybp_tostar where cid=ybp_company.id and personid="&request.QueryString("personid")&" "&sqlt&")"
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
   <form id="form1" name="form1" method="get" action="tj_list.asp"><tr>
    <td align="center" bgcolor="#F2F2F2">
      <input type="hidden" name="4star" id="4star" value="<%=request("4star")%>" />
      <input type="hidden" name="5star" id="5star" value="<%=request("5star")%>" />
      <input type="hidden" name="from_date" id="from_date" value="<%=request("from_date")%>" />
      <input type="hidden" name="to_date" id="to_date"  value="<%=request("to_date")%>" />
      <input type="hidden" name="dotype" id="dotype"  value="<%=request("dotype")%>" />
      <input type="hidden" name="NoCon" id="NoCon"  value="<%=request("NoCon")%>" />
      <input type="hidden" name="gonghai" id="gonghai"  value="<%=request("gonghai")%>" />
      <input type="hidden" name="star" id="star"  value="<%=request("star")%>" />
      <input type="hidden" name="Tomotor" id="Tomotor"  value="<%=request("Tomotor")%>" />
      <input type="hidden" name="Never" id="Never"  value="<%=request("Never")%>" />
      <input type="hidden" name="personid" id="personid"  value="<%=request("personid")%>" />
      店铺名：
<input type="text" name="shop_name" id="shop_name" />
联系方式：
        <input type="text" name="contact" id="contact" />
            
      <input type="submit" name="button" id="button" value=" 搜 索 " /></td>
  </tr> </form>
</table>
<%
	
	if request("shop_name")<>"" then
		sql=sql&" and shop_name like '%"&request("shop_name")&"%'"
		sear=sear&"&shop_name="&request("shop_name")
	end if
	if request("contact")<>"" then
		sql=sql&" and contact like '%"&request("contact")&"%'"
		sear=sear&"&contact="&request("contact")
	end if
	'----------------end
	'----------------begin
	'--------明日安排联系
	if request.QueryString("contacttype")<>"" then
		sql=sql&" and contacttype ="&request.QueryString("contacttype")&""
		sear=sear&"&contacttype="&request.QueryString("contacttype")
	end if
	
	sqlorder="order by id desc"
	ascdesc=request("ascdesc")
	
	sear=sear&"&comporder="&request("comporder")&"&ascdesc="&request("ascdesc")
	
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
		.sltFld  = "*"
		.FROMTbl = "ybp_company"
		.sqlOrder= sqlorder
		.sqlWhere= "WHERE id>0 "&sql
		.keyFld  = "id"    '不可缺少
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
<br />
<table width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#CCCCCC">
<tr>
    
      <td nowrap bgcolor="#f2f2f2">&nbsp;</td>
      <td nowrap bgcolor="#f2f2f2">星级</td>
      <td nowrap bgcolor="#f2f2f2">店铺网址</td>
      <td nowrap bgcolor="#f2f2f2">地区</td>
      <td nowrap bgcolor="#f2f2f2">店铺名</td>
      <td nowrap bgcolor="#f2f2f2">联系方式</td>
      <td nowrap bgcolor="#f2f2f2">旺旺号</td>
      <td align="right" nowrap bgcolor="#f2f2f2">行业分类</td>
      <td align="right" nowrap bgcolor="#f2f2f2">销售额/元</td>
      <td align="right" nowrap bgcolor="#f2f2f2">注册时间</td>
    <td align="right" nowrap bgcolor="#f2f2f2">下次联系时间</td>
      
    <td align="right" nowrap bgcolor="#f2f2f2">最后联系时间</td>
      <td align="right" nowrap bgcolor="#f2f2f2">最后跟进人员</td>
    <td align="right" nowrap bgcolor="#f2f2f2">所有者</td>
  </tr><%
while not rs.eof %>
  
  <tr>
    
                  <td nowrap bgcolor="#FFFFFF"><a href="history.asp?cid=<%=rs("id")%>" target="_blank">日志</a></td>
    <td nowrap bgcolor="#FFFFFF"><%=rs("star")%></td>
    <td nowrap bgcolor="#FFFFFF"><a href="<%=rs("weburl")%>" target="_blank"><%=left(rs("weburl"),15)%>...</a></td>
    <td nowrap bgcolor="#FFFFFF"><%=rs("area")%></td>
    <td nowrap bgcolor="#FFFFFF"><a href="companyshow.asp?cid=<%=rs("id")%>" target="_blank"><%=rs("shop_name")%></a></td>
    <td nowrap bgcolor="#FFFFFF"><%=rs("contact")%></td>
    <td nowrap bgcolor="#FFFFFF"><%=rs("wangwang_no")%></td>
    <td align="right" nowrap bgcolor="#FFFFFF"><%=rs("hy_type")%></td>
    <td align="right" nowrap bgcolor="#FFFFFF"><%=rs("income")%></td>
    <td align="right" nowrap bgcolor="#FFFFFF"><%=rs("regtime")%></td>
    <td align="right" nowrap bgcolor="#FFFFFF"><%=rs("interviewTime")%></td>
                  <td align="right" nowrap bgcolor="#FFFFFF">
                    <%
				  sqlt="select top 1 fdate from ybp_tel where cid="&rs("id")&" order by id desc"
				  set rst=conn.execute(sqlt)
				  if not rst.eof or not rst.bof then
				  	response.Write(rst(0))
				  end if
				  rst.close
				  set rst=nothing
				  %>
    </td>
                  <td align="right" nowrap bgcolor="#FFFFFF">
                  <%
				  if rs("personid")<>"" then
				  sqlu="select realname from users where id="&rs("personid")&""
				  set rsu=conn.execute(sqlu)
				  if not rsu.eof or not rsu.bof then
				  	response.Write(rsu(0))
				  end if
				  rsu.close
				  set rsu=nothing
				  end if
				  %>
    </td>
    <td align="right" nowrap bgcolor="#FFFFFF">
                    <%
				  sqlp="select personid from ybp_assign where cid="&rs("id")&""
				  set rsp=conn.execute(sqlp)
				  if not rsp.eof or not rsp.bof then
					  sqlu="select realname from users where id="&rsp("personid")&""
					  set rsu=conn.execute(sqlu)
					  if not rsu.eof or not rsu.bof then
						response.Write(rsu(0))
					  end if
					  rsu.close
					  set rsu=nothing
				  end if
				  rsp.close
				  set rsp=nothing
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

</body>
</html>
