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
	response.Write("<script>alert('�ɹ����ÿͻ��Ѿ��ŵ�����ɾ���ͻ�����');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
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
	response.Write("<script>alert('�ɹ���');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
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
		response.Write("<script>alert('�ɹ����ÿͻ��Ѿ��ŵ�����Ʒ�ء�')</script>")
		
		else
		response.Write("<script>alert('ʧ�ܣ�')</script>")
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
		response.Write("<script>alert('�ɹ����ѷ����ҵ��ص�ͻ�')</script>")
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
		response.Write("<script>alert('ʧ�ܣ����ܰѱ��˵Ŀͻ�����Ϊ�ҵ��ص�ͻ�')</script>")
	end if
	closeconn()
	response.End()
end if

if selectcb<>"" and dostay="zhongdianout" then
    sql="update crm_assign set Emphases=0 where com_id in ("& selectcb &") and personid="&session("personid")&""
	conn.Execute sql,ly
	if ly then
		response.Write("<script>alert('�ɹ����ÿͻ���ȡ��Ϊ�ҵ��ص�ͻ�')</script>")
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
		response.Write("<script>alert('ʧ�ܣ��������ñ��˵Ŀͻ�Ϊ�ҵ��ص�ͻ�')</script>")
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
	response.Write("<script>alert('�ɹ�������ɹ���');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
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
			   response.Write("<script>alert('ʧ�ܣ����Ŀͻ����Ѿ��ﵽ"&maxrccomp&"��������ݾ�������������Ŀͻ�.');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
			   closeconn()
			   response.end()
	end if
	for i=0 to ubound(mycrm)
		sqlc="select com_id,personid,Cushion from crm_assign where com_id="&trim(mycrm(i))&""
		set rsc=conn.execute(sqlc)
		if cint(cuscount)>=maxrccomp then
			   response.Write("<script>alert('ʧ�ܣ����Ŀͻ����Ѿ��ﵽ"&maxrccomp&"��������ݾ�������������Ŀͻ�.');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
			   response.end()
	    end if
		if rsc.eof then
		   sqlu="insert into crm_assign(com_id,personid,adminpersonid,Cushion) values("&trim(mycrm(i))&","&session("personid")&","&session("personid")&",1)"
		   conn.execute(sqlu)
		   cuscount=cuscount+1
		   response.Write("�ɹ����ÿͻ��Ѿ����䵽���ǻ������ͻ���")
		else
		   if cstr(rsc("personid"))=cstr(session("personid")) then
			   if cint(cuscount)>=maxrccomp then
				   response.Write("<script>alert('ʧ�ܣ����Ŀͻ����Ѿ��ﵽ"&maxrccomp&"��������ݾ�������������Ŀͻ�.');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
				   response.end()
			   end if
		       sqlu="update crm_assign set Cushion=1,fdate=getdate(),personid="&session("personid")&" where com_id="&trim(mycrm(i))&""
		       conn.execute(sqlu)
			   if rsc("Cushion")="0" then
			   	  cuscount=cuscount+1
			   end if
		   response.Write("<script>alert('�ɹ����ÿͻ��Ѿ����䵽���ǻ������ͻ���')</script>")
		   else
		   response.Write("<script>alert('ʧ�ܣ�ID��Ϊ"&trim(mycrm(i))&"�ͻ��Ѿ��������˷��䣡')</script>")
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
		   response.Write("<script>alert('�ɹ����ÿͻ��Ѿ����䵽���ҵĿͻ���')</script>")
		else
		   
		   if cstr(rsc("personid"))=cstr(session("personid")) then
		   sqlu="update crm_assign set Cushion=0 where com_id="&trim(mycrm(i))&" and personid="&session("personid")&""
		   conn.execute(sqlu)
		   sqlu="update comp_sales set com_type=0 where com_id="&trim(mycrm(i))&" "
		   conn.execute(sqlu)
		   cuscount=cuscount+1
		   response.Write("<script>alert('�ɹ����ÿͻ��Ѿ����䵽���ҵĿͻ���')</script>")
		   else
		   response.Write("ʧ�ܣ�ID��Ϊ"&trim(mycrm(i))&"�ͻ��Ѿ��������˷��䣡</br>")
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
	response.Write("<script>alert('�ɹ������빫��')</script>")
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
	
	response.Write("<script>alert('�ɹ����ѳ�����')</script>")
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
	
	response.Write("<script>alert('�ɹ����ѷ��������')</script>")
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
	response.Write("<script>alert('�ɹ����ѷ������ͨ������ͨ')</script>")
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
