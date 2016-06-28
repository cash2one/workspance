<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../include/adfsfs!@#.asp" -->
<!-- #include file="../localjumptolog.asp" -->
<!-- #include file="../../cn/function.asp" -->
<%
sql="select * from crm_personinfo where id="&request.QueryString("id")
set rsp=conn.execute(sql)
if rsp.eof then
response.Write("错误")
response.End()
end if
if request.Form("m")="1" then
				sql="select * from crm_PersonInfo where id="&request.Form("id")
				set rs=server.CreateObject("ADODB.recordset")
				rs.open sql,conn,1,3
				rs("PersonName")=request.Form("PersonName")
				rs("PersonSex")=request.Form("PersonSex")
				rs("PersonKey")=request.Form("PersonKey")
				rs("PersonStation")=request.Form("PersonStation")
				rs("PersonEmail")=request.form("PersonEmail")
				rs("PersonTel")=request.Form("PersonTel")
				rs("PersonFax")=request.Form("PersonFax")
				rs("PersonMoblie")=request.Form("PersonMoblie")
				rs("PersonOther")=request.Form("PersonOther")
				rs("PersonBz")=request.Form("PersonBz")
				rs("PersonAddr")=request.Form("PersonAddr")
				rs.update()
				rs.close
				set rs=nothing
				response.Write("<script>alert('保存成功！');window.close();</script>")
			end if
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<link href="datepicker.css" rel="stylesheet" type="text/css">
<link href="../main.css" rel="stylesheet" type="text/css">
<link href="../color.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.searchtable {
	border: 2px solid #243F74;
	background-color: #F5FFD7;
}
.crmcheckmod {
	background-color: #FFFFCC;
}
-->
</style>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
</head>

<body>
<form id="form1" name="form1" method="post" action="">
<table width="100%" border="0" cellpadding="0" cellspacing="0" id="lxflag"  class="searchtable">
  <tr>
    <td bgcolor="#FFCCCC"><table width="100%" border="0" cellspacing="0" cellpadding="3">
      <tr>
        <td align="right" width="130"><input name="m" type="hidden" id="m" value="1" />
          <input name="id" type="hidden" id="id" value="<%=request("id")%>" />
          联系人：</td>
        <td nowrap><input name="PersonName" type="text" id="PersonName" value="<%response.Write(rsp("PersonName"))%>">
          <input name="PersonSex" type="radio" value="1" <%if rsp("PersonSex")="1" then response.Write("checked")%>>
          先生
          <input type="radio" name="PersonSex" value="0"  <%if rsp("PersonSex")="0" then response.Write("checked")%>>
          女士</td>
        <td align="right">关键联系人： </td>
        <td>是
          <input type="radio" name="PersonKey" value="1" <%if rsp("PersonKey")="1" then response.Write("checked")%>>
          不是
          <input name="PersonKey" type="radio" value="0"  <%if rsp("PersonKey")="0" then response.Write("checked")%>></td>
        <td align="right">职务：</td>
        <td><input name="PersonStation" type="text" id="PersonStation" value="<%response.Write(rsp("PersonStation"))%>"></td>
      </tr>
      <tr>
        <td align="right">电话：</td>
        <td><input name="PersonTel" type="text" id="PersonTel" value="<%response.Write(rsp("PersonTel"))%>"></td>
        <td align="right">手机：</td>
        <td><input name="PersonMoblie" type="text" id="PersonMoblie" value="<%response.Write(rsp("PersonMoblie"))%>"></td>
        <td align="right">Email：</td>
        <td><input name="PersonEmail" type="text" id="PersonEmail" value="<%response.Write(rsp("PersonEmail"))%>"></td>
      </tr>
      
      <tr>
        <td align="right">传真： </td>
        <td><input name="PersonFax" type="text" id="PersonFax" value="<%response.Write(rsp("PersonFax"))%>"></td>
        <td align="right">其他联系方式：</td>
        <td><input name="PersonOther" type="text" id="PersonOther" value="<%response.Write(rsp("PersonOther"))%>"></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td align="right">地址：</td>
        <td colspan="5"><input name="PersonAddr" type="text" id="PersonAddr" value="<%=rsp("PersonAddr")%>" size="50" /></td>
      </tr>
      <tr>
        <td align="right">备注：</td>
        <td colspan="5"><textarea name="PersonBz" rows="3" id="PersonBz"><%response.Write(rsp("PersonBz"))%>
        </textarea></td>
        </tr>
      <tr>
        <td align="right">&nbsp;</td>
        <td colspan="5">
          <input type="submit" name="Submit" class="button" value="保存" />        </td>
      </tr>
    </table></td>
  </tr>
</table></form>  
</body>
</html>
<%
rsp.close
set rsp=nothing
conn.close
set conn=nothing
%>