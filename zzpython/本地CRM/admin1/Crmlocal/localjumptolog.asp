<!--#include file="include/include.asp"-->

<%
localip=Request.ServerVariables("REMOTE_ADDR")
posttype=Request.ServerVariables("REQUEST_METHOD")
server_v1=Cstr(Request.ServerVariables("HTTP_REFERER"))
server_v2=Cstr(Request.ServerVariables("SERVER_NAME"))
if posttype="post" then
	if mid(server_v1,8,len(server_v2))<>server_v2 then
		response.write "错误<br>"
		response.end
	end if
end if
if session("personid")="" or isnull(session("personid")) then
	if Request.Cookies("personid")<>"" then
	session("userID")=Request.Cookies("userID")
	session("personid")=Request.Cookies("personid")
	session("lmcode")=Request.Cookies("lmcode")
	session("Partadmin")=Request.Cookies("Partadmin")
	session("userClass")=Request.Cookies("userClass")
	else
	response.Write("<script>parent.window.location='/admin1/loginofrc.asp';</script>")
	%>
	<a href="loginofrc.asp" target="_parent">重新登录</a><%
	response.End()
	end if
end if

if session("personid")<>"" then
ptime=DatePart("h",now())
wtime=DatePart("w",now())
sqluu="select Closepart,closeflag,userqx from users where id="&session("personid")
set rsuu=conn.execute(sqluu)
if not rsuu.eof then
zguserqx=rsuu("userqx")
   if rsuu("closepart")=1 then
	   if cint(ptime)>=20 or cint(ptime)<8 or cint(wtime)=7 or cint(wtime)=1  then
		  response.Write("错误！请与管理员联系！")
		  response.End()
	   end if
   end if 
end if
end if
rsuu.close
set rsuu=nothing
%>
<%
lmurl=Request.ServerVariables("SCRIPT_NAME")
lmarray=split(trim(lmurl),"/",-1,1)
lmu=lmarray(2)
lmmarray=split(trim(lmu),"_",-1,1)
lmmu=lmmarray(0)
 sql="select qx from user_qx where uname='"&session("personid")&"'"
 set rs=server.CreateObject("adodb.recordset")
 rs.open sql,conn,1,1
 if not rs.eof then
 	qx=rs("qx")
 end if
 rs.close()
 set rs=nothing
sqlw="select code from cate_qx where link = '"&lmu&"' order by id asc"
set rsw=server.CreateObject("adodb.recordset")
rsw.open sqlw,conn,1,1
if not rsw.eof or not rsw.bof then
	codebb=rsw("code")
	Response.Cookies("lmcode")=rsw("code")
	session("lmcode")=rsw("code")
    bbb=0
	if qx<>"" then
		mqx=split(qx,",")
		for jj=0 to ubound(mqx)
		  if cstr(rsw("code"))=cstr(trim(mqx(jj))) then
			bbb=1
		  end if
		next
	end if
else
bbb=1
end if
rsw.close()
set rsw=nothing
if bbb=0 then
	response.Redirect("err.asp")
end if
 '*******************************************
 %>


