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
doflag=request("doflag")
topersonid=request("topersonid")
if selectcb<>"" and dostay<>"" then
	response.Redirect("do.asp?selectcb="&selectcb&"&dostay="&dostay&"&dotype="&dotype&"&doflag="&request("doflag")&"&topersonid="&topersonid)
end if

'用户级别管理权限
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
sql="select id from users where userdengji like '%10010018%' or userdengji like '%10010019%'"
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
  
function shezhizaizhi(obj,field,id)
{
	if (obj.value!="")
	{
		window.open("userliucheng_save.asp?code="+obj.value+"&field="+field+"&id="+id+"&personid=<%=session("personid")%>","personinfoa","")
	}
}

function searchfrm(frm)
{
	frm.outflag.value="0"
	frm.target=""
	frm.submit();
}
</script>
<script language=JavaScript>

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
            <td width="100" height="23" nowrap background="../../newimages/shale_fill_1.gif"><img src="../../newimages/shale_arrowhead.gif" width="28" height="23" align="absmiddle">店铺列表</td>
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
            <form name="form2" method="get" action="list.asp">
              <tr>
                <td height="30">地区<input name="area" type="text" id="area" size="10">店铺名
                
                  <input name="shop_name" type="text" id="shop_name" size="10"> 
                  
                  联系方式<input name="contact" type="text" id="contact" size="10">旺旺号
                  <input name="wangwang_no" type="text" id="wangwang_no" size="10">
                  
                  行业分类
                  <input name="hy_type" type="text" id="hy_type" size="10">
                  
                  店铺网址
                  <input name="weburl" type="text" id="weburl" size="10">
                  <select name="bankcheck" id="bankcheck">
                	  <option value="">自审情况</option>
                      <option value="1">通过</option>
                      <option value="2">未通过</option>
                      <option value="0">未审</option>
                      
                </select>
                <script>selectOption("bankcheck","<%=request.QueryString("bankcheck")%>")</script>
                  
                  <%if session("userid") ="10" or session("userid")="13" or (ywadminid<>"0" and ywadminid<>"" and (not isnull(ywadminid))) then%>
                  
                   所有者
                  
                  <select name="apersonid" id="apersonid">
                  	<option value="" >请选择--</option>
              
			  <% If session("userid")="13" Then %>
			  	<option value="<%=session("personid")%>" >┆&nbsp;&nbsp;┆&nbsp;&nbsp;┿<%=userName%></option>
			  <% End If %>
			  <%
			  if session("Partadmin")="13" then
			  	 sqlc="select code,meno from cate_adminuser where code in("&ywadminid&")"
			  else
				  if session("userid")="10" then
				  	 sqlc="select code,meno from cate_adminuser where code like '13%' or code like '26%'"
				  else
				  	 sqlc="select code,meno from cate_adminuser where code in("&ywadminid&")"
				  end if
			  end if
			  set rsc=conn.execute(sqlc)
			  
			  if not rsc.eof then
			  while not rsc.eof
			  %>
			  <option value="" <%=sle%>>┆&nbsp;&nbsp;┿<%response.Write(rsc("meno"))%></option>
                        <%
						sqlu="select realname,id from users where closeflag=1 and userid="&rsc("code")&""
						set rsu=server.CreateObject("ADODB.recordset")
						rsu.open sqlu,conn,1,1
						if not rsu.eof then
						do while not rsu.eof
						if cstr(request("doperson"))=cstr(rsu("id")) then
						sle="selected"
						else
						sle=""
						end if
						%>
              <option value="<%response.Write(rsu("id"))%>" <%=sle%>>┆&nbsp;&nbsp;┆&nbsp;&nbsp;┿<%response.Write(rsu("realname"))%></option>
              <%
						rsu.movenext
						loop
						end if
						rsu.close()
						set rsu=nothing
				rsc.movenext
				wend
				end if
				rsc.close
				set rsc=nothing
					 %>
                  </select>
                  <script>selectOption("apersonid","<%=request.QueryString("apersonid")%>")</script>
                  <%end if%>
                  </td>
              </tr>
              <tr>
              <td height="30">注册时间 从<script language=javascript>createDatePicker("fromdate",true,"<%=request("fromdate")%>",false,true,true,true)</script>到<script language=javascript>createDatePicker("todate",true,"<%=request("todate")%>",false,true,true,true)</script>电话时间 从<script language=javascript>createDatePicker("fdate",true,"<%=request("fdate")%>",false,true,true,true)</script>到<script language=javascript>createDatePicker("tdate",true,"<%=request("tdate")%>",false,true,true,true)</script>
                <select name="star" id="star">
                	  <option value="">请选择星级</option>
                      <option value="5">5星</option>
                      <option value="4">4星</option>
                      <option value="3">3星</option>
                      <option value="2">2星</option>
                      <option value="1">1星</option>
                </select>
                <script>selectOption("star","<%=request.QueryString("star")%>")</script>
                
                <input type="hidden" name="dotype" id="dotype" value="<%=dotype%>">
                <select name="orderstr" id="orderstr">
                  	<option value="">选择排序</option>
                    <option value="1">下次联系时间</option>
                    <option value="2">注册时间时间</option>
                    <option value="3">联系时间</option>
                    <option value="4">星级</option>
                    <option value="5">销售额</option>
                  </select>
                  <script>selectOption("orderstr","<%=request.QueryString("orderstr")%>")</script>
                </td>
            </tr>
              <tr>
                <td height="30" align="center"><input type="button" name="Submit2" value="   搜索   " onClick="searchfrm(this.form)" style="font-size:16px;font-weight:bold">
                  <%
				  rn=15
				    sql=""
				    set rs=server.CreateObject("adodb.recordset")
					
					
					
					
					if request.QueryString("area")<>"" then
						sql=sql&" and area like '%"&request.QueryString("area")&"%'"
						sear=sear&"&area="&request.QueryString("area")
					end if
					if request.QueryString("shop_name")<>"" then
						sql=sql&" and shop_name like '%"&request.QueryString("shop_name")&"%'"
						sear=sear&"&shop_name="&request.QueryString("shop_name")
					end if
					if request.QueryString("wangwang_no")<>"" then
						sql=sql&" and wangwang_no like '%"&request.QueryString("wangwang_no")&"%'"
						sear=sear&"&wangwang_no="&request.QueryString("wangwang_no")
					end if
					if request.QueryString("hy_type")<>"" then
						sql=sql&" and hy_type like '%"&request.QueryString("hy_type")&"%'"
						sear=sear&"&hy_type="&request.QueryString("hy_type")
					end if
					if request.QueryString("contact")<>"" then
						sql=sql&" and contact like '%"&request.QueryString("contact")&"%'"
						sear=sear&"&contact="&request.QueryString("contact")
					end if
				    
					if request.QueryString("fromdate")<>"" then
						sql=sql&" and regtime>='"&request.QueryString("fromdate")&"'"
						sear=sear&"&fromdate="&request.QueryString("fromdate")
					end if
					if request.QueryString("todate")<>"" then
						sql=sql&" and regtime<='"&request.QueryString("todate")&"'"
						sear=sear&"&todate="&request.QueryString("todate")
					end if
					
					if request.QueryString("fdate")<>"" then
						sql=sql&" and contacttime>='"&request.QueryString("fromdate")&"'"
						sear=sear&"&fromdate="&request.QueryString("fromdate")
					end if
					if request.QueryString("tdate")<>"" then
						sql=sql&" and contacttime<='"&request.QueryString("todate")&"'"
						sear=sear&"&todate="&request.QueryString("todate")
					end if
					if request.QueryString("star")<>"" then
						sql=sql&" and star='"&request.QueryString("star")&"'"
						sear=sear&"&star="&request.QueryString("star")
					end if
					if request.QueryString("bankcheck")<>"" then
						sql=sql&" and bankcheck='"&request.QueryString("bankcheck")&"'"
						sear=sear&"&bankcheck="&request.QueryString("bankcheck")
					end if
					
					
					'if request.QueryString("fdate")<>"" or request.QueryString("tdate")<>"" then
