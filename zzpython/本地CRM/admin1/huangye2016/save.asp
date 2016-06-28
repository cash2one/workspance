<!-- #include file="../include/ad!$#5V.asp" -->
<!-- #include file="jumplogin.asp" -->
<!-- #include file="../../cn/function.asp" -->
<%

if personid="" then personid=session("personid")
if pcheck="" then pcheck="0"
sqlp="select realname,huangye_check from users where id="&personid&""
set rsp=conn.execute(sqlp)
if not rsp.eof or not rsp.bof then
	realname=rsp(0)
	huangye_check=rsp("huangye_check")
end if
rsp.close
set rsp=nothing

com_email=request.Form("com_email")
if com_email="" then
	com_email=request.Form("mobile")+"@zz91.com"
end if
com_id=request.Form("com_id")
if com_id="" then com_id=0
id=request.Form("id")

comname=replacequot(trim(request.form("cname")))
comadd=replacequot(trim(request.form("cadd")))
comprovince=replacequot(trim(request.form("province"))&"|"&trim(request.form("city")))
comtel=trim(request.form("ctel"))
commobile=trim(request.form("cmobile"))
comcontactp=replacequot(trim(request.form("ccontactp")))
cproductslist=replacequot(trim(request.form("cproductslist")))
comkeywords=request.form("ckeywords")
comkeywords=replace(comkeywords,", ",",")
if comkeywords="" then comkeywords=0
personname=request.Form("personname")
membertype=request.Form("membertype")
lpersonid=request.Form("lpersonid")
pcheck=request.Form("pcheck")
js1=request.Form("js1")
js2=request.Form("js2")
sl1=request.Form("sl1")
sl2=request.Form("sl2")
qt1=request.Form("qt1")
qt2=request.Form("qt2")
newemail=request.Form("newemail")

if pcheck="" then pcheck=0
sqlcom="select personid from huangye_list where com_email='"&com_email&"' and huangye_qukan='2016'"
set rsc=conn.execute(sqlcom)
if not rsc.eof or not rsc.bof then
	mypersonid=rsc(0)
else
	mypersonid=session("personid")
end if
rsc.close
set rs=nothing


if huangye_check="1" then
	huangye=0
else
	if cstr(mypersonid)<>cstr(session("personid")) then
		response.Write("该客户已经添加，并且不是你添加的,你不能修改该客户")
		huangye=1
	end if
end if

if huangye<>1 then
	if id="0" or id="" then
		sqlcom="select * from huangye_list where com_email='"&com_email&"' and huangye_qukan='2016'"
	else
		sqlcom="select * from huangye_list where id="&id&""
	end if
	set rscc=server.CreateObject("ADODB.recordset")
	rscc.open sqlcom,conn,1,3
	if rscc.eof or rscc.bof then
		rscc.addnew
		rscc("com_email")=com_email
		rscc("com_id")=com_id
		action="add"
	end if
		rscc("cname")=comname
		rscc("cadd")=comadd
		rscc("province")=comprovince
		'rscc("ctel")=comtel
		rscc("cmobile")=commobile
		rscc("ccontactp")=comcontactp
		rscc("cproductslist")=cproductslist
		rscc("comkeywords")=comkeywords
		rscc("personid")=lpersonid
		rscc("personname")=personname
		rscc("membertype")=membertype
		rscc("newemail")=newemail
		if comkeywords="1" then
			rscc("js1")=js1
			rscc("js2")=js2
			rscc("sl1")=""
			rscc("sl2")=""
		elseif comkeywords="2" then
			rscc("js1")=""
			rscc("js2")=""
			rscc("sl1")=sl1
			rscc("sl2")=sl2
		elseif comkeywords="3" then
			rscc("js1")=""
			rscc("js2")=""
			rscc("sl1")=""
			rscc("sl2")=""
			rscc("qt1")=qt1
			rscc("qt2")=qt2
		end if
		rscc("huangye_qukan")="2016"
		if huangye_check="1" then
			rscc("pcheck")=pcheck
		end if
		action="edit"
		rscc.update()
	rscc.close
	set rscc=nothing
	if action="add" then
		response.Write("添加保存成功")
	elseif action="edit" then
		response.Write("修改成功")
	end if
end if
%>
<%
conn.close
set conn=nothing
%>

<a href="list.asp">返回列表</a>