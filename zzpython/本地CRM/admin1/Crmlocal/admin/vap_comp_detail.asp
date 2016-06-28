<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!-- #include file="../../../cn/function.asp" -->
<!-- #include file="../../include/include.asp" -->
<%
personid=request.QueryString("personid")
fromdate=request.QueryString("fromdate")
todate=request.QueryString("todate")
dotype=request.QueryString("dotype")
Never=request.QueryString("Never")
sqla=""
sqlb=""
comtype=request.QueryString("type")
sqlu="select realname from users where id="&personid&""
set rsu=conn.execute(sqlu)
if not rsu.eof or not rsu.bof then
	realname=rsu(0)
end if
rsu.close
set rsu=nothing
'-------大客户
sql1="select count(0) from v_salescomp where vpersonid="&personid&" and exists(select com_id from crm_vap_complist where v_salescomp.com_id=com_id) and exists(select com_id from comp_vapinfo where v_salescomp.com_id=com_id) and (exists(select com_id from comp_payinfo where v_salescomp.com_id=com_id and money>=8000)) and not EXISTS(select com_id from crm_notBussiness where com_id=v_salescomp.com_id)"

sqla=sqla&" and not EXISTS(select null from comp_salesVap where (com_type=13) and com_id=v_salescomp.com_id) "
if Never="1" then
	sqla=sqla&" and not EXISTS (select null from comp_tel where  com_id=v_salescomp.com_id and telflag=4)"
end if
'--------安排但未联系
if request.QueryString("NoCon")<>"" then
	sqla=sqla&" and vcontactnext_time<'"&date()&"' and vcontactnext_time>'1900-1-1'"
end if

'--------明日安排联系
if request.QueryString("Tomotor")<>"" then
	sqla=sqla&" and vcontactnext_time >='"&date()+1&"' and vcontactnext_time<='"&date()+2&"'"
end if
'----------公海挑入未联系
if request.QueryString("gonghai")<>"" then
	sqla=sqla&" and EXISTS(select com_id from v_groupcomid where telid in (select id from comp_tel where personid<>"&request.QueryString("personid")&") and com_id=v_salescomp.com_id)"
end if
	
if request.QueryString("star")<>"" then
	sqla=sqla&" and vcom_rank="&request.QueryString("star")&" "
end if

if request.QueryString("personid")<>"" then
	sqla=sqla&" and vpersonid="&request.QueryString("personid")&" "
end if


sql=sql1&" "&sqla&""
set rs=conn.execute(sql)
dakehucount=rs(0)

'-----小广告
sql1="select count(0) from v_salescomp where vpersonid="&personid&" and (exists(select com_id from comp_payinfo where v_salescomp.com_id=com_id and money<2000))"
sql=sql1&" "&sqla&""
set rs=conn.execute(sql)
xiaoguanggaocount=rs(0)

'-----必杀期
sql1="select count(0) from v_salescomp where vpersonid="&personid&" and exists(select com_id from crm_vap_complist where v_salescomp.com_id=com_id) and exists(select com_id from comp_vapinfo where v_salescomp.com_id=com_id) and (exists(select com_id from AdvKeyWords where v_salescomp.com_id=com_id and DATEDIFF(MM, fromDate, GETDATE()) <= 12 and DATEDIFF(MM, fromDate, GETDATE()) >= 10)) and not EXISTS(select com_id from crm_notBussiness where com_id=v_salescomp.com_id)"
sql2=" and com_id in (select com_id from advkeywords where typeid='11' )"
sql3=" and com_id in (select com_id from advkeywords where typeid='13' )"
sql4=" and com_id in (select com_id from advkeywords where typeid in(18,10,19,23,20,24,12,22,14,15) )"

sql=sql1&" "&sqla&""
set rs=conn.execute(sql)
bishaqi=rs(0)

sql=sql1&sql2&" "&sqla&""
set rs=conn.execute(sql)
bishaqi1=rs(0)

sql=sql1&sql3&" "&sqla&""
set rs=conn.execute(sql)
bishaqi2=rs(0)

sql=sql1&sql4&" "&sqla&""
set rs=conn.execute(sql)
bishaqi3=rs(0)

