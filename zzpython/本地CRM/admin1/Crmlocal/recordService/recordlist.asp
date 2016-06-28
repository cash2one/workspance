<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!--#include file="../../include/pagefunction.asp"-->
<%
sear="n="
if request.QueryString("del")="1" and request.QueryString("id")<>"" then
	sql="delete from crm_UploadFile where id="&request.QueryString("id")
	conn.execute(sql)
end if
com_id=request("com_id")
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
'----------
'connrecord.open strconnrecord
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>录音数据</title>
<style type="text/css">
<!--
body,td,th {
	font-size: 12px;
}
-->
</style>
<SCRIPT language=javascript src="../DatePicker.js"></SCRIPT>
<link href="../datepicker.css" rel="stylesheet" type="text/css">
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
    <form id="form1" name="form1" method="post" action="recordlist.asp">
    主叫号码
      
        <input name="CalledId" type="text" id="CalledId" size="15" />
        被叫号码
        <input name="Dtmf" type="text" id="Dtmf" size="15" />
<select name="doperson" class="button" id="doperson" >
  <option value="" >请选择--</option>
			  <% If session("userid")="13" Then %>
			  	<option value="<%=session("personid")%>" >┆&nbsp;&nbsp;┆&nbsp;&nbsp;┿<%=userName%></option>
			  <% End If %>
			  <%
			  if ywadminid<>"" and not isnull(ywadminid)  then
			      sqlc="select code,meno from cate_adminuser where code in("&ywadminid&")"
			  else
				  if session("userid")="10" then
				  	 sqlc="select code,meno from cate_adminuser where code like '13%'"
				  else
				  	 sqlc="select code,meno from cate_adminuser where code like '"&session("userid")&"%'"
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
<br />
电话时间
<script language=javascript>createDatePicker("fromdate",true,"<%=request.Form("fromdate")%>",false,false,false,true)</script>
到<script language=javascript>createDatePicker("todate",true,"<%=request.Form("todate")%>",false,false,false,true)</script>
    
      时长从
      <input name="fromsc" type="text" id="fromsc" size="5"  value="<%=request("fromsc")%>"/>
      分钟到
      <input name="tosc" type="text" id="tosc" size="5" value="<%=request("tosc")%>"/>
      <input type="submit" name="button" id="button" value="提交" />
    </form></td>
  </tr>
  <tr>
    <td>
    <%
	sql=" 1=1"
	sear=sear&"&com_id="&com_id
	if session("userid")="10" then
		if com_id="" then
			
			if request("doperson")<>"" then
				sql=sql&" and personid="&request("doperson")
			end if
		else
			sql=sql&" and com_id="&com_id
		end if
	else
		if com_id="" then
			if request("doperson")<>"" then
				sql=sql&" and personid="&request("doperson")
				sear=sear&"&doperson="&request("doperson")
			else
				if request.QueryString("personid")="" then
					if session("userid")="13" then
						sql=sql&" and personid in (select id from users where userid in("&ywadminid&"))"
					else
						sql=sql&" and personid="&session("personid")
					end if
				else
					sql=sql&" and personid="&request.QueryString("personid")
				end if
			end if
		else
			sql=sql&" and com_id="&com_id
		end if
	end if
	sear=sear&"&doperson="&request("doperson")
	if request("CalledId")<>"" then
		sql=sql&" and mytel like '%"&request("CalledId")&"%'"
		sear=sear&"&CalledId="&request("CalledId")
	end if
	if request("Dtmf")<>"" then
		sql=sql&" and Dtmf like '%"&request("Dtmf")&"%'"
		sear=sear&"&Dtmf="&request("Dtmf")
	end if
	if request("fromdate")<>"" then
		sql=sql&" and starttime>='"&request("fromdate")&"'"
		sear=sear&"&fromdate="&request("fromdate")
	end if
	if request("todate")<>"" then
		sql=sql&" and starttime<='"&request("todate")&"'"
		sear=sear&"&todate="&request("todate")
	end if
	if request("fromsc")<>"" then
		sql=sql&" and DATEDIFF(second,starttime,endtime)>='"&request("fromsc")*60&"'"
		sear=sear&"&fromsc="&request("fromsc")
	end if
	if request("tosc")<>"" then
		sql=sql&" and DATEDIFF(second,starttime,endtime)<='"&request("tosc")*60&"'"
		sear=sear&"&tosc="&request("tosc")
	end if
    Set oPage=New clsPageRs2
	With oPage
		.sltFld  = "*"
		.FROMTbl = "crm_callrecord"
		.sqlOrder= "order by starttime desc"
		.sqlWhere= " where "&sql
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
    <td bgcolor="#f2f2f2">&nbsp;</td>
    <td bgcolor="#f2f2f2">电话类型</td>
    <td bgcolor="#f2f2f2">开始电话时间</td>
    <td bgcolor="#f2f2f2">结束电话时间</td>
    <td bgcolor="#f2f2f2"><a href="?fromdate=<%=fromdate%>&amp;todate=<%=todate%>&amp;thsjord=1&amp;personid=<%=request.QueryString("personid")%>&com_id=<%=com_id%>">通话时长</a></td>
    <td bgcolor="#f2f2f2">销售</td>
    <td bgcolor="#f2f2f2">&nbsp;</td>
    <td bgcolor="#f2f2f2">&nbsp;</td>
  </tr>
 <%
while not rs.eof %>
  <tr>
    <td bgcolor="#FFFFFF"><%=rs("RecordNo")%></td>
    <td bgcolor="#FFFFFF"><%=rs("mytel")%></td>
    <td bgcolor="#FFFFFF"><a href="/admin1/crmlocal/crm_cominfoedit.asp?idprod=<%=rs("com_id")%>" target="_blank"><%=rs("CallerId")%></a></td>
    <td bgcolor="#FFFFFF"><%=left(rs("Dtmf"),30)%></td>
    <td bgcolor="#FFFFFF"><%
	if rs("CallType")="1" then response.Write("呼出")
	if rs("CallType")="2" then response.Write("打入")
	%></td>
    <td bgcolor="#FFFFFF"><%=rs("startTime")%></td>
    <td bgcolor="#FFFFFF"><%'=rs("endTime")%></td>
    <td bgcolor="#FFFFFF"><%=DateAdd("s",rs("recordTime"),0)%></td>
    <td nowrap="nowrap" bgcolor="#FFFFFF">
    <%
	sqlm="select realname from users where id="&rs("personid")&" "
	 set rsm=conn.execute(sqlm)
	 if not rsm.eof or not rsm.bof then
		 response.Write(rsm(0))
	 end if
	 rsm.close
	 set rsm=nothing
	 lyUrl=replace(rs("filepath"),"","")
	%>
    </td>
    <td nowrap="nowrap" bgcolor="#FFFFFF"><a href="<%=luyinurl%><%=rs("filepath")%>.WAV" target="_blank">录音</a></td>
    <td nowrap="nowrap" bgcolor="#FFFFFF"><a href="/admin1/crmlocal/file/download.asp?filename=<%=luyindownloadurl%><%=rs("filepath")%>.WAV">下载录音</a></td>
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
