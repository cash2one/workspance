<!-- #include file="../../include/ad!$#5V.asp" -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
</head>

<body>
<%
sql="select count(com_id) from v_salescomp where not EXISTS (select com_id from Agent_ClientCompany where com_id=v_salescomp.com_id) "
sql=sql&" and not exists(select com_id from Crm_Active_CompAll where com_id=v_salescomp.com_id and ActivePublic=0 and not exists (select null from Crm_Active_ActiveComp where com_id=Crm_Active_CompAll.com_id)) "
sql=sql&" and not EXISTS(select com_id from comp_sales where com_type='13' and com_id=v_salescomp.com_id) "
sql=sql&" and not EXISTS(select com_id from crm_InsertCompWeb where com_id=v_salescomp.com_id and saletype=2)" 
sql=sql&" and not EXISTS(select com_id from crm_webPipeiComp where com_id=v_salescomp.com_id ) "
sql=sql&" and not EXISTS(select com_id from crm_OtherComp where com_id=v_salescomp.com_id) "
sql=sql&" and not EXISTS(select com_id from Comp_ZSTinfo where com_id=v_salescomp.com_id) "
sql=sql&" and not EXISTS(select com_id from comp_sales where com_Especial='1' and com_id=v_salescomp.com_id)"
sql=sql&" and not EXISTS(select com_id from comp_logincount)"
sql=sql&" and not EXISTS(select com_id from v_groupcomid where com_id=v_salescomp.com_id) and com_regtime<'2008-1-1'"
sql=sql&" and EXISTS(select null from crm_assign where  com_id=v_salescomp.com_id)"
set rs=conn.execute(sql)
if not rs.eof then
	response.Write(rs(0))
end if
rs.close
set rs=nothing
%>
</body>
</html>
