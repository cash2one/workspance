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
      <a href="BaoJia-baojia<%=request("s_id")%>-<%=request("pl_id")%>-<%=request("b_id")%>-p1.html">��ҳ</a> &nbsp;<a href="BaoJia-baojia<%=request("s_id")%>-<%=request("pl_id")%>-<%=request("b_id")%>-p<%=page-1%>.html">��һҳ</a>
      <%else%>
��ҳ&nbsp;��һҳ
<%end if%>
 <%if beg>1 then%>
	    <a href="BaoJia-baojia<%=request("s_id")%>-<%=request("pl_id")%>-<%=request("b_id")%>-p<%=page-1%>.html"> << </a> &nbsp;
	    <%end if%>
	    <%
		  for i=beg to enf
			   if cint(page)=i then
			   %>
		    <a href="BaoJia-baojia<%=request("s_id")%>-<%=request("pl_id")%>-<%=request("b_id")%>-p<%=i%>.html" id=lblNav>[<b><%=i%></b>]</a>&nbsp;
		    <%
			  else
			  %>
		    <a href="BaoJia-baojia<%=request("s_id")%>-<%=request("pl_id")%>-<%=request("b_id")%>-p<%=i%>.html"><%=i%></a>&nbsp;
		    <%end if%>
        <%
		  
		  next
		  i=i+1
		  %>
        <%if enf<pg then%>
	       &nbsp; <a href="BaoJia-baojia<%=request("s_id")%>-<%=request("pl_id")%>-<%=request("b_id")%>-p<%=page+1%>.html"> >> </a> 
        <%end if%>
	<%if totalpg-page>0 then%>
<a href="BaoJia-baojia<%=request("s_id")%>-<%=request("pl_id")%>-<%=request("b_id")%>-p<%=page+1%>.html">��һҳ</a>&nbsp; <a href="BaoJia-baojia<%=request("s_id")%>-<%=request("pl_id")%>-<%=request("b_id")%>-p<%=totalpg%>.html">ĩҳ</a>
<%else%>
��һҳĩҳ
<%end if%>
 