<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../include/adfsfs!@#.asp" -->
<!-- #include file="../../cn/function.asp" -->
<!--#include file="../include/include.asp"-->
<!--#include file="../include/pagefunction.asp"-->
<%
function compTypeShow(code)
	select case code
	case "10"
		compTypeShow="�����ͻ�"
	case "11"
		compTypeShow="��ͨ�ͻ�"
	case "12"
		compTypeShow="���ʿͻ�"
	case "13"
		compTypeShow="VIP�ͻ�"
	end select
end function
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>�ޱ����ĵ�</title>
<style type="text/css">
<!--
body,td,th {
	font-size: 12px;
}
body {
	margin-left: 4px;
	margin-top: 4px;
	margin-right: 4px;
	margin-bottom: 4px;
}
-->
</style>
<script>
function CheckAll(form)
{
	for (var i=0;i<form.elements.length;i++)
	{
		var e = form.elements[i];
		if (e.name.substr(0,3)=='cbb')
	   	e.checked = form.cball.checked;
	}
}
function submtr(frm)
{
	if (frm.comType.value=="")
	{
		alert("��ѡ����༶��");
		return false;
	}
}
function selectOption(menuname,value)
{
    var menu = document.getElementById(menuname);
	if (menu)
	{
	for(var i=0;i<=menu.options.length;i++){
		if(menu.options[i].value==value)
		{
			menu.options[i].selected = true;
			break;
		}
	}
	}
}
</script>
</head>

<body>
<table width="100%" border="0" cellspacing="1" cellpadding="5" bgcolor="#666">
  <tr>
    <td align="center" bgcolor="#FFFFFF"><a href="http://admin.zz91.com/admin1/compClear/blackList.asp">�������ͻ�</a></td>
    <td align="center" bgcolor="#FFFFFF"><a href="http://admin.zz91.com/admin1/compClear/rubishlist.asp">��Ʒ�ؿͻ�</a></td>
    <td align="center" nowrap="nowrap" bgcolor="#CCCCCC"><a href="/admin1/compinfo/Telcomp.asp">����CRM�������ͻ�</a></td>
    <td align="center" nowrap="nowrap" bgcolor="#FFFFFF"><a href="TelcompPT.asp">���˽�����ͣ�����ػ�</a></td>
    <td align="center" nowrap="nowrap" bgcolor="#FFFFFF"><a href="TelcompYX.asp">������Ч��ϵ�Ŀͻ�</a></td>
  </tr>
</table>
<%
sql=""
sear="n="
       Set oPage=New clsPageRs2
	   With oPage
		 .sltFld  = "com_email,com_id,com_name"
		 .FROMTbl = "temp_salescomp"
		 .sqlOrder= "order by com_id desc"
		 '.sqlWhere= "WHERE exists(select com_id from crm_compcontactInfo where com_id=temp_salescomp.com_id and c_NoContact=2) and not exists(select com_id from crm_compcontactInfo where  (c_Type=13 or c_NoContact<>2) and com_id=temp_salescomp.com_id) and not exists(select com_id from comp_comType where com_id=temp_salescomp.com_id)"&sql
		 .sqlWhere= "WHERE exists(select com_id from comp_comTypeCheck where com_id=temp_salescomp.com_id and Fcheck=0 and comType=10) "&sql
		 .keyFld  = "com_id"    '����ȱ��
		 .pageSize= 10
		 .getConn = conn
		 Set Rs  = .pageRs
	   End With
	   total=oPage.getTotalPage
	   oPage.pageNav "?"&sear,""
	   totalpg=int(total/10)
%>
<table width="100%" border="0" cellspacing="1" cellpadding="5" bgcolor="#666666">
  <form name="form1" method="post" action="http://admin.zz91.com/admin1/compClear/TypeSave.asp" onSubmit="return submtr(this)">
  <tr>
    <td width="10" bgcolor="#f2f2f2">&nbsp;</td>
    <td bgcolor="#f2f2f2">��˾����</td>
    <td bgcolor="#f2f2f2">��������</td>
    <td bgcolor="#f2f2f2">&nbsp;</td>
    <td bgcolor="#f2f2f2">�ͻ�����</td>
    <td bgcolor="#f2f2f2">������</td>
  </tr>
  <%
  	
	   if not rs.eof or not rs.bof then
	   while not rs.eof
	    '-----------�ͻ�����
	   comType=""
	   sqlb="select comType from comp_comType where com_id="&rs("com_id")&""
	   set rsb=conn.execute(sqlb)
	   if not rsb.eof or not rsb.bof then
	   	comType=rsb(0)
	   end if
	   rsb.close
	   set rsb=nothing
	   
  %>
  <tr>
    <td bgcolor="#FFFFFF">
      <input type="checkbox" name="cbb" id="cbb" value="<%=rs("com_id")%>">
    </td>
    <td nowrap="nowrap" bgcolor="#FFFFFF"><a href="http://admin.zz91.com/admin1/compinfo/crm_cominfoedit.asp?idprod=<%=rs("com_id")%>" target="_blank"><%=rs("com_name")%></a></td>
    <td nowrap="nowrap" bgcolor="#FFFFFF"><%=rs("com_email")%></td>
    <td bgcolor="#FFFFFF"><a href="/admin1/crmlocal/crm_tel_comp.asp?com_id=<%=rs("com_id")%>" target="_blank">���ۼ�¼</a></td>
    <td nowrap="nowrap" bgcolor="#FFFFFF"><%=compTypeShow(comType)%></td>
    <td bgcolor="#FFFFFF">
    <%
				  sqluser="select a.personid,b.realname from Crm_Assign as a,users as b where a.com_id="&rs("com_id")&" and a.personid=b.id"
				  set rsuser=conn.execute(sqluser)
				  if not rsuser.eof then
				  	realnamead=rsuser("realname")
				  else
				  	realnamead=""
				  end if
				  rsuser.close
				  set rsuser=nothing
				  response.write(realnamead)
				  %>
    
    </td>
  </tr>
  
  <%
  rs.movenext
  wend
  end if
  rs.close
  set rs=nothing
  %>
  <tr>
    <td bgcolor="#FFFFFF"><input type="checkbox" name="cball" id="cball" onClick="CheckAll(this.form)" /></td>
    <td colspan="5" bgcolor="#FFFFFF">ȫѡ
      <select name="comType" id="comType">
      	<option value="">��ѡ��</option>
        <option value="10">�����ͻ�</option>
        <option value="11">��ͨ�ͻ�</option>
        <option value="12">���ʿͻ�</option>
        <option value="13">VIP�ͻ�</option>
      </select>
      <script>selectOption("comType","10")</script>
      <input name="dotype" type="hidden" id="dotype" value="local" />
      <input type="submit" name="button" id="button" value="����ͻ�" />
      <!--<input type="button" name="button2" id="button2" value="ȫ������" onclick="window.open('saveTelAll.asp','_blank','width=400,height=300')" />>--></td>
    </tr>
  </form>
</table>
</body>
</html>
<%endConnection()%>
