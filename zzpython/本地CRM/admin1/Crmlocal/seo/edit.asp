<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!--#include file="../../include/include.asp"-->
<!-- #include file="../../../cn/function.asp" -->
<!--#include file="../inc.asp"-->
<%
id=request.QueryString("id")
if id="" then response.End()
sql="select * from seo_list where id="&id&""
set rs=conn.execute(sql)
if not rs.eof or not rs.bof then
	com_email=rs("com_email")
	com_id=rs("com_id")
	target_assure=rs("target_assure")
	seo_start=rs("seo_start")
	'target_require=rs("target_require")
	'target_time=rs("target_time")
	if seo_start="1900-1-1" then
		seo_start=""
	end if
	'expire_time=rs("expire_time")
	if target_assure="1900-1-1" then
		target_assure=""
	end if
	com_msb=rs("com_msb")
	price=rs("price")
	lpersonid=rs("personid")
end if
rs.close
set rs=nothing
sql="select realname from users where id="&lpersonid&""
set rs=conn.execute(sql)
if not rs.eof or not rs.bof then
	realname=rs(0)
end if
rs.close
set rs=nothing
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>客户修改</title>
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

function frmsubmit(frm)
{
	if(frm.com_msb.value.length<=0)
	{
		alert("请输入门市部!");
		frm.com_msb.focus();
		return false;
	}
	if(frm.price.value.length<=0)
	{
		alert("请输入销售金额!");
		frm.price.focus();
		return false;
	}
	if(frm.lpersonid.value.length<=0)
	{
		alert("请选择SEO优化人员!");
		frm.lpersonid.focus();
		return false;
	}
	
	frm.submit();
}
</script>
</head>

<body>

