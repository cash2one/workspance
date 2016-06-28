<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!--#include file="../../../cn/sources/Md5.asp"-->
<!--#include file="../../include/include.asp"-->
<%
  set rs=server.createobject("adodb.recordset")
  sql="select * from users where id="&request("id") 
  rs.open sql,conn,1,1
  userid=rs("userid")
  username=rs("name")
  password=rs("password")
  realname=rs("realname")
  userbg=rs("userbg")
  adminuserid=rs("adminuserid")
  email=rs("email")
  emailpass=rs("emailpass")
  userqx=rs("userqx")
  nicheng=rs("nicheng")
  partuserid=rs("partuserid")
  ywadminID=rs("ywadminID")
  chatclose=rs("chatclose")
  closeflag=rs("closeflag")
  usertel=rs("usertel")
  rongyi=rs("rongyi")
  rs.close()
  set rs=nothing
  
  sqluser="select ywadminid,adminuserid,Partuserid from users where id="&session("personid")
  set rsuser=conn.execute(sqluser)
  p_ywadminid=rsuser("ywadminid")
  p_adminuserid=rsuser("adminuserid")
  p_Partuserid=rsuser("Partuserid")
  rsuser.close
  set rsuser=nothing
  'sqlu="select partid from cate_adminuser where code in ("&p_ywadminid&")"
  'set rsu=conn.execute(sqlu)
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title><%=username%>  --  用户修改</title>
<link href="../../css.css" rel="stylesheet" type="text/css">
<SCRIPT language=JavaScript src="../../main.js"></SCRIPT>
<script language=JavaScript>
function subchk(frm)
{
       if(frm.userid.value=="")
       {
         alert("请选择部门!\n");
         frm.userid.focus();
         return  false;
       }
	   
       if  (frm.uname.value=="")       
       {   
		 alert("请输入用户名称!\n");
         frm.uname.focus();
         return  false;                 
       } 
	   if  (frm.password.value=="")       
       {   
		 alert("请输入登录密码!\n");
         frm.password.focus();
         return  false;                 
       }
	   if  (frm.realname.value=="")       
       {   
		 alert("请输入真实姓名!\n");
         frm.realname.focus();
         return  false;                 
       } 
}
</script>
<link href="../../inc/Style.css" rel="stylesheet" type="text/css">
<style>
.plist
{
	float:left;
	width:150px;
}
</style>
</head>

<body bgcolor="7386A5" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="23" background="../../newimages/mid_dd.gif" bgcolor="#CED7E7"><img src="../../newimages/mid_left.gif" width="23" height="28"></td>
    <td background="../../newimages/mid_dd.gif">&nbsp;</td>
  </tr>
</table>
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
            <td width="100" height="23" nowrap background="../../newimages/shale_fill_1.gif"><img src="../../newimages/shale_arrowhead.gif" width="28" height="23" align="absmiddle">用户修改</td>
            <td width="21" background="../../newimages/shale_fill_1.gif"><img src="../../newimages/shale_photo.gif" width="21" height="23"></td>
            <td background="../../newimages/lm_ddd.jpg">&nbsp;</td>
          </tr>
        </table>
          </td>
      </tr>
      <tr>
        <td bgcolor="E7EBDE"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td>&nbsp;</td>
          </tr>
        </table>
        <table width="500" border="0" align="center" cellpadding="10" cellspacing="1" bgcolor="#CCCCCC">
          <tr>
            <td bgcolor="#F1F3ED"><table width="100%" border="0" align="center" cellpadding="4" cellspacing="0">
              <form name="company_sec"  method="post" action="admin_aduser_save.asp" onSubmit="return subchk(this)">
              
                <tr>
                  <td width="100" align="right">部门</td>
                  <td>
                  <%
				  touserid=request.QueryString("touserid")
				  if touserid="23" then
				  %>
                  人事部<input name="userid" type="hidden" value="<%=touserid%>">
                  <%
				  else
				  %>
          <select name="userid" id="userid">
                    <option value="">请选择...</option>
				      <%
		  sqlw="select * from cate_adminuser where code like '__' and closeflag=1 order by id desc"
		  set rsw=server.CreateObject("adodb.recordset")
			rsw.open sqlw,conn,1,1
			if not rsw.eof or not rsw.bof then
			do while not rsw.eof
		   %>
				      <option value="<%=rsw("code")%>">┆&nbsp;&nbsp;┿<%=rsw("meno")%></option>
				      <%
		  sql1="select * from cate_adminuser where code like '"&rsw("code")&"__' and code in("&p_ywadminid&") and closeflag=1  order by id desc"
		  set rs1=server.CreateObject("adodb.recordset")
			rs1.open sql1,conn,1,1
			if not rs1.eof or not rs1.bof then
			do while not rs1.eof
		     %>
				      <option value="<%=rs1("code")%>">┆&nbsp;&nbsp;┆&nbsp;&nbsp;┿<%=rs1("meno")%></option>
				     
				      <%
		 rs1.movenext
		 loop
		 end if
		 rs1.close
		  %>
				      <%
		 rsw.movenext
		 loop
		 end if
		 rsw.close
		  %>
				      </select>
                 <script>selectOption("userid","<%=trim(userid)%>")</script>
                 <%end if%>
                  </td>
                </tr>
                <tr>
                  <td align="right"><input type="hidden" name="m" value="1">
                    <input type="hidden" name="id" value=<%=request("id")%>>
                    <input type="hidden" name="uname1" value="<%=trim(username)%>">
                    用户名                  </td>
                  <td><input name="uname" type="text" id="uname" value="<%=trim(username)%>">                  </td>
                </tr>
                
                <tr>
                  <td align="right">密码</td>
                  <td><input name="password" type="password" id="password" value="<%=trim(password)%>">                  </td>
                </tr>
                <tr>
                  <td align="right">真实姓名</td>
                  <td><input name="realname" type="text" id="realname" value="<%=trim(realname)%>">                  </td>
                </tr>
                <tr align="center">
                  <td align="right">荣誉称号</td>
                  <td align="left"><input name="rongyi" type="text" id="rongyi" value="<%=trim(rongyi)%>"></td>
                </tr>
                <tr align="center">
                  <td align="right">业务电话</td>
                  <td align="left"><input name="usertel" type="text" id="usertel" maxlength="8" value="<%=usertel%>"></td>
                </tr>
                <tr align="center">
                  <td align="right">帐号开通情况</td>
                  <td align="left">
                  
                  <input type="radio" name="closeflag" id="radio" value="1" <%if closeflag=1 then response.Write("checked")%>>
                    开通
                      <input type="radio" name="closeflag" id="radio2" value="0" <%if closeflag=0 then response.Write("checked")%>>
                      关闭</td>
                </tr>
                <tr align="center">
                  <td colspan="2">
                    <input type="submit" name="Submit" value="修改">                  </td>
                </tr>
              </form>
            </table></td>
          </tr>
        </table>
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td>&nbsp;</td>
            </tr>
          </table></td>
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