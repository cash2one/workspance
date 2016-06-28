<!-- #include file="../include/ad!$#5V.asp" -->
<!-- #include file="../localjumptolog.asp" -->
<!-- #include file="../../cn/function.asp" -->
<!--#include file="../include/include.asp"-->
<%
strconnser="Provider=SQLOLEDB.1;driver={SQL Server};server=www.zz91.com,1525;uid=rcu;pwd=dfds@!!#32**kangxianyue;database=rcu1"
'connnews.open strconnnews
function OpenConn(ConnStr,ConnName)
	set ConnName=server.CreateObject("ADODB.connection")
	ConnName.open ConnStr
end function
Function CloseConn(ConnName)
	ConnName.close
	set ConnName=nothing
end function

 selectcb=request("selectcb")
 dostay=request("dostay")
 maxrccomp=800
 if selectcb<>"" and dostay="selec1tdelcom" then
    sqlu="update crm_assign set Cushion='-1',fdate=getdate() where com_id IN ("&selectcb&") AND personid="&session("personid")&""
	'response.Write(sqlu)
	conn.execute(sqlu)
	response.Write("<script>alert('成功！该客户已经放到“待删除客户区”');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
 end if
 if selectcb<>"" and dostay="delactive" then
	arrcom=split(selectcb,",",-1,1)
	for i=0 to ubound(arrcom)
		sql="delete from Crm_Active_CompAll where com_id = "& trim(arrcom(i)) &""
		conn.execute(sql)
		sql="delete from Crm_Active_ActiveComp where com_id = "& trim(arrcom(i)) &""
		conn.execute(sql)
		response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
		closeconn()
		response.End()
	next
 end if
 if selectcb<>"" and dostay="selectEmailcom" then
  OpenConn strconnser,connser
   sqlc="select com_id from v_SalesCompActive where not EXISTS (select com_id from Agent_ClientCompany where com_id=v_SalesCompActive.com_id) "&selectcb
   set rsc=conn.execute(sqlc)
   while not rsc.eof
   
	sql="select com_id from crm_active_email where com_id="&trim(rsc(0))&""
	set rs=connser.execute(sql)
	if rs.eof then
	sqlu="insert into crm_active_email(com_id) values("&trim(rsc(0))&")"
	connser.execute(sqlu)
	end if
	rs.close
	set rs=nothing
	rsc.movenext
 
   wend
   rsc.close
   set rsc=nothing 
   CloseConn connser
	response.Write("<script>alert('成功！');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
 end if
  if selectcb<>"" and dostay="selec1twastecom" then
    arrcom=split(selectcb,",",-1,1)
	
	for i=0 to ubound(arrcom)
	
		sql="select com_id from Crm_Active_Waste where com_id="&trim(arrcom(i))&""
		set rs=conn.execute(sql)
		if rs.eof then
			sqli="insert into Crm_Active_Waste (com_id,waste) values("&trim(arrcom(i))&",1)"
			conn.execute sqli,ly
		end if
		if ly then
		response.Write("<script>alert('成功！该客户已经放到“废品池”')</script>")
		
		else
		response.Write("<script>alert('失败！')</script>")
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

if selectcb<>"" and dostay="zhongdian" then
    sql="update crm_assign set Emphases=1 where com_id in ("& selectcb &") and personid="&session("personid")&""
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
	closeconn()
	response.End()
end if

if selectcb<>"" and dostay="zhongdianout" then
    sql="update crm_assign set Emphases=0 where com_id in ("& selectcb &") and personid="&session("personid")&""
	conn.Execute sql,ly
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
		response.Write("<script>alert('失败！不能设置别人的客户为我的重点客户')</script>")
	end if
	closeconn()
	response.End()
end if

if selectcb<>"" and dostay="selec1tcrm" then
    mycrm=split(selectcb,",",-1,1)
	
	for i=0 to ubound(mycrm)
	    
		sqlc="select com_id from crm_Active_assign where com_id="&trim(mycrm(i))
		set rsc=conn.execute(sqlc)
		if rsc.eof then
			   sqlu="insert into crm_Active_assign(com_id,personid) values("&trim(mycrm(i))&","&request("personid")&")"
			   conn.execute(sqlu)
		end if
		rsc.close
		set rsc=nothing
	next
	response.Write("<script>alert('成功！分配成功！');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
 end if
 
if selectcb<>"" and dostay="selec1tmycrm" then
    mycrm=split(selectcb,",",-1,1)
	sqlcount="select count(id) from crm_assign where personid="&session("personid")&" and cushion=1"
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
		sqlc="select com_id,personid,Cushion from crm_assign where com_id="&trim(mycrm(i))&""
		set rsc=conn.execute(sqlc)
		if cint(cuscount)>=maxrccomp then
			   response.Write("<script>alert('失败！您的客户数已经达到"&maxrccomp&"个！请根据具体情况减少您的客户.');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
			   response.end()
	    end if
		if rsc.eof then
		   sqlu="insert into crm_assign(com_id,personid,adminpersonid,Cushion) values("&trim(mycrm(i))&","&session("personid")&","&session("personid")&",1)"
		   conn.execute(sqlu)
		   cuscount=cuscount+1
		   response.Write("成功！该客户已经分配到“非缓冲区客户表”")
		else
		   if cstr(rsc("personid"))=cstr(session("personid")) then
			   if cint(cuscount)>=maxrccomp then
				   response.Write("<script>alert('失败！您的客户数已经达到"&maxrccomp&"个！请根据具体情况减少您的客户.');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
				   response.end()
			   end if
		       sqlu="update crm_assign set Cushion=1,fdate=getdate(),personid="&session("personid")&" where com_id="&trim(mycrm(i))&""
		       conn.execute(sqlu)
			   if rsc("Cushion")="0" then
			   	  cuscount=cuscount+1
			   end if
		   response.Write("<script>alert('成功！该客户已经分配到“非缓冲区客户表”')</script>")
		   else
		   response.Write("<script>alert('失败！ID号为"&trim(mycrm(i))&"客户已经被其他人分配！')</script>")
		   end if
		end if
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
	sqlcount="select count(id) from crm_assign where personid="&session("personid")&" and cushion=0"
	set rscount=conn.execute(sqlcount)
	if not rscount.eof then
		cuscount=rscount(0)
	else
		cuscount=0
	end if 
	'**************
	
	for i=0 to ubound(mycrm)
	    
		sqlc="select com_id,personid from crm_assign where com_id="&trim(mycrm(i))&""
		set rsc=conn.execute(sqlc)
		if rsc.eof then
		   sqlu="insert into crm_assign(com_id,personid,adminpersonid,Cushion) values("&trim(mycrm(i))&","&session("personid")&","&session("personid")&",0)"
		   conn.execute(sqlu)
		   response.Write("<script>alert('成功！该客户已经分配到“我的客户表”')</script>")
		else
		   
		   if cstr(rsc("personid"))=cstr(session("personid")) then
		   sqlu="update crm_assign set Cushion=0 where com_id="&trim(mycrm(i))&" and personid="&session("personid")&""
		   conn.execute(sqlu)
		   sqlu="update comp_sales set com_type=0 where com_id="&trim(mycrm(i))&" "
		   conn.execute(sqlu)
		   cuscount=cuscount+1
		   response.Write("<script>alert('成功！该客户已经分配到“我的客户表”')</script>")
		   else
		   response.Write("失败！ID号为"&trim(mycrm(i))&"客户已经被其他人分配！</br>")
		   end if
		   
		end if
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
    sqldel="delete from crm_Active_assign where com_id in ("& selectcb &")"
	conn.Execute(sqldel)
	
	arrcom=split(selectcb,",",-1,1)
	for i=0 to ubound(arrcom)
	sqlp="select * from Crm_Active_publicComp where com_id in ("& trim(arrcom(i)) &")"
	set rsp=conn.execute(sqlp)
	if rsp.eof then
	sqlpp="insert into Crm_Active_publicComp(com_id) values("& trim(arrcom(i)) &")"
	conn.execute(sqlpp)
	end if
	next
	response.Write("<script>alert('成功！已入公海')</script>")
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
 if selectcb<>"" and dostay="outselec1tcrm" then
    sqldel="delete from Crm_Active_publicComp where com_id in ("& selectcb &")"
	conn.Execute(sqldel)
	
	response.Write("<script>alert('成功！已出公海')</script>")
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
 
 if selectcb<>"" and dostay="outselec1tcrmtt" then
    sqldel="delete from crm_assign where com_id in ("& selectcb &")"
	conn.Execute(sqldel)
	
	response.Write("<script>alert('成功！已放入待分配')</script>")
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
 if selectcb<>"" and dostay="openzst" then
    sqldel="update crm_assign set Waiter_Open=1 where com_id in ("& selectcb &")"
	conn.Execute(sqldel)
	response.Write("<script>alert('成功！已放入待开通的再生通')</script>")
	if request("closed")="1" then

		response.Write("<script>parent.parent.window.close()</script>")
		closeconn()
		response.End()
	else
		response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
		closeconn()
		response.End()
	end if
	response.End()
 end if
 function closeconn()
 conn.close
 set conn=nothing
 end function
%>
