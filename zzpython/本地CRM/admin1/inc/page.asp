<table width="100%" border="0" cellspacing="0" cellpadding="0">
<%
dim self
%>
			   <form method="get" action="<%=self%>?<%=request("sear")%>" name="FrmPage" onsubmit="return FrmPage_Validator(this)">
  <tr>
    <td>             <font color="#666666"> 页数 &nbsp;第
		   
		  
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
	    <%if beg>1 then%>
	    <a href="<%=self%>?page=<%=page-1%>&<%=sear%>"> << </a> 
	    <%end if%>
	    <%
		  for i=beg to enf
			   if cint(page)=i then
			   %>
		    [<a href="<%=self%>?page=<%=i%>&<%=sear%>"><%=i%></a>]
		    <%
			  else
			  %>
		    <a href="<%=self%>?page=<%=i%>&<%=sear%>"><%=i%></a>
		    <%end if%>
        <%
		  
		  next
		  i=i+1
		  %>
        <%if enf<pg then%>
	        <a href="<%=self%>?page=<%=page+1%>&<%=sear%>"> >> </a> 
        <%end if%>
		  
		  
        
      页</font>
	  
	  </td>
    <td align="right"><font color="#666666">
      <%if page>1 then%>
      <a href="<%=self%>?page=1&<%=sear%>">首页</a> <a href="<%=self%>?page=<%=page-1%>&<%=sear%>">上一页</a>
      <%else%>
首页上一页
<%end if%>
<%if totalpg-page>0 then%>
<a href="<%=self%>?page=<%=page+1%>&<%=sear%>">下一页</a> <a href="<%=self%>?page=<%=totalpg%>&<%=sear%>">末页</a>
<%else%>
下一页末页
<%end if%>
    </font></td>
  </tr></form>
</table>
