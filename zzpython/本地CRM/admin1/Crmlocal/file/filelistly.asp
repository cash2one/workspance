<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<%fromcs=request("fromcs")%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<%if fromcs="" then%>
<!-- #include file="../../localjumptolog.asp" -->
<%end if%>
<!--#include file="../../include/pagefunction.asp"-->
<%
luyinurl="/admin1/crmlocal/recordService/play.asp?mdname="
luyindownloadurl="http://192.168.2.27/freeiris2/cpanel/record/"
sear="n="
if request.QueryString("del")="1" and request.QueryString("id")<>"" then
	sql="delete from crm_UploadFile where id="&request.QueryString("id")
	conn.execute(sql)
end if
function getlytel(lytel)
	sqltl="select id from record_list where called like '%"&lytel&"%'"
	if mytel<>"" then
		sqltl=sqltl&" and caller='"&mytel&"' "
	end if
	sqltl=sqltl&" and startime>='"&date&"'"
	
	set rstl=conn.execute(sqltl)
	if not rstl.eof or not rstl.bof then
		getlytel=rstl("id")
	else
		getlytel=""
	end if
	rstl.close
	set rstl=nothing
end function
'----------
if session("personid")<>"" then 
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
end if
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />

<title>录音列表</title>
<SCRIPT language=javascript src="../DatePicker.js"></SCRIPT>
<link href="../datepicker.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body,td,th {
	font-size: 12px;
}
-->
</style>
<script>
function selectOption(menuname,value)
{
    var menu = document.getElementById(menuname);
	if (menu)
	{
	for(var i=0;i<=menu.options.length;i++){
		if(menu.options[i].value==value)
		{
			menu.options[i].selected = true;
			break;
		}
	}
	}
}
</script>
</head>

<body>
<%
myly=request("myly")
if myly="" then
%>
<form id="form1" name="form1" method="post" action="filelistly.asp"><table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>主叫号码
      
        <input name="CalledId" type="text" id="CalledId" size="15" />
        被叫号码
        <input name="Dtmf" type="text" id="Dtmf" size="15" />
        <select name="ptype" id="ptype">
        	<option value=""></option>
          <option value="2">呼入</option>
          <option value="1">呼出</option>
        </select>
        <select name="doperson" class="button" id="doperson" >
      <option value="" >请选择--</option>
			 
			  <%
			  if fromcs="" then
				  if ywadminid<>"" and not isnull(ywadminid)  then
					  sqlc="select code,meno from cate_adminuser where code in("&ywadminid&")"
				  else
					  if session("userid")="10" then
						 sqlc="select code,meno from cate_adminuser where code like '13%'"
					  else
						 sqlc="select code,meno from cate_adminuser where code like '"&session("userid")&"%'"
					  end if
				  end if
			  else
			  	 sqlc="select code,meno from cate_adminuser where code like '24%'"
			  end if
			  set rsc=conn.execute(sqlc)
			  if not rsc.eof then
			  while not rsc.eof
			  %>
			  <option value="" <%=sle%>>┆&nbsp;&nbsp;┿<%response.Write(rsc("meno"))%></option>
                        <%
						sqlu="select realname,id,usertel from users where chat_userid="&rsc("code")&""
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
          <option value="<%response.Write(rsu("usertel"))%>" <%=sle%>>┆&nbsp;&nbsp;┆&nbsp;&nbsp;┿<%response.Write(rsu("realname"))%></option>
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
<br />
<script>selectOption("doperson","<%=request("doperson")%>")</script>
      电话时间
<script language=javascript>createDatePicker("fromdate",true,"<%=request.Form("fromdate")%>",false,false,false,true)</script>
到<script language=javascript>createDatePicker("todate",true,"<%=request.Form("todate")%>",false,false,false,true)</script>
   时长从
      <input name="fromsc" type="text" id="fromsc" size="5"  value="<%=request("fromsc")%>"/>
      分钟到
      <input name="tosc" type="text" id="tosc" size="5" value="<%=request("tosc")%>"/>
      <input type="submit" name="button" id="button" value="提交" />
      <input type="hidden" name="fromcs" id="fromcs" value="<%=fromcs%>" />
      <input type="hidden" name="myly" id="myly" value="<%=myly%>" />
      </td>
  </tr>
  
</table></form>
<%
else
%>
<form id="form1" name="form1" method="post" action="filelistly.asp"><table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>对方号码
<input name="Dtmf" type="text" id="Dtmf" size="15" />
        <select name="ptype" id="ptype">
        	<option value=""></option>
          <option value="2">呼入</option>
          <option value="1">呼出</option>
        </select>
        <br />
<script>selectOption("doperson","<%=request("doperson")%>")</script>
      电话时间
<script language=javascript>createDatePicker("fromdate",true,"<%=request.Form("fromdate")%>",false,false,false,true)</script>
到<script language=javascript>createDatePicker("todate",true,"<%=request.Form("todate")%>",false,false,false,true)</script>
   时长从
      <input name="fromsc" type="text" id="fromsc" size="5"  value="<%=request("fromsc")%>"/>
      分钟到
      <input name="tosc" type="text" id="tosc" size="5" value="<%=request("tosc")%>"/>
      <input type="submit" name="button" id="button" value="提交" />
      <input type="hidden" name="fromcs" id="fromcs" value="<%=fromcs%>" />
      <input type="hidden" name="myly" id="myly" value="<%=myly%>" /></td>
  </tr>
  
