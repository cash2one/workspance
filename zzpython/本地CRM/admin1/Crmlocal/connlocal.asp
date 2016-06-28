<!-- #include file="../include/adfsfs!@#.asp" -->
<%
set connserver=server.CreateObject("ADODB.connection")
strconnserver="Provider=SQLOLEDB.1;driver={SQL Server};server=www.zz91.com,2433;uid=rcu;pwd=dfsdf!@!@#sdfds$#^!*dfdsf4343749d;database=rcu"
connserver.open strconnserver
function closeconn()
	conn.close
	set conn=nothing
	connserver.close
	set connserver=nothing
end function
%>