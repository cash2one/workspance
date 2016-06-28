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
'----------
connrecord.open strconnrecord
if request.QueryString("daoru")="1" then
	a=1
	'sql="delete from temp_telid where telid in (select id from comp_tel where personid=693)"
	'conn.execute(sql)
	sqlc="select top 100 id,com_id,personid from comp_tel where TelDate>'"&date&"' and not exists(select telid from temp_telid where comp_tel.id=telid) and personid in (select id from users where partid='13') and contacttype='13' order by id desc"
	set rsc=conn.execute(sqlc)
	if not rsc.eof or not rsc.bof then
		while not rsc.eof
			'-----读取销售电话
			personid=rsc("personid")
			sqlu="select usertel from users where id="&personid&""
			set rsu=conn.execute(sqlu)
			if not rsu.eof or not rsu.bof then
				mytel=rsu(0)
			end if
			rsu.close
			set rsu=nothing
			idprod=rsc("com_id")
			'response.Write(idprod&"<br>")
			
			sqlu="select com_tel,com_mobile from comp_info where com_id="&idprod&""
			set rsu=conn.execute(sqlu)
			if not rsu.eof or not rsu.bof then
				comtel=rsu(0)
				commobile=rsu("com_mobile")
			end if
			rsu.close
			set rsu=nothing
			
			
			
			'-------读取客户电话
			comtel1=""
			comtel2=""
			comtel3=""
			comtel4=""
			if comtel<>"" and not isnull(comtel) then
				comtel1=right(replace(comtel,"-",""),7)
				comtel1=left(comtel1,len(comtel1)-1)
			end if
			if commobile<>"" and not isnull(commobile) then
				comtel2=right(commobile,9)
				comtel2=left(comtel2,len(comtel2)-1)
			end if
			sqlt="select top 1 persontel,PersonMoblie from crm_PersonInfo where com_id="&idprod
			set rst=conn.execute(sqlt)
			if not rst.eof or not rst.bof then
				if rst("persontel")<>"" and not isnull(rst("persontel")) then
					comtel3=right(replace(rst("persontel"),"-",""),7)
					comtel3=left(comtel3,len(comtel3)-1)
				end if
				if rst("PersonMoblie")<>"" and not isnull(rst("PersonMoblie")) then
					comtel4=right(rst("PersonMoblie"),9)
					comtel4=left(comtel4,len(comtel4)-1)
				end if
			end if
			rst.close
			set rst=nothing
			
			sqlteltemp=""
			
			if comtel1<>"" then
				if len(comtel1)>=6 then
					sqlteltemp=sqlteltemp&" Dtmf like '%"&comtel1&"%' or"
				end if
			end if
			if comtel2<>"" then
				if len(comtel2)>=8 then
					sqlteltemp=sqlteltemp&" Dtmf like '%"&comtel2&"%' or"
				end if
			end if
			if comtel3<>"" then
				if len(comtel3)>=6 then
					sqlteltemp=sqlteltemp&" Dtmf like '%"&comtel3&"%' or"
				end if
			end if
			if comtel4<>"" then
				if len(comtel4)>=8 then
					sqlteltemp=sqlteltemp&" Dtmf like '%"&comtel4&"%' or"
				end if
			end if
			if sqlteltemp<>"" then
				sqlteltemp=left(sqlteltemp,len(sqlteltemp)-2)
			end if
			
			if sqlteltemp<>"" then
				
				'---是CRM里的电话
				notel=0
			else
				'---不是CRM里的电话
				
				notel=1
			end if
			'-------读取录音电话
			'and DATEDIFF(minute,endtime,getdate())<30
			if notel=0 then
				RecordNo=""
				sqlt="select RecordNo,CallerId,CallType,startTime,endTime,recordTime,filePath,Dtmf from callTb where ("&sqlteltemp&")"
				if mytel<>"" then
					sqlt=sqlt&" and calledId='"&mytel&"' "
				end if
				sqlt=sqlt&" and endTime>'"&date&"' order by endTime desc"
				'response.Write(sqlt&"<br>")
				set rst=connrecord.execute(sqlt)
				if not rst.eof or not rst.bof then
					while not rst.eof
						RecordNo=rst("RecordNo")
						CallerId=rst("CallerId")
						CallType=rst("CallType")
						startTime=rst("startTime")
						endTime=rst("endTime")
						recordTime=rst("recordTime")
						filePath=rst("filePath")
						Dtmf=rst("Dtmf")
						
						'-----写入到录音记录表
						if RecordNo<>"" then
							sqle="select id from crm_callrecord where RecordNo='"&RecordNo&"'"
							set rse=conn.execute(sqle)
							if rst.eof or rse.bof then
								sqlq="insert into crm_callrecord(telid,com_id,personid,mytel,RecordNo,CallerId,CallType,startTime,endTime,recordTime,filePath,Dtmf) values("&rsc("id")&","&idprod&","&personid&",'"&mytel&"','"&RecordNo&"','"&CallerId&"','"&CallType&"','"&startTime&"','"&endTime&"','"&recordTime&"','"&filePath&"','"&Dtmf&"')"
								conn.execute(sqlq)
							end if
							rse.close
							set rse=nothing
							response.Write(a&"<br>")
						end if
						rst.movenext
					wend
				end if
				rst.close
				set rst=nothing
				
			end if
			
			sql="insert into temp_telid(telid) values("&rsc("id")&")"
			conn.execute(sql)
			a=a+1
		rsc.movenext
		wend
	end if
	rsc.close
	set rsc=nothing
