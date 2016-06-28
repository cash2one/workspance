<!-- #include file="../../../include/ad!$#5V.asp" -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
</head>
<body>
<table width="100%" border="1" cellspacing="0" cellpadding="0">
  <tr>
    <td height="15">关键字</td>
    <td>公司名称</td>
    <td>email</td>
    <td>开始时间</td>
    <td>结束时间</td>
    <td>广告部归属者</td>
    <td>再生通销售归属者</td>
    <td>点击率</td>
  </tr>
  <%
  sqls=" 1=1"
  If trim(request("keyName"))<>"" Then
	sqls="and keywords like '%"&trim(request("keyName"))&"%'"
	sear=sear&"&keyName="&trim(request("keyName"))
	end if
	If trim(request("subDomain"))<>"" Then
		sqls="and url like '%"&trim(request("subDomain"))&"%'"
		sear=sear&"&subDomain="&trim(request("subDomain"))
	end if
	if request("email")<>"" then
		sqls=sqls&" and com_email like '%"&request("email")&"%'"
		sear=sear&"&email="&trim(request("email"))
	end if
	if request("ADFromDate_from")<>"" then
		sqls=sqls&" and from_date>='"&Datevalue(request("ADFromDate_from"))&"'"
		sear=sear&"&ADFromDate_from="&request("ADFromDate_from")
	end if
	if request("ADFromDate_to")<>"" then
		sqls=sqls&" and from_date<'"&Datevalue(request("ADFromDate_to"))+1&"'"
		sear=sear&"&ADFromDate_to="&request("ADFromDate_to")
	end if
	if request("ADStopDate_from")<>"" then
		sqls=sqls&" and to_date>='"&Datevalue(request("ADStopDate_from"))&"'"
		sear=sear&"&ADStopDate_from="&request("ADStopDate_from")
	end if
	if request("ADStopDate_to")<>"" then
		sqls=sqls&" and to_date<'"&Datevalue(request("ADStopDate_to"))+1&"'"
		sear=sear&"&ADStopDate_to="&request("ADStopDate_to")
	end if
  
  sql="select * from Adv_KeyBaojiaList where "&sqls&" order by id desc "
  set rs=conn.execute(sql)
  if not rs.eof or not rs.bof then
  while not rs.eof
  url=rs("url")
  comname=""
  email=rs("com_email")
  sqlc="select com_name,com_id from comp_info where com_email='"&email&"'"
  set rsc=conn.execute(sqlc)
  if not rsc.eof or not rsc.bof then
	comname=rsc(0)
	comid=rsc(1)
  else
  	comname=""
	comid=0
  end if
  rsc.close
  set rsc=nothing
  '再生通销售归属者
  sqlp="select a.realname from users as a,crm_assign as b where a.id=b.personid and b.com_id="&comid&""
  set rsp=conn.execute(sqlp)
  if not rsp.eof or not rsp.bof then
  	zstperson_name=rsp(0)
  else
  	zstperson_name=""
  end if
  rsp.close
  set rsp=nothing
  '广告归属者
  sqlp="select a.realname from users as a,crm_assignad as b where a.id=b.personid and b.com_id="&comid&""
  set rsp=conn.execute(sqlp)
  if not rsp.eof or not rsp.bof then
  	adperson_name=rsp(0)
  else
  	adperson_name=""
  end if
  rsp.close
  set rsp=nothing
  %>
  <tr>
    <td height="15"><%=rs("keywords")%></td>
    <td><%=comname%></td>
    <td><%=rs("com_email")%></td>
    <td><%=rs("from_date")%></td>
    <td>
	<%=rs("to_date")%></td>
    <td><%=adperson_name%></td>
    <td><%=zstperson_name%></td>
    <td><%=rs("Kcount")%></td>
  </tr>
  <%
  rs.movenext
  wend
  end if
  rs.close
  set rs=nothing
  conn.close
  set conn=nothing
  'connnews.close
  'set connnews=nothing
  %>
</table>
</body>
</html>
<%
'导出excel文件
  response.Buffer
  Response.ContentType = "application/msexcel"
  Response.AddHeader "Content-disposition","attachment;filename=搜索关键字报价列表顶部广告.xls"
%>
