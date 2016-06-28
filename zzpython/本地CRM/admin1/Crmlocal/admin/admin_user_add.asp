<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!--#include file="../../include/include.asp"-->
<% 
'批量分配权限
'sql="select * from user_qx where uname=60"
'set rs=conn.execute(sql)
'qx=rs("qx")
'rs.close
'set rs=nothing
'
'sql="select * from users where userID='1301'"
'set rs=server.createobject("adodb.recordset")
'rs.open sql,conn,1,3
'while not rs.eof
'	sql0="select * from user_qx where uname="&rs("id")
'	set rs0=server.createobject("adodb.recordset")
'	rs0.open sql0,conn,1,3
'	if rs0.eof and rs0.bof then
'		rs0.addnew
'		rs0("uname")=rs("id")
'		rs0("qx")=qx
'		rs0.update
'	end if
'	rs0.close
'	set rs0=nothing
'	rs.movenext
'wend
 sqluser="select realname,ywadminid,xuqianFlag,adminuserid,partid,usertel from users where id="&session("personid")
 set rsuser=conn.execute(sqluser)
 userName=rsuser(0)
 ywadminid=rsuser(1)
 xuqianFlag=rsuser(2)
 partuserid=rsuser(3)
 adminuserid=rsuser("adminuserid")
 adminmypartid=rsuser("partid")
 mytel=rsuser("usertel")
 rsuser.close
 set rsuser=nothing
 %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>无标题文档</title>
<link href="../../css.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/javascript" src="../../include/JSDateFunction.js"></script>
<script language="javascript" src="../../include/calendar.js"></script>
<script type="text/javascript" src="../../include/b.js"></script>
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
		if (company_sec.userdengji.value=="")
		{
			alert("请选择用户等级!\n");
         	company_sec.userdengji.focus();
         	return  false;     
		}
              
                
   }
    </script>
<link href="../../inc/Style.css" rel="stylesheet" type="text/css">
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
            <td width="100" height="23" nowrap background="../../newimages/shale_fill_1.gif"><img src="../../newimages/shale_arrowhead.gif" width="28" height="23" align="absmiddle">添加用户</td>
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
              <form name="company_sec" method="post" action="admin_user_save.asp" onSubmit="return subchk()">
                <tr>
                  <td width="80" align="right">部门</td>
                  <td><select name="userid" id="userid">
                  <option value="">请选择...</option>
				  <%
				  sqlm=""
				  if session("userid")<>"10" then
				  	  sqlm=sqlm&" code in("&ywadminid&") and code like '__'"
				  else
				  	  sqlm=sqlm&" code like '__'"
				  end if
		  sqlw="select * from cate_adminuser where "&sqlm&" order by id desc"
		  set rsw=server.CreateObject("adodb.recordset")
			rsw.open sqlw,conn,1,1
			if not rsw.eof or not rsw.bof then
			do while not rsw.eof
		   %>
                  <option value="<%=rsw("code")%>">┆&nbsp;&nbsp;┿<%=rsw("meno")%></option>
				  <%
		  sql1="select * from cate_adminuser where code like '"&rsw("code")&"__'  order by id desc"
		  set rs1=server.CreateObject("adodb.recordset")
			rs1.open sql1,conn,1,1
			if not rs1.eof or not rs1.bof then
			do while not rs1.eof
		     %> 
			<option value="<%=rs1("code")%>">┆&nbsp;&nbsp;┆&nbsp;&nbsp;┿<%=rs1("meno")%></option>
             <%
		  sql2="select * from cate_adminuser where code like '"&rs1("code")&"__' and closeflag=1  order by id desc"
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
				  
                  </select></td>
                </tr>
                <tr>
                  <td align="right">权限等级</td>
                  <td>
                  <select name="userdengji" id="userdengji">
                  <option value="">......</option>
                  <%
				  sqlcate="select meno,code from crm_category where code like '1001____' order by ord asc"
				  set rscate=conn.execute(sqlcate)
				  if not rscate.eof or not rscate.bof then
				  i=0
				  while not rscate.eof
				  %>
                    <option value="<%=rscate("code")%>"><%=rscate("meno")%></option>
                  <%
				  i=i+1
				  rscate.movenext
				  wend
				  end if
				  rscate.close
				  set rscate=nothing
				  %>
                  </select> 
                  <font color="#CC0000"><br>
                  （如果不是销售部门的请不要选择）</font>
                  
                  </td>
                </tr>
                <tr>
                  <td align="right">用户名称</td>
                  <td><input name="uname" type="text" class="wenbenkuang" id="uname"></td>
                </tr>
                <tr>
                  <td align="right">登录密码</td>
                  <td><input name="password" type="password" class="wenbenkuang" id="password">
                  </td>
                </tr>
                <tr>
                  <td align="right">真实姓名</td>
                  <td><input name="realname" type="text" class="wenbenkuang" id="realname">
                  </td>
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