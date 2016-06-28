<!-- #include file="../dfd@#kang/dfd@#321)kang.asp" -->
<!-- #include file="function.asp" -->
<!-- #include file="sources/Md5.asp" -->
<%
server_v1=Cstr(Request.ServerVariables("HTTP_REFERER"))
server_v2=Cstr(Request.ServerVariables("SERVER_NAME"))
if mid(server_v1,8,len(server_v2))<>server_v2 then
response.write "您的操作有误，请重新登录<br>"
session("errtext")=1
response.Redirect("/cn/login.asp?url=/cn/guest_office_main.asp")
response.end
end if
windowip=Request.ServerVariables("REMOTE_HOST")
if session("log_comemail")<>"" or session("log_guestemail")<>"" then
response.Redirect("guest_office_main.asp")
response.End()
end if
Function IsValidEmail(Email)
	ValidFlag = False
	If (Email <> "") And (InStr(1, Email, "@") > 0) And (InStr(1, Email, ".") > 0) Then
	atCount = 0
	SpecialFlag = False
	For atLoop = 1 To Len(Email)
	atChr = Mid(Email, atLoop, 1)
	If atChr = "@" Then atCount = atCount + 1
	If (atChr >= Chr(32)) And (atChr <= Chr(44)) Then SpecialFlag = True
	If (atChr = Chr(47)) Or (atChr = Chr(96)) Or (atChr >= Chr(123)) Then SpecialFlag = True
	If (atChr >= Chr(58)) And (atChr <= Chr(63)) Then SpecialFlag = True
	If (atChr >= Chr(91)) And (atChr <= Chr(94)) Then SpecialFlag = True
	Next
	If (atCount = 1) And (SpecialFlag = False) Then
	BadFlag = False
	tAry1 = Split(Email, "@")
	UserName = tAry1(0)
	DomainName = tAry1(1)
	If (UserName = "") Or (DomainName = "") Then BadFlag = True
	If Mid(DomainName, 1, 1) = "." then BadFlag = True
	If Mid(DomainName, Len(DomainName), 1) = "." then BadFlag = True
	ValidFlag = True
	End If
	End If
	If BadFlag = True Then ValidFlag = False
	IsValidEmail = ValidFlag
