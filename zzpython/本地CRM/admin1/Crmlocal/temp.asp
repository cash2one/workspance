if request.Form("contactflag")="1" and contacttype="13" then  
		connrecord.open strconnrecord
		'-----读取销售电话
		sqlu="select usertel from users where id="&session("personid")&""
		set rsu=conn.execute(sqlu)
		if not rsu.eof or not rsu.bof then
			mytel=rsu(0)
		end if
		rsu.close
		set rsu=nothing
		if mytel="" or isnull(mytel) then mytel=0
		if len(mytel)<8 then
			response.Write("<script>parent.shownottel()</script>"&chr(13))
			response.End()
		end if
		'-------读取客户电话
		comtel1=""
		comtel2=""
		comtel3=""
		comtel4=""
		if comtel<>"" and not isnull(comtel) then
			comtel=replace(comtel," ","")
			comtel1=right(replace(comtel,"-",""),7)
			comtel1=left(comtel1,len(comtel1)-1)
		end if
		if commobile<>"" and not isnull(commobile) then
			commobile=replace(commobile," ","")
			comtel2=right(commobile,9)
			comtel2=left(comtel2,len(comtel2)-1)
		end if
		sqlt="select top 1 persontel,PersonMoblie from crm_PersonInfo where com_id="&idprod
		set rst=conn.execute(sqlt)
		if not rst.eof or not rst.bof then
			if rst("persontel")<>"" and not isnull(rst("persontel")) then
				comtel3=right(replace(rst("persontel"),"-",""),7)
				comtel3=left(comtel3,len(comtel3)-1)
			end if
			if rst("PersonMoblie")<>"" and not isnull(rst("PersonMoblie")) then
				comtel4=right(rst("PersonMoblie"),9)
				comtel4=left(comtel4,len(comtel4)-1)
			end if
		end if
		rst.close
		set rst=nothing
		sqlteltemp=""
		
		if comtel1<>"" then
			if len(comtel1)>=6 then
				sqlteltemp=sqlteltemp&" Dtmf like '%"&comtel1&"%' or"
			end if
		end if
		if comtel2<>"" then
			if len(comtel2)>=8 then
				sqlteltemp=sqlteltemp&" Dtmf like '%"&comtel2&"%' or"
			end if
		end if
		if comtel3<>"" then
			if len(comtel3)>=6 then
				sqlteltemp=sqlteltemp&" Dtmf like '%"&comtel3&"%' or"
			end if
		end if
		if comtel4<>"" then
			if len(comtel4)>=8 then
				sqlteltemp=sqlteltemp&" Dtmf like '%"&comtel4&"%' or"
			end if
		end if
		if sqlteltemp<>"" then
			sqlteltemp=left(sqlteltemp,len(sqlteltemp)-2)
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
			sqlt="select RecordNo,CallerId,CallType,startTime,endTime,recordTime,filePath,Dtmf from callTb where ("&sqlteltemp&") "
			if mytel<>"" then
				sqlt=sqlt&" and calledId='"&mytel&"' "
			end if
			sqlt=sqlt&" and endTime>='"&date&"' order by endTime desc"
			
			set rst=connrecord.execute(sqlt)
			if not rst.eof or not rst.bof then
				while not rst.eof
					RecordNo=rst("RecordNo")
					CallerId=rst("CallerId")
					CallType=rst("CallType")
					startTime=rst("startTime")
					endTime=rst("endTime")
					recordTime=rst("recordTime")
					filePath=rst("filePath")
					Dtmf=rst("Dtmf")
					'-----写入到录音记录表
					if RecordNo<>"" then
						sqle="select id from crm_callrecord where RecordNo='"&RecordNo&"'"
						set rse=conn.execute(sqle)
						if rse.eof or rse.bof then
							sqlq="insert into crm_callrecord(TelID,Dtmf,com_id,personid,mytel,RecordNo,CallerId,CallType,startTime,endTime,recordTime,filePath) values("&TelID&",'"&Dtmf&"',"&idprod&","&session("personid")&",'"&mytel&"','"&RecordNo&"','"&CallerId&"','"&CallType&"','"&startTime&"','"&endTime&"','"&recordTime&"','"&filePath&"')"
							conn.execute(sqlq)
						end if
						rse.close
						set rse=nothing
					end if
				rst.movenext
				wend
			end if
			rst.close
			set rst=nothing
			
		end if
		
		'response.Write((now()-10)&"<br>"&now)
		'response.Write(DATEDIFF("n",now-1,now))
		'response.Write(sqlt)
		connrecord.close
		set connrecord=nothing
		
	 end if