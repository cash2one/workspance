<%@LANGUAGE="VBSCRIPT" CODEPAGE="936"%>
<!-- #include file="../include/adfsfs!@#.asp" -->
<!-- #include file="../localjumptolog.asp" -->
<!-- #include file="../../cn/function.asp" -->
<!--#include file="../include/include.asp"-->
<!--#include file="../include/pagefunction.asp"-->
<%
page=request("page")
if page="" then page=1
%><!--#include file="inc.asp"-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>

<link href="s.css" rel="stylesheet" type="text/css" />
</head>

<body scroll=yes>
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#5F7290">
  <tr>
    <td valign="top" bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="5">
      <tr>
        <td height="20" bgcolor="#f2f2f2" class="btitle"><strong>常备工具库、分享库</strong></td>
        <td align="right" bgcolor="#f2f2f2" class="btitle">
		  <input type="button" name="Submit" value="我要提交" onClick="window.location='question_add.asp'" />
	      <%if dotechman=1 then%>
			  <input type="button" name="Submit22" value="所有分享" onClick="window.location='?my=all'" />
			  <input type="button" name="Submit2" value="我发布的分享" onClick="window.location='?my=1'">
              <input type="button" name="Submit2" value="收到的分享" onClick="window.location='?my=1'">
		  <%end if%>		  </td>
      </tr>
    </table>
	<%
	sear="n="
	sql=""
	if request("my")="1" then
	sql=sql&" and sdoman='"&session("personid")&"'"
	sear=sear&"&my="&request("my")
	end if
	if session("userid")="14" then
	else
	sql=sql&" and shidden=0 "
	end if
                   Set oPage=New clsPageRs2
				   With oPage
				     .sltFld  = "*"
				     .FROMTbl = "zz91_TechQuestion"
				     .sqlOrder= "order by sdate desc"
				     .sqlWhere= "WHERE ssubid=0 "&sql
				     .keyFld  = "ID"    '不可缺少
				     .pageSize= 14
				     .getConn = conn
				     Set rs  = .pageRs
				   End With
     %>
      <table width="100%" border="0" cellspacing="0" cellpadding="5">
        <tr>
          <td><%
				   total=oPage.getTotalPage
				   oPage.pageNav "?"&sear,""
				   totalpg=cint(total/14)
				   if total/14 > totalpg then
				   totalpg=totalpg+1
				   end if
				%></td>
        </tr>
      </table>
	  
	  <table width="100%" border="0" cellspacing="0" cellpadding="4">
        <tr>
          <td width="300" class="stitle">标题</td>
          <td class="stitle">发布时间</td>
          <td class="stitle">发布人</td>
          <td class="stitle">所属类别</td>
          <%if dotechman=1 then%><td class="stitle">要求解决时间</td><%end if%>
          <td class="stitle">解决人</td>
		  <%if techadmin=1 then%>
          <td class="stitle">分配</td>
		  <%end if%>
        </tr>
  <%
  if not rs.eof then
  do while not rs.eof
  %>
       
        <tr>
          <td class="lit"><img src="../../images/arrow_nextpage.gif" /> 
		  <%if rs("stitle")="" then%>
		  <a href="question_show.asp?id=<%=rs("id")%>"><%=left(rs("scontent"),20)%>...</a>
		  <%else%>
		  <a href="question_show.asp?id=<%=rs("id")%>"><%=left(rs("stitle"),20)%>...</a>
		  <%end if%>
		  <%sqlc="select count(id) from zz91_TechQuestion where ssubid="&rs("id")
		  set rsc=conn.execute(sqlc)
		  bcount=rsc(0)
		  rsc.close
		  set rsc=nothing
		  %>
		  (<%=bcount%>)
		  </td>
          <td class="lit"><%=rs("sdate")%></td>
          <td class="lit"><%call ssname(rs("adminuser"))%></td>
          <td class="lit">
		  <%
		  
		  call resalutq(rs("Scheck"))
		 
		  %></td>
          <%if dotechman=1 then%><td class="lit"><%=rs("dodate")%>&nbsp;</td><%end if%>
          <td class="lit"><%call ssname(rs("doperson"))%>&nbsp;</td>
		  <%if techadmin=1 then%>
          <td class="lit"><a href="#康" onClick="window.open('question_sal.asp?id=<%=rs("id")%>','_a','width=400,height=300')">分配</a></td>
		  <%end if%>
        </tr>
  <%
  rs.movenext
  loop
  end if
  rs.close
  set rs=nothing
  
  %>
      </table>
	  <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="30" align="center" class="btitle"><!-- #include file="../include/page.asp" --></td>
        </tr>
      </table></td>
  </tr>
</table>
</body>
</html>
<%endConnection()%>
