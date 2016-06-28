<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<%
'response.Write("此功能暂停！")
'response.End()
%>
<!-- #include file="../../include/adfsfs!@#.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!--#include file="../../include/pagefunction.asp"-->
<%
sqluser="select realname,ywadminid,xuqianFlag,adminuserid,partid from users where id="&session("personid")
set rsuser=conn.execute(sqluser)
userName=rsuser(0)
ywadminid=rsuser(1)
xuqianFlag=rsuser(2)
partuserid=rsuser(3)
adminuserid=rsuser("adminuserid")
adminmypartid=rsuser("partid")
rsuser.close
set rsuser=nothing
'--------------------
selectcb=request.Form("selectcb")
dostay=request.Form("dostay")
if selectcb<>"" and dostay<>"" then
	'response.Write(request("dopersonid"))
	response.Redirect("sms_assign_save.asp?selectcb="&request.Form("selectcb")&"&dostay="&request.Form("dostay")&"&personid="&request("dopersonid"))
	response.End()
end if
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
<SCRIPT language=javascript src="../DatePicker.js"></SCRIPT>
<link href="../datepicker.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
form
{
	margin:0px;
	padding:0px;
}
a {
	font-size: 12px;
}
body,td,th {
	font-size: 12px;
}
-->
</style>
<script language="javascript">
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
function checkperson(frm,promptText,value)
{
	if (frm.dopersonid.value=="")
	{
		alert("请选择你要分配给谁")
		return false;
	}else{
		postAll(frm,promptText,value)
	}
}
function UTF8UrlEncode(input){    
   
        var output = "";    
   
        var currentChar = '';    
   
        for(var counter = 0; counter < input.length; counter++){    
   
            currentChar = input.charCodeAt(counter);    
   
            if((0 <= currentChar) && (currentChar <= 127))    
   
                output = output + UTF8UrlEncodeChar(currentChar);    
   
            else   
   
                output = output + encodeURIComponent(input.charAt(counter));    
   
        }    
   
        var reslut = output.toUpperCase();    
        return reslut.replace(/%26/, "%2526");     
    } 
	function UTF8UrlEncodeChar(input){    
   
        if(input <= 0x7F) return "%" + input.toString(16);    
   
        var leadByte = 0xFF80;    
   
        var hexString = "";    
   
        var leadByteSpace = 5;    
   
        while(input > (Math.pow(2, leadByteSpace + 1) - 1)){    
   
            hexString = "%" + ((input & 0x3F) | 0x80).toString(16) + hexString;    
   
            leadByte = (leadByte >> 1);    
   
            leadByteSpace--;    
   
            input = input >> 6;    
   
        }    
   
        return ("%" + (input | (leadByte & 0xFF)).toString(16) + hexString).toUpperCase();    
   
	} 
function openservercrm(sid,mobile,com_contactperson,personid)
{
	window.open("http://adminasto.zz91.com/sms_searchcomp/?sid="+sid+"&com_mobile="+UTF8UrlEncode(mobile)+"&com_contactperson="+UTF8UrlEncode(com_contactperson)+"&personid="+UTF8UrlEncode(personid)+"&addtype=sms","_blank","")
}
</script>
</head>

<body>
<%
sql=""
sear="n="
havefp=request("havefp")
if havefp="" or isnull(havefp) then havefp="1"
if request("mobile")<>"" then
	sql=sql&" and mobile like '%"&request("mobile")&"%'"
	sear=sear&"&mobile="&request("mobile")
end if
if request("contact_person")<>"" then
	sql=sql&" and contact_person like '%"&request("contact_person")&"%'"
	sear=sear&"&contact_person="&request("contact_person")
end if
if havefp="0" then
	sql=sql&" and not exists(select sid from sms_assign where sid=sms_subscribe.id)"
	sear=sear&"&havefp="&request("havefp")
end if
if havefp="1" then
	'sql=sql&" and exists(select sid from sms_assign where sid=sms_subscribe.id)"
	sear=sear&"&havefp="&request("havefp")
end if
fromdate=request("fromdate")
if request("fromdate")<>"" then
  sql=sql&" and gmt_modified>='"&request("fromdate")&"'"
  sear=sear&"&fromdate="&request("fromdate")
