<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<%
response.End()
%>
<!-- #include file="../../include/ad!$#5V.asp" -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
<style type="text/css">
<!--
body,td,th {
	font-size: 12px;
}
-->
</style></head>

<body>
<table width="100%" border="1" cellpadding="3" cellspacing="0">
  <tr>
    <td nowrap="nowrap">长短期</td>
    <td nowrap="nowrap">到单人员</td>
    <td nowrap="nowrap">部门</td>
    <td nowrap="nowrap">公司</td>
    <td nowrap="nowrap">用户名</td>
    <td nowrap="nowrap">注册时间</td>
    <td nowrap="nowrap">办理时间</td>
    <td nowrap="nowrap">第一次有效联系时间</td>
    <td nowrap="nowrap">客户来源</td>
    <td nowrap="nowrap">有效联系次数</td>
    <td nowrap="nowrap">地点</td>
    <td nowrap="nowrap">行业</td>
  </tr>
  <%
  sql="select com_id,com_name,com_email,com_regtime,vip_datefrom,com_province,com_productslist_en from v_salescomp where vipflag=2 and vip_check=1 and vip_datefrom>'2008-3-15' order by vip_datefrom desc"
  set rs=conn.execute(sql)
  if not rs.eof or not rs.bof then
  while not rs.eof
  vip_datefrom=cdate(rs("vip_datefrom"))
	'判断到单人员
	sqlt="select top 1 personid from comp_tel where com_id="&rs("com_id")&" and teldate<'"&vip_datefrom+1&"' order by teldate desc"
	set rst=conn.execute(sqlt)
	if not rst.eof or not rst.bof then
		personid=rst("personid")
	else
		personid=0
	end if
	rst.close
	set rst=nothing
	realname=""
	if personid<>0 then
		sqlu="select realname,userid from users where id="&personid
		set rsu=conn.execute(sqlu)
		if not rsu.eof or not rs.bof then
			realname=rsu(0)
			userid=rsu(1)
		end if
		rsu.close
		set rsu=nothing
	
		'判断第一次有限联系时间
	    firstteldate=""
		sqlt="select top 1 teldate from comp_tel where com_id="&rs("com_id")&" and personid="&personid&" and contacttype=13 order by teldate asc "
	    set rst=conn.execute(sqlt)
		if not rst.eof or not rst.bof then
			firstteldate=rst(0)
		end if
		rst.close
		set rst=nothing
	end if
  if firstteldate<>"" then
  	longdate=datediff("d",formatdatetime(firstteldate,2),vip_datefrom)
  else
  	longdate=0
  end if 
  '判断是否在公海
  exepublic=0
  sqlp="select com_id from crm_publiccomp where com_id="&rs("com_id")
  set rsp=conn.execute(sqlp)
  if not rsp.eof or not rsp.bof then
  	exepublic=1
  end if
  rsp.close
  set rsp=nothing
  '----------------
  '判断新老客户
  ly=""
  comregtime=rs("com_regtime")
  if exepublic=0 then 
	  if datediff("d",formatdatetime(comregtime,2),vip_datefrom)<=40 then
	  	ly="新"
	  end if
  end if
  if longdate>30 then
  	ly="老"
  end if
  if exepublic=1 and longdate<30 then
  	ly="公海"
  end if
  '判断联系次数
  lxcount=""
  sqlc="select count(0) from comp_tel where com_id="&rs("com_id")&" and contacttype=13 and personid="&personid&""
  set rsc=conn.execute(sqlc)
  if not rsc.eof or not rsc.bof then
  	lxcount=rsc(0)
  end if
  rsc.close
  set rsc=nothing
  '-部门
	sqlp="select meno from cate_adminuser where code="&userid&""
	set rsp=conn.execute(sqlp)
	if not rsp.eof or not rsp.bof then
		bm=rsp(0)
	end if
	rsp.close
	set rsp=nothing
  %>
  <tr>
    <td><%=longdate%></td>
    <td>
	<%=realname%>	</td>
    <td>
	<%=bm%>
	</td>
    <td><a href="/admin1/crmlocal/modaldealog_body.asp?../crmlocal/crm_cominfoedit.asp?idprod=<%=rs("com_id")%>" target="_blank"><%=rs("com_name")%></a></td>
    <td><%=rs("com_email")%></td>
    <td><%=rs("com_regtime")%></td>
    <td><%=rs("vip_datefrom")%></td>
    <td><%=firstteldate%></td>
    <td><%=ly%></td>
    <td><%=lxcount%></td>
    <td nowrap="nowrap"><%=rs("com_province")%></td>
    <td><%=rs("com_productslist_en")%></td>
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
<%
'导出excel文件
  'response.Buffer
'  Response.ContentType = "application/msexcel"
'  Response.AddHeader "Content-disposition","attachment;filename=再生通客户列表.xls"
%>