'-----黄金客户
sql1="select count(0) from v_salescomp where vpersonid="&personid&" and exists(select com_id from crm_vap_complist where v_salescomp.com_id=com_id) and exists(select com_id from comp_vapinfo where v_salescomp.com_id=com_id) and (exists(select com_id from AdvKeyWords where v_salescomp.com_id=com_id and DATEDIFF(MM, fromDate, GETDATE()) <= 9 and DATEDIFF(MM, fromDate, GETDATE()) >= 2)) and (exists(select com_id from AdvKeyWords where v_salescomp.com_id=com_id and DATEDIFF(MM, fromDate, GETDATE()) <= 9 and DATEDIFF(MM, fromDate, GETDATE()) >= 2)) and not EXISTS(select com_id from crm_notBussiness where com_id=v_salescomp.com_id)"
sql=sql1&" "&sqla&""
set rs=conn.execute(sql)
huangjin=rs(0)

sql=sql1&sql2&" "&sqla&""
set rs=conn.execute(sql)
huangjin1=rs(0)

sql=sql1&sql3&" "&sqla&""
set rs=conn.execute(sql)
huangjin2=rs(0)

sql=sql1&sql4&" "&sqla&""
set rs=conn.execute(sql)
huangjin3=rs(0)

'-----新客户
sql1="select count(0) from v_salescomp where vpersonid="&personid&" and exists(select com_id from crm_vap_complist where v_salescomp.com_id=com_id) and exists(select com_id from comp_vapinfo where v_salescomp.com_id=com_id) and (exists(select com_id from AdvKeyWords where v_salescomp.com_id=com_id and DATEDIFF(MM, fromDate, GETDATE()) <= 1 and DATEDIFF(MM, fromDate, GETDATE()) >= 0)) and not EXISTS(select com_id from crm_notBussiness where com_id=v_salescomp.com_id) "
sql=sql1&" "&sqla&""
set rs=conn.execute(sql)
newcustomer=rs(0)

sql=sql1&sql2&" "&sqla&""
set rs=conn.execute(sql)
newcustomer1=rs(0)

sql=sql1&sql3&" "&sqla&""
set rs=conn.execute(sql)
newcustomer2=rs(0)

sql=sql1&sql4&" "&sqla&""
set rs=conn.execute(sql)
newcustomer3=rs(0)

'-----过期客户
sql1="select count(0) from v_salescomp where vpersonid="&personid&" and exists(select com_id from crm_vap_complist where v_salescomp.com_id=com_id) and exists(select com_id from comp_vapinfo where v_salescomp.com_id=com_id) and (exists(select com_id from AdvKeyWords where v_salescomp.com_id=com_id and DATEDIFF(DD, toDate, GETDATE()) > 0 )) and not EXISTS(select com_id from crm_notBussiness where com_id=v_salescomp.com_id)"
sql=sql1&" "&sqla&""
set rs=conn.execute(sql)
guoqicomp=rs(0)

sql=sql1&sql2&" "&sqla&""
set rs=conn.execute(sql)
guoqicomp1=rs(0)

sql=sql1&sql3&" "&sqla&""
set rs=conn.execute(sql)
guoqicomp2=rs(0)

sql=sql1&sql4&" "&sqla&""
set rs=conn.execute(sql)
guoqicomp3=rs(0)

frompagequrstr=Request.ServerVariables("QUERY_STRING")

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>vap客户统计详细信息</title>
<style type="text/css">
<!--
td {
	font-size: 12px;
	line-height: 22px;
}
-->
</style>
</head>

