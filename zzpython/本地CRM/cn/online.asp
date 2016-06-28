<%
dim ip
''////删除180秒内不活动的在线记录.
sql="Delete FROM comp_online WHERE DATEDIFF(s,outime,getdate())>150"
Conn.Execute sql
path_info=request.ServerVariables("URL")
path_info=path_info&"?"&Request.ServerVariables("QUERY_STRING")
stats=path_info
'stats=replace(stats,"/","*")
IP=replace(Request.ServerVariables("REMOTE_HOST"),".","")''////获取IP并消去IP中的"."
username=session("log_guestemail")
if session("log_comid")="" then
	gname="游客"
	com_id=0
else
	gname=username
	com_id=session("log_comid")
end if
sql="select * from comp_online where ip='"&ip&"'"
set rs=server.createobject("adodb.recordset")
rs.open sql,conn,1,2
if rs.eof or rs.bof then
rs.addnew()
rs("ip")=ip
rs("visitime")=now
	'sql="insert into online(id,guestname,stats,visitime,outime,base_id) values ('"&ip&"','"&gname&"','"&stats&"',getdate(),getdate(),"&gid&")"
else
	'sql="update online set outime=getdate(),stats='"&stats&"',guestname='"&gname&"',base_id="&gid&" where id='"&ip&"'"
end if

rs("outime")=now()
rs("guestname")=gname
rs("stats")=cstr(stats)
rs("com_id")=com_id
rs.update()
rs.close
set rs=nothing
'response.Write(stats)
%>