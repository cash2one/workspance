<script Language="JavaScript"><!--
function FrmPage_Validator(theForm)
{

  if (theForm.page.value == "")
  {
    alert("���� ҳ�� ��������ֵ��");
    theForm.page.focus();
    return (false);
  }

  if (theForm.page.value.length < 1)
  {
    alert("�� ҳ�� ���У����������� 1 ���ַ���");
    theForm.page.focus();
    return (false);
  }

  var checkOK = "0123456789-,";
  var checkStr = theForm.page.value;
  var allValid = true;
  var decPoints = 0;
  var allNum = "";
  for (i = 0;  i < checkStr.length;  i++)
  {
    ch = checkStr.charAt(i);
    for (j = 0;  j < checkOK.length;  j++)
      if (ch == checkOK.charAt(j))
        break;
    if (j == checkOK.length)
    {
      allValid = false;
      break;
    }
    if (ch != ",")
      allNum += ch;
  }
  if (!allValid)
  {
    alert("�� ҳ�� ���У�ֻ������ ���� ���ַ���");
    theForm.page.focus();
    return (false);
  }
  return (true);
}
//--></script>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<%
dim self

%>
			   <form method="get" action="<%=self%>?<%=request("sear")%>" name="FrmPage" onsubmit="return FrmPage_Validator(this)">
  <tr>
    <td>             <div align="right"><font color="#666666"> 
	
                        <%if page>1 then%>
						<a href="<%=self%>?page=1&<%=sear%>">��ҳ</a>
                        <a href="<%=self%>?page=<%=page-1%>&<%=sear%>">��һҳ</a>
                        <%else%>
          ��ҳ��һҳ
          <%end if%>
          <%if totalpg-page>0 then%>
          <a href="<%=self%>?page=<%=page+1%>&<%=sear%>">��һҳ</a>
		  <a href="<%=self%>?page=<%=totalpg%>&<%=sear%>">ĩҳ</a>
          <%else%>
          ��һҳĩҳ
          <%end if%>
           ҳ�� &nbsp;
		   
		  
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
		  
		  
          <input name="comp_id" type="hidden" value="<%=request("comp_id")%>">
		  <input type="hidden" name="kind" value=<%=kind%>>
					  
					  <input type="hidden" name="n" value="<%=n%>">
          </font></div>
                
              
	  </td>
  </tr></form>
</table>