<body>
<table border="0" cellspacing="1" cellpadding="5" bgcolor="#666666">
  <tr>
    <td colspan="6" bgcolor="#f2f2f2" style="font-weight:bold">客户数量统计</td>
  </tr>
  <tr>
    <td colspan="6" bgcolor="#FFFFFF" style="font-weight:bold">销售：<%=realname%> 客户星级：<%=request.QueryString("star")%></td>
  </tr>
  <tr>
    <td width="80" bgcolor="#FFFFFF">大广告</td>
    <td colspan="5" bgcolor="#FFFFFF"><a href="admin_tj_comp.asp?<%=frompagequrstr%>&lmaction=ad_dakehu" target=_blank><%=dakehucount%></a></td>
  </tr>
  <tr>
    <td rowspan="5" bgcolor="#FFFFFF">主打广告</td>
    <td bgcolor="#FFFFFF">&nbsp;</td>
    <td bgcolor="#FFFFFF">全部</td>
    <td bgcolor="#FFFFFF">黄展</td>
    <td bgcolor="#FFFFFF">标王</td>
    <td width="100" bgcolor="#FFFFFF">独家</td>
  </tr>
  <tr>
    <td width="80" bgcolor="#FFFFFF">必杀期</td>
    <td width="80" bgcolor="#FFFFFF"><a href="admin_tj_comp.asp?<%=frompagequrstr%>&lmaction=ad_bisha" target=_blank><%=bishaqi%></a></td>
    <td width="80" bgcolor="#FFFFFF"><a href="admin_tj_comp.asp?<%=frompagequrstr%>&lmaction=ad_bisha&action=huangzhan" target=_blank><%=bishaqi1%></a></td>
    <td width="80" bgcolor="#FFFFFF"><a href="admin_tj_comp.asp?<%=frompagequrstr%>&lmaction=ad_bisha&action=biaowang" target=_blank><%=bishaqi2%></a></td>
    <td bgcolor="#FFFFFF"><a href="admin_tj_comp.asp?<%=frompagequrstr%>&lmaction=ad_bisha&action=dujia" target=_blank><%=bishaqi3%></a></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">黄金客户</td>
    <td bgcolor="#FFFFFF"><a href="admin_tj_comp.asp?<%=frompagequrstr%>&lmaction=ad_huangjin" target=_blank><%=huangjin%></a></td>
    <td bgcolor="#FFFFFF"><a href="admin_tj_comp.asp?<%=frompagequrstr%>&lmaction=ad_huangjin&action=huangzhan" target=_blank><%=huangjin1%></a></td>
    <td bgcolor="#FFFFFF"><a href="admin_tj_comp.asp?<%=frompagequrstr%>&lmaction=ad_huangjin&action=biaowang" target=_blank><%=huangjin2%></a></td>
    <td bgcolor="#FFFFFF"><a href="admin_tj_comp.asp?<%=frompagequrstr%>&lmaction=ad_huangjin&action=dujia" target=_blank><%=huangjin3%></a></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">新客户</td>
    <td bgcolor="#FFFFFF"><a href="admin_tj_comp.asp?<%=frompagequrstr%>&lmaction=ad_newcomp" target=_blank><%=newcustomer%></a></td>
    <td bgcolor="#FFFFFF"><a href="admin_tj_comp.asp?<%=frompagequrstr%>&lmaction=ad_newcomp&action=huangzhan" target=_blank><%=newcustomer1%></a></td>
    <td bgcolor="#FFFFFF"><a href="admin_tj_comp.asp?<%=frompagequrstr%>&lmaction=ad_newcomp&action=biaowang" target=_blank><%=newcustomer2%></a></td>
    <td bgcolor="#FFFFFF"><a href="admin_tj_comp.asp?<%=frompagequrstr%>&lmaction=ad_newcomp&action=dujia" target=_blank><%=newcustomer3%></a></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">过期客户</td>
    <td bgcolor="#FFFFFF"><a href="admin_tj_comp.asp?<%=frompagequrstr%>&lmaction=ad_guoqi" target=_blank><%=guoqicomp%></a></td>
    <td bgcolor="#FFFFFF"><a href="admin_tj_comp.asp?<%=frompagequrstr%>&lmaction=ad_guoqi&action=huangzhan" target=_blank><%=guoqicomp1%></a></td>
    <td bgcolor="#FFFFFF"><a href="admin_tj_comp.asp?<%=frompagequrstr%>&lmaction=ad_guoqi&action=biaowang" target=_blank><%=guoqicomp2%></a></td>
    <td bgcolor="#FFFFFF"><a href="admin_tj_comp.asp?<%=frompagequrstr%>&lmaction=ad_guoqi&action=dujia" target=_blank><%=guoqicomp3%></a></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">小广告</td>
    <td colspan="5" bgcolor="#FFFFFF"><a href="admin_tj_comp.asp?<%=frompagequrstr%>&lmaction=ad_xiaoguangao" target=_blank><%=xiaoguanggaocount%></a></td>
  </tr>
</table>
</body>
</html>
<%
conn.close
set conn=nothing
%>