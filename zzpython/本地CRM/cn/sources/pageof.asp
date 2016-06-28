<%
temp="当前显示第<font color='#CC6600'><b> " & beginpage &" </b></font>—<font color='#CC6600'><b> "& endpage &" </b></font>条，共<font color='#CC6600'><b> " & totalpg & " </b></font>页 "
%>
<style type="text/css">
<!--
#pageleft {
	float: left;
	width: 350px;
}
-->
</style>


<div id="pageleft"><%response.Write(temp)%></div>
<div align="right" id="pagetop"><font color="#666666">
  <%if page>1 then%>
        <a href="<%=self%>?page=1&<%=sear%>">首页</a>&nbsp; <a href="<%=self%>?page=<%=page-1%>&<%=sear%>">上一页</a>
        <%else%>
  首页&nbsp;上一页
  <%end if%>
  <%if totalpg-page>0 then%>
  <a href="<%=self%>?page=<%=page+1%>&<%=sear%>">下一页</a> &nbsp;<a href="<%=self%>?page=<%=totalpg%>&<%=sear%>">末页</a>
  <%else%>
  下一页&nbsp;末页
<%end if%></div>