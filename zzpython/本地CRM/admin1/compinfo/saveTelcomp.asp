<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../include/adfsfs!@#.asp" -->
<!-- #include file="../../cn/function.asp" -->
<!--#include file="../include/include.asp"-->
<%
complist=request.QueryString("cbb")
arrcomp=split(complist,",")
comType=request.QueryString("comType")
dotype=request.QueryString("dotype")
url=request.QueryString("url")
if ubound(arrcomp)>=0 then
	for i=0 to ubound(arrcomp)
		sql="select com_id from comp_comType where com_id="&trim(arrcomp(i))&""
		set rs=conn.execute(sql)
		if not rs.eof or not rs.bof then
			sqlt="update comp_comType set comType="&comType&" where com_id="&trim(arrcomp(i))&""
			conn.execute(sqlt)
			sqlt="update comp_comTypeCheck set Fcheck=1 where com_id="&trim(arrcomp(i))&""
			conn.execute(sqlt)
		else
			sqlt="insert into comp_comType(com_id,comType) values("&trim(arrcomp(i))&","&comType&")"
			conn.execute(sqlt)
			sqlt="update comp_comTypeCheck set Fcheck=1 where com_id="&trim(arrcomp(i))&""
			conn.execute(sqlt)
		end if
		rs.close
		set rs=nothing
	next
end if
url=replace(url,"~amp~","&")
response.Redirect(url)
%>
<%endConnection()%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
</head>

<body>
</body>
</html>
