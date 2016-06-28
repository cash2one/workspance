<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!--#include file="../../include/include.asp"-->
<!--#include file="../../include/pagefunction.asp"-->
<!--#include file="../inc.asp"-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
</head>

<body>

<%
str=""
page=request.Form("page")
sqldata=request.Form("sqldata")
pagerecord=50
		sear=sear&"&dotype="&dotype
		Set oPage=New clsPageRs2
		With oPage
		 .sltFld  = "*"
		 .FROMTbl = "renshi_user"
		 .sqlOrder= "order by id desc"
		 .sqlWhere= "WHERE  id>0 "&sqldata
		 .keyFld  = "id"    '不可缺少
		 .pageSize= pagerecord
		 .getConn = conn
		 Set Rs  = .pageRs
		End With
		total=oPage.getTotalPage
		if not rs.eof or not rs.bof then
		while not rs.eof

    str=str&"<tr>"
      str=str&"<td>"&showMeno(conn,"category",rs("station"))&"</td>"
      str=str&"<td>"&rs("username")&"</td>"
      str=str&"<td>"&rs("sex")&"</td>"
      str=str&"<td>"&showMeno(conn,"category",rs("education"))&"</td>"
      str=str&"<td>"&rs("mobile")&"</td>"
      str=str&"<td>"&showMeno(conn,"category",rs("jl1"))&"</td>"
      str=str&"<td>"&showMeno(conn,"category",rs("jl2"))&"</td>"
      str=str&"<td>"&showMeno(conn,"category",rs("jl3"))&"</td>"
      str=str&"<td>"&showMeno(conn,"category",rs("jl4"))&"</td>"
      str=str&"<td>"&rs("laiyuan")&"</td>"
      str=str&"<td>"&rs("gmt_created")&"</td>"
      str=str&"<td>"&rs("interviewTime")&"</td>"
      str=str&"<td>"
				  if rs("personid")<>"" then
				  sqlu="select realname from users where id="&rs("personid")&""
				  set rsu=conn.execute(sqlu)
				  if not rsu.eof or not rsu.bof then
				  	str=str&rsu(0)
				  end if
				  rsu.close
				  set rsu=nothing
				  end if
	  str=str&"</td>"
      str=str&"<td>"
				  sqlp="select personid from renshi_assign where uid="&rs("id")&""
				  set rsp=conn.execute(sqlp)
				  if not rsp.eof or not rsp.bof then
				  
					  sqlu="select realname from users where id="&rsp("personid")&""
					  set rsu=conn.execute(sqlu)
					  if not rsu.eof or not rsu.bof then
						str=str&rsu(0)
					  end if
					  rsu.close
					  set rsu=nothing
				  end if
				  rsp.close
				  set rsp=nothing
		str=str&"</td>"
    str=str&"</tr>"
   rs.movenext
   wend
   end if
   rs.close
   set rs=nothing
   conn.close
   set conn=nothing
   str=replace(str,"'","")
   str=replace(str,"""","")
   response.Write("<script>parent.daochu("&page&",'"&str&"')</script>")
   %>
   <script>
   
   </script>
</body>
</html>
