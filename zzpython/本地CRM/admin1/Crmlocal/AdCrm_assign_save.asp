<!-- #include file="../include/ad!$#5V.asp" -->
<!-- #include file="../localjumptolog.asp" -->
<!-- #include file="../../cn/function.asp" -->
<!--#include file="../include/include.asp"-->
<%
 selectcb=request("selectcb")
 dostay=request("dostay")
 maxrccomp=80
 
if selectcb<>"" and dostay="selec1twastecom" then
    arrcom=split(selectcb,",")
	
	for i=0 to ubound(arrcom)
	
    sql="select com_id from crm_adwaste where com_id="&trim(arrcom(i))&""
	set rs=conn.execute(sql)
	
	if not rs.eof then
    sqlu="update crm_adwaste set waste=1 where com_id="&trim(arrcom(i))&" AND com_id in (select com_id from crm_assignAD where personid="&session("personid")&")"
	conn.execute sqlu,ly
	else
		sqlm="select com_id from crm_assignAD where com_id="&trim(arrcom(i))&" and personid="&session("personid")&""
		set rsm=conn.execute(sqlm)
		if not rsm.eof then
			sqlu="insert into crm_adwaste(com_id,waste) values("&trim(arrcom(i))&",1)"
		    conn.execute sqlu,ly
			'------------写入客户分配记录
				com_id=trim(arrcom(i))
				sDetail="广告部"&request.Cookies("admin_user")&"放入废品池"
				sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&trim(arrcom(i))&","&session("personid")&",'"&sDetail&"')"
				conn.execute(sqlp)
		end if
		rsm.close
		set rsm=nothing
	end if
	rs.close
	set rs=nothing
	
	
	if ly then
		response.Write("<script>alert('成功！该客户已经放到“废品池”')</script>")
	else
		response.Write("<script>alert('失败！该客户不是您的客户不能放到“废品池”')</script>")
	end if
	next
	
    if request("closed")="1" then
		response.Write("<script>parent.parent.window.close()</script>")
		closeconn()
		response.End()
	else
		response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
		closeconn()
		response.End()
	end if
 end if
'--------------------------------
'*客户共享
'------------------
if selectcb<>"" and dostay="selec1tToshare" then
    arrcom=split(selectcb,",")
	for i=0 to ubound(arrcom)
		sqlp="select com_id from crm_assignAD where com_id = "& trim(arrcom(i)) &" and personid="&session("personid")&""
		set rsp=conn.execute(sqlp)
		if not rsp.eof then
			sql="select com_id from Crm_Comp_Share where com_id = "& trim(arrcom(i)) &""
			set rsc=conn.Execute(sql)
			if rsc.eof then
				sqli="insert into Crm_Comp_Share(com_id,personid,salesType) values("& trim(arrcom(i)) &","&session("personid")&",0)"
				conn.execute sqli,ly
			else
				sqli="update Crm_Comp_Share set salesType=0 where com_id = "& trim(arrcom(i)) &""
				conn.execute sqli,ly
			end if
			    if ly then
					response.Write("<script>alert('成功！已放入我的共享客户区')</script>")
					
				else
					response.Write("<script>alert('失败！不能把该客户设置为共享客户')</script>")
				end if
		end if
		rsp.close
		set rsp=nothing
	next
	if request("closed")="1" then
		response.Write("<script>parent.parent.window.close()</script>")
		closeconn()
		response.End()
	else
		response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
		closeconn()
		response.End()
	end if
	closeconn()
	response.End()
end if

if selectcb<>"" and dostay="outselec1tToshare" then
    arrcom=split(selectcb,",",-1,1)
	for i=0 to ubound(arrcom)
		sqlp="select com_id from crm_assignAD where com_id = "& trim(arrcom(i)) &" and personid="&session("personid")&""
		set rsp=conn.execute(sqlp)
		if not rsp.eof then
			sql="select com_id from Crm_Comp_Share where com_id = "& trim(arrcom(i)) &""
			set rsc=conn.Execute(sql)
			if not rsc.eof then
				sqli="delete from Crm_Comp_Share where com_id="&trim(arrcom(i))
				conn.execute sqli,ly
				if ly then
					response.Write("<script>alert('成功！已取消共享此客户')</script>")
					
				else
					response.Write("<script>alert('失败！不能取消该客户')</script>")
				end if
			end if
		end if
		rsp.close
		set rsp=nothing
	next
	if request("closed")="1" then
						response.Write("<script>parent.parent.window.close()</script>")
						closeconn()
						response.End()
					else
						response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
						closeconn()
						response.End()
					end if
	closeconn()
	response.End()
