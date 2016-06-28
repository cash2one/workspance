<!-- #include file="../include/adfsfs!@#.asp" -->
<!-- #include file="../../cn/function.asp" -->
<!--#include file="../include/include.asp"-->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>客户申请</title>
<style type="text/css">
<!--
body {
	margin-left: 5px;
	margin-top: 0px;
	margin-right: 5px;
	margin-bottom: 5px;
}
-->
</style>
</head>

<body>
<%

	com_id=request.Form("com_id")
	sid=request.Form("sid")
	personid=request.Form("personid")
	active_flag=request.Form("active_flag")
	'response.End()
	if personid="" or isnull(personid) then personid=session("personid")
	comemail=lcase(trim(request.form("cemail")))
	comname=replacequot(trim(request.form("cname")))
	comadd=replacequot(trim(request.form("cadd")))
	comzip=trim(request.form("czip"))
	savefromflag=request("savefromflag")
	if savefromflag="" then
		'---------一天最多只能申请5个
		sql="select count(0) from crm_Assign_Request where personid="&personid&" and fdate>'"&date()&"' and fdate<'"&date()+1&"'"
		set rs=conn.execute(sql)
		if rs(0)>5 then
			response.Write("一天只能预申请5个客户")
			response.End()
		end if
		rs.close
		set rs=nothing
	end if
	
	if request.Form("countryselect")="1" then
		comprovince=replacequot(trim(request.form("province"))&"|"&trim(request.form("city")))
		comcountry=1
	else
		comprovince=trim(request.form("othercity"))
		comcountry=trim(request.form("ccountry"))
	end if
	
	comtel=trim(request.form("ctel"))
	commobile=trim(request.form("cmobile"))
	comfax=trim(request.form("cfax"))
	comcontactp=replacequot(trim(request.form("ccontactp")))
	comdesi=replacequot(trim(request.form("cdesi")))
	comwebsite=trim(request.form("cweb"))
	comkeywords=request.form("ckeywords")
	comkind=request.form("ckind")
	comintro=trim(request.form("cintroduce"))
	comsendpost=request.form("csendpost")
	comkeywords=replace(comkeywords,", ",",")
     
			sqlcc="select * from comp_info where com_id="&com_id
			set rscc=server.CreateObject("adodb.recordset")
			rscc.open sqlcc,conn,1,3
			if rscc.eof or rscc.bof then
				rscc.addnew()
				rscc("com_id")=com_id
				rscc("com_regtime")=now
				rscc("adminuser")=10000
				exites=0
			else
				exites=1'已经存在
			end if
				if comkind="" then comkind=0
				rscc("com_name")=comname
				rscc("com_add")=comadd
				rscc("com_zip")=comzip
				rscc("com_province")=comprovince
				if comcountry="" then comcountry=1
				rscc("com_ctr_id")=comcountry
				rscc("com_tel")=comtel
				rscc("com_mobile")=commobile
				rscc("com_fax")=comfax
				response.Write(comemail)
				rscc("com_email")=cstr(comemail)
				rscc("com_contactperson")=comcontactp
				rscc("com_desi")=comdesi
				rscc("com_website")=comwebsite
				rscc("com_kind")=comkind
				rscc("com_intro")=ltrim(rtrim(comintro))
				rscc("com_productslist_en")=trim(request.Form("cproductslist_en"))
				if comkeywords="" then comkeywords=""
				rscc("com_keywords")=comkeywords
				
				rscc.update()
				rscc.close
				set rscc=nothing
				'if comcountry="1" then
'					province=request.form("province1")
'					city=request.form("city1")
'					if city="" then city=0
'					Garden=request.form("Garden")
'					if Garden="" then Garden=0
'					sqle="delete from comp_provinceID where com_id="&com_id
'					conn.execute(sqle)
'					sqlm="select com_id from comp_provinceID where com_id="&com_id
'					set rsm=conn.execute(sqlm)
'					if rsm.eof or rsm.bof then
'						sqle="insert into comp_provinceID(id,com_id,province,city,Garden) values(0,"&com_id&",'"&province&"','"&city&"','"&Garden&"')"
'						conn.execute(sqle)
'					else
'						sqle="update comp_provinceID set province='"&province&"',city='"&city&"',Garden='"&Garden&"' where com_id="&com_id
'						conn.execute(sqle)
'					end if
'				end if
        '参加活动客户
		sqlb="select com_id from temp_contactSearch where com_id="&com_id&""
		set rsb=conn.execute(sqlb)
		if rsb.eof or rsb.bof then
			sqlp="insert into temp_contactSearch(com_id,com_tel,com_mobile,searchtext,com_name) values("&com_id&",'"&comtel&"','"&commobile&"','"&comtel&"|"&commobile&"','"&comname&"')"
			conn.execute(sqlp)
		end if
		rsb.close
		set rsb=nothing
		if active_flag<>"" then
			sqla="select * from comp_regfrom1 where com_id="&com_id&""
			set rsa=conn.execute(sqla)
			if rsa.bof or rsa.eof then
				sqlb="insert into comp_regfrom1(com_id,fromname) values("&com_id&",'"&active_flag&"')"
				conn.execute(sqlb)
			end if
			rsa.close
			set rsa=nothing
		end if
		'普通客户添加
		if (request("addtype")="zst" or request("addtype")="zhanhui") and request("yufenpai")="" then
			'sqla="select * from crm_assign where com_id="&request("com_id")
