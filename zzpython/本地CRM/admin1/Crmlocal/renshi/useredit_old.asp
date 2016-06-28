<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!--#include file="../../include/include.asp"-->
<!--#include file="../../../cn/sources/Md5.asp"-->
<!--#include file="../inc.asp"-->
<%
add=request("add")
userID=request("userid")
uname=trim(request("uname"))
realname=trim(request("realname"))
personid=request.QueryString("id")

if add<>"" then
	set rs=server.createobject("adodb.recordset")
	sql="select * from users where id="&request.Form("id")&""
	rs.open sql,conn,1,3
	  'rs.addnew()
	  rs("userID")=userID
	  'rs("name")=trim(uname)
	  'rs("password")="12345"
	  'rs("safepass")=cstr(md5("12345",16))
	  rs("realname")=trim(realname)
	  'rs("nicheng")=realname
	  'rs("Partuserid")=0
	  rs("sex")=request("sex")
	  rs("renshi_code")=request("renshi_code")
	  if request("birthday")<>"" then
	  rs("birthday")=request("birthday")
	  end if
	  rs("renshi_shiyong")=request("renshi_shiyong")
	  rs("renshi_guanli")=request("renshi_guanli")
	  rs("renshi_zaizhi")=request("renshi_zaizhi")
	  rs("renshi_ruzi")=request("renshi_ruzi")
	  rs.update()
	  rs.close()
	  set rs=nothing
	  response.Write("<script>alert('修改成功！');window.close()</script>")
end if
if personid<>"" then
	sql="select * from users where id="&personid&""
	set rs=conn.execute(sql)
end if
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>员工信息修改</title>
<link href="../../css.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/javascript" src="../../include/JSDateFunction.js"></script>
<script language="javascript" src="../../include/calendar.js"></script>
<script type="text/javascript" src="../../include/b.js"></script>
<SCRIPT language=javascript src="../DatePicker.js"></SCRIPT>
<SCRIPT language=JavaScript src="../../main.js"></SCRIPT>
<link href="../datepicker.css" rel="stylesheet" type="text/css">
<script language=JavaScript>
function subchk(frm)
{
	if (frm.realname.value=="")
	{
		alert("请输入姓名！");
		frm.realname.focus();
		return false;
	}
       if  (frm.uname.value=="")       
		{   
		 alert("请输入名字拼音(用户名)!\n");
		 frm.uname.focus();
		 return  false;                 
		} 
		
}
</script>
<style>
.input
{
	width:150px;
}
</style>
<link href="../../inc/Style.css" rel="stylesheet" type="text/css">
</head>

