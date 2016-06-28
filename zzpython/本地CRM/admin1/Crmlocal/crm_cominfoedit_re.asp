<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../include/ad!$#5V.asp" -->
<!-- #include file="../localjumptolog.asp" -->
<!-- #include file="../../cn/function.asp" -->
<!-- #include file="../../record/inc.asp" -->
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
<SCRIPT language=javascript src="../sources/pop.js"></SCRIPT>
<SCRIPT language=javascript src="../DatePicker.js"></SCRIPT>
<SCRIPT language=JavaScript src="../main.js"></SCRIPT>
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
</style>
<%
dotype=request("dotype")


if session("personid")="" then
	response.Write("<script>alert('保存失败！请刷新页面后重新保存。')</script>")
end if
	'----------修改权限判断 begin
	  err1=1
	  err2=0
	  err3=0
	  idprod=trim(request.Form("idprod"))
	  if left(dotype,3)="vap" then
	     sqla="select personid from crm_assignvap where com_id="&idprod
	  elseif left(dotype,3)="sms" then
	  	 sqla="select personid from crm_assignsms where com_id="&idprod
	  else
	  	 sqla="select personid from crm_assign where com_id="&idprod
	  end if
	  set rsa=conn.execute(sqla)
	  if not rsa.eof or not rsa.bof then
		  if cstr(session("personid"))=cstr(rsa(0)) then
			err1=1
		  else
		  	err1=0
		  end if
	  else
		err1=0
	  end if
	  rsa.close
	  set rsa=nothing
	  
	  if left(dotype,3)<>"vap" then
		  sqla="select personid from crm_continue_assign where com_id="&idprod&" "
		  set rsa=conn.execute(sqla)
		  if not rsa.eof or not rsa.bof then	
			  if cstr(session("personid"))<>cstr(rsa(0)) then
					err2=1
			  end if
		  end if
		  rsa.close
		  set rsa=nothing
	  end if
	  

	  
	  if err1=0 then
	  	if session("userid")<>"10" then
			response.Write("你没有权限修改该信息！<br><a href=###kang onclick='parent.window.close()'>关闭</a>")
			response.Write("<script>parent.document.getElementById('crmeidt').style.display=''</script>")
			response.Write("<script>parent.document.getElementById('crmeidt').style.height='100';parent.document.getElementById('submitsave').disabled=false;parent.document.getElementById('submitsave1').disabled=false;parent.document.getElementById('submitsave2').disabled=false;</script>")
			response.End()
		end if
	  end if
	  if err2=1 then
	  	if session("userid")<>"10" then
			response.Write("该客户在其他人的续签库里<br><a href=###kang onclick='parent.window.close()'>关闭</a>")
			response.Write("<script>parent.document.getElementById('crmeidt').style.display=''</script>")
			response.Write("<script>parent.document.getElementById('crmeidt').style.height='100';parent.document.getElementById('submitsave').disabled=false;parent.document.getElementById('submitsave1').disabled=false;parent.document.getElementById('submitsave2').disabled=false;</script>")
			response.End()
		end if
	  end if
	  '----------------idc再生通保留客户
	  if (session("userid")="1307" or session("userid")="1306" or session("userid")="1302" or session("userid")="13" or session("userid")="10") then
		  'if request.Form("idcbaoliu")<>"" then
'			sqlc="select id from temp_baoliucomp where com_id="&idprod&""
'			set rsc=conn.execute(sqlc)
'			if rsc.eof or rsc.bof then
'				sqlb="insert into temp_baoliucomp (com_id,personid) values("&idprod&","&session("personid")&")"
'				conn.execute(sqlb)
'			end if
'			rsc.close
'			set rsc=nothing
'		  else
'			sqlb="delete from temp_baoliucomp where com_id="&idprod&""
'			conn.execute(sqlb)
'		  end if
	  end if
	  
  '-------------修改权限判断 end
        com_id=request.Form("com_id")
		frompage=request("frompage")
		frompagequrstr=request("frompagequrstr")
        newcomemail=trim(request.form("newemail"))
		
		comemail=trim(request.form("cemail"))
		comname=replacequot(trim(request.form("cname")))
		comadd=replacequot(trim(request.form("cadd")))
		comzip=trim(request.form("czip")) 
		
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
		comkind=request.form("ckind")
		if comkind="" then comkind=0
		comintro=request.form("cintroduce")
		comintro=replacequot(comintro)
		comkeywords=request.form("ckeywords")
		comkeywords=replace(comkeywords,", ",",")
		if comkeywords="" then comkeywords=0
		cproductslist=replacequot(trim(request.form("cproductslist")))
		cproductslist_en=replacequot(trim(request.form("cproductslist_en")))
		'comsubname=request.Form("com_subname")
		'-------是否保存联系记录标志
		contactflag=request("contactflag")
		contacttype=request("contacttype")
		
		
if request("re")<>"" then
		errfild=""
		resuc=1
		if contactflag="1" then
			if request("com_rank")="" then
				errfild=errfild&"请选择客户等级<br>"
				response.Write("<script>parent.document.getElementById('crmeidt').style.display=''</script>")
				response.Write("<script>parent.document.getElementById('crmeidt').style.height='100'</script>")
				resuc=0
			end if
		end if

		response.Write("<script>parent.document.getElementById('crmeidt').style.display=''</script>"&chr(13))
		response.Write("<script>parent.document.getElementById('crmeidt').style.height='100';parent.document.getElementById('submitsave').disabled=false;parent.document.getElementById('submitsave1').disabled=false;parent.document.getElementById('submitsave2').disabled=false;</script>")
	
	    '-----读取销售人员电话
		recordflag=0
		sqlu="select usertel,recordflag from users where id="&session("personid")&""
		set rsu=conn.execute(sqlu)
		if not rsu.eof or not rsu.bof then
			mytel=rsu(0)
			recordflag=rsu("recordflag")
			if recordflag="" or isnull(recordflag) then recordflag=0
		end if
		rsu.close
		set rsu=nothing
		if mytel="" or isnull(mytel) then mytel=0
		if len(mytel)<8 then
			response.Write("<script>parent.shownottel()</script>"&chr(13))
			response.End()
		end if
		'-------读取客户电话
		function getlytel(lytel)
			
			
			
			'sqltl="select top 1 id from record_list where ((called like '%"&lytel&"%' and caller='"&mytel&"') or (caller like '%"&lytel&"%' and called='"&mytel&"'))"
