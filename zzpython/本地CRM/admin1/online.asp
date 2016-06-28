<%
dim ip
sql="Delete FROM admin_online WHERE DATEDIFF(s,outime,getdate())>150"
Conn.Execute sql
path_info=request.ServerVariables("URL")
path_info=path_info&"?"&Request.ServerVariables("QUERY_STRING")
stats=path_info
IP=replace(Request.ServerVariables("REMOTE_HOST"),".","")''////获取IP并消去IP中的"."
username=session("admin_user")
if session("personid")="" then
	gname="游客"
	personid=0
else
	gname=username
	personid=session("personid")
end if
sql="select * from admin_online where personid='"&personid&"'"
set rs=server.createobject("adodb.recordset")
rs.open sql,conn,1,2
if rs.eof or rs.bof then
rs.addnew()
rs("ip")=ip
rs("visitime")=now
end if
rs("outime")=now()
rs("guestname")=gname
rs("stats")=cstr(stats)
rs("personid")=personid
rs.update()
rs.close
set rs=nothing
%>