<!-- #include file="../checkuser.asp" -->
<!-- #include file="../include/adfsfs!@#.asp" -->
<!--#include file="../include/include.asp"-->
<%
b_name=request("b_name")
n=request("n")
page=Request("page")

'''''''''''''''''''''''''''''''''''
sear="b_name="&b_name&"&rn="&request("rn")
    if isnumeric(page) then
    else
	    page=1
    end if
    
if page="" then 
page=1 
end if
  ''''''''''''''''''''''''''''''''''''''''''
 selectcb=request("selectcb")
 dostay=request("dostay")
 if selectcb<>"" and dostay="delitem" then
 sqldel="delete from users where id in ("& selectcb &")"
	conn.Execute(sqldel)
	response.Redirect("admin_user_list.asp?"&sear&"&page="&page)
 end if
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
    <td background="../newimages/mid_dd.gif"><input name="Submit" type="button" class="button01-out" value="保存" onClick="window.form1.submit()">
    <input name="Submit4" type="button" class="button01-out" value="返回" onClick="window.history.back(1)"></td>
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
            <td width="120" height="23" nowrap background="../newimages/shale_fill_1.gif"><img src="../newimages/shale_arrowhead.gif" width="28" height="23" align="absmiddle">级别权限管理</td>
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
        </table>          <table width="98%" height="90%" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#333333">
          <tr>
            <td valign="top" bgcolor="#F1F3ED"><table width="100%"  border="0" align="center" cellpadding="1" cellspacing="0" bgcolor="#666666">
              <form action="admin_user_class_qx_save.asp" method="post" name="form1">
                <input type="hidden" name="uname" value="<%=request("uname")%>">
				<input name="userid" type="hidden" value="<%=request("userid")%>">
                <%
  
  
 
  sqlw="select * from cate_qx where code like '__00' order by ord asc"
  
						 
						 set rsw=server.CreateObject("adodb.recordset")
						 rsw.open sqlw,conn,1,3
						if not rsw.eof or not rsw.bof then
						do while not rsw.eof
						 %>
                <tr bgcolor="#C1CCAA">
                  <td colspan="3"><font color="#000000"><%=rsw("meno")%></font></td>
                </tr>
                <%
  sqlx="select * from cate_qx where code like '"&left(rsw("code"),2)&"__' and code<>'"&rsw("code")&"' order by ord asc"
  set rsx=server.CreateObject("adodb.recordset")
						 rsx.open sqlx,conn,1,3
						if not rsx.eof or not rsx.bof then
						do while not rsx.eof
  %>
                <tr>
                  <td width="4%" rowspan="2" bgcolor="#FFFFFF">&nbsp;</td>
                  <td colspan="2" bgcolor="#f2f2f2">
                    <%
					qx=""
sql="select * from user_qx where uname='"&request("uname")&"'"
 set rs=server.CreateObject("adodb.recordset")
 rs.open sql,conn,1,2
 if not rs.eof then
 qx=rs("qx")
 end if
 rs.close()
 set rs=nothing
%>
                    <%
	a=0
	if qx<>"" then
	myqx=split(qx,",",-1,1)
	for jj=0 to ubound(myqx)
	if rsx("code")=trim(myqx(jj)) then
	a=1
	end if
	next
	end if
	%>
                    <%if a=1 then%>
                    <input name="qx" type="checkbox" value="<%=rsx("code")%>" checked>&nbsp;<%=rsx("meno")%>                     <%else%>
                    <input name="qx" type="checkbox" value="<%=rsx("code")%>">&nbsp;<%=rsx("meno")%>
                    <%end if%>
</td>
                </tr>
                <tr>
				<td width="26%" bgcolor="#FFFFFF">&nbsp;</td>
                  <td width="70%" bgcolor="#FFFFFF">
				  <%
			if isnull(rsx("qx")) or rsx("qx")="" then
		    else
				  gnarray=split(rsx("qx"),",",-1,1)
				  for nn=0 to ubound(gnarray)
						
				  %>
				    <%
					 gnqx=""
					 sqlgn1="select * from user_gnqx where personid='"&request("uname")&"' and lmid='"&rsx("code")&"'"
					 set rsgn1=server.CreateObject("adodb.recordset")
					 rsgn1.open sqlgn1,conn,1,2
					 if not rsgn1.eof then
					 	gnqx=rsgn1("qx")
					 end if
					 rsgn1.close()
					 set rsgn1=nothing
					 gn=0
						if gnqx<>"" then
							mygnqx=split(gnqx,",",-1,1)
							for n=0 to ubound(mygnqx)
								if cstr(trim(gnarray(nn)))=cstr(trim(mygnqx(n))) then
								gn=1
							end if
							next
						end if
					 %>
					 <%if gn=1 then%>
				      <input name="qxgn<%=rsx("code")%>" type="checkbox" value="<%=trim(gnarray(nn))%>" checked>&nbsp;<%call shows("cate_gnqx",trim(gnarray(nn)))%>
				     <%else%>
					  <input name="qxgn<%=rsx("code")%>" type="checkbox" value="<%=trim(gnarray(nn))%>">&nbsp;<%call shows("cate_gnqx",trim(gnarray(nn)))%>
				     <%end if%>
					 <%
			       next
			end if
				     %>
	| <a href="../admin_user_class_gnqx.asp?code=<%=rsx("code")%>&uname=<%=request("uname")%>">更改功能选项</a></td>
                </tr>
                     <%
					  rsx.movenext
					  loop
					  end if
					  rsx.close
					  set rsx=nothing
					 %>
                     <%
					  rsw.movenext
					  loop
					  end if
					  rsw.close
					  set rsw=nothing
					 %>
                
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
</html><!-- #include file="../../conn_end.asp" -->