'			
'			sqltl=sqltl&" and DATEDIFF(mi,startime,'"&now&"')<30 and DATEDIFF(mi,startime,'"&now&"')>0  order by id desc"
'			'response.Write(sqltl)
'			set rstl=conn.execute(sqltl)
'			if not rstl.eof or not rstl.bof then
'				getlytel=rstl("id")
'			else
'				getlytel=""
'			end if
'			rstl.close
'			set rstl=nothing
			getlytel=""
		end function
		
		comtel1=""
		comtel2=""
		comtel3=""
		comtel4=""
		comtelly=""
		if comtel<>"" and not isnull(comtel) then
			comtel=replace(comtel," ","")
			comtel1=right(replace(comtel,"-",""),7)
			if comtel1<>"" then 
			comtel1=left(comtel1,len(comtel1)-1)
			comtelly=getlytel(comtel1)
			else
			comtelly=""
			end if
		end if
		if comtelly="" then
			if commobile<>"" and not isnull(commobile) then
				commobile=replace(commobile," ","")
				comtel2=right(commobile,9)
				comtel2=left(comtel2,len(comtel2)-1)
				comtelly=getlytel(comtel2)
			end if
		end if
		
		if comtelly="" then
			sqlt="select top 1 persontel,PersonMoblie from crm_PersonInfo where com_id="&idprod
			set rst=conn.execute(sqlt)
			if not rst.eof or not rst.bof then
				while not rst.eof
					if trim(rst("persontel"))<>"" and not isnull(rst("persontel")) then
						comtel3=right(replace(trim(rst("persontel")),"-",""),7)
						comtel3=left(comtel3,len(comtel3)-1)
						comtelly=getlytel(comtel3)
					end if
					if comtelly="" then
						if trim(rst("PersonMoblie"))<>"" and not isnull(rst("PersonMoblie")) then
							comtel4=right(trim(rst("PersonMoblie")),9)
							comtel4=left(comtel4,len(comtel4)-1)
							comtelly=getlytel(comtel4)
						end if
					end if
				rst.movenext
				wend
			end if
			rst.close
			set rst=nothing
		end if
		if comtelly<>"" and session("userid")="10" then
			response.Write("<script>parent.showgltel("&comtelly&","&idprod&")</script>"&chr(13))
			'response.End()
		end if
	 '-------客户电话与录音电话的判断/begin
	 if request.Form("contactflag")="1" and contacttype="13" and request.Form("recordflag")="0" and cstr(recordflag)="1" then  
		
		
		
		sqlteltemp=""
		
		if comtelly<>"" then
			sqlteltemp=sqlteltemp&" id="&comtelly&" "
		end if
		if sqlteltemp<>"" then
			'---是CRM里的电话
			notel=0
			
			'--------------判断销售的电话是否已经修改，如没有修改自动修改
			sqlmytel="select caller from record_list where ("&sqlteltemp&")"
			set rsmytel=conn.execute(sqlmytel)
			if not rsmytel.eof or not rsmytel.bof then
				recordmytel=rsmytel(0)
				if len(recordmytel)=8 then
					if cstr(mytel)<>cstr(recordmytel) then
						response.Write("<script>parent.shownottel()</script>"&chr(13))
						'conn.execute("update users set usertel='"&recordmytel&"',recordflag=1 where id="&session("personid")&"")
						'mytel=recordmytel
					end if
				end if
			end if
			rsmytel.close
			set rsmytel=nothing
			'--------------end
		else
			'---不是CRM里的电话
			response.Write("<script>parent.showkhtel("&idprod&",'"&comtel&" "&commobile&"')</script>"&chr(13))
			'response.End()
			notel=1
		end if
	end if
	'-------客户电话与录音电话的判断/end
		'---------------------------------
		'********************
		'开始保存 begin
		if resuc=1 then
				'**********************修改comp_info
						sqlcom="select * from Crm_Temp_SalesComp where com_id="&idprod&""
						set rscc=server.CreateObject("ADODB.recordset")
						rscc.open sqlcom,conn,1,3
						if rscc.eof or rscc.bof then
							rscc.addnew()
							rscc("com_id")=idprod
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
							'if comsubname<>"" then
								'rscc("com_subname")=comsubname
							'end if
							rscc("com_keywords")=comkeywords
						else
							modflag=0
							if rscc("com_name")<>comname then
								rscc("com_name")=comname
								modflag=1
							end if
							if cstr(trim(rscc("com_add")))<>cstr(trim(comadd)) then
								rscc("com_add")=comadd
								modflag=1
								
							end if
							if rscc("com_zip")<>comzip then
								rscc("com_zip")=comzip
								modflag=1
							end if
							
							'if rscc("com_province")<>comprovince then
								rscc("com_province")=comprovince
								modflag=1
							'end if
							
							'if rscc("com_ctr_id")<>comcountry then
								rscc("com_ctr_id")=comcountry
								modflag=1
							'end if
							
							if rscc("com_tel")<>comtel then
								rscc("com_tel")=comtel
								modflag=1
							end if
							if rscc("com_mobile")<>commobile then
								rscc("com_mobile")=commobile
								modflag=1
							end if
							
							if rscc("com_fax")<>comfax then
								rscc("com_fax")=comfax
								modflag=1
							end if
							if session("userid")="10" then
								rscc("com_email")=newcomemail
							end if
							if rscc("com_contactperson")<>comcontactp then
								rscc("com_contactperson")=comcontactp
								modflag=1
							end if
							if rscc("com_desi")<>comdesi then
								rscc("com_desi")=comdesi
								modflag=1
							end if
							if rscc("com_website")<>comwebsite then
								rscc("com_website")=comwebsite
								modflag=1
							end if
							
							if cstr(rscc("com_kind"))<>cstr(comkind) then
								rscc("com_kind")=comkind
								modflag=1
							end if
							'response.Write(modflag&rscc("com_kind")&"<br>"&comkind)
							if rscc("com_intro")<>comintro then
								rscc("com_intro")=comintro
								modflag=1
							end if
							if rscc("com_productslist_en")<>cproductslist_en then
								rscc("com_productslist_en")=cproductslist_en
								modflag=1
							end if
							'if comsubname<>"" and rscc("com_subname")<>comsubname then
								'rscc("com_subname")=comsubname
								'modflag=1
							'end if
							if rscc("com_keywords")<>comkeywords then 
								rscc("com_keywords")=comkeywords
								modflag=1
							end if
							if request.form("crmcheck")<>"" and modflag=1 then
								rscc("crmcheck")=request.form("crmcheck")
							end if
							if modflag=1 and request.form("crmcheck")<>"" then
							    rscc("fcheck")=0
							end if
							rscc("flag")=1
						end if
							rscc.update()
							rscc.close
							set rscc=nothing
					'------参加过的活动
					active_flag=request.Form("active_flag")
					if active_flag<>"" then
						sqla="select * from comp_regfrom1 where com_id="&com_id&" and fromname='"&active_flag&"'"
						set rsa=conn.execute(sqla)
						if rsa.bof or rsa.eof then
							sqlb="insert into comp_regfrom1(com_id,fromname) values("&com_id&",'"&active_flag&"')"
							conn.execute(sqlb)
						end if
						rsa.close
						set rsa=nothing
					end if
					'-----------------------------------------------------
					
					    'sqlcom="select * from comp_info where com_id="&idprod&""
