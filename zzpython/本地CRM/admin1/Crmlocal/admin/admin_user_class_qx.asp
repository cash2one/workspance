<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!--#include file="../../include/include.asp"-->
<%
b_name=request("b_name")
n=request("n")
page=Request("page")

'''''''''''''''''''''''''''''''''''
sear="b_name="&b_name&"&rn="&request("rn")
if not isnumeric(page) then page=1
if page="" then page=1 
  ''''''''''''''''''''''''''''''''''''''''''
selectcb=request("selectcb")
dostay=request("dostay")
if selectcb<>"" and dostay="delitem" then
	sqldel="delete from users where id in ("& selectcb &")"
	conn.Execute(sqldel)
	response.Redirect("admin_user_list.asp?"&sear&"&page="&page)
end if
sql="select qx,uname from user_qx where uname="&request("uname")&""
set rs=conn.execute(sql)

if not rs.eof or not rs.bof then
while not rs.eof 
	qx=rs(0)
	if qx<>"" and not isnull(qx) then
	arrqx=split(qx,",")
	myqx=""
	for i=0 to ubound(arrqx)
		sqlp="select id from cate_qx where code='"&trim(arrqx(i))&"'"
		set rsp=conn.execute(sqlp)
		if not rsp.eof or not rsp.bof then
			myqx=myqx&rsp(0)&","
		end if
		rsp.close
		set rsp=nothing
	next
	myqx=left(myqx,len(myqx)-1)
	'sqlp="update user_qx set myqx='"&myqx&"' where uname='"&rs("uname")&"'"
	'conn.execute(sqlp)
	end if
rs.movenext
wend
end if
rs.close
set rs=nothing
'--------------------------复制销售部权限
 if request("xiaosouqx")<>""  then
     sqlq="select qx from user_qx where uname="&request("xiaosouqx")&""
	 set rsq=conn.execute(sqlq)
	 if not rsq.eof then
	 	allqx=rsq("qx")
	 end if
	 rsq.close
	 set rsq=nothing
	 
	 sqlt="select * from user_qx where uname="&request("uname")&""
	 set rst=server.CreateObject("adodb.recordset")
	 rst.open sqlt,conn,1,3
	 if rst.eof then
		 rst.addnew()
		 rst("uname")=request("uname")
	 end if
		 rst("qx")=allqx
		 rst.update()
		 rst.close
		 set rst=nothing
	 response.Write("<script>alert('设置成功！')</script>")
 end if
 qx=""
 sql="select myqx from user_qx where uname="&request("uname")&""
 set rs=server.CreateObject("adodb.recordset")
 rs.open sql,conn,1,1
 if not rs.eof then
 	qx=rs("myqx")
 end if
 rs.close()
 set rs=nothing
 sql="select realname from users where id="&request("uname")
 set rs=conn.execute(sql)
 if not rs.eof or not rs.bof then
 	realname=rs(0)
 end if
 rs.close
 set rs=nothing
 '--------------------------------
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>权限管理</title>
<link href="../../css.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/javascript" src="../../include/JSDateFunction.js"></script>
<script language="javascript" src="../../include/calendar.js"></script>
<script type="text/javascript" src="../../include/b.js"></script>
<link href="../../inc/Style.css" rel="stylesheet" type="text/css">
<script language=JavaScript>
	function showsub(id)
	{
		var obj=document.getElementById("sub"+id);
		var obj1=document.getElementById("liston"+id);
		
		if (obj.style.display=="none")
		{
			obj.style.display="";
			obj1.className="liston"
		}else
		{
			obj.style.display="none";
			obj1.className="listoff"
		}
	}
</script>
<style type="text/css">
<!--
.liston {
	background-image: url(../inc/DTree/Img/nolines_minus.gif);
	background-repeat: no-repeat;
	padding-left: 18px;
	border-bottom-width: 1px;
	border-bottom-style: solid;
	border-bottom-color: #990;
	line-height: 20px;
	cursor: hand;
}
.listoff {
	background-image: url(../inc/DTree/Img/nolines_plus.gif);
	background-repeat: no-repeat;
	padding-left: 18px;
	line-height: 20px;
	border-bottom-width: 1px;
	border-bottom-style: solid;
	border-bottom-color: #990;
	cursor: hand;
}
form
{
	padding:0px;
	margin:0px;
}
-->
</style>
</head>

<body bgcolor="7386A5" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="23" background="../../newimages/mid_dd.gif" bgcolor="#CED7E7"><img src="../../newimages/mid_left.gif" width="23" height="28"></td>
    <td background="../../newimages/mid_dd.gif"><input name="Submit" type="button" class="button01-out" value="保存" onClick="window.form1.submit()">
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
        <td valign="top" bgcolor="E7EBDE"><table width="600" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td>&nbsp;</td>
            </tr>
            <tr>
              <td><%
			  response.Write(realname&" 的权限设置")
			  %></td>
              </tr>
            <!--<form name="form2" method="post" action="admin_user_class_qx.asp"><tr>
              <td>
              <input type="radio" name="xiaosouqx" id="radio" value="777">
                vap普通销售
              <input type="radio" name="xiaosouqx" id="radio" value="332">
                普通销售 
                  <input type="radio" name="xiaosouqx" id="radio2" value="734"> 
                  主管 
                  <input type="radio" name="xiaosouqx" id="radio3" value="735"> 
                  区长 
                  <input type="radio" name="xiaosouqx" id="radio4" value="736"> 
                销售总监
                
                  <input type="submit" name="button" id="button" value="提交"><input type="hidden" name="uname" value="<%=request("uname")%>">
                </td>
            </tr>-->
            <tr>
              <td>&nbsp;</td>
            </tr></form>
          </table><form action="admin_user_class_qx_save.asp" method="post" name="form1">
            <table width="600" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#333333">
            <tr>
              <td valign="top" bgcolor="#F1F3ED"><table width="600" height="90%" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#333333">
                <tr>
                  <td valign="top" bgcolor="#F1F3ED"><input type="hidden" name="uname" value="<%=request("uname")%>">
                    <input name="userid" type="hidden" value="<%=request("userid")%>">
                    <%
				sqlw="select id,meno,code from cate_qx where parent_id=0 and closeflag=1 order by ord asc"
				set rsw=server.CreateObject("adodb.recordset")
				rsw.open sqlw,conn,1,1
				if not rsw.eof or not rsw.bof then
				do while not rsw.eof
					a=0
					if qx<>"" then
						myqx=split(qx,",")
						for jj=0 to ubound(myqx)
							if cstr(rsw("id"))=trim(cstr(myqx(jj))) then
								a=1
							end if
						next
					end if
				%>
                    <table width="100%"  border="0" align="center" cellpadding="1" cellspacing="0" bgcolor="#666666" >
                      <tr bgcolor="#C1CCAA">
                        <td bgcolor="#E2E8D9" width="20"><div class="listoff" ><input name="qx" type="checkbox" value="<%=rsw("id")%>" <%if a=1 then response.Write("checked")%>></div></td><td bgcolor="#E2E8D9"><div class="listoff" id="liston<%=rsw("id")%>" onClick="showsub(<%=rsw("id")%>)"><font color="#000000"><%=rsw("meno")%></font></div></td>
                      </tr>
                    </table>
                    <table width="100%"  border="0" align="center" cellpadding="1" cellspacing="0" bgcolor="#666666" id="sub<%=rsw("id")%>" style="display:none; margin-left:25px;">
                      <%
						sqlx="select id,code,meno,subid from cate_qx where parent_id="&rsw("id")&" and closeflag=1 order by ord asc"
						set rsx=server.CreateObject("adodb.recordset")
						rsx.open sqlx,conn,1,1
						if not rsx.eof or not rsx.bof then
						do while not rsx.eof
                %>
                      <tr>
                        <td bgcolor="#f2f2f2"><%
					a=0
					'response.Write(qx)
					if qx<>"" then
						myqx=split(qx,",")
						for jj=0 to ubound(myqx)
							if cstr(rsx("id"))=trim(cstr(myqx(jj))) then
								a=1
							end if
						next
					end if
					%>
                          <%if a=1 then%>
                          <input name="qx" type="checkbox" value="<%=rsx("id")%>" checked>
                          &nbsp;<%=rsx("meno")%>
                          <%else%>
                          <input name="qx" type="checkbox" value="<%=rsx("id")%>">
                          &nbsp;<%=rsx("meno")%>
                          <%end if%></td>
                      </tr>
                        <%
						sqlx1="select id,code,meno from cate_qx where parent_id="&rsx("id")&" and closeflag=1 order by ord asc"
						set rsx1=server.CreateObject("adodb.recordset")
						rsx1.open sqlx1,conn,1,1
						if not rsx1.eof or not rsx1.bof then
						do while not rsx1.eof
               			%>
                      <tr>
                        <td bgcolor="#f2f2f2">&nbsp;&nbsp;&nbsp;&nbsp;
                          <%
						b=0
						if qx<>"" then
							myqx=split(qx,",",-1,1)
							for jj=0 to ubound(myqx)
								if cstr(rsx1("id"))=trim(cstr(myqx(jj))) then
									b=1
								end if
							next
						end if
						%>
                          <%if b=1 then%>
                          <input name="qx" type="checkbox" value="<%=rsx1("id")%>" checked>
                          &nbsp;<%=rsx1("meno")%>
                          <%else%>
                          <input name="qx" type="checkbox" value="<%=rsx1("id")%>">
                          &nbsp;<%=rsx1("meno")%>
                          <%end if%></td>
                      </tr>
                      	<%
						sqlx2="select id,code,meno from cate_qx where parent_id="&rsx1("id")&" and closeflag=1 order by ord asc"
						set rsx2=server.CreateObject("adodb.recordset")
						rsx2.open sqlx2,conn,1,1
						if not rsx2.eof or not rsx2.bof then
						do while not rsx2.eof
               			%>
                        <tr>
                        <td bgcolor="#f2f2f2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                          <%
						b=0
						if qx<>"" then
							myqx=split(qx,",",-1,1)
							for jj=0 to ubound(myqx)
								if cstr(rsx2("id"))=trim(cstr(myqx(jj))) then
									b=1
								end if
							next
						end if
						%>
                          <%if b=1 then%>
                          <input name="qx" type="checkbox" value="<%=rsx2("id")%>" checked>
                          &nbsp;<%=rsx2("meno")%>
                          <%else%>
                          <input name="qx" type="checkbox" value="<%=rsx2("id")%>">
                          &nbsp;<%=rsx2("meno")%>
                          <%end if%></td>
                      </tr>
                      <%
					  		rsx2.movenext
						  loop
						  end if
						  rsx2.close
						  set rsx2=nothing
						  
						  
					 	  rsx1.movenext
						  loop
						  end if
						  rsx1.close
						  set rsx1=nothing
					  rsx.movenext
					  loop
					  end if
					  rsx.close
					  set rsx=nothing
					  %>
                    </table>
                    <%
				  rsw.movenext
				  loop
				  end if
				  rsw.close
				  set rsw=nothing
				%></td>
                </tr>
              </table></td>
            </tr>
            </table>
            <br>
          </form>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td>&nbsp;</td>
          </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
  
</table>
</body>
</html>
<%conn.close
set conn=nothing
%>
