<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../../include/ad!$#5V.asp" -->
<%
sqluser="select realname,ywadminid,xuqianFlag,adminuserid,partid from users where id="&session("personid")
 set rsuser=conn.execute(sqluser)
 userName=rsuser(0)
 ywadminid=rsuser(1)
 xuqianFlag=rsuser(2)
 partuserid=rsuser(3)
 adminuserid=rsuser("adminuserid")
 adminmypartid=rsuser("partid")
 rsuser.close
 set rsuser=nothing
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>每日有效联系客户统计</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
body,td,th {
	font-size: 12px;
}
-->
</style>
<script>
function openusre(code)
{
	var obj=document.getElementById("us"+code)
	if (obj.style.display=='')
	{
		obj.style.display="none"
	}else
	{
		obj.style.display=""
	}
}
</script>
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="200" valign="top"><table width="100%" border="0" cellpadding="4" cellspacing="1" bgcolor="#666666">
	<%
	if ywadminid<>"" and not isnull(ywadminid)  then
		  sqlc="select code,meno from cate_adminuser where code in("&ywadminid&") and closeflag=1"
	  else
		  if session("userid")="10" then
			 sqlc="select code,meno from cate_adminuser where code like '13%' and closeflag=1 "
		  else
			 sqlc="select code,meno from cate_adminuser where code like '"&session("userid")&"%' and closeflag=1"
		  end if
	  end if
	set rs=conn.execute(sqlc)
	if not rs.eof or not rs.bof then
	while not rs.eof
	%>
      <tr>
        <td bgcolor="#CCCCCC" onclick="openusre(<%=rs("code")%>)"><%=rs("meno")%></td>
      </tr>
      <tr id="us<%=rs("code")%>" style="display:none">
        <td bgcolor="#FFFFFF">
		<table width="100%" border="0" cellpadding="2" cellspacing="1" bgcolor="#CCCCCC">
      <%
	  sqlu=""
	  
	  sqlu="select realname,id from users where closeflag=1 and userid="&rs("code")&""&sqlu
	  set rsu=server.CreateObject("ADODB.recordset")
	  rsu.open sqlu,conn,1,1
	  if not rsu.eof then
	  do while not rsu.eof
	  %>
      <tr>
        <td bgcolor="#FFFFFF">&nbsp;&nbsp;
          <a href="index.asp?personid=<%=rsu("id")%>" target="main"><%response.Write(rsu("realname"))%></a></td>
      </tr>
	  <%
	  rsu.movenext
	  loop
	  end if
	  rsu.close()
	  set rsu=nothing
	  %>
        </table></td>
      </tr>
	  
	<%
	rs.movenext
	wend
	end if
	rs.close
	set rs=nothing
	%>
    </table></td>
    <td height="600" valign="top">
	  <iframe id="main" name="main"  style="HEIGHT: 100%; VISIBILITY: inherit; WIDTH: 100%; Z-INDEX: 2" frameborder="0" src=""></iframe>
	</td>
  </tr>
</table>
</body>
</html>
<%
conn.close
set conn=nothing
%>