<!-- #include file="../include/ad!$#5V.asp" -->
<!-- #include file="jumplogin.asp" -->
<!-- #include file="../../cn/function.asp" -->
<%
com_email=request.Form("com_email")
com_id=request.Form("com_id")
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
if pcheck="" then pcheck=0
sqlcom="select personid from huangye_list where com_email='"&com_email&"'"
set rsc=conn.execute(sqlcom)
if not rsc.eof or not rsc.bof then
	mypersonid=rsc(0)
else
	mypersonid=session("personid")
end if
rsc.close
set rs=nothing


if session("personid")="761" or session("personid")="1135" or session("personid")="14" then
	huangye=0
else
	if cstr(mypersonid)<>cstr(session("personid")) then
		response.Write("该客户已经添加，并且不是你添加的,你不能修改该客户")
		huangye=1
	end if
end if

if huangye<>1 then
	sqlcom="select * from huangye_list where com_email='"&com_email&"'"
	set rscc=server.CreateObject("ADODB.recordset")
	rscc.open sqlcom,conn,1,3
	if rscc.eof or rscc.bof then
		rscc.addnew
		rscc("com_email")=com_email
		rscc("com_id")=com_id
	end if
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
		rscc("membertype")=membertype
		if session("personid")="761" or session("personid")="1135" or session("personid")="14" then
			rscc("pcheck")=pcheck
		end if
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