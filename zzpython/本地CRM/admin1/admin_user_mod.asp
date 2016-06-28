<!-- #include file="checkuser.asp" -->
<!-- #include file="include/adfsfs!@#.asp" -->
<!--#include file="include/include.asp"-->
<%
set rs=server.createobject("adodb.recordset")
sql="select * from users where id="&session("personid") 
  rs.open sql,conn,1,1
  userid=rs("userid")
  username=rs("name")
  password=rs("password")
  realname=rs("realname")
  rs.close()
  set rs=nothing
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>无标题文档</title>
<link href="css.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/javascript" src="include/JSDateFunction.js"></script>
<script language="javascript" src="include/calendar.js"></script>
<script type="text/javascript" src="include/b.js"></script>
 <script language=JavaScript>
  function subchk()
  {
      if(company_sec.secSubCatId.value=="")
      {
         alert("请选择单位!\n");
         company_sec.secSubCatId.focus();
         return  false;
       }
       if  (company_sec.uname.value=="")       
        {   
		alert("请输入用户名称!\n");
         company_sec.uname.focus();
         return  false;                 
        } 
		if  (company_sec.password.value=="")       
        {   
		alert("请输入登录密码!\n");
         company_sec.password.focus();
         return  false;                 
        }  
		       
              
                
   }
    </script>
<link href="inc/Style.css" rel="stylesheet" type="text/css">
</head>

<body bgcolor="7386A5" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="23" background="newimages/mid_dd.gif" bgcolor="#CED7E7"><img src="newimages/mid_left.gif" width="23" height="28"></td>
    <td background="newimages/mid_dd.gif">&nbsp;</td>
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
            <td width="100" height="23" nowrap background="newimages/shale_fill_1.gif"><img src="newimages/shale_arrowhead.gif" width="28" height="23" align="absmiddle">密码修改</td>
            <td width="21" background="newimages/shale_fill_1.gif"><img src="newimages/shale_photo.gif" width="21" height="23"></td>
            <td background="newimages/lm_ddd.jpg">&nbsp;</td>
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
        <table width="400" border="0" align="center" cellpadding="10" cellspacing="1" bgcolor="#CCCCCC">
          <tr>
            <td bgcolor="#F1F3ED"><table width="100%" border="0" align="center" cellpadding="4" cellspacing="0">
              <form name="form1" method="post" action="admin_user1_save.asp">
                <tr>
                  <td width="28%">用户名
                      <input type="hidden" name="m" value="1">
                      <input type="hidden" name="id" value=<%=session("personid")%>>
                      <input type="hidden" name="uname1" value="<%=trim(username)%>">
                  </td>
                  <td width="72%"><input name="uname" type="text" class="wenbenkuang" id="uname" value="<%=trim(username)%>">
                  </td>
                </tr>
                <tr>
                  <td>原密码</td>
                  <td width="72%"><input name="password" type="password" class="wenbenkuang" id="password"></td>
                </tr>
                <tr>
                  <td>新密码</td>
                  <td><input name="password1" type="password" class="wenbenkuang" id="password1">
</td>
                </tr>
                <tr>
                  <td>真实姓名</td>
                  <td><input name="realname" type="text" class="wenbenkuang" id="realname" value="<%=trim(realname)%>">
                  </td>
                </tr>
                <tr align="center">
                  <td colspan="2">
                    <input type="submit" name="Submit" value="修改">
                  </td>
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
</html><!-- #include file="../conn_end.asp" -->
<%
endConnection()%>
