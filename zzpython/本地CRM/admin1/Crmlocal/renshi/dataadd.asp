<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!--#include file="../../include/include.asp"-->
<!--#include file="../inc.asp"-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>��ӵ�������</title>
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
			//˵�����ַ��������� 
			return 0; 
		} 
	} 
	//˵�������� 
	return 1; 
}
function subst(frm)
{
	if (frm.realname.value=="")
	{
		alert("��ѡ��������Ա")
		return false;
	}
	for (var i=0;i<frm.elements.length;i++)
	{
		var e = frm.elements[i];
		if (e.name.substr(0,12)=='service_type')
		{
			if (e.value=="")
			{
			   alert("��ѡ���Ʒ���࣡");
			   e.focus();
			   return false;
			}
		}
		if (e.name.substr(0,10)=='sales_type')
		{
			if (e.value=="")
			{
			   alert("��ѡ��ͻ����࣡");
			   e.focus();
			   return false;
			}
		}
		if (e.name.substr(0,11)=='sales_email')
		{
			if (e.value=="")
			{
			   alert("������ͻ����䣡");
			   e.focus();
			   return false;
			}
			if(!/^(.+)@(.+)(\.\w+)+$/ig.test(e.value)){  
				alert("���������ʽ����");
				e.focus();
				return  false;
			}
		}
		
		if (e.name.substr(0,11)=='sales_price')
		{
			if (chknum(e.value)==0)
			{
			   alert("���ʽ����������֣�");
			   e.focus();
			   return false;
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
	frm.action="dataadd_save.asp"
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
    <td width="60" align="right" bgcolor="#FFFFFF">������Ա</td>
    <td bgcolor="#FFFFFF"><input type="text" name="realname" id="realname" value="<%=request.Form("realname")%>" class="Input" readonly/>
      <input name="personid" type="hidden" id="personid" value="<%=request.Form("personid")%>" />
<input type="button" name="button2" id="button2" value="ѡ��" /></td>
  </tr>
  <tr>
    <td align="right" bgcolor="#FFFFFF">����ʱ��</td>
    <td bgcolor="#FFFFFF"><script language=javascript>createDatePicker("sales_date",false,"<%=request.Form("sales_date")%>",false,true,true,true)</script></td>
  </tr>
  <tr>
    <td align="right" bgcolor="#FFFFFF">����</td>
    <td bgcolor="#FFFFFF">
      <select name="sales_num" id="sales_num" onChange="changeNum(this.form)">
      	<option value="">��ѡ����</option>
        <option value="1">1��</option>
        <option value="2">2��</option>
        <option value="3">3��</option>
        <option value="4">4��</option>
        <option value="5">5��</option>
        <option value="6">6��</option>
        <option value="7">7��</option>
        <option value="8">8��</option>
        <option value="9">9��</option>
        <option value="10">10��</option>
      </select>
      <script>selectOption("sales_num","<%=request.Form("sales_num")%>")</script>
    </td>
  </tr>
  </table>
<br />
<%
sales_num=request.Form("sales_num")
function getbieshu()
  bstr="<table width='400' border='0' align='center' cellpadding='3' cellspacing='1' bgcolor='#666666'>"
  bstr=bstr&"<tr>"
  bstr=bstr&"<td width='60' align='right' bgcolor='#FFFFFF'><font color=#ff0000>*</font> ��Ʒ����</td>"
  bstr=bstr&"<td bgcolor='#FFFFFF'>"&cateMeno_public(conn,"category","service_type",request.Form("service_type"),"","13")&" <a href=/admin1/crmlocal/renshi/sort_list.asp?lmcode=4202 target=_blank>����</a></td>"
  bstr=bstr&"</tr>"
  bstr=bstr&"<tr>"
  bstr=bstr&"<td align='right' bgcolor='#FFFFFF'><font color=#ff0000>*</font> �ͻ�����</td>"
  bstr=bstr&"<td bgcolor='#FFFFFF'>"&cateMeno_public(conn,"category","sales_type",request.Form("sales_type"),"","14")&" <a href=/admin1/crmlocal/renshi/sort_list.asp?lmcode=4202 target=_blank>����</a></td>"
  bstr=bstr&"</tr>"
  bstr=bstr&"<tr>"
  bstr=bstr&"<td align='right' bgcolor='#FFFFFF'><font color=#ff0000>*</font> ���ʽ��</td>"
  bstr=bstr&"<td bgcolor='#FFFFFF'><input type='text' name='sales_price' id='sales_price' /></td>"
  bstr=bstr&"</tr>"
  bstr=bstr&"<tr>"
  bstr=bstr&"<td align='right' bgcolor='#FFFFFF'><font color=#ff0000>*</font> �ͻ�����</td>"
  bstr=bstr&"<td bgcolor='#FFFFFF'><input type='text' name='sales_email' id='sales_email' /><br><font color=#ff0000>����д�����ͻ�Email</font></td>"
  bstr=bstr&"</tr>"
  bstr=bstr&"<tr>"
  bstr=bstr&"<td align='right' bgcolor='#FFFFFF'>�ͻ��ֻ�</td>"
  bstr=bstr&"<td bgcolor='#FFFFFF'><input type='text' name='sales_mobile' id='sales_mobile' /><br><font color=#ff0000>����д�ͻ��ֻ�</font></td>"
  bstr=bstr&"</tr>"
  bstr=bstr&"<tr>"
  bstr=bstr&"<td align='right' bgcolor='#FFFFFF'>������ע</td>"
  bstr=bstr&"<td bgcolor='#FFFFFF'><input type='text' name='sales_bz' id='sales_bz' /><br>������Դ��1.�¿ͻ�/�����ͻ�/ת����/վ���ռ���2.���ڣ�3���ڣ�һ���ڣ�һ���ڣ������ڣ�һ�����⣩��3.�ؼ��㣺����ɱ��/</td>"
  bstr=bstr&"</tr>"
  bstr=bstr&"</table>"
  getbieshu=bstr
end function
salesstr=getbieshu()
%>
<%
for i=1 to sales_num
	response.Write(salesstr&"<br>")
next
%>
  <br />


  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td align="center"><input type="button" name="button" id="button" value="����" onClick="return subst(this.form)" /></td>
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
