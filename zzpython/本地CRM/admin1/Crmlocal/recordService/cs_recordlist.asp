<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!--#include file="../../include/pagefunction.asp"-->
<%
sear="n="
com_id=request("com_id")
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>¼������</title>
<style type="text/css">
<!--
body,td,th {
	font-size: 12px;
}
-->
</style>
<SCRIPT language=javascript src="../DatePicker.js"></SCRIPT>
<link href="../datepicker.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style></head>

<body>
<%
if request("fromcs")<>"1" then
%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
    <form id="form1" name="form1" method="post" action="cs_recordlist.asp">
    ���к���
      
        <input name="CalledId" type="text" id="CalledId" size="15" />
        ���к���
        <input name="Dtmf" type="text" id="Dtmf" size="15" />
<select name="doperson" class="button" id="doperson" >
              <option value="" >��ѡ��--</option>
			  <%
			  sqlc="select code,meno from cate_adminuser where code like '24%'"
			  set rsc=conn.execute(sqlc)
			  if not rsc.eof then
			  while not rsc.eof
			  %>
			  <option value="" <%=sle%>>��&nbsp;&nbsp;��<%response.Write(rsc("meno"))%></option>
                        <%
						sqlu="select realname,id,userTel from users where chatflag=1 and chatclose=1 and userid="&rsc("code")&""
						set rsu=server.CreateObject("ADODB.recordset")
						rsu.open sqlu,conn,1,1
						if not rsu.eof then
						while not rsu.eof
						userTel=rsu("usertel")
						if cstr(request("doperson"))=cstr(userTel) then
							sle="selected"
						else
							sle=""
						end if
						%>
              <option value="<%=userTel%>" <%=sle%>>��&nbsp;&nbsp;��&nbsp;&nbsp;��<%response.Write(rsu("realname"))%></option>
              <%
						rsu.movenext
						wend
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
�绰ʱ��
<script language=javascript>createDatePicker("fromdate",true,"<%=request.Form("fromdate")%>",false,false,false,true)</script>
��<script language=javascript>createDatePicker("todate",true,"<%=request.Form("todate")%>",false,false,false,true)</script>
    
      ʱ����
      <input name="fromsc" type="text" id="fromsc" size="5"  value="<%=request("fromsc")%>"/>
      ���ӵ�
      <input name="tosc" type="text" id="tosc" size="5" value="<%=request("tosc")%>"/>
      <input type="submit" name="button" id="button" value="�ύ" />
    </form></td>
  </tr>
  
</table>
<%
end if
%>
<%
	sql=" 1=1"
	sear=sear&"&com_id="&com_id&"&fromcs=1"
	
	if com_id="" then
		if request("doperson")<>"" then
			sql=sql&" and mytel like '%"&request("doperson")&"%'"
			sear=sear&"&doperson="&request("doperson")
		end if
	else
		sql=sql&" and com_id="&com_id
	end if
	
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
	sql=sql&" and cspersonid>0"
	'response.Write(sql)
    Set oPage=New clsPageRs2
	With oPage
		.sltFld  = "*"
		.FROMTbl = "crm_callrecord"
		.sqlOrder= "order by starttime desc"
		.sqlWhere= " where "&sql
		.keyFld  = "id"    '����ȱ��
		.pageSize= 10
		.getConn = conn
		Set Rs  = .pageRs
	End With
	total=oPage.getTotalPage
	oPage.pageNav "?"&sear,""
	totalpg=int(total/10)
  %>
<table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#666666">
  <tr>
    <td bgcolor="#f2f2f2">���</td>
    <td bgcolor="#f2f2f2">���к���</td>
    <td bgcolor="#f2f2f2">���к���</td>
    <td bgcolor="#f2f2f2">&nbsp;</td>
    <td bgcolor="#f2f2f2">�绰����</td>
    <td bgcolor="#f2f2f2">��ʼ�绰ʱ��</td>
    <td bgcolor="#f2f2f2">�����绰ʱ��</td>
    <td bgcolor="#f2f2f2">ͨ��ʱ��</td>
    <td bgcolor="#f2f2f2">����</td>
    <td bgcolor="#f2f2f2">&nbsp;</td>
    <td bgcolor="#f2f2f2">&nbsp;</td>
  </tr>
 <%
while not rs.eof %>
  <tr>
    <td bgcolor="#FFFFFF"><%=rs("RecordNo")%></td>
    <td bgcolor="#FFFFFF"><%=rs("mytel")%></td>
    <td bgcolor="#FFFFFF"><a href="http://admin.zz91.com/admin1/compinfo/crm_cominfoedit.asp?idprod=<%=rs("com_id")%>&dotype=allcomp" target="_blank"><%=rs("CallerId")%></a></td>
    <td bgcolor="#FFFFFF"><%=left(rs("Dtmf"),30)%></td>
    <td bgcolor="#FFFFFF"><%
	if rs("CallType")="1" then response.Write("����")
	if rs("CallType")="2" then response.Write("����")
	%></td>
    <td bgcolor="#FFFFFF"><%=rs("startTime")%></td>
    <td bgcolor="#FFFFFF"><%'=rs("endTime")%></td>
    <td bgcolor="#FFFFFF"><%=DateAdd("s",rs("recordTime"),0)%></td>
    <td nowrap="nowrap" bgcolor="#FFFFFF">
    <%
	 sqlm="select top 1 realname from users where UserTel='"&rs("mytel")&"' "
	 set rsm=conn.execute(sqlm)
	 if not rsm.eof or not rsm.bof then
		 response.Write(rsm(0))
	 end if
	 rsm.close
	 set rsm=nothing
	 lyUrl=replace(rs("filepath"),"","")
	%>
    </td>
    <td nowrap="nowrap" bgcolor="#FFFFFF"><a href="<%=luyinurl%><%=rs("filepath")%>.WAV" target="_blank">¼��</a></td>
    <td nowrap="nowrap" bgcolor="#FFFFFF"><a href="/admin1/crmlocal/file/download.asp?filename=<%=rs("filepath")%>">����¼��</a></td>
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
