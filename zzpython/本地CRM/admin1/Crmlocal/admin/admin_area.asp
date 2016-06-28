<%@ Language=VBScript %>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!--#include file="../../include/include.asp"-->
<%
parent_id=request("parent_id")
'if parent_id="" then parent_id=0
frompagea=split(request.servervariables("script_name"),"/")
frompage=lcase(frompagea(UBound(frompagea)))
frompagequrstr=Request.ServerVariables("QUERY_STRING")
frompagequrstr=replace(frompagequrstr,"&","~amp~")
if request("closeflag")="1" then
	sql="update cate_qx set closeflag=0 where id="&request("id")
	conn.execute(sql)
	response.Redirect("?parent_id="&parent_id&"")
end if
if request("closeflag")="0" then
	sql="update cate_qx set closeflag=1 where id="&request("id")
	conn.execute(sql)
	response.Redirect("?parent_id="&parent_id&"")
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
.textcaidan
{
	border: 1px solid #CCC;	
}
.addkuang
{
	border: 1px solid #999;
	background-color: #f2f2f2;
}
-->
</style>
<link href="../../inc/Style.css" rel="stylesheet" type="text/css">
<link href="../../FORUM.CSS" rel="stylesheet" type="text/css">
<link href="../../css.css" rel="stylesheet" type="text/css">
<script>
function showadd(pid)
{
	var obj=document.getElementById("addkuang"+pid)
	if (obj.style.display=="")
	{
		obj.style.display="none"
	}else
	{
		obj.style.display=""
	}
}
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
</head>

<body bgcolor="7386A5" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="5">	</td>
  </tr>
</table>
<table width="98%" height="88%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#000000">
  <tr>
    <td valign="top" bgcolor="#E7EBDE">
      <table width="95%" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#000000">
        <tr>
          <td bgcolor="#F1F3ED"><table width="100%" align="center" cellpadding="1" cellspacing="0" bordercolordark="#C6DBE7" bordercolorlight="#092094">
            <tr valign="top">
              <td height="10" colspan="6" align="center"><input name="Submit" type="button" class="button01-out" onClick="window.location='admin_area.asp'" value="返回"></td>
            </tr>
            <tr>
              <td height="11" colspan="6" align="center">
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                 
                  <form action="admin_area_add.asp" method="post" name="form2" onSubmit="return subchk()">
                    <%
					if parent_id<>"" then
						sql="select * from cate_qx where id='"&parent_id&"'"
						set rs=server.createobject("adodb.recordset")
						
						rs.open sql,conn,1,1
						if not rs.eof or not rs.bof then
						meno=rs("meno")
						link=rs("link")
					%>
								<tr>
								  <td align="center">
								  <input type="hidden" name="m" value="1">
                                  <input type="hidden" name="subid" value="<%=parent_id%>">
								  <input type="hidden" name="id" value=<%=parent_id%>>栏目名称
								  <input name="meno" type="text" class="wenbenkuang" id="meno" value="<%=meno%>" size="20">链接
								  <input name="link" type="text" class="wenbenkuang" id="link" value="<%=link%>" size="20">
								  <input name="Submit42" type="submit" class="button01-out" value="修改">
								  </td>
								</tr>
					  <%
					  end if
					  rs.close
					  set rs=nothing
					  end if
					  
					  %>
                  </form>
                  <!--添加 begin-->
                  <div class="addkuang" align="center">
                      <form action="admin_area_add.asp" method="post" name="form2" onSubmit="return subchk()">
                        <input type="hidden" name="subid" id="subid" value=<%=parent_id%>>
                        <input type="hidden" name="parent_id" id="parent_id" value=<%=parent_id%>>栏目名称
                        <input name="meno" type="text" class="wenbenkuang" id="meno" size="20">链接
                        <input name="link" type="text" class="wenbenkuang" id="link" size="20">排序
                        <input name="ord" type="text" id="ord" value="0" size="3">
                        <input name="Submit4" type="submit" class="button01-out" value="添加"> 
                      </form>
                  </div>
                  <!--添加 end-->
                </table>
                </td>
            </tr>
            
            <%
			  if parent_id<>"" then
				 sqlw="select * from cate_qx where parent_id="&parent_id&" order by ord asc"
			  end if
			  if parent_id="" then
				 sqlw="select * from cate_qx where parent_id=0 order by ord asc"
			  end if
			set rsw=server.CreateObject("adodb.recordset")
			'response.Write(sqlw)
			rsw.open sqlw,conn,1,1
			if not rsw.eof or not rsw.bof then
			i=0
			do while not rsw.eof
				'subcode=rsw("code")
