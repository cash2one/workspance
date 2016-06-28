<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!-- #include file="../../../cn/function.asp" -->
<!-- #include file="../../include/include.asp" -->

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>无标题文档</title>
<link href="../../css.css" rel="stylesheet" type="text/css">
<link href="../../inc/Style.css" rel="stylesheet" type="text/css">

<script language="javascript" src="../../include/calendar.js"></script>
<script>
function plus(id){

if (document.all(id).style.display=="none")
	{
	document.all(id).style.display=""
	
	}
else
	{
	document.all(id).style.display="none"

	}
}
</script>
<style type="text/css">
<!--
.STYLE6 {color: #FFFFFF}
.STYLE7 {color: #FF6600}
.STYLE10 {
	font-size: 14px;
	color: #FF0000;
	font-weight: bold;
}
-->
</style>
</head>

<body bgcolor="7386A5" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<table width="100%" height="95%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="6">
	</td>
  </tr>
  <tr>
    <td height="100%" valign="top"><table width="98%" height="100%" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td height="23" bgcolor="E7EBDE"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="130" height="23" nowrap background="../../newimages/shale_fill_1.gif"><img src="../../newimages/shale_arrowhead.gif" width="28" height="23" align="absmiddle">我的客户统计</td>
            <td width="21" background="../../newimages/shale_fill_1.gif"><img src="../../newimages/shale_photo.gif" width="21" height="23"></td>
            <td background="../../newimages/lm_ddd.jpg">&nbsp;</td>
          </tr>
        </table>
          </td>
      </tr>
      <tr>
        <td align="center" valign="top" bgcolor="E7EBDE">
		  <br>
		  <br>
		  <table width="80%"  border="0" align="center" cellpadding="5" cellspacing="0">
          <tr>
            <td align="center">
			<%
			totalnocon=0
			nocontact=0
			tomoCon=0
			totalnevercon=0
			if request("to_date")="" then
			todate=date()
			else
			todate=cdate(request("to_date"))
			end if
			
			%>
<!--			<form name="form1" method="get" action="">
  时间到
    <input name="to_date" type="text" class="wenbenkuang" id="to_date" value="<%=todate%>" size="15">
    <a onClick="event.cancelBubble=true;" href="javascript:showCalendar('dimg2',false,'to_date')"><img id=dimg2 align=absmiddle width=34 height=21 src="../../newimages/button.gif" border=0></a> <STRONG>  </STRONG><STRONG>
    </STRONG>
    <input type="submit" name="Submit" value="搜索">
              </form>--></td>
          </tr>
        </table>
		<table width="95%"  border="0" align="center" id="mytab" cellpadding="3" cellspacing="1" bgcolor="#7386A5">
              <tr bgcolor="#FFFFFF">
                <td colspan="16" align="center" bgcolor="#7386A5"><span class="STYLE6">我的客户统计</span></td>
              </tr>
              <tr bgcolor="#FFFFFF">
                <td width="100" bgcolor="#F2F2F2">&nbsp;</td>
				<%
				set rsstar=server.CreateObject("ADODB.recordset")
				sqlstar="select code from cate_kh_csd order by id desc"
				rsstar.open sqlstar,conn,1,2
				if not rsstar.eof then
				do while not rsstar.eof 
				%>
                <td align="center"><%=rsstar(0)%>星</td>
				<%
				rsstar.movenext
				loop
				end if
				rsstar.close
				set rsstar=nothing
				%>
				<!--<td align="center" rowspan="2">安排但<br>未联系</td>-->
				<td align="center" bgcolor="#FFFF00">合计</td>
				<td align="center" >从未联系</td>
				<td align="center" >安排但<br>未联系</td>
				<td align="center" >明日安<br>排联系</td>
              </tr>
              
			  <%
			  	dim t(5)
			  	sqlTemp="select count(0) from v_salescomp WHERE not EXISTS (select com_id from Agent_ClientCompany where com_id=v_salescomp.com_id) "
				sqlTemp=sqlTemp&" and not EXISTS(select null from comp_sales where com_type=13 and com_id=v_salescomp.com_id) "
				sqlTemp=sqlTemp&" and not EXISTS(select null from comp_sales where com_Especial=1 and com_id=v_salescomp.com_id)" 
				
			    sqlu="select realname,id from users where closeflag=1 and adminuserid<>''"
				set rsu=server.CreateObject("ADODB.recordset")
				rsu.open sqlu,conn,1,2
				
				if not rsu.eof then
				
				do while not rsu.eof
				totaluser=0
				%>
		      
		      <tr bgcolor="#FFFFFF">
            <td align="left" bgcolor="#f2f2f2"><%=rsu("realname")%></td>
				<%
				set rsstar=server.CreateObject("ADODB.recordset")
				sqlstar="select code from cate_kh_csd order by id desc"
				rsstar.open sqlstar,conn,1,2
				if not rsstar.eof then
				ii=1
				do while not rsstar.eof 
				%>

            <td align="center">
			<%
			'*****中文
			
			sql=sqlTemp&" and personid="&rsu("id")
			sql=sql&" and com_rank='"&rsstar("code")&"'"
			sql=sql&" and not EXISTS(select null from Comp_ZSTinfo where com_id=v_salescomp.com_id)"
			set rs=conn.execute(sql)
			totaluser=totaluser+rs(0)
			t(ii)=t(ii)+rs(0)
			%>
			<% If session("userid")<>"10" and session("personid")<>"32" Then %>
				<% If rsu("id")=session("personid") Then %>
					<a href="../crm_allcomp_list.asp?dotype=my&com_rank=<%= rsstar(0) %>"><%=rs(0)%></a></td>
				<% Else %>
					<%=rs(0)%>
				<% End If %>
			<% Else %>
				<a href="../crm_allcomp_list.asp?dotype=my&doperson=<%= rsu("id") %>&com_rank=<%= rsstar(0) %>"><%=rs(0)%></a></td>
			<% End If %>
			</td>
			<%
				rs.close
				rsstar.movenext
				ii=ii+1
				loop
				end if
				rsstar.close
				set rsstar=nothing
				%>
			<td bgcolor="#FFFF00" align="center"><%=totalUser%></td>
			
			<%
			sql=sqlTemp&" and not EXISTS(select null from Comp_ZSTinfo where com_id=v_salescomp.com_id) and personid="&rsu("id")&"  and not EXISTS (select null from v_groupcomid where  com_id=v_salescomp.com_id)" 
			set rs=conn.execute(sql)
			totalnevercon=totalnevercon+rs(0)
			
			%>
			<td align="center">
			<% If session("userid")<>"10" and session("personid")<>"32" Then %>
				<% If rsu("id")=session("personid") Then %>
					<a href="../crm_allcomp_list.asp?dotype=mynocontact&datetime=<%=date()%>"><%=rs(0)%></a></td>
				<% Else %>
					<%=rs(0)%>
				<% End If %>
			<% Else %>
				<a href="../crm_allcomp_list.asp?dotype=mynocontact&datetime=<%=date()%>&doperson=<%= rsu("id") %>"><%=rs(0)%></a></td>
			<% End If %>
			</td>
			<% rs.close %>
			<% 
			sql=sqlTemp&" and personid="&rsu("id")
			sql=sql&" and contactnext_time<'"&date()&"' and contactnext_time<>'1900-1-1'"
			set rs=conn.execute(sql)
			totalnocon=totalnocon+rs(0)
			 %>
			
			<td align="center">
			<% If session("userid")<>"10" and session("personid")<>"32" Then %>
				<% If rsu("id")=session("personid") Then %>
					<a href="../crm_allcomp_list.asp?dotype=contact&datetime=<%=date()%>"><%=rs(0)%></a></td>
				<% Else %>
					<%=rs(0)%>
				<% End If %>
			<% Else %>
				<a href="../crm_allcomp_list.asp?dotype=contact&datetime=<%=date()%>&doperson=<%= rsu("id") %>"><%=rs(0)%></a></td>
			<% End If %>
			</td>
			<% rs.close %>
			<td align="center">
				<% 			
				sqlTomo=" and contactnext_time >='"&date()+1&"' and contactnext_time<='"&date()+2&"'"
			'明日总数
		sql0="select count(0) from v_salescomp where personid="&rsu("id")&sqlTomo&"" 
				
				set rs0=conn.execute(sql0)
				%>
				<% If session("userid")<>"10" and session("personid")<>"32" Then %>
					<% If rsu("id")=session("personid") Then %>
						<a href="../crm_allcomp_list.asp?personid=<%=rsu("id")%>&datetime=<%=date()%>&dotype=tomocontact"><%=rs0(0)%></a>
					<% Else %>
						<%=rs0(0)%>
					<% End If%>
				<% Else %>
				<a href="../crm_allcomp_list.asp?personid=<%=rsu("id")%>&datetime=<%=date()%>&dotype=tomocontact"><%=rs0(0)%></a></td>
			<% End If 
			
				tomoCon=tomoCon+rs0(0)
				rs0.close
				set rs0=nothing%>				</td>
            </tr>
		  
		  <%
		        rsu.movenext

				loop
				end if
				rsu.close()
				set rsu=nothing
				%>
          <tr bgcolor="#FFFFFF">
            <td align="center" nowrap bgcolor="#f2f2f2">合计</td>
			<%
			
			for j=1 to 5
			%>
            <td align="center"><%=t(j)%></td>
				
				<%
				totalstar=totalstar+t(j)
				next
				%>
				<td align="center" bgcolor="#FFFF00"><%= totalstar %></td>
				<td align="center"><%= totalnevercon %></td>
				<td align="center"><%= totalnocon %></td>
				<td align="center"><%= tomoCon %></td>
            </tr>
        </table>
		<br></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="40" align="center" bgcolor="#FFFFFF">
	<script>
	function gettab()
	{
	var obj=document.getElementById("mytab");
	var objinfo=document.getElementById("tabinfo");
	objinfo.value=obj.outerHTML
	form1.submit()
	//alert (obj.outerHTML)
	}
	</script>
	<%
	sql="select id from crm_tel_content where sdate='"&date()&"'"
	set rs=conn.execute(sql)
	if not rs.eof then
	response.Write("今天的客户信息已经保持，是否重新保存今天客户信息")
	else
	response.Write("今天的客户信息还没有保存！请及时保存")
	end if
	rs.close
	set rs=nothing
	%>
      <form name="form1" method="post" action="tel_tongji_save.asp">
	    <input name="tabinfo" type="hidden" id="tabinfo">
	    <input type="button" value="保存该天的信息" onClick="gettab()">&nbsp;&nbsp;&nbsp;&nbsp;
        <input type="button" value="查看所有统计" onClick="window.location='tel_tongji_list.asp'">    
        </form></td>
  </tr>
</table>
</body>
</html>
<%
conn.close
set conn=nothing
%>