end if

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
<form id="form1" name="form1" method="post" action="filelistly.asp"><table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>本机号码
      
        <input name="CalledId" type="text" id="CalledId" size="15" />
        对方号码
        <input name="Dtmf" type="text" id="Dtmf" size="15" />
<select name="doperson" class="button" id="doperson" >
          <option value="" >请选择--</option>
			 
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
						sqlu="select realname,id,usertel from users where closeflag=1 and userid="&rsc("code")&""
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
      <input type="submit" name="button" id="button" value="提交" /></td>
  </tr>
  <tr>
    <td><a href="?daoru=1">导入</a></td>
  </tr>
</table></form>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
    <%
	sql=""
	if request("doperson")<>"" then
		sql=sql&" and CalledId='"&request("doperson")&"'"
		sear=sear&"&doperson="&request("doperson")
	end if
	if request("CalledId")<>"" then
		sql=sql&" and CalledId like '%"&request("CalledId")&"%'"
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
	response.Write(sql)
    Set oPage=New clsPageRs2
	With oPage
		.sltFld  = "*"
		.FROMTbl = "callTb"
		.sqlOrder= "order by id desc"
		.sqlWhere= " where 1=1"&sql
		.keyFld  = "id"    '不可缺少
		.pageSize= 10
		.getConn = connrecord
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
    <td bgcolor="#f2f2f2">本机号码</td>
    <td bgcolor="#f2f2f2">电话号码</td>
    <td bgcolor="#f2f2f2">&nbsp;</td>
    <td bgcolor="#f2f2f2">电话类型</td>
    <td bgcolor="#f2f2f2">开始电话时间</td>
    <td bgcolor="#f2f2f2">结束电话时间</td>
    <td bgcolor="#f2f2f2">通话时长</td>
    <td bgcolor="#f2f2f2">&nbsp;</td>
    <td bgcolor="#f2f2f2">&nbsp;</td>
  </tr>
 <%
while not rs.eof %>
  <tr>
    <td bgcolor="#FFFFFF"><%=rs("RecordNo")%></td>
    <td bgcolor="#FFFFFF"><%=rs("CalledId")%></td>
    <td bgcolor="#FFFFFF"><%=rs("CallerId")%></td>
    <td bgcolor="#FFFFFF"><%=left(rs("Dtmf"),20)&"..."%></td>
    <td bgcolor="#FFFFFF">
	<%
	if rs("CallType")="0" then response.Write("呼出")
	if rs("CallType")="1" then response.Write("打入")
	%></td>
    <td bgcolor="#FFFFFF"><%=rs("startTime")%></td>
    <td bgcolor="#FFFFFF"><%=rs("endTime")%></td>
    <td bgcolor="#FFFFFF"><%=DateAdd("s",DATEDIFF("s", rs("starttime"), rs("endtime")),0)%></td>
    <td bgcolor="#FFFFFF"><a href="lyshow.asp?lyUrl=<%=rs("filepath")%>" target="_blank">录音</a></td>
    <td bgcolor="#FFFFFF">
    <%
	lyUrl=replace(rs("filepath"),"E:\","")
	%>
    <a href="http://192.168.2.14/download.asp?FileName=<%=lyUrl%>">下载录音</a></td>
  </tr>
  <%
    rs.movenext
	wend
	rs.close
	set rs=nothing
	connrecord.close
	set connrecord=nothing
	conn.close
	set conn=nothing
  %>
</table>
</body>
</html>