'				sqlp="select id from cate_qx where code<>'"&left(subcode,2)&"00' and code like '"&left(subcode,2)&"__' and subid<=1"
'				set rsp=conn.execute(sqlp)
'				if not rsp.eof or not rsp.bof then
'					while not rsp.eof
'						sqlt="update cate_qx set parent_id="&rsw("id")&" where id="&rsp(0)
'						conn.execute(sqlt)
'					rsp.movenext
'					wend
'				end if
'				rsp.close
'				set rsp=nothing
				'if parent_id<>"" then
'					sqlp="select id from cate_qx where  subid='"&subcode&"'"
'					response.Write(sqlp&"<br>")
'					set rsp=conn.execute(sqlp)
'					if not rsp.eof or not rsp.bof then
'						while not rsp.eof
'						sqlt="update cate_qx set parent_id="&rsw("id")&" where id="&rsp(0)
'						conn.execute(sqlt)
'						rsp.movenext
'						wend
'					end if
'					rsp.close
'					set rsp=nothing
'				end if
			%>
            <tr class="lmline" bgcolor="#CCCCCC">
              <td class="lmline">
              <a href="admin_area.asp?parent_id=<%=rsw("id")%>"><b><%=rsw("meno")%></b></a><%=rsw("parent_id")%><br><%=rsw("link")%>
              </td>
              <form name="form<%=i%>" method="post" action="admin_area_ord.asp">
                <td class="lmline">
                <input type="hidden" name="id" value="<%=rsw("id")%>">
                <input type="hidden" name="parent_id" value="<%=parent_id%>">
                排序
                <input name="ord" type="text" class="wenbenkuang" id="ord" value="<%=rsw("ord")%>" size="3">
                <input name="Submit2" type="submit" value="go" class="wenbenkuang" >
				<%if parent_id<>"" then%>
                	<a href="###" onClick="showadd(<%=rsw("id")%>)">添加子目录</a>
                	<a href="admin_area_sub.asp?id=<%=rsw("id")%>&parent_id=<%=parent_id%>" target="_blank">更改目录</a>
                <%end if%>
				<%if rsw("closeflag")="1" then%>
                	<input type="button" name="Submit3" value="开通" onClick="window.location='admin_area.asp?id=<%=rsw("id")%>&closeflag=1&parent_id=<%=parent_id%>'">
                <%else%>
                	<input type="button" name="Submit3" value="关闭" style="background:#FF6600" onClick="window.location='admin_area.asp?id=<%=rsw("id")%>&closeflag=0&parent_id=<%=parent_id%>'">
                <%end if%>		       </td>
              </form>
              <td align="center" class="lmline"> <a href="admin_area_del.asp?id=<%=rsw("id")%>&parent_id=<%=parent_id%>" onClick="return confirm('删除这些信息?')">删除</a> </td>
            </tr>
            <tr >
              <td colspan="3" align="center" class="lmline">
              <!--添加 begin-->
              <div class="addkuang" id="addkuang<%=rsw("id")%>" style="display:none">
                  <form action="admin_area_add.asp" method="post" name="form2" onSubmit="return subchk()">
                    <input type="hidden" name="subid" id="subid" value=<%=parent_id%>>
                    <input type="hidden" name="parent_id" id="parent_id" value=<%=rsw("id")%>>栏目名称
                    <input name="meno" type="text" class="wenbenkuang" id="meno" size="20">链接
                    <input name="link" type="text" class="wenbenkuang" id="link" size="20">排序
                    <input name="ord" type="text" id="ord" value="0" size="3">
                    <input name="Submit4" type="submit" class="button01-out" value="添加"> 
                  </form>
              </div>
              <!--添加 end-->
              </td>
              </tr>
            <%
			if parent_id<>"" then
				sqlw1="select * from cate_qx where parent_id="&rsw("id")&" order by ord asc"
				set rsw1=server.CreateObject("adodb.recordset")
				rsw1.open sqlw1,conn,1,1
				if not rsw1.eof or not rsw1.bof then
				do while not rsw1.eof
				
				'if request("code")<>"" then
