<!-- #include file="include/adfsfs!@#.asp" -->
<!--#include file="../cn/sources/Md5.asp"-->
<%
On Error Resume Next
splitString = "<===>"
ljstr=splitString
allstr="fromWskwebData.returnLoginValue"&ljstr
windowip=Request.ServerVariables("REMOTE_HOST")
username=trim(request.QueryString("username"))
username=lcase(replace(username," ",""))
username=replace(trim(username),"'","''")

cpwd=request.QueryString("pwd")
cpwd=replace(trim(cpwd),"'","''")
if username="" then
	allstr=allstr&"0"
	response.Write(allstr)
	response.End()
end if
if cpwd="" then
	allstr=allstr&"0"
	response.Write(allstr)
	response.End()
end if
set rslog=server.CreateObject("ADODB.recordset")
sql="select id,password,SafePass,name,userqx,userGroup,userid,Partuserid from users where name='"&username&"' and chatflag=1 and chatclose=1"'�ҳ�ע���û�
rslog.open sql,conn,1,1
if not rslog.eof and not rslog.bof then'�û�����
	'****************************
	if cstr(rslog("SafePass"))=cstr(md5(cpwd,16)) then
		allstr=allstr&rslog("id")
	else'�������
		allstr=allstr&"0"
	end if
else'�û�������
	allstr=allstr&"0"
end if
rslog.close
set rslog=nothing
conn.close
set conn=nothing
response.Write(allstr)	

%>

