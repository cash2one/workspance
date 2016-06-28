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
            <td width="130" height="23" nowrap background="../../newimages/shale_fill_1.gif"><img src="../../newimages/shale_arrowhead.gif" width="28" height="23" align="absmiddle">客户联系情况统计</td>
            <td width="21" background="../../newimages/shale_fill_1.gif"><img src="../../newimages/shale_photo.gif" width="21" height="23"></td>
            <td background="../../newimages/lm_ddd.jpg">&nbsp;</td>
          </tr>
        </table>
          </td>
      </tr>
      <tr>
        <td align="center" valign="top" bgcolor="E7EBDE"><table width="80%"  border="0" align="center" cellpadding="5" cellspacing="0">
          <tr>
            <td align="center"><%
			if request("curdate")="" then
				 curdate=date()
			else
				curdate=cdate(request("curdate"))
			end if
			sqlTemp=""
			if curdate<>"" then
				sqlTemp=sqlTemp&" and teldate>='"&curdate&"' and teldate<='"&curdate+1&"'"
				
			end if
			%>
                <form name="form1" method="get" action="">
                  时间
                  <input name="curdate" type="text" class="wenbenkuang" id="curdate" value="<%=curdate%>" size="15">
          <a onClick="event.cancelBubble=true;" href="javascript:showCalendar('dimg2',false,'curdate')"><img id=dimg2 align=absmiddle width=34 height=21 src="../../newimages/button.gif" border=0></a> <STRONG> </STRONG><STRONG> </STRONG>
          <input type="submit" name="Submit" value="搜索">
              </form></td>
          </tr>
        </table>
		<% 			
		'取得总联系量
		personid=47
		sql="select count(com_id) from temp_salescomp where com_id in (select com_id from comp_tel where com_id=temp_salescomp.com_id and personid="&personid&" "&sqlTemp&" ) and contactType=13"
		'response.write sql
		set rs=conn.execute(sql)
		if not (rs.bof and rs.eof) then
			total=rs(0)

		%>
		<form name="form2" method="post" action="">
		  <table width="90%" border="0" cellpadding="4" cellspacing="1" bgcolor="#CCCCCC">
            <tr>
              <td width="66%" bgcolor="#66CC00"><span class="STYLE8">今日联系情况</span></td>
              <td width="34%" bgcolor="#66CC00"><span class="STYLE8">今日有效联系量:<%= total %>个</span></td>
            </tr>
            <% 		
			for r=5 to 1 step -1
			'对应星级人数
			sql1="select count(com_rank) from temp_salescomp where exists(select com_id from comp_tel where com_id=temp_salescomp.com_id and personid="&personid&" "&sqlTemp&" ) and contactType=13 and com_rank="&r	
			'response.write sql1
			set rs1=conn.execute(sql1)
			rankTotal=rs1(0)
			%>
            <tr>
              <td colspan="2" bgcolor="#CBFFB9"><strong><%= r %>星级 (<%= rankTotal %>个)</strong></td>
            </tr>
            <%
			rs1.close
			set rs1=nothing
			'对应星级明细
			if r>=4 then
				sql2="select com_name,com_email from temp_salescomp where exists(select com_id from comp_tel where com_id=temp_salescomp.com_id and personid="&personid&" "&sqlTemp&" ) and contactType=13 and com_rank="&r	
				'response.write sql2
				set rs2=conn.execute(sql2)
				if not (rs2.bof and rs2.eof) then
					j=1
					while not rs2.eof
					if j mod 2 = 1 then
						trColor="#ffffff"
					else
						trColor="#eeeeee"
					end if
					%>
				<tr bgColor="<%= trColor %>">
				  <td colspan="2"><%= rs2("com_name") %> (<%= rs2("com_email") %>)</td>
				</tr>
				<% rs2.movenext
					j=j+1
					wend 
				end if
				rs2.close
				set rs2=nothing
			end if
			%>
            <% next %>
            <% 
			'今日签单客户
			sql3="select count(com_id) from temp_salescomp where exists(select com_id from comp_tel where com_id=temp_salescomp.com_id and personid="&personid&" "&sqlTemp&" ) and com_id in(select com_id from crm_assign where com_id=temp_salescomp.com_id and waiter_open=1 )and contactType=13"
			set rs3=conn.execute(sql3)
			todayTotal=rs3(0)
			rs3.close
			set rs3=nothing
			 %>
            <tr>
              <td bgcolor="#66CC00" class="STYLE8">今日签单客户</td>
              <td bgcolor="#66CC00" class="STYLE8"><%= todayTotal %></td>
            </tr>
            <% 
			'待开通客户列表
			sql3="select com_name,com_id from temp_salescomp where exists(select com_id from comp_tel where com_id=temp_salescomp.com_id and personid="&personid&" "&sqlTemp&" ) and com_id in(select com_id from crm_assign where com_id=temp_salescomp.com_id and waiter_open=1 )and contactType=13"
			response.write sql3
			set rs3=conn.execute(sql3)
			while not rs3.eof
			 %>
            <tr>
              <td colspan="2" bgcolor="#66CC00" class="STYLE8">客户:<%= com_name %></td>
            </tr>
            <tr>
              <td colspan="2" bgcolor="#FFFFFF" class="STYLE8"><textarea name="analysis_<%= com_id %>" cols="50" rows="5"></textarea><input type="hidden" value="<%= com_id %>" name="analysis_comid"></td>
            </tr>
			<% rs3.movenext
			wend %>
			<% 
			sqlTomo=" and contactnext_time >='"&date()+1&"' and contactnext_time<='"&date()+2&"'"
			'明日总数
		sql="select count(com_id) from temp_salescomp where com_id in (select com_id from comp_tel where com_id=temp_salescomp.com_id and personid="&personid&" ) "&sqlTomo&""
		'response.write sql
		set rs=conn.execute(sql)
		if not (rs.bof and rs.eof) then
			totalTomo=rs(0)
		end if %>
			<tr>
              <td bgcolor="#66CC00" class="STYLE8">明日联系客户</td>
              <td bgcolor="#66CC00" class="STYLE8"><%= totalTomo %>个</td>
            </tr>
 <% 		
			for r=5 to 1 step -1
			'明日对应星级人数
			sql1="select count(com_id) from temp_salescomp where com_id in (select com_id from comp_tel where com_id=temp_salescomp.com_id and personid="&personid&" ) "&sqlTomo&" and com_rank="&r	
			'response.write sql1
			set rs1=conn.execute(sql1)
			rankTotal=rs1(0)
			%>
            <tr>
              <td colspan="2" bgcolor="#CBFFB9"><strong><%= r %>星级 (<%= rankTotal %>个)</strong></td>
            </tr>
            <%
			rs1.close
			set rs1=nothing
			'对应星级明细
			if r>=4 then
				sql2="select com_name,com_email from temp_salescomp where com_id in (select com_id from comp_tel where com_id=temp_salescomp.com_id and personid="&personid&" ) "&sqlTomo&" and com_rank="&r
				'response.write sql2
				set rs2=conn.execute(sql2)
				if not (rs2.bof and rs2.eof) then
					j=1
					while not rs2.eof
					if j mod 2 = 1 then
						trColor="#ffffff"
					else
						trColor="#eeeeee"
					end if
					%>
				<tr bgColor="<%= trColor %>">
				  <td colspan="2"><%= rs2("com_name") %> (<%= rs2("com_email") %>)</td>
				</tr>
				<% rs2.movenext
					j=j+1
					wend 
				end if
				rs2.close
				set rs2=nothing
			end if
			%>
            <% next %>
            <tr>
              <td colspan="2" bgcolor="#66CC00" class="STYLE8">今日客户分析</td>
              </tr>
			  <tr >
              <td colspan="2" bgcolor="#FFFFFF">
                <textarea name="allAnalysis" cols="50" rows="5"></textarea>
              </td>
            </tr>
            <tr>
              <td colspan="2" bgcolor="#66CC00" class="STYLE8">其它分享</td>
            </tr>
            <tr >
              <td colspan="2" bgcolor="#FFFFFF">
                <textarea name="share" cols="50" rows="5"></textarea>
              </td>
            </tr>
            <tr >
              <td colspan="2" bgcolor="#FFFFFF"><input type="submit" name="Submit2" class="button" value="提交"></td>
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
