<!-- #include file="../include/adfsfs!@#.asp" -->
<%
set connserver=server.CreateObject("ADODB.connection")
strconnserver="Provider=SQLOLEDB.1;driver={SQL Server};server=(local);uid=cru_crm;pwd=fdf@$@#dfdf9780@#1.kdsfd;database=rcu1"
connserver.open strconnserver
sdate=request("sdate")
%>


<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>第<%=request("AssignTimes")%>次/打印公司列表</title>
<style type="text/css">
<!--
body {
	margin-left: 2px;
	margin-top: 2px;
	margin-right: 2px;
	margin-bottom: 2px;
}
td {
	font-size: 12px;
	line-height: 16px;
}
.n {
	margin-bottom: 2px;
}
-->
</style>
<style media="print">
.Noprint{display:none;}
.PageNext{
	page-break-after: always;
}
div {
	font-size: 12px;
}
</style>
<!-- #include file="../../cn/function.asp" -->
<!-- #include file="../include/include.asp" -->
</head>

<body>
<div class="Noprint">
  <div align="center">
    <input type="button" name="Submit" value=" 打 印 " onClick="window.print()">
  </div>
</div>
<div>
  <div align="center" style="font-size:12px">分配时间：<%=sdate%>   激活人：<%=request("realname")%> 分配次数：<%=request("AssignTimes")%></div>
</div>
<%

i=1

sql="select com_id,com_name,com_contactperson,com_desi,com_email,com_tel,com_mobile,com_regtime,com_add,com_productslist_en from v_SalesCompActive where personid="&request("id")&" and AssignTimes="&request("AssignTimes")&"  and exists(select com_id from Crm_Active_CompAll where com_id=v_SalesCompActive.com_id) and AssignDate>='"&sdate&"' and AssignDate<='"&cdate(sdate)+1&"' and not EXISTS(select com_id from Crm_Active_Waste where waste=1 and com_id=v_SalesCompActive.com_id) and not EXISTS(select com_id from Comp_ZSTinfo where com_id=v_SalesCompActive.com_id) order by com_regtime desc"
set rs=server.CreateObject("adodb.recordset")
rs.open sql,conn,1,2
pcount=rs.recordcount
if not rs.eof then
while not rs.eof 
%>
<table width="760" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#000000">
  <tr>
    <td nowrap bgcolor="#FFFFFF">(<%=i%>)</td>
    <td width="150" nowrap bgcolor="#FFFFFF"><%=rs("com_email")%></td>
    <td bgcolor="#FFFFFF"><%=rs("com_name")%></td>
    <td bgcolor="#FFFFFF"><%=rs("com_contactperson")%><%=rs("com_desi")%></td>
    <td bgcolor="#FFFFFF"><%=rs("com_tel")%>|<%=rs("com_mobile")%></td>
    <td width="100" bgcolor="#FFFFFF"><%=formatdatetime(rs("com_regtime"),2)%></td>
  </tr>
  <tr>
    <td colspan="6" bgcolor="#F2F2F2"><%=rs("com_add")%>|
	<%
	sqlp="select top 4 pdt_name from products where com_id="&rs("com_id")
	set rsp=connserver.execute(sqlp)
	while not rsp.eof
	response.Write("·"&rsp(0))
	rsp.movenext
	wend
	rsp.close
	set rsp=nothing
	%>
	</td>
  </tr>
</table>
<table width="760" border="0" align="center" cellpadding="1" cellspacing="1" bgcolor="#000000" class="n">
  <tr>
    <td width="50" bgcolor="#FFFFFF">主营业务</td>
    <td width="216" height="20" nowrap bgcolor="#FFFFFF"><%=left(rs("com_productslist_en"),18)%></td>
    <td width="26" bgcolor="#FFFFFF">省市</td>
    <td width="58" bgcolor="#FFFFFF">&nbsp;</td>
    <td width="27" bgcolor="#FFFFFF">供求</td>
    <td bgcolor="#FFFFFF">&nbsp;</td>
    <td width="51" bgcolor="#FFFFFF">公司类型</td>
    <td width="59" bgcolor="#FFFFFF">&nbsp;</td>
  </tr>
</table>
<%
if i mod 17=0 and i<>pcount then
response.Write("<div class=PageNext></div>")
%>
<div>
  <div align="center" style="font-size:12px">分配时间：<%=sdate%>   激活人：<%=request("realname")%>  分配次数：<%=request("AssignTimes")%></div>
</div>
<%
end if
i=i+1
rs.movenext
wend
else
response.Write("今天未分配客户")
end if
rs.close
set rs=nothing
conn.close
set conn=nothing
connserver.close
set connserver=nothing
%>
</body>
</html>