'			set rsa=conn.execute(sqla)
'			if rsa.eof or rsa.bof then
'	       		sqlu="insert into crm_assign(com_id,personid) values("&com_id&","&personid&")"
'		   		conn.execute(sqlu)
'			end if
'			rsa.close
'			set rsa=nothing
			
			sql="insert into crm_InsertCompWeb(com_id,personid,saletype) values("&com_id&","&personid&",1)"
		    conn.execute(sql)

		   '------------写入客户分配记录
			sDetail=request.Cookies("admin_user")&"添加客户"
			sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&personid&",'"&sDetail&"')"
			conn.execute(sqlp)
		end if
		if request("addtype")="sms" and request("yufenpai")="" then
			sqla="select * from crm_assignsms where com_id="&request("com_id")
			set rsa=conn.execute(sqla)
			if rsa.eof or rsa.bof then
	       		sqlu="insert into crm_assignsms(com_id,personid) values("&com_id&","&personid&")"
		   		conn.execute(sqlu)
				sql="insert into crm_InsertCompWeb(com_id,personid,saletype) values("&com_id&","&personid&",2)"
		    	conn.execute(sql)
			end if
			rsa.close
			set rsa=nothing
			'------------写入客户分配记录
			sDetail=request.Cookies("admin_user")&"添加短信客户"
			sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&personid&",'"&sDetail&"')"
			conn.execute(sqlp)
			sql="update sms_subscribe set company_id="&com_id&" where id="&sid&""
			conn.execute(sql)
		end if
		conn.execute("exec zz91_insertsalescomp '"&com_id&"'")
		'------------申请预分配
		if request("yufenpai")<>""  and request("addtype")="zst" then
			sqla="select * from crm_assign where com_id="&request("com_id")
			set rsa=conn.execute(sqla)
			if not rsa.eof or not rsa.bof then
				response.Write("该客户已经在其他人的库里！")
				conn.close
				set conn=nothing
				response.End()
			end if
			rsa.close
			set rsa=nothing
			
			
			
			sql="select com_id from crm_Assign_Request where com_id="&com_id&" and assignflag=0"
			set rs=conn.execute(sql)
			if rs.eof or rs.bof then
				sqla="insert into crm_Assign_Request(com_id,personid) values("&com_id&","&request("personid")&")"
				conn.execute(sqla)
				response.Write("申请成功！")
				rs.close
				set rs=nothing
				'------------写入客户分配记录
		        sDetail=request.Cookies("admin_user")&"申请预分配"
			    sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&personid&",'"&sDetail&"')"
			    conn.execute(sqlp)
				conn.close
				set conn=nothing
				response.End()
			else
				response.Write("该客户已经被其他人申请！")
				rs.close
				set rs=nothing
				conn.close
				set conn=nothing
				response.End()
			end if
		end if
		if request("yufenpai")<>""  and request("addtype")="sms" then
			sqla="select * from crm_assignsms where com_id="&request("com_id")
			set rsa=conn.execute(sqla)
			if not rsa.eof or not rsa.bof then
				response.Write("该客户已经在其他人的库里！")
				conn.close
				set conn=nothing
				response.End()
			else
				sqlu="insert into crm_assignsms(com_id,personid) values("&com_id&","&personid&")"
		   		conn.execute(sqlu)
				sql="insert into crm_InsertCompWeb(com_id,personid,saletype) values("&com_id&","&personid&",2)"
		    	conn.execute(sql)
				'------------写入客户分配记录
				sDetail=request.Cookies("admin_user")&"添加短信客户"
				sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&personid&",'"&sDetail&"')"
				conn.execute(sqlp)
				sql="update sms_subscribe set company_id="&com_id&" where id="&sid&""
				conn.execute(sql)
				response.Write("成功！该客户已经分配到“我的客户表”")
			end if
			rsa.close
			set rsa=nothing
		end if
		if savefromflag="" then
			if request("yufenpai")="" then
				response.Write("成功！需通过审核，该客户才能分配到“我的客户表”")
			end if
		else
			response.Write("成功！已经保存到本地")
		end if
		conn.close
		set conn=nothing
		response.End()
%>
</body>
</html>
