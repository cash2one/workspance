<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!-- #include file="../../../cn/function.asp" -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title></title>
<script>
function closerecord()
{
	window.close();
	parent.document.getElementById('alertmsg').style.display='none';
	parent.document.getElementById('page_cover').style.display='none';
}
function CheckAll(form)
{
	for (var i=0;i<form.elements.length;i++)
	{
		var e = form.elements[i];
		if (e.name=='comid')
	   {
		  e.checked = form.chktoggle.checked;
	   }
	}
}
</script>
<style type="text/css">
<!--
body,td,th {
	font-size: 12px;
}
-->
</style>
</head>
<body>
<%
com_id=request("com_id")
telid=request("telid")
sql="select com_tel,com_mobile from temp_salescomp where com_id="&com_id&""
set rs=conn.execute(sql)
if not rs.eof or not rs.bof then
	comtel=rs("com_tel")
	commobile=rs("com_mobile")
end if
rs.close
set rs=nothing
'------------�ҵĵ绰
if personid="" then
sql="select top 1 personid from crm_assign where com_id="&com_id&""
set rs=conn.execute(sql)
if not rs.eof or not rs.bof then
	personid=rs(0)
end if
rs.close
set rs=nothing
else
personid=request.QueryString("personid")
end if
if personid="" then
	sql="select top 1 personid from comp_tel where com_id="&com_id&" order by id desc"
	set rs=conn.execute(sql)
	if not rs.eof or not rs.bof then
		personid=rs(0)
	else
		personid=0
	end if
	rs.close
	set rs=nothing
end if

sqlu="select usertel,recordflag from users where id="&personid&""
set rsu=conn.execute(sqlu)
if not rsu.eof or not rsu.bof then
	mytel=rsu(0)
end if
rsu.close
set rsu=nothing

'-------��ȡ¼��ID
function getlytel(lytel,mytel)
	lyidlist="0"
	sqltl="select id from record_list where ((called like '%"&lytel&"%' and caller='"&mytel&"') or (caller like '%"&lytel&"%' and called='"&mytel&"')) "
	sqltl=sqltl&" and startime>='"&date&"'   order by id desc"
	'response.Write(sqltl)
	set rstl=conn.execute(sqltl)
	if not rstl.eof or not rstl.bof then
		while not rstl.eof
			lyidlist=lyidlist&","&rstl("id")
		rstl.movenext
		wend
	end if
	getlytel=lyidlist
	rstl.close
	set rstl=nothing
end function

comtel1=""
comtel2=""
comtel3=""
comtel4=""
comtelly="0"
if comtel<>"" and not isnull(comtel) then
	comtel=replace(comtel," ","")
	comtel1=right(replace(comtel,"-",""),7)
	if comtel1<>"" then 
		comtel1=left(comtel1,len(comtel1)-1)
		comtelly=comtelly&","&getlytel(comtel1,mytel)
	else
		comtelly="0"
	end if
end if
'if comtelly="0" then
	if commobile<>"" and not isnull(commobile) then
		commobile=replace(commobile," ","")
		comtel2=right(commobile,9)
		'comtel2=left(comtel2,len(comtel2)-1)
		comtelly=comtelly&","&getlytel(comtel2,mytel)
	end if
'end if

'if comtelly="0" then
	sqlt="select persontel,PersonMoblie from crm_PersonInfo where com_id="&com_id
	set rst=conn.execute(sqlt)
	if not rst.eof or not rst.bof then
		while not rst.eof
			if trim(rst("persontel"))<>"" and not isnull(rst("persontel")) then
				comtel3=right(replace(trim(rst("persontel")),"-",""),7)
				comtel3=left(comtel3,len(comtel3)-1)
				comtelly=comtelly&","&getlytel(comtel3,mytel)
			end if
			'if comtelly="0" then
				if trim(rst("PersonMoblie"))<>"" and not isnull(rst("PersonMoblie")) then
					comtel4=right(trim(rst("PersonMoblie")),9)
					comtel4=left(comtel4,len(comtel4)-1)
					comtelly=comtelly&","&getlytel(comtel4,mytel)
				end if
			'end if
		rst.movenext
		wend
	end if
	rst.close
	set rst=nothing
'end if
'1----------------------------------end	

recordID=comtelly
'----��ȡ¼����ı�������
sql="select caller,called,type from record_list where id in ("&recordID&")"
'response.Write(sql)
set rs=conn.execute(sql)
if not rs.eof or not rs.bof then
	if rs("type")="1" then
		calltel=rs("caller")
	else
		calltel=rs("called")
	end if
end if
rs.close
set rs=nothing

'----------------


'----------�жϺ������Ƿ�һ��
sqlt="select teldate from comp_tel where id="&telid&""
set rst=conn.execute(sqlt)
if not rst.eof or not rst.bof then
	nowteldate=rst(0)
