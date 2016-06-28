<%@ Language=VBScript %>
<!-- #include file="../checkuser.asp" -->
<!-- #include file="../include/adfsfs!@#.asp" -->
<!--#include file="../include/include.asp"-->
<%
mya=split(request("qx"),",",-1,1)
userid=request("userid")
for i=0 to ubound(mya)-1
	'myb=split(request("qxgn"&trim(mya(i))),",",-1,1)
	'response.Write(myb(0))
	set rsq=server.createobject("adodb.recordset")
	sqlq="select * from user_gnqx where personid='"&request("uname")&"' and lmid='"&trim(mya(i))&"'"
	rsq.open sqlq,conn,1,2
	if not rsq.eof then
	rsq("qx")=request("qxgn"&trim(mya(i)))
	else
	rsq.addnew()
	rsq("personid")=request("uname")
	rsq("lmid")=trim(mya(i))
	rsq("qx")=request("qxgn"&trim(mya(i)))
	end if
	rsq.update()
	rsq.close()
	set rsq=nothing
next
   set rs=server.createobject("adodb.recordset")
   sql="select * from user_qx where uname='"&request("uname")&"'"
   rs.open sql,conn,1,2
		if not rs.eof then
		rs("qx")=request("qx")
		else
		rs.addnew()
		rs("uname")=request("uname")
		rs("qx")=request("qx")
        end if 
		rs.update()
rs.close()
set rs=nothing
response.Redirect("admin_user_list.asp?uname="&request("uname")&"&userid="&userID)
%><!-- #include file="../../conn_end.asp" -->
