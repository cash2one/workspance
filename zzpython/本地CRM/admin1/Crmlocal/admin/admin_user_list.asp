<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!--#include file="../../../cn/sources/Md5.asp"-->
<!--#include file="../../include/include.asp"-->
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
if page="" then page=1
  ''''''''''''''''''''''''''''''''''''''''''
 selectcb=request("selectcb")
 dostay=request("dostay")
 if selectcb<>"" and dostay="delitem" then
 	sqldel="delete from users where id in ("& selectcb &")"
	conn.Execute(sqldel)
	response.Redirect("admin_user_list.asp?"&sear&"&page="&page)
 end if
 if request("zddel")<>"" and request.QueryString("personid")<>"" then
 	sqldel="delete from userZD where personid in ("& request.QueryString("personid") &")"
	conn.Execute(sqldel)
	response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
	closeconn()
	response.End()
 end if
 if request("urtel")<>"" and request.QueryString("personid")<>"" then
 	sqldel="update users set usertel='' where id in ("& request.QueryString("personid") &")"
	conn.Execute(sqldel)
	response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
	closeconn()
	response.End()
 end if
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
<link href="../../inc/Style.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
.buuton {
	background-color: #99CC99;
	border-top-width: 3px;
	border-top-style: solid;
	border-top-color: #DAEDDA;
	height: 25px;
}
.buutoff {
	background-color: #CCCCCC;
	border-top-width: 3px;
	border-top-style: solid;
	border-top-color: #f2f2f2;
	height: 25px;
}
-->
</style>
</head>

<body bgcolor="7386A5" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" height="95%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="6">
	</td>
  </tr>
  <tr>
    <td height="100%" valign="top"><table width="98%" height="100%" border="0" align="center" cellpadding="0" cellspacing="0">
      
      <tr>
        <td bgcolor="E7EBDE">
        <%if session("userid")="10" then%>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td align="center"><a href="?pcloseflag=0">关闭的团队</a></td>
          </tr>
        </table>
        <%end if%>   
		  <table width="100%" border="0" cellspacing="0" cellpadding="0">
		    <form name="form2" method="post" action="admin_user_list.asp"><tr>
		      <td align="center">
		        姓名
		          <input name="realname" type="text" id="realname" size="10">
		          用户名
		          <input name="uname" type="text" id="uname" size="10">
		          销售电话
<input name="usertel" type="text" id="usertel" size="10">
<input type="checkbox" name="rongyi" id="rongyi">
荣誉标志
<input type="checkbox" name="userqx" id="userqx">
主管<input type="checkbox" name="recordflag" id="recordflag">
录音<input type="submit" name="button" id="button" value="搜索"></td>
		      </tr></form>
		    </table>
		  <table width="95%" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#000000">
            <tr>
            <td align="center" class="buutoff"><a href="?userid=1305&pcloseflag=">团队权限用户</a></td>
			<%
			if request("userid")="" then
				'userid="14"
			else
				userid=request.QueryString("userid")
			end if
			sqlm=""
			if request.QueryString("pcloseflag")="0" then
				sqlm=sqlm&" and  closeflag=0"
			else
				sqlm=sqlm&" and  closeflag=1"
			end if
			if session("userid")="10" then
				
			else
				sqlm=sqlm&"and  code in ("&ywadminid&")"
			end if
			sear=sear&"&pcloseflag="&request.QueryString("pcloseflag")
			sqlu="select code,meno from cate_adminuser where 1=1 "&sqlm&"   order by id desc"
			'response.Write(sqlu)
			set rsu=server.CreateObject("adodb.recordset")
			rsu.open sqlu,conn,1,1
			if not rsu.eof then
			do while not rsu.eof 
				if rsu("code")=userid then
					buclass="buuton"
				else
					buclass="buutoff"
				end if
			%>
              <td align="center" class="<%=buclass%>"><a href="?userid=<%=rsu("code")%>&pcloseflag=<%=request.QueryString("pcloseflag")%>"><%=rsu("meno")%></a></td>
			<%
			rsu.movenext
			loop
			end if
			rsu.close
			set rsu=nothing

