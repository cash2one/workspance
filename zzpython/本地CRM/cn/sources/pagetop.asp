
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
	rn=25  '设定每页显示数
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
'temp="合计<font color='#ff0000'>"& total &"</font>条记录  页次：<font color='#ff0000'>" & page & "</font>/<b>" & totalpg & "</b>页 <font color='#ff0000'>" & rn & "</font>条/页 本页显示第<font color='#ff0000'>" & beginpage &"</font>-<font color='#ff0000'>"& endpage &"</font>条 "
'response.Write(temp)
%>

