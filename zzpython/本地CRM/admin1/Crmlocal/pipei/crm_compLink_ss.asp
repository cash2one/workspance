<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<%
if request.Form("add")<>"" then
	ssdetail=request.Form("ssdetail")
	ssdetail=replace(ssdetail,"'","”")
	ssdetail=replace(ssdetail,",","。")
    sqlt="insert into crm_compLink_ss(com_id,ssdetail,GroupID,personid) values("&request.Form("com_id")&",'"&ssdetail&"','"&request.Form("GroupID")&"',"&session("personid")&")"
	conn.execute(sqlt)
	sql="update crm_compLink set ssFlag=1 where GroupID='"&request.Form("GroupID")&"' and com_id="&request.Form("com_id")&""
	conn.execute(sql)
	sql="update crm_compLink_main set ssFlag=1 where GroupID='"&request.Form("GroupID")&"'"
	conn.execute(sql)
	response.Write("<script>alert('提交成功！')</script>")
end if
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
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
function sssubit(frm)
{
	if (frm.ssdetail.value=="")
	{
		alert("请输入申述内容!");
		return false;
	}
}
</script>
</head>

<body>
<form id="form1" name="form1" method="post" action="" onsubmit="return sssubit(this)">

申诉原因
  <input type="submit" name="button" id="button" value="提交申述" />
  <input name="add" type="hidden" id="add" value="1" />
  <input type="hidden" name="GroupID" id="GroupID" value="<%=request("GroupID")%>"/>
  <br>      
<input type="hidden" name="com_id" id="com_id" value="<%=request("com_id")%>"/>
<textarea name="ssdetail" id="ssdetail" cols="45" rows="4" style="width:100%"></textarea>
</form>
</body>
</html>
<%
conn.close
set conn=nothing
%>
