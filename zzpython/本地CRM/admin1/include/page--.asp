<table width="100%" border="0" cellspacing="0" cellpadding="0">
			  
  <tr>
    <td nowrap="nowrap">ҳ��:<strong><font color="#FF0000"><%=page%></font></strong>/<font color="#FF0000"><%=totalpg%></font> ÿҳ:<font color="#FF0000"><%=page_size%></font> ����:<font color="#FF0000"><%=total%></font></td>
    <td>             <font color="#666666"> ҳ�� &nbsp;��
		   
		  
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
		  
		  
        
      ҳ</font>	  </td>
    <td align="right"><font color="#666666">
      <%if page>1 then%>
      <a href="<%=self%>?page=1&<%=sear%>">��ҳ</a> <a href="<%=self%>?page=<%=page-1%>&<%=sear%>">��һҳ</a>
      <%else%>
��ҳ��һҳ
<%end if%>
<%if totalpg-page>0 then%>
<a href="<%=self%>?page=<%=page+1%>&<%=sear%>">��һҳ</a> <a href="<%=self%>?page=<%=totalpg%>&<%=sear%>">ĩҳ</a>
<%else%>
��һҳĩҳ
<%end if%>
    </font></td>
    <td align="right">
	<%
	'----------ȥ��"page="������ֹ�ظ�----------
    str=request.ServerVariables("QUERY_STRING")
    'Dim queryStr
	'response.Write(Instr(str,"page="))
	if Instr(str,"page=")>0 then
	myquerystr=split(str,"page=")
	pagevalue=split(myquerystr(1),"&")(0)
	'response.Write(pagevalue)
	
	if Instr(str,"&page="&pagevalue&"&")>0 then
	strurl=replace(str,"&page="&pagevalue&"&","")
	end if
	if Instr(str,"&page="&pagevalue)>0 then
	strurl=replace(str,"&page="&pagevalue,"")
	end if
	
	if Instr(str,"page="&pagevalue&"&")>0 then
	strurl=replace(str,"page="&pagevalue&"&","")
	end if
	if Instr(str,"page=")>0 then
	strurl=replace(str,"page="&pagevalue,"")
	end if
	strurl=sear
	end if
	'response.Write(sear)
   
    If Len(urlPrefix)=0 Then urlPrefix=request.ServerVariables("script_name")
    If inStr(urlPrefix,"?")>0 Then   'ָ��urlPrefix�д���queryString����
      pageUrl=urlPrefix
	  
     Else
      pageUrl=urlPrefix&"?"&strurl
	  
    End If
	%>
	
	<select name="pageselect" onchange="window.location='<%=pageUrl%>&page='+this.value">
	  <%
	  
	  for pi=1 to totalpg
	  selectpg=""
	  if cint(pi)=cint(page) then
	  selectpg="selected"
	  end if
	  %>
      <option value="<%=pi%>" <%=selectpg%>>��<%=pi%>ҳ</option>
	  <%next%>
    </select>
    </td>
  </tr>
</table>
