<!-- #include file="../../include/adfsfs!@#.asp" -->
<!-- include file="../localjumptolog.asp" -->
<!-- #include file="../../../cn/function.asp" -->
<!--#include file="../../include/include.asp"-->
<%
	com_id=request.Form("com_id")
	personid=request.Form("personid")
	if personid="" or isnull(personid) then personid=session("personid")
	comemail=lcase(trim(request.form("cemail")))
	comname=replacequot(trim(request.form("cname")))
	comadd=replacequot(trim(request.form("cadd")))
	comzip=trim(request.form("czip"))
	savefromflag=request("savefromflag")
	'personid=request("personid")
	
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
	sid=request.Form("sid")
     
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
			if exites=0 then
				if comkind="" then comkind=0
				rscc("com_name")=comname
				rscc("com_add")=comadd
				rscc("com_zip")=comzip
				rscc("com_province")=comprovince
				rscc("com_ctr_id")=comcountry
				rscc("com_tel")=comtel
				rscc("com_mobile")=commobile
				rscc("com_fax")=comfax
				rscc("com_email")=comemail
				rscc("com_contactperson")=comcontactp
				rscc("com_desi")=comdesi
				rscc("com_website")=comwebsite
				rscc("com_kind")=comkind
				rscc("com_intro")=ltrim(rtrim(comintro))
				rscc("com_productslist_en")=trim(request.Form("cproductslist_en"))
				
				rscc("com_keywords")=comkeywords
				
				rscc.update()
				rscc.close
				set rscc=nothing
			
				if comcountry="1" then
					province=request.form("province1")
					city=request.form("city1")
					if city="" then city=0
					Garden=request.form("Garden")
					if Garden="" then Garden=0
					sqle="delete from comp_provinceID where com_id="&com_id
					conn.execute(sqle)
					sqlm="select com_id from comp_provinceID where com_id="&com_id
					set rsm=conn.execute(sqlm)
					if rsm.eof or rsm.bof then
						sqle="insert into comp_provinceID(id,com_id,province,city,Garden) values(0,"&com_id&",'"&province&"','"&city&"','"&Garden&"')"
						conn.execute(sqle)
					else
						sqle="update comp_provinceID set province='"&province&"',city='"&city&"',Garden='"&Garden&"' where com_id="&com_id
						conn.execute(sqle)
					end if
				end if
				
				sql="insert into crm_InsertCompWeb(com_id,personid,saletype) values("&com_id&","&personid&",5)"
		   		conn.execute(sql)
				conn.execute("exec zz91_insertsalescomp '"&com_id&"'")
		    end if
		
		
	   
	   
		
		
		
		'------------申请分配
		if request("yufenpai")<>"" then
			sqla="select * from crm_assignsms where com_id="&request("com_id")
			set rsa=conn.execute(sqla)
			if not rsa.eof or not rsa.bof then
				response.Write("该客户已经在其他人的库里！")
				conn.close
				set conn=nothing
				response.End()
			end if
			rsa.close
			set rsa=nothing
		end if
	   sqlu="insert into crm_assignsms(com_id,personid) values("&com_id&","&personid&")"
	   conn.execute(sqlu)
	   
	   '------------写入客户分配记录
	   sDetail=request.Cookies("admin_user")&"添加短信客户"
	   sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&personid&",'"&sDetail&"')"
	   conn.execute(sqlp)
	   sql="update sms_subscribe set company_id="&com_id&" where id="&sid&""
	   
	   conn.execute(sql)
	   
		response.Write("<script>alert('"&sql&"成功！需通过审核，该客户才能分配到“我的客户表短信客户库里”');parent.window.close()</script>")
		conn.close
		set conn=nothing
		response.End()
%>
