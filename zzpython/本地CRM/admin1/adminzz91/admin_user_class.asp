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
 del=request("del")
 if selectcb<>"" and del="1" then
 sqldel="delete from cate_adminuser where id in ("& selectcb &")"
	conn.Execute(sqldel)
	response.Redirect("admin_user_class.asp?"&sear&"&page="&page)
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
        </table>          
          <table width="95%" height="90%" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#333333">
          <tr>
            <td valign="top" bgcolor="#F1F3ED"><table width="100%" border="0" cellpadding="0" cellspacing="0">
              <form action="../admin_user_list.asp" method="post" name=form1>
                <%
				    set rs=server.CreateObject("adodb.recordset")
					sql="SELECT * FROM cate_adminuser where code like '__' "
					sql=sql&" order by id desc"
					rs.open sql,conn,1,1
					%>
                    <!--#include file="../include/pagetop_notop.asp"-->
                <tr>
                  <td colspan="4" bgcolor="#D0D8BE"><font color="#000000">&nbsp;</font><font color="#000000">级别</font></td>
                  <td width="68%" bgcolor="#D0D8BE"><font color="#000000">操作</font></td>
                  </tr>
				  <tr>
                  <td colspan="5"><font color="#000000">&nbsp;&nbsp;<a href="../admin_user_class_add.asp">添加大类</a></font></td>
                  </tr>
                <%
				 if not rs.eof  then 
				 i=0                                                                      
				Do While Not rs.EOF 
				if i mod 2=1 then
				bgcol="#F4F4F4"
				else
				bgcol="#F4F4F4"
				end if
				%>
                <tr >
                  <td width="3%" background="../images/2.gif" bgcolor="<%=bgcol%>"><img src="../images/2.gif" width="18" height="18"> </td>
                  <td width="3%" background="../images/2.gif" bgcolor="<%=bgcol%>"><img src="../images/3.gif" width="18" height="18"></td>
                  <td colspan="2" nowrap bgcolor="<%=bgcol%>"><%=rs("meno")%>
[<a href="../admin_user_class_add.asp?code=<%=rs("code")%>"><font color="#FF0000">添加小类</font></a>]
<strong>（代码：<font color="#FF0000"><%=rs("code")%></font>）</strong>
<table width="100%" height="1" border="0" cellpadding="0" cellspacing="0">
                      <tr>
                        <td height="1" bgcolor="#CCCCCC">
						</td>
                      </tr>
                    </table></td>
                  <td bgcolor="<%=bgcol%>"><a href="../admin_user_class_add.asp?mod=1&code=<%=rs("code")%>">修改</a>		|	<a href="../admin_user_class.asp?selectcb=<%=rs("id")%>&del=1" onClick="return confirm('删除这些信息?')">删除</a>
                    <table width="100%" height="1" border="0" cellpadding="0" cellspacing="0">
                      <tr>
                        <td height="1" bgcolor="#CCCCCC">
						</td>
                      </tr>
                    </table>
	                    </td>
                  </tr>
				   <%
		  sql1="select * from cate_adminuser where code like '"&rs("code")&"__' order by id desc"
		  set rs1=server.CreateObject("adodb.recordset")
			rs1.open sql1,conn,1,3
			if not rs1.eof or not rs1.bof then
			do while not rs1.eof
		     %>  
                <tr >
                  <td width="3%" background="../images/2.gif" bgcolor="<%=bgcol%>">&nbsp;</td>
                  <td width="3%" background="../images/2.gif" bgcolor="<%=bgcol%>"><img src="../images/1.gif" width="18" height="18"></td>
                  <td colspan="2" nowrap bgcolor="<%=bgcol%>"><%=rs1("meno")%>