eDate=request("editDate")

if eDate="" then eDate=date()
			%>
              
            </tr>
          </table>       
          <table width="90%"  border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td height="30" align="center"><input type="button" name="Submit" value="开通的帐号" onClick="window.location='?closeflag=0&userid=<%=userid%>'">
&nbsp;&nbsp;
<input type="button" name="Submit" value="关闭的帐号" onClick="window.location='?closeflag=1&userid=<%=userid%>'">
<!--<input type="button" name="Submit" value="当天未改密码" onClick="window.location='?editPwd=0&editDate='+document.getElementById('editDate').value">
<input type="button" name="Submit" value="当天已改密码" onClick="window.location='?editPwd=1&editDate='+document.getElementById('editDate').value">
<input type="text" name="editDate" id="editDate" value="<%=eDate%>" />--></td>
            </tr>
          </table>
          <table width="95%" height="90%" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#333333">
          <tr>
            <td valign="top" bgcolor="#F1F3ED"><table width="100%" border="0" cellpadding="2" cellspacing="1" bgcolor="#CCCCCC">
              <form action="admin_user_list.asp" method="post" name=form1>
                <%
				    sql=""
					'response.Write(request.QueryString("userid"))
				    set rs=server.CreateObject("adodb.recordset")
					sear=sear&"&userid="&userid
					sql="SELECT name,userid,id,password,realname,nicheng,usertel,chatclose,closeflag,recordflag FROM v_users where id>0"
					if request.QueryString("userid")<>"" then
						sql=sql&" and userid="&userid&""
					end if
					if request("button")="" then
						if request("closeflag")="0" then
							sql=sql&" and closeflag=1"
							sear=sear&"&closeflag="&request("closeflag")
						end if
					end if
					if request("closeflag")="1" then
						sql=sql&" and closeflag=0"
						sear=sear&"&closeflag="&request("closeflag")
					end if
					if request("editPwd")="0" then
						sql=sql&" and (editTime<'"&eDate&"' or editTime is null)"
						sear=sear&"&editPwd="&request("editPwd")&"&editDate="&eDate
					end if
					if request("editPwd")="1" then
						sql=sql&" and editTime>='"&eDate&"'"
						sear=sear&"&editPwd="&request("editPwd")&"&editDate="&eDate
					end if
					if request("realname")<>"" then
						sql=sql&" and realname like '%"&request("realname")&"%'"
						sear=sear&"&realname="&request("realname")
					end if
					if request("uname")<>"" then
						sql=sql&" and [name] like '%"&request("uname")&"%'"
						sear=sear&"&uname="&request("uname")
					end if
					if request("zdname")<>"" then
						sql=sql&" and zdname like '%"&request("zdname")&"%'"
						sear=sear&"&zdname="&request("zdname")
					end if
					if request("usertel")<>"" then
						sql=sql&" and usertel like '%"&request("usertel")&"%'"
						sear=sear&"&usertel="&request("usertel")
					end if
					if request("rongyi")<>"" then
						sql=sql&" and rongyi<>''"
						sear=sear&"&rongyi="&request("rongyi")
					end if
					if request("userqx")<>"" then
						sql=sql&" and userqx=1"
						sear=sear&"&userqx="&request("userqx")
					end if
					if request("recordflag")<>"" then
						sql=sql&" and recordflag=1"
						sear=sear&"&recordflag="&request("recordflag")
					end if
					if session("userid")="10" then
				
					else
						sql=sql&" and  userid in ("&ywadminid&")"
					end if
					'response.Write(sql)
					sql=sql&" order by userid desc,id asc"
					rs.open sql,conn,1,1
					%>
                    <!--#include file="../../include/pagetop_notop.asp"-->
                <tr bgcolor="#D0D8BE">
                  <td width="21"><font color="#000000">&nbsp;</font></td>
                  <td nowrap><font color="#000000">用户名</font></td>
                  <td align="right">IDC客户数</td>
                  <td align="right">VAP客户数</td>
                  <td align="right">电话</td>
                  <td align="right"><font color="#000000">操作</font></td>
                </tr>
                <%
				if not rs.eof  then 
				 i=0                                                                      
				Do While Not rs.EOF and RowCount>0
					
				%>
				<%if rs("userid")<>pp then%>
                <tr bgcolor="#FFFFCC">
                  <td colspan="6">
                  <%
				  call shows("cate_adminuser",rs("userid"))
				  %>
                  </td>
                  </tr>
				 <%end if%>
                <tr bgcolor="#ffffff" >
                  <td><input name="cbb<%=i%>" type="checkbox" value="<%=rs("id")%>">
                  </td>
                  <td nowrap>
				  
				  <font color="#999999"><%=rs("name")%>
				  <%if session("userid")="10" then%>
				  (<%=rs("password")%>)
				  <%end if%></font>
                  <br>
                  (<%=rs("realname")%>-
				  <%
				  nicheng=rs("nicheng")
				  if nicheng<>"" and not isnull(nicheng) then
				  	 nicheng=lencontrol(rs("nicheng"),20)
				  else
				  	 nicheng=""
				  end if
				  response.Write(nicheng)
				  %>)<font color="#999999"><br>最近登录
                  <%
				  sqle="select top 1 loginTime from crm_adminlogin where personid="&rs("id")&" order by logintime desc"
				  set rse=conn.execute(sqle)
				  if not rse.eof or not rse.bof then
				  	 response.Write(rse(0))
				  end if
				  rse.close
				  set rse=nothing
				  %></font>
				  </td>
                  <td align="right" nowrap><a href="../crm_allcomp_list.asp?dotype=allall&doperson=<%=rs("id")%>" target="_blank"><%
				  sqlcc="select count(0) from crm_assign where personid="&rs("id")&" "
				  set rscc=conn.execute(sqlcc)
				  response.Write(rscc(0))
				  rscc.close
				  set rscc=nothing
				  %>
                  掉公海</a>
                  </td>
                  <td align="right" nowrap>
				  <a href="vapdrop.asp?personid=<%=rs("id")%>" target="_blank"><%
				  sqlcc="select count(0) from crm_assignvap where personid="&rs("id")&" "
				  set rscc=conn.execute(sqlcc)
				  response.Write(rscc(0))
				  rscc.close
				  set rscc=nothing
				  %>
                  掉公海</a>
                  
                  </td>
                  <td align="right" nowrap><%if session("userid")="10" then%><a href="?personid=<%=rs("id")%>&urtel=1">删除</a><%end if%></td>
                  <td align="right" nowrap>
                    <%if rs("closeflag")="1" then%>
                    <a href="admin_adminuser_close.asp?id=<%=rs("id")%>&flag=0&userid=<%=userid%>&closeflag=<%=request("closeflag")%>">开通帐号</a>
                    <%else%>
                    <a href="admin_adminuser_close.asp?id=<%=rs("id")%>&flag=1&userid=<%=userid%>&closeflag=<%=request("closeflag")%>"><font color="#FF0000">关闭帐号</font></a>
                    <%end if%>
                    <%if session("userid")="10" then%>
                    <a href="admin_user_class_qx.asp?uname=<%=rs("id")%>&closeflag=<%=request("closeflag")%>" target="_blank">权限设置</a> 
                    <%end if%>
                    <a href="admin_adminuser_mod.asp?id=<%=rs("id")%>&closeflag=<%=request("closeflag")%>" target="_blank">修改</a>
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
                  <td colspan="11">暂时无信息！</td>
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
                  <td colspan="12" align="right">
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
                  <td><!-- #include file="../../include/page.asp" -->
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
</html>
<%
conn.close
set conn=nothing
%>
<!-- #include file="../../../conn_end.asp" -->
