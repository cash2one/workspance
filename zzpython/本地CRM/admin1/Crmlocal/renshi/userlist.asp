<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!--#include file="../../include/include.asp"-->
<!--#include file="../../include/pagefunction.asp"-->
<!--#include file="../inc.asp"-->
<%
b_name=request("b_name")
n=request("n")
page=Request("page")
'''''''''''''''''''''''''''''''''''
sear="b_name="&b_name&"&rn="&request("rn")
if page="" then page=1 
  ''''''''''''''''''''''''''''''''''''''''''
selectcb=request("selectcb")
dostay=request("dostay")
dotype=request("dotype")
if selectcb<>"" and dostay="delitem" then
	sqldel="delete from renshi_user where id in ("& selectcb &")"
	conn.Execute(sqldel)
	response.Redirect("userlist.asp?"&sear&"&page="&page)
end if
function getsucess()
	response.Write("<script>alert('设置成功！');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
	response.End()
end function
 '放到我的客户库
  if selectcb<>"" and dostay="tomy" then
 	smsID=split(selectcb,",")
	for i=0 to ubound(smsID)
		sql="select uid from renshi_assign where uid = "& smsID(i) &""
		set rsp=conn.execute(sql)
		if rsp.eof and rsp.bof then
			conn.Execute("insert into renshi_assign(uid,personid) values("&trim(smsID(i))&","&session("personid")&")")
		end if
		rsp.close
		set rsp=nothing
	next
	getsucess()
 end if
 '--------分配客户
 if selectcb<>"" and dostay="assignto" then
 	smsID=split(selectcb,",")
	for i=0 to ubound(smsID)
		sql="select uid from renshi_assign where uid = "& smsID(i) &""
		set rsp=conn.execute(sql)
		if rsp.eof and rsp.bof then
			conn.Execute("insert into renshi_assign(uid,personid) values("&trim(smsID(i))&","&request("topersonid")&")")
		else
			conn.execute("upate renshi_assign set personid="&request("topersonid")&" where uid="&trim(smsID(i))&"")
		end if
		rsp.close
		set rsp=nothing
	next
	getsucess()
 end if
  '放到公海
  if selectcb<>"" and dostay="gonghai" then
	sql="delete from renshi_assign where uid in ("&selectcb&")"
	conn.execute(sql)
	getsucess()
 end if
 sqluser="select realname,ywadminid,xuqianFlag,adminuserid from users where id="&session("personid")
 set rsuser=conn.execute(sqluser)
 userName=rsuser(0)
 ywadminid=rsuser(1)
 xuqianFlag=rsuser(2)
 partuserid=rsuser(3)
 adminuserid=rsuser("adminuserid")
 rsuser.close
 set rsuser=nothing

				  idlist="0"
				    sql="select id from users where userdengji like '%10010008%' or userdengji like '%10010009%'"
				    set rsr=conn.execute(sql)
					if not rsr.eof or not rsr.bof then
					while not rsr.eof
						idlist=idlist&rsr(0)&","
					rsr.movenext
					wend
					end if
					rsr.close
					set rsr=nothing
					if len(idlist)>0 then
						idlist=left(idlist,len(idlist)-1)
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
<SCRIPT language=javascript src="../DatePicker.js"></SCRIPT>
<link href="../datepicker.css" rel="stylesheet" type="text/css">
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
function shezhizaizhi(obj,field,id)
{
	if (obj.value!="")
	{
		window.open("userliucheng_save.asp?code="+obj.value+"&field="+field+"&id="+id+"&personid=<%=session("personid")%>","personinfoa","")
	}
}
function dataout(frm)
{
	frm.outflag.value="1"
	frm.target="personinfoa"
	frm.submit();
	
}
function searchfrm(frm)
{
	frm.outflag.value="0"
	frm.target=""
	frm.submit();
}
</script>
<script language=JavaScript>
function subchk(frm)
{
	if (frm.realname.value=="")
	{
		alert("请输入姓名！");
		frm.realname.focus();
		return false;
	}
       if  (frm.uname.value=="")       
		{   
		 alert("请输入名字拼音(用户名)!\n");
		 frm.uname.focus();
		 return  false;                 
		} 
		
}
function isnum(f)
{
	if (isNaN(f.value))
	{
		f.value="";
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
form
{
	padding:0px;
	margin:0px;
}
.kuan
{
	border: 1px solid #CCC;
	background-color: #FFF;
	padding: 2px;
}
.hl {
background-color: #ededec;
}
.sl {
background-color: #e0edf7;

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
            <td width="100" height="23" nowrap background="../../newimages/shale_fill_1.gif"><img src="../../newimages/shale_arrowhead.gif" width="28" height="23" align="absmiddle">应聘人员列表</td>
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
                <td height="30">手机<input name="mobile" type="text" id="mobile" size="10">姓名
                
                  <input name="username" type="text" id="username" size="10"> 
                  
                  邮箱
                  <input name="email" type="text" id="email" size="10">
                  <select name="sex" id="sex">
                  	<option value="">选择性别</option>
                    <option value="男">男</option>
                    <option value="女">女</option>
                  </select>
                  
                      
                  
                  <script>selectOption("sex","<%=request.QueryString("sex")%>")</script>
                  应聘岗位<%=cateMeno_public(conn,"category","station",request.QueryString("station"),"","15")%>第二应聘岗<%=cateMeno_public(conn,"category","station2",request.QueryString("station2"),"","15")%>最高学历<%=cateMeno_public(conn,"category","education",request.QueryString("education"),"","16")%>工作年限<input name="worklonger" type="text" id="worklonger" size="5" onKeyUp="isnum(this)" value="<%=request.QueryString("worklonger")%>">年简历来源
                  <input name="laiyuan" type="text" id="laiyuan" size="10">
                  录入人
                  <select name="rpersonid" id="rpersonid">
                  	<option value="">......</option>
                  	<%
					sqlr="select id,realname from users where userid like '23%' and closeflag=1"
					set rsr=conn.execute(sqlr)
					if not rsr.eof or not rsr.bof then
					while not rsr.eof
					%>
                    <option value="<%=rsr(0)%>"><%=rsr(1)%></option>
                    <%
					rsr.movenext
					wend
					end if
					rsr.close
					set rsr=nothing
					%>
                  </select>
                  <script>selectOption("rpersonid","<%=request.QueryString("rpersonid")%>")</script>
                  所有者
                  
                  <select name="apersonid" id="apersonid">
                  	<option value="">......</option>
                  	<%
					'userid like '23%'
					sqlr="select id,realname from users where id in ("&idlist&") and closeflag=1"
					set rsr=conn.execute(sqlr)
					if not rsr.eof or not rsr.bof then
					while not rsr.eof
					%>
                    <option value="<%=rsr(0)%>"><%=rsr(1)%></option>
                    <%
					rsr.movenext
					wend
					end if
					rsr.close
					set rsr=nothing
					%>
                  </select>
                  <script>selectOption("apersonid","<%=request.QueryString("apersonid")%>")</script>
                  </td>
              </tr>
              <tr>
              <td height="30">面谈时间 从<script language=javascript>createDatePicker("fromdate",true,"<%=request("fromdate")%>",false,true,true,true)</script>到<script language=javascript>createDatePicker("todate",true,"<%=request("todate")%>",false,true,true,true)</script>记录时间 从<script language=javascript>createDatePicker("fdate",true,"<%=request("fdate")%>",false,true,true,true)</script>到<script language=javascript>createDatePicker("tdate",true,"<%=request("tdate")%>",false,true,true,true)</script>
                <select name="star" id="star">
                	  <option value="">请选择星级</option>
                      <option value="5">5星</option>
                      <option value="4">4星</option>
                      <option value="3">3星</option>
                      <option value="2">2星</option>
                      <option value="1">1星</option>
                </select>
                <script>selectOption("star","<%=request.QueryString("star")%>")</script>
                邀约记录<%=cateMeno_public(conn,"category","jl1",request.QueryString("jl1"),"","17")%>初试记录<%=cateMeno_public(conn,"category","jl2",request.QueryString("jl2"),"","18")%>复试记录<%=cateMeno_public(conn,"category","jl3",request.QueryString("jl3"),"","19")%>报到记录<%=cateMeno_public(conn,"category","jl4",request.QueryString("jl4"),"","20")%><br>
过程结束<%=cateMeno_public(conn,"category","jl5",request.QueryString("jl5"),"","21")%>联系状态<%=cateMeno_public(conn,"category","contactstat",request.QueryString("contactstat"),"","22")%>
                <input type="hidden" name="dotype" id="dotype" value="<%=dotype%>">
                <select name="orderstr" id="orderstr">
                  	<option value="">选择排序</option>
                    <option value="1">面谈时间</option>
                    <option value="2">添加时间</option>
                  </select>
                  <script>selectOption("orderstr","<%=request.QueryString("orderstr")%>")</script>
                </td>
            </tr>
              <tr>
                <td height="30"><input type="button" name="Submit2" value="搜索" onClick="searchfrm(this.form)">
                  <input type="button" name="button5" id="button5" value="导出数据" onClick="dataout(this.form)">
                  
<%
				  rn=15
				    sql=""
				    set rs=server.CreateObject("adodb.recordset")
					
					
					if request.QueryString("station")<>"" then
						sql=sql&" and station="&request.QueryString("station")&""
						sear=sear&"&station="&request.QueryString("station")
					end if
					if request.QueryString("station2")<>"" then
						sql=sql&" and station2="&request.QueryString("station2")&""
						sear=sear&"&station2="&request.QueryString("station2")
					end if
					if request.QueryString("sex")<>"" and request.QueryString("sex")<>"1" then
						sql=sql&" and sex='"&request.QueryString("sex")&"'"
						sear=sear&"&sex="&request.QueryString("sex")
					end if
					if request.QueryString("education")<>"" then
						sql=sql&" and education='"&request.QueryString("education")&"'"
						sear=sear&"&education="&request.QueryString("education")
					end if
					if request.QueryString("star")<>"" then
						sql=sql&" and star='"&request.QueryString("star")&"'"
						sear=sear&"&star="&request.QueryString("star")
					end if
					if request.QueryString("jl1")<>"" then
						sql=sql&" and jl1='"&request.QueryString("jl1")&"'"
						sear=sear&"&jl1="&request.QueryString("jl1")
					end if
					if request.QueryString("jl2")<>"" then
						sql=sql&" and jl2='"&request.QueryString("jl2")&"'"
						sear=sear&"&jl2="&request.QueryString("jl2")
					end if
					if request.QueryString("jl3")<>"" then
						sql=sql&" and jl3='"&request.QueryString("jl3")&"'"
						sear=sear&"&jl3="&request.QueryString("jl3")
					end if
					if request.QueryString("jl4")<>"" then
						sql=sql&" and jl4='"&request.QueryString("jl4")&"'"
						sear=sear&"&jl4="&request.QueryString("jl4")
					end if
					if request.QueryString("jl5")<>"" then
						sql=sql&" and jl5='"&request.QueryString("jl5")&"'"
						sear=sear&"&jl5="&request.QueryString("jl5")
					end if
					if request.QueryString("contactstat")<>"" then
						sql=sql&" and contactstat='"&request.QueryString("contactstat")&"'"
						sear=sear&"&contactstat="&request.QueryString("contactstat")
					end if
					if request.QueryString("worklonger")<>"" then
						sql=sql&" and worklonger='"&request.QueryString("worklonger")&"'"
						sear=sear&"&worklonger="&request.QueryString("worklonger")
					end if
					
					if request.QueryString("username")<>"" then
						sql=sql&" and username like '%"&request.QueryString("username")&"%'"
						sear=sear&"&username="&request.QueryString("username")
					end if
					if request.QueryString("mobile")<>"" then
						sql=sql&" and mobile like '%"&request.QueryString("mobile")&"%'"
						sear=sear&"&mobile="&request.QueryString("mobile")
					end if
					if request.QueryString("email")<>"" then
						sql=sql&" and email like '%"&request.QueryString("email")&"%'"
						sear=sear&"&email="&request.QueryString("email")
					end if
					if request.QueryString("laiyuan")<>"" then
						sql=sql&" and laiyuan like '%"&request.QueryString("laiyuan")&"%'"
						sear=sear&"&laiyuan="&request.QueryString("laiyuan")
					end if
				    
					if request.QueryString("fromdate")<>"" then
						sql=sql&" and interviewTime>='"&request.QueryString("fromdate")&"'"
						sear=sear&"&fromdate="&request.QueryString("fromdate")
					end if
					if request.QueryString("todate")<>"" then
						sql=sql&" and interviewTime<='"&request.QueryString("todate")&"'"
						sear=sear&"&todate="&request.QueryString("todate")
					end if
					
					if request.QueryString("fdate")<>"" or request.QueryString("tdate")<>"" then
						sql=sql&" and exists(select uid from renshi_history where uid=renshi_user.id "
						if request.QueryString("fdate")<>"" then
							sql=sql&" and fdate>='"&request.QueryString("fdate")&"'"
							sear=sear&"&fdate="&request.QueryString("fdate")
						end if
						if request.QueryString("tdate")<>"" then
							sql=sql&" and fdate<='"&request.QueryString("tdate")&"'"
							sear=sear&"&tdate="&request.QueryString("tdate")
						end if
						sql=sql&" )"
					end if
					
					if request.QueryString("rpersonid")<>"" then
						sql=sql&" and personid="&request.QueryString("rpersonid")&""
						sear=sear&"&rpersonid="&request.QueryString("rpersonid")
					end if
					if request.QueryString("apersonid")<>"" then
						sql=sql&" and exists(select uid from renshi_assign where uid=renshi_user.id and personid="&request.QueryString("apersonid")&")"
						sear=sear&"&apersonid="&request.QueryString("apersonid")
					end if
					
					'---------今天安排联系
					if dotype="today" then
						sql=sql&" and exists(select uid from renshi_assign where uid=renshi_user.id"
						if session("userid")="10" then
						else 
						sql=sql&" and personid="&session("personid")
						end if
						
						sql=sql&")"
						sql=sql&" and interviewTime>'"&date()&"' and interviewTime<'"&date()+1&"'"
					end if
					'---------安排联系未联系(跟丢简历)
					if dotype="contact" then
						sql=sql&" and exists(select uid from renshi_assign where uid=renshi_user.id"
						if session("userid")="10" then
						else 
						sql=sql&" and personid="&session("personid")
						end if
						
						sql=sql&")"
						sql=sql&" and interviewTime<'"&date()&"'"
					end if
					'-----我录入的人员
					if dotype="my" then
						sql=sql&" and exists(select uid from renshi_assign where uid=renshi_user.id and personid="&session("personid")&")"
					end if
					'---------公海
					if dotype="gonghai" then
						sql=sql&" and not exists(select uid from renshi_assign where uid=renshi_user.id"
						sql=sql&")"
					end if
					'---------录用人员
					if dotype="luyong" then
						sql=sql&" and jl3=1901"
					end if
					sqloutsql=sql
					sear=sear&"&dotype="&dotype
				  %>
                  <input name="outflag" type="hidden" id="outflag" value="0"></td>
              </tr>
            </form>
            
          </table>
          <form name="formo" method="post" action="userdownload_main.asp" target="_blank">
          	 <input type="hidden" name="sqldata" id="sqldata" value="<%=sqloutsql%>">
          </form>
          <%
		  if request.QueryString("outflag")="1" then
		  %>
          <script>
		  	 function outsep()
			 {
				 document.getElementById("formo").submit();
			 }
			 outsep();
		  </script>
          <%
		  response.End()
		  end if
		  %>
          <form name="form1" method="post" action="userlist.asp">
            <table width="95%" height="90%" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#333333">
          <tr>
            <td valign="top" bgcolor="#F1F3ED">
            <%
					
				   if session("userid")="10" then
				   	  response.Write(sql)
				   end if
				   sqlorder="id desc"
				   if request("orderstr")="1" then
				   		sqlorder="interviewTime desc"
				   end if
				   if request("orderstr")="2" then
				   		sqlorder="id desc"
				   end if
				   if request("orderstr1")="1" then
				   		sqlorder="interviewTime desc"
				   end if
				   sear=sear&"&orderstr="&request("orderstr")
				   Set oPage=New clsPageRs2
				   With oPage
				     .sltFld  = "*"
					 .FROMTbl = "renshi_user"
				     .sqlOrder= "order by "&sqlorder
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
                  <td nowrap>星级</td>
                  <td nowrap>手机</td>
                  <td nowrap>姓名</td>
                  <td nowrap>性别</td>
                  <td nowrap>应聘岗位</td>
                  <td align="right" nowrap>最高学历</td>
                  <td align="right" nowrap><a href="?<%=sear%>&orderstr1=1">面谈时间</a></td>
                  <td align="right" nowrap>联系状态</td>
                  
                  <td align="right" nowrap>邀约记录</td>
                  <td align="right" nowrap>初试记录</td>
                  <td align="right" nowrap>复试记录</td>
                  <td align="right" nowrap>报到记录</td>
                  <td align="right" nowrap>简历来源</td>
                  <td align="right" nowrap>过程结束</td>
                  
                  <td align="right" nowrap>其他联系方式</td>
                  <td align="right" nowrap>电子信箱</td>
                  
                  <td align="right" nowrap><a href="?<%=sear%>&orderstr1=2">时间记录</a></td>
                  <td align="right" nowrap>最后跟进人员</td>
                  <td align="right" nowrap>所有者</td>
                  
                  <td align="right" nowrap><font color="#000000">操作</font></td>
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
				<SPAN onMouseOver="DoHL()" onMouseOut="DoLL()" onClick="DoSL();">
                <tr bgcolor="<%=bgcol%>"  >
                  <td><input name="cbb" id="cbb<%=rs("id")%>" type="checkbox" value="<%=rs("id")%>">                  </td>
                  <td nowrap><%=rs("star")%></td>
                  <td nowrap><%=rs("mobile")%></td>
                  <td nowrap><a href="usershow.asp?uid=<%=rs("id")%>" target="_blank"><%=rs("username")%></a></td>
                  <td nowrap><%=rs("sex")%></td>
                  <td nowrap><%=showMeno(conn,"category",rs("station"))%><%'=cateMeno_public(conn,"category","station"&rs("id"),rs("station"),"onchange=shezhizaizhi(this,'station',"&rs("id")&")","15")%></td>
                  <td align="right" nowrap><%=showMeno(conn,"category",rs("education"))%><%'=cateMeno_public(conn,"category","education"&rs("id"),rs("education"),"onchange=shezhizaizhi(this,'education',"&rs("id")&")","16")%></td>
                  <td align="right" nowrap>
                  
                  <iframe class="kuan" id="personinfoa<%=rs("id")%>" name="personinfoa<%=rs("id")%>"  style="position:absolute;display:none;HEIGHT: 30px;  WIDTH: 200px; Z-INDEX: 2; " scrolling="no" frameborder="0" src="mianshitime.asp?id=<%=rs("id")%>&interviewTime=<%=rs("interviewTime")%>"></iframe>
                  <span id="interviewTimetime<%=rs("id")%>"><%=rs("interviewTime")%></span></td>
                  <td align="right" nowrap><%=cateMeno_public(conn,"category","contactstat"&rs("id"),rs("contactstat"),"onchange=shezhizaizhi(this,'contactstat',"&rs("id")&")","22")%></td>
                  <td align="right" nowrap><%=cateMeno_public(conn,"category","jl1"&rs("id"),rs("jl1"),"onchange=shezhizaizhi(this,'jl1',"&rs("id")&")","17")%></td>
                  <td align="right" nowrap><%=cateMeno_public(conn,"category","jl2"&rs("id"),rs("jl2"),"onchange=shezhizaizhi(this,'jl2',"&rs("id")&")","18")%></td>
                  <td align="right" nowrap><%=cateMeno_public(conn,"category","jl3"&rs("id"),rs("jl3"),"onchange=shezhizaizhi(this,'jl3',"&rs("id")&")","19")%></td>
                  <td align="right" nowrap><%=cateMeno_public(conn,"category","jl4"&rs("id"),rs("jl4"),"onchange=shezhizaizhi(this,'jl4',"&rs("id")&")","20")%></td>
                  <td align="right" nowrap><%=rs("laiyuan")%><br>
                  <!--<a href="user_historybz.asp?uid=<%=rs("id")%>" target="_blank">添加说明</a>--></td>
                  <td align="right" nowrap><%=cateMeno_public(conn,"category","jl5"&rs("id"),rs("jl5"),"onchange=shezhizaizhi(this,'jl5',"&rs("id")&")","21")%></td>
                  <td align="right" nowrap><%=rs("othercontact")%></td>
                  <td align="right" nowrap><%=rs("email")%></td>
                  
                  <td align="right" nowrap>
                  <%
				  sqlt="select top 1 fdate from renshi_history where uid="&rs("id")&" and left(code,2)<>'15' and left(code,2)<>'16' order by id desc"
				  set rst=conn.execute(sqlt)
				  if not rst.eof or not rst.bof then
				  	response.Write(rst(0))
				  end if
				  rst.close
				  set rst=nothing
				  %>
                  </td>
                  <td align="right" nowrap>
                  <%
				  if rs("personid")<>"" then
				  sqlu="select realname from users where id="&rs("personid")&""
				  set rsu=conn.execute(sqlu)
				  if not rsu.eof or not rsu.bof then
				  	response.Write(rsu(0))
				  end if
				  rsu.close
				  set rsu=nothing
				  end if
				  %>
                  </td>
                  <td align="right" nowrap>
                  <%
				  sqlp="select personid from renshi_assign where uid="&rs("id")&""
				  set rsp=conn.execute(sqlp)
				  if not rsp.eof or not rsp.bof then
				  
					  sqlu="select realname from users where id="&rsp("personid")&""
					  set rsu=conn.execute(sqlu)
					  if not rsu.eof or not rsu.bof then
						response.Write(rsu(0))
					  end if
					  rsu.close
					  set rsu=nothing
				  end if
				  rsp.close
				  set rsp=nothing
				  %>
                  </td>
                  
                  <td align="right" nowrap>
                  
				 
				 
				 <a href="user_history.asp?uid=<%=rs("id")%>" target="_blank">过程记录</a> | <%if trim(resumeUrl)<>"" then%><a href="../file/download.asp?FileName=<%=rs("resumeUrl")%>">简历下载</a> | <%end if%><a href="useredit.asp?uid=<%=rs("id")%>" target="_blank">修改</a>
                 </td>
                </tr>
                </SPAN>
                <%
			rs.movenext
			i=i+1
			loop  
			
			else

				  %>
                <tr bgcolor="#FFFFFF">
                  <td colspan="26">暂时无信息！</td>
                </tr>
                <%end if
				rs.close
				set rs=nothing
				%>
                <script language="JavaScript">
<!--
function showmiantime(id)
{
	var obj=document.getElementById("personinfoa"+id)
	if (obj.style.display=="")
	{
		obj.style.display="none"
	}else{
		obj.style.display=""
	}
}
function CheckAll(form)
{
	for (var i=0;i<form.elements.length;i++)
	{
	var e = form.elements[i];
	if (e.name.substr(0,3)=='cbb')
	   e.checked = form.cball.checked;
	}
}
///////////////////////////////////
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
                  <td colspan="27" align="right">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><input name="cball" type="checkbox" value="" onClick="CheckAll(this.form)">
                          全选                        
                            <%if len(partuserid)>=2 then%>
                            <input type="button" name="button" id="button" value="删除" onClick="return delitem(this.form)">
                            <input type="button" name="button4" id="button4" value="分配给" onClick="postAll(this.form,'确实要分配吗?','assignto')">
                            <select name="topersonid" id="topersonid">
                            	<option value="">请选择...</option>
								<%
								sql="select realname,id from users where id in ("&idlist&") and closeflag=1"
								set rs=conn.execute(sql)
								if not rs.eof then
								while not rs.eof
								%>
                              	<option value="<%=rs("id")%>"><%=rs("realname")%></option>
                                <%
								rs.movenext
								wend
								end if
								rs.close
								set rs=nothing
								%>
                            </select>
<%end if%>
                            <input type="button" name="button2" id="button2" value="放到我的库" onClick="postAll(this.form,'确实要放到我的库吗?','tomy')">
                            <input type="button" name="button3" id="button3" value="放入公海"  onClick="postAll(this.form,'确实要放入公海吗?','gonghai')"></td>
                        <td align="right"><input type="hidden" name="dostay">
                            <input type="hidden" name="selectcb">
                            </td>
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
        </table></form>
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
