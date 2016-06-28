<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../include/ad!$#5V.asp" -->
<!-- #include file="../localjumptolog.asp" -->
<!-- #include file="../../cn/function.asp" -->
<%
	  idprod=trim(request.Form("idprod"))
	  sqla="select personid from crm_Active_assign where com_id="&idprod
	  set rsa=conn.execute(sqla)
	  if not rsa.eof then
		  if cstr(session("personid"))<>cstr(rsa(0)) then
			if session("userid")<>"10" then
				response.Write("你没有权限修改该信息！<br><a href=###kang onclick='parent.window.close()'>关闭</a>")
				response.End()
			end if
		  end if
	  end if
	  rsa.close
	  set rsa=nothing
        com_id=request.Form("com_id")
		frompage=request("frompage")
		frompagequrstr=request("frompagequrstr")
        newcomemail=trim(request.form("newemail"))
		
		comemail=trim(request.form("cemail"))
		comname=replacequot(trim(request.form("cname")))
		comadd=replacequot(trim(request.form("cadd")))
		comzip=trim(request.form("czip")) 
		city=trim(request.form("city"))
		if city="城市" then
		city=""
		end if
		comprovince=replacequot(trim(request.form("province"))&"|"&city)
		
		comcountry=trim(request.form("ccountry")) 
		comtel=trim(request.form("ctel"))
		commobile=trim(request.form("cmobile"))
		comfax=trim(request.form("cfax")) 
		comcontactp=replacequot(trim(request.form("ccontactp")))
		comdesi=replacequot(trim(request.form("cdesi")))
		comwebsite=trim(request.form("cweb"))
		comkind=request.form("ckind")
		comintro=request.form("cintroduce")
		comintro=replacequot(comintro)
		comkeywords=request.form("ckeywords")
		comkeywords=replace(comkeywords,", ",",")
		cproductslist=replacequot(trim(request.form("cproductslist")))
		cproductslist_en=replacequot(trim(request.form("cproductslist_en")))
		comsubname=request.Form("com_subname")