end if
todate=request("todate")
if request("todate")<>"" then
  sql=sql&" and gmt_modified<='"&cdate(request("todate"))&"'"
  sear=sear&"&todate="&request("todate")
end if
if session("userid")="10" or session("personid")="456" or session("personid")="288" then
else
	sql=sql&" and exists(select sid from sms_assign where sid=sms_subscribe.id and personid="&session("personid")&")"
end if
havedo=request("havedo")
if havedo="1" then
	if session("userid")="10" then
		sql=sql&" and exists(select sid from sms_action where sid=sms_subscribe.id  and doaction=1)"
	else
		sql=sql&" and exists(select sid from sms_action where sid=sms_subscribe.id and personid="&session("personid")&" and doaction=1)"
	end if
else
	sql=sql&" and not exists(select sid from sms_action where sid=sms_subscribe.id and personid="&session("personid")&" and doaction=1)"
end if
sear=sear&"&havedo="&request("havedo")
'response.Write(sql)
%>
<table  width="100%" border="0" cellspacing="1" cellpadding="2" bgcolor="#666666">
<%
	'if cstr(session("personid"))=adminperson or session("userid")="10" then
	%>
    <tr>
      <td bgcolor="#FFFFFF"><a href="?havefp=1">已经分配</a> | <a href="?havefp=0">未分配</a> | <a href="?havedo=1">已经处理的客户</a></td>
    </tr>
    <%
	'end if
	%>
  <form id="form1" name="form1" method="post" action=""><tr>
    <td bgcolor="#FFFFFF">手机
      
        <input type="text" name="mobile" id="mobile" />
联系人
<input type="text" name="contact_person" id="contact_person" /> 
      <input type="hidden" name="havefp" id="havefp" value="<%=request("havefp")%>" />
      <script language=javascript>createDatePicker("fromdate",true,"<%=fromdate%>",false,true,true,true)</script>   <script language=javascript>createDatePicker("todate",true,"<%=todate%>",false,true,true,true)</script>  <input type="submit" name="button" id="button" value="搜索" /></td>
  </tr>
  	
  </form>
</table>
<br />
<%

'--------------
pageNum=15
Set oPage=New clsPageRs2
With oPage
 .sltFld  = "id,mobile,company_name,gmt_modified,contact_person,company_id"
 .FROMTbl = "sms_subscribe"
 .sqlOrder= "order by id desc"
 .sqlWhere= "WHERE not EXISTS (select id from test where id=sms_subscribe.id) "&sql
 .keyFld  = "id"    '不可缺少
 .pageSize= pageNum
 .getConn = conn
 Set Rs  = .pageRs
End With
total=oPage.getTotalPage
oPage.pageNav "?"&sear,""
totalpg=cint(total/pageNum)
if total/pageNum > totalpg then
  totalpg=totalpg+1
