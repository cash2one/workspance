<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- #include file="../include/adfsfs!@#.asp" -->
<!-- #include file="../localjumptolog.asp" -->
<!-- #include file="../../cn/function.asp" -->
<!--#include file="../include/include.asp"-->
<!-- #include file="connlocal.asp" -->
<%
        com_id=request.QueryString("com_id")
		compkind=request.QueryString("compkind")
        sqlc="select com_id,personid from crm_assignweb where com_id="&com_id&""
		set rsc=conn.execute(sqlc)
		if rsc.eof or rsc.bof then
		   sqlu="insert into crm_assignweb(com_id,personid) values("&com_id&","&session("personid")&")"
		   conn.execute(sqlu)
		   
			sql="select * from comp_info where com_id="&com_id&""
			set rs=connserver.execute(sql)
			if not rs.eof then
			
			sqlcc="select * from comp_info where com_id="&rs("com_id")
			set rscc=server.CreateObject("adodb.recordset")
			rscc.open sqlcc,conn,1,3
			if rscc.eof then
			rscc.addnew()
			rscc("com_id")=rs("com_id")
			end if
			
				rscc("com_name")=rs("com_name")
				rscc("com_subname")=rs("com_subname")
				rscc("com_add")=rs("com_add")
				rscc("com_zip")=rs("com_zip")
				rscc("com_province")=rs("com_province")
				rscc("com_ctr_id")=rs("com_ctr_id")
				rscc("com_tel")=rs("com_tel")
				rscc("com_mobile")=rs("com_mobile")
				rscc("com_fax")=rs("com_fax")
				rscc("com_email")=rs("com_email")
				rscc("com_email_back")=rs("com_email_back")
				rscc("com_email_check")=rs("com_email_check")
				rscc("com_contactperson")=rs("com_contactperson")
				rscc("com_desi")=rs("com_desi")
				rscc("com_station")=rs("com_station")
				rscc("com_website")=rs("com_website")
				rscc("com_kind")=rs("com_kind")
				rscc("com_intro")=rs("com_intro")
				rscc("com_productslist")=rs("com_productslist")
				rscc("com_productslist_en")=rs("com_productslist_en")
				rscc("com_regtime")=rs("com_regtime")
				rscc("com_pass")=rs("com_pass")
				rscc("com_SafePass")=rs("com_SafePass")
				rscc("com_safekey")=rs("com_safekey")
				rscc("com_keywords")=rs("com_keywords")
				rscc("vipflag")=rs("vipflag")
				rscc("vip_date")=rs("vip_date")
				rscc("vip_check")=rs("vip_check")
				rscc("vipRequest")=rs("vipRequest")
				rscc("vip_datefrom")=rs("vip_datefrom")
				rscc("vip_dateto")=rs("vip_dateto")
				rscc("viptype")=rs("viptype")
				rscc("agent")=rs("agent")
				rscc("Com_FQR")=rs("Com_FQR")
				rscc("EditDate")=rs("EditDate")
				rscc("DelFlag")=rs("DelFlag")
				rscc("adminuser")=rs("adminuser")
				rscc("sb_cls")=rs("sb_cls")
				rscc.update()
				rscc.close
				set rscc=nothing
		   end if
		   rs.close
		   set rs=nothing
		   sql="insert into crm_InsertCompWeb(com_id,personid,saletype) values("&com_id&","&session("personid")&",2)"
		   conn.execute(sql)
		   arrkind=split(compkind,",")
		   for i=0 to ubound(arrkind)
		   	   sqlc="select * from crm_WebcompKind where com_id="&com_id&" and com_Type="&trim(arrkind(i))&""
			   set rsc=conn.execute(sqlc)
			   if rsc.eof or rsc.bof then
			   	  sqlt="insert into crm_WebcompKind (com_id,com_type) values("&com_id&","&trim(arrkind(i))&")"
				  conn.execute(sqlt)
			   end if
			   rsc.close
			   set rsc=nothing
		   next
		   conn.execute("exec zz91_insertsalescomp '"&com_id&"'")
		   closeconn()
		   response.Write("<script>alert('成功！该客户已经分配到“我的客户表”');window.close()</script>")
		end if
		rsc.close
		set rsc=nothing
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
</head>

<body>
</body>
</html>
