<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!--#include file="../../../cn/sources/Md5.asp"-->
<!--#include file="../../include/include.asp"-->
<!--#include file="../../include/pagefunction_single.asp"-->
<!--#include file="../inc.asp"-->
<%
 sqluser="select realname,ywadminid,xuqianFlag,adminuserid from users where id="&session("personid")
 set rsuser=conn.execute(sqluser)
 userName=rsuser(0)
 ywadminid=rsuser(1)
 xuqianFlag=rsuser(2)
 partuserid=rsuser(3)
 adminuserid=rsuser("adminuserid")
 rsuser.close
 set rsuser=nothing
 n=request.QueryString("n")
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
<style type="text/css">
<!--
body,td,th {
	font-size: 12px;
}
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
<SCRIPT language=JavaScript src="../../main.js"></SCRIPT>
</head>

<body>
<table border="0" align="center" cellpadding="0" cellspacing="0">
<form id="form2" name="form2" method="get" action="selectuser1.asp">
  <tr>
    <td>部门
<select name="userid" class="button" id="userid" >
              <option value="" >请选择--</option>
			  <% If session("userid")="13" Then %>
			  	<option value="<%=session("personid")%>" ><%=userName%></option>
			  <% End If %>
  <%
			  if ywadminid<>"" and not isnull(ywadminid)  then
			      sqlc="select code,meno from cate_adminuser where code in("&ywadminid&") and  closeflag=1"
			  else
				  if session("userid")="10" then
				  	 sqlc="select code,meno from cate_adminuser where code like '13%'"
				  else
				  	 sqlc="select code,meno from cate_adminuser where code like '"&session("userid")&"%'"
				  end if
			  end if
			  set rsc=conn.execute(sqlc)
			  if not rsc.eof then
			  while not rsc.eof
			  %>
			  <option value="<%response.Write(rsc("code"))%>"><%response.Write(rsc("meno"))%></option>
                        
              <%
					
				rsc.movenext
				wend
				end if
				rsc.close
				set rsc=nothing
					 %>
            </select>
    <script>selectOption("userid","<%=request.QueryString("userid")%>")</script>
      <br />
      姓名
<input name="realname" type="text" id="realname" size="10" />
<input type="hidden" name="n" id="n" value="<%=n%>" />
<input type="submit" name="button" id="button" value="搜索" /></td>
  </tr></form>
</table>
<%
sear="n="
sql=""
if request.QueryString("userid")<>"" then
						sql=sql&" and userid like '"&request.QueryString("userid")&"%'"
						sear=sear&"&userid="&request.QueryString("userid")
					end if
					if request.QueryString("sex")<>"" and request.QueryString("sex")<>"1" then
						sql=sql&" and sex='"&request.QueryString("sex")&"'"
						sear=sear&"&sex="&request.QueryString("sex")
					end if
					if request.QueryString("renshi_code")<>"" then
						sql=sql&" and renshi_code='"&request.QueryString("renshi_code")&"'"
						sear=sear&"&renshi_code="&request.QueryString("renshi_code")
					end if
					if request.QueryString("realname")<>"" then
						sql=sql&" and realname like '%"&request.QueryString("realname")&"%'"
						sear=sear&"&realname="&request.QueryString("realname")
					end if
					
				    if request.QueryString("onstaton")="1" then
						sql=sql&" and id not in (select id from users where renshi_zaizhi='1203')"
						sear=sear&"&onstaton="&request.QueryString("onstaton")
					end if
					if request.QueryString("renshi_zaizhi")<>"" then
						sql=sql&" and renshi_zaizhi='"&request.QueryString("renshi_zaizhi")&"'"
						sear=sear&"&renshi_zaizhi="&request.QueryString("renshi_zaizhi")
					end if
					if request.QueryString("renshi_guanli")<>"" then
						sql=sql&" and renshi_guanli='"&request.QueryString("renshi_guanli")&"'"
						sear=sear&"&renshi_guanli="&request.QueryString("renshi_guanli")
					end if
					if request.QueryString("renshi_shiyong")<>"" then
						sql=sql&" and renshi_shiyong='"&request.QueryString("renshi_shiyong")&"'"
						sear=sear&"&renshi_shiyong="&request.QueryString("renshi_shiyong")
					end if
					'response.Write(sql)
  Set oPage=New clsPageRs2
   With oPage
	 .sltFld  = "id,userid,realname"
	 .FROMTbl = "users"
	 .sqlOrder= "order by userid desc,id asc"
	 .sqlWhere= "WHERE  closeflag=1 and userid in ("&ywadminid&")"&sql
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
<script>
function getpersonname(obj)
{
	var pobj=parent.form<%=n%>.saler;
	var tobj=parent.form<%=n%>.personid;
	//alert(obj.value)
	//var uobj=parent.document.getElementById("userid");
	pobj.value=obj.title;
	tobj.value=obj.value;
	//uobj.value=obj.alt;
}
</script>
<table border="0" align="center" cellpadding="3" cellspacing="1">
  <tr>
    <td>&nbsp;</td>
    <td>姓名</td>
  </tr>
  <form id="form1" name="form1" method="post" action=""><%
  
    if not rs.eof  then 
	Do While Not rs.EOF
  %>
  <tr>
    <td><input type="radio" name="radio" id="radio" value="<%=rs("id")%>" title="<%=rs("realname")%>" alt="<%=rs("userid")%>" onClick="getpersonname(this)"/></td>
    <td><%=rs("realname")%></td>
    </tr>
  <%
  rs.movenext
	i=i+1
	loop 
 END IF
  %></form>
</table>
</body>
</html>
