<!-- #include file="../include/ad!$#5V.asp" -->
<!-- #include file="../localjumptolog.asp" -->
<!-- #include file="../../cn/function.asp" -->
<!--#include file="../include/include.asp"-->
<%
 selectcb=request("selectcb")
 dostay=request("dostay")
 maxrccomp=400
 
  if selectcb<>"" and dostay="selec1twastecom" then
    arrcom=split(selectcb,",",-1,1)
	
	for i=0 to ubound(arrcom)
	
    sql="select com_id from crm_webwaste where com_id="&trim(arrcom(i))&""
	set rs=conn.execute(sql)
	
	if not rs.eof then
    sqlu="update crm_webwaste set waste=1 where com_id="&trim(arrcom(i))&" AND com_id in (select com_id from crm_assignweb where personid="&session("personid")&")"
	conn.execute sqlu,ly
	else
		sqlm="select com_id from crm_assignweb where com_id="&trim(arrcom(i))&" and personid="&session("personid")&""
		set rsm=conn.execute(sqlm)
		if not rsm.eof then
			sqlu="insert into crm_webwaste(com_id,waste) values("&trim(arrcom(i))&",1)"
		    conn.execute sqlu,ly
		end if
		rsm.close
		set rsm=nothing
	end if
	rs.close
	set rs=nothing
	
	
	if ly then
	response.Write("<script>alert('�ɹ����ÿͻ��Ѿ��ŵ�����Ʒ�ء�')</script>")
	
	else
	response.Write("<script>alert('ʧ�ܣ��ÿͻ��������Ŀͻ����ܷŵ�����Ʒ�ء�')</script>")
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
'*�ͻ�����
'------------------
if selectcb<>"" and dostay="selec1tToshare" then
    arrcom=split(selectcb,",",-1,1)
	for i=0 to ubound(arrcom)
		sqlp="select com_id from crm_AssignWeb where com_id = "& trim(arrcom(i)) &" and personid="&session("personid")&""
		set rsp=conn.execute(sqlp)
		if not rsp.eof then
			sql="select com_id from Crm_Comp_Share where com_id = "& trim(arrcom(i)) &""
			set rsc=conn.Execute(sql)
			if rsc.eof then
				sqli="insert into Crm_Comp_Share(com_id,personid,salesType) values("& trim(arrcom(i)) &","&session("personid")&",2)"
				conn.execute sqli,ly
			else
				sqli="update Crm_Comp_Share set salesType=2 where com_id = "& trim(arrcom(i)) &""
				conn.execute sqli,ly
			end if
			    if ly then
					response.Write("<script>alert('�ɹ����ѷ����ҵĹ���ͻ���')</script>")
					
				else
					response.Write("<script>alert('ʧ�ܣ����ܰѸÿͻ�����Ϊ����ͻ�')</script>")
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
		sqlp="select com_id from crm_AssignWeb where com_id = "& trim(arrcom(i)) &" and personid="&session("personid")&""
		set rsp=conn.execute(sqlp)
		if not rsp.eof then
			sql="select com_id from Crm_Comp_Share where com_id = "& trim(arrcom(i)) &""
			set rsc=conn.Execute(sql)
			if not rsc.eof then
				sqli="delete from Crm_Comp_Share where com_id="&trim(arrcom(i))
				conn.execute sqli,ly
				if ly then
					response.Write("<script>alert('�ɹ�����ȡ������˿ͻ�')</script>")
					
				else
					response.Write("<script>alert('ʧ�ܣ�����ȡ���ÿͻ�')</script>")
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
			sql="select * from crm_assignweb where com_id="&trim(mycrm(i))&""
			set rs=conn.execute(sql)
			if not rs.eof or not rs.bof then
				if request("personid")<>"" then
					sqlt="update crm_assignweb set Emphases=1,personid="&request("personid")&" where com_id="&trim(mycrm(i))&""
					conn.execute(sqlt)
				else
					response.Write("<script>alert('��ѡ����Ҫ������ĸ�������Ա��')</script>")
					response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
				end if
			else
				if request("personid")<>"" then
					sqlu="insert into crm_assignweb(com_id,personid,Emphases) values("&trim(mycrm(i))&","&request("personid")&",1)"
					conn.execute(sqlu)
				else
					response.Write("<script>alert('��ѡ����Ҫ������ĸ�������Ա��')</script>")
					response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
				end if
			end if
		next
		response.Write("<script>alert('�ɹ����ѷ����ص�ͻ�')</script>")
		response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
	else
		sql="update crm_assignweb set Emphases=1 where com_id in ("& selectcb &") and personid="&session("personid")&""
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
	end if
	closeconn()
	response.End()
end if

if selectcb<>"" and dostay="selec1tTozst" then
    sql="update comp_websales set TozstContact=1 where com_id in ("& selectcb &")"
	conn.Execute sql,ly
	if ly then
		response.Write("<script>alert('�ɹ����ѷŵ�������ͨ����')</script>")
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
		response.Write("<script>alert('ʧ�ܣ����ܰѱ��˵Ŀͻ��ŵ�������ͨ����')</script>")
	end if
	closeconn()
	response.End()
end if



if selectcb<>"" and dostay="zhongdianout" then
	if session("userid")="10" then
		sql="update crm_assignweb set Emphases=0 where com_id in ("& selectcb &")"
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
			response.Write("<script>alert('ʧ�ܣ�')</script>")
		end if
	else
		sql="update crm_assignweb set Emphases=0 where com_id in ("& selectcb &") and personid="&session("personid")&""
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
	end if
	closeconn()
	response.End()
end if