End Function
	cemail=changeNum(trim(request.form("email")))
	cemail=lcase(cemail)
	cpwd=changeNum(request.form("pwd"))
	Response.Cookies("zz91")("comemail")=cemail
	Response.Cookies("zz91").Expires=Date()+1
	rf=ucase(request.form)
	if instr(rf,"'")>0 or instr(rf,"%27")>0 or instr(rf,";")>0 or instr(rf,"%3B")>0 then
		response.redirect "login.asp"
		response.end
	end if
	cemail=replace(trim(cemail),"'","''")
	cpwd=replace(trim(cpwd),"'","''")
	if cemail="" then
	    session("errtext")=5
		response.Redirect("/cn/login.asp?url=/cn/guest_office_main.asp")
		response.End()
	else
		if IsValidEmail(cemail)=False then
			session("errtext")=7
			response.Redirect("/cn/login.asp?url=/cn/guest_office_main.asp")
			response.End()
		end if
	end if
	if cpwd="" then
	    session("errtext")=6
		response.Redirect("/cn/login.asp?url=/cn/guest_office_main.asp")
		response.End()
	end if
	set rslog=server.CreateObject("ADODB.recordset")
	sql="select com_id,com_pass,vipflag,vip_check,com_SafePass from comp_info where com_email='"&cemail&"'"'找出注册用户
	rslog.open sql,conn,1,1
	if not rslog.eof and not rslog.bof then'用户存在
	    '****************************
		sqlblack="select * from comp_blacklist where com_id="&rslog("com_id")
		set rsblack=conn.execute(sqlblack)
		if not rsblack.eof then
		session("errtext")=2
		response.Redirect("/cn/login.asp?url=/cn/guest_office_main.asp")
		response.End()
		end if
		rsblack.close
		set rsblack=nothing
		'*********************
		if isnull(rslog("com_SafePass")) or trim(rslog("com_SafePass"))="" then
		    if cstr(rslog("com_pass"))=cstr(cpwd) then
			sqlsafe="update comp_info set com_SafePass='"&md5(cpwd,16)&"' where com_email='"&cemail&"'"
			conn.execute(sqlsafe)
			end if
		end if
		if cstr(rslog("com_SafePass"))=cstr(md5(cpwd,16)) then
			com_id=rslog("com_id")
			sqle="select com_id from comp_login where com_email='"&cemail&"'"
			set rse=conn.execute(sqle)
			if not rse.eof or not rse.bof then
			sqlp="update comp_login set logincount=logincount+1,lastlogintime=getdate(),com_id="&com_id&",viewip='"&windowip&"' where com_email='"&cemail&"'"
			else
			sqlp="insert into comp_login (com_id,com_email,logintime,lastlogintime,logincount,viewip) values("&com_id&",'"&cemail&"',getdate(),getdate(),1,'"&windowip&"')"
			end if
			conn.execute (sqlp)
			rse.close()
			set rse=nothing
	        '***************************记录登录情况/start
			'sqlcu="select logincount from comp_login where com_id="&com_id&""
			'set rscu=conn.execute(sqlcu)
			'if not rscu.eof then
			'Alcount=rscu(0)
			'else
			'Alcount=1
			'end if
			'rscu.close
			'set rscu=nothing
			
			sqllogin="select id from comp_logincount where com_id="&com_id&" and Fdate='"&date()&"'"
			set rslogin=conn.execute(sqllogin)
			if not rslogin.eof then
			
			
			
			sqlin="update comp_logincount set Lcount=Lcount+1 where com_id="&com_id&" and Fdate='"&date()&"'"
			conn.execute(sqlin)
			else
			'**************
			
			
			sqlin="insert into comp_logincount (com_id,Fdate) values("&com_id&",'"&date()&"')"
			conn.execute(sqlin)
			end if
			rslogin.close
			set rslogin=nothing
			'***************************记录登录情况/end
			if rslog("vipflag")=2 and rslog("vip_check")=1 then
			If CBool(Request.Form("AutoGet")) or Request.Form("AutoGet")<>"" Then
			Response.Cookies("zz91")("comemail")=cemail
			Response.Cookies("zz91").Expires=Date()+1
			end if
			session("log_comemail")=cemail
			session("log_comid")=rslog("com_id")
			session("vipflag")=rslog("vipflag")
			session("vipcheck")=rslog("vip_check")
			session("log_guestemail")=""
			session("log_en")="cn"
			response.Redirect(request("url"))
			else
			If CBool(Request.Form("AutoGet")) or Request.Form("AutoGet")<>"" Then
			Response.Cookies("zz91")("comemail")=cemail
			Response.Cookies("zz91").Expires=Date()+1
			end if
			session("log_guestemail")=cemail
			session("log_comid")=rslog("com_id")
			session("vipflag")=rslog("vipflag")
			session("vipcheck")=rslog("vip_check")
			session("log_comemail")=""
			session("log_en")="cn"
			response.Redirect(request("url"))
			end if
		else'密码错误
		    if rslog("com_pass")=cpwd and cstr(rslog("com_SafePass"))<>cstr(md5(cpwd,16)) then
				sqlsafe="update comp_info set com_SafePass='"&md5(cpwd,16)&"' where com_email='"&cemail&"'"
				conn.execute(sqlsafe)
			end if
		session("errtext")=3
		response.Redirect("/cn/login.asp?url=/cn/guest_office_main.asp")
		response.end
		end if
	else'用户不存在
		session("errtext")=4
		response.Redirect("/cn/login.asp?url=/cn/guest_office_main.asp")
		response.end
	end if
	rslog.close
	set rslog=nothing
	conn.close
	set conn=nothing
%>
