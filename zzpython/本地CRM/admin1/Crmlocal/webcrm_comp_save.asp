<%
set connserver=server.CreateObject("ADODB.connection")
strconnserver="Provider=SQLOLEDB.1;driver={SQL Server};server=(local);uid=rcu;pwd=dfsdf!@!@#sdfds$#^!*dfdsf4343749d;database=rcu"
connserver.open strconnserver
%>
<!-- #include file="../../cn/function.asp" -->
<!-- #include file="../../cn/sources/Md5.asp" -->
<%
comemail=lcase(trim(request.form("cemail")))
comname=replacequot(trim(request.form("cname")))
comadd=replacequot(trim(request.form("cadd")))
comzip=trim(request.form("czip"))

city=trim(request.form("city"))
if city="����" then
city=""
end if
comprovince=replacequot(trim(request.form("province"))&"|"&city)
'comprovince=replacequot(trim(request.form("cprovince")))
comcountry=trim(request.form("ccountry"))
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
if request.Form("m")="" then 
set rsreg=server.CreateObject("ADODB.recordset")
sql="select * from comp_info where com_email='"&comemail&"'"
rsreg.open sql,connserver,1,3
errword="<li>"
errflag=true
if not rsreg.eof and not rsreg.bof then'�м�¼
		errword=errword&"�������EMAIL�Ѵ��ڣ�<br>"
		errflag=false
	regstatus="1"'���м�¼(��ע���û�)
else
	regstatus="0"'�޼�¼
end if
if trim(comintro)="" then
    'errflag=false
	if errword="<li>" then
		errword=errword&"�����빫˾���!"
	else
		errword=errword&"<li>�����빫˾���!"
	end if
end if
rsreg.close
set rsreg=nothing
else
errflag=true
end if
if errflag=true then
	compass=keygenpass(8)
    comsafekey=keygenl(24)
	if request.Form("m")="" then 
	sqlc="select * from comp_info where com_email='"&comemail&"'"
	else
	sqlc="select * from comp_info where com_id="&request.Form("com_id")&""
	end if
	set rsc=server.CreateObject("ADODB.recordset")
	rsc.open sqlc,connserver,1,3
	if request.Form("m")="" then 
	rsc.addnew()
	rsc("com_pass")=compass
	rsc("com_safekey")=comsafekey
	rsc("com_SafePass")=md5(compass,16)
	else
	com_id=request.Form("com_id")
	end if
	
	rsc("com_name")=comname
	rsc("com_add")=comadd
	rsc("com_zip")=comzip
	rsc("com_province")=comprovince
	
	rsc("com_ctr_id")=1
	rsc("com_tel")=comtel
	rsc("com_mobile")=commobile
	rsc("com_fax")=comfax
	rsc("com_email")=comemail
	rsc("com_website")=comwebsite
	
	rsc("com_contactperson")=comcontactp
	rsc("com_desi")=comdesi
	rsc("com_keywords")=comkeywords
	rsc("com_kind")=comkind
    rsc("com_productslist_en")=trim(request.Form("cproductslist_en"))
	rsc("com_intro")=ltrim(rtrim(comintro))
	rsc("adminuser")=1000
	rsc.update()
	rsc.close()
	set rsc=nothing
	sqlmax="select max(com_id) from comp_info where com_email='"&comemail&"'"
	set rsmax=connserver.execute(sqlmax)
	if not rsmax.eof then
	com_id=rsmax(0)
	end if
	rsmax.close
	set rsmax=nothing
	response.Redirect("/admin1/crmlocal/webcrm_comp_savelocal.asp?com_id="&com_id)

	    response.write "<script language='javascript'>alert('����ɹ�!');window.close()</script>"
		response.end

	
end if
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>�ޱ����ĵ�</title>
</head>

<body>
<%response.Write(errword)%>
<input type="submit" name="Submit" value="����" onClick="window.history.back(1)">
</body>
</html>