<html>
<head>
<!-- #include file="include/adfsfs!@#.asp" -->
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
<link href="../agentweb/css/main.css" rel="stylesheet" type="text/css">
</head>

<body >
<%
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
<table width="96%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="64"><h2> <font color="#339900">欢迎您：</font><font color="#000099"><%=session.Contents("admin_user")%></font></h2><p></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="5" cellspacing="5" bgcolor="#A9CD96">
	      <tr >
        <td valign="top"><span class="STYLE3"><font color="#FFFFFF" >信息中心</font></span></td>
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
                <td width="24%"><input type="submit" name="Submit2" value="我要发布信息" onClick="location.href='news/news_add.asp'"></td>
                <td width="76%">
				<%
				   total=oPage.getTotalPage
				   oPage.pageNav "main.asp?"&sear,""
				   totalpg=cint(total/20)
				%></td>
              </tr>
            </table></td>
          </tr>
		    <%
  
  while not rst.eof
  %>
          <tr >
            <td width="62%" height="25" class="liness"><img src="/agentweb/images/houye.gif">
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
              <img src="/cn/img/icon_new.gif">
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
            <td height="26" colspan="3"><%fpage02 fpage,fpall %></td>
          </tr>
		   <%
			rst.close
			set rst=nothing
			else
			%>
		<tr>
            <td height="26" colspan="3"><input type="submit" name="Submit2" value="我要发布信息" onClick="location.href='news/news_add.asp'"></td>
          </tr>
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
