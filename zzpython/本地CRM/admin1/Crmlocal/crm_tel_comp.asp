<!-- #include file="../include/adfsfs!@#.asp" -->
<!-- #include file="../../cn/function.asp" -->
<!-- #include file="../include/include.asp" -->
<%
if request("del")="1" then
	sqldel="delete from comp_tel where id="&request("id")
	conn.execute(sqldel)
	response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
end if
%>
<html>
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
sqlc="select com_id,com_name,com_email from comp_info where  com_id="&request("com_id")&" "
		  set rsc=conn.execute(sqlc)
		  if not rsc.eof then
		  com_name=rsc("com_name")
		  com_email=rsc("com_email")
		  end if
		telflag=request.QueryString("telflag")
		if telflag="" then telflag=0
		sear=sear&"adminuser="&request.QueryString("adminuser")&"&contacttype="&request.QueryString("contacttype")&"&com_id="&request.QueryString("com_id")&"&telflag="&telflag
		sql="select com_id,id,teldate,contacttype,personid,com_rank,detail,case1,case2,case3,contactnext_time from comp_tel where  com_id="&request("com_id")&" "
		if telflag=0 then
			sql=sql&" and telflag<4"
		else
			sql=sql&" and telflag="&telflag&""
		end if
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
            <td width="200" height="30" align="center" class="<%if telflag="0" then response.Write("readon")%>"><a href="?com_id=<%=request("com_id")%>&telflag=0">再生通销售电话记录</a></td>
            <!--<td width="200" align="center" class="<%if telflag="2" then response.Write("readon")%>"><a href="?com_id=<%=request("com_id")%>&telflag=2">再生通<b>续签</b>销售电话记录</a></td>-->
            <td width="200" align="center" class="<%if telflag="4" then response.Write("readon")%>"><a href="?com_id=<%=request("com_id")%>&telflag=4"><b>VAP</b>销售电话记录</a></td>
            <td width="200" align="center" class="<%if telflag="5" then response.Write("readon")%>"><a href="?com_id=<%=request("com_id")%>&telflag=5"><b>SMS</b>销售电话记录</a></td>
            <td width="200" align="center"><a href="http://admin1949.zz91.com/web/zz91/salecrm/viewInfo.htm?companyId=<%=request("com_id")%>&email=<%=com_email%>" target="_blank"><b>CS</b>服务记录</a></td>
			<td width="200" align="center"><a href="http://admin1949.zz91.com/web/zz91/trust/myLog.htm?companyId=<%=request("com_id")%>" target="_blank">交易组电话记录</a></td>
			<td width="200" align="center"><a href="/admin1/Crmlocal/seo/seo_dolist.asp?com_id=<%=request("com_id")%>" target="_blank">SEO小计</a></td>
			<td width="150" align="center" bgcolor="#CCCCCC">
            <%
			sqlz="select count(0) from crm_zgtel_content where com_id="&request("com_id")&" and read_check=0"
			set rsz=conn.execute(sqlz)
			if not rsz.eof or not rsz.bof then
				zgcount=rsz(0)
			end if
			rsz.close
			set rsz=nothing
			rn=10
			%>
            <a href="crm_zgtel_comp.asp?com_id=<%=request("com_id")%>">主管建议</a>(<font color="#FF0000"><%=zgcount%></font>)</td>
            <td width="200" align="center"><a href="http://admin1949.zz91.com/reborn-admin/sms/stat/manageSMSLog.htm?companyId=<%=request("com_id")%>" target="_blank">短信发送记录</a></td>
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
            <td nowrap bgcolor="#CED7E7">下次联系</td>
            <td nowrap bgcolor="#CED7E7">联系人</td>
            <td nowrap bgcolor="#CED7E7">客户等级</td>
            <td nowrap bgcolor="#CED7E7">联系内容</td>
            <td nowrap bgcolor="#CED7E7">录音</td>
            <!--<td nowrap bgcolor="#CED7E7">客户态度</td>
            <td nowrap bgcolor="#CED7E7">客户状况</td>
            <td nowrap bgcolor="#CED7E7">服务介绍</td>
            <td nowrap bgcolor="#CED7E7">跟进方式</td>
            <td nowrap bgcolor="#CED7E7">催单方式</td>
            <td nowrap bgcolor="#CED7E7">产品资料</td>
            <td nowrap bgcolor="#CED7E7">汇款帐号</td>-->
            </tr>
		  <%
		  
		  if not rs.eof then
		  do while not rs.eof and RowCount>0
		  
		  sqlt="select * from Crm_compContactInfo where com_id="&rs("com_id")&" and telid="&rs("id")&""
		  set rst=conn.execute(sqlt)
		  if not rst.eof then
		  	c_NoContact=rst("c_NoContact")
		  	C_CustomTaidu=rst("C_CustomTaidu")
			C_CustomStation=rst("C_CustomStation")
			C_ServerIntro=rst("C_ServerIntro")
			C_ServerType=rst("C_ServerType")
			C_ServerGo=rst("C_ServerGo")
			C_ServerTo=rst("C_ServerTo")
			C_PayType=rst("C_PayType")
		  else
		  	c_NoContact="0"
		  	C_CustomTaidu="0"
			C_CustomStation="0"
			C_ServerIntro="0"
			C_ServerType="0"
			C_ServerGo="0"
			C_ServerTo="0"
			C_PayType="0"
		  end if
		  rst.close
		  set rst=nothing
		  %>
          <%
		  sqlm="select com_id from crm_assign_out where com_id="&request("com_id")&" and telid="&rs("id")&" and teltype=0" 
		  set rsm=conn.execute(sqlm)
		  if not rsm.eof or not rsm.bof then
		  %>
          <tr>
            <td height="2" colspan="14" bgcolor="#FF9900">
            </td>
          </tr>
          <%
		  end if
		  rsm.close
		  set rsm=nothing
		  %>
          <%
		  sqlm="select com_id from crm_assign_out where com_id="&request("com_id")&" and telid="&rs("id")&" and teltype=4" 
		  set rsm=conn.execute(sqlm)
		  if not rsm.eof or not rsm.bof then
		  %>
          <tr>
            <td height="2" colspan="14" bgcolor="#330099">
            </td>
          </tr>
          <%
		  end if
		  rsm.close
		  set rsm=nothing
		  %>
          <tr>
            <td bgcolor="#FFFFFF"><%=rs("teldate")%></td>
            <td bgcolor="#FFFFFF">
			<%'=c_NoContact%>
			<%if rs("contacttype")="13" then%>
			<%call shows("cate_contact_about",rs("contacttype"))%>
			<%else%>
			无效联系—<%call shows("cate_crmNoContact",c_NoContact)%>
			<%end if%>			</td>
            <td bgcolor="#FFFFFF"><%=rs("contactnext_time")%></td>
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
            <td bgcolor="#FFFFFF"><%=rs("detail")%><%=rs("case1")%><%=rs("case2")%><%=rs("case3")%></td>
            <td bgcolor="#FFFFFF">
            <%
				sqlt="select id,filepath from crm_callrecord where telid="&rs("id")&""
				set rst=conn.execute(sqlt)
				pp=1
				if not rst.eof or not rst.bof then
					while not rst.eof
					response.Write("<a href='"&luyinurl&rst("filepath")&".WAV' target=_blank>录音("&pp&")</a><br>")
					pp=pp+1
					rst.movenext
					wend
				end if
				rst.close
				set rst=nothing
				%><%if session("userid")="10" then%>
			<a href="?del=1&com_id=<%=rs("com_id")%>&id=<%=rs("id")%>">删除</a>
			<%end if%>
            </td>
            <!--<td bgcolor="#FFFFFF"><%'call shows("cate_crmTaidu",C_CustomTaidu)%></td>
            <td bgcolor="#FFFFFF"><%'call shows("cate_CrmCompStation",C_CustomStation)%></td>
            <td bgcolor="#FFFFFF"><%'call shows("cate_crmserverIntro",C_ServerIntro)%></td>
            <td bgcolor="#FFFFFF"><%'call shows("cate_crmServerType",C_ServerType)%></td>
            <td bgcolor="#FFFFFF"><%'call shows("cate_crmServerGo",C_ServerGo)%></td>
            <td bgcolor="#FFFFFF"><%'call shows("cate_crmServerTo",C_ServerTo)%></td>
            <td bgcolor="#FFFFFF"><%'call shows("Cate_CrmPayType",C_PayType)%></td>-->
            </tr>
          
		  <%
		  rs.movenext
		  RowCount=RowCount-1
		  loop
		  end if
		  %>
          
		   <tr>
            <td colspan="14" align="right" bgcolor="#FFFFFF"><!-- #include file="../include/page.asp" --></td>
            </tr>
</table>
          
</body>
</html>
<%
conn.close
set conn=nothing
%>