</table></form>
<%
end if
%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
    <%
	sql=""
	
	if myly<>"" then
		sql=sql&" and caller like '%"&mytel&"%'"
		sear=sear&"&myly="&myly
	end if
	if request("doperson")<>"" then
		sql=sql&" and caller='"&request("doperson")&"'"
		sear=sear&"&doperson="&request("doperson")
	end if
	if request("CalledId")<>"" then
		sql=sql&" and caller like '%"&request("CalledId")&"%'"
		sear=sear&"&CalledId="&request("CalledId")
	end if
	if request("Dtmf")<>"" then
		sql=sql&" and called like '%"&request("Dtmf")&"%'"
		sear=sear&"&Dtmf="&request("Dtmf")
	end if
	
	if request("fromdate")<>"" then
		sql=sql&" and startime>='"&request("fromdate")&"'"
		sear=sear&"&fromdate="&request("fromdate")
	end if
	'if request("todate")<>"" then
'		sql=sql&" and beginTime<='"&request("todate")&"'"
'		sear=sear&"&todate="&request("todate")
'	end if
	if request("fromsc")<>"" then
		sql=sql&" and answeredtime>='"&request("fromsc")*60&"'"
		sear=sear&"&fromsc="&request("fromsc")
	end if
	if request("tosc")<>"" then
		sql=sql&" and answeredtime<='"&request("tosc")*60&"'"
		sear=sear&"&tosc="&request("tosc")
	end if
	if request("ptype")<>"" then
		sql=sql&" and type='"&request("ptype")&"'"
		sear=sear&"&ptype="&request("ptype")
	end if
	sear=sear&"&fromcs="&fromcs
    Set oPage=New clsPageRs2
	With oPage
		.sltFld  = "id,uniqueid,startime,caller,accountcode,answeredtime,called,monitorfile,type,ml"
		.FROMTbl = "record_list"
		.sqlOrder= "order by startime desc"
		.sqlWhere= " where 1=1"&sql
		.keyFld  = "id"    '不可缺少
		.pageSize= 10
		.getConn = conn
		Set Rs  = .pageRs
	End With
	total=oPage.getTotalPage
	oPage.pageNav "?"&sear,""
	totalpg=int(total/10)
  %></td>
  </tr>
</table>
<table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#666666">
  <tr>
    <td bgcolor="#f2f2f2">序号</td>
    <td bgcolor="#f2f2f2">主叫号码</td>
    
    <td bgcolor="#f2f2f2">被叫号码</td>
    <td bgcolor="#f2f2f2">电话类型</td>
    <td bgcolor="#f2f2f2">开始电话时间</td>
    <td bgcolor="#f2f2f2">结束电话时间</td>
    <td bgcolor="#f2f2f2">通话时长(秒)</td>
    <td bgcolor="#f2f2f2">&nbsp;</td>
    <td bgcolor="#f2f2f2">&nbsp;</td>
  </tr>
 <%
while not rs.eof 
if rs("ml")="null" or isnull(rs("ml")) then
ml="1/"
else
ml=rs("ml")
end if
%>
  <tr>
    <td bgcolor="#FFFFFF"><%=rs("Id")%></td>
    <td bgcolor="#FFFFFF"><%=rs("caller")%></td>
    
    <td bgcolor="#FFFFFF"><a href="../recordService/searchtel.asp?mobile=<%=rs("called")%>&fromcs=<%=fromcs%>" target="_blank"><%=rs("called")%></a></td>
    <td bgcolor="#FFFFFF">
	<%
	if rs("type")="1" then response.Write("呼出")
	if rs("type")="2" then response.Write("打入")
	%></td>
    <td bgcolor="#FFFFFF"><%=rs("startime")%></td>
    <td bgcolor="#FFFFFF"></td>
    <td bgcolor="#FFFFFF"><%=DateAdd("s",rs("answeredtime"),0)%><%'=DateAdd("s",DATEDIFF("s", rs("beginTime"), rs("endtime")),0)%></td>
    <td bgcolor="#FFFFFF"><a href="<%=luyinurl%><%=rs("monitorfile")%>.WAV&ml=<%=ml%>" target="_blank">录音</a></td>
    <td bgcolor="#FFFFFF">
    <%
	'lyUrl=replace(rs("monitorfile"),"","")
	luyindownloadurl1=luyindownloadurl&ml
	%>
    <a href="/admin1/crmlocal/file/download.asp?FileName=<%=luyindownloadurl1%><%=rs("monitorfile")%>.WAV" target="_blank">下载录音</a></td>
  </tr>
  <%
    rs.movenext
	wend
	rs.close
	set rs=nothing
	
	conn.close
	set conn=nothing
  %>
</table>
</body>
</html>
