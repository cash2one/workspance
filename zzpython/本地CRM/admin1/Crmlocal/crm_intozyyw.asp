<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../include/adfsfs!@#.asp" -->
<!-- #include file="../localjumptolog.asp" -->
<!-- #include file="../../cn/function.asp" -->
<!--#include file="../include/include.asp"-->
<!--#include file="inc.asp"-->
<%
comidlist=request.Form("comidlist")
if comidlist<>"" then comidlist=left(comidlist,len(comidlist)-1)
keywords=request.Form("keywords")
putc=request.Form("putc")

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>数据导入</title>
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
form
{
	margin:0px;
	padding:0px;
}
-->
</style>
<script>
function startdaoru()
{
	document.getElementById("suc1").innerHTML='正在导入...';
	document.getElementById("form1").submit();
}
</script>
</head>

<body>

<%
if comidlist<>"" then
	response.Write("<script>parent.document.getElementById('suc"&cint(putc)+1&"').innerHTML='正在导入...';</script>")
	sqltemp="insert into temp_gonghai_keywords(keywords,com_id) select '"&trim(keywords)&"',com_id from comp_info where com_id in ("&comidlist&") and com_id not in(select com_id from temp_gonghai_keywords where keywords='"&trim(keywords)&"' and com_id in ("&comidlist&"))"
	'response.Write(sqltemp)
	conn.execute(sqltemp)
	sqle="select count(0) from temp_gonghai_keywords where keywords='"&trim(keywords)&"' and com_id in ("&comidlist&")"
	set rse=conn.execute(sqle)
	daorecount=rse(0)
	rse.close
	set rse=nothing
	response.Write("<script>parent.document.getElementById('suc"&cint(putc)&"').innerHTML='导入成功"&daorecount&"';</script>")
	response.Write("<script>parent.document.getElementById('form"&cint(putc)+1&"').submit();</script>")
	response.End()
end if
if keywords<>"" then
	Curl="http://192.168.2.2/admin1/crmlocal/crmlist.asp?keywords="&keywords
	read=getHTTPPage(Curl)
	if read<>"" then
		read=left(read,len(read)-1)
	end if
end if

%><form id="form0" name="form0" method="post" action="">
<table width="500" border="1" align="center" cellpadding="5" cellspacing="0">
  <tr>
    <td width="100">输入关键字</td>
    <td>
      <input type="text" name="keywords" id="keywords" value="<%=keywords%>" />
      <input type="submit" name="button" id="button" value="搜索" /> </td>
  </tr>
</table></form>

<%
if read<>"" then
	arrread=split(read,",")
	comcount=ubound(arrread)
end if
n=cdbl(comcount) /2000 +1
%>
<table width="500" border="1" align="center" cellpadding="5" cellspacing="0">
  <tr>
    <td width="100">搜索数量</td>
    <td><input type="hidden" name="comidlist" id="comidlist" value="<%=read%>" />
    <%=comcount%>
    <input type="button" name="button2" id="button2" value="导入" onClick="startdaoru()" /></td>
  </tr>
  <%
if read<>"" then
  for i=1 to n
  comidlist=""
  if i<n-1 then
  	tolen=i*2000
  else
  	tolen=comcount
  end if
  
  for a=(i-1)*2000 to tolen
  	comidlist=comidlist&arrread(a)&","
  next
  %>
  <form id="form<%=i%>" name="form<%=i%>" method="post" action="" target="daoru<%=i%>"><tr>
    <td>前<%=i*2000%></td>
    <td><input type="hidden" name="comidlist" id="comidlist" value="<%=comidlist%>" />
      <input type="hidden" name="putc" id="putc" value="<%=i%>" />
      <input type="hidden" name="keywords" id="keywords" value="<%=keywords%>" />
      <div id="suc<%=i%>"></div><iframe name="daoru<%=i%>" src="" frameborder='0' width="0px" height="0px" scrolling="no" ></iframe>
      &nbsp; </td>
  </tr></form>
  <%next%>
<%end if%>
</table>

</body>
</html>
<%
conn.close
set conn=nothing
%>