<table width="95%" border="0" align="center" cellpadding="6" cellspacing="1" bgcolor="#666666">
  <tr>
    <td bgcolor="#FFFFFF">&nbsp;</td>
    <td bgcolor="#FFFFFF"><a href="list.asp">返回列表</a></td>
  </tr>
  <tr>
    <td width="100" bgcolor="#FFFFFF">邮箱</td>
    <td bgcolor="#FFFFFF">
    <%=com_email%></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">关键词</td>
    <td bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="1" cellpadding="3" bgcolor="#666666">
      <tr>
        <td bgcolor="#FFFFFF">关键字</td>
        <td bgcolor="#FFFFFF">价格</td>
        <!--<td bgcolor="#FFFFFF">开始优化时间</td>
        <td bgcolor="#FFFFFF">保证达标时间</td>-->
        <td bgcolor="#FFFFFF">达标要求</td>
        <td bgcolor="#FFFFFF">达标时间</td>
        <td bgcolor="#FFFFFF">过期时间</td>
        <td bgcolor="#FFFFFF">操作</td>
      </tr>
      <%
	sqlk="select keywords,baidu_ranking,id,target_require,seo_start,target_assure,target_time,expire_time,price from seo_keywordslist where sid="&id&""
	set rsk=conn.execute(sqlk)
	if not rsk.eof or not rsk.bof then
	while not rsk.eof
	expire_time1=rsk("expire_time")
	target_time1=rsk("target_time")
	if target_time1="1900-1-1" then
		target_time1=""
	end if
	if expire_time1="1900-1-1" then
		expire_time1=""
	end if
	%><form id="form<%=rsk("id")%>" name="form<%=rsk("id")%>" method="post" action="ksave.asp">
      <tr>
        <td bgcolor="#FFFFFF">
          
            <input type="text" name="keywords" id="keywords" value="<%=rsk("keywords")%>" />
            <input type="hidden" name="kid" id="kid" value="<%=rsk("id")%>" />
            <input name="action" type="hidden" id="action" value="edit" /></td>
        <td bgcolor="#FFFFFF"><input name="price" type="text" id="price" value="<%=rsk("price")%>" size="10" /></td>
        <!--<td bgcolor="#FFFFFF"><script language=javascript>createDatePicker("seo_start",false,"<%=rsk("seo_start")%>",false,true,true,true)</script></td>
        <td bgcolor="#FFFFFF"><script language=javascript>createDatePicker("target_assure",false,"<%=rsk("target_assure")%>",false,true,true,true)</script></td>-->
        <td bgcolor="#FFFFFF"><select name="target_require" id="target_require<%=rsk("id")%>">
          <option value="1">第一页</option>
          <option value="2">第二页</option>
          <option value="3">第三页</option>
        </select>
        <script>selectOption("target_require<%=rsk("id")%>","<%=rsk("target_require")%>")</script>
        </td>
        <td bgcolor="#FFFFFF"><script language=javascript>createDatePicker("target_time",true,"<%=target_time1%>",false,true,true,true)</script></td>
        <td bgcolor="#FFFFFF"><script language=javascript>createDatePicker("expire_time",true,"<%=expire_time1%>",false,true,true,true)</script></td>
        <td bgcolor="#FFFFFF"><input type="submit" name="button2" id="button2" value="修改" /> 
          | <a href="ksave.asp?kid=<%=rsk("id")%>&action=del" onClick="return confirm('确实删除吗？')">删除</a></td>
      </tr>
     </form>
      <%
	rsk.movenext
	wend
	end if
	rsk.close
	set rsk=nothing
	%> 
        <form id="form2" name="form2" method="post" action="ksave.asp">
      <tr>
        <td bgcolor="#FFFFFF"><input type="text" name="keywords" id="keywords" />
        <input name="action" type="hidden" id="action" value="add" />
        <input type="hidden" name="sid" id="sid" value="<%=id%>" />
        <input type="hidden" name="com_msb" id="com_msb" value="<%=com_msb%>" />
        <input name="personid" type="hidden" id="personid" value="<%=lpersonid%>" /></td>
        <td bgcolor="#FFFFFF"><input name="price" type="text" id="price" value="0" size="10" /></td>
        <!--<td bgcolor="#FFFFFF"><script language=javascript>createDatePicker("seo_start",false,"",false,true,true,true)</script></td>
        <td bgcolor="#FFFFFF"><script language=javascript>createDatePicker("target_assure",false,"",false,true,true,true)</script></td>-->
        <td bgcolor="#FFFFFF"><select name="target_require" id="target_require">
      <option value="1">第一页</option>
      <option value="2">第二页</option>
      <option value="3">第三页</option>
    </select></td>
        <td bgcolor="#FFFFFF"><script language=javascript>createDatePicker("target_time",true,"",false,true,true,true)</script></td>
        <td bgcolor="#FFFFFF"><script language=javascript>createDatePicker("expire_time",true,"",false,true,true,true)</script></td>
        <td bgcolor="#FFFFFF"><input type="submit" name="button4" id="button4" value="添加" /></td>
      </tr>
      </form>
    </table></td>
  </tr>
  <form id="form1" name="form1" method="post" action="save.asp">
  <tr>
    <td bgcolor="#FFFFFF">开始优化时间</td>
    <td bgcolor="#FFFFFF"><script language=javascript>createDatePicker("seo_start",false,"<%=seo_start%>",false,true,true,true)</script></td>
  </tr><tr>
    <td bgcolor="#FFFFFF">保证达标时间</td>
    <td bgcolor="#FFFFFF"><script language=javascript>createDatePicker("target_assure",false,"<%=target_assure%>",false,true,true,true)</script></td>
  </tr>
  <!--<tr>
    <td bgcolor="#FFFFFF">达标时间</td>
    <td bgcolor="#FFFFFF"><script language=javascript>createDatePicker("target_time",true,"<%=target_time%>",false,true,true,true)</script></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">过期时间</td>
    <td bgcolor="#FFFFFF"><script language=javascript>createDatePicker("expire_time",false,"<%=expire_time%>",false,true,true,true)</script></td>
  </tr>-->
  <tr>
    <td bgcolor="#FFFFFF">门市部</td>
    <td bgcolor="#FFFFFF">HTTP://
      <input type="text" name="com_msb" id="com_msb" style="width:300px;" value="<%=com_msb%>"/></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">购买金额</td>
    <td bgcolor="#FFFFFF"><input type="text" name="price" id="price" style="width:100px;" value="<%=price%>"/></td>
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
    <script>selectOption("lpersonid","<%=lpersonid%>")</script>
    <%
	else
	%>
      <%=realname%>
      <input type="hidden" name="lpersonid" id="lpersonid" value="<%=lpersonid%>" />
      <%end if%>
      <input name="action" type="hidden" id="action" value="edit" />
      <input type="hidden" name="sid" id="sid" value="<%=id%>" /></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">&nbsp;</td>
    <td bgcolor="#FFFFFF"><input type="button" name="button" id="button" value="确认提交"  onClick="return frmsubmit(this.form)"/></td>
  </tr>
 </form>
</table>

</body>
</html>
<%
conn.close
set conn=nothing
%>
