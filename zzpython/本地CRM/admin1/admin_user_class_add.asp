<%@ Language=VBScript %>
<!-- #include file="checkuser.asp" -->
<!-- #include file="include/adfsfs!@#.asp" -->
<!--#include file="include/include.asp"-->
<%
m="0"
db_table="cate_adminuser"
if request("add")="1" then
	if request("partid")<>"" then
		partid=request("partid")
	else
		partid=0
	end if
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
	sql="select * from "&db_table&" where code='"&request("code")&"'"
	set rs=server.createobject("adodb.recordset")
	rs.open sql,conn,1,3
	rs("meno")=request("meno")
	rs("ord")=request("ord")
	rs("closeflag")=request("closeflag")
	rs("partid")=partid
	else
	sql="select * from "&db_table&" "
	set rs=server.createobject("adodb.recordset")
	rs.open sql,conn,1,3
	rs.addnew()
	rs("code")=num
	rs("meno")=request("meno")
	rs("ord")=request("ord")
	rs("partid")=partid
	rs("closeflag")=request("closeflag")
	end if
	rs.update()
	rs.close
	set rs=nothing
	response.Redirect("admin_user_class.asp")
end if
if request("mod")="1" then
    sql="select * from "&db_table&" where code='"&request("code")&"'"
	set rs=server.createobject("adodb.recordset")
	rs.open sql,conn,1,3
	code=rs("code")
	meno=rs("meno")
	ord=rs("ord")
	partID=rs("partID")
	closeflag=rs("closeflag")
	If ord="" Or IsNull(ord) Then
	ord=0
	End if
	rs.close()
	set rs=nothing
	m="1"
	if closeflag="" or isnull(closeflag) then closeflag="0"
end if
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>无标题文档</title>
<link href="css.css" rel="stylesheet" type="text/css">
<link href="inc/Style.css" rel="stylesheet" type="text/css">
<script>
function subb()
{
	if (form1.meno.value=="")
	{
		alert("请输入名称！")
		form1.meno.focus()
		return false
	}
	if (form1.ord.value=="")
	{
		alert("请输入排序！")
		form1.ord.focus()
		return false
	}
	form1.submit()
}

</script>
</head>

<body bgcolor="7386A5" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="23" background="admin/newimages/mid_dd.gif" bgcolor="#CED7E7"><img src="newimages/mid_left.gif" width="23" height="28"></td>
    <td background="newimages/mid_dd.gif"><input name="Submit" type="button" class="button01-out" value="保存" onClick="subb()">
    <input name="Submit3" type="button" class="button01-out" value="返回" onClick="window.history.back(1)"></td>
  </tr>
</table>
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
   <tr>
    <td height="6">
	</td>
  </tr>
  <tr>
    <td valign="top">
	<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td width="100" height="23" nowrap background="newimages/shale_fill_1.gif"><img src="newimages/shale_arrowhead.gif" width="28" height="23" align="absmiddle">类别管理</td>
        <td width="21" background="newimages/shale_fill_1.gif"><img src="newimages/shale_photo.gif" width="21" height="23"></td>
        <td background="newimages/lm_ddd.jpg">&nbsp;</td>
      </tr>
    </table>
      <table width="98%" height="90%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" bgcolor="#E7EBDE"><table width="98%" border="0" align="center" cellpadding="5" cellspacing="0">
            <form name="form1" method="post" action="admin_user_class_add.asp" onSubmit="return  subb()">
              <tr>
                <td colspan="2" bgcolor="E7EBDE">&nbsp;</td>
              </tr>
              <tr>
                <td align="right" bgcolor="E7EBDE">名 称 ：</td>
                <td width="558" bgcolor="E7EBDE">
                  <input name="meno" type="text" class="wenbenkuang" value="<%=meno%>">
                  <input type="hidden" name="code" value="<%=request("code")%>">
                  <input type="hidden" name="add" value="1">
                  <input type="hidden" name="m" value="<%=m%>">
                </td>
              </tr>
              
              <tr>
                <td align="right" bgcolor="E7EBDE">排 序 ：</td>
                <td bgcolor="E7EBDE"><input name="ord" type="text" class="wenbenkuang" id="ord" value="<%=ord%>">
                </td>
              </tr>
              <tr>
                <td align="right" bgcolor="E7EBDE">开通冻结：</td>
                <td bgcolor="E7EBDE"><input type="radio" name="closeflag" id="radio" value="1" <%if closeflag="1" then response.Write("checked")%>>
                  开通
                    <input type="radio" name="closeflag" id="radio2" value="0" <%if closeflag="0" then response.Write("checked")%>>
                  冻结</td>
              </tr>
              <tr>
                <td align="right" bgcolor="E7EBDE">所属区：</td>
                <td bgcolor="E7EBDE">
                <select name="PartID" id="PartID">
                <option value="">请选择...</option>
                <%
				sqlt="select code,meno from cate_adminuserPart where code like '__'"
				set rst=conn.execute(sqlt)
				if not rst.eof or not rst.bof then
				while not rst.eof
				%>
                  <option value="<%=rst("code")%>" <%if rst("code")=partID then response.Write("selected")%>><%=rst("meno")%></option>
                <%
				rst.movenext
				wend
				end if
				rst.close
				set rst=nothing
				%>
                </select>
                
                </td>
              </tr>
              <tr>
                <td colspan="2" bgcolor="E7EBDE">&nbsp;                </td>
                </tr>
            </form>
          </table></td>
        </tr>
    </table></td>
  </tr>
</table>
</body>
</html>
<%
endConnection()%>
