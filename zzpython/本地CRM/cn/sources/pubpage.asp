<%
page=SafeRequest("page",1)
if page="" then
page=1
end if
		  pg=totalpg
		  if pg=0 then
		  pg=1
		  end if 
		  if page<=6 then
		  beg=1
			  if pg<10 then
			  enf=pg
			  else
			  enf=10
			  end if
		  else
		  beg=page-4
		  enf=page+5
			  if enf>pg then
				  if pg<10 then
				  beg=1
				  else
				  beg=pg-9
				  end if
			  enf=pg
			  end if
		  end if
		  %>
<style type="text/css">
<!--
#lblNav {
	color: #FFFFFF;
	background-color: #FF6600;
}
.pageupmain {
	margin-top: 13px;
}
-->
</style>
<div class="pageupmain">
<%
oPage.bottonpagenav
%>
      <%if page>1 then%>
      &nbsp;&nbsp;&nbsp;&nbsp; <a href="<%response.Write(towardpage)%>_p1.html">��ҳ</a>&nbsp;<a href="<%response.Write(towardpage)%>_p<%=page-1%>.html">��һҳ</a>
      <%else%>&nbsp;&nbsp;&nbsp;&nbsp;��ҳ&nbsp;��һҳ&nbsp;<%end if%>
  <%if beg>1 then%> <a href="<%response.Write(towardpage)%>_p<%=page-1%>.html"><img src="http://www.zz91.com/zz91images/icon_ar.gif" border="0" align="absmiddle" /></a>&nbsp;
      <%end if%>
	    <%
		  for i=beg to enf
			  if cint(page)=i then
			   %><a href="<%response.Write(towardpage)%>_p<%=i%>.html" id=lblNav><b>&nbsp;<%=i%>&nbsp;</b></a>&nbsp;<%
			  else
			  %><a href="<%response.Write(towardpage)%>_p<%=i%>.html"><b><%=i%></b></a>&nbsp;<%end if%>
        <%
		  next
		  i=i+1
		  %>
    <%if enf<pg then%>&nbsp;<a href="<%response.Write(towardpage)%>_p<%=page+1%>.html"><img src="http://www.zz91.com/zz91images/icon_al.gif" border="0" align="absmiddle" /></a>
        <%end if%>
<%if totalpg-page>0 then%><a href="<%response.Write(towardpage)%>_p<%=page+1%>.html">��һҳ</a>&nbsp;<a href="<%response.Write(towardpage)%>_p<%=totalpg%>.html">ĩҳ</a>
<%else%>��һҳ ĩҳ<%end if%>
</div>