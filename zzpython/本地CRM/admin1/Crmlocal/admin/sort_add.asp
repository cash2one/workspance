<!-- #include file="../../include/ad!$#5V.asp" -->
<%
db_table="cate_webCompType"
if request("add")="1" then
  arrcity=split(request("meno"),"|",-1,1)
  for i=0 to ubound(arrcity)
	if request("code")<>"" then
		sqlmax="select max(code) from "&db_table&" where code like '"&trim(request("code"))&"__' "
		set rsmax=server.createobject("adodb.recordset")
		rsmax.open sqlmax,conn,1,1
		if rsmax(0)<>"" then
		num=rsmax(0)+01
		else
		num=trim(request("code"))&"01"
		end if
		rsmax.close
		set rsmax=nothing
	else
		sqlmax="select max(code) from "&db_table&" where code like '__' "
		set rsmax=server.createobject("adodb.recordset")
		rsmax.open sqlmax,conn,1,1
		if rsmax(0)<>"" then
		num=rsmax(0)+01
		else
		num="10"
		end if
		rsmax.close
		set rsmax=nothing
	end if

	if request("m")="1" then
	sql="select * from "&db_table&" where id="&request("id")&""
	response.Write(sql)
	set rs=server.createobject("adodb.recordset")
	rs.open sql,conn,1,3
	rs("meno")=request("meno")
	else
	sql="select * from "&db_table&" "
	set rs=server.createobject("adodb.recordset")
	rs.open sql,conn,1,3
	rs.addnew()
	rs("code")=num
	rs("meno")=trim(arrcity(i))
	end if
	rs.update()
	rs.close
	set rs=nothing
  next
	response.Redirect("sort_list.asp")
end if
if request("action")="mod" then
set rs=server.createobject("adodb.recordset")
sql="select * from "&db_table&" where id="&request("id")&""
rs.open sql,conn,1,1
code=rs("code")
meno=rs("meno")
m=1
rs.close()
set rs=nothing
else
m=0

end if

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>无标题文档</title>
<link href="../../css.css" rel="stylesheet" type="text/css">
<link href="../../inc/Style.css" rel="stylesheet" type="text/css">
<link href="../../cn/sources/style.css" rel="stylesheet" type="text/css">
</head>

<body bgcolor="7386A5" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="23" background="../newimages/mid_dd.gif" bgcolor="#CED7E7"><img src="/admin1/newimages/mid_left.gif" width="23" height="28"></td>
    <td background="/admin1/newimages/mid_dd.gif"><input name="Submit2" type="button" class="button01-out" value="保存" onClick="window.form1.submit()">
    <input name="Submit3" type="button" class="button01-out" value="返回" onClick="window.history.back(1)"></td>
  </tr>
</table>
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td valign="top"><table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td width="100" height="23" nowrap background="/admin1/newimages/shale_fill_1.gif"><img src="/admin1/newimages/shale_arrowhead.gif" width="28" height="23" align="absmiddle">类别管理</td>
        <td width="21" background="../newimages/shale_fill_1.gif"><img src="/admin1/newimages/shale_photo.gif" width="21" height="23"></td>
        <td background="/admin1/newimages/lm_ddd.jpg">&nbsp;</td>
      </tr>
    </table>
      <table width="98%" height="90%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" bgcolor="#E7EBDE"><table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
            <form name="form1" method="post" action="sort_add.asp">
              <tr>
                <td colspan="3" bgcolor="E7EBDE">&nbsp;</td>
              </tr>
              <tr>
                <td width="150" bgcolor="E7EBDE">&nbsp;</td>
                <td width="62" bgcolor="E7EBDE">名 称 ：</td>
                <td width="558" bgcolor="E7EBDE">
                  <input name="meno" type="text" class="wenbenkuang" value="<%=meno%>">
                  <input type="hidden" name="code" value="<%=request("code")%>">
                  <input type="hidden" name="add" value="1">
                  <input type="hidden" name="m" value="<%=m%>">
                  <input type="hidden" name="id" value="<%=request("id")%>">
                </td>
              </tr>
              <tr>
                <td bgcolor="E7EBDE">&nbsp;</td>
                <td bgcolor="E7EBDE">排 序 ：</td>
                <td bgcolor="E7EBDE"><input name="ord" type="text" class="wenbenkuang" id="ord">
                </td>
              </tr>
              <tr>
                <td bgcolor="E7EBDE">&nbsp;</td>
                <td colspan="2" bgcolor="E7EBDE">&nbsp;                </td>
              </tr>
            </form>
          </table></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</body>
</html>

