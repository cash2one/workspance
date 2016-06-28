<!-- #include file="../include/adfsfs!@#.asp" -->
<!-- #include file="../../cn/function.asp" -->
<!-- #include file="../include/include.asp" -->
<%
if request("del")="1" then
	sqldel="delete from comp_telAD where id="&request("id")
	conn.execute(sqldel)
	response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
end if
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>销售服务记录</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.readon {
	font-size: 14px;
	font-weight: normal;
	color: #FFF;
	background-color: #900;
}
.readon a{
	color:#FFF;
}
.readon a:link{
	color:#FFF;
}
.readon a:hover{
	color:#FFF;
}
.readon a:visited{
	color:#FFF;
}
-->
</style>
<link href="../css.css" rel="stylesheet" type="text/css">

<link href="../inc/Style.css" rel="stylesheet" type="text/css">
</head>

<body  scroll=yes>
<%
		sear=sear&"adminuser="&request.QueryString("adminuser")&"&contacttype="&request.QueryString("contacttype")&"&com_id="&request.QueryString("com_id")
		sql="select * from comp_telAD where com_id="&request("com_id")&" "
		   if cstr(request("from_date"))<>cstr(request("to_date")) then
				if request("from_date")<>"" then
					sql=sql&" and teldate>='"&request("from_date")&"'"
					sear=sear&"&from_date="&request("from_date")
				end if
				if request("to_date")<>"" then
					sql=sql&" and teldate<='"&request("to_date")&"'"
					sear=sear&"&to_date="&request("to_date")
				end if
			else
			    if request("from_date")<>"" and request("to_date")<>"" then
					sql=sql&" and year(teldate)="&year(request("from_date"))&" and month(teldate)="&month(request("from_date"))&" and day(teldate)="&day(request("from_date"))&""
				    sear=sear&"&from_date="&request("from_date")
					sear=sear&"&to_date="&request("to_date")
				end if
			end if
			if request.QueryString("orderaction")<>"" then
				sql=sql&" order by "&request.QueryString("orderaction")&" desc"
				sear=sear&"&orderaction="&request("orderaction")
			else
				sql=sql&" order by teldate desc"
			end if
			
			
		  set rs=server.CreateObject("ADODB.recordset")
		  rs.open sql,conn,1,1
		%>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="200" height="30" align="center" class="<%if telflag="1" then response.Write("readon")%>"><a href="crm_tel_comp.asp?com_id=<%=request("com_id")%>&telflag=1">线下BD部电话记录</a></td>
            <td width="200" align="center" class="<%if telflag="0" then response.Write("readon")%>"><a href="crm_tel_comp.asp?com_id=<%=request("com_id")%>&telflag=0">再生通<b>新签</b>销售电话记录</a></td>
            <td width="200" align="center" class="<%if telflag="2" then response.Write("readon")%>"><a href="crm_tel_comp.asp?com_id=<%=request("com_id")%>&telflag=2">再生通<b>续签</b>销售电话记录</a></td>
            <td width="150" align="center" class="readon"><a href="crm_Adtel_comp.asp?com_id=<%=request("com_id")%>">广告部电话记录</a></td>
			<td width="150" align="center" bgcolor="#CCCCCC">
            <%
			sqlz="select count(0) from crm_zgtel_content where com_id="&request("com_id")&" and read_check=0"
			set rsz=conn.execute(sqlz)
			if not rsz.eof or not rsz.bof then
				zgcount=rsz(0)
			end if
			rsz.close
			set rsz=nothing
			%>
            <a href="crm_zgtel_comp.asp?com_id=<%=request("com_id")%>">主管建议</a>(<font color="#FF0000"><%=zgcount%></font>)</td>
            <td>&nbsp;</td>
          </tr>
        </table>
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td><!--#include file="../include/pagetop_notop.asp"--></td>
          </tr>
</table>
          <table width="100%" border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#7386A5">
          <tr>
            <td nowrap bgcolor="#CED7E7">联系时间</td>
            <td nowrap bgcolor="#CED7E7">联系情况</td>
            <td nowrap bgcolor="#CED7E7">联系人</td>
            <td nowrap bgcolor="#CED7E7">客户等级</td>
            <td nowrap bgcolor="#CED7E7">联系内容</td>
            </tr>
		  <%
		  
		  if not rs.eof then
		  do while not rs.eof and RowCount>0
		  sqlc="select com_id,com_name,com_email from comp_info where  com_id="&rs("com_id")&" "
		  set rsc=conn.execute(sqlc)
		  if not rsc.eof then
			  com_name=rsc("com_name")
			  com_email=rsc("com_email")
		  end if
		  %>
          <%
		  sqlm="select com_id from crm_assign_out where com_id="&request("com_id")&" and telid="&rs("id")&" and teltype=1" 
		  set rsm=conn.execute(sqlm)
		  if not rsm.eof or not rsm.bof then
		  %>
          <tr>
            <td height="2" colspan="12" bgcolor="#FF9900">
            </td>
          </tr>
          <%
		  end if
		  rsm.close
		  set rsm=nothing
		  %>
          <tr>
            <td bgcolor="#FFFFFF"><%=rs("teldate")%></td>
            <td bgcolor="#FFFFFF"><%call shows("cate_contact_about",rs("contacttype"))%></td>
            <td bgcolor="#FFFFFF">
			<%
			call selet_("realname","users","id",rs("personid"))
			%></td>
            <td bgcolor="#FFFFFF">
			<%
			if rs("com_rank")<>"-1" then
			response.Write(rs("com_rank")&"星")
			end if
			 %>			</td>
            <td bgcolor="#FFFFFF"><%=rs("detail")%>
            <%if session("userid")="10" then%>
			<a href="?del=1&com_id=<%=rs("com_id")%>&id=<%=rs("id")%>">删除</a>
			<%end if%>	
            </td>
            </tr>
         
		  <%
		  rs.movenext
		  RowCount=RowCount-1
		  loop
		  end if
		  %>
		   <tr>
            <td colspan="5" align="right" bgcolor="#FFFFFF"><!-- #include file="../include/page.asp" --></td>
            </tr>
</table>
</body>
</html>
