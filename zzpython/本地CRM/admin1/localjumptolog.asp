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
	session("admin_user")=request.Cookies("admin_user")
	session("Partadmin")=Request.Cookies("Partadmin")
	session("userClass")=Request.Cookies("userClass")
	else
	response.Redirect("/admin1/loginofrc.asp")
	%>
	<a href="loginofrc.asp" target="_parent">重新登录</a><%
	response.End()
	end if
end if
ptime=DatePart("h",now())
wtime=DatePart("w",now())

lmurl=Request.ServerVariables("SCRIPT_NAME")
lmurlTemp=replace(lmurl,"/admin1/","")

lmurlcode=Request.ServerVariables("QUERY_STRING")
lmarray=split(trim(lmurl),"/")
lmu=lmarray(ubound(lmarray))
 
 qxid=0
 sql="select userdengji,userqx from users where id="&session("personid")&""
 set rs=conn.execute(sql)
 if not rs.eof or not rs.bof then
 	userdengji=rs(0)
	zguserqx=rs("userqx")
 end if
 rs.close
 set rs=nothing
 'response.Write(Request.Cookies("personid"))
 if isnull(userdengji) then
    response.Write("帐号权限没有设置好！请联系帐号添加者")
	response.End()
 end if
 if isnull(userdengji) then userdengji=""
 arrUserdengji=split(userdengji,",")
 qxid=""
 for i=0 to ubound(arrUserdengji)
	 sql="select top 1 id from users where userdengji='"&trim(arrUserdengji(i))&"' and userid='1305'"
	 set rs=conn.execute(sql)
	 if not rs.eof or not rs.bof then
		qxid=qxid&rs(0)&","
	 end if
	 rs.close
	 set rs=nothing
 next
 if qxid<>"" then
 	qxid=left(qxid,len(qxid)-1)
 end if
 
 if session("userid")="10" then qxid=session("personid")
 'response.Write(qxid)
 arrQxid=split(qxid,",")
 qx=""
 for i=0 to ubound(arrQxid)
	 sql="select myqx from user_qx where uname="&trim(arrQxid(i))&""
	 set rs=server.CreateObject("adodb.recordset")
	 rs.open sql,conn,1,1
	 if not rs.eof then
		qx=qx&","&rs("myqx")
	 end if
	 rs.close()
	 set rs=nothing
 next 
 'qx=left(qx,len(qx)-1)
 
'if session("userid")="10" then
	lmcode=request.QueryString("lmcode")
	if lmcode="" then lmcode=request.Form("lmcode")
	    sqlw="select id,link from cate_qx where link like '"&lmurlTemp&"%' and closeflag=1 order by id asc"
		set rsw=server.CreateObject("adodb.recordset")
		rsw.open sqlw,conn,1,1
		tempDotype=""
		if not rsw.eof or not rsw.bof then
			while not rsw.eof
				bbbb=0
				if qx<>"" then
					mqx=split(qx,",")
					for jj=0 to ubound(mqx)
					  if cstr(rsw("id"))=cstr(trim(mqx(jj))) then
						bbbb=1
					  end if
					next
				end if
				if bbbb=1 then
					link=lcase(rsw("link"))
					arrlink=split(link,"dotype=")
					if ubound(arrlink)>0 then
						arrarrlink=split(arrlink(1),"&")
						dotypevalue=trim(arrarrlink(0))
                        tempDotype=tempDotype&dotypevalue&"|"
					end if
				end if
				rsw.movenext
			wend
		else
			bbb=1
		end if
		rsw.close()
		set rsw=nothing
		sqlw="select id from cate_qx where id='"&lmcode&"' order by id asc"
		set rsw=server.CreateObject("adodb.recordset")
		rsw.open sqlw,conn,1,1
		if not rsw.eof or not rsw.bof then
			while not rsw.eof
				codebb=rsw("id")
				bbb=0
				if qx<>"" then
					mqx=split(qx,",")
					for jj=0 to ubound(mqx)
					  if cstr(rsw("id"))=cstr(trim(mqx(jj))) then
						bbb=1
					  end if
					next
				end if
				rsw.movenext
			wend
			'response.Write(bbb)
		else
			'response.Write(qx)
			bbb=0
			sqlw="select id from cate_qx where link like '"&lmurlTemp&"%' order by id asc"
			set rsw=server.CreateObject("adodb.recordset")
			rsw.open sqlw,conn,1,1
			if not rsw.eof or not rsw.bof then
				while not rsw.eof
					codebb=rsw("id")
					if qx<>"" then
						mqx=split(qx,",")
						for jj=0 to ubound(mqx)
						  if cstr(rsw("id"))=cstr(trim(mqx(jj))) then
							bbb=1
						  end if
						next
					end if
					'response.Write(bbb)
					rsw.movenext
				wend
			else
				bbb=1
			end if
			'response.Write(lmurlTemp)
			'response.Write(bbb&"a")
		end if
		
		rsw.close()
		set rsw=nothing
		if bbb=0 then
			response.Write("对不起！你没有权限查看")
			response.End()
		end if
 '*******************************************
luyinurl="/admin1/crmlocal/recordService/play.asp?mdname="
luyindownloadurl="http://192.168.2.27/freeiris2/cpanel/record/1/"
%>


