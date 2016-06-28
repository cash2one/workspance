
<!-- #include file="../include/adfsfs!@#.asp" -->
<!-- #include file="../../cn/function.asp" -->
<!--#include file="../include/include.asp"-->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
</head>
<body>
<table width="100%" border="1" cellspacing="0" cellpadding="0">
 
  <%
  'response.Write(request("compkind"))
  if request("compkind")="" then
  	response.Write("请选择客户标注")
	response.End()
  end if
  if request("outcontent")="" then
  	response.Write("请选择导出内容")
	response.End()
  end if
  outcontent=request("outcontent")
  arroutcontent=split(outcontent,",")
  sql="select "&request("outcontent")&" from comp_info where com_id in (select com_id from crm_WebcompKind where com_type in ("&request("compkind")&")) order by vip_datefrom desc "
  set rs=conn.execute(sql)
  if not rs.eof or not rs.bof then
  while not rs.eof
 
  %>
  <tr>
  	<%for i=0 to ubound(arroutcontent)%>
    <td height="15"><%=rs(i)%></td>
    <%next%>
    
  </tr>
  <%
  rs.movenext
  wend
  end if
  rs.close
  set rs=nothing
  conn.close
  set conn=nothing
  %>
</table>
</body>
</html>
<%
'导出excel文件
  'response.Buffer
'  Response.ContentType = "application/msexcel"
'  Response.AddHeader "Content-disposition","attachment;filename=客户列表.xls"
%>
