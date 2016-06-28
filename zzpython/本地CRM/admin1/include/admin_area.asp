<%@ Language=VBScript %>
<!-- #include file="checkuser.asp" -->
<!-- #include file="include/conn.asp" -->
<!--#include file="include/include.asp"-->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>无标题文档</title>
<link href="../default.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
td {
	font-size: 12px;
	line-height: 22px;
}
-->
</style>
<link href="inc/Style.css" rel="stylesheet" type="text/css">
<link href="FORUM.CSS" rel="stylesheet" type="text/css">
<link href="css.css" rel="stylesheet" type="text/css">
</head>

<body bgcolor="7386A5" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="23" background="newimages/mid_dd.gif" bgcolor="#CED7E7"><img src="newimages/mid_left.gif" width="23" height="28"></td>
    <td background="newimages/mid_dd.gif">      <%if request("code")<>"" then%>
      <input name="Submit" type="button" class="button01-out" onClick="window.location='admin_area.asp'" value="返回">
    <%end if%>    </td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="5">	</td>
  </tr>
</table>
<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="100" height="23" nowrap background="newimages/shale_fill_1.gif"><img src="newimages/shale_arrowhead.gif" width="28" height="23" align="absmiddle">栏目
          管理</td>
        <td width="21" background="newimages/shale_fill_1.gif"><img src="newimages/shale_photo.gif" width="21" height="23"></td>
        <td background="newimages/lm_ddd.jpg">&nbsp;</td>
      </tr>
    </table></td>
  </tr>
</table>
<table width="98%" height="88%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#000000">
  <tr>
    <td valign="top" bgcolor="#E7EBDE">      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>&nbsp;</td>
        </tr>
      </table>
      <table width="95%" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#000000">
        <tr>
          <td bgcolor="#F1F3ED"><table width="98%" align="center" cellpadding="3" cellspacing="0" bordercolordark="#C6DBE7" bordercolorlight="#092094">
            <tr valign="top">
              <td height="10" colspan="6" align="center">&nbsp; </td>
            </tr>
            <tr>
              <td height="11" colspan="6" align="center"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <script language=JavaScript>
  function subchk()
  {
      if(form2.meno.value=="")
      {
         alert("请输入中文类别名称!\n");
         form2.meno.focus();
         return  false;
       }
       if  (form2.meno_e.value=="")       
        {   
		alert("请输入英文类别名称!\n");
         form2.meno_e.focus();
         return  false;                 
        }  
		       
              
                
   }
    function subchk1()
  {
      if(form1.meno.value=="")
      {
         alert("请输入中文类别名称!\n");
         form1.meno.focus();
         return  false;
       }
       if  (form1.meno_e.value=="")       
        {   
		alert("请输入英文类别名称!\n");
         form1.meno_e.focus();
         return  false;                 
        }  
		       
              
                
   }
          </script>
                  <form action="admin_area_add.asp" method="get" name="form2" onsubmit="return subchk()">
                    <%
		if request("code")<>"" then
		sql="select * from cate_qx where code='"&request("code")&"'"
        set rs=server.createobject("adodb.recordset")
		rs.open sql,conn,1,2
		
		meno=rs("meno")
		link=rs("link")
		
		%>
                    <tr>
                      <td align="center"><input type="hidden" name="m" value="1">
                          <input type="hidden" name="code" value=<%=request("code")%>>
              栏目名称
              <input name="meno" type="text" class="wenbenkuang" id="meno" value="<%=meno%>" size="30.5">
              链接
              <input name="link" type="text" class="wenbenkuang" id="link" value="<%=link%>" size="30">
              <input name="Submit42" type="submit" class="button01-out" value="修改">
                      </td>
                    </tr>
                    <%end if%>
                  </form>
                </table>
              </td>
            </tr>
            <%if right(request("code"),2)="00" or request("code")="" then%>
            <tr>
              <td height="22" colspan="6">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <script language=JavaScript>
  function subchk()
  {
      if(form2.meno.value=="")
      {
         alert("请输入中文类别名称!\n");
         form2.meno.focus();
         return  false;
       }
       if  (form2.meno_e.value=="")       
        {   
		alert("请输入英文类别名称!\n");
         form2.meno_e.focus();
         return  false;                 
        }  
		       
              
                
   }
    function subchk1()
  {
      if(form1.meno.value=="")
      {
         alert("请输入中文类别名称!\n");
         form1.meno.focus();
         return  false;
       }
       if  (form1.meno_e.value=="")       
        {   
		alert("请输入英文类别名称!\n");
         form1.meno_e.focus();
         return  false;                 
        }  
		       
              
                
   }
          </script>
                  <form action="admin_area_add.asp" method="get" name="form2" onsubmit="return subchk()">
                    <tr>
                      <td align="center"><input type="hidden" name="code" value=<%=request("code")%>>
              栏目名称
                <input name="meno" type="text" class="wenbenkuang" id="meno" size="30.5">
              链接
              <input name="link" type="text" class="wenbenkuang" id="link" size="30">
              <input name="Submit4" type="submit" class="button01-out" value="添加">
                      </td>
                    </tr>
                  </form>
                </table>
              </td>
            </tr>
            <%end if%>
            <%
  if request("code")<>"" then
  sqlw="select * from cate_qx where code like '"&left(request("code"),2)&"%' and code<>'"&left(request("code"),2)&"00' order by ord asc"
  end if
  
  if request("code")="" then
  sqlw="select * from cate_qx where code like '__00' order by ord asc"
  end if
						 
						 set rsw=server.CreateObject("adodb.recordset")
						 rsw.open sqlw,conn,1,3
						if not rsw.eof or not rsw.bof then
						i=0
						do while not rsw.eof
						 %>
            <tr>
              <td height="22" align="center"><%=rsw("code")%></td>
              <td align="center"> <a href="admin_area.asp?code=<%=rsw("code")%>"><%=rsw("meno")%></a> </td>
              <td align="center"><%=rsw("link")%></td>
              <form name="form<%=i%>" method="post" action="admin_area_ord.asp">
                <td align="center">
                  <input type="hidden" name="id" value="<%=rsw("id")%>">
                  <input type="hidden" name="code" value="<%=request("code")%>">
        排序
        <input name="ord" type="text" class="wenbenkuang" id="ord" value="<%=rsw("ord")%>" size="5">
        <input name="Submit2" type="submit" class="button01-out" value="go">
        <input type="button" name="Submit" value="设置为次目录" class="wenbenkuang">
                </td>
              </form>
              <td align="center"> <a href="admin_area_del.asp?id=<%=rsw("id")%>&code=<%=request("code")%>" onClick="return confirm('删除这些信息?')">删除</a> </td>
            </tr>
            <%
					  rsw.movenext
						i=i+1
						loop
					  end if
					   rsw.close
						 %>
          </table>
          <br>
          <br></td>
        </tr>
    </table></td>
  </tr>
</table>
</body>
</html>