'						set rscc=server.CreateObject("ADODB.recordset")
'						rscc.open sqlcom,conn,1,3
'						if rscc.eof or rscc.bof then
'							rscc.addnew()
'							rscc("com_id")=idprod
'							rscc("com_name")=comname
'							rscc("com_add")=comadd
'							rscc("com_zip")=comzip
'							rscc("com_province")=comprovince
'							rscc("com_ctr_id")=comcountry
'							rscc("com_tel")=comtel
'							rscc("com_mobile")=commobile
'							rscc("com_fax")=comfax
'							rscc("com_email")=newcomemail
'							rscc("com_contactperson")=comcontactp
'							rscc("com_desi")=comdesi
'							rscc("com_website")=comwebsite
'							rscc("com_kind")=comkind
'							rscc("com_intro")=comintro
'							rscc("com_productslist_en")=cproductslist_en
'							if comsubname<>"" then
'								rscc("com_subname")=comsubname
'							end if
'							rscc("com_keywords")=comkeywords
'						else
'							modflag=0
'							if rscc("com_name")<>comname then
'								rscc("com_name")=comname
'								modflag=1
'							end if
'							if cstr(trim(rscc("com_add")))<>cstr(trim(comadd)) then
'								rscc("com_add")=comadd
'								modflag=1
'								
'							end if
'							if rscc("com_zip")<>comzip then
'								rscc("com_zip")=comzip
'								modflag=1
'							end if
'							
'							if rscc("com_province")<>comprovince then
'								rscc("com_province")=comprovince
'								modflag=1
'							end if
'							
'							if rscc("com_ctr_id")<>comcountry then
'								rscc("com_ctr_id")=comcountry
'								modflag=1
'							end if
'							
'							if rscc("com_tel")<>comtel then
'								rscc("com_tel")=comtel
'								modflag=1
'							end if
'							if rscc("com_mobile")<>commobile then
'								rscc("com_mobile")=commobile
'								modflag=1
'							end if
'							
'							if rscc("com_fax")<>comfax then
'								rscc("com_fax")=comfax
'								modflag=1
'							end if
'							if session("userid")="10" then
'								rscc("com_email")=newcomemail
'							end if
'							if rscc("com_contactperson")<>comcontactp then
'								rscc("com_contactperson")=comcontactp
'								modflag=1
'							end if
'							if rscc("com_desi")<>comdesi then
'								rscc("com_desi")=comdesi
'								modflag=1
'							end if
'							if rscc("com_website")<>comwebsite then
'								rscc("com_website")=comwebsite
'								modflag=1
'							end if
'							
'							if cstr(rscc("com_kind"))<>cstr(comkind) then
'								rscc("com_kind")=comkind
'								modflag=1
'							end if
'							if rscc("com_intro")<>comintro then
'								rscc("com_intro")=comintro
'								modflag=1
'							end if
'							if rscc("com_productslist_en")<>cproductslist_en then
'								rscc("com_productslist_en")=cproductslist_en
'								modflag=1
'							end if
'							if comsubname<>"" and rscc("com_subname")<>comsubname then
'								rscc("com_subname")=comsubname
'								modflag=1
'							end if
'							if rscc("com_keywords")<>comkeywords then 
'								rscc("com_keywords")=comkeywords
'								modflag=1
'							end if
'						end if
'							rscc.update()
'							rscc.close
'							set rscc=nothing
							'---------------------------------
						if session("userid")="10" then
							'sql="update temp_salescomp set com_email='"&newcomemail&"' where com_id="&idprod&""
