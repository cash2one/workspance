<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../include/adfsfs!@#.asp" -->
<!-- #include file="../jumptolog.asp" -->
<!--#include file="Cls_vbsPage.asp"-->
<%
'-----------------------------------------------------------------------------------------------
On Error Resume Next
DIM startime,endtime
startime=timer()
'-----------------------------------------------------------------------------------------------
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>�ޱ����ĵ�</title>
<style type="text/css">
<!--
table {  font-size: 12px}
a {  font-size: 12px; color: #000000; text-decoration: none}
-->
</style>
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <%
Dim ors
Set ors=new Cls_vbsPage	'��������
Set ors.Conn=conn		'�õ����ݿ����Ӷ���
With ors
	.PageSize=200		'ÿҳ��¼����
	.PageName="page"	'cookies����
	.DbType="MSSQL"
	'���ݿ�����,ACΪaccess,MSSQLΪsqlserver2000�洢���̰�,MYSQLΪmysql,PGSQLΪPostGreSql
	.RecType=0
	'��¼����(>0Ϊ����ȡֵ�ٸ�����߹̶�ֵ,0ִ��count���ô�cookies,-1ִ��count������cookies)
	.JsUrl=""			'Cls_jsPage.js��·��
	.Pkey="com_id"			'����
	.Field="*"
	.Table="temp_salescomp"
	.Condition="editdate>'"&date()-1&"'"		'����,����Ҫwhere
	.OrderBy="com_regtime asc"	'����,����Ҫorder by,��Ҫasc����desc
End With

iRecCount=ors.RecCount()'��¼����
iRs=ors.ResultSet()		'����ResultSet
If  iRecCount<1 Then
	response.Write("����Ϣ��")
Else     
    For i=0 To Ubound(iRs,2)
	sql="select * from temp_salescomp where com_id="&iRs(4,i)
	set rsc=conn.execute(sql)
	if rsc.eof then
	rsc.addnew()
	end if
	for n=0 to 23
	rsc(n)=iRs(n,i)
	next
	rsc.update()
	rsc.close
	set rsc=nothing
    Next	
End If

%>
</table>
<table width="760" border="0" cellspacing="2" cellpadding="2" align="center">
  <tr> 
    <td>
<%ors.ShowPage()%>
</td>
  </tr>  
</table> 
<table width="760" border="0" align="center" cellpadding="2" cellspacing="2">
  <tr> 
    <td align="center"> 
      <%endtime=timer()%>
      ��ҳ��ִ��ʱ�䣺<%=FormatNumber((endtime-startime)*1000,3)%>����</td>
  </tr>
</table>
</body>
</html>
