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
      <a href="<%=self%>?page=1&<%=sear%>">��ҳ</a>&nbsp;<a href="<%=self%>?page=<%=page-1%>&<%=sear%>">��һҳ</a>
      <%else%>
��ҳ&nbsp;��һҳ
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
<a href="<%=self%>?page=<%=page+1%>&<%=sear%>">��һҳ</a>&nbsp;<a href="<%=self%>?page=<%=totalpg%>&<%=sear%>">ĩҳ</a>
<%else%>
��һҳ ĩҳ
<%end if%>
��<%=total%>��/<%=totalpg%>ҳ
 