<body bgcolor="7386A5" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" height="95%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="6">
	</td>
  </tr>
  <tr>
    <td height="100%" valign="top"><table width="98%" height="100%" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td height="23" bgcolor="E7EBDE"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="100" height="23" nowrap background="../../newimages/shale_fill_1.gif"><img src="../../newimages/shale_arrowhead.gif" width="28" height="23" align="absmiddle">添加员工</td>
            <td width="21" background="../../newimages/shale_fill_1.gif"><img src="../../newimages/shale_photo.gif" width="21" height="23"></td>
            <td background="../../newimages/lm_ddd.jpg">&nbsp;</td>
          </tr>
        </table>
          </td>
      </tr>
      <tr>
        <td bgcolor="E7EBDE"><table width="400" border="0" align="center" cellpadding="10" cellspacing="1" bgcolor="#CCCCCC">
          <tr>
            <td bgcolor="#F1F3ED"><table width="100%" border="0" align="center" cellpadding="4" cellspacing="0">
              <form name="company_sec" method="post" action="useredit.asp" onSubmit="return subchk(this)">
                <tr>
                  <td align="right">姓名</td>
                  <td><input name="realname" type="text" class="input" id="realname" value="<%=rs("realname")%>">
                    <input name="add" type="hidden" id="add" value="1"></td>
                </tr>
                <tr>
                  <td align="right">名字拼音(用户名)</td>
                  <td><input name="uname" type="text" class="input" id="uname" value="<%=rs("name")%>" readonly>
                    <input name="id" type="hidden" id="id" value="<%=personid%>"></td>
                </tr>
                <tr>
                  <td align="right">性别</td>
                  <td><input name="sex" type="radio" id="radio" value="男" <%if rs("sex")="男" then response.Write("checked")%>>
                    男
                      <input type="radio" name="sex" id="radio2" value="女" <%if rs("sex")="女" then response.Write("checked")%>>
                      女</td>
                </tr>
                <tr>
                  <td align="right">员工编号</td>
                  <td><input type="text" name="renshi_code" class="input" id="renshi_code" value="<%=rs("renshi_code")%>"></td>
                </tr>
                <tr>
                  <td align="right">生日</td>
                  <td><script language=javascript>createDatePicker("birthday",true,"<%=rs("birthday")%>",false,true,true,true)</script></td>
                </tr>
				<tr>
                  <td align="right">入职时间</td>
                  <td><script language=javascript>createDatePicker("renshi_ruzi",true,"<%=rs("renshi_ruzi")%>",false,true,true,true)</script></td>
                </tr>
                <tr>
                  <td align="right">部门</td>
                  <td><select name="userid" id="userid">
                  <option value="">请选择部门</option>
                    <%
		  sqlw="select * from cate_adminuser where code like '__' and closeflag=1 order by id desc"
		  set rsw=server.CreateObject("adodb.recordset")
			rsw.open sqlw,conn,1,1
			if not rsw.eof or not rsw.bof then
			do while not rsw.eof
		   %>
                    <option value="<%=rsw("code")%>">┆&nbsp;&nbsp;┿<%=rsw("meno")%></option>
                    <%
		  sql1="select * from cate_adminuser where code like '"&rsw("code")&"__'  and closeflag=1 order by id desc"
		  set rs1=server.CreateObject("adodb.recordset")
			rs1.open sql1,conn,1,1
			if not rs1.eof or not rs1.bof then
			do while not rs1.eof
		     %>
                    <option value="<%=rs1("code")%>">┆&nbsp;&nbsp;┆&nbsp;&nbsp;┿<%=rs1("meno")%></option>
                    <%
		  sql2="select * from cate_adminuser where code like '"&rs1("code")&"__'  and closeflag=1 order by id desc"
		  set rs2=server.CreateObject("adodb.recordset")
			rs2.open sql2,conn,1,1
			if not rs2.eof or not rs2.bof then
			do while not rs2.eof
		     %>
                    <option value="<%=rs2("code")%>">┆&nbsp;&nbsp;┆&nbsp;&nbsp;┆&nbsp;&nbsp;┿<%=rs2("meno")%></option>
                    <%
		 rs2.movenext
		 loop
		 end if
		 rs2.close
		 set rs2=nothing
		  %>
                    <%
		 rs1.movenext
		 loop
		 end if
		 rs1.close
		 set rs1=nothing
		  %>
                    <%
		 rsw.movenext
		 loop
		 end if
		 rsw.close
		 set rsw=nothing
		  %>
                  </select>
                  <script>selectOption("userid","<%=rs("userid")%>")</script>
                    <a href="/admin1/admin_user_class.asp?lmcode=1002" target="_blank">管理</a></td>
                </tr>
                
                
                <tr>
                  <td align="right">P路线职级</td>
                  <td><%=cateMeno_public(conn,"category","renshi_shiyong",rs("renshi_shiyong"),"","10")%> <a href="sort_list.asp?lmcode=4202" target="_blank">管理</a></td>
                </tr>
                <tr>
                  <td align="right">M路线职级</td>
                  <td><%=cateMeno_public(conn,"category","renshi_guanli",rs("renshi_guanli"),"","11")%> <a href="sort_list.asp?lmcode=4202" target="_blank">管理</a></td>
                </tr>
                <tr>
                  <td align="right">在职情况</td>
                  <td><%=cateMeno_public(conn,"category","renshi_zaizhi",rs("renshi_zaizhi"),"","12")%> <a href="sort_list.asp?lmcode=4202" target="_blank">管理</a></td>
                </tr>
                <tr align="center">
                  <td colspan="2">
                    <input name="Submit" type="submit" class="button01-out" value="提交">
                    <input name="Submit2" type="reset" class="button01-out" value="重置">
                  </td>
                </tr>
              </form>
            </table></td>
          </tr>
        </table>
          </td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="6">	</td>
  </tr>
</table>
</body>
</html>
<!-- #include file="../../../conn_end.asp" -->