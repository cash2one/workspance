<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!--#include file="../../include/include.asp"-->
<!--#include file="../../include/pagefunction.asp"-->
<!--#include file="../inc.asp"-->
<%
sqldata=request.Form("sqldata")
pagerecord=50
rn=pagerecord
Set oPage=New clsPageRs2
With oPage
 .sltFld  = "id"
 .FROMTbl = "renshi_user"
 .sqlOrder= "order by id desc"
 .sqlWhere= "WHERE  id>0 "&sqldata
 .keyFld  = "id"    '不可缺少
 .pageSize= pagerecord
 .getConn = conn
 Set Rs  = .pageRs
End With
total=oPage.getTotalPage
totalpg=cint(total/pagerecord)+1

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>数据导出</title>
<script>
function daochu(page,str)
{
	if (page>=<%=totalpg%> && page!="0")
	{
		document.getElementById("fxtxt").innerHTML="导出完毕！"
		document.getElementById("loaddown").style.display="none"
		document.getElementById("form2").style.display="";
		document.getElementById("usercontent").value+=str;
		return false;
	}else
	{
		document.getElementById("page").value=page+1;
		document.getElementById("form1").submit();
		document.getElementById("usercontent").value+=str;
		document.getElementById("form2").style.display="none";
	}
}
</script>
<style type="text/css">
<!--
body,td,th {
	font-size: 12px;
}
form
{
	padding:0px;
	margin:0px;
}
-->
</style></head>

<body>
<table width="100%" border="1" cellspacing="0" cellpadding="4">
  <tr>
    <td><div id="fxtxt">共 <%=totalpg%> 页</div></td>
  </tr>
  <tr>
    <td>
    <div id="loaddown">
    <form id="form1" name="form1" method="post" action="userdownload.asp" target="down">
      <input type="hidden" name="datalist" id="datalist" value="<%=sqldata%>"/>
      正在下载
      第
      <input name="page" type="text" id="page" value="1" size="10" readonly="readonly">
    页
    数据......
    </form>
    </div>
    </td>
  </tr>
  <tr>
    <td>
    <form id="form2" name="form2" method="post" action="userdownload_show.asp" style="display:none">
      <input type="hidden" name="usercontent" id="usercontent" value=""/>
      <input type="submit" name="button" id="button" value="下载数据" />
    </form>
    </td>
  </tr>
</table>

<iframe id="down" name="down"  style="HEIGHT: 0px; VISIBILITY: inherit; WIDTH: 0px; Z-INDEX: 2" frameborder="0" src=""></iframe>
<%
rs.close
set rs=nothing
conn.close
set conn=nothing
%>
<script>
daochu(0,"");
</script>
</body>
</html>
