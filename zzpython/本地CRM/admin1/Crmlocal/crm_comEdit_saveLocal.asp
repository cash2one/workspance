<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../include/adfsfs!@#.asp" -->
<!-- #include file="../../cn/function.asp" -->
<%
		idprod=trim(request.Form("idprod"))
		frompage=request("frompage")
		frompagequrstr=request("frompagequrstr")
        newcomemail=trim(request.form("newemail"))
		comemail=trim(request.form("cemail"))
		comname=replacequot(trim(request.form("cname")))
		comadd=replacequot(trim(request.form("cadd")))
		comzip=trim(request.form("czip")) 
		'comprovince=replacequot(trim(request.form("cprovince")))
		comprovince=replacequot(trim(request.form("province"))&"|"&trim(request.form("city")))
		
		comcountry=trim(request.form("countryselect")) 
		comtel=trim(request.form("ctel"))
		commobile=trim(request.form("cmobile"))
		comfax=trim(request.form("cfax")) 
		comcontactp=replacequot(trim(request.form("ccontactp")))
		comdesi=replacequot(trim(request.form("cdesi")))
		comstation=replacequot(trim(request.form("cstation")))
		comwebsite=trim(request.form("cweb"))
		comkind=request.form("ckind")
		comintro=request.form("cintroduce")
		comintro=replacequot(comintro)
		cproductslist=replacequot(trim(request.form("cproductslist")))
		cproductslist_en=replacequot(trim(request.form("cproductslist_en")))
		comsubname=request.Form("com_subname")
		com_id=idprod
