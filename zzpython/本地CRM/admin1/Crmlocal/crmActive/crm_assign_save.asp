<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<!-- #include file="../../../cn/function.asp" -->
<!--#include file="../../include/include.asp"-->
<%
 selectcb=request("selectcb")
 dostay=request("dostay")
 maxrccomp=800
 if selectcb<>"" and dostay="selec1tdelcom" then
    sqlu="update crm_active_assign set Cushion='-1',fdate=getdate() where com_id IN ("&selectcb&") AND personid="&session("personid")&""
	'response.Write(sqlu)
	conn.execute(sqlu)
	response.Write("<script>alert('�ɹ����ÿͻ��Ѿ��ŵ�����ɾ���ͻ�����');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
 end if
 
  if selectcb<>"" and dostay="selec1twastecom" then
    arrcom=split(selectcb,",",-1,1)
	
	for i=0 to ubound(arrcom)
	
    sql="select com_id from comp_active_sales where com_id="&trim(arrcom(i))&""
	set rs=conn.execute(sql)
	
	if not rs.eof then
    sqlu="update comp_active_sales set com_type='13' where com_id="&trim(arrcom(i))&" AND com_id in (select com_id from crm_active_assign where personid="&session("personid")&")"
	conn.execute sqlu,ly
	else
		sqlm="select com_id from crm_active_assign where com_id="&trim(arrcom(i))&" and personid="&session("personid")&""
		set rsm=conn.execute(sqlm)
		if not rsm.eof then
			sqlu="insert into comp_active_sales(com_id,com_type) values("&trim(arrcom(i))&",'13')"
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

if selectcb<>"" and dostay="zhongdian" then
    sql="update crm_active_assign set Emphases=1 where com_id in ("& selectcb &") and personid="&session("personid")&""
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
    sql="update crm_active_assign set Emphases=0 where com_id in ("& selectcb &") and personid="&session("personid")&""
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
	sqlcount="select count(id) from crm_active_assign where personid="&request("personid")&"  and cushion=1"
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
		sqlc="select com_id,cushion from crm_active_assign where com_id="&trim(mycrm(i))
		set rsc=conn.execute(sqlc)
		if rsc.eof then
			   sqlu="insert into crm_active_assign(com_id,personid,adminpersonid,cushion) values("&trim(mycrm(i))&","&request("personid")&","&session("personid")&",1)"
			   conn.execute(sqlu)
			   cuscount=cuscount+1
		else
		        if cint(cuscount)>=maxrccomp then
				   response.Write("<script>alert('ʧ�ܣ���������Ա�ͻ����Ѿ��ﵽ"&maxrccomp&"����');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
				   closeconn()
				   response.end()
				end if
		   sqlu="update crm_active_assign set personid="&request("personid")&",cushion=1,fdate=getdate() where com_id="&trim(mycrm(i))&""
		   conn.execute(sqlu)
		   if rsc("cushion")="0" then
		   		cuscount=cuscount+1
		   end if
		end if
		rsc.close
		set rsc=nothing
	next
	response.Write("<script>alert('�ɹ�������ɹ���');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
 end if
 
if selectcb<>"" and dostay="selec1tmycrm" then
    mycrm=split(selectcb,",",-1,1)
	sqlcount="select count(id) from crm_active_assign where personid="&session("personid")&" and cushion=1"
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
		sqlc="select com_id,personid,Cushion from crm_active_assign where com_id="&trim(mycrm(i))&""
		set rsc=conn.execute(sqlc)
		if cint(cuscount)>=maxrccomp then
			   response.Write("<script>alert('ʧ�ܣ����Ŀͻ����Ѿ��ﵽ"&maxrccomp&"��������ݾ�������������Ŀͻ�.');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
			   response.end()
	    end if
		if rsc.eof then
		   sqlu="insert into crm_active_assign(com_id,personid,adminpersonid,Cushion) values("&trim(mycrm(i))&","&session("personid")&","&session("personid")&",1)"
		   conn.execute(sqlu)
		   cuscount=cuscount+1
		   response.Write("�ɹ����ÿͻ��Ѿ����䵽���ǻ������ͻ���")
		else
		   if cstr(rsc("personid"))=cstr(session("personid")) then
			   if cint(cuscount)>=maxrccomp then
				   response.Write("<script>alert('ʧ�ܣ����Ŀͻ����Ѿ��ﵽ"&maxrccomp&"��������ݾ�������������Ŀͻ�.');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
				   response.end()
			   end if
		       sqlu="update crm_active_assign set Cushion=1,fdate=getdate(),personid="&session("personid")&" where com_id="&trim(mycrm(i))&""
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
	sqlcount="select count(id) from crm_active_assign where personid="&session("personid")&" and cushion=0"
	set rscount=conn.execute(sqlcount)
	if not rscount.eof then
		cuscount=rscount(0)
	else
		cuscount=0
	end if 
	'**************
	
	for i=0 to ubound(mycrm)
	    
		sqlc="select com_id,personid from crm_active_assign where com_id="&trim(mycrm(i))&""
		set rsc=conn.execute(sqlc)
		if rsc.eof then
		   sqlu="insert into crm_active_assign(com_id,personid,adminpersonid,Cushion) values("&trim(mycrm(i))&","&session("personid")&","&session("personid")&",0)"
		   conn.execute(sqlu)
		   response.Write("<script>alert('�ɹ����ÿͻ��Ѿ����䵽���ҵĿͻ���')</script>")
		else
		   
		   if cstr(rsc("personid"))=cstr(session("personid")) then
		   sqlu="update crm_active_assign set Cushion=0 where com_id="&trim(mycrm(i))&" and personid="&session("personid")&""
		   conn.execute(sqlu)
		   sqlu="update comp_active_sales set com_type=0 where com_id="&trim(mycrm(i))&" "
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
    sqldel="delete from crm_active_assign where com_id in ("& selectcb &")"
	conn.Execute(sqldel)
	'sqldel="delete from crm_assignVIP where com_id in ("& selectcb &")"
	'conn.Execute(sqldel)
	arrcom=split(selectcb,",",-1,1)
	for i=0 to ubound(arrcom)
	sqlp="select * from crm_active_publiccomp where com_id in ("& trim(arrcom(i)) &")"
	set rsp=conn.execute(sqlp)
	if rsp.eof then
	sqlpp="insert into crm_active_publiccomp(com_id) values("& trim(arrcom(i)) &")"
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
    sqldel="delete from crm_active_publiccomp where com_id in ("& selectcb &")"
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
    sqldel="delete from crm_active_assign where com_id in ("& selectcb &")"
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
    sqldel="update crm_active_assign set Waiter_Open=1 where com_id in ("& selectcb &")"
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
