<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<%
outsql=request.Form("outsql")
total=request.Form("total")
if outsql="" then
	response.Write("不能对所有的客户进行群发短信操作")
	response.End()
end if
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title></title>
<link href="../css/list.css" rel="stylesheet" type="text/css">
<link href="../../main.css" rel="stylesheet" type="text/css">
<link href="../../color.css" rel="stylesheet" type="text/css">
<script>
function getcompanylist()
{
	form2.submit();
}
</script>
</head>

<body>

<br />
<table width="500" border="0" align="center" cellpadding="5" cellspacing="0" class="searchtable">
  <tr>
    <td>共有<b><%=total%></b>个客户</td>
  </tr>
  <tr>
    <td><div id="succtext">正在导入数据...</div></td>
  </tr>
  <tr id="trcom" style="display:none">
    <td>
    <form id="form1" name="form1" method="post" action="http://admin1949.zz91.com/reborn-admin/sms/main/sendFromZZ91.htm">
        <input type="hidden" name="companyIds" id="companyIds" value="0">
        <input type="hidden" name="mobiles" id="mobiles" value="0">
        <input type="submit" name="button" id="button" value="提交到群发后台" />
    </form>
    <form id="form2" name="form2" method="post" action="sendselect.asp" target="copekuan">
        <input type="hidden" name="companyIds" id="companyIds" value="">
        <input type="hidden" name="mobiles" id="mobiles" value="">
        <input type="hidden" name="outsql" id="outsql" value="<%=outsql%>">
    </form>
    <iframe name="copekuan" src="" frameborder='0' width="0" height="0" scrolling="no" ></iframe>
    </td>
  </tr>
</table>
<script>getcompanylist();</script>
</body>
</html>