end if
rst.close
set rst=nothing
'response.Write(nowteldate)
sql="select id,uniqueid,startime,caller,accountcode,answeredtime,called,monitorfile,type from record_list where id in ("&recordID&") and DATEDIFF(mi,startime,'"&nowteldate&"')<60 and DATEDIFF(mi,startime,'"&nowteldate&"')>0"
'response.Write(sql)
set rs=conn.execute(sql)
if request.Form("gl")<>"" then
	if cstr(mytel)<>cstr(calltel) then
		response.Write("<script>alert('�ú������ɡ�"&calltel&"�������ġ������������۵绰�Ƿ���ȷ��')</script>")
		response.End()
	end if
	RecordNo=rs("id")
	CallerId=rs("caller")
	CallType=rs("type")
	startTime=rs("startime")
	endTime=""
	recordTime=rs("answeredtime")
	'recordTime=""
	filePath=rs("monitorfile")
	Dtmf=rs("called")
	
	sqlmaxtel="select max(id) from comp_tel where com_id="&com_id&" and DATEDIFF(mi,'"&startTime&"',teldate)<60 and DATEDIFF(mi,'"&startTime&"',teldate)>0"
	
	set rsmax=conn.execute(sqlmaxtel)
	if not rsmax.eof or not rsmax.bof then
		TelID=rsmax(0)
	else
		response.Write("<script>alert('�绰ʱ���С��ʱ��̫���ˣ�����60�֣����޷����¹�����')</script>")
	end if
	response.Write(TelID)
	rsmax.close
	set rsmax=nothing
	'response.Write(RecordNo)
	'-----д�뵽¼����¼��
	if RecordNo<>"" then
		sqle="delete from crm_callrecord where RecordNo='"&RecordNo&"'"
		conn.execute(sqle)
		sqle="select id from crm_callrecord where RecordNo='"&RecordNo&"'"
		set rse=conn.execute(sqle)
		if rse.eof or rse.bof then
			sqlq="insert into crm_callrecord(TelID,Dtmf,com_id,personid,mytel,RecordNo,CallerId,CallType,startTime,endTime,recordTime,filePath) values("&TelID&",'"&Dtmf&"',"&com_id&","&personid&",'"&mytel&"','"&RecordNo&"','"&CallerId&"','"&CallType&"','"&startTime&"','"&endTime&"','"&recordTime&"','"&filePath&"')"
			'response.Write(sqlq)
			conn.execute(sqlq)
			response.Write("<script>alert('�����ɹ�!');closerecord();parent.document.getElementById('topt').location.reload();</script>")
		else
			
		end if
		rse.close
		set rse=nothing
	end if
	
end if
%><form id="form1" name="form1" method="post" action="">
<table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#666666">
  <tr>
    <td bgcolor="#f2f2f2">���к���</td>
    <td bgcolor="#f2f2f2">���к���</td>
    <td bgcolor="#f2f2f2">�绰����</td>
    <td bgcolor="#f2f2f2">��ʼ�绰ʱ��</td>
    <td bgcolor="#f2f2f2">ͨ��ʱ��(��)</td>
    <td bgcolor="#f2f2f2">&nbsp;</td>
  </tr>
  <%
if not rs.eof then
while not rs.eof %>
  <tr>
    <td bgcolor="#FFFFFF"><%=rs("caller")%></td>
    <td bgcolor="#FFFFFF"><a href="../recordService/searchtel.asp?mobile=<%=rs("called")%>&amp;fromcs=<%=fromcs%>" target="_blank"><%=rs("called")%></a></td>
    <td bgcolor="#FFFFFF"><%
	if rs("type")="1" then response.Write("����")
	if rs("type")="2" then response.Write("����")
	%></td>
    <td bgcolor="#FFFFFF"><%=rs("startime")%></td>
    <td bgcolor="#FFFFFF"><%=DateAdd("s",rs("answeredtime"),0)%>
      <%'=DateAdd("s",DATEDIFF("s", rs("beginTime"), rs("endtime")),0)%></td>
    <td bgcolor="#FFFFFF"><a href="<%=luyinurl%><%=rs("monitorfile")%>.WAV" target="_blank">¼��</a></td>
  </tr>
  <%
    rs.movenext
	wend
	rs.close
	set rs=nothing
else
	response.Write("��¼������")
end if
  %>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="30" align="center">
    <input type="submit" name="button" id="button" value="  ����¼��  " />      
    <input type="button" name="button3" id="button3" value=" �� �� " onClick="closerecord()" />
    <input type="hidden" name="com_id" id="com_id" value="<%=com_id%>" />
    <input name="gl" type="hidden" id="gl" value="1" /></td>
  </tr>
</table></form>
</body>
</html>
<%
conn.close
set conn=nothing
%>
