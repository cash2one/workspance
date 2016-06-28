<!-- #include file="../include/adfsfs!@#.asp" -->


<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>打印公司列表</title>
<style type="text/css">
<!--
body {
	margin-left: 2px;
	margin-top: 2px;
	margin-right: 2px;
	margin-bottom: 2px;
}
td {
	font-size: 12px;
	line-height: 20px;
}
-->
</style>
<style media="print">
.Noprint{display:none;}
.PageNext{
	page-break-after: always;
}
</style>
<!-- #include file="../../cn/function.asp" -->
<!-- #include file="../include/include.asp" -->
</head>

<body>
<%

sqlu="select realname,id from users where closeflag=1 and userid=21 "
						set rsu=server.CreateObject("ADODB.recordset")
						rsu.open sqlu,conn,1,1
						if not rsu.eof then
						do while not rsu.eof
%>
<table width="100%" border="0" cellspacing="5" cellpadding="0">
<form name="form1" method="post" target="_blank" action="Activecrm_comp_print.asp">
  <tr>
    <td align="center"><input name="realname" type="hidden" id="realname" value="<%=rsu("realname")%>">
    <input name="id" type="hidden" id="id" value="<%=rsu("id")%>">
    <input type="button" name="Submit" value="<%=rsu("realname")%>" onClick="window.open('ActiveCrm_comp_print.asp?id=<%=rsu("id")%>&realname=<%=rsu("realname")%>','','')">
    <input name="sdate" type="text" id="sdate" value="<%=date()%>" size="10">
    <select name="AssignTimes">
			<option value="">选择次数...</option>
			<%
			sqlt="select AssignTimes from crm_Active_AssignTimes where id>1"
			set rst=conn.execute(sqlt)
			while not rst.eof
			%>
              <option value="<%=rst("AssignTimes")%>" <%if cstr(request("AssignTimes"))=cstr(rst(0)) then response.Write("selected")%>><%=rst("AssignTimes")%></option>
			<%
			rst.movenext
			wend
			rst.close
			set rst=nothing
			%>
        </select>
    
      <input type="submit" name="Submit2" value="提交打印">
   
    </td>
  </tr> </form>
</table>
<%
rsu.movenext
						loop
						end if
						rsu.close()
						set rsu=nothing
%>
</body>
</html>