end if

if selectcb<>"" and dostay="zhongdian" then
	if session("userid")="10" then
		mycrm=split(selectcb,",",-1,1)
		for i=0 to ubound(mycrm)
			sql="select * from crm_assignAD where com_id="&trim(mycrm(i))&""
			set rs=conn.execute(sql)
			if not rs.eof or not rs.bof then
				Npersonid=rs("personid")
					'--------判断冲突客户
					sqla="select personid from crm_assign where com_id="&trim(mycrm(i))&""
					set rsa=conn.execute(sqla)
					if not rsa.eof or not rsa.bof then
						Zpersonid=rsa(0)
					end if
					rsa.close
					set rsa=nothing
					if Npersonid<>Zpersonid then
						response.Write("<script>alert('该客户有冲突，不能进行再分配！')</script>")
						response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
						response.End()
					end if
					'---------------------
				if request("personid")<>"" then
					
					sqlt="update crm_assignAD set Emphases=1,personid="&request("personid")&" where com_id="&trim(mycrm(i))&""
					conn.execute(sqlt)
					'------------写入客户分配记录
					com_id=trim(arrcom(i))
					sDetail="广告部"&request.Cookies("admin_user")&"设置为重点"
					sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&trim(mycrm(i))&","&session("personid")&",'"&sDetail&"')"
					conn.execute(sqlp)
				else
					response.Write("<script>alert('请选择你要分配给哪个销售人员！')</script>")
					response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
				end if
			else
				if request("personid")<>"" then
					sqlu="insert into crm_assignAD(com_id,personid,Emphases) values("&trim(mycrm(i))&","&request("personid")&",1)"
					conn.execute(sqlu)
					'------------写入客户分配记录
					com_id=trim(arrcom(i))
					sDetail="广告部"&request.Cookies("admin_user")&"设置为重点"
					sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&trim(mycrm(i))&","&session("personid")&",'"&sDetail&"')"
					conn.execute(sqlp)
				else
					response.Write("<script>alert('请选择你要分配给哪个销售人员！')</script>")
					response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
				end if
			end if
		next
		response.Write("<script>alert('成功！已放入重点客户')</script>")
		response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
	else
		sql="update crm_assignAD set Emphases=1 where com_id in ("& selectcb &") and personid="&session("personid")&""
		conn.Execute sql,ly
		if ly then
			response.Write("<script>alert('成功！已放入我的重点客户')</script>")
			if request("closed")="1" then
				response.Write("<script>parent.parent.window.close()</script>")
				closeconn()
				response.End()
			else
				response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
				closeconn()
				response.End()
			end if
		else
			response.Write("<script>alert('失败！不能把别人的客户设置为我的重点客户')</script>")
		end if
	end if
	closeconn()
	response.End()
end if

if selectcb<>"" and dostay="selec1tTozst" then
    sql="update comp_Adsales set ToAdContact=1 where com_id in ("& selectcb &")"
	conn.Execute sql,ly
	if ly then
		response.Write("<script>alert('成功！已放到“广告部”')</script>")
		if request("closed")="1" then
			response.Write("<script>parent.parent.window.close()</script>")
			closeconn()
			response.End()
		else
			response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
			closeconn()
			response.End()
		end if
	else
		response.Write("<script>alert('失败！不能把别人的客户放到“广告部”')</script>")
	end if
	closeconn()
	response.End()
end if

if selectcb<>"" and dostay="tempselectmycrm" then
    sql="update crm_AssignAD set selected=1 where com_id in ("& selectcb &")"
	conn.Execute sql,ly
	if ly then
		'------------写入客户记录
		arrcom=split(selectcb,",")
		for i=0 to ubound(arrcom)
			com_id=trim(arrcom(i))
			sDetail=request.Cookies("admin_user")&"放到广告客户库"
			sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
			conn.execute(sqlp)
		next
					
		response.Write("<script>alert('成功！已放到“我的客户”')</script>")
		if request("closed")="1" then
			response.Write("<script>parent.parent.window.close()</script>")
			closeconn()
			response.End()
		else
			response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
			closeconn()
			response.End()
		end if
	else
		response.Write("<script>alert('失败！不能把别人的客户放到“我的客户”')</script>")
	end if
	closeconn()
	response.End()
