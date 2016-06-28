<!-- #include file="include/adfsfs!@#.asp" -->
<!-- #include file="../cn/function.asp" -->
<!--#include file="../cn/sources/Md5.asp"-->
<%
username=request.QueryString("uid")
if request("m")="1" then
	uname=trim(request("uname"))
	password=trim(request("password"))
	password1=trim(request("password1"))
	
	if IsNumeric(password1) then
		errpass=1
		response.Redirect("changepasswd.asp?uid="&uid&"&errpass="&errpass)
		response.End()
	end if
	if len(password1)<6 then
		errpass=2
		response.Redirect("changepasswd.asp?uid="&uid&"&errpass="&errpass)
		response.End()
	end if
	
	aaa=0
	for i=1 to len(password1)
		str=password1
		if IsNumeric(mid(str,i,1)) then
			aaa=aaa+1
		end if
	next
	if aaa=0 then
		errpass=3
		response.Redirect("changepasswd.asp?uid="&uid&"&errpass="&errpass)
		response.End()
	end if
	
	
	set rs=server.createobject("adodb.recordset")
	sql="select * from users where name='"&request("uname")&"'"
    rs.open sql,conn,1,1
	if trim(password)<>trim(rs("password")) then
		response.write "<script languang='javascript'>alert('原密码有错误！');</script>"
		response.write "<script languang='javascript'>javascript:history.back(1);</script>"
		rs.close()
		set rs=nothing
		response.End()
	end if
	rs.close()
	set rs=nothing
	
	set rs=server.createobject("adodb.recordset")
	  sql="select * from users where name='"&request("uname")&"'"
	  rs.open sql,conn,1,3
	  rs("name")=trim(uname)
	  rs("password")=trim(password1)
	  rs("SafePass")=trim(md5(password1,16))
	  rs.update()
	  rs.close()
	  set rs=nothing
	  response.write "<script languang='javascript'>alert('修改成功！请重新登录！');window.location='login.asp?uid="&request("uname")&"'</script>"
end if
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
          <table width="50%" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td><div style="font-size:16px; color:#F00; line-height:25px">
              <%
			  errpass=request.QueryString("errpass")
			  if errpass="1" then
			  	response.Write("你的密码不能全为数字，请修改你的密码！")
			  end if
			  if errpass="2" then
			  	response.Write("你的密码必须大于6位数，请修改你的密码！")
			  end if
			  if errpass="3" then
			  	response.Write("你的密码必须包括数字和字符组合，请修改你的密码！")
			  end if 
			  %>
              
              </div></td>
            </tr>
          </table>
          <table width="400" border="0" align="center" cellpadding="10" cellspacing="1" bgcolor="#CCCCCC">
          <tr>
            <td bgcolor="#F1F3ED"><table width="100%" border="0" align="center" cellpadding="4" cellspacing="0">
              <form name="form1" method="post" action="changepasswd.asp">
                <tr>
                  <td width="28%">用户名
                      <input type="hidden" name="m" value="1"></td>
                  <td width="72%"><input name="uname" type="text" class="wenbenkuang" id="uname" value="<%=trim(username)%>" readonly>
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