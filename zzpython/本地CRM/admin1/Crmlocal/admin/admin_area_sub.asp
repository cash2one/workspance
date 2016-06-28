<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!--#include file="../../include/include.asp"-->
<%
if request("subid")<>"" then
	sql="update cate_qx set parent_id="&request("subid")&" where id="&request("id")
	conn.execute(sql)
	response.Write("<script>alert('设置成功!')</script>")
	response.Write("<script>opener.location.reload()</script>")
	response.Write("<script>window.close()</script>")
end if
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>更改目录</title>
<link href="../default.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
td {
	font-size: 12px;
	line-height: 22px;
}
-->
</style>
<link href="inc/Style.css" rel="stylesheet" type="text/css">
<link href="FORUM.CSS" rel="stylesheet" type="text/css">
<link href="css.css" rel="stylesheet" type="text/css">
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
      选择该栏目的主目录
        <select name="subid" id="subid">
	<%
	parent_id=request.QueryString("parent_id")
	sql="select id,meno from cate_qx where parent_id=0 and closeflag=1 order by ord asc"
	set rsw=server.CreateObject("adodb.recordset")
	rsw.open sql,conn,1,1
	if not rsw.eof or not rsw.bof then
		do while not rsw.eof
		  %>
		  <option value="<%=rsw("id")%>"><%=rsw("meno")%></option>
		  <%
		  sql1="select id,meno from cate_qx where parent_id="&rsw("id")&" and closeflag=1 order by ord asc"
		  set rs1=conn.execute(sql1)
		  if not rs1.eof or not rs1.bof then
		  	while not rs1.eof
			%>
				<option value="<%=rs1("id")%>">┆&nbsp;&nbsp;┿<%=rs1("meno")%></option>
			<%
            rs1.movenext
			wend
		  end if
		  rs1.close
		  set rs1=nothing
		rsw.movenext
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
    <td align="center" bgcolor="#E7EBDE"><input name="Submit" type="submit" class="button01-out" value="提交">
      <input name="Submit2" type="button" class="button01-out" onClick="window.close()" value="关闭">
      <input name="Submit3" type="button" class="button02-out" value="取消该次目录" onClick="window.location='admin_area_sub.asp?subid=0&id=<%=request("id")%>'"></td>
  </tr>
  <tr>
    <td height="10" align="center" bgcolor="#7386A5">
	</td>
  </tr>
  </form>
</table>
</body>
</html>
<%
conn.close
set conn=nothing
%>
