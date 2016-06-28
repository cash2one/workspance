<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!-- #include file="../../../cn/function.asp" -->
<!-- #include file="../../include/include.asp" -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>日报详细信息</title>
<link href="../../main.css" rel="stylesheet" type="text/css">
<link href="../../color.css" rel="stylesheet" type="text/css">
<script language="javascript" src="../../include/calendar.js"></script>
<script>
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
            <td width="130" height="23" nowrap background="../../newimages/shale_fill_1.gif"><img src="../../newimages/shale_arrowhead.gif" width="28" height="23" align="absmiddle">日报详情</td>
            <td width="21" background="../../newimages/shale_fill_1.gif"><img src="../../newimages/shale_photo.gif" width="21" height="23"></td>
            <td background="../../newimages/lm_ddd.jpg">&nbsp;</td>
          </tr>
        </table>
          </td>
      </tr>
	  <%
		actontype=request("actontype")
		sql="select * from crm_diary where id="&request("diaryid")
		set rs=conn.execute(sql)
		writeDate=rs("fDate")
		total_today=rs("total_today")
		total_today1=rs("total_today1")
		star5cout=rs("star5cout")
		star4cout=rs("star4cout")
		total_tomo=rs("total_tomo")
		analysis_today=rs("analysis_today")
		share=rs("share")
		diaryid=rs("id")
		isDraft=rs("isDraft")
		haveReply=rs("haveReply")
		replyContent=rs("replycontent")
		analysis_sign=rs("analysis_sign")
		star4comp=rs("star4comp")
		star5comp=rs("star5comp")
		coutzdcomp=rs("coutzdcomp")
		coutzd=rs("coutzd")
		personid=rs("personid")
		curdate=writeDate
		replyperson=rs("replyperson")
		rs.close
		if replyperson<>"" then
			sqlp="select realname from users where id="&replyperson&""
			set rsp=conn.execute(sqlp)
			if not rsp.eof or not rsp.bof then
				replyname=rsp(0)
			end if
			rsp.close
			set rsp=nothing
		end if
		%>
      <tr>
        <td align="center" valign="top" bgcolor="E7EBDE">
        <table width="80%"  border="0" align="center" cellpadding="2" cellspacing="0">
          <tr>
            <td align="center"><h4><%=writeDate %>的日报</h4><input name="writeDate" type="hidden" value="<%= writeDate %>"></td>
          </tr>
        </table>
		<input type="button" name="Submit3" value="返回" onClick="history.back()">
		<form name="form2" method="post" action="">
		  <table width="90%" border="0" cellpadding="4" cellspacing="1" bgcolor="#CCCCCC">
            <tr>
              <td width="50%" bgcolor="#66CC00"><span class="STYLE8">今日联系情况</span></td>
              <td width="50%" bgcolor="#66CC00"><span class="STYLE8">有效联系量:<%= total_today %>个</span><input type="hidden" name="Total_today" value="<%= total %>"> 
              | 无效联系量 <%= total_today1 %><input type="hidden" name="Total_today1" value="<%= Total_today1 %>"></td>
            </tr>
            <% 		
			
			'对应星级人数
			sql1="select rank1,rank2,rank3,rank4,rank5 from crm_rankTotal where diaryid="&diaryid&" and todayOrTomo=0"
			
			set rs1=conn.execute(sql1)
			rank1=rs1("rank1")
			rank2=rs1("rank2")
			rank3=rs1("rank3")
			rank4=rs1("rank4")
			rank5=rs1("rank5")
			for r=5 to 1 step -1
			rankValue=rs1("rank"&r)
			if isnull(rankvalue) then 
				rankValue=0
				tRank=0
			else
				arrRank=split(rankValue,",")
				tRank=ubound(arrRank)+1
			end if
			if r<=2 then
				styletr="style='display:none'"
			else
				styletr=""
			end if
			%>
			<input type="hidden" name="rank<%= r %>_today" value="<%= rankValue%>">
            <tr <%=styletr%>>
              <td colspan="2" bgcolor="#CBFFB9"><strong><%= r %>星级 (<%= tRank %>个)</strong></td>
            </tr>
            <%
			'对应星级明细
			sql2="select com_name,com_email,com_id from temp_salescomp where com_id in ("&rankValue&")"	
			'response.write sql2
			set rs2=conn.execute(sql2)
			if not (rs2.bof and rs2.eof) then
				j=1
				while not rs2.eof
					%>
					
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
			next 
			rs1.close
			set rs1=nothing
			'今日签单客户
			 %>
            <!--<tr>
              <td bgcolor="#66CC00" class="STYLE8" colspan="2">今日签单客户及分析</td>
              
            </tr>
<tr><td bgcolor="#ffffff" colspan="2"><textarea name="analysis_sign" cols="50" rows="5"><%= analysis_sign %></textarea></td>
            </tr>-->
