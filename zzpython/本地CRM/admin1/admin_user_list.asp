<!-- #include file="checkuser.asp" -->
<!-- #include file="include/adfsfs!@#.asp" -->
<!--#include file="../cn/sources/Md5.asp"-->
<!--#include file="include/include.asp"-->
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
            <td width="100" height="23" nowrap background="newimages/shale_fill_1.gif"><img src="newimages/shale_arrowhead.gif" width="28" height="23" align="absmiddle">修改用户</td>
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
        </table>          <table width="95%" height="90%" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#333333">
          <tr>
            <td valign="top" bgcolor="#F1F3ED"><table width="100%" border="0" cellpadding="2" cellspacing="1" bgcolor="#CCCCCC">
              <form action="admin_user_list.asp" method="post" name=form1>
                <%
				    set rs=server.CreateObject("adodb.recordset")
					sql="SELECT * FROM users where id is not null "
					sql=sql&" order by userid desc"
					rs.open sql,conn,1,1
					%>
                    <!--#include file="include/pagetop_notop.asp"-->
                <tr bgcolor="#D0D8BE">
                  <td width="21"><font color="#000000">&nbsp;</font></td>
                  <td><font color="#000000">用户名</font></td>
                  <td align="right"><font color="#000000">操作</font></td>
                </tr>
                <%
				 if not rs.eof  then 
				 i=0                                                                      
				Do While Not rs.EOF and RowCount>0
				if i mod 2=1 then
				bgcol="#ffffff"
				else
				bgcol="#ffffff"
				end if
				%>
				<%if rs("userid")<>pp then%>
                <tr bgcolor="<%=bgcol%>" >
                  <td colspan="3"> <%
				call shows("cate_adminuser",rs("userid"))
				%></td>
                  </tr>
				 <%end if%>
                <tr bgcolor="<%=bgcol%>" >
                  <td><input name="cbb<%=i%>" type="checkbox" value="<%=rs("id")%>">
                  </td>
                  <td>
				  
				  <%=rs("name")%>
				  <%if session("userid")="10" then%>
				  (<%=rs("password")%>)
				  <%end if%>
				  </td>
                  <td align="right" nowrap>
				   <%if rs("closepart")=0 then%>
				  <input name="Submit4" type="button" class="button01-out" value="开通定时" onClick="window.location='admin_adminuser_closepart.asp?id=<%=rs("id")%>&flag=1'">
				  <%else%>
				  <input name="Submit4" type="button" class="button01-out" value="关闭定时" style=" background:#FF0000;color:#ffffff;" onClick="window.location='admin_adminuser_closepart.asp?id=<%=rs("id")%>&flag=0'">
				  <%end if%>
				  <%if rs("closeflag")=1 then%>
				  <input name="Submit4" type="button" class="button01-out" value="开通帐号" onClick="window.location='admin_adminuser_close.asp?id=<%=rs("id")%>&flag=0'">
				  <%else%>
				  <input name="Submit4" type="button" class="button01-out" value="关闭帐号" style=" background:#FF0000;color:#ffffff;" onClick="window.location='admin_adminuser_close.asp?id=<%=rs("id")%>&flag=1'">
				  <%end if%>
				  
                    <input name="Submit3" type="button" class="button01-out" value="权限设置" onClick="window.location='admin_user_class_qx.asp?uname=<%=rs("id")%>'">
                    <input name="Submit" type="button" class="button01-out" value="修改" onClick="window.location='admin_adminuser_mod.asp?id=<%=rs("id")%>'">
                   
				    </td>
                </tr>
                <%
				                                                    
     		RowCount = RowCount - 1
			pp=rs("userid")
			rs.movenext
			i=i+1
			loop  
			
			else

				  %>
                <tr bgcolor="#FFFFFF">
                  <td colspan="8">暂时无信息！</td>
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
                <tr bgcolor="#F1F3ED">
                  <td colspan="9" align="right">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><input name="cball" type="checkbox" value="" onClick="CheckAll(this.form)">
                          全选                        </td>
                        <td align="right"><input type="hidden" name="dostay">
                            <input type="hidden" name="selectcb">
                            <input type="hidden" name="kind2" value=<%=kind%>>
                            <input type="hidden" name="Statuss2" value="<%=Statuss%>">
                            <input type="hidden" name="orderby2" value="<%=orderby%>">
                            <input type="hidden" name="rn" value="<%=request("rn")%>">
                            <input type="hidden" name="page" value="<%=page%>">
                            <input type="hidden" name="b_name2" value="<%=b_name%>">
                            <input name="Submit2" type="button" class="button01-out" onClick="delitem(this.form)" value="删除">
                        </td>
                      </tr>
                    </table>
                  </td>
                </tr>
              </form>
              
            </table>
              <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td><!-- #include file="include/page.asp" -->
                  </td>
                </tr>
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
</html><%
call CloseConn(conn) 
endConnection()%>

