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
      <a href="<%=self%>_p1&<%=sear%>.html">首页</a> &nbsp;<a href="<%=self%>_p<%=page-1%>&<%=sear%>">上一页</a>
      <%else%>
首页&nbsp;上一页
<%end if%>
 <%if beg>1 then%>
	    <a href="<%=self%>_p<%=page-1%>&<%=sear%>"> << </a> &nbsp;
	    <%end if%>
	    <%
		  for i=beg to enf
			   if cint(page)=i then
			   %>
		    <a href="<%=self%>_p<%=i%>&<%=sear%>" id=lblNav>[<b><%=i%></b>]</a>&nbsp;
		    <%
			  else
			  %>
		    <a href="<%=self%>_p<%=i%>&<%=sear%>"><%=i%></a>&nbsp;
		    <%end if%>
        <%
		  
		  next
		  i=i+1
		  %>
        <%if enf<pg then%>
	       &nbsp; <a href="<%=self%>_p<%=page+1%>&<%=sear%>"> >> </a> 
        <%end if%>
	<%if totalpg-page>0 then%>
<a href="<%=self%>_p<%=page+1%>&<%=sear%>">下一页</a>&nbsp; <a href="<%=self%>_p<%=totalpg%>&<%=sear%>">末页</a>
<%else%>
下一页末页
<%end if%>
 