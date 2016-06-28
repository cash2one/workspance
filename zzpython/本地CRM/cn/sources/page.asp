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
<%
'dim self
page=request("page")
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
      <%if page>1 then%>
      <a href="<%=self%>?page=1&<%=sear%>">首页</a> &nbsp;<a href="<%=self%>?page=<%=page-1%>&<%=sear%>">上一页</a>
      <%else%>
首页&nbsp;上一页
<%end if%>
 <%if beg>1 then%>
	    <a href="<%=self%>?page=<%=page-1%>&<%=sear%>"><img src="http://www.zz91.com/zz91images/icon_ar.gif" border="0" align="absmiddle" /></a>
	    <%end if%>
	    <%
		  for i=beg to enf
			   if cint(page)=i then
			   %>
		    <a href="<%=self%>?page=<%=i%>&<%=sear%>" id=lblNav><b>&nbsp;<%=i%>&nbsp;</b></a>
		    <%
			  else
			  %>
		    <a href="<%=self%>?page=<%=i%>&<%=sear%>"><b><%=i%></b></a>
		    <%end if%>
        <%
		  next
		  i=i+1
		  %>
        <%if enf<pg then%>
	       <a href="<%=self%>?page=<%=page+1%>&<%=sear%>"><img src="http://www.zz91.com/zz91images/icon_al.gif" border="0" align="absmiddle" /></a> 
        <%end if%>
	<%if totalpg-page>0 then%>
<a href="<%=self%>?page=<%=page+1%>&<%=sear%>">下一页</a>&nbsp; <a href="<%=self%>?page=<%=totalpg%>&<%=sear%>">末页</a>
<%else%>
下一页 末页
<%end if%>
 