if request("re")<>"" then
    errfild=""
	resuc=1
	if request("com_rank")="" then
	errfild=errfild&"请选择客户等级<br>"
	response.Write("<script>parent.document.all.crmeidt.style.display=''</script>")
	response.Write("<script>parent.document.all.crmeidt.style.height='100'</script>")
	resuc=0
	end if
		'********************
		if resuc=1 then
				'**********************修改comp_info
						sqlcom="select * from Crm_Temp_SalesComp where com_id="&idprod&""
						set rscc=server.CreateObject("ADODB.recordset")
						rscc.open sqlcom,conn,1,2
						if rscc.eof or rscc.bof then
						rscc.addnew()
						'response.Write(idprod)
						rscc("com_id")=idprod
						end if
							rscc("com_name")=comname
							rscc("com_add")=comadd
							rscc("com_zip")=comzip
							rscc("com_province")=comprovince
							rscc("com_ctr_id")=comcountry
							rscc("com_tel")=comtel
							rscc("com_mobile")=commobile
							rscc("com_fax")=comfax
							rscc("com_email")=newcomemail
							rscc("com_contactperson")=comcontactp
							rscc("com_desi")=comdesi
							rscc("com_website")=comwebsite
							rscc("com_kind")=comkind
							rscc("com_intro")=comintro
							rscc("com_productslist_en")=cproductslist_en
							if comsubname<>"" then
							rscc("com_subname")=comsubname
							end if
							rscc("com_keywords")=comkeywords
							if request.form("crmcheck")<>"" then
							rscc("crmcheck")=request.form("crmcheck")
							end if
							rscc.update()
							rscc.close
							set rscc=nothing
						
							sqlk="update comp_info set com_keywords='"&comkeywords&"' where com_id="&idprod&""
							connserver.execute(sqlk)
								sqldelkey="delete from Comp_ComTrade where com_id="&idprod
								connserver.execute(sqldelkey)
								mya=split(comkeywords,",",-1,1)
									if comkeywords<>"" then
										for i=0 to ubound(mya)
											sql="insert into Comp_ComTrade (com_id,TradeID) values("&idprod&","&trim(mya(i))&")"
											connserver.execute(sql)
										next
									end if
							    comKinds=request("comkindof")
								sqldelkey="delete from Comp_ComKind where com_id="&idprod
								connserver.execute(sqldelkey)
								mycomKinds=split(comKinds,",",-1,1)
								
									if comKinds<>"" then
										for i=0 to ubound(mycomKinds)
											sql="insert into Comp_ComKind (com_id,KindID) values("&idprod&","&trim(mycomKinds(i))&")"
											connserver.execute(sql)
										next
									end if
				'*********************
			if errfild<>"" then
			errcss="failText"
			else
				errfild=errfild&"保存成功！"
				errcss="successText"
				'*****************
			if request.Form("contactflag")="1" then
				sql="select * from Crm_Active_Tel"
				set rs=server.CreateObject("ADODB.recordset")
				rs.open sql,conn,1,2
				rs.addnew()
				rs("com_type")=request.Form("com_type")
				rs("com_rank")=request.Form("com_rank")
				rs("com_kind")=request.Form("com_kind")
				if request.Form("contactnext_time")<>"" then
				rs("contactnext_time")=request.Form("contactnext_time")
				end if
				rs("com_id")=com_id
				rs("teldate")=now
				rs("detail")=request.Form("detail")
				rs("contacttype")=request.Form("contacttype")
				rs("personid")=session("personid")
				'rs("com_sting")=request.Form("com_sting")
				rs("case1")=request.Form("case1")
				rs("case2")=request.Form("case2")
				rs("case3")=request.Form("case3")
				rs.update()
				rs.close
				set rs=nothing
			end if
			
			if request.Form("lxcontactflag")="1" then
				sql="select * from crm_PersonInfo"
				set rs=server.CreateObject("ADODB.recordset")
				rs.open sql,conn,1,2
				rs.addnew()
				rs("PersonName")=request.Form("PersonName")
				rs("PersonSex")=request.Form("PersonSex")
				rs("PersonKey")=request.Form("PersonKey")
				rs("PersonStation")=request.Form("PersonStation")
				rs("com_id")=com_id
				rs("PersonEmail")=request.form("PersonEmail")
				rs("PersonTel")=request.Form("PersonTel")
				rs("PersonFax")=request.Form("PersonFax")
				rs("personid")=session("personid")
				rs("PersonMoblie")=request.Form("PersonMoblie")
				rs("PersonOther")=request.Form("PersonOther")
				rs("PersonBz")=request.Form("PersonBz")
				rs.update()
				rs.close
				set rs=nothing
				response.Write("<script>parent.document.all.personinfoa.src='crm_personinfo.asp?com_id="&com_id&"'</script>")
			end if
				'********************
				sql="select * from Crm_Active_Sales where com_id="&com_id
				set rs=server.CreateObject("ADODB.recordset")
				rs.open sql,conn,1,2
				if rs.eof or rs.bof then
					rs.addnew()
					rs("com_id")=com_id
				end if
					rs("com_rank")=request.Form("com_rank")
					if request.Form("contactnext_time")<>"" then
					rs("contactnext_time")=request.Form("contactnext_time")
					end if
					if request.Form("contacttype")<>"" then
					rs("contacttype")=request.Form("contacttype")
					end if
					rs("LastTelDate")=now
					rs("contactTimes")=request.Form("contactTimes")'联系次数
					rs.update()
					rs.close
					set rs=nothing

			end if
			response.Write("<script>parent.document.all.topt.src='ActiveCrm_tel_comp.asp?com_id="&com_id&"'</script>")
			response.Write("<script>parent.document.all.crmeidt.style.display=''</script>")
			response.Write("<script>parent.document.all.crmeidt.style.height='100'</script>")
		end if
end if
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
<SCRIPT language=javascript src="../sources/pop.js"></SCRIPT>
<SCRIPT language=javascript src="../DatePicker.js"></SCRIPT>
<link href="../datepicker.css" rel="stylesheet" type="text/css">
<link href="../main.css" rel="stylesheet" type="text/css">
<link href="../color.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style></head>

<body>
<a name="top"></a>
<a name="error">
<div id="<%=errcss%>"> <strong> <font color="red"><%=errfild%></font> </strong></div>
</a>
</body>
</html>
<%
connserver.close
set connserver=nothing
conn.close
set conn=nothing
%>