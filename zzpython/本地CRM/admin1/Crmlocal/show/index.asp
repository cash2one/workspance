<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/adfsfs!@#.asp" -->
<!--#include file="../inc.asp"-->
<%
loclurl="commType"
txtname="xzproductLy"
MakeHtmltime=60000*60*60
if ExistsTxt(loclurl,txtname,MakeHtmltime)<>0 then
	Htmltemp=creatText2008(loclurl,txtname,"",MakeHtmltime)
end if
'response.Write(Htmltemp)
if request.Form("code")<>"" then
	DelTxt2008 loclurl,txtname 
	Htmltemp=creatText2008(loclurl,txtname,replace(request.Form("code")," ",""),MakeHtmltime)
end if
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
-->
</style>
<script>
function selectCheckBox(boxname,thevalue)
{
	var boxes = document.form1.elements[boxname];
	for(var i=0;i<boxes.length;i++){
		var arrvv=thevalue.split(",")
		for(var n=0;n<arrvv.length;n++)
		{
			//alert(arrvv[n]);
			//return false;
			if (arrvv[n].toString()==boxes[i].value.toString())
			{
				boxes[i].checked = true;
			}
		}
		//if(thevalue.indexOf(boxes[i].value)>=0)
//		{
//			boxes[i].checked = true;
//		}
	}
}
</script>
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><strong>展会客户限制被查看设置</strong></td>
  </tr>
</table>
<form id="form1" name="form1" method="post" action="">
<%
sql="select code,meno from cate_product_ly where code like '__'"
set rs=conn.execute(sql)
if not rs.eof or not rs.bof then
while not rs.eof
%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="10">&nbsp;</td>
    <td><input type="checkbox" name="code" id="code" value="<%=rs("code")%>" /><%=rs("meno")%></td>
  </tr>
</table>
<%
sql1="select code,meno from cate_product_ly where code like '"&rs("code")&"__'"
set rs1=conn.execute(sql1)
if not rs1.eof or not rs1.bof then
while not rs1.eof
%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="30">&nbsp;</td>
    <td><input type="checkbox" name="code" id="code" value="<%=rs1("code")%>" /><%=rs1("meno")%></td>
  </tr>
</table>
<%
sql2="select code,meno from cate_product_ly where code like '"&rs1("code")&"__'"
set rs2=conn.execute(sql2)
if not rs2.eof or not rs2.bof then
while not rs2.eof
%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="50">&nbsp;</td>
    <td><input type="checkbox" name="code" id="code" value="<%=rs2("code")%>" /><%=rs2("meno")%></td>
  </tr>
</table>
<%
rs2.movenext
wend
end if
rs2.close
set rs2=nothing
%>
<%
rs1.movenext
wend
end if
rs1.close
set rs1=nothing
%>
<%
rs.movenext
wend
end if
rs.close
set rs=nothing
%>
<input type="submit" name="button" id="button" value="提交" />
</form>
<script>
selectCheckBox("code","<%=Htmltemp%>")
</script>
</body>
</html>
<%
conn.close
set conn=nothing
%>
