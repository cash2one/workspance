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
personid=request("personid")

city=trim(request.form("city"))
if city="城市" then
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
    '-----------判断EMAIL
	set rsreg=server.CreateObject("ADODB.recordset")
	sql="select com_id from comp_info where com_email='"&comemail&"'"
	rsreg.open sql,connserver,1,3
	errword=""
	errflag=true
	if not rsreg.eof and not rsreg.bof then'有记录
			errword=errword&"您输入的EMAIL已存在！<br>"
			errflag=false
		regstatus="1"'已有记录(非注册用户)
	else
		regstatus="0"'无记录
	end if
	rsreg.close
	set rsreg=nothing
	'-----------------end 
	'-----------判断手机
	sql="select com_id,com_email from comp_info where com_mobile like '"&right(commobile,9)&"'"
	set rs=connserver.execute(sql)
	if not (rs.eof or rs.bof) then
		errflag=false
		errword=errword&"你输入的手机与“"&rs("com_email")&"”有重复!<br>"
	end if
	rs.close
	set rs=nothing
	'--------------end
	'-----------判断电话
	sql="select com_id,com_email from comp_info where com_tel like '"&right(comtel,7)&"'"
	set rs=connserver.execute(sql)
	if not (rs.eof or rs.bof) then
		errflag=false
		errword=errword&"你输入的电话与“"&rs("com_email")&"”有重复!<br>"
	end if
	rs.close
	set rs=nothing
	'--------------end 
	if trim(comintro)="" then
		if errword="<li>" then
			errword=errword&"请输入公司简介!"
		else
			errword=errword&"<li>请输入公司简介!"
		end if
	end if
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
	'rsc("com_safekey")=comsafekey
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
	rsc("adminuser")=10000
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
	'-----------------------
	'-写入新的登录表
	'-----------------------------begin
	sqln="select com_id from comp_loading where com_id="&com_id
	set rsn=connserver.execute(sqln)
	if rsn.eof and rsn.bof then
		sqli="insert into comp_loading(com_id,Com_Email,Com_PW,Com_PWsafe) values("&com_id&",'"&comemail&"','"&compass&"','"&md5(compass,16)&"')"
		connserver.execute(sqli)
	end if
	rsn.close
	set rsn=nothing
	'-----------------------------end
	response.Redirect("http://192.168.1.2/admin1/crmlocal/crm_comp_savelocal.asp?com_id="&com_id&"&personid="&personid)

	    response.write "<script language='javascript'>alert('保存成功!');window.close()</script>"
		response.end

	
end if
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>无标题文档</title>
<style type="text/css">
<!--
body,td,th {
	font-size: 12px;
	color: #FF0000;
}
-->
</style>
</head>

<body>
<font color="#FF0000"><%response.Write(errword)%></font>
</body>
</html>