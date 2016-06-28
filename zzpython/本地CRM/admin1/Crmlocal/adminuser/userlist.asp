<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!--#include file="../../../cn/sources/Md5.asp"-->
<!--#include file="../../include/include.asp"-->
<!--#include file="../../include/pagefunction.asp"-->
<%
if session("Partadmin")="0" and session("userid")<>"10" then
	response.Write("没有权限")
	response.End()
end if
b_name=request("b_name")
n=request("n")
page=Request("page")
'''''''''''''''''''''''''''''''''''
sear="b_name="&b_name&"&rn="&request("rn")
if page="" then page=1 
  ''''''''''''''''''''''''''''''''''''''''''
selectcb=request("selectcb")
dostay=request("dostay")
if selectcb<>"" and dostay="delitem" then
	sqldel="delete from users where id in ("& selectcb &")"
	conn.Execute(sqldel)
	response.Redirect("userlist.asp?"&sear&"&page="&page)
end if

dostay=request("dostay")
if selectcb<>"" and dostay="zhuanzheng" then
	sql="update users set zhuanzheng=1 where id in ("& selectcb &")"
	response.Write(sql)
	conn.Execute(sql)
	response.Redirect("userlist.asp?"&sear&"&page="&page)
end if
if selectcb<>"" and dostay="nozhuanzheng" then
	sql="update users set zhuanzheng=0 where id in ("& selectcb &")"
	response.Write(sql)
	conn.Execute(sql)
	response.Redirect("userlist.asp?"&sear&"&page="&page)
end if

  sqluser="select ywadminid,adminuserid,Partuserid from users where id="&session("personid")
  set rsuser=conn.execute(sqluser)
  p_ywadminid=rsuser("ywadminid")
  p_adminuserid=rsuser("adminuserid")
  p_Partuserid=rsuser("Partuserid")
  rsuser.close
  set rsuser=nothing
  touserid=request("touserid")
  if p_ywadminid="23" then touserid="23"
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
         
   }
   function postAll(form,promptText,value)
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
	alert ("选择你要的信息！")
	return false
	}
	else
	{
	  if (confirm(promptText))
	  {
	  form.dostay.value=value
	  form.submit()
	  }
	}
	
  }
    </script>
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
        <td height="23" bgcolor="E7EBDE"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="100" height="23" nowrap background="../../newimages/shale_fill_1.gif"><img src="../../newimages/shale_arrowhead.gif" width="28" height="23" align="absmiddle">用户管理</td>
            <td width="21" background="../../newimages/shale_fill_1.gif"><img src="../../newimages/shale_photo.gif" width="21" height="23"></td>
            <td background="../../newimages/lm_ddd.jpg">&nbsp;</td>
          </tr>
        </table>
          </td>
      </tr>
      <tr>
        <td bgcolor="E7EBDE"><%
		    if request("userid")="" then
				userid=session("userid")
			else
				userid=request("userid")
			end if
			 sqluser="select ywadminid,xuqianFlag from users where id="&session("personid")
			 set rsuser=conn.execute(sqluser)
			 ywadminid=rsuser(0)
			 xuqianFlag=rsuser(1)
			 rsuser.close
			 set rsuser=nothing
			 if ywadminid="" or isnull(ywadminid) then ywadminid=userid
		%>     
          <table width="95%"  border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td height="40" align="center"><input type="button" name="Submit2" value="添加帐号" onClick="window.location='admin_adminuser_add.asp?touserid=<%=touserid%>'">
                &nbsp;&nbsp;
<input type="button" name="Submit" value="已开通的帐号" onClick="window.location='?closeflag=0&userid=<%=userid%>&touserid=<%=touserid%>'">
&nbsp;&nbsp;
<input type="button" name="Submit" value="已关闭的帐号" onClick="window.location='?closeflag=1&userid=<%=userid%>&touserid=<%=touserid%>'"></td>
            </tr>
            <tr>
              <td height="30"><table width="100%" border="0" cellspacing="0" cellpadding="0">
		    <form name="form2" method="get" action="userlist.asp"><tr>
		      <td align="center">
		        姓名
		          <input name="realname" type="text" id="realname" size="10">
		          用户名
		          <input name="uname" type="text" id="uname" size="10">
		          销售电话
<input name="usertel" type="text" id="usertel" size="10">
<select name="userid" id="userid">
                    <option value="">请选择...</option>
				      <%
		  sqlw="select * from cate_adminuser where code in("&left(p_ywadminid,2)&") and code like '__' and closeflag=1 order by id desc"
		  set rsw=server.CreateObject("adodb.recordset")
			rsw.open sqlw,conn,1,1
			if not rsw.eof or not rsw.bof then
			do while not rsw.eof
		   %>
				      <option value="<%=rsw("code")%>">┆&nbsp;&nbsp;┿<%=rsw("meno")%></option>
				      <%
		  sql1="select * from cate_adminuser where code like '"&rsw("code")&"__' and code in("&p_ywadminid&") and closeflag=1  order by id desc"
		  set rs1=server.CreateObject("adodb.recordset")
			rs1.open sql1,conn,1,1
			if not rs1.eof or not rs1.bof then
			do while not rs1.eof
		     %>
				      <option value="<%=rs1("code")%>">┆&nbsp;&nbsp;┆&nbsp;&nbsp;┿<%=rs1("meno")%></option>
				     
				      <%
		 rs1.movenext
		 loop
		 end if
		 rs1.close
		  %>
				      <%
		 rsw.movenext
		 loop
		 end if
		 rsw.close
		  %>
				      </select>
<input type="checkbox" name="rongyi" id="rongyi">
荣誉标志
<input type="submit" name="button" id="button" value="搜索"></td>
		      </tr></form>
		    </table></td>
            </tr>
            <tr>
              <td height="30"><strong  style="color:#F00">注：新人一律需开通新帐号，不可在其他人的帐号上修改</strong></td>
            </tr>
          </table>
          <table width="95%" height="90%" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#333333">
          <tr>
            <td valign="top" bgcolor="#F1F3ED">
            <%
					rn=10
					
				    'set rs=server.CreateObject("adodb.recordset")
					'sear=sear&"&userid="&userid
					sql=""
					if touserid="23" then
						sql=sql&" and userid=23"
					else
						if p_Partuserid<>"1311" then
							sql=sql&" and userid in ("&ywadminid&") "
						else
							sql=sql&" and userid like '13__'"
						end if
					end if
					sear=sear&"&touserid="&touserid&""
					if request("closeflag")="0" then
						sql=sql&" and closeflag=1"
						sear=sear&"&closeflag="&request("closeflag")
					end if
					if request("closeflag")="1" then
						sql=sql&" and closeflag=0"
						sear=sear&"&closeflag="&request("closeflag")
					else
						sql=sql&" and closeflag=1"
						sear=sear&"&closeflag="&request("closeflag")
					end if
					
				   if request("realname")<>"" then
						sql=sql&" and realname like '%"&request("realname")&"%'"
						sear=sear&"&realname="&request("realname")
					end if
					if request("uname")<>"" then
						sql=sql&" and [name] like '%"&request("uname")&"%'"
						sear=sear&"&uname="&request("uname")
					end if
					if request("usertel")<>"" then
						sql=sql&" and usertel like '%"&request("usertel")&"%'"
						sear=sear&"&usertel="&request("usertel")
					end if
					if request("rongyi")<>"" then
						sql=sql&" and rongyi<>''"
						sear=sear&"&rongyi="&request("rongyi")
					end if
					if request.QueryString("userid")<>"" then
						userida=request.QueryString("userid")
						if left(userida,1)="p" then
							useridp=right(userida,len(userida)-1)
							sql=sql&" and partid='"&useridp&"'"
						else
							sql=sql&" and userid='"&request.QueryString("userid")&"'"
						end if
						sear=sear&"&userid="&request.QueryString("userid")
					end if
				   Set oPage=New clsPageRs2
				   With oPage
				     .sltFld  = "id,userid,name,password,closeflag,chatclose,realname,nicheng,zhuanzheng"
					 .FROMTbl = "v_users"
				     .sqlOrder= "order by userid desc,id asc"
				     .sqlWhere= "WHERE  id>0 "&sql
				     .keyFld  = "id"    '不可缺少
				     .pageSize= 15
				     .getConn = conn
				     Set Rs  = .pageRs
				   End With
                   total=oPage.getTotalPage
				   oPage.pageNav "?"&sear,""
				   totalpg=cint(total/15)
				   if total/15 > totalpg then
				   	  totalpg=totalpg+1
				   end if
					%>
            <table width="100%" border="0" cellpadding="2" cellspacing="1" bgcolor="#CCCCCC">
              <form action="userlist.asp" method="post" name=form1>
                
                    
                <tr bgcolor="#D0D8BE">
                  <td width="21"><font color="#000000">&nbsp;</font></td>
                  <td nowrap><font color="#000000">用户名</font></td>
                  <td nowrap>真实姓名</td>
                  <td nowrap>是否转正</td>
                  <td nowrap>客户数</td>
                  <td align="right">帐号开通情况</td>
                  <td align="right"><font color="#000000">操作</font></td>
                </tr>
                <%
				 if not rs.eof  then 
				 i=0                                                                      
				Do While Not rs.EOF
				
				%>
				<%if rs("userid")<>pp then%>
                <tr bgcolor="#FFFFCC" >
                  <td colspan="7"> <%
				call shows("cate_adminuser",rs("userid"))
				%></td>
                  </tr>
				<%end if%>
                <tr bgcolor="#ffffff" >
                  <td><input name="cbb<%=i%>" type="checkbox" value="<%=rs("id")%>">                  </td>
                  <td nowrap>
				  
				  <%=rs("name")%>
				  <%if session("userid")="10" then%>
				  (<%=rs("password")%>)
				  <%end if%></td>
                  <td nowrap><%=rs("realname")%></td>
                  <td align="left" nowrap><%
				  if rs("zhuanzheng")="1" then
				  	response.Write("已转正")
				  end if
				  %></td>
                  <td align="left" nowrap>
                    
                    <%
				  sqlcc="select count(0) from crm_assign where personid="&rs("id")&" and com_id in (select com_id from comp_info)"
				  set rscc=conn.execute(sqlcc)
				  response.Write(rscc(0))
				  rscc.close
				  set rscc=nothing
				  %>
                  </td>
                  <td align="right" nowrap>
                    
                      <%if rs("closeflag")=1 then%>
                    <a href="admin_adminuser_close.asp?id=<%=rs("id")%>&flag=0&userid=<%=userid%>&closeflag=<%=request("closeflag")%>">开通</a>
                    <%else%>
                    <a href="admin_adminuser_close.asp?id=<%=rs("id")%>&flag=1&userid=<%=userid%>&closeflag=<%=request("closeflag")%>"><font color="#FF0000">关闭</font></a>
                    <%end if%>
                  </td>
                  <td align="right" nowrap>
                    
                    
                    
                    <a href="admin_adminuser_mod.asp?id=<%=rs("id")%>&closeflag=<%=request("closeflag")%>&touserid=<%=rs("userid")%>" target="_blank">修改</a>				    </td>
                </tr>
                <%
			pp=rs("userid")
			rs.movenext
			i=i+1
			loop  
			
			else

				  %>
                <tr bgcolor="#FFFFFF">
                  <td colspan="12">暂时无信息！</td>
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
                  <td colspan="13" align="left">
                    <table border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><input name="cball" type="checkbox" value="" onClick="CheckAll(this.form)">
                          全选                        </td>
                        <td align="right"><input type="button" name="button2" id="button2" value="转正" onClick="postAll(this.form,'确实要设置为转正人员吗?','zhuanzheng')">
                          <input type="button" name="button3" id="button3" value="取消转正" onClick="postAll(this.form,'确实要取消转正吗?','nozhuanzheng')">
<input type="hidden" name="dostay">
                            <input type="hidden" name="selectcb">
                            <input type="hidden" name="kind2" value=<%=kind%>>
                            <input type="hidden" name="Statuss2" value="<%=Statuss%>">
                            <input type="hidden" name="orderby2" value="<%=orderby%>">
                            <input type="hidden" name="rn" value="<%=request("rn")%>">
                            <input type="hidden" name="page" value="<%=page%>">
                            <input type="hidden" name="b_name2" value="<%=b_name%>"></td>
                      </tr>
                    </table>                  </td>
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
</html><!-- #include file="../../../conn_end.asp" -->
