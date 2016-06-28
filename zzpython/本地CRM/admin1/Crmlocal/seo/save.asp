<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!--#include file="../../include/include.asp"-->
<!-- #include file="../../../cn/function.asp" -->
<!--#include file="../inc.asp"-->

<%
com_email=request.Form("com_email")
com_id=request.Form("com_id")
if com_id="" then com_id=0
keywords=request.Form("keywords")
target_assure=request.Form("target_assure")
seo_start=request.Form("seo_start")
target_require=request.Form("target_require")
target_time=request.Form("target_time")
expire_time=request.Form("expire_time")
com_msb=request.Form("com_msb")
com_msb=replace(lcase(com_msb),"http://","")
'com_msb=replace(com_msb,"/","")
price=request.Form("price")
personid=request.Form("lpersonid")

action=request.Form("action")

if action="add" then
	sql="select * from seo_list where com_email='"&com_email&"' and seo_start='"&seo_start&"'"
	set rscc=server.CreateObject("ADODB.recordset")
	rscc.open sql,conn,1,3
	if rscc.eof or rscc.bof then
		rscc.addnew
		rscc("com_email")=trim(com_email)
		rscc("com_id")=com_id
		if target_assure<>"" then
			rscc("target_assure")=target_assure
		end if
		if seo_start<>"" then
			rscc("seo_start")=seo_start
		end if
		if target_require<>"" then
			rscc("target_require")=target_require
		end if
		if target_time<>"" then
			rscc("target_time")=target_time
		end if
		
		if expire_time<>"" then
			rscc("expire_time")=expire_time
		end if
		
		rscc("com_msb")=trim(com_msb)
		rscc("price")=price
		rscc("personid")=personid
		rscc.update()
		rscc.close
		set rscc=nothing
		
		sqlt="select max(id) from seo_list where com_email='"&com_email&"' and seo_start='"&seo_start&"'"
		set rst=conn.execute(sqlt)
		if not rst.eof or not rst.bof then
			sid=rst(0)
		end if
		rst.close
		set rst=nothing
		arrkeywords=split(keywords,"|")
		for i=0 to ubound(arrkeywords)
			sqlk="select * from seo_keywordslist where keywords='"&trim(arrkeywords(i))&"' and sid="&sid&""
			set rsk=server.CreateObject("ADODB.recordset")
			rsk.open sqlk,conn,1,3
			if rsk.eof or rsk.bof then
				rsk.addnew
				rsk("sid")=sid
				rsk("keywords")=trim(arrkeywords(i))
				rsk("com_msb")=trim(com_msb)
				if target_assure<>"" then
					rsk("target_assure")=target_assure
				end if
				if seo_start<>"" then
					rsk("seo_start")=seo_start
				end if
				if target_require<>"" then
					rsk("target_require")=target_require
				end if
				if target_time<>"" then
					rsk("target_time")=target_time
				end if
				
				if expire_time<>"" then
					rscc("expire_time")=expire_time
				end if
				rsk("personid")=personid
				rsk.update()
			end if
			rsk.close
			set rsk=nothing
		next
		response.Write("保存成功")
	end if
end if
if action="edit" then
	id=request.Form("sid")
	sql="select * from seo_list where id="&id&""
	set rscc=server.CreateObject("ADODB.recordset")
	rscc.open sql,conn,1,3
	if not rscc.eof or not rscc.bof then

		if target_assure<>"" then
			rscc("target_assure")=target_assure
		end if
		
		if seo_start<>"" then
			rscc("seo_start")=seo_start
		end if
		if target_require<>"" then
			rscc("target_require")=target_require
		end if
		if target_time<>"" then
			rscc("target_time")=target_time
		end if
		if seo_start<>"" then
			rscc("seo_start")=seo_start
		end if

		
		if expire_time<>"" then
			rscc("expire_time")=expire_time
		end if
		
		rscc("com_msb")=trim(com_msb)
		rscc("price")=price
		rscc("personid")=personid
		rscc.update()
		rscc.close
		set rscc=nothing
		
		
		response.Write("保存成功")
	end if
end if

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>保存成功</title>
</head>

<body>
</body>
</html>
