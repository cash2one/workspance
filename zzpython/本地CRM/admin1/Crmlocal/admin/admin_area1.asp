<%@ Language=VBScript %>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!--#include file="../../include/include.asp"-->
<%
frompagea=split(request.servervariables("script_name"),"/")
frompage=lcase(frompagea(UBound(frompagea)))
frompagequrstr=Request.ServerVariables("QUERY_STRING")
frompagequrstr=replace(frompagequrstr,"&","~amp~")
if request("closeflag")="1" then
sql="update cate_qx set closeflag=0 where id="&request("id")
conn.execute(sql)
response.Redirect("admin_area.asp?code="&request("code"))
end if
if request("closeflag")="0" then
sql="update cate_qx set closeflag=1 where id="&request("id")
conn.execute(sql)
response.Redirect("admin_area.asp?code="&request("code"))
end if
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>无标题文档</title>
<link href="../../../default.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
td {
	font-size: 12px;
	line-height: 22px;
}
.lmline {
	border-bottom-width: 1px;
	border-bottom-style: solid;
	border-bottom-color: #999999;
}
-->
</style>
<link href="../../inc/Style.css" rel="stylesheet" type="text/css">
<link href="../../FORUM.CSS" rel="stylesheet" type="text/css">
<link href="../../css.css" rel="stylesheet" type="text/css">
</head>

