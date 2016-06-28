<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../include/adfsfs!@#.asp" -->
<!-- #include file="../localjumptolog.asp" -->
<!-- #include file="../../cn/function.asp" -->
<!--#include file="../include/include.asp"-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
<style type="text/css">
<!--
body,td,th {
	font-size: 12px;
}
-->
</style></head>

<body>
<table width="300" border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#666666">
  <form id="form1" name="form1" method="post" action="webcrm_compout_save.asp"><tr>
    <td colspan="2" bgcolor="#CCCCCC">选择你要导出的客户</td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">客户类型</td>
    <td bgcolor="#FFFFFF">
      <select name="comType" id="comType">
        <option value="0" selected="selected">所有</option>
        <option value="2">再生通</option>
        <option value="1">普通</option>
      </select>    </td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">客户标注</td>
    <td bgcolor="#FFFFFF">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <%
					  sqlt="select code,meno from cate_webCompType where code like '__'"
					  set rst=conn.execute(sqlt)
					  if not rst.eof or not rst.bof then
					  while not rst.eof 
					  %>
                        <tr>
                          <td width="100"><%=rst("meno")%></td>
                          <td>
                          <%
						  sqlt1="select code,meno from cate_webCompType where code like '"&rst("code")&"__'"
						  set rst1=conn.execute(sqlt1)
						  if not rst1.eof or not rst1.bof then
						  while not rst1.eof 
						  %>
                          <input name="compkind" type="checkbox" id="compkind" value="<%=rst1("code")%>"><%=rst1("meno")%>
                          <%
						  rst1.movenext
						  wend
						  end if
						  rst1.close
						  set rst1=nothing
						  %>                          </td>
                        </tr>
                      <%
					  rst.movenext
					  wend
					  end if
					  rst.close
					  set rst=nothing
					  %>
                      </table>    </td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">注册时间</td>
    <td bgcolor="#FFFFFF"><input type="text" name="regtime" id="regtime" /></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">开通时间</td>
    <td bgcolor="#FFFFFF"><input type="text" name="vipdate" id="vipdate" /></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">导出内容</td>
    <td bgcolor="#FFFFFF"><input name="outcontent" type="checkbox" id="outcontent" value="com_name" />
      公司名称<br />
      <input name="outcontent" type="checkbox" id="outcontent" value="com_add" />
      公司地址<br />
      <input name="outcontent" type="checkbox" id="outcontent" value="com_email" />
      EMAIL<br />
      <input name="outcontent" type="checkbox" id="outcontent" value="com_tel" />
      电话<br />
      <input name="outcontent" type="checkbox" id="outcontent" value="com_fax" />
      传真<br />
      <input name="outcontent" type="checkbox" id="outcontent" value="com_mobile" />
      手机</td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">&nbsp;</td>
    <td bgcolor="#FFFFFF"><input type="submit" name="button" id="button" value="确定导出" /></td>
  </tr>
</form>
</table>
</body>
</html>
<!-- #include file="../../conn_end.asp" -->
