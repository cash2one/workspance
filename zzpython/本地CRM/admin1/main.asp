<html>
<head>
<!-- #include file="include/adfsfs!@#.asp" -->
<link href="main.css" rel="stylesheet" type="text/css">
<!-- #include file="jumptolog.asp" -->
<!-- #include file="../cn/function.asp" -->
<!-- #include file="inc/pagetop.asp" -->
<!-- #include file="include/include.asp" -->
<!--#include file="include/pagefunction.asp"-->
<style type="text/css">
<!--
.STYLE3 {
	font-size: 14px;
	font-weight: bold;
}
.STYLE4 {color: #FF0000}
td {
	font-size: 12px;
}
-->
</style>
<!-- #include file="jumptolog.asp" -->
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>无标题文档</title>
<style type="text/css">
<!--
.floadtable {
	position: relative;
	float: right;
	vertical-align: bottom;
}
.floadimg {
	position: relative;
}
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.liness {
	border-bottom-width: 1px;
	border-bottom-style: solid;
	border-bottom-color: #A5A5A5;
	font-size: 12px;
}

-->
</style>
</head>

<body >
<%
page=Request("page")
if page="" then 
page=1 
end if
sear=sear&"n="
sub renews(ids)
sqln="select count(id) as nn from zz91_renews where newsid="&Ids
set rsnn=conn.execute(sqln)
nn=rsnn("nn")
response.write nn
rsnn.close
end sub
%>
<%
                   Set oPage=New clsPageRs2
				   With oPage
				     .sltFld  = "id,NewsTitle,NewsDate,newsauto"
				     .FROMTbl = "Zz91_News"
				     .sqlOrder= "order by NewsDate desc"
				     .sqlWhere= "WHERE id>1"
				     .keyFld  = "id"    '不可缺少
				     .pageSize= 20
				     .getConn = conn
				     Set rst  = .pageRs
				   End With
%>
<div align="center">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
 
  <tr>
    <td><table width="100%" border="0" cellpadding="5" cellspacing="5" bgcolor="#A9CD96">
	      <tr >
        <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><span class="STYLE3"><font color="#FFFFFF" >信息中心 </font></span></td>
              <td align="right"><span class="STYLE3"><font color="#FFFFFF" >
                <input type="submit" name="Submit22" value="我要发布信息" onClick="location.href='news/news_add.asp'">
              </font></span></td>
            </tr>
          </table></td>
	      </tr>
		  <%
if not rst.eof and not rst.bof then
 ' fpsize=20
 ' fpi=0
 ' fpall=fpage1(rst,fpsize,fpage)
  'pagetop rst,20
%>
      <tr bgcolor="#FFFFFF">
        <td valign="top"><table width="100%" height="59" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td height="26" colspan="3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td>
				<%
				   total=oPage.getTotalPage
				   oPage.pageNav "main.asp?"&sear,""
				   totalpg=cint(total/20)
				   if total/20 > totalpg then
				   totalpg=totalpg+1
				   end if
				%></td>
                </tr>
            </table></td>
          </tr>
		    <%
  
  while not rst.eof
  %>
          <tr >
            <td width="62%" height="25" class="liness">·
              <a href="news/news_show.asp?id=<%response.Write(rst("id"))%>"><b>
              <%response.Write(rst("newstitle"))%>
              </b></a>
              <% sql1="select id from zz91_newsattach where newsID="&rst("id")
			set rsn=conn.execute(sql1)
			if rsn.eof then
			else
			str="<font color='#FF0000'>[包含文件]</font>&nbsp;"
			response.write str
			end if
			rsn.close
			response.write rst("NewsDate")
			%>
                <%
			nowd=date()
			putd=rst("newsdate")
			if nowd-putd<4 then
			%>
              <img src="http://www.zz91.com/cn/img/icon_new.gif">
                <%end if%></td>
            <td width="21%" class="liness"><%=rst("newsauto")%>发表</td>
            <td width="17%" class="liness">回复贴数：<span class="STYLE4"><%call renews(rst("id"))%></span></td>
            </tr>
    <%
  fpi=fpi+1
  rst.movenext
  wend

  %>
          <tr>
            <td height="26" colspan="3"><!-- #include file="include/page.asp" --></td>
          </tr>
		   <%
			rst.close
			set rst=nothing
			else
			%>
		 
			<%
			end if
			%>
        </table></td>
      </tr>
    </table></td>
  </tr>
</table>
</div>
</body>
</html>