<body bgcolor="7386A5" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="23" background="../../newimages/mid_dd.gif" bgcolor="#CED7E7"><img src="../../newimages/mid_left.gif" width="23" height="28"></td>
    <td background="../../newimages/mid_dd.gif">      <%if request("code")<>"" then%>
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
        <td width="100" height="23" nowrap background="../../newimages/shale_fill_1.gif"><img src="../../newimages/shale_arrowhead.gif" width="28" height="23" align="absmiddle">栏目
          管理</td>
        <td width="21" background="../../newimages/shale_fill_1.gif"><img src="../../newimages/shale_photo.gif" width="21" height="23"></td>
        <td background="../../newimages/lm_ddd.jpg">&nbsp;</td>
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
          <td bgcolor="#F1F3ED"><table width="100%" align="center" cellpadding="1" cellspacing="0" bordercolordark="#C6DBE7" bordercolorlight="#092094">
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
                  <form action="admin_area_add.asp" method="get" name="form2" onSubmit="return subchk()">
                    <%
		if request("code")<>"" then
		sql="select * from cate_qx where code='"&request("code")&"'"
        set rs=server.createobject("adodb.recordset")
		rs.open sql,conn,1,1
		
		meno=rs("meno")
		link=rs("link")
		
		%>
                    <tr>
                      <td align="center"><input type="hidden" name="m" value="1">
                          <input type="hidden" name="code" value=<%=request("code")%>>
              栏目名称
              <input name="meno" type="text" class="wenbenkuang" id="meno" value="<%=meno%>" size="20">
              链接
              <input name="link" type="text" class="wenbenkuang" id="link" value="<%=link%>" size="20">
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
                  <form action="admin_area_add.asp" method="get" name="form2" onSubmit="return subchk()">
                    <tr>
                      <td align="center"><input type="hidden" name="code" value=<%=request("code")%>>
              栏目名称
                <input name="meno" type="text" class="wenbenkuang" id="meno" size="20">
              链接
              <input name="link" type="text" class="wenbenkuang" id="link" size="20">
              排序
              <input name="ord" type="text" id="ord" value="0" size="3">
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
				sqlw="select * from cate_qx where code like '"&left(request("code"),2)&"%' and code<>'"&left(request("code"),2)&"00' and (subid=1 or subid=0 or subid is null) order by ord asc"
			  end if
			  if request("code")="" then
				sqlw="select * from cate_qx where code like '__00' and (subid=1 or subid=0 or subid is null) order by ord asc"
			  end if
			set rsw=server.CreateObject("adodb.recordset")
			rsw.open sqlw,conn,1,3
			if not rsw.eof or not rsw.bof then
			i=0
			do while not rsw.eof
			%>
            <tr class="lmline" bgcolor="#CCCCCC">
              <td class="lmline"><a href="admin_area.asp?code=<%=rsw("code")%>"><b><%=rsw("meno")%></b></a><br><%=rsw("link")%></td>
              <form name="form<%=i%>" method="post" action="admin_area_ord.asp">
                <td nowrap class="lmline">
                <input type="hidden" name="id" value="<%=rsw("id")%>">
                <input type="hidden" name="code" value="<%=request("code")%>">排序
                <input name="ord" type="text" class="wenbenkuang" id="ord" value="<%=rsw("ord")%>" size="3">
                <input name="Submit2" type="submit" value="go" class="wenbenkuang" >
				<%
				if request("code")<>"" then
					if rsw("subid")<>"0" then
					'response.Write(rsw("subid")&"")
					end if
                %>
               <a href="admin_area_sub.asp?id=<%=rsw("id")%>&code=<%=request("code")%>&subflag=1" target="_blank">设为次目录</a>		
               <%end if%>
				<%if rsw("closeflag")="1" then%>
                <input type="button" name="Submit3" value="开通" onClick="window.location='admin_area.asp?id=<%=rsw("id")%>&closeflag=1&code=<%=request("code")%>'">
                <%else%>
                <input type="button" name="Submit3" value="关闭" style="background:#FF6600" onClick="window.location='admin_area.asp?id=<%=rsw("id")%>&closeflag=0&code=<%=request("code")%>'">
                <%end if%>		       </td>
              </form>
              <td align="center" class="lmline"> <a href="admin_area_del.asp?id=<%=rsw("id")%>&code=<%=request("code")%>" onClick="return confirm('删除这些信息?')">删除</a> </td>
            </tr>
            <%
			sqlw1="select * from cate_qx where subid="&rsw("code")&" order by ord asc"
			set rsw1=server.CreateObject("adodb.recordset")
			rsw1.open sqlw1,conn,1,3
			if not rsw1.eof or not rsw1.bof then
			do while not rsw1.eof
			%>
            <tr >
              <td class="lmline">&nbsp;&nbsp;<a href="admin_area.asp?code=<%=rsw1("code")%>"><%=rsw1("meno")%></a><br>&nbsp;&nbsp;<%=rsw1("link")%></td>
              <form name="form<%=i%>" method="post" action="admin_area_ord.asp">
                <td class="lmline">
                <input type="hidden" name="id" value="<%=rsw1("id")%>">
                <input type="hidden" name="code" value="<%=request("code")%>">排序
                <input name="ord" type="text" class="wenbenkuang" id="ord" value="<%=rsw1("ord")%>" size="3">
                <input name="Submit2" type="submit" value="go" class="wenbenkuang" >
				<%
				if request("code")<>"" then
					if rsw1("subid")<>"0" then
					'response.Write(rsw1("subid")&"")
					end if
                %>
               <a href="admin_area_sub.asp?id=<%=rsw1("id")%>&code=<%=request("code")%>&subflag=1" target="_blank">设为次目录</a>		
               <%end if%>
				<%if rsw1("closeflag")="1" then%>
                <input type="button" name="Submit3" value="开通" onClick="window.location='admin_area.asp?id=<%=rsw1("id")%>&closeflag=1&code=<%=request("code")%>'">
                <%else%>
                <input type="button" name="Submit3" value="关闭" style="background:#FF6600" onClick="window.location='admin_area.asp?id=<%=rsw1("id")%>&closeflag=0&code=<%=request("code")%>'">
                <%end if%>
		       </td>
              </form>
              <td align="center" class="lmline"> <a href="admin_area_del.asp?id=<%=rsw1("id")%>&code=<%=request("code")%>" onClick="return confirm('删除这些信息?')">删除</a> </td>
            </tr>
            <%
			rsw1.movenext
			i=i+1
			loop
			end if
			rsw1.close
			set rsw1=nothing
			%>
            <%
			rsw.movenext
			i=i+1
			loop
			end if
			rsw.close
			set rsw=nothing
			%>
          </table>
          </td>
        </tr>
    </table>
    
      <br>
    <br></td>
  </tr>
</table>
</body>
</html>
<!-- #include file="../../../conn_end.asp" -->