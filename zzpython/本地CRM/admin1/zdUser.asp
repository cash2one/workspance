<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="include/adfsfs!@#.asp" -->
<%
userid = Request.ServerVariables("LOGON_USER")
compid = Request.ServerVariables("REMOTE_USER")
zdname=request.Form("zdname")

if request("add")<>"" and (trim(request.Form("zdname"))<>"" or request.Form("ZDFlag")="1") then
	if instr(zdname,"a")>0 or instr(zdname,"b")>0 or instr(zdname,"c")>0 or instr(zdname,"d")>0 or instr(zdname,"e")>0 or instr(zdname,"f")>0 then
	else
		response.Write("���ô������������ã�")
		response.End()
	end if
	
	sql="select * from userZD where personid="&Request.Cookies("personid")&""
	set rs=server.CreateObject("adodb.recordset")
	rs.open sql,conn,1,3
	if rs.eof or rs.bof then
	rs.addnew()
	
	rs("personid")=Request.Cookies("personid")
	rs("zdname")=zdname
	rs("zdIP")=request.Form("zdIP")
	if request.Form("ZDFlag")<>"" then
	rs("zdflag")=request.Form("ZDFlag")
	end if
	end if
	rs.update()
	
	rs.close
	set rs=nothing
	response.Write("<script>parent.window.location='/admin1/cp_main.asp';</script>")
end if
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>�ޱ����ĵ�</title>
<script>
function subs(frm)
{
	if (frm.ZDFlag.checked==false)
	{
		//if(frm.ZDIp.value=="")
//		{
//			alert("����дIP")
//			frm.ZDIp.focus()
//			return false
//		}
		if(frm.ZDname.value=="")
		{
			alert("����д�û���")
			frm.ZDname.focus()
			return false
		}
	}
}
</script>
<style type="text/css">
<!--
body,td,th {
	font-size: 12px;
}
-->
</style></head>

<body>
<table width="600" border="0" align="center" cellpadding="5" cellspacing="0">
  <tr>
    <td><div style="font-size:14px; color:#F00; font-weight:bold">ע�⣺Ϊ�˱���ά���ն˻����������ն˻���ͬ�����������ú����µ���Ϣ��</div></td>
  </tr>
</table>
<table width="600" border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#999999" style="line-height:25PX">
  <form id="form1" name="form1" method="post" action="" onsubmit="return subs(this)">
    <tr>
      <td width="100" align="right" bgcolor="#FFFFFF"><strong>�ն˻���������</strong><strong style="color:#F00"><br />
        </strong></td>
      <td bgcolor="#FFFFFF"><input type="text" name="ZDname" id="ZDname" />
        <strong style="color:#F00">��¼�ն˻��Ļ�����<br />
        </strong>
        <div style="font-size:18px; color:#F00"><strong style="font-size:30px; color:#06C; line-height:40px">��������д��������˽���ͨ��</strong><br />
          <strong style="color:#000">��¼���ն˻����û���������CRM�û���</strong><br />
        <strong style="font-size:35px; line-height:40px">(����)�����Ե��windows�Ŀ�ʼ---��˻���ע�������Կ������ڵ��û���</strong></div></td>
    </tr>
    <tr>
      <td align="right" bgcolor="#FFFFFF"><input name="add" type="hidden" id="add" value="1" /></td>
      <td bgcolor="#FFFFFF"><label>
<input type="checkbox" name="ZDFlag" id="ZDFlag" value="1" />
�������ն˻�</label></td>
    </tr>
    <tr>
      <td align="right" bgcolor="#FFFFFF">&nbsp;</td>
      <td bgcolor="#FFFFFF"><input type="submit" name="button" id="button" value="�ύ" /></td>
    </tr>
  </form>
</table>
</body>
</html>
<%endConnection()%>