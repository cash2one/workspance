<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!-- #include file="../../../cn/function.asp" -->
<%
if request.Form("usertel")<>"" then
	sql="update users set usertel='"&request.Form("usertel")&"' where id="&session("personid")&""
	conn.execute(sql)
	
	response.Write("<script>alert('保存成功');parent.document.getElementById('alertmsg').style.display='none';</script>")
	response.Write("<script>parent.document.getElementById('page_cover').style.display='none';</script>")
end if
sql="select usertel from users where id="&session("personid")&""
set rs=conn.execute(sql)
if not rs.eof or not rs.bof then
	usertel=rs(0)
end if
rs.close
set rs=nothing
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
<style>
.err
{
	font-size: 16px;
	line-height: 30px;
	font-weight: bold;
	color: #F00;
}
</style>
<script>
function strmit(frm)
{
	if (frm.usertel.value=="")
	{
		alert("请输入8位固定号码！")
		frm.usertel.focus();
		return false;
	}
	if (frm.usertel.value.length<8)
	{
		alert("请输入8位固定号码！")
		return false;
	}
}
</script>
</head>

<body>
<form id="form1" name="form1" method="post" action="mytel.asp" onSubmit="return strmit(this)">
<table width="100%" border="0" cellspacing="0" cellpadding="5">
  <tr>
    <td colspan="2">
    <div class="err">
    对不起！你的座机号码是否正确，请确认。<br />
      如果你的座机号码有更改请及时调整。谢谢合作！
    </div>
    </td>
    </tr>
  <tr>
    <td width="150" align="right">你的电话</td>
    <td>
      <input name="usertel" type="text" id="usertel" maxlength="8" value="<%=usertel%>"/>
      <br />
      <strong>(必须是8位固定号码如：56209898) </strong></td>
  </tr>
  <tr>
    <td align="right">&nbsp;</td>
    <td><input type="submit" name="button" id="button" value="提交" /></td>
  </tr>
</table></form>
</body>
</html>
<%
conn.close
set conn=nothing
%>
