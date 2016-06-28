<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!--#include file="../../include/include.asp"-->
<!--#include file="../inc.asp"-->
<%
sql="select * from renshi_salesIncome where id="&request("id")&""
set rs=conn.execute(sql)
if rs.eof or rs.bof then
	response.write("Error!")
	response.end()
else
	personid=rs("personid")
	realname=rs("realname")
	sales_date=rs("sales_date")
	service_type=rs("service_type")
	sales_type=rs("sales_type")
	sales_price=rs("sales_price")
	sales_email=rs("sales_email")
	sales_mobile=rs("sales_mobile")
	sales_bz=rs("sales_bz")
end if
rs.close
set rs=nothing
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>修改到单</title>
<SCRIPT language=javascript src="../DatePicker.js"></SCRIPT>
<SCRIPT language=JavaScript src="../../main.js"></SCRIPT>
<link href="../datepicker.css" rel="stylesheet" type="text/css">
<link href="../../inc/Style.css" rel="stylesheet" type="text/css">
<script>
function changeNum(frm)
{
	frm.action="dataadd.asp"
	frm.submit()
}
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
function subst(frm)
{
	if (frm.realname.value=="")
	{
		alert("请选择销售人员")
		return false;
	}
	for (var i=0;i<frm.elements.length;i++)
	{
		var e = frm.elements[i];
		if (e.name.substr(0,12)=='service_type')
		{
			if (e.value=="")
			{
			   alert("请选择产品分类！");
			   e.focus();
			   return false;
			}
		}
		if (e.name.substr(0,10)=='sales_type')
		{
			if (e.value=="")
			{
			   alert("请选择客户分类！");
			   e.focus();
			   return false;
			}
		}
		if (e.name.substr(0,11)=='sales_price')
		{
			if (chknum(e.value)==0)
			{
			   alert("单笔金额必须是数字！");
			   e.focus();
			   return false;
			}
		}
		if (e.name.substr(0,11)=='sales_email')
		{
			if (e.value=="")
			{
			   alert("请输入客户邮箱！");
			   e.focus();
			   return false;
			}
			if(!/^(.+)@(.+)(\.\w+)+$/ig.test(e.value)){  
				alert("电子邮箱格式错误");
				e.focus();
				return  false;
			}
		}
		if (e.name.substr(0,8)=='sales_bz')
		{
			e.value=e.value.replace(/,/,"|")
		}
		if (e.name.substr(0,11)=='sales_email')
		{
			e.value=e.value.replace(/,/,"|")
		}
	}
	frm.action="dataedit_save.asp"
	frm.submit();
	
}
</script>
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="300" valign="top">
    <form id="form1" name="form1" method="post" action="dataadd.asp"><table width="400" border="0" cellpadding="3" cellspacing="1" bgcolor="#666666">
  <tr>
    <td width="60" align="right" bgcolor="#FFFFFF">销售人员</td>
    <td bgcolor="#FFFFFF"><input type="text" name="realname" id="realname" value="<%=realname%>" class="Input" readonly/>
      <input name="personid" type="hidden" id="personid" value="<%=personid%>" />
<input type="button" name="button2" id="button2" value="选择" />
<input name="id" type="hidden" id="id" value="<%=request("id")%>" /></td>
  </tr>
  <tr>
    <td align="right" bgcolor="#FFFFFF">到单时间</td>
    <td bgcolor="#FFFFFF"><script language=javascript>createDatePicker("sales_date",false,"<%=sales_date%>",false,true,true,true)</script></td>
  </tr>
  
  </table>
<br />
<%
sales_num=request.Form("sales_num")
function getbieshu()
  bstr="<table width='400' border='0' align='center' cellpadding='3' cellspacing='1' bgcolor='#666666'>"
  bstr=bstr&"<tr>"
  bstr=bstr&"<td width='60' align='right' bgcolor='#FFFFFF'><font color=#ff0000>*</font> 产品分类 </td>"
  bstr=bstr&"<td bgcolor='#FFFFFF'>"&cateMeno_public(conn,"category","service_type",service_type,"","13")&" <a href=/admin1/crmlocal/renshi/sort_list.asp?lmcode=4202 target=_blank>管理</a></td>"
  bstr=bstr&"</tr>"
  bstr=bstr&"<tr>"
  bstr=bstr&"<td align='right' bgcolor='#FFFFFF'><font color=#ff0000>*</font> 客户分类 </td>"
  bstr=bstr&"<td bgcolor='#FFFFFF'>"&cateMeno_public(conn,"category","sales_type",sales_type,"","14")&" <a href=/admin1/crmlocal/renshi/sort_list.asp?lmcode=4202 target=_blank>管理</a></td>"
  bstr=bstr&"</tr>"
  bstr=bstr&"<tr>"
  bstr=bstr&"<td align='right' bgcolor='#FFFFFF'><font color=#ff0000>*</font> 单笔金额</td>"
  bstr=bstr&"<td bgcolor='#FFFFFF'><input type='text' name='sales_price' id='sales_price' value='"&sales_price&"'/></td>"
  bstr=bstr&"</tr>"
  bstr=bstr&"<tr>"
  bstr=bstr&"<td align='right' bgcolor='#FFFFFF'><font color=#ff0000>*</font> 客户邮箱</td>"
  bstr=bstr&"<td bgcolor='#FFFFFF'><input type='text' name='sales_email' id='sales_email' value='"&sales_email&"' /><br><font color=#ff0000>请填写客户邮箱</font></td>"
  bstr=bstr&"</tr>"
  bstr=bstr&"<tr>"
  bstr=bstr&"<td align='right' bgcolor='#FFFFFF'>客户手机</td>"
  bstr=bstr&"<td bgcolor='#FFFFFF'><input type='text' name='sales_mobile' id='sales_mobile' value='"&sales_mobile&"'/><br><font color=#ff0000>请填写客户手机</font></td>"
  bstr=bstr&"</tr>"
  bstr=bstr&"<tr>"
  bstr=bstr&"<td align='right' bgcolor='#FFFFFF'>到单备注</td>"
  bstr=bstr&"<td bgcolor='#FFFFFF'><input type='text' name='sales_bz' id='sales_bz'  value='"&sales_bz&"'/><br>到单来源：1.新客户/公海客户/转介绍/站外收集；2.短期（3天内，一周内，一月内）；长期（一个月外）；3.关键点：促销杀进/</td>"
  bstr=bstr&"</tr>"
  bstr=bstr&"</table>"
  getbieshu=bstr
end function
salesstr=getbieshu()
%>
<%
for i=1 to 1
	response.Write(salesstr&"<br>")
next
%>
  <br />


  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td align="center"><input type="button" name="button" id="button" value="保存" onClick="return subst(this.form)" /></td>
    </tr>
  </table>
</form>
    </td>
    <td width="300" valign="top">
    <iframe id="personinfoa" name="personinfoa"  style="HEIGHT: 500px; VISIBILITY: inherit; WIDTH: 100%; Z-INDEX: 2" frameborder="0" src="selectuser.asp"></iframe>
    </td>
  </tr>
</table>
</body>
</html>
