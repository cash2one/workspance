<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!-- #include file="../../../cn/function.asp" -->
<!-- #include file="../../include/include.asp" -->
<%
personid=request.QueryString("personid")
fromdate=request.QueryString("fromdate")
todate=request.QueryString("todate")
localtel=request.QueryString("localtel")
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>通话时长</title>
<style type="text/css">
<!--
body,td,th {
	font-size: 12px;
}
-->
</style></head>

<body>
<table width="100%" border="0" cellspacing="1" cellpadding="5" bgcolor="#999999">
  <tr>
    <td bgcolor="#CCCCCC">对方号码</td>
    <td bgcolor="#CCCCCC">销售</td>
    <td bgcolor="#CCCCCC">开始时间</td>
    <td bgcolor="#CCCCCC">结束时间</td>
    <td bgcolor="#CCCCCC">方向</td>
    <td bgcolor="#CCCCCC"><a href="?localtel=<%=localtel%>&fromdate=<%=fromdate%>&todate=<%=todate%>&thsjord=1&personid=<%=request.QueryString("personid")%>">通话时长</a></td>
    <td bgcolor="#CCCCCC">录音</td>
  </tr>
  <%
	sqlm="select realname from users where id="&request.QueryString("personid")&" "
	 set rsm=conn.execute(sqlm)
	 if not rsm.eof or not rsm.bof then
		 realname=rsm(0)
	 end if
	 rsm.close
	 set rsm=nothing
  sql="select * from crm_callRecord where personid='"&request.QueryString("personid")&"' and starttime>='"&fromdate&"' and starttime<='"&cdate(todate)+1&"'"
  
  if request("thsjord")="1" then
  	sql=sql&" order by recordTime desc"
  elseif request("telord")="1" then
  	sql=sql&" order by tel asc"
  else
  	sql=sql&" order by starttime asc"
  end if
  set rs=conn.execute(sql)
  if not rs.eof or not rs.bof then
  while not rs.eof
  %>
  <tr>
    <td bgcolor="#FFFFFF"><a href="/admin1/crmlocal/crm_cominfoedit.asp?idprod=<%=rs("com_id")%>" target="_blank"><%=rs("Dtmf")%></a></td>
    <td bgcolor="#FFFFFF"><%=realname%></td>
    <td bgcolor="#FFFFFF"><%=rs("starttime")%></td>
    <td bgcolor="#FFFFFF"><%'=rs("endtime")%></td>
    <td bgcolor="#FFFFFF"><%=rs("CallType")%></td>
    <td bgcolor="#FFFFFF"><%=DateAdd("s",rs("recordTime"),0)%></td>
    <td bgcolor="#FFFFFF">
    <%
	'lysours=replace(rs("fileurl"),"D:\TMNData\","")
'	lysours=replace(lysours,"\","|")
'	lysours=replace(lysours,"-","!")
	%>
    <a href="<%=luyinurl%><%=rs("filepath")%>.WAV" target="_blank">听录音</a></td>
  </tr>
  <%
  rs.movenext
  wend
  end if
  rs.close
  set rs=nothing
  %>
</table>
</body>
</html>
<%
conn.close
set conn=nothing
%>
