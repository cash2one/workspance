<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../include/adfsfs!@#.asp" -->
<!-- #include file="../../cn/function.asp" -->
<!--#include file="../include/include.asp"-->
<%
compList=""
if request.Form("savelocal")<>"" then
	'sqly="insert into comp_comType(com_id,comType) select com_id,c_NoContact from crm_compcontactInfo where c_NoContact=2 and not exists(select com_id from comp_comType where com_id=crm_compcontactInfo.com_id)"
	'conn.execute(sqly)
	'sql="select com_id from crm_compcontactInfo where c_NoContact<>2 and c_NoContact>0 and com_id not in (select com_id from crm_compcontactInfo where c_Type=13)"
	'sql="select com_id from temp_salescomp where exists(select com_id from crm_compcontactInfo where com_id=temp_salescomp.com_id and c_NoContact<>2 and c_NoContact>0) and not exists(select com_id from crm_compcontactInfo where  c_Type=13 and com_id=temp_salescomp.com_id) "
	sql="select top 4000 com_id from temp_salescomp where not exists(select com_id from comp_comTypeCheck where com_id=temp_salescomp.com_id) and not exists(select com_id from comp_comType where com_id=temp_salescomp.com_id) and exists(select com_id from comp_sales where contacttype=13 and com_id=temp_salescomp.com_id)"
	set rs=conn.execute(sql)
	if not rs.eof or not rs.bof then
		while not rs.eof
			compList=compList&","&rs("com_id")
			'sqlt="delete from comp_comType where com_id="&rs("com_id")&""
			'conn.execute(sqlt)
			sqls="select com_id from comp_comType where com_id="&rs("com_id")&""
			set rss=conn.execute(sqls)
			if rss.eof or rss.bof then
				sqlt="insert into comp_comType(com_id,comType) values("&rs("com_id")&",11)"
				conn.execute(sqlt)
			end if
		rs.movenext
		wend
	end if
	rs.close
	set rs=nothing
	response.Write("保存成功！")
end if
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
</head>

<body>
<%if request.Form("savelocal")="" then%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center"><form id="form2" name="form2" method="post" action="">
      <input type="submit" name="button2" id="button2" value="第一步，保存本地数据" />
      <input name="savelocal" type="hidden" id="savelocal" value="1" />
    </form></td>
  </tr>
</table>
<%end if%>
<br />
<%if request.Form("savelocal")="1" then%>
<form name="form1" method="post" action="http://admin.zz91.com/admin1/compClear/TypeSaveAll.asp">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td align="center"><input type="submit" name="button" id="button" value="第二步，保存网上数据" /></td>
    </tr>
  </table>
  <input type="hidden" name="compList" id="compList" value="<%=compList%>">
</form>
<%end if%>
</body>
</html>
<%endConnection()%>