'							conn.execute(sql)
'							sql="update comp_info set com_email='"&newcomemail&"' where com_id="&idprod&""
'							conn.execute(sql)
						end if

				'*********************
			if errfild<>"" then
				errcss="failText"
			else
				errfild=errfild&"保存成功！"
				errcss="successText"
				'-----------公海除杂
				gonghai_out=request.Form("gonghai_out")
				
				sqli="delete from crm_category_info where property_id='"&com_id&"' and property_value like '1004____' and property_value not in (select code from crm_category where code like '1004____' and closeflag=1)"
				conn.execute(sqli)
				
				if gonghai_out<>"" then
					arrgonghai_out=split(gonghai_out,",")
					for a=0 to ubound(arrgonghai_out)
						itemgonghai_out=trim(arrgonghai_out(a))
						sqlp="select * from crm_category_info where property_id='"&com_id&"' and property_value='"&itemgonghai_out&"'"
						set rsp=conn.execute(sqlp)
						if rsp.eof or rsp.bof then
							sqli="insert into crm_category_info(property_id,property_value) values('"&com_id&"','"&itemgonghai_out&"')"
							conn.execute(sqli)
						else
							
						end if
						rsp.close
						set rsp=nothing
					next
				end if
				
				'-----------客户分类
				zdycomp=request.Form("zdycomp")
				usertype=request.Form("usertype")
				if usertype="" then usertype="1"
				sqli="delete from crm_category_info where property_id='"&com_id&"' and left(property_value,4)='1005' and type="&usertype&" and property_value not in (select code from crm_category where left(code,4)='1005' and closeflag=1 and type="&usertype&")"
				conn.execute(sqli)
				
				if zdycomp<>"" then
					arrgonghai_out=split(zdycomp,",")
					for a=0 to ubound(arrgonghai_out)
						itemgonghai_out=trim(arrgonghai_out(a))
						sqlp="select * from crm_category_info where property_id='"&com_id&"' and property_value='"&itemgonghai_out&"' and type="&usertype&""
						set rsp=conn.execute(sqlp)
						if rsp.eof or rsp.bof then
							sqli="insert into crm_category_info(property_id,property_value,type) values('"&com_id&"','"&itemgonghai_out&"',"&usertype&")"
							conn.execute(sqli)
						else
							
						end if
						rsp.close
						set rsp=nothing
					next
				end if
				'*****************
			    '---------------基本信息保存
			    '------------------begin
			    sqlc="select * from Crm_CompOtherInfo where com_id="&com_id
				set rsc=server.CreateObject("ADODB.recordset")
				rsc.open sqlc,conn,1,3
				if rsc.eof or rsc.bof then
					rsc.addnew()
					rsc("com_id")=com_id
				end if
				rsc("Com_zyyw")=request.Form("Com_zyyw")
				rsc("com_mysl")=request.Form("com_mysl")
				rsc("com_Area")=request.Form("com_Area")
				rsc("com_jyfs")=request.Form("com_jyfs")
				rsc("com_wlyx")=request.Form("com_wlyx")
				rsc.update()
				rsc.close
				set rsc=nothing
			'------------------end
			'囤货信息保存
				'if request.Form("storeCate")<>"" then
'					sqlc="select * from Crm_CompStore where com_id="&com_id
'					set rsc=server.CreateObject("ADODB.recordset")
'					rsc.open sqlc,conn,1,3
'					'if rsc.eof or rsc.bof then
'					rsc.addnew()
'					rsc("com_id")=com_id
'					rsc("personid")=session("personid")
'					'end if
'					rsc("storeCate")=request.Form("storeCate")
'					rsc("storeDesc")=request.Form("storeDesc")
'					rsc("storePrice")=request.Form("storePrice")
'					rsc("storeCount")=request.Form("storeCount")
'					rsc("flag")=1
'					rsc.update()
'					rsc.close
'					set rsc=nothing
'				end if
			'囤货结束
			'------------------end
			'保存归类
				'if request.Form("cate_compCate")<>"" then
