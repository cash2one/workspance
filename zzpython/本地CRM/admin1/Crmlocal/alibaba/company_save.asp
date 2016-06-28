<!-- #include file="../connlocal.asp" -->
<!-- #include file="../../../cn/function.asp" -->
<!-- #include file="../../../cn/sources/Md5.asp" -->
<%
set connali=server.CreateObject("ADODB.connection")
strconnali="Provider=SQLOLEDB.1;driver={SQL Server};server=(local);uid=cru_crm;pwd=fdf@$@#dfdf9780@#1.kdsfd;database=zhuaqu"
connali.open strconnali
sql="select * from company1 where id="&request("id")
set rs=connali.execute(sql)
if not rs.eof then
    com_subname=trim(rs("weburl"))
	if rs("cxt")="1" then
	com_subname=replace(com_subname,".cn.alibaba.com/","")
	com_subname=replace(com_subname,"http://","")
	else
	com_subname=replace(com_subname,"http://china.alibaba.com/company/detail/","")
	com_subname=replace(com_subname,".html","")
	end if

	comemail=com_subname&"@zz91.net"
	comname=replacequot(rs("com_name"))
	comadd=replacequot(rs("com_addr"))
	comzip=rs("com_zip")
	city=replacequot(rs("com_city"))
	comstaton=rs("com_station")
	comprovince=replacequot(trim(rs("com_province"))&"|"&city)
	comcountry=trim(rs("com_country"))
	comtel=trim(rs("com_tel"))
	commobile=trim(rs("com_mobile"))
	comfax=trim(rs("com_fax"))
	comcontactp=replacequot(trim(rs("com_contactperson")))
	comdesi=replacequot(trim(rs("com_desi")))
	comwebsite=trim(rs("Weburl"))
	
	if request.Form("m")="" then 
	set rsreg=server.CreateObject("ADODB.recordset")
	sql="select * from comp_info where com_email='"&comemail&"'"
	rsreg.open sql,connserver,1,3
	errword="<li>"
	errflag=true
	if not rsreg.eof and not rsreg.bof then'有记录
			errword=errword&"您输入的EMAIL已存在！<br>"
			errflag=false
		regstatus="1"'已有记录(非注册用户)
	else
		regstatus="0"'无记录
	end if
	if trim(comintro)="" then
		'errflag=false
		if errword="<li>" then
			errword=errword&"请输入公司简介!"
		else
			errword=errword&"<li>请输入公司简介!"
		end if
	end if
	rsreg.close
	set rsreg=nothing
	else
	errflag=true
	end if
	if errflag=true then
		compass=keygenpass(8)
		
		sqlc="select * from comp_info where com_email='"&comemail&"'"
		set rsc=server.CreateObject("ADODB.recordset")
		rsc.open sqlc,connserver,1,2
		rsc.addnew()
		rsc("com_pass")=compass
		rsc("com_SafePass")=md5(compass,16)

		rsc("com_name")=comname
		rsc("com_add")=comadd
		rsc("com_zip")=comzip
		rsc("com_province")=comprovince
		
		rsc("com_ctr_id")=1
		rsc("com_tel")=replace(trim(comtel),"	","")
		rsc("com_mobile")=commobile
		rsc("com_fax")=replace(trim(comfax),"	","")
		rsc("com_email")=comemail
		rsc("com_website")=comwebsite
		
		rsc("com_contactperson")=comcontactp
		rsc("com_desi")=comdesi
		rsc("com_keywords")=comkeywords
		rsc("com_kind")=comkind
		rsc("com_productslist_en")=trim(rs("com_zyyw"))
		
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
		'-------------------------begin
		'写入到登陆表
		sqlp="select * from comp_loading where com_id="&com_id
		set rsp=connserver.execute(sqlp)
		if not rsp.eof or not rsp.bof then
			sqlh="update comp_loading set com_UserName='"&com_subname&"' where com_id="&com_id&""
			connserver.execute(sqlh)
		else
			sqlh="insert into comp_loading (Com_Email,Com_UserName,Com_PW,Com_PWsafe) values('"&comemail&"','"&com_subname&"','"&compass&"','"&md5(compass,16)&"')"
			connserver.execute(sqlh)
		end if
		rsp.close
		set rsp=nothing
		'-------------------------begin
		'排除插入的客户
		sqli="select id from InsertComp where wid="&request("id")
		set rsi=connali.execute(sqli)
		if  rsi.eof or  rsi.bof then
			sqlb="insert into insertcomp(wid) values("&request("id")&")"
			connali.execute(sqlb)
		end if
		rsi.close
		set rsi=nothing
		connali.close
		set connali=nothing
		'-------------------------end
		response.Redirect("../webcrm_comp_savelocal.asp?com_id="&com_id)
		closeconn()
		response.write "<script language='javascript'>alert('保存成功!');window.close()</script>"
		response.end
	end if
end if
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
</head>

<body>
</body>
</html>
