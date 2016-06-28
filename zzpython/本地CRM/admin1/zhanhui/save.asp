<!-- #include file="../include/ad!$#5V.asp" -->
<!-- #include file="jumplogin.asp" -->
<!-- #include file="../../cn/function.asp" -->
<%
com_email=request.Form("com_email")
com_id=request.Form("com_id")
id=request.Form("id")
if id="" then id=0
if com_id="" then com_id=0
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
personid=request.Form("personid")
pcheck=request.Form("pcheck")




if huangye<>1 then
	sqlcom="select * from zhanhui_list where id="&id&""
	set rscc=server.CreateObject("ADODB.recordset")
	rscc.open sqlcom,conn,1,3
	if rscc.eof or rscc.bof then
		rscc.addnew
		rscc("com_id")=com_id
	end if
		rscc("com_email")=com_email
		rscc("cname")=comname
		rscc("cadd")=comadd
		rscc("province")=comprovince
		rscc("ctel")=comtel
		rscc("cmobile")=commobile
		rscc("ccontactp")=comcontactp
		rscc("cproductslist")=cproductslist
		rscc("comkeywords")=comkeywords
		rscc("personid")=personid
		rscc("personname")=personname
		'rscc("membertype")=membertype
		'if session("personid")="761" or session("personid")="1135" or session("personid")="14" then
		'	rscc("pcheck")=pcheck
		'end if
		rscc.update()
	rscc.close
	set rscc=nothing
	response.Write("保存成功")
end if
%>
<%
conn.close
set conn=nothing
%>

<a href="list.asp">返回列表</a>