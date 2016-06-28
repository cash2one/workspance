<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/adfsfs!@#.asp" -->
<!-- #include file="../../../cn/function.asp" -->
<!-- #include file="../../jumptolog.asp" -->
<!--#include file="../../include/include.asp"-->
<!--#include file="../inc.asp"-->
<%
if request.Form("add")="1" then
	comp_sale_type=request.Form("comp_sale_type")
	tuohuireson=request.Form("tuohuireson")
	tuohuireson=replace(tuohuireson,"''","""")
	com_email=request.Form("com_email")
	sql="select com_id from comp_info where com_email='"&com_email&"'"
	set rs=conn.execute(sql)
	if not rs.eof or not rs.bof then
		com_id=rs(0)
	else
		response.Write("<script>alert('该邮箱不存在！');window.history.back(1);</script>")
		response.End()
	end if
	rs.close
	set rs=nothing
	if comp_sale_type="" or isnull(comp_sale_type) then
	else
		sqlppp="insert into crm_tuo_hui_comp(com_id,personid,comp_sale_type,reson) values("&com_id&","&session("personid")&",'"&comp_sale_type&"','"&tuohuireson&"')"
		'response.Write(sqlppp)
		conn.execute(sqlppp)
		response.Redirect("tuohuilist.asp")
	end if
end if
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
<SCRIPT language=JavaScript src="../../main.js"></SCRIPT>
<SCRIPT language=javascript src="../../DatePicker.js"></SCRIPT>
<link href="../../datepicker.css" rel="stylesheet" type="text/css">
<link href="../../main.css" rel="stylesheet" type="text/css">
<link href="../../color.css" rel="stylesheet" type="text/css">
</head>

<body><form id="form1" name="form1" method="post" action="">
<br />
<br />
<br />
<table width="500" border="0" align="center" cellpadding="5" cellspacing="1" bgcolor="#666666">
  <tr>
    <td width="150" align="right" bgcolor="#FFFFFF">邮箱</td>
    <td bgcolor="#FFFFFF">
      <input type="text" name="com_email" id="com_email" />
      <input name="add" type="hidden" id="add" value="1" /></td>
  </tr>
  <tr>
    <td align="right" bgcolor="#FFFFFF">是否拖单/毁单</td>
    <td bgcolor="#FFFFFF"><input type="radio" name="comp_sale_type" id="comp_sale_type" value="">无
                            <input type="radio" name="comp_sale_type" id="comp_sale_type" value="拖单">
                            拖单
                            <input type="radio" name="comp_sale_type" id="comp_sale_type" value="毁单">
      毁单</td>
  </tr>
  <tr>
    <td align="right" bgcolor="#FFFFFF">拖单/毁单原因</td>
    <td bgcolor="#FFFFFF"><input name="tuohuireson" type="text" id="tuohuireson" size="40" /></td>
  </tr>
  <tr>
    <td align="right" bgcolor="#FFFFFF">&nbsp;</td>
    <td bgcolor="#FFFFFF"><input type="submit" name="button" id="button" value="提交" /></td>
  </tr>
</table>
</form>
</body>
</html>
<%
conn.close
set conn=nothing
%>
