<%
temp="��ǰ��ʾ��<font color='#CC6600'><b> " & beginpage &" </b></font>��<font color='#CC6600'><b> "& endpage &" </b></font>������<font color='#CC6600'><b> " & totalpg & " </b></font>ҳ "
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
        <a href="<%=self%>?page=1&<%=sear%>">��ҳ</a>&nbsp; <a href="<%=self%>?page=<%=page-1%>&<%=sear%>">��һҳ</a>
        <%else%>
  ��ҳ&nbsp;��һҳ
  <%end if%>
  <%if totalpg-page>0 then%>
  <a href="<%=self%>?page=<%=page+1%>&<%=sear%>">��һҳ</a> &nbsp;<a href="<%=self%>?page=<%=totalpg%>&<%=sear%>">ĩҳ</a>
  <%else%>
  ��һҳ&nbsp;ĩҳ
<%end if%></div>