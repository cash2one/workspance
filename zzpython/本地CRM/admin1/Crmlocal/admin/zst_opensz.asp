<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<%
function showproviceN(code)
	sqlp="select meno from cate_province where code='"&code&"'"
	set rsp=conn.execute(sqlp)
	if not rsp.eof or not rsp.bof then
		showproviceN=rsp(0)
	end if
	rsp.close
	set rsp=nothing
end function
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
</head>

<body>
<table width="100%" border="0" cellpadding="4" cellspacing="1" bgcolor="#CCCCCC">
<%
sql="select realname,id from users where userid=22 and closeflag=1"
set rs=conn.execute(sql)
if not rs.eof or not rs.bof then
while not rs.eof
%>
  <tr>
    <td bgcolor="#FFFFFF"><%=rs("realname")%></td>
    <td bgcolor="#FFFFFF">
	<%
	provinceID=""
	sqln="select provinceId from crm_webProvince_assgin where  personid="&rs("id")&""
	set rsn=conn.execute(sqln)
	if not rsn.eof or not rsn.bof then
		provinceID=rsn("provinceID")
	end if
	rsn.close
	set rsn=nothing
	if provinceID<>"" then	
		arrp=split(provinceID,",")
		for i=0 to ubound(arrp)
			response.Write(showproviceN(trim(arrp(i)))&"|")
		next
	end if
	%>
    </td>
    <td bgcolor="#FFFFFF"><input type="button" name="button" id="button" value="设置开放省份" onclick="window.location='zst_opensz_province.asp?personid=<%=rs("id")%>'" /></td>
  </tr>
<%
rs.movenext
wend
end if
rs.close
set rs=nothing
%>
</table>
<%
conn.close
set conn=nothing
%>
</body>
</html>
