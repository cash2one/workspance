<%

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
      <a href="<%=self%>?page=1&<%=sear%>">首页</a>&nbsp;<a href="<%=self%>?page=<%=page-1%>&<%=sear%>">上一页</a>
      <%else%>
首页&nbsp;上一页
<%end if%>
 <%if beg>1 then%>
	    <a href="<%=self%>?page=<%=page-1%>&<%=sear%>"> << </a>&nbsp;<%end if%>
	    <%
		  for i=beg to enf
			   if cint(page)=i then
			   %>
		    <a href="<%=self%>?page=<%=i%>&<%=sear%>" id=lblNav><%=i%></a>&nbsp;<%
			  else
			  %>
		    <a href="<%=self%>?page=<%=i%>&<%=sear%>"><%=i%></a>&nbsp;<%end if%>
        <%
		  
		  next
		  i=i+1
		  %>
        <%if enf<pg then%>
	     &nbsp;<a href="<%=self%>?page=<%=page+1%>&<%=sear%>">>></a> 
        <%end if%>
	<%if totalpg-page>0 then%>
<a href="<%=self%>?page=<%=page+1%>&<%=sear%>">下一页</a>&nbsp;<a href="<%=self%>?page=<%=totalpg%>&<%=sear%>">末页</a>
<%else%>
下一页 末页
<%end if%>
共<%=total%>条/<%=totalpg%>页
 