<tr><td bgcolor="#66CC00" class="STYLE8" colspan="2">开发量</td></tr>
			<tr>
			  <td bgcolor="#ffffff" colspan="2">
              
              4星（<%=star4cout%>） 5星（<%=star5cout%>）</td></tr>
            
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
					<input type="hidden" name="rank<%= r %>_today" value="<%= rs2("com_id") %>">
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
			sql2="select com_name,com_email,com_id from v_salescomp where  com_id in ("&star5comp&")" 	
			set rs2=conn.execute(sql2)
			if not (rs2.bof and rs2.eof) then
				j=1
				while not rs2.eof
					%>
					<input type="hidden" name="rank<%= r %>_today" value="<%= rs2("com_id") %>">
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
				If j mod 2 = 0 then response.write "<td bgColor='#eeeeee'>&nbsp;</td></tr>" 	
			end if
			rs2.close
			set rs2=nothing
			end if
			%>
			<tr>
              <td bgcolor="#66CC00" class="STYLE8">明日客户准备</td>
              <td bgcolor="#66CC00" class="STYLE8"><%= total_Tomo %>个</td>
            </tr>
            <% 		
			
			'对应星级人数
			sql1="select rank1,rank2,rank3,rank4,rank5 from crm_rankTotal where diaryid="&diaryid&" and todayOrTomo=1"
			
			set rs1=conn.execute(sql1)
			if not rs1.eof or not rs1.bof then
			rank1=rs1("rank1")
			rank2=rs1("rank2")
			rank3=rs1("rank3")
			rank4=rs1("rank4")
			rank5=rs1("rank5")
			for r=5 to 1 step -1
			rankValue=rs1("rank"&r)
			if isnull(rankvalue) then 
				rankValue=0
				tRank=0
			else
				arrRank=split(rankValue,",")
				tRank=ubound(arrRank)+1
			end if
			if r<=2 then
				styletr="style='display:none'"
			else
				styletr=""
			end if
			%>
			<input type="hidden" name="rank<%= r %>_tomo" value="<%= rankValue %>">
            <tr <%=styletr%>>
              <td colspan="2" bgcolor="#CBFFB9"><strong><%= r %>星级 (<%= tRank %>个)</strong></td>
            </tr>
            <%
			'对应星级明细
			sql2="select com_name,com_email,com_id from temp_salescomp where com_id in ("&rankValue&")"	
			'response.write sql2
			set rs2=conn.execute(sql2)
			if not (rs2.bof and rs2.eof) then
				j=1
				while not rs2.eof
					%>
					
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
			next
			
			end if
			rs1.close
			set rs1=nothing 
			
			%>
           
            <tr>
              <td bgcolor="#66CC00" class="STYLE8">重点客户准备</td>
              <td bgcolor="#66CC00" class="STYLE8"><%=coutzd%>个</td>
              </tr>
			  <%
			if coutzdcomp<>"" then
			sql2="select com_name,com_email,com_id from v_salescomp where com_id in ("&coutzdcomp&")" 	
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
				If j mod 2 = 0 then response.write "<td bgColor='#eeeeee'>&nbsp;</td></tr>" 	
			end if
			rs2.close
			set rs2=nothing
			end if
			%>
            <!--<tr>
              <td colspan="2" bgcolor="#66CC00" class="STYLE8">今日客户分析</td>
              </tr>
			  <tr >
              <td colspan="2" bgcolor="#FFFFFF">
                <textarea name="Analysis_today" cols="50" rows="5" ><% = Analysis_today%></textarea>
              </td>
            </tr>-->
            <tr>
              <td colspan="2" bgcolor="#66CC00" class="STYLE8">希望获得的帮助或成长点</td>
            </tr>
            <tr >
              <td colspan="2" bgcolor="#FFFFFF">
                <%If actontype="look" Then%>
                <%= share %>
                <%else%>
                <textarea name="share" cols="50" rows="5"><%= share %></textarea>
                <%end if%>
              </td>
            </tr>
			<%If actontype="back" Then%>
			<tr>
              <td colspan="2" bgcolor="#FF9900" class="STYLE8">回复内容</td>
            </tr>
            <tr >
              <td colspan="2" bgcolor="#FFFFFF">
                <textarea cols="50" rows="5" name="replyContent"><%= replyContent %></textarea>
              </td>
            </tr><tr>
              <td colspan="2" bgcolor="#FFFFFF" class="STYLE8">回复者：<%=replyname%></td>
            </tr>
            <%End If %>
            <%If actontype="look" and haveReply="1" Then%>
            
            <tr>
              <td colspan="2" bgcolor="#FF9900" class="STYLE8">回复内容</td>
            </tr>
            <tr >
              <td colspan="2" bgcolor="#FFFFFF">
               <%
			   if replyContent<>"" then
			   response.Write(replyContent)
			   end if
			   %>
              </td>
            </tr>
            <%End If %>
            <%If actontype<>"look" Then%>
            <tr >
              <td colspan="2" align="center" bgcolor="#FFFFFF"><input type="hidden" name="diaryid" id="diaryid" value="<%=request("diaryid")%>">
                <input type="hidden" name="writeDate" value="<%= writeDate %>"><input type="button" name="Submit2" class="button" value=" 提 交 " onClick="saveDraft(this.form,1)">
                <input type="hidden" name="personid" id="personid" value="<%=personid%>">
                <input name="actontype" type="hidden" id="actontype" value="<%=actontype%>"></td>
            </tr>
			<%end if%>

          </table>
            <p>
              <input type="button" name="Submit3" value="返回" onClick="history.back()">
              <a href="my_diary_reply.asp?personid=<%=personid%>" target="_blank">查看所有日报回复</a></p>
		</form>

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
