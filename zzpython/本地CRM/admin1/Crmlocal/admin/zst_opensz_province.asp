<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<%
personid=request("personid")
sql="select provinceID from crm_webProvince_assgin where personid="&personid&""
set rs=conn.execute(sql)
if not rs.eof or not rs.bof then
	provinceID=rs("provinceID")
end if
rs.close
set rs=nothing
if request("add")<>"" then
	if request("provinceID")<>"" then
		sql="select * from crm_webProvince_assgin where personid="&personid&""
		set rs=conn.execute(sql)
		if not rs.eof or not rs.bof then
			sql="update crm_webProvince_assgin set provinceid='"&request("provinceID")&"' where personid="&personid&""
			conn.execute(sql)
		else
			sql="insert into crm_webProvince_assgin(personid,provinceID) values("&personid&",'"&request("provinceID")&"')"
			conn.execute(sql)
		end if
		rs.close
		set rs=nothing
		response.Redirect("zst_opensz.asp")
	end if
end if
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
</head>

<body>
<table width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#CCCCCC">
<form id="form1" name="form1" method="post" action="">
<%
arrp=split(provinceID,",")
'response.Write(provinceID)
sql="select code,meno from cate_province where code like '__' and meno<>'------'"
set rs=conn.execute(sql)
if not rs.eof or not rs.bof then
while not rs.eof
exet=0
for i=0 to ubound(arrp)
	if cstr(rs("code"))=cstr(trim(arrp(i))) then
		exet=1
	end if
next
%>
  <tr>
    <td bgcolor="#FFFFFF">
      <input type="checkbox" name="provinceID" id="provinceID" value="<%=rs("code")%>" <%if exet=1 then response.Write("checked")%> />    </td>
    <td bgcolor="#FFFFFF"><%=rs("meno")%></td>
  </tr>
<%
rs.movenext
wend
end if
rs.close
set rs=nothing
%> <tr>
    <td bgcolor="#FFFFFF"><input name="add" type="hidden" id="add" value="1" /></td>
    <td bgcolor="#FFFFFF">
    <input type="submit" name="button" id="button" value="提交" />
    <input type="hidden" name="personid" id="personid" value="<%=request("personid")%>" />
    </td>
  </tr></form>
</table>
</body>
</html>
<%
conn.close
set conn=nothing
%>