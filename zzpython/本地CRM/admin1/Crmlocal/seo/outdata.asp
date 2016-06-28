<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!--#include file="../../../cn/sources/Md5.asp"-->
<!--#include file="../../include/include.asp"-->
<!--#include file="../../include/pagefunction.asp"-->
<!--#include file="../inc.asp"-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title></title>
</head>

<body>
<%
sql=""
sear="n="
if request("com_email")<>"" then
	sql=sql&" and com_email like '%"&request("com_email")&"%'"
	sear=sear&"&com_email="&request("com_email")
end if
if request("keywords")<>"" then
	sql=sql&" and exists(select sid from seo_keywordslist where keywords like '%"&request("keywords")&"%' and sid=v_seolist.id)"
	sear=sear&"&keywords="&request("keywords")
end if
if request("dbtype")<>"" then
	sql=sql&" and dbflag="&request("dbtype")&""
	sear=sear&"&dbtype="&request("dbtype")
end if
if request("seo_start")<>"" then
	sql=sql&" and seo_start>='"&request("seo_start")&"'"
	sear=sear&"&seo_start="&request("seo_start")
end if
if request("com_msb")<>"" then
	sql=sql&" and com_msb like '%"&request("com_msb")&"%'"
	sear=sear&"&com_msb="&request("com_msb")
end if
if request("target_assure")<>"" then
	sql=sql&" and target_assure='"&request("target_assure")&"'"
	sear=sear&"&target_assure="&request("target_assure")
end if
if request("seoperson")<>"" then
	sql=sql&" and personid="&request("seoperson")&""
	sear=sear&"&seoperson="&request("seoperson")
end if
if request("waste")<>"" then
	sql=sql&" and waste="&request("waste")&""
	sear=sear&"&waste="&request("waste")
end if

if session("userid")="10" or session("userid")="13" or Partuserid="4204" or left(session("userid"),2)="13" then
else
	sql=sql&" and personid="&session("personid")&""
end if
'response.Write(sql)
   Set oPage=New clsPageRs2
   With oPage
	 .sltFld  = "*"
	 .FROMTbl = "v_seolist"
	 .sqlOrder= "order by seo_start desc"
	 .sqlWhere= "WHERE  id>0 "&sql
	 .keyFld  = "id"    '不可缺少
	 .pageSize= 15
	 .getConn = conn
	 Set Rs  = .pageRs
   End With
   total=oPage.getTotalPage
   oPage.pageNav "?"&sear,""
   totalpg=cint(total/15)
   if total/15 > totalpg then
	  totalpg=totalpg+1
   end if
%>
</body>
</html>