if request("re")<>"" then
		'*************判断二级域名重复
	if trim(request("com_subname"))<>trim(request("com_subname1")) then
		if trim(request("com_subname"))<>"" then
				sqltt="select com_email from comp_info where com_subname='"&trim(request("com_subname"))&"'"
				set rstt=conn.execute(sqltt)
				if not rstt.eof then
					response.Write("<script>alert('二级域名重复！')</script>")
					response.Write("<script>window.history.back(1)</script>")
					response.End()
					rstt.close
					set rstt=nothing
				end if
		end if
	end if
		'********************
				'**********************修改comp_info
				
						sqlcom="select * from comp_info where com_id="&idprod&""
						set rscc=server.CreateObject("ADODB.recordset")
						rscc.open sqlcom,conn,1,3
							if rscc.eof then
							rscc.addnew
							end if
								rscc("com_name")=comname
								rscc("com_add")=comadd
								rscc("com_zip")=comzip
							rscc("com_province")=comprovince
							rscc("com_ctr_id")=comcountry
								rscc("com_tel")=comtel
								rscc("com_mobile")=commobile
								rscc("com_fax")=comfax
								rscc("com_contactperson")=comcontactp
								rscc("com_desi")=comdesi
								rscc("com_website")=comwebsite
								rscc("com_kind")=comkind
								rscc("com_intro")=comintro
								rscc("com_productslist")=cproductslist
								rscc("com_productslist_en")=cproductslist_en
								rscc("com_subname")=comsubname
							rscc("editdate")=now()
							rscc.update()
							rscc.close
							set rscc=nothing
							
							sqlcom="select * from Crm_Temp_SalesComp where com_id="&idprod&""
						set rscc=server.CreateObject("ADODB.recordset")
						rscc.open sqlcom,conn,1,3
							if not rscc.eof then
								rscc("com_name")=comname
								rscc("com_add")=comadd
								rscc("com_zip")=comzip
							    rscc("com_province")=comprovince
							    rscc("com_ctr_id")=comcountry
								rscc("com_tel")=comtel
								rscc("com_mobile")=commobile
								rscc("com_fax")=comfax
								rscc("com_contactperson")=comcontactp
								rscc("com_desi")=comdesi
								rscc("com_website")=comwebsite
								rscc("com_kind")=comkind
								rscc("com_intro")=comintro
								'rscc("com_productslist")=cproductslist
								rscc("com_productslist_en")=cproductslist_en
								rscc("com_subname")=comsubname
							    rscc("editdate")=now()
							    rscc.update()
							end if
							rscc.close
							set rscc=nothing
							
							'------------------------------------------------------	        
			'&&保存省市
	            arrprivince=split(comprovince,"|")
				response.Write(comprovince)
				if ubound(arrprivince)=1 then
						province=request.Form("province1")
						city=request.Form("city1")
						if city="" then city=0
						Garden=request.Form("Garden")
						if Garden="" then Garden=0
						sqle="delete from comp_provinceID where com_id="&com_id
					    conn.execute(sqle)
						sqlm="select com_id from comp_provinceID where com_id="&com_id
						set rsm=conn.execute(sqlm)
						if rsm.eof or rsm.bof then
							sqle="insert into comp_provinceID(com_id,province,city,Garden) values("&com_id&",'"&province&"','"&city&"','"&Garden&"')"
							conn.execute(sqle)
						else
							sqle="update comp_provinceID set province='"&province&"',city='"&city&"',Garden='"&Garden&"' where com_id="&com_id
							conn.execute(sqle)
						end if
				else
					sqle="delete from comp_provinceID where com_id="&com_id
					conn.execute(sqle)
				end if
	      '&保存省市 
		  '------------------------------------------------------------
		  '***************主营方向
			if request("salesType")<>"" then
				sqlreg="select com_id from comp_salestype where com_id="&com_id
				set rsreg=conn.execute(sqlreg)
				if rsreg.eof or rsreg.bof then
					sqlinreg="insert into comp_salestype(com_id,salestype,salestext,buytext) values("&com_id&",'"&request.Form("salestype")&"','"&replacequot(request.Form("salestext"))&"','"&replacequot(request.Form("buytext"))&"')"
					conn.execute(sqlinreg)
				else
					sqlinreg="update comp_salestype set salestype='"&request.Form("salestype")&"',salestext='"&replacequot(request.Form("salestext"))&"',buytext='"&replacequot(request.Form("buytext"))&"',editTime='"&now&"' where com_id="&com_id&""
					conn.execute(sqlinreg)
				end if
				response.Write(sqlinreg)
				rsreg.close
				set rsreg=nothing
			end if
		  '---------------客户接触情况
		  CMeet1=request.Form("CMeet1")
			CMeet2=request.Form("CMeet2")
			CMeet3=request.Form("CMeet3")
			CMeet4=request.Form("CMeet4")
			CMeet5=request.Form("CMeet5")
			CMeet6=request.Form("CMeet6")
			CMeet7=request.Form("CMeet7")
			
			if CMeet1="" then CMeet1="*"
			if CMeet2="" then CMeet2="*"
			if CMeet3="" then CMeet3="*"
			if CMeet4="" then CMeet4="*"
			if CMeet5="" then CMeet5="*"
			if CMeet6="" then CMeet6="*"
			if CMeet7="" then CMeet7="*"
			
			CMeet=CMeet1&CMeet2&CMeet3&CMeet4&CMeet5&CMeet6&CMeet7
				
		  sql="select * from comp_salestype where com_id="&com_id
		  set rs=conn.execute(sql)
		  if not rs.eof or not rs.bof then
		  	sqlt="update comp_salestype set cmeet='"&CMeet&"' where com_id="&com_id
			conn.execute(sqlt)
		  else
		  	sqlt="insert into comp_salestype(com_id,cmeet) values("&com_id&",'"&CMeet&"')"
			conn.execute(sqlt)
		  end if
		  rs.close
		  set rs=nothing
			topage=frompage
			if frompagequrstr<>"" then topage=topage&"?"&replace(frompagequrstr,"~amp~","&")
			response.write "<script language='javascript'>parent.document.getElementById('form1').action='http://www.zz91.com/admin1/compinfo/crm_comedit_re.asp?frompage="&frompage&""&frompagequrstr&"';parent.document.getElementById('form1').target='';parent.document.getElementById('form1').submit()</script>"'从postcheck.asp 引入的
			response.end
end if
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>www.RecycleChina.com</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
</head>
<body>

</body>
</html><%endConnection()%>
