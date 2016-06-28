<!-- #include file="../../include/ad!$#5V.asp" -->
<%
Response.Buffer =false
response.cachecontrol="private"
Response.Expires =0
On Error Resume Next
function getlytel(lytel)
	sqltl="select top 1 id from record_list where called like '%"&lytel&"%' "
	if mytel<>"" then
		sqltl=sqltl&" and caller='"&mytel&"' "
	end if
	sqltl=sqltl&" and startime>='"&date&"' order by id desc"
	
	set rstl=conn.execute(sqltl)
	if not rstl.eof or not rstl.bof then
		getlytel=rstl("id")
	else
		getlytel=""
	end if
	rstl.close
	set rstl=nothing
end function
    sqlc="select top 20 id,com_id,personid from comp_tel where TelDate>'"&date&"' and not exists(select telid from temp_telid where comp_tel.id=telid) order by id desc"
	set rsc=conn.execute(sqlc)
	if not rsc.eof or not rsc.bof then
		while not rsc.eof
			'-----读取销售电话
			personid=rsc("personid")
			sqlu="select usertel from users where id="&personid&""
			set rsu=conn.execute(sqlu)
			if not rsu.eof or not rsu.bof then
				mytel=rsu(0)
			end if
			rsu.close
			set rsu=nothing
			idprod=rsc("com_id")
			'response.Write(idprod&"<br>")
			
			sqlu="select com_tel,com_mobile from comp_info where com_id="&idprod&""
			set rsu=conn.execute(sqlu)
			if not rsu.eof or not rsu.bof then
				comtel=rsu(0)
				commobile=rsu("com_mobile")
			end if
			rsu.close
			set rsu=nothing
			
			
			
			'-------读取客户电话
		
		
		comtel1=""
		comtel2=""
		comtel3=""
		comtel4=""
		comtelly=""
		if comtel<>"" and not isnull(comtel) then
			comtel=replace(comtel," ","")
			comtel1=right(replace(comtel,"-",""),7)
			comtel1=left(comtel1,len(comtel1)-1)
			comtelly=getlytel(comtel1)
		end if
		if comtelly="" then
			if commobile<>"" and not isnull(commobile) then
				commobile=replace(commobile," ","")
				comtel2=right(commobile,9)
				comtel2=left(comtel2,len(comtel2)-1)
				comtelly=getlytel(comtel2)
			end if
		end if
		
		if comtelly="" then
			sqlt="select top 1 persontel,PersonMoblie from crm_PersonInfo where com_id="&idprod
			set rst=conn.execute(sqlt)
			if not rst.eof or not rst.bof then
				while not rst.eof
					if trim(rst("persontel"))<>"" and not isnull(rst("persontel")) then
						comtel3=right(replace(trim(rst("persontel")),"-",""),7)
						comtel3=left(comtel3,len(comtel3)-1)
						comtelly=getlytel(comtel3)
					end if
					if comtelly="" then
						if trim(rst("PersonMoblie"))<>"" and not isnull(rst("PersonMoblie")) then
							comtel4=right(trim(rst("PersonMoblie")),9)
							comtel4=left(comtel4,len(comtel4)-1)
							comtelly=getlytel(comtel4)
						end if
					end if
				rst.movenext
				wend
			end if
			rst.close
			set rst=nothing
		end if
		
		sqlteltemp=""
		
		
		if comtelly<>"" then
			sqlteltemp=sqlteltemp&" id="&comtelly&" "
		end if
		if sqlteltemp<>"" then
				
			'---是CRM里的电话
			notel=0
		else
			'---不是CRM里的电话
			
			notel=1
		end if
			response.Write(notel)
			'-------读取录音电话
			'and DATEDIFF(minute,endtime,getdate())<30
			if notel=0 then
				RecordNo=""
				sqlt="select id,startime,caller,accountcode,answeredtime,called,monitorfile,type from record_list where ("&sqlteltemp&")"
				
				if mytel<>"" then
					sqlt=sqlt&" and caller='"&mytel&"' "
				end if
				sqlt=sqlt&" and startime>'"&date&"' order by id desc"
				
				'response.Write(sqlt&"<br>")
				set rst=conn.execute(sqlt)
				if not rst.eof or not rst.bof then
					while not rst.eof
						RecordNo=rst("id")
						CallerId=rst("caller")
						CallType=rst("type")
						startTime=rst("startime")
						endTime=""
						recordTime=rst("answeredtime")
						'recordTime=""
						filePath=rst("monitorfile")
						Dtmf=rst("called")
						
						'-----写入到录音记录表
						if RecordNo<>"" then
							sqle="select id from crm_callrecord where RecordNo='"&RecordNo&"'"
							set rse=conn.execute(sqle)
							if rse.eof or rse.bof then
								sqlq="insert into crm_callrecord(telid,com_id,personid,mytel,RecordNo,CallerId,CallType,startTime,endTime,recordTime,filePath,Dtmf) values("&rsc("id")&","&idprod&","&personid&",'"&mytel&"','"&RecordNo&"','"&CallerId&"','"&CallType&"','"&startTime&"','"&endTime&"','"&recordTime&"','"&filePath&"','"&Dtmf&"')"
								conn.execute(sqlq)
							end if
							rse.close
							set rse=nothing
							response.Write("写入成功<br>")
						end if
						rst.movenext
					wend
				end if
				rst.close
				set rst=nothing
				
				sql="insert into temp_telid(telid) values("&rsc("id")&")"
				conn.execute(sql)
			end if
			
			
			a=a+1
		rsc.movenext
		wend
	end if
	rsc.close
	set rsc=nothing
	conn.close
	set conn=nothing
	frompagea=split(request.servervariables("script_name"),"/")
	frompage=lcase(frompagea(UBound(frompagea)))
	frompagequrstr=Request.ServerVariables("QUERY_STRING")
	response.Write("<script>setTimeout(""window.location='"&frompage&"?"&frompagequrstr&"'"",2000*5)</script>")
%>
