<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!-- #include file="../../../cn/function.asp" -->
<!-- #include file="../../include/include.asp" -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>无标题文档</title>
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
	  <%personid=47

		sql="select * from crm_diary where id="&request("diaryid")
		set rs=conn.execute(sql)
		writeDate=rs("fDate")
		total_today=rs("total_today")
		total_tomo=rs("total_tomo")
		analysis_today=rs("analysis_today")
		share=rs("share")
		diaryid=rs("id")
		isDraft=rs("isDraft")
		replyContent=rs("replyContent")
		analysis_sign=rs("analysis_sign")
		rs.close
			%>
      <tr>
        <td align="center" valign="top" bgcolor="E7EBDE">
        <table width="80%"  border="0" align="center" cellpadding="2" cellspacing="0">
          <tr>
            <td align="center"><h4><%=writeDate %>的日报</h4><input name="writeDate" type="hidden" value="<%= writeDate %>"></td>
          </tr>
        </table>
		<input type="button" name="Submit3" value="返回" onClick="history.back()">
		<form name="form2" method="post" action="saveReply.asp">
		  <table width="90%" border="0" cellpadding="4" cellspacing="1" bgcolor="#CCCCCC">
            <tr>
              <td width="50%" bgcolor="#66CC00"><span class="STYLE8">今日联系情况</span></td>
              <td width="50%" bgcolor="#66CC00"><span class="STYLE8">今日有效联系量:<%= total_today %>个</span><input type="hidden" name="Total_today" value="<%= total %>"></td>
            </tr>
            <% 		
			
			'对应星级人数
			sql1="select rank1,rank2,rank3,rank4,rank5 from crm_rankTotal where diaryid="&diaryid&" and todayOrTomo=0"
			
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
			%>
			<input type="hidden" name="rank<%= r %>_today" value="<%= rankValue%>">
            <tr>
              <td colspan="2" bgcolor="#CBFFB9"><strong><%= r %>星级 (<%=tRank %>个)</strong></td>
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
					if r>=4 then
						if j mod 2 = 1 then
							trColor="#ffffff"
						else
							trColor="#eeeeee"
						end if
						
						%>
						<% If j mod 2 = 1 then response.write "<tr>" %>						
						  <td width='50%' bgColor="<%= trColor %>"><%= rs2("com_name") %> (<%= rs2("com_email") %>)</td>
						<% If j mod 2 = 0 then response.write "</tr>" %>		
					<% 
					end if
					rs2.movenext
					j=j+1
				wend 
			If j mod 2 = 0  and r>=4 then response.write "<td bgColor='#eeeeee'>&nbsp;</td></tr>" 	
			end if
			rs2.close
			set rs2=nothing
			next 
			end if
			rs1.close
			set rs1=nothing

			 %>
            <tr>
              <td bgcolor="#66CC00" class="STYLE8" colspan="2">今日签单客户及分析</td>
              
            </tr>
<tr><td bgcolor="#ffffff" colspan="2"><textarea name="analysis_sign" cols="50" rows="5"><%= analysis_sign %></textarea></td>
            </tr>


			<tr>
              <td bgcolor="#66CC00" class="STYLE8">明日联系客户</td>
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
			
			%>
			<input type="hidden" name="rank<%= r %>_tomo" value="<%= rankValue %>">
            <tr>
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
					if r>=4 then
						if j mod 2 = 1 then
							trColor="#ffffff"
						else
							trColor="#eeeeee"
						end if
						%>
						<% If j mod 2 = 1 then response.write "<tr>" %>						
						  <td width='50%' bgColor="<%= trColor %>"><%= rs2("com_name") %> (<%= rs2("com_email") %>)</td>
						<% If j mod 2 = 0 then response.write "</tr>" %>	
					<% 
					end if
					rs2.movenext
					j=j+1
				wend 
			 If j mod 2 = 0  and r>=4 then response.write "<td bgColor='#eeeeee'>&nbsp;</td></tr>" 	
			end if
			rs2.close
			set rs2=nothing
			next
			end if
			rs1.close
			set rs1=nothing %>
            <tr>
              <td colspan="2" bgcolor="#66CC00" class="STYLE8">今日客户分析</td>
              </tr>
			  <tr >
              <td colspan="2" bgcolor="#FFFFFF">
                <textarea name="Analysis_today" cols="50" rows="5" readonly ><% = Analysis_today%></textarea>              </td>
            </tr>
            <tr>
              <td colspan="2" bgcolor="#66CC00" class="STYLE8">其它分享</td>
            </tr>
            <tr >
              <td colspan="2" bgcolor="#FFFFFF">
                <textarea name="share" cols="50" rows="5" readonly><%= share %></textarea>              </td>
            </tr>
            <tr >
              <td colspan="2" align="left" bgcolor="#FF9900"><strong>管理员回复</strong></td>
            </tr>
            <tr >
              <td colspan="2" align="center" bgcolor="#FFFFFF"><textarea name="reply" cols="50" rows="5" id="reply"><%= replyContent %></textarea></td>
            </tr>
            <tr >
              <td colspan="2" align="center" bgcolor="#FFFFFF"><input type="hidden" name="writeDate" value="<%= writeDate %>"><input type="hidden" name="diaryid" value="<%= request("diaryid") %>"><input type="submit" name="Submit2" class="button" value="发表回复"></td>
            </tr>
          </table>
            <p>
              <input type="button" name="Submit3" value="返回" onClick="history.back()">
            </p>
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
