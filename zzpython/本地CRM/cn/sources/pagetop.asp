
<%
page=Request("page")
if isnumeric(page) then
    else
	    page=1
    end if
    
if page="" then 
page=1 
end if
'dim total,rn
if rn="" then
	rn=25  '�趨ÿҳ��ʾ��
	'else
	'rn=cint(request("rn"))
	end if
	rs.pagesize=rn
	total=rs.recordcount
    totalpg=rs.pagecount	
    if totalpg-page<0 then
   	page=1                                                                                                         
    end if
if not rs.eof  then
		rs.pagesize=rn
		rs.AbsolutePage =1
		if page<>"" then 
		rs.AbsolutePage =page
		end if
		RowCount=rn
		beginpage=rn*(page-1)
		if beginpage=0 then
		beginpage=1
		end if
		if total<rn then
		endpage=total
		else 
			if clng(page)>=totalpg then
			endpage=total
			else
			endpage=rn*(page)
			end if
		end if
end if
'temp="�ϼ�<font color='#ff0000'>"& total &"</font>����¼  ҳ�Σ�<font color='#ff0000'>" & page & "</font>/<b>" & totalpg & "</b>ҳ <font color='#ff0000'>" & rn & "</font>��/ҳ ��ҳ��ʾ��<font color='#ff0000'>" & beginpage &"</font>-<font color='#ff0000'>"& endpage &"</font>�� "
'response.Write(temp)
%>

