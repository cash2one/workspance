<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>�����ͻ��ύϵͳ</title>
<link href="../../main.css" rel="stylesheet" type="text/css">
<link href="../../color.css" rel="stylesheet" type="text/css">
<script>
function CheckAll(form)
{
	for (var i=0;i<form.elements.length;i++)
	{
		var e = form.elements[i];
		if (e.name=='comid')
	   {
		  e.checked = form.chktoggle.checked;
	   }
	}
}
function getcom(form,flag)
{
  selectcb="0"
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
    if (e.name=='comid')
       if (e.checked==true)
	   {
	   var selectcb=selectcb+","+e.value
	   }
    }
	if (selectcb=="0")
	{
		alert ("ѡ����Ҫ�ύ����Ϣ��")
		return false
	}
	else
	{
	  if (confirm("ȷʵҪ�ύ����Ϣ��?"))
	  {
		  form.flag.value=flag
	  	form.action="s_list_comp_save.asp"
	  	form.submit()
	  }
	}
}
function delglcom(form)
{
  selectcb="0"
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
    if (e.name=='comid')
       if (e.checked==true)
	   {
	   		var selectcb=selectcb+","+e.value
	   }
    }
	if (selectcb=="0")
	{
		alert ("ѡ����Ҫ�ύ����Ϣ��")
		return false
	}
	else
	{
	  if (confirm("ȷʵҪɾ���ù�����Ϣ��?"))
	  {
	  	form.action="s_list_comp_del.asp"
	  	form.submit()
	  }
	}
}
</script>
</head>

<body scroll=yes>
<input type="button" name="button3" id="button3" value="��  ��" onclick="window.location='s_p_list.asp?page=<%=request("page")%>'" />
<br />
<br />
<table width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#999999">
  <tr>
    <td bgcolor="#FFFFFF">&nbsp;</td>
    <td bgcolor="#FFFFFF">��˾����</td>
    <td bgcolor="#FFFFFF">Email</td>
    <td bgcolor="#FFFFFF">�绰</td>
    <td bgcolor="#FFFFFF">�ֻ�</td>
    <td bgcolor="#FFFFFF">��ϵ��</td>
    <td bgcolor="#FFFFFF">ʡ��</td>
    <td bgcolor="#FFFFFF">ע��ʱ��</td>
    <td bgcolor="#FFFFFF">�����ϵʱ��</td>
    <td bgcolor="#FFFFFF">&nbsp;</td>
    <td bgcolor="#FFFFFF">������</td>
    <td bgcolor="#FFFFFF">�ύ���</td>
    <td bgcolor="#FFFFFF">���</td>
  </tr>
  <form id="form2" name="form2" method="post" action="s_list_comp_save.asp">
  <%
    sql=" and groupid='"&request("groupid")&"'"
  	
					
	  sqlm="select top 50 com_id,com_name,com_tel,com_email,com_contactperson,com_mobile,com_province,com_regtime,contactnext_time,personid from v_salescomp where EXISTS (select com_id from crm_complink where com_id=v_salescomp.com_id "&sql&" ) "
	  'response.Write(sqlm)
	  set rs=conn.execute(sqlm)
	  if not rs.eof or not rs.bof then
	  while not rs.eof
	  %>
      <%
		shflag="0"
		tjflag="0"
		flag="0"
		sqlg="select com_id,shflag,tjflag,flag from crm_compLink where com_id="&rs("com_id")&" and groupid='"&request("groupid")&"'"
		set rsg=conn.execute(sqlg)
		if not rsg.eof or not rsg.bof then
			shflag=rsg("shflag")
			tjflag=rsg("tjflag")
			flag=rsg("flag")
		end if
		rsg.close
		set rsg=nothing
		%>   
	  <tr>
		<td bgcolor="#FFFFFF">
		  <input type="checkbox" name="comid" id="comid" value="<%=rs("com_id")%>" <%if tjflag="1" then response.Write("checked")%> />		</td>
		<td bgcolor="#FFFFFF"><a href="/admin1/crmlocal/modaldealog_body.asp?../crmlocal/crm_cominfoedit.asp?idprod=<%=rs("com_id")%>" target="_blank"><%=rs("com_name")%></a></td>
		<td bgcolor="#FFFFFF"><%=rs("com_email")%></td>
		<td bgcolor="#FFFFFF"><%=rs("com_tel")%></td>
		<td bgcolor="#FFFFFF"><%=rs("com_mobile")%></td>
		<td bgcolor="#FFFFFF"><%=rs("com_contactperson")%></td>
		<td bgcolor="#FFFFFF"><%=rs("com_province")%></td>
		<td bgcolor="#FFFFFF"><%=rs("com_regtime")%></td>
		<td bgcolor="#FFFFFF"><%=rs("contactnext_time")%></td>
		<td nowrap="nowrap" bgcolor="#FFFFFF"><%=flag%></td>
		<td nowrap="nowrap" bgcolor="#FFFFFF">
		 <%
				  sqluser="select a.personid,b.realname from crm_assign as a,users as b where a.com_id="&rs("com_id")&" and a.personid=b.id"

				  set rsuser=conn.execute(sqluser)
				  if not rsuser.eof or not rsuser.bof then
				  realname=rsuser("realname")
				  else
				  realname=""
				  end if
				  rsuser.close
				  set rsuser=nothing
				  response.Write(realname)
				  %> 
         <%if trim(rs("personid"))<>"" and not isnull(rs("personid")) then%>
         <input type="hidden" name="personid" id="personid" value="<%=rs("personid")%>" />
         <%
		 end if
		 %>         </td>
              
		<td nowrap="nowrap" bgcolor="#FFFFFF">
        <%
		    if tjflag="1" then
				response.Write("���ύ")
			else
				response.Write("<font color=#ff0000>δ�ύ</font>")
			end if
		%>        </td>
		<td nowrap="nowrap" bgcolor="#FFFFFF">
        <%
		    if shflag="1" then
				response.Write("�����")
			else
				response.Write("<font color=#ff0000>δ���</font>")
			end if
		%>        </td>
	  </tr>
	  
	  <%
	  rs.movenext
	  wend
	  end if
	  rs.close
	  set rs=nothing
  %> 
  <tr>
	    <td colspan="13" bgcolor="#FFFFFF"><input name="chktoggle" type="checkbox" id="chktoggle" value="1" onClick="CheckAll(this.form)"/>
      ȫѡ      
	     
      <input type="button" name="button2" id="button2" value="���" onClick="return getcom(this.form,1)" />
      <input type="button" name="button" id="button" value="ȡ�����" onclick="return getcom(this.form,0)" />
<input type="hidden" name="GroupID" id="GroupID" value="<%=request("groupid")%>" />
<input type="hidden" name="flag" id="flag" /></td>
    </tr>
  </form>
</table>
</body>
</html>
<%
conn.close
set conn=nothing
%>