'					sqlp="select id from cate_qx where code='"&rsw("code")&"'"
'					set rsp=conn.execute(sqlp)
'					if not rsp.eof or not rsp.bof then
'						sqlt="update cate_qx set parent_id="&rsp("id")&" where id="&rsw1("id")
'						conn.execute(sqlt)
'					end if
'					rsp.close
'					set rsp=nothing
'				end if
				'subcode=rsw1("code")
'				sqlp="select id from cate_qx where  subid='"&subcode&"'"
'				response.Write(sqlp&"<br>")
'				set rsp=conn.execute(sqlp)
'				if not rsp.eof or not rsp.bof then
'					while not rsp.eof
'					sqlt="update cate_qx set parent_id="&rsw1("id")&" where id="&rsp(0)
'					conn.execute(sqlt)
'					rsp.movenext
'					wend
'				end if
'				rsp.close
'				set rsp=nothing
			%>
            
            <tr >
              <td class="lmline">
              <form name="formxg<%=i%>" method="post" action="admin_area_add.asp">
              <input type="hidden" name="m" value="1">
              <input type="hidden" name="subid" value="<%=parent_id%>">
			  <input type="hidden" name="id" value=<%=rsw1("id")%>>
              <input type="text" name="meno" value=<%=rsw1("meno")%>>
              &nbsp;&nbsp;<a href="admin_area.asp?parent_id=<%=rsw1("id")%>"><%=rsw1("meno")%></a><%=rsw1("parent_id")%><br>&nbsp;&nbsp;
              <input type="text" name="link" class="textcaidan" style="width:400px" value="<%=rsw1("link")%>">
              <input type="submit" name="button" id="button" value="提交">
              </form>
              </td>
              <form name="form<%=i%>" method="post" action="admin_area_ord.asp">
                <td class="lmline">
                <input type="hidden" name="id" value="<%=rsw1("id")%>">
                <input type="hidden" name="parent_id" value="<%=parent_id%>">
                排序
                <input name="ord" type="text" class="wenbenkuang" id="ord" value="<%=rsw1("ord")%>" size="3">
                <input name="Submit2" type="submit" value="go" class="wenbenkuang" >
				<a href="###" onClick="showadd(<%=rsw1("id")%>)">添加子目录</a>
                <a href="admin_area_sub.asp?id=<%=rsw1("id")%>&parent_id=<%=rsw("id")%>" target="_blank">更改目录</a>		
               
				<%if rsw1("closeflag")="1" then%>
                <input type="button" name="Submit3" value="开通" onClick="window.location='admin_area.asp?id=<%=rsw1("id")%>&closeflag=1&parent_id=<%=parent_id%>'">
                <%else%>
                <input type="button" name="Submit3" value="关闭" style="background:#FF6600" onClick="window.location='admin_area.asp?id=<%=rsw1("id")%>&closeflag=0&parent_id=<%=parent_id%>'">
                <%end if%>		       </td>
              </form>
              <td align="center" class="lmline"> <a href="admin_area_del.asp?id=<%=rsw1("id")%>&code=<%=request("code")%>" onClick="return confirm('删除这些信息?')">删除</a> </td>
            </tr>
            <tr >
              <td colspan="3" align="center" class="lmline">
              <!--添加 begin-->
              <div class="addkuang" id="addkuang<%=rsw1("id")%>" style="display:none">
                  <form action="admin_area_add.asp" method="post" name="form2" onSubmit="return subchk()">
                    <input type="hidden" name="subid" id="subid" value=<%=parent_id%>>
                    <input type="hidden" name="parent_id" id="parent_id" value=<%=rsw1("id")%>>栏目名称
                    <input name="meno" type="text" class="wenbenkuang" id="meno" size="20">链接
                    <input name="link" type="text" class="wenbenkuang" id="link" size="20">排序
                    <input name="ord" type="text" id="ord" value="0" size="3">
                    <input name="Submit4" type="submit" class="button01-out" value="添加"> 
                  </form>
              </div>
              <!--添加 end-->
              </td>
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
			end if
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
<%conn.close
set conn=nothing
%>