'						sql=sql&" and exists(select cid from ybp_tel where uid=ybp_company.id "
'						if request.QueryString("fdate")<>"" then
'							sql=sql&" and fdate>='"&request.QueryString("fdate")&"'"
'							sear=sear&"&fdate="&request.QueryString("fdate")
'						end if
'						if request.QueryString("tdate")<>"" then
'							sql=sql&" and fdate<='"&request.QueryString("tdate")&"'"
'							sear=sear&"&tdate="&request.QueryString("tdate")
'						end if
'						sql=sql&" )"
'					end if
					
					if request.QueryString("rpersonid")<>"" then
						sql=sql&" and personid="&request.QueryString("rpersonid")&""
						sear=sear&"&rpersonid="&request.QueryString("rpersonid")
					end if
					if request.QueryString("apersonid")<>"" then
						sql=sql&" and exists(select cid from ybp_assign where cid=ybp_company.id and personid="&request.QueryString("apersonid")&")"
						sear=sear&"&apersonid="&request.QueryString("apersonid")
					end if
					'---------新分配客户
					if dotype="nocontact" then
						sql=sql&" and exists(select cid from ybp_assign where cid=ybp_company.id"
						if session("userid")="10" then
						else 
						sql=sql&" and personid="&session("personid")
						end if
						
						sql=sql&")"
						sql=sql&" and not exists(select cid from ybp_tel where cid=ybp_company.id and personid="&session("personid")&")"
					end if
					'---------今天安排联系
					if dotype="today" then
						sql=sql&" and exists(select cid from ybp_assign where cid=ybp_company.id"
						if session("userid")="10" then
						else 
						sql=sql&" and personid="&session("personid")
						end if
						
						sql=sql&")"
						sql=sql&" and interviewTime>'"&date()&"' and interviewTime<'"&date()+1&"'"
					end if
					'---------安排联系未联系(跟丢简历)
					if dotype="contact" then
						sql=sql&" and exists(select cid from ybp_assign where cid=ybp_company.id"
						if session("userid")="10" then
						else 
						sql=sql&" and personid="&session("personid")
						end if
						
						sql=sql&")"
						sql=sql&" and interviewTime<'"&date()&"'"
					end if
					'-----我录入的人员
					if dotype="my" then
						sql=sql&" and exists(select cid from ybp_assign where cid=ybp_company.id and personid="&session("personid")&")"
					end if
					'---------公海
					if dotype="gonghai" then
						sql=sql&" and not exists(select cid from ybp_assign where cid=ybp_company.id"
						sql=sql&")"
					end if
					'******************部门所有客户/start
					if dotype="allbm" then
						sql=sql&" and exists(select cid from ybp_assign where cid=ybp_company.id and personid in (select id from users where userid in ("&ywadminid&") and closeflag=1)"
						
						sql=sql&" )"
						sear=sear&"&dotype="&dotype
					end if
					
					'---------公海(未分配)
					if dotype="gonghai_noassign" then
						sql=sql&" and not exists(select cid from ybp_assign where cid=ybp_company.id"
						sql=sql&") and not exists(select cid from ybp_gonghai where cid=ybp_company.id)"
					end if
					
					'---------公海(未分配)
					if dotype="gonghai_contact" then
						sql=sql&" and not exists(select cid from ybp_assign where cid=ybp_company.id"
						sql=sql&") and contacttime >'1970-1-1'"
					end if
					
					
					sqloutsql=sql
					sear=sear&"&dotype="&dotype
				  %>
                  <input name="outflag" type="hidden" id="outflag" value="0"></td>
              </tr>
            </form>
            
          </table>
  
          <form name="form1" method="post" action="list.asp">
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
				   		sqlorder="regtime desc"
				   end if
				   if request("orderstr")="4" then
				   		sqlorder="star desc"
				   end if
				   if request("orderstr")="3" then
				   		sqlorder="contacttime desc"
				   end if
				   
				   if request("orderstr")="5" then
				   		sqlorder="income desc"
				   end if
			
				   sear=sear&"&orderstr="&request("orderstr")
				   Set oPage=New clsPageRs2
				   With oPage
				     .sltFld  = "*"
					 .FROMTbl = "ybp_company"
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
                  <td width="21">&nbsp;</td>
                  <td nowrap>&nbsp;</td>
                  <td nowrap>星级</td>
                  <td nowrap>店铺网址</td>
                  <td nowrap>地区</td>
                  <td nowrap>店铺名</td>
                  <td nowrap>联系方式</td>
                  <td nowrap>旺旺号</td>
                  <td align="right" nowrap>行业分类</td>
                  <td align="right" nowrap>销售额/元</td>
                  <td align="right" nowrap>注册时间</td>
                  <td align="right" nowrap>下次联系时间</td>
                  
                  <td align="right" nowrap>最后联系时间</td>
                  <td align="right" nowrap>最后跟进人员</td>
                  <td align="right" nowrap>所有者</td>
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
                  <td nowrap><a href="history.asp?cid=<%=rs("id")%>" target="_blank">日志</a></td>
                  <td nowrap><%=rs("star")%></td>
                  <td nowrap><a href="<%=rs("weburl")%>" target="_blank"><%=left(rs("weburl"),15)%>...</a></td>
                  <td nowrap><%=rs("area")%></td>
                  <td nowrap><a href="companyshow.asp?cid=<%=rs("id")%>" target="_blank"><%=rs("shop_name")%></a></td>
                  <td nowrap><%=rs("contact")%></td>
                  <td nowrap><%=rs("wangwang_no")%></td>
                  <td align="right" nowrap><%=rs("hy_type")%></td>
                  <td align="right" nowrap><%=rs("income")%></td>
                  <td align="right" nowrap><%=rs("regtime")%></td>
                  <td align="right" nowrap><%=rs("interviewTime")%></td>
                  <td align="right" nowrap>
                    <%
				  sqlt="select top 1 fdate from ybp_tel where cid="&rs("id")&" order by id desc"
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
				  sqlp="select personid from ybp_assign where cid="&rs("id")&""
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
                  </tr>
                </SPAN>
                <%
			rs.movenext
			i=i+1
			loop  
			
			else

				  %>
                <tr bgcolor="#FFFFFF">
                  <td colspan="20">暂时无信息！</td>
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
                  <td colspan="21" align="right">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td><input name="cball" type="checkbox" value="" onClick="CheckAll(this.form)">
                          全选                        
                            <%if session("userid") ="10" or session("userid")="13" or (ywadminid<>"0" and ywadminid<>"" and (not isnull(ywadminid))) then%>
                            <!--<input type="button" name="button" id="button" value="删除" onClick="return delitem(this.form)">-->
                            <input type="button" name="button4" id="button4" value="分配给" onClick="postAll(this.form,'确实要分配吗?','assignto')">
                            <select name="topersonid" class="button" id="topersonid" >
              <option value="" >请选择--</option>
              
			  <% If session("userid")="13" Then %>
			  	<option value="<%=session("personid")%>" >┆&nbsp;&nbsp;┆&nbsp;&nbsp;┿<%=userName%></option>
			  <% End If %>
			  <%
			  if session("Partadmin")="13" then
			  	 sqlc="select code,meno from cate_adminuser where code in("&ywadminid&") and closeflag=1"
			  else
				  if session("userid")="10" then
				  	 sqlc="select code,meno from cate_adminuser where code like '13%' or code like '26%' and closeflag=1"
				  else
				  	 sqlc="select code,meno from cate_adminuser where code in("&ywadminid&") and closeflag=1"
				  end if
			  end if
			  set rsc=conn.execute(sqlc)
			  
			  if not rsc.eof then
			  while not rsc.eof
			  %>
			  <option value="" <%=sle%>>┆&nbsp;&nbsp;┿<%response.Write(rsc("meno"))%></option>
                        <%
						sqlu="select realname,id from users where closeflag=1 and userid="&rsc("code")&""
						set rsu=server.CreateObject("ADODB.recordset")
						rsu.open sqlu,conn,1,1
						if not rsu.eof then
						do while not rsu.eof
						if cstr(request("doperson"))=cstr(rsu("id")) then
						sle="selected"
						else
						sle=""
						end if
						%>
              <option value="<%response.Write(rsu("id"))%>" <%=sle%>>┆&nbsp;&nbsp;┆&nbsp;&nbsp;┿<%response.Write(rsu("realname"))%></option>
              <%
						rsu.movenext
						loop
						end if
						rsu.close()
						set rsu=nothing
				rsc.movenext
				wend
				end if
				rsc.close
				set rsc=nothing
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
