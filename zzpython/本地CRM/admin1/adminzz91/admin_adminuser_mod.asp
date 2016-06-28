<!-- #include file="../checkuser.asp" -->
<!-- #include file="../include/adfsfs!@#.asp" -->
<!--#include file="../../cn/sources/Md5.asp"-->
<!--#include file="../include/include.asp"-->
<%

set rs=server.createobject("adodb.recordset")
sql="select * from users where id="&request("id") 
  rs.open sql,conn,1,2
  userid=rs("userid")
  username=rs("name")
  password=rs("password")
  realname=rs("realname")
  userbg=rs("userbg")
  adminuserid=rs("adminuserid")
  email=rs("email")
  emailpass=rs("emailpass")
  userqx=rs("userqx")
  rs.close()
  set rs=nothing
'safepass=cstr(md5(password,16))
'sqluu="update users set safepass='"&safepass&"' where id="&request("id") 
'response.Write(sqluu)
'conn.execute(sqluu)
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>无标题文档</title>
<link href="../css.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/javascript" src="../include/JSDateFunction.js"></script>
<script language="javascript" src="../include/calendar.js"></script>
<script type="text/javascript" src="../include/b.js"></script>
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
<link href="../inc/Style.css" rel="stylesheet" type="text/css">
</head>

<body bgcolor="7386A5" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="23" background="../newimages/mid_dd.gif" bgcolor="#CED7E7"><img src="../newimages/mid_left.gif" width="23" height="28"></td>
    <td background="../newimages/mid_dd.gif">&nbsp;</td>
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
            <td width="100" height="23" nowrap background="../newimages/shale_fill_1.gif"><img src="../newimages/shale_arrowhead.gif" width="28" height="23" align="absmiddle">用户修改</td>
            <td width="21" background="../newimages/shale_fill_1.gif"><img src="../newimages/shale_photo.gif" width="21" height="23"></td>
            <td background="../newimages/lm_ddd.jpg">&nbsp;</td>
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
              <form name="company_sec"  method="post" action="admin_aduser_save.asp">
              
                <tr>
                  <td width="28%">级别</td>
                  <td width="72%"><select name="userid" id="userid">
          <%
		  sqlw="select * from cate_adminuser where code like '__' order by id desc"
		  set rsw=server.CreateObject("adodb.recordset")
			rsw.open sqlw,conn,1,3
			if not rsw.eof or not rsw.bof then
			do while not rsw.eof
		   %>
		    <%if trim(userid)=trim(rsw("code")) then%>
                    <option value="<%=rsw("code")%>" selected>┆&nbsp;&nbsp;┿<%=rsw("meno")%></option>
            <%else%>
			       <option value="<%=rsw("code")%>">┆&nbsp;&nbsp;┿<%=rsw("meno")%></option> 
			<%end if%>       
				    <%
		  sql1="select * from cate_adminuser where code like '"&rsw("code")&"__' order by id desc"
		  set rs1=server.CreateObject("adodb.recordset")
			rs1.open sql1,conn,1,3
			if not rs1.eof or not rs1.bof then
			do while not rs1.eof
		     %>
			 <%if trim(userid)=trim(rs1("code")) then%>
                    <option value="<%=rs1("code")%>" selected>┆&nbsp;&nbsp;┆&nbsp;&nbsp;┿<%=rs1("meno")%></option>
             <%else%>
			        <option value="<%=rs1("code")%>">┆&nbsp;&nbsp;┆&nbsp;&nbsp;┿<%=rs1("meno")%></option>
			 <%end if%>      
			<%
		  sql2="select * from cate_adminuser where code like '"&rs1("code")&"__' order by id desc"
		  set rs2=server.CreateObject("adodb.recordset")
			rs2.open sql2,conn,1,3
			if not rs2.eof or not rs2.bof then
			do while not rs2.eof
		     %>
			 <%if trim(userid)=trim(rs2("code")) then%>
                    <option value="<%=rs2("code")%>" selected>┆&nbsp;&nbsp;┆&nbsp;&nbsp;┆&nbsp;&nbsp;┿<%=rs2("meno")%></option>
             <%else%> 
			        <option value="<%=rs2("code")%>">┆&nbsp;&nbsp;┆&nbsp;&nbsp;┆&nbsp;&nbsp;┿<%=rs2("meno")%></option>
			 <%end if%>    
					<%
		 rs2.movenext
		 loop
		 end if
		 rs2.close
		  %>
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
                  </select></td>
                </tr>
                <tr>
                  <td>用户名
                      <input type="hidden" name="m" value="1">
                      <input type="hidden" name="id" value=<%=request("id")%>>
                      <input type="hidden" name="uname1" value="<%=trim(username)%>">                  </td>
                  <td width="72%"><input name="uname" type="text" id="uname2" value="<%=trim(username)%>">                  </td>
                </tr>
                <tr>
                  <td>密码</td>
                  <td><input name="password" type="password" id="password2" value="<%=trim(password)%>">                  </td>
                </tr>
                <tr>
                  <td>Email</td>
                  <td><input name="Email" type="text" id="Email" value="<%response.Write(email)%>"></td>
                </tr>
                <tr>
                  <td>Email 密码</td>
                  <td><input name="emailpass" type="text" id="emailpass" value="<%response.Write(emailpass)%>"></td>
                </tr>
                <tr>
                  <td>是否主管</td>
                  <td><input type="radio" name="userqx" value="1" <%if userqx="1" then response.Write("checked")%>>
                    是
                      <input type="radio" name="userqx" value="0" <%if userqx="0" then response.Write("checked")%>>
                      否</td>
                </tr>
                <tr>
                  <td>代表颜色</td>
                  <td><input name="userbg" type="text" id="userbg" value="<%=userbg%>"></td>
                </tr>
                <tr>
                  <td>真实姓名</td>
                  <td><input name="realname" type="text" id="realname2" value="<%=trim(realname)%>">                  </td>
                </tr>
                <tr align="center">
                  <td align="left">次级别</td>
                  <td align="left">
				   <%
		  sqlw="select * from cate_adminuser where code like '__' order by id desc"
		  set rsw=server.CreateObject("adodb.recordset")
			rsw.open sqlw,conn,1,3
			if not rsw.eof or not rsw.bof then
			do while not rsw.eof
			'response.Write(adminuserid)
			checkflag=0
			if adminuserid<>"" or not isnull(adminuserid) then
			myuseriy=split(adminuserid,",",-1,1)
			for ii=0 to ubound(myuseriy)
				if cstr(trim(myuseriy(ii)))=cstr(rsw("code")) then
				checkflag=1
				
				end if
			next
			if checkflag=1 then
			checkvalue="checked"
			else
			checkvalue=""
			end if
			'response.Write(checkvalue)
			end if
		   %>
				  <input name="adminuserid" type="checkbox" id="adminuserid" value="<%=rsw("code")%>" <%=checkvalue%>>
				  <%=rsw("meno")%>
				                    <%
		 rsw.movenext
		 loop
		 end if
		 rsw.close
		  %>				  </td>
                </tr>
                <tr align="center">
                  <td align="left">&nbsp;</td>
                  <td align="left">&nbsp;</td>
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
<!-- #include file="../../conn_end.asp" -->