end if
%>
<form id="form2" name="form2" method="post" action="list.asp">
<table width="100%" border="0" cellspacing="1" cellpadding="2" bgcolor="#666666">
  <tr>
    <td width="25" bgcolor="#CCCCCC">&nbsp;</td>
    <td bgcolor="#CCCCCC">ID</td>
    <td bgcolor="#CCCCCC">手机</td>
    <td bgcolor="#CCCCCC">联系人</td>
    <td bgcolor="#CCCCCC">公司名称</td>
    <td bgcolor="#CCCCCC">报名时间</td>
    <td bgcolor="#CCCCCC">定制内容</td>
    <td bgcolor="#CCCCCC">是否添加到公司库里</td>
    <td bgcolor="#CCCCCC">&nbsp;</td>
    <td bgcolor="#CCCCCC">&nbsp;</td>
  </tr>
  <%
  if not rs.eof or not rs.bof then
  while not rs.eof 
  %>
  <tr>
    <td bgcolor="#FFFFFF"><input name="cbb" id="cbb<%=rs("id")%>" type="checkbox" value="<%=rs("id")%>"></td>
    <td bgcolor="#FFFFFF"><%=rs("id")%></td>
    <td bgcolor="#FFFFFF"><%=rs("mobile")%></td>
    <td bgcolor="#FFFFFF"><%=rs("contact_person")%></td>
    <td bgcolor="#FFFFFF"><%=rs("company_name")%></td>
    <td bgcolor="#FFFFFF"><%=rs("gmt_modified")%></td>
    <td bgcolor="#FFFFFF">
    <%
	sqls="select * from sms_dingzhi where subscribe_id="&rs("id")&""
	%>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
    	<%
		set rss=conn.execute(sqls)
		if not rss.eof or not rss.bof then
		while not rss.eof
		%>
      <tr>
        <td><%=rss("categoryname")%></td>
        <td><%=rss("areaname")%></td>
        <td><%=rss("timename")%></td>
        <td><%=rss("gmt_modified")%></td>
      </tr>
      	<%
		rss.movenext
		wend
		end if
		rss.close
		set rss=nothing
		%>
    </table></td>
    <td bgcolor="#FFFFFF"><font color="red"><%'=rs("company_id")%>
    <%
	if rs("company_id")<>"0" then
		response.Write("已经在公司库 ")
	
		sqlp="select personid from crm_assignsms where com_id="&rs("company_id")&""
		set rsp=conn.execute(sqlp)
		if not rsp.eof or not rsp.bof then
			personid=rsp(0)
			sqlu="select realname from users where id="&personid
			set rsu=conn.execute(sqlu)
			if not rsu.eof or not rsu.bof then
				response.Write(rsu(0))
			end if
			rsu.close
			set rsu=nothing
		end if
		rsp.close
		set rsp=nothing
	end if
	%>
    </font>
    </td>
    <td bgcolor="#FFFFFF">
    <%
	sqlp="select personid from sms_assign where sid="&rs("id")&""
		set rsp=conn.execute(sqlp)
		if not rsp.eof or not rsp.bof then
			personid=rsp(0)
			sqlu="select realname from users where id="&personid
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
    <td bgcolor="#FFFFFF"><a href="###" onClick="openservercrm('<%=rs("id")%>','<%=rs("mobile")%>','<%=rs("contact_person")%>','<%=session("personid")%>')">添加到本地库</a></td>
  </tr>
  <%
  rs.movenext
  wend
  rs.close
  set rs=nothing
  end if
  %>
</table>

<table width="100%" border="0" cellspacing="1" cellpadding="2" bgcolor="#666666">
  <tr>
    <td bgcolor="#FFFFFF"><table border="0" cellspacing="0" cellpadding="1">
        <tr>
          <td width="50"><input name="cball" type="checkbox" value="" onClick="CheckAll(this.form)" />全选<input type="hidden" name="selectcb" id="selectcb" />
            <input type="hidden" name="dostay" id="dostay" /></td>
          <%
		if (ywadminid<>"" and session("userid")="13") or session("userid")="10" or session("personid")="456" or session("personid")="288" then
		%>
          <td>&nbsp;</td>
          <td>
          	<select name="dopersonid" class="button" id="dopersonid" >
              <option value="" >请选择--</option>
              
			  <% If session("userid")="13" Then %>
			  	<option value="<%=session("personid")%>" >┆&nbsp;&nbsp;┆&nbsp;&nbsp;┿<%=userName%></option>
			  <% End If %>
			  <%
			  if session("Partadmin")="13" or session("personid")="288"  then
			  	 sqlc="select code,meno from cate_adminuser where code in("&ywadminid&") and closeflag=1"
			  else
				  if session("userid")="10" then
				  	 sqlc="select code,meno from cate_adminuser where (code like '13%' or code like '26%') and closeflag=1"
				  else
				  	 sqlc="select code,meno from cate_adminuser where code like '"&session("userid")&"%' and closeflag=1"
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
          	<input type="button" name="button2" id="button2" value="分配给" onclick="checkperson(this.form,'确实要分配吗？','assign')" /></td>
          <%end if%>
          <%
		  if havedo<>"1" then
		  %>
          <td><input type="button" name="button3" id="button3" value="已经处理" onClick="postAll(this.form,'确实已经处理了吗？','havedo')" /></td>
          <%
		  end if
		  %>
          <%
		  if havedo="1" then
		  %>
          <td><input type="button" name="button3" id="button3" value="未处理这些客户" onClick="postAll(this.form,'确实未处理这些客户吗？','havenodo')" /></td>
          <%
		  end if
		  %>
          <td>&nbsp;</td>
        </tr>
    </table></td>
  </tr>
</table>

</form>
</body>
</html>
<%
conn.close
set conn=nothing
%>
