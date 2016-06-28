<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!-- #include file="../../../cn/function.asp" -->
<!-- #include file="../../include/include.asp" -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>vap联系情况统计</title>
<link href="../../css.css" rel="stylesheet" type="text/css">
<link href="../../inc/Style.css" rel="stylesheet" type="text/css">

<script language="javascript" src="../../include/calendar.js"></script>
</head>

<body bgcolor="7386A5" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<br>
<% 
  code=13
%>
<table width="1000"  border="0" align="center" cellpadding="5" cellspacing="0">
          <tr>
            <td align="center" bgcolor="#FFFFFF">
			<%
			dotype=request.QueryString("dotype")
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
			<form name="form1" method="get" action="" style="padding:0px; margin:0px;">
			  <input type="hidden" name="dotype" id="dotype" value="<%=dotype%>">
    <input type="hidden" name="userClassId" id="userClassId" value="<%=request("userClassId")%>">
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
<table width="1000" border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#000000">
  <tr>
    <td colspan="11" align="center" bgcolor="#CCCCCC">联系情况统计</td>
  </tr>
  <tr>
    <td align="center" bgcolor="#FFFFFF">Sales</td>
    <td align="center" bgcolor="#FFFFFF">5星</td>
    
    <td align="center" bgcolor="#FFFFFF">
    <%if dotype<>"vapcomp" then%>
    钻4星
	<%else%>
    长4星
	<%end if%>
    </td>
    
    
    
    <td align="center" bgcolor="#FFFFFF">
    <%if dotype<>"vapcomp" then%>
    普4星
    <%else%>
    短4星
    <%end if%>
    </td>
    <td align="center" bgcolor="#FFFFFF">3星</td>
    <td align="center" bgcolor="#FFFFFF">2星</td>
    <td align="center" bgcolor="#FFFFFF">1星</td>
    <td align="center" bgcolor="#FFFFFF">拖、毁单</td>
    <td align="center" bgcolor="#FFCC00">合计</td>
    <td align="center" bgcolor="#FFFFFF">有效联系率</td>
    <td align="center" bgcolor="#FFFFFF">明日安<BR>
    排联系</td>
  </tr>
  
  <%
  sql1=" and id="&session("personid")&""
  sql1="select realname,id from users where closeflag=1 "&sql1
  set rs1=conn.execute(sql1)
  if not rs1.eof or not rs1.bof then
  while not rs1.eof 
  %>
  <tr>
    <td bgcolor="#FFFFFF"><%=rs1("realname")%></td>
    <td align="center" bgcolor="#FFFFFF">
    <IFRAME border=0 name=aaaa  marginWidth=0 frameSpacing=0 marginHeight=0 src="TJ_1.asp?personid=<%=rs1("id")%>&star=5&contacttype=13&fromdate=<%=fromdate%>&todate=<%=todate%>&dotype=<%=dotype%>"   frameBorder=0 noResize width="45px" scrolling=no  height="21" vspale="0"></IFRAME></td>
    <td align="center" bgcolor="#FFFFFF">
    <IFRAME border=0 name=aaaa  marginWidth=0 frameSpacing=0 marginHeight=0 src="TJ_1.asp?personid=<%=rs1("id")%>&star=4.8&contacttype=13&fromdate=<%=fromdate%>&todate=<%=todate%>&dotype=<%=dotype%>"   frameBorder=0 noResize width="45px" scrolling=no  height="21" vspale="0"></IFRAME>	</td>
    <td align="center" bgcolor="#F2F2F2">
    <IFRAME border=0 name=aaaa  marginWidth=0 frameSpacing=0 marginHeight=0 src="TJ_1.asp?personid=<%=rs1("id")%>&star=4.1&contacttype=13&fromdate=<%=fromdate%>&todate=<%=todate%>&dotype=<%=dotype%>"   frameBorder=0 noResize width="45px" scrolling=no  height="21" vspale="0"></IFRAME></td>
    <td align="center" bgcolor="#FFFFFF">
    <IFRAME border=0 name=aaaa  marginWidth=0 frameSpacing=0 marginHeight=0 src="TJ_1.asp?personid=<%=rs1("id")%>&star=3&contacttype=13&fromdate=<%=fromdate%>&todate=<%=todate%>&dotype=<%=dotype%>"   frameBorder=0 noResize width="45px" scrolling=no  height="21" vspale="0"></IFRAME></td>
    <td align="center" bgcolor="#FFFFFF">
    <IFRAME border=0 name=aaaa  marginWidth=0 frameSpacing=0 marginHeight=0 src="TJ_1.asp?personid=<%=rs1("id")%>&star=2&contacttype=13&fromdate=<%=fromdate%>&todate=<%=todate%>&dotype=<%=dotype%>"   frameBorder=0 noResize width="45px" scrolling=no  height="21" vspale="0"></IFRAME></td>
    <td align="center" bgcolor="#FFFFFF">
    <IFRAME border=0 name=aaaa  marginWidth=0 frameSpacing=0 marginHeight=0 src="TJ_1.asp?personid=<%=rs1("id")%>&star=1&contacttype=13&fromdate=<%=fromdate%>&todate=<%=todate%>&dotype=<%=dotype%>"   frameBorder=0 noResize width="45px" scrolling=no  height="21" vspale="0"></IFRAME></td>
    <td align="center" bgcolor="#F2F2F2"><IFRAME border=0 name=aaaa  marginWidth=0 frameSpacing=0 marginHeight=0 src="TJ_1.asp?personid=<%=rs1("id")%>&star=&contacttype=&fromdate=<%=fromdate%>&todate=<%=todate%>&dotype=<%=dotype%>&tuohuiflag=1"   frameBorder=0 noResize width="45px" scrolling=no  height="21" vspale="0"></IFRAME></td>
    <td align="center" bgcolor="#FFCC00">
    <IFRAME border=0 name=aaaa  marginWidth=0 frameSpacing=0 marginHeight=0 src="TJ_1.asp?personid=<%=rs1("id")%>&contacttype=13&fromdate=<%=fromdate%>&todate=<%=todate%>&dotype=<%=dotype%>"   frameBorder=0 noResize width="45px" scrolling=no  height="21" vspale="0"></IFRAME></td>
    <td align="center" bgcolor="#FFFFFF">
    <IFRAME border=0 name=aaaa  marginWidth=0 frameSpacing=0 marginHeight=0 src="TJ_lv.asp?personid=<%=rs1("id")%>&fromdate=<%=fromdate%>&todate=<%=todate%>&dotype=<%=dotype%>"   frameBorder=0 noResize width="45px" scrolling=no  height="21" vspale="0"></IFRAME>	</td>
    <td align="center" bgcolor="#FFFFFF">
	<IFRAME border=0 name=aaaa  marginWidth=0 frameSpacing=0 marginHeight=0 src="TJ.asp?personid=<%=rs1("id")%>&Tomotor=1&fromdate=<%=fromdate%>&todate=<%=todate%>&dotype=<%=dotype%>"   frameBorder=0 noResize width="45px" scrolling=no  height="21" vspale="0"></IFRAME>	</td>
  </tr>
  <%
  rs1.movenext
  wend
  end if
  rs1.close
  set rs1=nothing
 
  %>
</table>
</body>
</html>
<%
conn.close
set conn=nothing
%>
