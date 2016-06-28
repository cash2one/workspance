<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!--#include file="../../../cn/sources/Md5.asp"-->
<!--#include file="../../include/include.asp"-->
<!--#include file="../../include/pagefunction.asp"-->
<!--#include file="../inc.asp"-->
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
if request.QueryString("personid")<>"" and request.QueryString("lizhi")<>"" then
	sql="update users set renshi_zaizhi='"&request.QueryString("lizhi")&"' where id="&request.QueryString("personid")&""
	conn.execute(sql)
	if request.QueryString("lizhi")="1203" then
		sql="update users set renshi_on=0,chatflag=0,chatclose=0 where id="&request.QueryString("personid")&""
		conn.execute(sql)
	end if
	response.Write("<script>alert('修改成功')</script>")
	response.End()
end if
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>无标题文档</title>
<link href="../../css.css" rel="stylesheet" type="text/css">
<SCRIPT language=JavaScript src="../../main.js"></SCRIPT>
<script language="javascript" src="../../include/calendar.js"></script>
<script type="text/javascript" src="../../include/b.js"></script>
<SCRIPT language=javascript src="../../../cn/sources/pop.js"></SCRIPT>
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
function shezhizaizhi(obj,personid)
{
	if (obj.value!="")
	{
		window.open("userlist.asp?lizhi="+obj.value+"&personid="+personid,"personinfoa","")
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
            <td width="100" height="23" nowrap background="../../newimages/shale_fill_1.gif"><img src="../../newimages/shale_arrowhead.gif" width="28" height="23" align="absmiddle">员工列表</td>
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
			 'sqluser="select ywadminid,xuqianFlag from users where id="&session("personid")
'			 set rsuser=conn.execute(sqluser)
'			 ywadminid=rsuser(0)
'			 xuqianFlag=rsuser(1)
'			 rsuser.close
'			 set rsuser=nothing
'			 if ywadminid="" or isnull(ywadminid) then ywadminid=userid
		%>     
          <table width="95%"  border="0" align="center" cellpadding="0" cellspacing="0">
            <form name="form2" method="get" action="userlist.asp">
              <tr>
                <td height="30">员工姓名
                
                  <input name="realname" type="text" id="realname" size="10"> 
                  员工编号
                  <input name="renshi_code" type="text" id="renshi_code" size="10">
                  性别
                  <input name="sex" type="radio" id="radio3" value="1" <%if request.QueryString("sex")="1" then response.Write("checked")%>>
                  全部
                  <input name="sex" type="radio" id="radio" value="男" <%if request.QueryString("sex")="男" then response.Write("checked")%>>
                    男
                      <input type="radio" name="sex" id="radio2" value="女" <%if request.QueryString("sex")="女" then response.Write("checked")%>>
                      女
                  <select name="userid" id="userid">
                    <option value="">请选择部门</option>
                    <%
		  sqlw="select * from cate_adminuser where code like '__' and closeflag=1 order by id desc"
		  set rsw=server.CreateObject("adodb.recordset")
			rsw.open sqlw,conn,1,1
			if not rsw.eof or not rsw.bof then
			do while not rsw.eof
		   %>
                    <option value="<%=rsw("code")%>">┆&nbsp;&nbsp;┿<%=rsw("meno")%></option>
                    <%
		  sql1="select * from cate_adminuser where code like '"&rsw("code")&"__'  and closeflag=1 order by id desc"
		  set rs1=server.CreateObject("adodb.recordset")
			rs1.open sql1,conn,1,1
			if not rs1.eof or not rs1.bof then
			do while not rs1.eof
		     %>
                    <option value="<%=rs1("code")%>">┆&nbsp;&nbsp;┆&nbsp;&nbsp;┿<%=rs1("meno")%></option>
                    <%
		  sql2="select * from cate_adminuser where code like '"&rs1("code")&"__'  and closeflag=1 order by id desc"
		  set rs2=server.CreateObject("adodb.recordset")
			rs2.open sql2,conn,1,1
			if not rs2.eof or not rs2.bof then
			do while not rs2.eof
		     %>
                    <option value="<%=rs2("code")%>">┆&nbsp;&nbsp;┆&nbsp;&nbsp;┆&nbsp;&nbsp;┿<%=rs2("meno")%></option>
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
                  </select>
                  <script>selectOption("userid","<%=request.QueryString("userid")%>")</script>
                  </td>
              </tr>
              <tr>
              <td height="30">                  在职情况<%=cateMeno_public(conn,"category","renshi_zaizhi",request.QueryString("renshi_zaizhi"),"","12")%>管理职级<%=cateMeno_public(conn,"category","renshi_guanli",request.QueryString("renshi_guanli"),"","11")%>试用期职级<%=cateMeno_public(conn,"category","renshi_shiyong",request.QueryString("renshi_shiyong"),"","10")%>
                  <input name="onstaton" type="checkbox" id="onstaton" value="1" <%if request.QueryString("onstaton")="1" then response.Write("checked")%>>
                  在职
                  <input type="submit" name="Submit2" value="搜索"></td>
            </tr></form>
            <tr>
              <td height="30"><strong  style="color:#F00">注：新人一律需开通新帐号，不可在其他人的帐号上修改</strong></td>
            </tr>
          </table>
          <table width="95%" height="90%" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#333333">
          <tr>
            <td valign="top" bgcolor="#F1F3ED">
            <%
					rn=10
				    sql=""
				    set rs=server.CreateObject("adodb.recordset")
					
					
					if request.QueryString("userid")<>"" then
						sql=sql&" and userid="&request.QueryString("userid")&""
						sear=sear&"&userid="&request.QueryString("userid")
					end if
					if request.QueryString("sex")<>"" and request.QueryString("sex")<>"1" then
						sql=sql&" and sex='"&request.QueryString("sex")&"'"
						sear=sear&"&sex="&request.QueryString("sex")
					end if
					if request.QueryString("renshi_code")<>"" then
						sql=sql&" and renshi_code='"&request.QueryString("renshi_code")&"'"
						sear=sear&"&renshi_code="&request.QueryString("renshi_code")
					end if
					if request.QueryString("realname")<>"" then
						sql=sql&" and realname like '%"&request.QueryString("realname")&"%'"
						sear=sear&"&realname="&request.QueryString("realname")
					end if
					
				    if request.QueryString("onstaton")="1" then
						sql=sql&" and id not in (select id from users where renshi_zaizhi='1203')"
						sear=sear&"&onstaton="&request.QueryString("onstaton")
					end if
					if request.QueryString("renshi_zaizhi")<>"" then
						sql=sql&" and renshi_zaizhi='"&request.QueryString("renshi_zaizhi")&"'"
						sear=sear&"&renshi_zaizhi="&request.QueryString("renshi_zaizhi")
					end if
					if request.QueryString("renshi_guanli")<>"" then
						sql=sql&" and renshi_guanli='"&request.QueryString("renshi_guanli")&"'"
						sear=sear&"&renshi_guanli="&request.QueryString("renshi_guanli")
					end if
					if request.QueryString("renshi_shiyong")<>"" then
						sql=sql&" and renshi_shiyong='"&request.QueryString("renshi_shiyong")&"'"
						sear=sear&"&renshi_shiyong="&request.QueryString("renshi_shiyong")
					end if
				   'response.Write(sql)
				   Set oPage=New clsPageRs2
				   With oPage
				     .sltFld  = "id,userid,name,renshi_code,sex,renshi_zaizhi,renshi_ruzi,renshi_guanli,renshi_shiyong,birthday,realname"
					 .FROMTbl = "users"
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
                <tr bgcolor="#D0D8BE">
                  <td width="21"><font color="#000000">&nbsp;</font></td>
                  <td nowrap>员工编号</td>
                  <td nowrap>姓名</td>
                  <td nowrap>性别</td>
                  <td nowrap>在职情况</td>
                  <td align="right" nowrap>管理职级</td>
                  <td align="right">试用期职级</td>
                  <td align="right">生日</td>
                  <td align="right">入职时间</td>
                  <td align="right"><font color="#000000">操作</font></td>
                </tr>
                <%
				 if not rs.eof  then 
				 i=0                                                                      
				Do While Not rs.EOF
				if i mod 2=1 then
					bgcol="#ffffff"
				else
					bgcol="#ffffff"
				end if
				%>
				<%if rs("userid")<>pp then%>
                <tr bgcolor="<%=bgcol%>" >
                  <td colspan="10"> <%
				call shows("cate_adminuser",rs("userid"))
				%></td>
                  </tr>
				<%end if%>
                <tr bgcolor="<%=bgcol%>" >
                  <td><input name="cbb<%=i%>" type="checkbox" value="<%=rs("id")%>">                  </td>
                  <td nowrap><%=rs("renshi_code")%></td>
                  <td nowrap><%=rs("realname")%></td>
                  <td nowrap><%=rs("sex")%></td>
                  <td nowrap><%=cateMeno_public(conn,"category","renshi_zaizhi"&rs("id"),rs("renshi_zaizhi"),"onchange=shezhizaizhi(this,"&rs("id")&")","12")%></td>
                  <td align="right" nowrap><%=shows_("category",rs("renshi_guanli"))%></td>
                  <td align="right" nowrap><%=shows_("category",rs("renshi_shiyong"))%></td>
                  <td align="right" nowrap><%=rs("birthday")%></td>
                  <td align="right" nowrap><%=rs("renshi_ruzi")%></td>
                  <td align="right" nowrap>
                  
				 
				 
				 人事变更 | <a href="useredit.asp?id=<%=rs("id")%>" target="_blank">修改</a>
                 </td>
                </tr>
                <%
			pp=rs("userid")
			rs.movenext
			i=i+1
			loop  
			
			else

				  %>
                <tr bgcolor="#FFFFFF">
                  <td colspan="15">暂时无信息！</td>
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
                  <td colspan="16" align="right">
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
                            <input type="hidden" name="b_name2" value="<%=b_name%>"></td>
                      </tr>
                    </table>                  </td>
                </tr>
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
        </table>
		<iframe id="personinfoa" name="personinfoa"  style="HEIGHT: 0px; VISIBILITY: inherit; WIDTH: 0px; Z-INDEX: 2" frameborder="0" src=""></iframe>
		</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="6">	</td>
  </tr>
</table>
</body>
</html><!-- #include file="../../../conn_end.asp" -->