end if

if selectcb<>"" and dostay="zhongdianout" then
	if session("userid")="10" then
		sql="update crm_assignAD set Emphases=0 where com_id in ("& selectcb &")"
		conn.Execute sql,ly
		'------------写入客户记录
		arrcom=split(selectcb,",")
		for i=0 to ubound(arrcom)
			com_id=trim(arrcom(i))
			sDetail="管理员"&request.Cookies("admin_user")&"取消为我的重点客户（广告）"
			sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
			conn.execute(sqlp)
		next
		if ly then
			response.Write("<script>alert('成功！该客户已取消为我的重点客户')</script>")
			if request("closed")="1" then
				response.Write("<script>parent.parent.window.close()</script>")
				closeconn()
				response.End()
			else
				response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
				closeconn()
				response.End()
			end if
		else
			response.Write("<script>alert('失败！')</script>")
		end if
	else
		sql="update crm_assignAD set Emphases=0 where com_id in ("& selectcb &") and personid="&session("personid")&""
		conn.Execute sql,ly
		if ly then
			'------------写入客户记录
			arrcom=split(selectcb,",")
			for i=0 to ubound(arrcom)
				com_id=trim(arrcom(i))
				sDetail="广告部"&request.Cookies("admin_user")&"取消为我的重点客户"
				sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
				conn.execute(sqlp)
			next
			response.Write("<script>alert('成功！该客户已取消为我的重点客户')</script>")
			if request("closed")="1" then
				response.Write("<script>parent.parent.window.close()</script>")
				closeconn()
				response.End()
			else
				response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
				closeconn()
				response.End()
			end if
		else
			response.Write("<script>alert('失败！不能设置别人的客户为我的重点客户')</script>")
		end if
	end if
	closeconn()
	response.End()
end if

