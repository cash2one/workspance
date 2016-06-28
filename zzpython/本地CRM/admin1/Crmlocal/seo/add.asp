<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!--#include file="../../include/include.asp"-->
<!-- #include file="../../../cn/function.asp" -->
<!--#include file="../inc.asp"-->
<%
com_id=0
if request("com_email")<>"" then
	sql="select com_email,com_id,com_name from temp_salescomp where com_email='"&request("com_email")&"'"
	set rs=conn.execute(sql)
	if not rs.eof or not rs.bof then
		com_email=rs("com_email")
		com_id=rs("com_id")
		comname=replacequot(trim(rs("com_name")))
	end if
	personid=session("personid")
	rs.close
	set rs=nothing
	action="add"
end if
if personid="" then personid=session("personid")
if pcheck="" then pcheck="0"
sqlp="select realname from users where id="&personid&""
set rsp=conn.execute(sqlp)
if not rsp.eof or not rsp.bof then
	realname=rsp(0)
end if
rsp.close
set rsp=nothing

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
<SCRIPT language=javascript src="/cn/sources/pop.js"></SCRIPT>
<SCRIPT language=JavaScript src="/admin1/main.js"></SCRIPT>
<SCRIPT language=javascript src="/admin1/crmlocal/DatePicker.js"></SCRIPT>
<SCRIPT language=javascript src="/admin1/crmlocal/js/province.js"></SCRIPT>
<SCRIPT language=javascript src="/admin1/crmlocal/js/compkind.js"></SCRIPT>
<SCRIPT language=javascript src="/admin1/crmlocal/js/list.js"></SCRIPT>
<link href="../datepicker.css" rel="stylesheet" type="text/css">
<SCRIPT language=javascript src="../DatePicker.js"></SCRIPT>
<style>
.input
{
	width:90%
}
.red
{
	color:#F00
}
</style>
<link href="s.css" rel="stylesheet" type="text/css" />
<script>
function chknum(NUM) 
{ 
	var i,j,strTemp; 
	strTemp=".0123456789"; 
	if ( NUM.length== 0) 
	return 0 
	for (i=0;i<NUM.length;i++) 
	{ 
		j=strTemp.indexOf(NUM.charAt(i)); 
		if (j==-1) 
		{ 
			//说明有字符不是数字 
			return 0; 
		} 
	} 
	//说明是数字 
	return 1; 
}
function searchemail(frm)
{
	frm.action="add.asp"
	frm.submit();
}
function frmsubmit(frm)
{
	
	if(frm.com_email.value=="")
	{
		alert("请输入邮箱!");
		frm.com_email.focus();
		return false;
	}
	if(frm.cname.value=="")
	{
		alert("请输入公司名称!");
		frm.cname.focus();
		return false;
	}
	
	if(frm.keywords.value=="")
	{
		alert("请输入关键字!");
		frm.keywords.focus();
		return false;
	}
	if(frm.com_msb.value=="")
	{
		alert("请输入门市部!");
		frm.com_msb.focus();
		return false;
	}
	if(frm.price.value=="")
	{
		alert("请输入销售金额!");
		frm.price.focus();
		return false;
	}
	if(frm.lpersonid.value=="")
	{
		alert("请优化人!");
		frm.lpersonid.focus();
		return false;
	}
}
</script>
</head>

<body>

<table width="600" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#666666">
<form id="form2" name="form2" method="post" action="" onSubmit="return searchemail(this)">
  <tr>
    <td bgcolor="#FFFFFF">&nbsp;</td>
    <td bgcolor="#FFFFFF"><a href="list.asp">返回列表</a></td>
  </tr>
  <tr>
    <td width="100" bgcolor="#FFFFFF">邮箱</td>
    <td bgcolor="#FFFFFF">
    
    <input name="com_email" type="text" id="com_email" value="<%=com_email%>" size="40" />
    <input type="hidden" name="com_id" id="com_id" value="<%=com_id%>" /><input type="submit" name="button3" id="button3" value="搜索" />
      <br />
      输入客户的邮箱点击搜索即可读取客户信息
    </td>
  </tr>
</form>
<form id="form1" name="form1" method="post" action="save.asp" onSubmit="return frmsubmit(this)">
  <tr>
    <td bgcolor="#FFFFFF">公司名</td>
    <td bgcolor="#FFFFFF">
    <input name="com_email" type="text" id="com_email" value="<%=com_email%>" size="40" />
    <input type="hidden" name="com_id" id="com_id" value="<%=com_id%>" />
    <input name="cname" type="text" class="input" id="cname" value="<%=comname%>" maxlength="50"/> 
      <a href="/admin1/crmlocal/crm_cominfoedit.asp?idprod=<%=com_id%>&amp;dotype=my&amp;lmcode=134" target="_blank">查看</a></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">关键词</td>
    <td bgcolor="#FFFFFF">
      <input type="text" name="keywords" id="keywords" class="input" />
      <br />
      多关键字用&ldquo;|&rdquo;隔开</td>
  </tr>
  
  <tr>
    <td bgcolor="#FFFFFF">开始优化时间</td>
    <td bgcolor="#FFFFFF"><script language=javascript>createDatePicker("seo_start",true,"",false,true,true,true)</script></td>
  </tr><tr>
    <td bgcolor="#FFFFFF">保证达标时间</td>
    <td bgcolor="#FFFFFF"><script language=javascript>createDatePicker("target_assure",true,"",false,true,true,true)</script></td>
  </tr>
  
  <!--<tr>
    <td bgcolor="#FFFFFF">达标时间</td>
    <td bgcolor="#FFFFFF"><script language=javascript>createDatePicker("target_time",true,"",false,true,true,true)</script></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">过期时间</td>
    <td bgcolor="#FFFFFF"><script language=javascript>createDatePicker("expire_time",true,"",false,true,true,true)</script></td>
  </tr>-->
  <tr>
    <td bgcolor="#FFFFFF">门市部</td>
    <td bgcolor="#FFFFFF">HTTP://
      <input type="text" name="com_msb" id="com_msb" style="width:300px;"/></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">购买金额总价</td>
    <td bgcolor="#FFFFFF"><input type="text" name="price" id="price" style="width:100px;"/></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">优化人</td>
    <td bgcolor="#FFFFFF">
    <%
	if session("userid")="10" then
	%>
    <select name="lpersonid" id="lpersonid">
    <option value="">选择SEO人员</option>
    <%
	sql="select id,realname from users where userid='4204' and closeflag=1"
	set rs=conn.execute(sql)
	if not rs.eof or not rs.bof then
	while not rs.eof
	%>
      <option value="<%=rs("id")%>"><%=rs("realname")%></option>
    <%
	rs.movenext
	wend
	end if
	rs.close
	set rs=nothing
	%>
    </select>
    <%
	else
	%>
      <%=realname%>
      <input type="hidden" name="lpersonid" id="lpersonid" value="<%=personid%>" />
      <%end if%>
      <input name="action" type="hidden" id="action" value="add" />
      </td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">&nbsp;</td>
    <td bgcolor="#FFFFFF"><input type="submit" name="button" id="button" value="确认提交" /></td>
  </tr>
  </form>
</table>

</body>
</html>
<%
conn.close
set conn=nothing
%>