'					sqlc="select * from cate_compCate where com_id="&com_id
'					set rsc=server.CreateObject("ADODB.recordset")
'					rsc.open sqlc,conn,1,3
'					if rsc.eof or rsc.bof then
'						rsc.addnew()
'						rsc("com_id")=com_id
'					end if
'					rsc("cateID")=request.Form("cate_compCate")
'					
'					rsc.update()
'					rsc.close
'					set rsc=nothing
'					
'				end if
				'保存结束
				'-----------
				
				'保存联系记begin
				if cstr(request.Form("contactflag"))="1" then  
					'---------------vap广告客户客户类型修改
					if request.Form("paystats")<>"0" and request.Form("paystats")<>"" then
						paytype=request.Form("paytype")
						paykind=request.Form("paykind")
						donation=request.Form("donation")
						if donation="" then donation=0
						paytype="3"
						fromdate=request.Form("payfromdate")
						todate=request.Form("paytodate")
						money=request.Form("money")
						bz=request.Form("bz")
						if fromdate="" then fromdate=null
						if todate="" then todate=null
						sqli="insert into comp_payinfo (com_id,paytype,paykind,fromdate,todate,money,bz,donation,islocalsave) values("&com_id&","&paytype&",'"&paykind&"','"&fromdate&"','"&todate&"','"&money&"','"&bz&"',"&donation&",1)"
						conn.execute(sqli)
						if cint(money)>=2000 then
							sqlp="insert into temp_vap_zhuda(com_id) select com_id from comp_payinfo where com_id not in (select com_id from temp_vap_zhuda) and com_id="&com_id&""
							conn.execute(sqlp)
						end if
					end if
					'------------------是否5星明确选项
					strresonlst=""
					if cstr(request.Form("com_rank"))="5" and left(dotype,3)<>"vap" then
						reson_checklist=request.Form("reson_checklist")
						reson_typeid=request.Form("reson_typeid")
						resontel=request.Form("resontel")
						arrreson_checklist=split(reson_checklist,",")
						arrreson_typeid=split(reson_typeid,",")
						arrresontel=split(resontel,",")
						
						for i=0 to ubound(arrreson_checklist)-1
							sql="select * from crm_star5reson where com_id="&com_id&" and reson_typeid='"&trim(arrreson_typeid(i))&"' and personid="&session("personid")&""
							set rs=conn.execute(sql)
							if not rs.eof or not rs.bof then
								sqlpppp="update crm_star5reson set reson_check="&arrreson_checklist(i)&" where com_id="&com_id&" and reson_typeid='"&trim(arrreson_typeid(i))&"' and personid="&session("personid")&""
							else
								sqlpppp="insert into crm_star5reson (com_id,reson_typeid,reson_check,personid) values("&com_id&",'"&trim(arrreson_typeid(i))&"',"&arrreson_checklist(i)&","&session("personid")&")"
							end if
							'------------------
							checkstr=trim(arrreson_checklist(i))
							if checkstr="1" then ppcheckstr="（是）"
							if checkstr="0" then ppcheckstr="（<font color=#ff0000>否</font>）"
							strresonlst=strresonlst&trim(arrresontel(i))&ppcheckstr&"<br>"
							conn.execute(sqlpppp)
						next
						strresonlst=strresonlst&"-------------------------------------------<br>"
						response.Write("<script>//parent.showstar5open("&com_id&");</script>")
					end if
					'-------------记录拖单/毁单
					if cstr(request.Form("com_rankflag"))="5" and left(dotype,3)<>"vap" then
						comp_sale_type=request.Form("comp_sale_type")
						tuohuireson=request.Form("tuohuireson")
						tuohuireson=replace(tuohuireson,"''","""")
						if comp_sale_type="" or isnull(comp_sale_type) then
						else
							sqlppp="insert into crm_tuo_hui_comp(com_id,personid,comp_sale_type,reson) values("&com_id&","&session("personid")&",'"&comp_sale_type&"','"&tuohuireson&"')"
							conn.execute(sqlppp)
						end if
						response.Write(sqlppp)
					end if
					'------------------转四星统计
					if cstr(request.Form("telflag"))<>"4" and cstr(request.Form("telflag"))<>"5" then
						sql="select top 1 com_rank from v_salescomp where com_id="&com_id&" and personid="&session("personid")&" and com_id in (select com_id from comp_tel where com_id="&com_id&" and personid="&session("personid")&" and telflag<4)"
						set rs=conn.execute(sql)
						if not rs.eof and not rs.bof then
							if rs("com_rank")<>"" then
								nowcom_rank=cstr(request.Form("com_rank"))
								if cdbl(rs("com_rank"))<=4 and (nowcom_rank="4" or nowcom_rank="4.1" or nowcom_rank="4.8") then
									sqlc="select * from crm_To4star where com_id="&com_id&" and personid="&session("personid")&" and fdate<'"&date+1&"' and fdate>'"&date&"'"
									set rsc=conn.execute(sqlc)
									if rsc.eof or rsc.bof then
										if cint(rs("com_rank"))<4 then
											sqlp="insert into crm_To4star(com_id,personid,fdate) values("&com_id&","&session("personid")&",'"&now&"')"
											conn.execute(sqlp)
										end if
									end if
									
									rsc.close
									set rsc=nothing
								end if
								'-------------转5星
								if cdbl(rs("com_rank"))<5 and cstr(request.Form("com_rank"))="5" then
									sqlc="select * from crm_To5star where com_id="&com_id&" and personid="&session("personid")&" and fdate<'"&date+1&"' and fdate>'"&date&"'"
									set rsc=conn.execute(sqlc)
									if rsc.eof or rsc.bof then
										if cdbl(rs("com_rank"))<5 then
											sqlp="insert into crm_To5star(com_id,personid,fdate) values("&com_id&","&session("personid")&",'"&now&"')"
											conn.execute(sqlp)
										end if
									end if
									rsc.close
									set rsc=nothing
								end if
							end if
						else
							sqle="select top 1 com_rank from comp_tel where com_id="&com_id&" and telflag<4 order by teldate desc"
							set rse=conn.execute(sqle)
							if not rse.eof or not rse.bof then
								oldcomrank=rse(0)
							else
								oldcomrank=0
							end if
							rse.close
							set rse=nothing
							nowcom_rank=cstr(request.Form("com_rank"))
							if (nowcom_rank="4" or nowcom_rank="4.1" or nowcom_rank="4.8") and cdbl(oldcomrank)<4 then
								sqlp="insert into crm_To4star(com_id,personid,fdate,star) values("&com_id&","&session("personid")&",'"&now&"',"&nowcom_rank&")"
								conn.execute(sqlp)
							end if
							if cstr(request.Form("com_rank"))="5" and cdbl(oldcomrank)<5 then 
								sqlp="insert into crm_To5star(com_id,personid,fdate) values("&com_id&","&session("personid")&",'"&now&"')"
								conn.execute(sqlp)
							end if
						end if
						rs.close
						set rs=nothing
					elseif cstr(request.Form("telflag"))="5" then
						'	sms销售电话
						
						
					else
						
						'-------vap客户转四星统计
						    sqle="select top 1 com_rank from comp_tel where com_id="&com_id&" and telflag=4 order by id desc"
							set rse=conn.execute(sqle)
							if not rse.eof or not rse.bof then
								oldcomrank=rse(0)
							else
								oldcomrank=0
							end if
							rse.close
							set rse=nothing
							if isnull(oldcomrank) then oldcomrank=0
							nowcom_rank=cstr(request.Form("com_rank"))
							if (left(nowcom_rank,1)="4" or left(nowcom_rank,1)="5") and cdbl(left(nowcom_rank,1))>cdbl(left(oldcomrank,1)) then
								sqlc="select * from crm_Tostar_vap where com_id="&com_id&" and personid="&session("personid")&" and fdate<'"&date+1&"' and fdate>'"&date&"' and star="&nowcom_rank&""
								set rsc=conn.execute(sqlc)
								if rsc.eof or rsc.bof then
									sqlp="insert into crm_Tostar_vap(com_id,personid,fdate,star) values("&com_id&","&session("personid")&",'"&now&"',"&nowcom_rank&")"
									conn.execute(sqlp)
								end if
								rsc.close
								set rsc=nothing
							end if
					end if
					'--------------------------------
					'0 新签 1 BD部 2 续签 3 广告 4 vap客户
					'-------------------判断是否做过再生通
					zstflag=0
					sqlzst="select com_id from comp_zstinfo where com_id="&com_id
					set rszst=conn.execute(sqlzst)
					if not rszst.eof or not rszst.bof then
						zstflag=1
					end if
					rszst.close
					set rszst=nothing
					'------------------------------------记录电话记录
					telflag=request.Form("telflag")
					if telflag="" then telflag="0"
					if telflag="2" and zstflag=0 then
						telflag="1"
					end if
					'if telflag="0" or telflag="1" or telflag="2" or telflag="" then
						sql="select * from comp_tel where id is null"
						set rs=server.CreateObject("ADODB.recordset")
						rs.open sql,conn,1,3
						rs.addnew()
						rs("com_type")=request.Form("com_type")
						rs("com_rank")=request.Form("com_rank")
						rs("com_kind")=request.Form("com_kind")
						if request.Form("contactnext_time")<>"" then
							rs("contactnext_time")=request.Form("contactnext_time")
						end if
						rs("com_id")=com_id
						rs("teldate")=now
						rs("detail")=strresonlst&request.Form("detail")
						rs("contacttype")=request.Form("contacttype")
						rs("personid")=session("personid")
						rs("telflag")=telflag
						rs.update()
						rs.close
						set rs=nothing
					'----------------------------------------------
					if contacttype="13" then
						sql="update temp_salescomp set teldate=getdate() where com_id="&com_id
						conn.execute(sql)
						sql="update temp_3dayNocontact set teldate=getdate() where com_id="&com_id
						conn.execute(sql)
						'----联系次数加一
						sql="update temp_telcount set telcount=telcount+1 where com_id="&com_id
						conn.execute(sql)
						
					end if
					sqlg="update crm_assign_gonghai set outflag=1 where com_id="&com_id&""
					conn.execute(sqlg)
					sqlmaxtel="select max(id) from comp_tel where com_id="&com_id
					set rsmax=conn.execute(sqlmaxtel)
					if not rsmax.eof or not rsmax.bof then
						TelID=rsmax(0)
					end if
					rsmax.close
					set rsmax=nothing
					
					'-----记录SEO销售
					if request.Form("seoflag")<>"" then
						sqls="insert into crm_seotel (com_id,telid) values("&com_id&","&telid&")"
						conn.execute(sqls)
					end if
				 '----开始记录录音数据 begin
				 if request.Form("contactflag")="1" and contacttype="13" and request.Form("recordflag")="0" and cstr(recordflag)="1" then  
					'-------读取录音电话
					'and DATEDIFF(minute,endtime,getdate())<30
					if notel=0 then
						RecordNo=""
						
						sqlt="select id,startime,caller,accountcode,answeredtime,called,monitorfile,type from record_list where ("&sqlteltemp&") "
						if mytel<>"" then
							'sqlt=sqlt&" and caller='"&mytel&"' "
						end if
						sqlt=sqlt&" and startime>='"&date&"' order by id desc"
						
						set rst=conn.execute(sqlt)
						if not rst.eof or not rst.bof then
							
							while not rst.eof
								RecordNo=rst("id")
								CallerId=rst("caller")
								CallType=rst("type")
								startTime=rst("startime")
								endTime=""
								recordTime=rst("answeredtime")
								'recordTime=""
								filePath=rst("monitorfile")
								Dtmf=rst("called")
								'-----写入到录音记录表
								if RecordNo<>"" then
									sqle="select id from crm_callrecord where RecordNo='"&RecordNo&"'"
									set rse=conn.execute(sqle)
									if rse.eof or rse.bof then
										sqlq="insert into crm_callrecord(TelID,Dtmf,com_id,personid,mytel,RecordNo,CallerId,CallType,startTime,endTime,recordTime,filePath) values("&TelID&",'"&Dtmf&"',"&idprod&","&session("personid")&",'"&mytel&"','"&RecordNo&"','"&CallerId&"','"&CallType&"','"&startTime&"','"&endTime&"','"&recordTime&"','"&filePath&"')"
										conn.execute(sqlq)
									end if
									rse.close
									set rse=nothing
								end if
							rst.movenext
							wend
						end if
						rst.close
						set rst=nothing
					end if
				 end if
				 
				'----开始记录录音数据 end 
				    '-----------------------if 
					'-------------------------
					'---记录联系情况
					'-----------------------begin
					sqlc="select * from crm_compcontactInfo where com_id="&com_id
					set rsc=server.CreateObject("ADODB.recordset")
					rsc.open sqlc,conn,1,3
					rsc.addnew()
					rsc("com_id")=com_id
					rsc("TelID")=TelID
					rsc("c_Type")=request.Form("contacttype")
					if request.Form("c_NoContact")="" then
					c_NoContact=0
					else
					c_NoContact=request.Form("c_NoContact")
					end if
					if request.Form("contacttype")="12" then
					rsc("c_NoContact")=c_NoContact
					end if
					if cstr(c_NoContact)="2" then
						sqlp="select * from crm_category_info where property_id='"&com_id&"' and property_value='10040008'"
						set rsp=conn.execute(sqlp)
						if rsp.eof or rsp.bof then
							sqli="insert into crm_category_info(property_id,property_value) values('"&com_id&"','10040008')"
							conn.execute(sqli)
						end if
						rsp.close
						set rsp=nothing
					end if
					if request.Form("contacttype")="13" then
						sqlp="delete from crm_category_info where property_id='"&com_id&"' and property_value='10040008'"
						conn.execute(sqlp)
					end if
					'rsc("C_CustomTaidu")=request.Form("c_Taidu")
					'rsc("C_CustomStation")=request.Form("c_compStation")
					'rsc("C_ServerIntro")=request.Form("c_serverIntro")
					'rsc("C_ServerType")=request.Form("c_ServerType")
					'rsc("c_ServerGo")=request.Form("c_ServerGo")
					'rsc("C_ServerTo")=request.Form("C_ServerTo")
					'rsc("c_PayType")=request.Form("c_PayType")
					
					rsc.update()
					rsc.close
					set rsc=nothing
					'-----------预备品牌通  2011-9-6
					yubeippt=request.Form("yubeippt")
					if yubeippt="1" then
						sqlp="select * from crm_category_info where property_id='"&com_id&"' and property_value='10050001'"
						set rsp=conn.execute(sqlp)
						if rsp.eof or rsp.bof then
							sqli="insert into crm_category_info(property_id,property_value) values('"&com_id&"','10050001')"
							conn.execute(sqli)
						end if
						rsp.close
						set rsp=nothing
					else
						sqlp="delete from crm_category_info where property_id='"&com_id&"' and property_value='10050001'"
						conn.execute(sqlp)
					end if
					'-----------重点客户准备  2012-2-10
					'zhongdiankh=request.Form("zhongdiankh")
'					if zhongdiankh="1" then
'						sqlp="select * from crm_category_info where property_id='"&com_id&"' and property_value='10050002'"
'						set rsp=conn.execute(sqlp)
'						if rsp.eof or rsp.bof then
'							sqli="insert into crm_category_info(property_id,property_value) values('"&com_id&"','10050002')"
'							conn.execute(sqli)
'						end if
'						rsp.close
'						set rsp=nothing
'					else
'						sqlp="delete from crm_category_info where property_id='"&com_id&"' and property_value='10050002'"
'						conn.execute(sqlp)
'					end if
					
					'---------------保存公司级别
					'------------无人接听，停机，关机的客户归类为普通客户   号码错误的客户：归类为垃圾客户
					'------------10 垃圾客户 11 普通客户 12 优质客户 13 vip客户
					comType=""
					if request.Form("contacttype")="12" then
						if c_NoContact="2" then
							comType="10"
						else
							comType="11"
						end if
					end if
					if comType<>"" then
						sql="select com_id,comType from comp_comTypeCheck where com_id="&com_id&""
						set rs=conn.execute(sql)
						if not rs.eof or not rs.bof then
							if cstr(rs("comType"))<>comType and cstr(rs("comType"))<>"12" and cstr(rs("comType"))<>"13" then
								sqlt="update comp_comTypeCheck set comType="&comType&",fcheck=0 where com_id="&com_id&""
								conn.execute(sqlt)
							end if
						else
							sqlt="insert into comp_comTypeCheck(com_id,comType) values("&com_id&","&comType&")"
							conn.execute(sqlt)
						end if
						rs.close
						set rs=nothing
					end if
					'--------------------------------
					if telflag="4" then
					
						sql="select * from comp_salesvap where com_id="&com_id
						set rs=server.CreateObject("ADODB.recordset")
						rs.open sql,conn,1,3
						if rs.eof or rs.bof then
							rs.addnew()
							rs("com_id")=com_id
						end if
							rs("com_rank")=request.Form("com_rank")
							rs("com_kind")=request.Form("com_kind")
							if request.Form("contactnext_time")<>"" then
								rs("contactnext_time")=request.Form("contactnext_time")
							end if
							if request.Form("contacttype")<>"" then
								rs("contacttype")=request.Form("contacttype")
							end if
							rs("com_Especial")=request.Form("com_Especial")
							if request.Form("contacttype")="13" then
								rs("LastTelDate")=now()
							end if
							rs.update()
							rs.close
							set rs=nothing
							
					elseif telflag="5" then
					'---------sms销售情况
						response.Write(request.Form("telflag"))
						sql="select * from comp_salessms where com_id="&com_id
						set rs=server.CreateObject("ADODB.recordset")
						rs.open sql,conn,1,3
						if rs.eof or rs.bof then
							rs.addnew()
							rs("com_id")=com_id
						end if
							rs("com_rank")=request.Form("com_rank")
							rs("com_kind")=request.Form("com_kind")
							if request.Form("contactnext_time")<>"" then
								rs("contactnext_time")=request.Form("contactnext_time")
							end if
							if request.Form("contacttype")<>"" then
								rs("contacttype")=request.Form("contacttype")
							end if
							rs("com_Especial")=request.Form("com_Especial")
							if request.Form("contacttype")="13" then
								rs("LastTelDate")=now()
							end if
							rs.update()
							rs.close
							set rs=nothing
					else
						'-----ICD销售情况
						sql="select * from comp_sales where com_id="&com_id
						set rs=server.CreateObject("ADODB.recordset")
						rs.open sql,conn,1,3
						if rs.eof or rs.bof then
							rs.addnew()
							rs("com_id")=com_id
						end if
							rs("com_rank")=request.Form("com_rank")
							rs("com_kind")=request.Form("com_kind")
							if request.Form("contactnext_time")<>"" then
								rs("contactnext_time")=request.Form("contactnext_time")
							end if
							if request.Form("contacttype")<>"" then
								rs("contacttype")=request.Form("contacttype")
							end if
							rs("xiaoshouflag")=request.Form("xiaoshouflag")
							rs("com_Especial")=request.Form("com_Especial")
							if request.Form("contacttype")="13" then
								rs("LastTelDate")=now()
							end if
							rs.update()
							rs.close
							set rs=nothing
					
							sql="select * from temp_salescomp where com_id="&com_id
							set rs=server.CreateObject("ADODB.recordset")
							rs.open sql,conn,1,3
							rs("com_rank")=request.Form("com_rank")
							if request.Form("contactnext_time")<>"" then
								rs("contactnext_time")=request.Form("contactnext_time")
							end if
							if request.Form("contacttype")<>"" then
								rs("contacttype")=request.Form("contacttype")
							end if
							rs.update()
							rs.close
							set rs=nothing
					end if
				end if
				'-----------vap 没有增值意向
				zengzhi=request.Form("zengzhi")
				if zengzhi="1" then
					sql="insert into crm_notBussiness(com_id) values("&com_id&")"
					conn.execute(sql)
				end if
				if zengzhi="0" then
					sql="delete from crm_notBussiness where com_id="&com_id&""
					conn.execute(sql)
				end if
				'***************主营方向
				goodCom=request.Form("goodCom")
				if goodCom="" then goodCom="0"
				abroadCom=request.Form("abroadCom")
				if abroadCom="" then abroadCom="0"
				salestype=request.Form("salestype")
				if salestype="" then salestype="0"
				
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
				
				if request("salestype")<>"" or goodCom<>"0" or abroadCom<>"0" or CMeet<>"*******" then
					sqlreg="select * from temp_comp_salestype where com_id="&com_id
					set rsreg=conn.execute(sqlreg)
					if rsreg.eof then
						sqlinreg="insert into temp_comp_salestype(id,com_id,salestype,salestext,buytext,GoodCom,abroadCom,cmeet) values(0,"&com_id&",'"&salestype&"','"&replacequot(request.Form("salestext"))&"','"&replacequot(request.Form("buytext"))&"',"&goodCom&","&abroadCom&",'"&CMeet&"')"
						conn.execute(sqlinreg)
						'------------修改审核标志
						if request.Form("contacttype")="13" then
						sqld="update Crm_Temp_SalesComp set Fcheck=0 where com_id="&com_id&""
						conn.execute(sqld)
						end if
					else
						tempvalue1=cstr(rsreg("salestype")&rsreg("salestext")&rsreg("buytext")&rsreg("goodCom")&rsreg("abroadCom")&rsreg("cmeet"))
						tempvalue2=cstr(salestype&replacequot(request.Form("salestext"))&replacequot(request.Form("buytext"))&goodCom&abroadCom&cmeet)
						sqlinreg="update temp_comp_salestype set salestype='"&salestype&"',salestext='"&replacequot(request.Form("salestext"))&"',buytext='"&replacequot(request.Form("buytext"))&"',goodCom="&goodCom&",abroadCom="&abroadCom&",editTime='"&now&"',editCheck=0,cmeet='"&cmeet&"' where com_id="&com_id&""
						conn.execute(sqlinreg)
						'------------判断是否修改过信息
						if tempvalue1<>tempvalue2 then
							if request.Form("contacttype")="13" then
							sqld="update Crm_Temp_SalesComp set Fcheck=0 where com_id="&com_id&""
							conn.execute(sqld)
							end if
						end if
					end if
					rsreg.close
					set rsreg=nothing
					
				end if
				'保存联系记end if
				'------------------------------------------------------	        
			'&&保存省市
				if comcountry="1" then
					province=request.Form("province1")
					city=request.Form("city1")
					if city="" then city=0
					Garden=request.Form("Garden")
					if Garden="" then Garden=0
					sqlm="select * from temp_comp_provinceID where com_id="&com_id
					set rsm=conn.execute(sqlm)
					if rsm.eof or rsm.bof then
						sqle="insert into temp_comp_provinceID(id,com_id,province,city,Garden) values(0,"&com_id&",'"&province&"','"&city&"','"&Garden&"')"
						conn.execute(sqle)
						'------------修改审核标志
						if request.Form("contacttype")="13" then
						sqld="update Crm_Temp_SalesComp set Fcheck=0 where com_id="&com_id&""
						conn.execute(sqld)
						end if
					else
						sqle="update temp_comp_provinceID set province='"&province&"',city='"&city&"',Garden='"&Garden&"',Fcheck=0 where com_id="&com_id
						conn.execute(sqle)
						pvalue1=cstr(rsm("province"))&cstr(rsm("city"))&cstr(rsm("Garden"))
						pvalue2=cstr(province)&cstr(city)&cstr(Garden)
						if pvalue1<>pvalue2 then
							'------------修改审核标志
							'sqld="update Crm_Temp_SalesComp set Fcheck=0 where com_id="&com_id&""
							'conn.execute(sqld)
						end if
					end if
					rsm.close
					set rsm=nothing
				else
					sqle="delete from temp_comp_provinceID where com_id="&com_id
					conn.execute(sqle)
					'------------修改审核标志
					'sqld="update Crm_Temp_SalesComp set Fcheck=0 where com_id="&com_id&""
					'conn.execute(sqld)
				end if
	      '&保存省市 
				'------------------添加联系人信息 begin
				if request.Form("lxcontactflag")="1" then
					sql="select * from crm_PersonInfo"
					set rs=server.CreateObject("ADODB.recordset")
					rs.open sql,conn,1,3
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
					rs("personComname")=request.Form("personComname")
					rs.update()
					rs.close
					set rs=nothing
					response.Write("<script>parent.document.getElementById('personinfoa').src='crm_personinfo.asp?com_id="&com_id&"'</script>")
				end if
				'------------------添加联系人信息 end
			end if
				response.Write("<script>parent.document.getElementById('topt').src='crm_tel_comp.asp?com_id="&com_id&"&telflag="&telflag&"'</script>")
				response.Write("<script>parent.document.getElementById('crmeidt').style.display=''</script>")
				response.Write("<script>parent.document.getElementById('crmeidt').style.height='100';parent.document.getElementById('submitsave').disabled=false;parent.document.getElementById('submitsave1').disabled=false;parent.document.getElementById('submitsave2').disabled=false;</script>")
		end if
		'开始保存 end
end if
%>
</head>

<body>
<% '参与的活动
if active_flag<>"" then
%>
<iframe id="saveactive" src="" width="0" height="0" frameborder="0" ></iframe>
<script>
var activename="<%= active_flag %>";
if (activename=="zz91_2014台州塑交会"){
	active_flag="20031004"
}
//if (activename!=""){
//	activename=UTF8UrlEncode(activename)
//}
document.getElementById("saveactive").src="http://adminasto.zz91.com/saveactive_flag.html?com_id=<%= com_id %>&active_flag="+active_flag;
</script>
<%
end if
%>
<a name="top"></a>
<a name="error">
<div id="<%=errcss%>"> <strong> <font color="red"><%=errfild%></font> </strong></div>
</a>
<%
if cstr(c_NoContact)="2" or gonghai_out<>"" then
%>
<iframe src="http://admin.zz91.com/admin1/crm/saveNocontactLocal.asp?com_id=<%= com_id %>&c_NoContact=2&gonghai_out=<%=gonghai_out%>&contacttype=<%=contacttype%>" width="0" height="0" frameborder="0" ></iframe>
<%
end if
%>
<% '囤货信息保存
if request.Form("storeCate")<>"" then
%>
<iframe src="http://admin.zz91.com/admin1/crm/saveStoreLocal.asp?com_id=<%= com_id %>&storeCate=<%= request("storeCate") %>&storeDesc=<%= request("storeDesc") %>&storePrice=<%= request("storePrice") %>&storeCount=<%= request("storeCount") %>" width="100" height="100" frameborder="0" ></iframe>
<%
end if
%>

</body>
</html>
<%

conn.close
set conn=nothing
%>