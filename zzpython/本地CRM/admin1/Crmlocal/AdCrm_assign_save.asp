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
			'------------д��ͻ������¼
				com_id=trim(arrcom(i))
				sDetail="��沿"&request.Cookies("admin_user")&"�����Ʒ��"
				sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&trim(arrcom(i))&","&session("personid")&",'"&sDetail&"')"
				conn.execute(sqlp)
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
		sqlp="select com_id from crm_assignAD where com_id = "& trim(arrcom(i)) &" and personid="&session("personid")&""
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
			sql="select * from crm_assignAD where com_id="&trim(mycrm(i))&""
			set rs=conn.execute(sql)
			if not rs.eof or not rs.bof then
				Npersonid=rs("personid")
					'--------�жϳ�ͻ�ͻ�
					sqla="select personid from crm_assign where com_id="&trim(mycrm(i))&""
					set rsa=conn.execute(sqla)
					if not rsa.eof or not rsa.bof then
						Zpersonid=rsa(0)
					end if
					rsa.close
					set rsa=nothing
					if Npersonid<>Zpersonid then
						response.Write("<script>alert('�ÿͻ��г�ͻ�����ܽ����ٷ��䣡')</script>")
						response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
						response.End()
					end if
					'---------------------
				if request("personid")<>"" then
					
					sqlt="update crm_assignAD set Emphases=1,personid="&request("personid")&" where com_id="&trim(mycrm(i))&""
					conn.execute(sqlt)
					'------------д��ͻ������¼
					com_id=trim(arrcom(i))
					sDetail="��沿"&request.Cookies("admin_user")&"����Ϊ�ص�"
					sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&trim(mycrm(i))&","&session("personid")&",'"&sDetail&"')"
					conn.execute(sqlp)
				else
					response.Write("<script>alert('��ѡ����Ҫ������ĸ�������Ա��')</script>")
					response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
				end if
			else
				if request("personid")<>"" then
					sqlu="insert into crm_assignAD(com_id,personid,Emphases) values("&trim(mycrm(i))&","&request("personid")&",1)"
					conn.execute(sqlu)
					'------------д��ͻ������¼
					com_id=trim(arrcom(i))
					sDetail="��沿"&request.Cookies("admin_user")&"����Ϊ�ص�"
					sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&trim(mycrm(i))&","&session("personid")&",'"&sDetail&"')"
					conn.execute(sqlp)
				else
					response.Write("<script>alert('��ѡ����Ҫ������ĸ�������Ա��')</script>")
					response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
				end if
			end if
		next
		response.Write("<script>alert('�ɹ����ѷ����ص�ͻ�')</script>")
		response.Write("<script>window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
	else
		sql="update crm_assignAD set Emphases=1 where com_id in ("& selectcb &") and personid="&session("personid")&""
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
    sql="update comp_Adsales set ToAdContact=1 where com_id in ("& selectcb &")"
	conn.Execute sql,ly
	if ly then
		response.Write("<script>alert('�ɹ����ѷŵ�����沿��')</script>")
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
		response.Write("<script>alert('ʧ�ܣ����ܰѱ��˵Ŀͻ��ŵ�����沿��')</script>")
	end if
	closeconn()
	response.End()
end if

if selectcb<>"" and dostay="tempselectmycrm" then
    sql="update crm_AssignAD set selected=1 where com_id in ("& selectcb &")"
	conn.Execute sql,ly
	if ly then
		'------------д��ͻ���¼
		arrcom=split(selectcb,",")
		for i=0 to ubound(arrcom)
			com_id=trim(arrcom(i))
			sDetail=request.Cookies("admin_user")&"�ŵ����ͻ���"
			sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
			conn.execute(sqlp)
		next
					
		response.Write("<script>alert('�ɹ����ѷŵ����ҵĿͻ���')</script>")
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
		response.Write("<script>alert('ʧ�ܣ����ܰѱ��˵Ŀͻ��ŵ����ҵĿͻ���')</script>")
	end if
	closeconn()
	response.End()
end if

if selectcb<>"" and dostay="zhongdianout" then
	if session("userid")="10" then
		sql="update crm_assignAD set Emphases=0 where com_id in ("& selectcb &")"
		conn.Execute sql,ly
		'------------д��ͻ���¼
		arrcom=split(selectcb,",")
		for i=0 to ubound(arrcom)
			com_id=trim(arrcom(i))
			sDetail="����Ա"&request.Cookies("admin_user")&"ȡ��Ϊ�ҵ��ص�ͻ�����棩"
			sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
			conn.execute(sqlp)
		next
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
		sql="update crm_assignAD set Emphases=0 where com_id in ("& selectcb &") and personid="&session("personid")&""
		conn.Execute sql,ly
		if ly then
			'------------д��ͻ���¼
			arrcom=split(selectcb,",")
			for i=0 to ubound(arrcom)
				com_id=trim(arrcom(i))
				sDetail="��沿"&request.Cookies("admin_user")&"ȡ��Ϊ�ҵ��ص�ͻ�"
				sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
				conn.execute(sqlp)
			next
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
	sqlcount="select count(id) from crm_assignAD where personid="&request("personid")&""
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
				   response.Write("<script>alert('ʧ�ܣ���������Ա�ͻ����Ѿ��ﵽ"&maxrccomp&"����');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
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
				sDetail="��沿"&request.Cookies("admin_user")&"����ͻ�"
				sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
				conn.execute(sqlp)
		rsc.close
		set rsc=nothing
	next
	response.Write("<script>alert('�ɹ�������ɹ���');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
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
			   response.Write("<script>alert('ʧ�ܣ����Ŀͻ����Ѿ��ﵽ"&maxrccomp&"��������ݾ�������������Ŀͻ�.');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
			   closeconn()
			   response.end()
	end if
	for i=0 to ubound(mycrm)
		sqlc="select com_id,personid from crm_assignAD where com_id="&trim(mycrm(i))&""
		set rsc=conn.execute(sqlc)
		if cint(cuscount)>=maxrccomp then
			   response.Write("<script>alert('ʧ�ܣ����Ŀͻ����Ѿ��ﵽ"&maxrccomp&"��������ݾ�������������Ŀͻ�.');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
			   response.end()
	    end if
		
		'����Ǳ������빫��,��ʹ��ص�������Ա��7��֮�ڲ�׼�ٴ�����ÿͻ�
		sqls="select id from crm_beDroppedInSea where fdate>='"&date()-7&"' and personid="&session("personid")&" and com_id="&trim(mycrm(i))
		set rss=conn.execute(sqls)
		if not (rss.eof and rss.bof) then
			   response.Write("<script>alert('ʧ�ܣ����ڸÿͻ�����Ϊ30����û����Ч��ϵ���������빫��,���Ը�������Ա��7��֮�ڲ�׼�ٴ�����ÿͻ�');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
			   response.end()
	    end if
		rss.close
		set rss=nothing
		'�жϽ���
		if rsc.eof then
		   sqlu="insert into crm_assignAD(com_id,personid) values("&trim(mycrm(i))&","&session("personid")&")"
		   conn.execute(sqlu)
		   cuscount=cuscount+1
		   sqlc="delete from crm_adwaste where com_id="&trim(mycrm(i))&""
		   conn.execute(sqlc)
		   response.Write("�ɹ����ÿͻ��Ѿ����䵽")
		else
		   if cstr(rsc("personid"))=cstr(session("personid")) then
			   if cint(cuscount)>=maxrccomp then
				   response.Write("<script>alert('ʧ�ܣ����Ŀͻ����Ѿ��ﵽ"&maxrccomp&"��������ݾ�������������Ŀͻ�.');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
				   response.end()
			   end if
			   sqlc="delete from crm_adwaste where com_id="&trim(mycrm(i))&" "
		       conn.execute(sqlc)
		   else
		   	   response.Write("<script>alert('ʧ�ܣ�ID��Ϊ"&trim(mycrm(i))&"�ͻ��Ѿ��������˷��䣡');window.location='"&request.ServerVariables("HTTP_REFERER")&"';</script>")
			   closeconn()
	           response.end()
		   end if
		end if
		com_id=trim(mycrm(i))
				sDetail="��沿"&request.Cookies("admin_user")&"�ŵ��ҵĿͻ���"
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
	   response.Write("<script>alert('ʧ�ܣ����Ŀͻ����Ѿ��ﵽ"&maxrccomp&"��������ݾ�������������Ŀͻ�.');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
	   closeconn()
	   response.end()
	end if
	
	for i=0 to ubound(mycrm)
	    '------------------�ж��Ƿ�������ͨ���ۿ�
		haveinzst=0
		sqlc="select com_id,personid from crm_assign where com_id="&trim(mycrm(i))&""
		set rsc=conn.execute(sqlc)
		if not rsc.eof or not rsc.bof then
			if cstr(rsc("personid"))=cstr(session("personid")) then
				haveinzst=1
			else
				response.Write("<script>alert('ʧ�ܣ��Ŀͻ��Ѿ�������������Ա������ͨ������ܰ����ŵ���Ĺ���');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
				response.End()
			end if
		end if
		rsc.close
		set rsc=nothing
		
	    '����Ǳ������빫��,��ʹ��ص�������Ա��7��֮�ڲ�׼�ٴ�����ÿͻ�
		sqls="select id from crm_beDroppedInSea where fdate>='"&date()-7&"' and personid="&session("personid")&" and com_id="&trim(mycrm(i))
		set rss=conn.execute(sqls)
		if not (rss.eof and rss.bof) then
			   response.Write("<script>alert('ʧ�ܣ����ڸÿͻ�����Ϊ30����û����Ч��ϵ���������빫��,���Ը�������Ա��7��֮�ڲ�׼�ٴ�����ÿͻ�');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
			   response.end()
	    end if
		rss.close
		set rss=nothing
		'�жϽ���
		
		sqlc="select com_id,personid from crm_assignAD where com_id="&trim(mycrm(i))&""
		set rsc=conn.execute(sqlc)
		if rsc.eof or rsc.bof then
		   if haveinzst=0 then
		   '--------------------��������ͨ��
		   sqlu="insert into crm_assign(com_id,personid) values("&trim(mycrm(i))&","&session("personid")&")"
		   conn.execute(sqlu)
		   sqlu="update comp_sales set com_type=0 where com_id="&trim(mycrm(i))&" "
		   conn.execute(sqlu)
		   end if
		   '--------------------�������
		   sqlu="insert into crm_assignAD(com_id,personid) values("&trim(mycrm(i))&","&session("personid")&")"
		   conn.execute(sqlu)
		   sqlcc="delete from crm_ADwaste where com_id="&trim(mycrm(i))&""
		   conn.execute(sqlcc)
		   '-------------------------
		   cuscount=cuscount+1
		   if cint(cuscount)>=maxrccomp then
			   response.Write("<script>alert('ʧ�ܣ����Ŀͻ����Ѿ��ﵽ"&maxrccomp&"��������ݾ�������������Ŀͻ�.');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
			   closeconn()
			   response.end()
	       end if
		   response.Write("<script>alert('�ɹ����ÿͻ��Ѿ����䵽���ҵĹ��ͻ��⡱')</script>")
		else
		   if cstr(rsc("personid"))=cstr(session("personid")) then
			   sqlcc="delete from crm_ADwaste where com_id="&trim(mycrm(i))
			   conn.execute(sqlcc)
			   if haveinzst=0 then
			   '--------------------��������ͨ��
			   sqlu="insert into crm_assign(com_id,personid) values("&trim(mycrm(i))&","&session("personid")&")"
			   conn.execute(sqlu)
			   sqlu="update comp_sales set com_type=0 where com_id="&trim(mycrm(i))&" "
			   conn.execute(sqlu)
			   cuscount=cuscount+1
			   end if
			   response.Write("<script>alert('�ɹ����ÿͻ��Ѿ����䵽���ҵĹ��ͻ���')</script>")
		   else
			   response.Write("<script>alert('ʧ�ܣ�ID��Ϊ"&trim(mycrm(i))&"�ͻ��Ѿ��������˷��䣡');window.location='"&request.ServerVariables("HTTP_REFERER")&"'</script>")
			   closeconn()
			   response.end()
		   end if
		end if
		        com_id=trim(mycrm(i))
				sDetail="��沿"&request.Cookies("admin_user")&"�ŵ��ҵĿͻ���"
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
	'----------��¼���ŵ�������ʱ�䣬����ע
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
				sDetail="��沿"&request.Cookies("admin_user")&"�ŵ�����"
				sqlp="insert into crm_assignHistory (com_id,personid,sDetail) values("&com_id&","&session("personid")&",'"&sDetail&"')"
				conn.execute(sqlp)
		next
		response.Write("<script>alert('�ɹ������빫��')</script>")
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
