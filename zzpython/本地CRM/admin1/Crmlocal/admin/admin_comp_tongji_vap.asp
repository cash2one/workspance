<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!-- #include file="../../../cn/function.asp" -->
<!-- #include file="../../include/include.asp" -->
<%
dotype=request("dotype")
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>vap客户统计</title>
<link href="../../css.css" rel="stylesheet" type="text/css">
<link href="../../inc/Style.css" rel="stylesheet" type="text/css">

<script language="javascript" src="../../include/calendar.js"></script>
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
<br>
  
<table width="800" border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#000000">
  <tr>
    <td colspan="13" align="center" bgcolor="#CCCCCC">客户数统计</td>
  </tr>
  <tr>
    <td align="center" bgcolor="#FFFFFF">Sales</td>
    <td align="center" bgcolor="#FFFFFF">6星</td>
    <td align="center" bgcolor="#FFFFFF">5星</td>
    <td align="center" bgcolor="#FFFFFF">
	<%if dotype<>"vapcomp" then%>
    普4星
    <%else%>
    短4星
    <%end if%>
    </td>
    <td align="center" bgcolor="#FFFFFF"><%if dotype<>"vapcomp" then%>
    钻4星
    <%else%>
    长4星
    <%end if%></td>
    <td align="center" bgcolor="#FFFFFF">3星</td>
    <td align="center" bgcolor="#FFFFFF">2星</td>
    <td align="center" bgcolor="#FFFFFF">1星</td>
    <td align="center" bgcolor="#FFCC00">客户总数</td>
    <td align="center" bgcolor="#FFFFFF">从未联系</td>
    <td align="center" bgcolor="#FFFFFF">安排但<BR>
    未联系</td>
    <td align="center" bgcolor="#FFFFFF">公海挑入<br>
    未联系</td>
    <td align="center" bgcolor="#FFFFFF">明日安<BR>
    排联系</td>
  </tr>
  <%
  code=1315
  if request("userClassId")<>"" then
  	code=request("userClassId")
  else
	  if session("userid")<>"" and session("userid")<>"10" and session("userid")<>"13" then
		code=session("userid")
	  end if
  end if
  sql="select code,meno from cate_adminuser where code='"&code&"'"
  set rs=conn.execute(sql)
  if not rs.eof or not rs.bof then
  while not rs.eof 
  %>
  <tr>
    <td colspan="13" bgcolor="#f2f2f2"><%=rs("meno")%></td>
  </tr>
  <%
  sql1="select realname,id from users where userid="&rs("code")&" and closeflag=1"
  set rs1=conn.execute(sql1)
  if not rs1.eof or not rs1.bof then
  while not rs1.eof 
  %>
  <tr>
    <td bgcolor="#FFFFFF"><%=rs1("realname")%></td>
    <td align="center" bgcolor="#FFFFFF"><IFRAME border=0 name=aaaa  marginWidth=0 frameSpacing=0 marginHeight=0 src="TJ.asp?personid=<%=rs1("id")%>&star=6&dotype=<%=dotype%>"   frameBorder=0 noResize width="45px" scrolling=no  height="21" vspale="0"></IFRAME></td>
    <td align="center" bgcolor="#FFFFFF">
	<IFRAME border=0 name=aaaa  marginWidth=0 frameSpacing=0 marginHeight=0 src="TJ_vap_comp.asp?personid=<%=rs1("id")%>&star=5&dotype=<%=dotype%>"   frameBorder=0 noResize width="45px" scrolling=no  height="21" vspale="0"></IFRAME>
	</td>
    <td align="center" bgcolor="#FFFFFF">
    <IFRAME border=0 name=aaaa  marginWidth=0 frameSpacing=0 marginHeight=0 src="TJ_vap_comp.asp?personid=<%=rs1("id")%>&star=4.1&dotype=<%=dotype%>"   frameBorder=0 noResize width="45px" scrolling=no  height="21" vspale="0"></IFRAME></td>
    
    <td align="center" bgcolor="#FFFFFF">
	<IFRAME border=0 name=aaaa  marginWidth=0 frameSpacing=0 marginHeight=0 src="TJ_vap_comp.asp?personid=<%=rs1("id")%>&star=4.8&dotype=<%=dotype%>"   frameBorder=0 noResize width="45px" scrolling=no  height="21" vspale="0"></IFRAME>
	</td>
    
    <td align="center" bgcolor="#FFFFFF">
	<IFRAME border=0 name=aaaa  marginWidth=0 frameSpacing=0 marginHeight=0 src="TJ_vap_comp.asp?personid=<%=rs1("id")%>&star=3&dotype=<%=dotype%>"   frameBorder=0 noResize width="45px" scrolling=no  height="21" vspale="0"></IFRAME>
	</td>
    <td align="center" bgcolor="#FFFFFF">
	<IFRAME border=0 name=aaaa  marginWidth=0 frameSpacing=0 marginHeight=0 src="TJ_vap_comp.asp?personid=<%=rs1("id")%>&star=2&dotype=<%=dotype%>"   frameBorder=0 noResize width="45px" scrolling=no  height="21" vspale="0"></IFRAME>
	</td>
    <td align="center" bgcolor="#FFFFFF">
      <IFRAME border=0 name=aaaa  marginWidth=0 frameSpacing=0 marginHeight=0 src="TJ_vap_comp.asp?personid=<%=rs1("id")%>&star=1&dotype=<%=dotype%>"   frameBorder=0 noResize width="45px" scrolling=no  height="21" vspale="0"></IFRAME>
    </td>
    <td align="center" bgcolor="#FFCC00">
      <IFRAME border=0 name=aaaa  marginWidth=0 frameSpacing=0 marginHeight=0 src="TJ_vap_comp.asp?personid=<%=rs1("id")%>&dotype=<%=dotype%>"   frameBorder=0 noResize width="45px" scrolling=no  height="21" vspale="0"></IFRAME>
    </td>
    <td align="center" bgcolor="#FFFFFF">
	<IFRAME border=0 name=aaaa  marginWidth=0 frameSpacing=0 marginHeight=0 src="TJ_vap_comp.asp?personid=<%=rs1("id")%>&Never=1&dotype=<%=dotype%>"   frameBorder=0 noResize width="45px" scrolling=no  height="21" vspale="0"></IFRAME>
	</td>
    <td align="center" bgcolor="#FFFFFF">
	<IFRAME border=0 name=aaaa  marginWidth=0 frameSpacing=0 marginHeight=0 src="TJ_vap_comp.asp?personid=<%=rs1("id")%>&NoCon=1&dotype=<%=dotype%>"   frameBorder=0 noResize width="45px" scrolling=no  height="21" vspale="0"></IFRAME>
	</td>
    <td align="center" bgcolor="#FFFFFF">
    <IFRAME border=0 name=aaaa  marginWidth=0 frameSpacing=0 marginHeight=0 src="TJ_vap_comp.asp?personid=<%=rs1("id")%>&gonghai=1&dotype=<%=dotype%>"   frameBorder=0 noResize width="45px" scrolling=no  height="21" vspale="0"></IFRAME>
    </td>
    <td align="center" bgcolor="#FFFFFF">
	<IFRAME border=0 name=aaaa  marginWidth=0 frameSpacing=0 marginHeight=0 src="TJ_vap_comp.asp?personid=<%=rs1("id")%>&Tomotor=1&dotype=<%=dotype%>"   frameBorder=0 noResize width="45px" scrolling=no  height="21" vspale="0"></IFRAME>
	</td>
  </tr>
  <%
  rs1.movenext
  wend
  end if
  rs1.close
  set rs1=nothing
  rs.movenext
  wend
  end if
  rs.close
  set rs=nothing
  %>
  <tr>
    <td align="center" bgcolor="#FFFFFF">&nbsp;</td>
    <td align="center" bgcolor="#FFFFFF"><IFRAME border=0 name=aaaa  marginWidth=0 frameSpacing=0 marginHeight=0 src="TJ_vap_comp.asp?code=<%=code%>&star=6&dotype=<%=dotype%>"   frameBorder=0 noResize width="45px" scrolling=no  height="21" vspale="0"></IFRAME></td>
    <td align="center" bgcolor="#FFFFFF"><IFRAME border=0 name=aaaa  marginWidth=0 frameSpacing=0 marginHeight=0 src="TJ_vap_comp.asp?code=<%=code%>&star=5&dotype=<%=dotype%>"   frameBorder=0 noResize width="45px" scrolling=no  height="21" vspale="0"></IFRAME></td>
    <td align="center" bgcolor="#FFFFFF"><IFRAME border=0 name=aaaa  marginWidth=0 frameSpacing=0 marginHeight=0 src="TJ_vap_comp.asp?code=<%=code%>&star=4.1&dotype=<%=dotype%>"   frameBorder=0 noResize width="45px" scrolling=no  height="21" vspale="0"></IFRAME></td>
    
    <td align="center" bgcolor="#FFFFFF"><IFRAME border=0 name=aaaa  marginWidth=0 frameSpacing=0 marginHeight=0 src="TJ_vap_comp.asp?code=<%=code%>&star=4.8&dotype=<%=dotype%>"   frameBorder=0 noResize width="45px" scrolling=no  height="21" vspale="0"></IFRAME></td>
    
    <td align="center" bgcolor="#FFFFFF">&nbsp;</td>
    <td align="center" bgcolor="#FFFFFF">&nbsp;</td>
    <td align="center" bgcolor="#FFFFFF">&nbsp;</td>
    <td align="center" bgcolor="#FFCC00">&nbsp;</td>
    <td align="center" bgcolor="#FFFFFF">&nbsp;</td>
    <td align="center" bgcolor="#FFFFFF">&nbsp;</td>
    <td align="center" bgcolor="#FFFFFF">&nbsp;</td>
    <td align="center" bgcolor="#FFFFFF">&nbsp;</td>
  </tr>
</table>
</body>
</html>
<%
conn.close
set conn=nothing
%>
