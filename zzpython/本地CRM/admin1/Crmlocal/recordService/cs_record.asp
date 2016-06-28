<!-- #include file="../../include/ad!$#5V.asp" -->
<%
Response.Buffer =false
response.cachecontrol="private"
Response.Expires =0
On Error Resume Next
function getlytel(lytel)
	sqltl="select id from record_list where called like '%"&lytel&"%'"
	if mytel<>"" then
		sqltl=sqltl&" and caller='"&mytel&"' "
	end if
	sqltl=sqltl&" and startime>='"&date&"'"
	
	set rstl=conn.execute(sqltl)
	if not rstl.eof or not rstl.bof then
		getlytel=rstl("id")
	else
		getlytel="0"
	end if
	rstl.close
	set rstl=nothing
end function
		personid=request.QueryString("personid")
		mytel=request.QueryString("mytel")
		com_id=request.QueryString("com_id")
		comtel=request.QueryString("comtel")
		commobile=request.QueryString("commobile")
		telid=request.QueryString("telid")
		'-------读取客户电话
		comtel1=""
		comtel2=""
		comtel3=""
		comtel4=""
		comtelly="0"
		if comtel<>"" and not isnull(comtel) then
			comtel=replace(comtel," ","")
			comtel1=right(replace(comtel,"-",""),7)
			comtel1=left(comtel1,len(comtel1)-1)
			comtelly=getlytel(comtel1)
		end if
		if comtelly="0" then
			if commobile<>"" and not isnull(commobile) then
				commobile=replace(commobile," ","")
				comtel2=right(commobile,9)
				comtel2=left(comtel2,len(comtel2)-1)
				comtelly=getlytel(comtel2)
			end if
		end if
		
		sqlteltemp=""
		
		if comtelly<>"0" then
			sqlteltemp=sqlteltemp&" id="&comtelly&" "
		end if
		if sqlteltemp<>"" then
			'---是CRM里的电话
			notel=0
		else
			'---不是CRM里的电话
			notel=1
		end if
		
		'-------读取录音电话
		'and DATEDIFF(minute,endtime,getdate())<30
		if notel=0 then
			RecordNo=""
			sqlt="select id,startime,caller,accountcode,answeredtime,called,monitorfile,type from record_list where ("&sqlteltemp&")"
			
			if mytel<>"" then
				sqlt=sqlt&" and caller='"&mytel&"' "
			end if
			sqlt=sqlt&" and startime>'"&date&"' order by id desc"
			
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
					telid=0
					'-----写入到录音记录表
					if RecordNo<>"" then
						sqle="select id from crm_callrecord where RecordNo='"&RecordNo&"'"
						set rse=conn.execute(sqle)
						if rse.eof or rse.bof then
							sqlq="insert into crm_callrecord(telid,com_id,cspersonid,mytel,RecordNo,CallerId,CallType,startTime,endTime,recordTime,filePath,Dtmf) values("&telid&","&com_id&","&personid&",'"&mytel&"','"&RecordNo&"','"&CallerId&"','"&CallType&"','"&startTime&"','"&endTime&"','"&recordTime&"','"&filePath&"','"&Dtmf&"')"
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
		end if
	conn.close
	set conn=nothing

%>
