<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!--#include file="../../include/include.asp"-->
<%
if request("subid")<>"" then
sql="update cate_qx set subid='"&request("subid")&"' where id="&request("id")
conn.execute(sql)
sql="update cate_qx set subid='1' where code='"&request("subid")&"'"
conn.execute(sql)
response.Write("<script>alert('���óɹ�!')</script>")
response.Write("<script>opener.location.reload()</script>")
response.Write("<script>window.close()</script>")

end if
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>�ޱ����ĵ�</title>
<link href="../../../default.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
td {
	font-size: 12px;
	line-height: 22px;
}
-->
</style>
<link href="../../inc/Style.css" rel="stylesheet" type="text/css">
<link href="../../FORUM.CSS" rel="stylesheet" type="text/css">
<link href="../../css.css" rel="stylesheet" type="text/css">
</head>

<body bgcolor="7386A5" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="98%" height="100%" border="0" align="center" cellpadding="5" cellspacing="1">
  <form name="form1" method="post" action="">
    <tr>
      <td height="10" align="center" bgcolor="#7386A5">
	  </td>
    </tr>
    <tr>
    <td align="center" bgcolor="#E7EBDE">
      ѡ�����Ŀ����Ŀ¼
        <select name="subid" id="subid">
	  <%
	  sql="select * from cate_qx where code like '"&left(request("code"),2)&"%' and (subid=0 or subid=1 or subid is null)"
	  set rsw=server.CreateObject("adodb.recordset")
	rsw.open sql,conn,1,3
						if not rsw.eof or not rsw.bof then
						i=0
						do while not rsw.eof
	  %>
      <option value="<%=rsw("code")%>"><%=rsw("meno")%></option>
	  <%
					  rsw.movenext
						i=i+1
						loop
					  end if
					   rsw.close
					   set rsw=nothing
						 %>
      </select>
        <input type="hidden" name="id" value="<%=request("id")%>">
    </td>
  </tr>
  <tr>
    <td align="center" bgcolor="#E7EBDE"><input name="Submit" type="submit" class="button01-out" value="�ύ">
      <input name="Submit2" type="button" class="button01-out" onClick="window.close()" value="�ر�">
      <input name="Submit3" type="button" class="button02-out" value="ȡ���ô�Ŀ¼" onClick="window.location='admin_area_sub.asp?subid=0&id=<%=request("id")%>'"></td>
  </tr>
  <tr>
    <td height="10" align="center" bgcolor="#7386A5">
	</td>
  </tr>
  </form>
</table>
</body>
</html><!-- #include file="../../../conn_end.asp" -->