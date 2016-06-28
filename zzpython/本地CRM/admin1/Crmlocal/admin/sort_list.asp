<!-- #include file="../../include/ad!$#5V.asp" -->
<%
db_table="cate_webCompType"
if request("action")="del" then

 sqldel="delete from "&db_table&" where id="&request("id")
	conn.Execute(sqldel)
	response.Redirect("sort_list.asp?"&sear&"&page="&page)

end if
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>无标题文档</title>
<link href="../../../css.css" rel="stylesheet" type="text/css">
<link href="../../../cn/sources/style.css" rel="stylesheet" type="text/css">
</head>

<body bgcolor="7386A5" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="23" background="../../newimages/mid_dd.gif" bgcolor="#CED7E7"><img src="/admin1/newimages/mid_left.gif" width="23" height="28"></td>
    <td background="/admin1/newimages/mid_dd.gif">&nbsp;</td>
  </tr>
</table>
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td valign="top"><table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td width="100" height="23" nowrap background="/admin1/newimages/shale_fill_1.gif"><img src="/admin1/newimages/shale_arrowhead.gif" width="28" height="23" align="absmiddle">类别管理</td>
        <td width="21" background="../newimages/shale_fill_1.gif"><img src="/admin1/newimages/shale_photo.gif" width="21" height="23"></td>
        <td background="/admin1/newimages/lm_ddd.jpg">&nbsp;</td>
      </tr>
    </table>
      <table width="98%" height="90%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td height="19" valign="top" bgcolor="#E7EBDE"><table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td>&nbsp;</td>
            </tr>
          </table>
            <table width="95%" height="80%" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#000000">
              <tr>
                <td bgcolor="#F1F3ED"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td>&nbsp;</td>
                  </tr>
                </table>
                  <table width="98%" height="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                  <tr>
                    <td valign="top"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                        
                        <tr>
                          <td width="18" ><img src="../../newimages/2.gif" width="18" height="18"></td>
                          <td width="18" ><img src="../../newimages/1.gif" width="18" height="18"></td>
                          <td colspan="3"> [<a href="sort_add.asp">添加类别</a>]</td>
                        </tr>
                        <%
		  sqlw="select * from "&db_table&" where code like '__' order by id desc"
		  set rsw=server.CreateObject("adodb.recordset")
			rsw.open sqlw,conn,1,3
			if not rsw.eof or not rsw.bof then
			do while not rsw.eof
		   %>
                        <tr>
                          <td width="18" ><img src="../../newimages/2.gif" width="18" height="18"></td>
                          <td width="18" ><img src="../../newimages/3.gif" width="18" height="18"></td>
                          <td colspan="3"><%=rsw("meno")%> [<a href="sort_add.asp?code=<%=rsw("code")%>">添加小类</a>][<a href="sort_add.asp?action=mod&id=<%=rsw("id")%>">修改</a>][<a href="sort_list.asp?action=del&id=<%=rsw("id")%>" onClick="return confirm('删除这些信息?')">删除</a>]</td>
                        </tr>
                        <%
		  sql1="select * from "&db_table&" where code like '"&rsw("code")&"__' order by id desc"
		  set rs1=server.CreateObject("adodb.recordset")
			rs1.open sql1,conn,1,3
			if not rs1.eof or not rs1.bof then
			do while not rs1.eof
		%>
                        <tr>
                          <td background="../newimages/2.gif" bgcolor="#F4F4F4">&nbsp;</td>
                          <td background="../newimages/2.gif" bgcolor="#F4F4F4">&nbsp;</td>
                          <td width="25"><img src="../../newimages/1.gif" width="18" height="18"></td>
                          <td colspan="2"><%=rs1("meno")%> [<a href="sort_add.asp?code=<%=rs1("code")%>">添加小类</a>][<a href="sort_add.asp?action=mod&id=<%=rs1("id")%>">修改</a>][<a href="sort_list.asp?action=del&id=<%=rs1("id")%>" onClick="return confirm('删除这些信息?')">删除</a>]</td>
                        </tr>
                        <%
		  sql2="select * from "&db_table&" where code like '"&rs1("code")&"__' order by id desc"
		  set rs2=server.CreateObject("adodb.recordset")
			rs2.open sql2,conn,1,3
			if not rs2.eof or not rs2.bof then
			do while not rs2.eof
		%>
                        <tr>
                          <td background="../newimages/2.gif" >&nbsp;</td>
                          <td background="../newimages/2.gif" >&nbsp;</td>
                          <td background="../newimages/2.gif">&nbsp;</td>
                          <td width="24"><img src="../../newimages/1.gif" width="18" height="18"></td>
                          <td width="685" align="left"><div align="left"><%=rs2("meno")%> [<a href="sort_add.asp?action=mod&id=<%=rs2("id")%>">修改</a>][<a href="sort_list.asp?action=del&id=<%=rs2("id")%>" onClick="return confirm('删除这些信息?')">删除</a>]</div>
                          </td>
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
		 rsw.movenext
		 loop
		 end if
		 rsw.close
		 set rsw=nothing
		  %>
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
            </table>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</body>
</html>