if selectcb<>"" and dostay="selec1tcrm" then
    mycrm=split(selectcb,",",-1,1)
	sqlcount="select count(id) from crm_assignAD where personid="&request("personid")&""
	set rscount=conn.execute(sqlcount)
	if not rscount.eof then
		cuscount=rscount(0)
	else
		cuscount=0
	end if 
	if cint(cuscount)>=maxrccomp then
	   response.Write("<script>alert('失败！该销售人员客户数已经达到"&maxrccomp&"个！');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
	   closeconn()
	   response.end()
	end if
	for i=0 to ubound(mycrm)
	    if cint(cuscount)>=maxrccomp then
		   response.Write("<script>alert('失败！该销售人员客户数已经达到"&maxrccomp&"个！');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
		   response.end()
	    end if
		sqlc="select com_id from crm_assignAD where com_id="&trim(mycrm(i))
		set rsc=conn.execute(sqlc)
		if rsc.eof then
			   sqlu="insert into crm_assignAD(com_id,personid) values("&trim(mycrm(i))&","&request("personid")&")"
			   conn.execute(sqlu)
			   cuscount=cuscount+1
			   sqlc="delete from crm_adwaste where com_id="&trim(mycrm(i))&""
		       conn.execute(sqlc)
			   
			    
		else
		        if cint(cuscount)>=maxrccomp then
				   response.Write("<script>alert('失败！该销售人员客户数已经达到"&maxrccomp&"个！');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
				   closeconn()
				   response.end()
				end if
		   sqlu="update crm_assignAD set personid="&request("personid")&" where com_id="&trim(mycrm(i))&""
		   conn.execute(sqlu)
		   cuscount=cuscount+1
		   sqlc="delete from crm_adwaste where com_id="&trim(mycrm(i))&""
		   conn.execute(sqlc)
		end if
		        com_id=trim(mycrm(i))
				sDetail="广告部"&request.Cookies("admin_user")&"分配客户"
				sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
				conn.execute(sqlp)
		rsc.close
		set rsc=nothing
	next
	response.Write("<script>alert('成功！分配成功！');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
 end if
 
if selectcb<>"" and dostay="selec1tmycrm" then
    mycrm=split(selectcb,",")
	sqlcount="select count(id) from crm_assignAD where personid="&session("personid")&""
	set rscount=conn.execute(sqlcount)
	if not rscount.eof then
		cuscount=rscount(0)
	else
		cuscount=0
	end if 
	if cint(cuscount)>=maxrccomp then
			   response.Write("<script>alert('失败！您的客户数已经达到"&maxrccomp&"个！请根据具体情况减少您的客户.');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
			   closeconn()
			   response.end()
	end if
	for i=0 to ubound(mycrm)
		sqlc="select com_id,personid from crm_assignAD where com_id="&trim(mycrm(i))&""
		set rsc=conn.execute(sqlc)
		if cint(cuscount)>=maxrccomp then
			   response.Write("<script>alert('失败！您的客户数已经达到"&maxrccomp&"个！请根据具体情况减少您的客户.');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
			   response.end()
	    end if
		
		'如果是被动掉入公海,并使相关的销售人员在7天之内不准再次挑入该客户
		sqls="select id from crm_beDroppedInSea where fdate>='"&date()-7&"' and personid="&session("personid")&" and com_id="&trim(mycrm(i))
		set rss=conn.execute(sqls)
		if not (rss.eof and rss.bof) then
			   response.Write("<script>alert('失败！由于该客户是因为30天内没有有效联系而被动掉入公海,所以该销售人员在7天之内不准再次挑入该客户');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
			   response.end()
	    end if
		rss.close
		set rss=nothing
		'判断结束
		if rsc.eof then
		   sqlu="insert into crm_assignAD(com_id,personid) values("&trim(mycrm(i))&","&session("personid")&")"
		   conn.execute(sqlu)
		   cuscount=cuscount+1
		   sqlc="delete from crm_adwaste where com_id="&trim(mycrm(i))&""
		   conn.execute(sqlc)
		   response.Write("成功！该客户已经分配到")
		else
		   if cstr(rsc("personid"))=cstr(session("personid")) then
			   if cint(cuscount)>=maxrccomp then
				   response.Write("<script>alert('失败！您的客户数已经达到"&maxrccomp&"个！请根据具体情况减少您的客户.');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
				   response.end()
			   end if
			   sqlc="delete from crm_adwaste where com_id="&trim(mycrm(i))&" "
		       conn.execute(sqlc)
		   else
		   	   response.Write("<script>alert('失败！ID号为"&trim(mycrm(i))&"客户已经被其他人分配！');window.location='"&request.ServerVariables("HTTP_REFERER")&"';</script>")
			   closeconn()
	           response.end()
		   end if
		end if
		com_id=trim(mycrm(i))
				sDetail="广告部"&request.Cookies("admin_user")&"放到我的客户库"
				sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
				conn.execute(sqlp)
		rsc.close
		set rsc=nothing
	next
	
	if request("closed")="1" then
	response.Write("<script>parent.parent.window.close()</script>")
	closeconn()
	response.End()
	else
	response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
	closeconn()
	response.End()
	end if
 end if
 
 if selectcb<>"" and dostay="selec1toutmycrm" then
    mycrm=split(selectcb,",",-1,1)
	'************
	
	sqlcount="select count(id) from crm_assignAD where personid="&session("personid")&""
	set rscount=conn.execute(sqlcount)
	if not rscount.eof then
		cuscount=rscount(0)
	else
		cuscount=0
	end if 
	'**************
	if cint(cuscount)>=maxrccomp then
	   response.Write("<script>alert('失败！您的客户数已经达到"&maxrccomp&"个！请根据具体情况减少您的客户.');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
	   closeconn()
	   response.end()
	end if
	
	for i=0 to ubound(mycrm)
	    '------------------判断是否在再生通销售库
		haveinzst=0
		sqlc="select com_id,personid from crm_assign where com_id="&trim(mycrm(i))&""
		set rsc=conn.execute(sqlc)
		if not rsc.eof or not rsc.bof then
			if cstr(rsc("personid"))=cstr(session("personid")) then
				haveinzst=1
			else
				response.Write("<script>alert('失败！改客户已经在其他销售人员的再生通库里，不能把它放到你的广告库');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
				response.End()
			end if
		end if
		rsc.close
		set rsc=nothing
		
	    '如果是被动掉入公海,并使相关的销售人员在7天之内不准再次挑入该客户
		sqls="select id from crm_beDroppedInSea where fdate>='"&date()-7&"' and personid="&session("personid")&" and com_id="&trim(mycrm(i))
		set rss=conn.execute(sqls)
		if not (rss.eof and rss.bof) then
			   response.Write("<script>alert('失败！由于该客户是因为30天内没有有效联系而被动掉入公海,所以该销售人员在7天之内不准再次挑入该客户');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
			   response.end()
	    end if
		rss.close
		set rss=nothing
		'判断结束
		
		sqlc="select com_id,personid from crm_assignAD where com_id="&trim(mycrm(i))&""
		set rsc=conn.execute(sqlc)
		if rsc.eof or rsc.bof then
		   if haveinzst=0 then
		   '--------------------挑入再生通库
		   sqlu="insert into crm_assign(com_id,personid) values("&trim(mycrm(i))&","&session("personid")&")"
		   conn.execute(sqlu)
		   sqlu="update comp_sales set com_type=0 where com_id="&trim(mycrm(i))&" "
		   conn.execute(sqlu)
		   end if
		   '--------------------挑入广告库
		   sqlu="insert into crm_assignAD(com_id,personid) values("&trim(mycrm(i))&","&session("personid")&")"
		   conn.execute(sqlu)
		   sqlcc="delete from crm_ADwaste where com_id="&trim(mycrm(i))&""
		   conn.execute(sqlcc)
		   '-------------------------
		   cuscount=cuscount+1
		   if cint(cuscount)>=maxrccomp then
			   response.Write("<script>alert('失败！您的客户数已经达到"&maxrccomp&"个！请根据具体情况减少您的客户.');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
			   closeconn()
			   response.end()
	       end if
		   response.Write("<script>alert('成功！该客户已经分配到“我的广告客户库”')</script>")
		else
		   if cstr(rsc("personid"))=cstr(session("personid")) then
			   sqlcc="delete from crm_ADwaste where com_id="&trim(mycrm(i))
			   conn.execute(sqlcc)
			   if haveinzst=0 then
			   '--------------------挑入再生通库
			   sqlu="insert into crm_assign(com_id,personid) values("&trim(mycrm(i))&","&session("personid")&")"
			   conn.execute(sqlu)
			   sqlu="update comp_sales set com_type=0 where com_id="&trim(mycrm(i))&" "
			   conn.execute(sqlu)
			   cuscount=cuscount+1
			   end if
			   response.Write("<script>alert('成功！该客户已经分配到“我的广告客户表”')</script>")
		   else
			   response.Write("<script>alert('失败！ID号为"&trim(mycrm(i))&"客户已经被其他人分配！');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
			   closeconn()
			   response.end()
		   end if
		end if
		        com_id=trim(mycrm(i))
				sDetail="广告部"&request.Cookies("admin_user")&"放到我的客户库"
				sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
				conn.execute(sqlp)
		rsc.close
		set rsc=nothing
	next
	
    if request("closed")="1" then
		response.Write("<script>parent.parent.window.close()</script>")
		closeconn()
		response.End()
	else
		response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
		closeconn()
		response.End()
	end if
 end if
 
 if selectcb<>"" and dostay="delselec1tcrm" then
    sqldel="delete from crm_assignAD where com_id in ("& selectcb &") and personid="&session("personid")&""
	conn.Execute sqldel,ly
	arrcom=split(selectcb,",")
	'----------记录最后放到公海的时间，并标注
	if ly and selectcb<>"" then
		arrcomp=split(selectcb,",")
		for i=0 to ubound(arrcomp)
			sqlm="select max(id) from comp_telad where com_id="&trim(arrcomp(i))&""
			set rsm=conn.execute(sqlm)
			if not rsm.eof or not rsm.bof then
				maxtelid=rsm(0)
			else
				maxtelid=0
			end if
			rsm.close
			set rsm=nothing
			if maxtelid="" or isnull(maxtelid) then maxtelid=0
			if trim(arrcomp(i))<>"" then
				sql="select * from crm_assign_out where com_id="&trim(arrcomp(i))&" and telid="&maxtelid&" and teltype=1"
				set rs=conn.execute(sql)
				if rs.eof or rs.bof then
					sqln="insert into crm_assign_out (com_id,telid,teltype) values("&trim(arrcomp(i))&","&maxtelid&",1)"
					conn.execute(sqln)
				end if
				rs.close
				set rs=nothing
			end if
			com_id=trim(arrcomp(i))
				sDetail="广告部"&request.Cookies("admin_user")&"放到公海"
				sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
				conn.execute(sqlp)
		next
		response.Write("<script>alert('成功！已入公海')</script>")
	end if 
	
	if request("closed")="1" then
		response.Write("<script>parent.parent.window.close()</script>")
		closeconn()
		response.End()
	else
		response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
		closeconn()
		response.End()
	end if
	closeconn()
	response.End()
 end if

 function closeconn()
	 conn.close
	 set conn=nothing
 end function
%>
