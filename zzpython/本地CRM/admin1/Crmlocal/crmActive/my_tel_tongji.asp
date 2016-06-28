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
            <td width="130" height="23" nowrap background="../../newimages/shale_fill_1.gif"><img src="../../newimages/shale_arrowhead.gif" width="28" height="23" align="absmiddle">客户联系情况统计</td>
            <td width="21" background="../../newimages/shale_fill_1.gif"><img src="../../newimages/shale_photo.gif" width="21" height="23"></td>
            <td background="../../newimages/lm_ddd.jpg">&nbsp;</td>
          </tr>
        </table>
          </td>
      </tr>
      <tr>
        <td align="center" valign="top" bgcolor="E7EBDE">
		  <br>
		  <table width="80%" border="0" cellpadding="4" cellspacing="1" bgcolor="#7386A5">
            <tr>
              <td align="center" bgcolor="#FFFFFF"><a href="?weekflag=1">本周显示</a></td>
              <td align="center" bgcolor="#FFFFFF"><a href="?moth=1">本月显示</a></td>
              </tr>
          </table>
		  <br>
		  <table width="80%"  border="0" align="center" cellpadding="5" cellspacing="0">
          <tr>
            <td align="center">
			<%
			count1Total=0
			count2Total=0
			if request("from_date")="" then
			    if request.QueryString("weekflag")="1" then
					fromdate=date()-weekday(now())+2
				else
				    if request.QueryString("moth")="1" then
					pyear=DatePart("yyyy",now())
					pmouth=DatePart("m",now()) 
					pday=DatePart("d",now()) 
					fromdate=formatdatetime(pyear&"-"&pmouth&"-1",2)
					else
				    fromdate=formatdatetime(now,2)
					end if
				end if
			
			else
				
				fromdate=request("from_date")
			end if
			if request("to_date")="" then
			todate=formatdatetime(now,2)
			else
			todate=request("to_date")
			end if
			
			%>
			<form name="form1" method="get" action="">
  时间从
    <input name="from_date" type="text" class="wenbenkuang" id="from_date" value="<%=fromdate%>" size="15">
    <a onClick="event.cancelBubble=true;" href="javascript:showCalendar('dimg1',false,'from_date')"><img id=dimg1 align=absmiddle width=34 height=21 src="../../newimages/button.gif" border=0> </a>到
    <input name="to_date" type="text" class="wenbenkuang" id="to_date" value="<%=todate%>" size="15">
    <a onClick="event.cancelBubble=true;" href="javascript:showCalendar('dimg2',false,'to_date')"><img id=dimg2 align=absmiddle width=34 height=21 src="../../newimages/button.gif" border=0></a> <STRONG>  </STRONG><STRONG>
    </STRONG>
    <input type="submit" name="Submit" value="搜索">
              </form></td>
          </tr>
        </table>
		<table width="95%"  border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#7386A5">
              <tr bgcolor="#FFFFFF">
                <td colspan="15" align="center" bgcolor="#7386A5"><span class="STYLE6">联系情况统计</span></td>
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
                <td align="center" colspan="2"><%=rsstar(0)%>星</td>
				<%
				rsstar.movenext
				loop
				end if
				rsstar.close
				set rsstar=nothing
				%>
                <td align="center" colspan="2" bgcolor="#FFFF00">合计</td>
                <td align="center">&nbsp;</td>
              </tr>
              <tr bgcolor="#FFFFFF">
		        <td bgcolor="#F2F2F2">&nbsp;</td>
				<%
				set rsstar=server.CreateObject("ADODB.recordset")
				sqlstar="select code from cate_kh_csd order by id desc"
				rsstar.open sqlstar,conn,1,2
				if not rsstar.eof then
				do while not rsstar.eof 
				%>
				<%
				set rscate=server.CreateObject("ADODB.recordset")
				sqlcate="select * from cate_contact_about where code<>'10'"
				rscate.open sqlcate,conn,1,2
				if not rscate.eof then
				do while not rscate.eof 
				%>
		        <td align="center" nowrap><strong><%=left(rscate("meno"),2)%></strong></td>
				
				<%
				rscate.movenext
				loop
				end if
				rscate.close
				set rscate=nothing
				%>
				<%
				rsstar.movenext
				loop
				end if
				rsstar.close
				set rsstar=nothing
				%>
				<td align="center" nowrap bgcolor="#FFFF00"><STRONG>无效</STRONG></td>
				<td align="center" nowrap bgcolor="#FFFF00"><STRONG>有效</STRONG></td>
				<td align="center">有效联系率</td>
	          </tr>
			  <%sqlu="select * from users where closeflag=1 and adminuserid<>'' and id="&session("personid")
				set rsu=server.CreateObject("ADODB.recordset")
				rsu.open sqlu,conn,1,2
				countpost_ch=0
				countpost_en=0
				counttrade_sh=0
				if not rsu.eof then
				do while not rsu.eof
				%>
		      
		      <tr bgcolor="#FFFFFF">
            <td align="left" bgcolor="#f2f2f2"><%=rsu("realname")%></td>
			<%  countc1=0
			    countcall=0
				countcall13=0
				countcall12=0
				set rsstar=server.CreateObject("ADODB.recordset")
				sqlstar="select code from cate_kh_csd order by id desc"
				rsstar.open sqlstar,conn,1,2
				if not rsstar.eof then
				do while not rsstar.eof 
				%>
			<%
			
			set rscate=server.CreateObject("ADODB.recordset")
			sqlcate="select * from cate_contact_about where code<>'10' "
			rscate.open sqlcate,conn,1,2
			if not rscate.eof then
			do while not rscate.eof 
			
			%>
            <td align="center">
			<%
			'*****中文
			sql=""
			if cstr(fromdate)<>cstr(todate) then
				if fromdate<>"" then
					sql=sql&" and teldate>='"&fromdate&"'"
				end if
				if todate<>"" then
					sql=sql&" and teldate<='"&cdate(todate)+1&"'"
				end if
			else
			    if fromdate<>"" and todate<>"" then
					sql=sql&" and year(teldate)="&year(fromdate)&" and month(teldate)="&month(fromdate)&" and day(teldate)="&day(fromdate)&""
				end if
			end if
			sqlmy="select count(com_id) from temp_salescomp where EXISTS(select com_id from comp_active_tel where personid="&rsu("id")&" and com_id=temp_salescomp.com_id "&sql&") and com_rank='"&rsstar("code")&"' and contacttype='"&rscate("code")&"' "&sql&""
			
		
			'response.Write(sql)
			set rs=conn.execute(sqlmy&sql)
			'set rsnoemail=connnoemail.execute(sql)
			count_ch=rs(0)
			countc1=countc1+count_ch
			if rscate("code")="13" then
			countc13=count_ch
			else
			countc13=0
			end if
			if rscate("code")="12" then
			countc12=count_ch
			else
			countc12=0
			end if
			countcall=countcall+countc12+countc13
			rs.close()
			set rs=nothing
			countcall13=countcall13+countc13
			countcall12=countcall12+countc12
			%>
			<a href="admin_tel_comp1.asp?adminuser=<%=rsu("id")%>&contacttype=<%=rscate("code")%>&from_date=<%=fromdate%>&to_date=<%=todate%>&com_rank=<%=rsstar("code")%>"><%=count_ch%></a></td>
			
			<%
			rscate.movenext
			loop
			end if
			%>
			<%
				rsstar.movenext
				loop
				end if
				rsstar.close
				set rsstar=nothing
				%>
			<td align="center" bgcolor="#FFFF00"><%=countcall12%></td>
			<td align="center" bgcolor="#FFFF00"><%=countcall13%></td>
			<td align="center"><%
			if countc1<>0 then
				response.Write(formatnumber(((countcall13) / (countcall13+countcall12))*100,2))
				count1Total=count1Total+countcall12
				count2Total=count2Total+countcall13
			else
				response.write "0"
			end if
			%>%</td>
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
				set rsstar=server.CreateObject("ADODB.recordset")
				sqlstar="select code from cate_kh_csd order by id desc"
				rsstar.open sqlstar,conn,1,2
				if not rsstar.eof then
				do while not rsstar.eof 
				%>
			<%
				set rscate=server.CreateObject("ADODB.recordset")
				sqlcate="select code from cate_contact_about where code<>'10'"
				rscate.open sqlcate,conn,1,2
				if not rscate.eof then
				do while not rscate.eof 
				sqlcou=""
				if cstr(fromdate)<>cstr(todate) then
				if fromdate<>"" then
					sqlcou=sqlcou&" and teldate>='"&fromdate&"'"
				end if
				if todate<>"" then
					sqlcou=sqlcou&" and teldate<='"&cdate(todate)+1&"'"
				end if
				else
					if fromdate<>"" and todate<>"" then
						sqlcou=sqlcou&" and year(teldate)="&year(fromdate)&" and month(teldate)="&month(fromdate)&" and day(teldate)="&day(fromdate)&""
					end if
				end if
				sqlcoucount="select count(com_id) from temp_salescomp where contacttype='"&rscate("code")&"' and com_rank='"&rsstar("code")&"' and EXISTS(select com_id from comp_active_tel where personid="&session("personid")&" and com_id=temp_salescomp.com_id "&sqlcou&") "
				'response.Write(sqlcoucount&sqlcou)
				set rscu=conn.execute(sqlcoucount&sqlcou)
				if not rscu.eof then
				allcontactcount=rscu(0)
				end if
				%>
            <td align="center"><%=allcontactcount%></td>
				
				<%
				rscate.movenext
				loop
				end if
				rscate.close
				set rscate=nothing
				%>
				<%
				rsstar.movenext
				loop
				end if
				rsstar.close
				set rsstar=nothing
				%>
				<td align="center" bgcolor="#FFFF00"><%= count1Total %></td>
				<td align="center" bgcolor="#FFFF00"><%= count2Total %></td>
				<td align="center">
				<%countTotal=count1Total+count2Total
				if countTotal<>0 then
				response.write round((count2Total/(count1Total+count2Total))*100,2)
				else
				response.write "0"
				end if
				 %>%</td>
            </tr>
        </table>
		<br></td>
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