if selectcb<>"" and dostay="selec1tcrm" then
    mycrm=split(selectcb,",",-1,1)
	sqlcount="select count(id) from crm_assignweb where personid="&request("personid")&""
	set rscount=conn.execute(sqlcount)
	if not rscount.eof then
	cuscount=rscount(0)
	else
	cuscount=0
	end if 
	if cint(cuscount)>=maxrccomp then
	   response.Write("<script>alert('ʧ�ܣ���������Ա�ͻ����Ѿ��ﵽ"&maxrccomp&"����');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
	   closeconn()
	   response.end()
	end if
	for i=0 to ubound(mycrm)
	    if cint(cuscount)>=maxrccomp then
		   response.Write("<script>alert('ʧ�ܣ���������Ա�ͻ����Ѿ��ﵽ"&maxrccomp&"����');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
		   response.end()
	    end if
		sqlc="select com_id from crm_assignweb where com_id="&trim(mycrm(i))
		set rsc=conn.execute(sqlc)
		if rsc.eof then
			   sqlu="insert into crm_assignweb(com_id,personid) values("&trim(mycrm(i))&","&request("personid")&")"
			   conn.execute(sqlu)
			   cuscount=cuscount+1
			   sqlc="delete from crm_webwaste where com_id="&trim(mycrm(i))&""
		       conn.execute(sqlc)
		else
		        if cint(cuscount)>=maxrccomp then
				   response.Write("<script>alert('ʧ�ܣ���������Ա�ͻ����Ѿ��ﵽ"&maxrccomp&"����');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
				   closeconn()
				   response.end()
				end if
		   sqlu="update crm_assignweb set personid="&request("personid")&" where com_id="&trim(mycrm(i))&""
		   conn.execute(sqlu)
		   cuscount=cuscount+1
		   sqlc="delete from crm_webwaste where com_id="&trim(mycrm(i))&""
		   conn.execute(sqlc)
		end if
		rsc.close
		set rsc=nothing
	next
	response.Write("<script>alert('�ɹ�������ɹ���');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
 end if
 
if selectcb<>"" and dostay="selec1tmycrm" then
    mycrm=split(selectcb,",",-1,1)
	sqlcount="select count(id) from crm_assignweb where personid="&session("personid")&""
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
		sqlc="select com_id,personid from crm_assignweb where com_id="&trim(mycrm(i))&""
		set rsc=conn.execute(sqlc)
		if cint(cuscount)>=maxrccomp then
			   response.Write("<script>alert('ʧ�ܣ����Ŀͻ����Ѿ��ﵽ"&maxrccomp&"��������ݾ�������������Ŀͻ�.');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
			   response.end()
	    end if
		if rsc.eof then
		   sqlu="insert into crm_assignweb(com_id,personid) values("&trim(mycrm(i))&","&session("personid")&")"
		   conn.execute(sqlu)
		   cuscount=cuscount+1
		   sqlc="delete from crm_webwaste where com_id="&trim(mycrm(i))&""
		   conn.execute(sqlc)
		   response.Write("�ɹ����ÿͻ��Ѿ����䵽")
		else
		   if cstr(rsc("personid"))=cstr(session("personid")) then
			   if cint(cuscount)>=maxrccomp then
				   response.Write("<script>alert('ʧ�ܣ����Ŀͻ����Ѿ��ﵽ"&maxrccomp&"��������ݾ�������������Ŀͻ�.');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
				   response.end()
			   end if
			   sqlc="delete from crm_webwaste where com_id="&trim(mycrm(i))&" "
		       conn.execute(sqlc)
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
	sqlcount="select count(id) from crm_assignweb where personid="&session("personid")&""
	set rscount=conn.execute(sqlcount)
	if not rscount.eof then
		cuscount=rscount(0)
	else
		cuscount=0
	end if 
	'**************
	if cint(cuscount)>=maxrccomp then
			   response.Write("<script>alert('ʧ�ܣ����Ŀͻ����Ѿ��ﵽ"&maxrccomp&"��������ݾ�������������Ŀͻ�.');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
			   closeconn()
			   response.end()
	end if
	
	for i=0 to ubound(mycrm)
	    
		sqlc="select com_id,personid from crm_assignweb where com_id="&trim(mycrm(i))&""
		set rsc=conn.execute(sqlc)
		if rsc.eof then
		   sqlu="insert into crm_assignweb(com_id,personid) values("&trim(mycrm(i))&","&session("personid")&")"
		   conn.execute(sqlu)
		   sqlcc="delete from crm_webwaste where com_id="&trim(mycrm(i))&""
		   conn.execute(sqlcc)
		   cuscount=cuscount+1
		   if cint(cuscount)>=maxrccomp then
			   response.Write("<script>alert('ʧ�ܣ����Ŀͻ����Ѿ��ﵽ"&maxrccomp&"��������ݾ�������������Ŀͻ�.');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
			   closeconn()
			   response.end()
	       end if
		   response.Write("<script>alert('�ɹ����ÿͻ��Ѿ����䵽���ҵĿͻ���')</script>")
		else
		   
		   if cstr(rsc("personid"))=cstr(session("personid")) then
		   
		   sqlcc="delete from crm_webwaste where com_id="&trim(mycrm(i))&" and personid="&session("personid")&""
		   conn.execute(sqlcc)
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
    sqldel="delete from crm_assignweb where com_id in ("& selectcb &")"
	conn.Execute(sqldel)
	arrcom=split(selectcb,",",-1,1)
	
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
    sqldel="delete from crm_publiccomp where com_id in ("& selectcb &")"
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
    sqldel="delete from crm_assignweb where com_id in ("& selectcb &")"
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
