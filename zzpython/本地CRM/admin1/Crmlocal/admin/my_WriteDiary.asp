<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!-- #include file="../../../cn/function.asp" -->
<!-- #include file="../../include/include.asp" -->

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>写日报</title>
<link href="../../main.css" rel="stylesheet" type="text/css">
<link href="../../color.css" rel="stylesheet" type="text/css">

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
function saveDraft(f,d){
	f.action="my_writeDiarySave.asp?isDraft="+d;
	f.submit();
}
</script>
<style type="text/css">
<!--
.STYLE8 {color: #000; font-weight: bold; }
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
            <td width="130" height="23" nowrap background="../../newimages/shale_fill_1.gif"><img src="../../newimages/shale_arrowhead.gif" width="28" height="23" align="absmiddle">日报管理</td>
            <td width="21" background="../../newimages/shale_fill_1.gif"><img src="../../newimages/shale_photo.gif" width="21" height="23"></td>
            <td background="../../newimages/lm_ddd.jpg">&nbsp;</td>
          </tr>
        </table>
          </td>
      </tr>
	  <%
	  		menuid=1
			if request("curdate")="" then
				curdate=date()
			else
				curdate=cdate(request("curdate"))
			end if
			sqlTemp=""
			if curdate<>"" then
				sqlTemp=sqlTemp&" and LastTelDate>='"&curdate&"' and LastTelDate<='"&curdate+1&"'"
			end if
			%>
      <tr>
        <td align="center" valign="top" bgcolor="E7EBDE"><table width="80%"  border="0" align="center" cellpadding="2" cellspacing="0">
          <tr>
            <td align="center"><!--#include file="my_diary_menu.asp"--></td>
          </tr>
        </table>
          <br>
          
            
            <%
			notijiao=0
if session("userClass")="1" or session("userID")="10" then
else
	sqla=" and personid="&session("personid")&" and not EXISTS(select com_id from comp_sales where com_type=13 and com_id=v_salescomp.com_id) and not EXISTS(select com_id from comp_sales where com_Especial=1 and com_id=v_salescomp.com_id)"
	sql="select top 10 com_id,com_email,com_name from v_salescomp where contactnext_time>'"&date()&"' and contactnext_time<'"&date()+1&"' "&sqla
	set rs=conn.execute(sql)
	if not rs.eof then
		notijiao=1
	%>
    <table width="90%" border="0" cellspacing="1" cellpadding="3" bgcolor="#999999">
    <tr><td bgcolor="#FFFF99">
    <%
		response.Write("你今天安排联系的客户还没有处理好，不能题写日报！<br>以下下是你未处理的客户信息<br>")
		%>
        </td></tr>
        <%
		while not rs.eof
		%>
        <tr>
              <td bgcolor="#FFFFFF">
		<%
			response.Write("<a href='/admin1/crmlocal/crm_cominfoedit.asp?idprod="&rs("com_id")&"&dotype=my' target='_blank'>"&rs("com_name")&rs("com_email")&"</a><br>")
		%>
    	</td></tr>
    	<%	
		rs.movenext
		wend
		%>
    </table>
    <%	
	end if
	rs.close
	set rs=nothing
	
	if weekday(date()+1)=7 or weekday(date()+1)=1 then
	
	else
		sql="select top 1 com_id from v_salescomp where contactnext_time>'"&date()+1&"' and contactnext_time<'"&date()+2&"' "&sqla
		set rs=conn.execute(sql)
		if rs.eof then
			response.Write("你还没有安排明天联系的客户，不能题写日报！<br>")
			notijiao=1
			response.end()
		end if
		rs.close
		set rs=nothing
	end if
end if
%>
            
          <br>

		<% 			
		'取得总联系量
		writeDate=date()
		personid=session("personid")
		'---------------------无效联系量
		sqlw="select count(0) from v_salescomp where personid="&personid&" and exists(select com_id from comp_tel where com_id=v_salescomp.com_id and contactType=12 and teldate>'"&curdate&"' and teldate<'"&curdate+1&"' and personid="&personid&")"
		'response.Write(sqlw)
		set rsw=conn.execute(sqlw)
		wtotal=rsw(0)
		rsw.close
		set rsw=nothing
		'---------------------有效联系量
		sql="select count(0) from v_salescomp where personid="&personid&" "&sqlTemp&"  and exists(select com_id from comp_tel where com_id=v_salescomp.com_id and contactType=13 and teldate>'"&curdate&"' and teldate<'"&curdate+1&"' and personid="&personid&")"
		'response.write sql
		set rs=conn.execute(sql)
		if not (rs.bof and rs.eof) then
			total=rs(0)

		%>
		<form name="form2" method="post" action="">
		  <table width="90%" border="0" cellpadding="4" cellspacing="1" bgcolor="#CCCCCC">
            <tr>
              <td width="50%" bgcolor="#66CC00"><span class="STYLE8">今日联系情况</span></td>
              <td width="50%" bgcolor="#66CC00">有效联系量:<a href="/admin1/crmlocal/admin/admin_tel_comp1.asp?lo=1&personid=<%=personid%>&star=&contacttype=13&fromdate=<%=curdate%>&todate=<%=curdate%>&dotype=" target="_blank"><%= total %></a>个<input type="hidden" name="Total_today" value="<%= total %>"> |  无效联系量:<input type="hidden" name="Total_today1" value="<%= wtotal %>"><a href="/admin1/crmlocal/admin/admin_tel_comp1.asp?lo=1&personid=<%=personid%>&star=&contacttype=12&fromdate=<%=curdate%>&todate=<%=curdate%>&dotype=" target="_blank"><%= wtotal %></a>个 </td>
            </tr>
            <% 		
			for r=5 to 1 step -1
			'对应星级人数
			sql1="select count(0) from v_salescomp where personid="&personid&" "&sqlTemp&"  and contactType=13 and com_rank="&r&""
			set rs1=conn.execute(sql1)
			rankTotal=rs1(0)
			if r<=2 then
				styletr="style='display:none'"
			else
				styletr=""
			end if
			%>
            <tr <%=styletr%>>
              <td colspan="2" bgcolor="#CBFFB9"><strong><%= r %>星级 (<a href="/admin1/crmlocal/admin/admin_tel_comp1.asp?lo=1&personid=<%=personid%>&star=<%=r%>&contacttype=13&fromdate=<%=curdate%>&todate=<%=curdate%>&dotype=" target="_blank"><%= rankTotal %></a>个)</strong></td>
            </tr>
            <%
			rs1.close
			set rs1=nothing
			'对应星级明细
		
			sql2="select com_name,com_email,com_id from v_salescomp where personid="&personid&" "&sqlTemp&" and contactType=13 and com_rank="&r&"" 	
			'response.write sql2
			set rs2=conn.execute(sql2)
			if not (rs2.bof and rs2.eof) then
				j=1
				while not rs2.eof
					%>
					<input type="hidden" name="rank<%= r %>_today" value="<%= rs2("com_id") %>">
					<%
					if r>=3 then
						if j mod 2 = 1 then
							trColor="#ffffff"
						else
							trColor="#eeeeee"
						end if
						%>
					<% If j mod 2 = 1 then response.write "<tr>" %>						
						  <td width='50%' bgColor="<%= trColor %>"><a href="../modaldealog_body.asp?../crmlocal/crm_cominfoedit.asp?idprod=<%=rs2("com_id")%>" target="_blank"><%= rs2("com_name") %> (<%= rs2("com_email") %>)</a></td>
						<% If j mod 2 = 0 then response.write "</tr>" %>	
					<% 
					end if
					rs2.movenext
					j=j+1
				wend 
				If j mod 2 = 0  and r>=3 then response.write "<td bgColor='#eeeeee'>&nbsp;</td></tr>" 	
			end if
			rs2.close
			set rs2=nothing
			%>
            <% next %>
            <tr><td bgcolor="#66CC00" class="STYLE8" colspan="2">开发量</td></tr>
			<tr>
			  <td bgcolor="#ffffff" colspan="2">
              <%
	'---------统计转5星客户
	sqlt=""
	sqlTemp=""
	sqlTemp=sqlTemp&" and not EXISTS(select null from comp_sales where com_type=13 and com_id=crm_to5star.com_id) "
	'sqlTemp=sqlTemp&" and not EXISTS(select null from Comp_ZSTinfo where com_id=crm_to5star.com_id)"
	sqlt=sqlt&sqlTemp
	sqlt=sqlt&" and fdate>='"&curdate&"'"
	sqlt=sqlt&" and fdate<='"&curdate+1&"'"
	cout5=0
	star5comp=""
	sqlc="select com_id from v_salescomp where com_id in (select com_id from  crm_to5star where personid="&personid&sqlt&") "
	set rsc=conn.execute(sqlc)
	if not rsc.eof or not rsc.bof then
		while not rsc.eof
			cout5=cout5+1
			star5comp=star5comp&rsc(0)&","
		rsc.movenext
		wend
		star5comp=left(star5comp,len(star5comp)-1)
	end if
	rsc.close
	set rsc=nothing
	'---------统计转4星客户
	sqlt=""
	sqlTemp=""
	sqlTemp=sqlTemp&" and not EXISTS(select null from comp_sales where com_type=13 and com_id=crm_to4star.com_id) "
	'sqlTemp=sqlTemp&" and not EXISTS(select null from Comp_ZSTinfo where com_id=crm_to5star.com_id)"
	sqlt=sqlt&sqlTemp
	sqlt=sqlt&" and fdate>='"&curdate&"'"
	sqlt=sqlt&" and fdate<='"&curdate+1&"'"
	cout4=0
	star4comp=""
	sqlc="select com_id from v_salescomp where com_id in (select com_id from  crm_to4star where personid="&personid&sqlt&") "
	set rsc=conn.execute(sqlc)
	if not rsc.eof or not rsc.bof then
		while not rsc.eof
			cout4=cout4+1
			star4comp=star4comp&rsc(0)&","
		rsc.movenext
		wend
		star4comp=left(star4comp,len(star4comp)-1)
	end if
	rsc.close
	set rsc=nothing
	
			  %>
              4星（<%=cout4%>） 5星（<%=cout5%>）
              <input type="hidden" name="star4cout" id="star4cout" value="<%=cout4%>">
              <input type="hidden" name="star5cout" id="star5cout" value="<%=cout5%>">
              <input type="hidden" name="star4comp" id="star4comp" value="<%=star4comp%>">
              <input type="hidden" name="star5comp" id="star5comp" value="<%=star5comp%>">
              </td></tr>
            
			<%
			'对应星级明细
			if star4comp<>"" then
			%>
            <tr><td bgcolor="#66CC00" class="STYLE8" colspan="2">4星</td></tr>
            <%
			sql2="select com_name,com_email,com_id from v_salescomp where com_id in ("&star4comp&")" 	
			set rs2=conn.execute(sql2)
			if not (rs2.bof and rs2.eof) then
				j=1
				while not rs2.eof
					%>
					
					<%
						if j mod 2 = 1 then
							trColor="#ffffff"
						else
							trColor="#eeeeee"
						end if
						%>
					<% If j mod 2 = 1 then response.write "<tr>" %>						
						  <td width='50%' bgColor="<%= trColor %>"><a href="../modaldealog_body.asp?../crmlocal/crm_cominfoedit.asp?idprod=<%=rs2("com_id")%>" target="_blank"><%= rs2("com_name") %> (<%= rs2("com_email") %>)</a></td>
						<% If j mod 2 = 0 then response.write "</tr>" %>	
					<% 
					rs2.movenext
					j=j+1
				wend 
				If j mod 2 = 0  then response.write "<td bgColor='#eeeeee'>&nbsp;</td></tr>" 	
			end if
			rs2.close
			set rs2=nothing
			end if
			%>
            
			<%
			'对应星级明细
			if star5comp<>"" then
			%>
            <tr><td bgcolor="#66CC00" class="STYLE8" colspan="2">5星</td></tr>
            <%
			sql2="select com_name,com_email,com_id from v_salescomp where com_id in ("&star5comp&")" 	
			set rs2=conn.execute(sql2)
			if not (rs2.bof and rs2.eof) then
				j=1
				while not rs2.eof
					%>
					
					<%
						if j mod 2 = 1 then
							trColor="#ffffff"
						else
							trColor="#eeeeee"
						end if
						%>
					<% If j mod 2 = 1 then response.write "<tr>" %>						
						  <td width='50%' bgColor="<%= trColor %>"><a href="../modaldealog_body.asp?../crmlocal/crm_cominfoedit.asp?idprod=<%=rs2("com_id")%>" target="_blank"><%= rs2("com_name") %> (<%= rs2("com_email") %>)</a></td>
						<% If j mod 2 = 0 then response.write "</tr>" %>	
					<% 
					rs2.movenext
					j=j+1
				wend 
				If j mod 2 = 0  then response.write "<td bgColor='#eeeeee'>&nbsp;</td></tr>" 	
			end if
			rs2.close
			set rs2=nothing
			end if
			%>
            <!--<tr><td bgcolor="#66CC00" class="STYLE8" colspan="2">今日签单客户及分析</td></tr>
			<tr><td bgcolor="#ffffff" colspan="2"><textarea name="analysis_sign" cols="50" rows="2"></textarea></td></tr>-->
			
			<% 
			sqlTomo=" and contactnext_time >='"&date()+1&"' and contactnext_time<='"&date()+2&"'"
			'明日总数
		sql="select count(0) from v_salescomp where personid="&personid&" "&sqlTomo&""
		'response.write sql
		set rs=conn.execute(sql)
		if not (rs.bof and rs.eof) then
			totalTomo=rs(0)
		end if %>
			<tr>
              <td bgcolor="#66CC00" class="STYLE8"><p >明日客户准备</p></td>
              <td bgcolor="#66CC00" class="STYLE8"><a href="/admin1/crmlocal/admin/admin_tj_comp.asp?lo=1&personid=<%=personid%>&Tomotor=1&fromdate=<%=curdate%>&todate=<%=curdate%>&dotype=" target="_blank"><%= totalTomo %></a>个<input type="hidden" name="Total_tomo" value="<%= totalTomo %>"></td>
            </tr>
 <% 		
			for r=5 to 1 step -1
			'明日对应星级人数
			sql1="select count(0) from v_salescomp where personid="&personid&" "&sqlTomo&"  and com_rank="&r	
			'response.write sql1
			set rs1=conn.execute(sql1)
			rankTotal=rs1(0)
			if r<=2 then
				styletr="style='display:none'"
			else
				styletr=""
			end if
			%>
            <tr <%=styletr%>>
              <td colspan="2" bgcolor="#CBFFB9"><strong><%= r %>星级 (<a href="/admin1/crmlocal/admin/admin_tj_comp.asp?lo=1&personid=<%=personid%>&Tomotor=1&fromdate=<%=curdate%>&todate=<%=curdate%>&dotype=&star=<%=r%>" target="_blank"><%= rankTotal %></a>个)</strong></td>
            </tr>
            <%
			rs1.close
			set rs1=nothing
			'对应星级明细
			sql2="select com_name,com_email,com_id from v_salescomp where personid="&personid&" "&sqlTomo&" and com_rank="&r
			'response.write sql2
			set rs2=conn.execute(sql2)
			if not (rs2.bof and rs2.eof) then
				j=1
				while not rs2.eof
				%>
				<input type="hidden" name="rank<%= r %>_tomo" value="<%= rs2("com_id") %>">
				<%
				if r>=3 then
					if j mod 2 = 1 then
						trColor="#ffffff"
					else
						trColor="#eeeeee"
					end if
					%>
<% If j mod 2 = 1 then response.write "<tr>" %>						
						  <td width='50%' bgColor="<%= trColor %>"><a href="../modaldealog_body.asp?../crmlocal/crm_cominfoedit.asp?idprod=<%=rs2("com_id")%>" target="_blank"><%= rs2("com_name") %> (<%= rs2("com_email") %>)</a></td>
						<% If j mod 2 = 0 then response.write "</tr>" %>	
			<% 
				end if
				rs2.movenext
				j=j+1
				wend 
				If j mod 2 = 0  then response.write "<td bgColor='#eeeeee'>&nbsp;</td></tr>" 	
			end if
			rs2.close
			set rs2=nothing
			%>
            <% next %>
            <%
			'---------重点客户准备
	sqlt=""
	sqlTemp=""
	'sqlTemp=sqlTemp&" and not EXISTS(select null from comp_sales where com_type=13 and com_id=crm_category_info.com_id) "
	sqlt=sqlt&sqlTemp
	sqlt=sqlt&" and fdate>='"&curdate&"'"
	sqlt=sqlt&" and fdate<='"&curdate+1&"'"
	coutzd=0
	coutzdcomp=""
	sqlc="select com_id from v_salescomp where personid="&personid&" and exists(select property_id from crm_category_info where v_salescomp.com_id=property_id and property_value='10050002' "&sqlt&") "
	set rsc=conn.execute(sqlc)
	if not rsc.eof or not rsc.bof then
		while not rsc.eof
			coutzd=coutzd+1
			coutzdcomp=coutzdcomp&rsc(0)&","
		rsc.movenext
		wend
		coutzdcomp=left(coutzdcomp,len(coutzdcomp)-1)
	end if
	rsc.close
	set rsc=nothing
	%>
            <tr>
              <td bgcolor="#66CC00" class="STYLE8">重点客户准备</td>
              <td bgcolor="#66CC00" class="STYLE8"><input type="hidden" name="coutzd" id="coutzd" value="<%=coutzd%>"><%=coutzd%>个<input type="hidden" name="coutzdcomp" id="coutzdcomp" value="<%=coutzdcomp%>"></td>
              </tr>
			  <%
			if cstr(coutzdcomp)<>"" then
			sql2="select com_name,com_email,com_id from v_salescomp where com_id in ("&coutzdcomp&")"
			set rs2=conn.execute(sql2)
			if not rs2.bof or not rs2.eof then
				
				j=1
				while not rs2.eof
					%>
					
					<%
					
						if j mod 2 = 1 then
							trColor="#ffffff"
						else
							trColor="#eeeeee"
						end if
						%>
					<% If j mod 2 = 1 then response.write "<tr>" %>						
						  <td width='50%' bgColor="<%= trColor %>"><a href="../modaldealog_body.asp?../crmlocal/crm_cominfoedit.asp?idprod=<%=rs2("com_id")%>" target="_blank"><%= rs2("com_name") %> (<%= rs2("com_email") %>)</a></td>
					<% If j mod 2 = 0 then response.write "</tr>" %>	
					<% 
					
					rs2.movenext
					j=j+1
				wend 
				If j mod 2 = 0  and r>=3 then response.write "<td bgColor='#eeeeee'>&nbsp;</td></tr>" 	
			end if
			rs2.close
			set rs2=nothing
			end if
			%>
            <!--
            <tr>
              <td colspan="2" bgcolor="#66CC00" class="STYLE8">今日客户分析</td>
              </tr>
			  <tr >
              <td colspan="2" bgcolor="#FFFFFF">
                <textarea name="Analysis_today" cols="30" rows="5"></textarea>
              </td>
            </tr>-->
            <tr>
              <td colspan="2" bgcolor="#66CC00" class="STYLE8">希望获得的帮助或成长点</td>
            </tr>
            <tr >
              <td colspan="2" bgcolor="#FFFFFF">
                <textarea name="share" cols="50" rows="5"></textarea>
              </td>
            </tr>
            <tr >
              <td colspan="2" align="center" bgcolor="#FFFFFF">
              <input type="hidden" name="writeDate" value="<%= writeDate %>">
              <%if notijiao=0 then%>
              <input type="button" name="Submit2" class="button" value="提交日报" onClick="saveDraft(this.form,0)">
              <%end if%>
              <input name="actontype" type="hidden" id="actontype" value="add"></td>
            </tr>
          </table>
            </form>
		<% Else %>
		   <table width="90%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td>无数据!</td>
            </tr>
          </table>
		  <% End If 
		  rs.close
		set rs=nothing%>

		  </td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="6">	</td>
  </tr>
</table>
</body>
</html>
<%
conn.close
set conn=nothing
%>