[<a href="../admin_user_class_add.asp?code=<%=rs1("code")%>"><font color="#FF0000">添加小类</font></a>]
<table width="100%" height="1" border="0" cellpadding="0" cellspacing="0">
                      <tr>
                        <td height="1" bgcolor="#CCCCCC">
						</td>
                      </tr>
                    </table>
				  </td>
                  <td bgcolor="<%=bgcol%>">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="../admin_user_class_add.asp?mod=1&code=<%=rs1("code")%>">修改</a>		|	<a href="../admin_user_class.asp?selectcb=<%=rs1("id")%>&del=1" onClick="return confirm('删除这些信息?')">删除</a>
                    <table width="100%" height="1" border="0" cellpadding="0" cellspacing="0">
                      <tr>
                        <td height="1" bgcolor="#CCCCCC">
						</td>
                      </tr>
                    </table>
				  </td>
                </tr>
				 <%
		  sql2="select * from cate_adminuser where code like '"&rs1("code")&"__' order by id desc"
		  set rs2=server.CreateObject("adodb.recordset")
			rs2.open sql2,conn,1,3
			if not rs2.eof or not rs2.bof then
			do while not rs2.eof
		     %>
                <tr >
                  <td width="3%" background="../images/2.gif" bgcolor="<%=bgcol%>">&nbsp;</td>
                  <td width="3%" background="../images/2.gif" bgcolor="<%=bgcol%>">&nbsp;</td>
                  <td width="3%" background="../images/2.gif" bgcolor="<%=bgcol%>"><img src="../images/1.gif" width="18" height="18"></td>
                  <td width="23%" bgcolor="<%=bgcol%>"> <%=rs2("meno")%>
				  <table width="100%" height="1" border="0" cellpadding="0" cellspacing="0">
                      <tr>
                        <td height="1" bgcolor="#CCCCCC">
						</td>
                      </tr>
                    </table>
				  </td>
                  <td bgcolor="<%=bgcol%>">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="../admin_user_class_add.asp?mod=1&code=<%=rs2("code")%>">修改</a>		|	<a href="../admin_user_class.asp?selectcb=<%=rs2("id")%>&del=1" onClick="return confirm('删除这些信息?')">删除</a>  <table width="100%" height="1" border="0" cellpadding="0" cellspacing="0">
                      <tr>
                        <td height="1" bgcolor="#CCCCCC">
						</td>
                      </tr>
                    </table></td>
                </tr>
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
			rs.movenext
			i=i+1
			loop  
			
			else

				  %>
                <tr>
                  <td colspan="10" bgcolor="#FFFFFF">暂时无信息！</td>
                </tr>
                <%end if
				rs.close
				set rs=nothing
				%>
                <script language="JavaScript">
<!--

function CheckAll(form)
  {
  
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
	
    if (e.name.substr(0,3)=='cbb')
	//alert (e.name)
       e.checked = form.cball.checked;
    }
  }
  function cbplay(form)
  {
 selectcb="0"
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
	
    if (e.name.substr(0,3)=='cbb')
       if (e.checked==true)
	   {
	   var selectcb=selectcb+","+e.value
	   }
	  
    }
	//alert (selectcb)
	form.selectcb.value=selectcb.substr(2)
	if (selectcb=='0')
	{
	alert ("选择你要改变的信息！")
	return false
	}
	else if (form.stay.value=="")
	{
	alert ("请选择发布状态！")
	return false
	}
	else
	{
	  
	  form.dostay.value="dochange"
	  form.submit()
	 
	}
  }
 function delitem(form)
  {
  selectcb="0"
  for (var i=0;i<form.elements.length;i++)
    {
    var e = form.elements[i];
	
    if (e.name.substr(0,3)=='cbb')
       if (e.checked==true)
	   {
	   var selectcb=selectcb+","+e.value
	   }
	  
    }
	
	form.selectcb.value=selectcb.substr(2)
	if (selectcb=="0")
	{
	alert ("选择你要删除的信息！")
	return false
	}
	else
	{
	  if (confirm("删除这些信息?"))
	  {
	  form.dostay.value="delitem"
	  form.submit()
	  }
	}
	
  }
  
-->
            </script>
              </form>
              
            </table>
              </td>
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
