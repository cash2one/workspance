<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/adfsfs!@#.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<%
'if session("userid")<>"10" then
'	response.Write("Ϊ�˿ͻ����밲ȫ��������Ĺ����Ѿ��رա��뵽��վ�ϵġ��������롱���ÿͻ�ȥ��ѯ���룡")
'	response.End()
'end if
'if session("personid")="93" or session("userid")="10" then
'else
'	response.End()
'end if
if trim(request.Form("email"))<>"" then
	sqlc="select * from crm_searchPass where com_email='"&trim(request.Form("email"))&"' and personid="&session("personid")&" and Ldate='"&date()&"'"
	set rsc=conn.execute(sqlc)
	if not (rsc.eof or rsc.bof) then
		sqlt="update crm_searchPass set lcount=lcount+1 where com_email='"&trim(request.Form("email"))&"' and personid="&session("personid")&" and Ldate='"&now&"'"
		conn.execute(sqlt)
	else
		sqlt="insert into crm_searchPass(personid,com_email,Ldate) values("&session("personid")&",'"&trim(request.Form("email"))&"','"&now&"')"
		conn.execute(sqlt)
	end if
	rsc.close
	set rsc=nothing
	response.Redirect("http://admin.zz91.com/admin1/pass/search.asp?email="&trim(request.Form("email")))
end if
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>�ͻ������ѯ</title>
<style type="text/css">
<!--
.pass {
	font-size: 14px;
	line-height: 30px;
	font-weight: bold;
	background-color: #FFFFCC;
	border: 1px solid #FF9900;
	width: 500px;
	background-position: center;
	text-align: center;
}
-->
</style>
</head>

<body>
<table width="100%" border="0" height="100%" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center">
    <form name="form1" method="post" action="">
    <table width="500" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="50" align="center">Email��
          
            <input type="text" name="email" id="email" value="<%=request.Form("email")%>"> &nbsp;
            <input type="submit" name="button" id="button" value="�ύ��ѯ">          </td>
      </tr>
      <tr>
        <td height="50" align="center">
        <%if trim(request.Form("email"))<>"" then%>
        	<%if  passflag=1 then%>
        	<div class="pass">��鵽������Ϊ��<font color="#FF0000"><%=compass%></font></div>
            <%else%>
            <div class="pass">û���ҵ���Ӧ����,��˹�˾δע��</div>
            <%end if%>
        <%end if%>
        </td>
      </tr>
    </table>
    </form>
    </td>
  </tr>
</table>
</body>
</html>
<%
conn.close
set conn=nothing
%>
