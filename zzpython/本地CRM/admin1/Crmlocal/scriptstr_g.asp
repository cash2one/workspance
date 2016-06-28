<script>
<%
if comidList<>"" then
comidList=left(comidList,len(comidList)-1)
end if
'今天登陆激活的客户
if comidList<>"" then
	sqlm="select com_id from crm_activeComp where com_id in ("&comidList&")"
	 set rsm=conn.execute(sqlm)
	 if not rsm.eof or not rsm.bof then
		 while not rsm.eof
			com_id=rsm(0)
			Str="<div class='crm_tishi'>今天登陆激活的客户</div>"
			response.Write("document.getElementById(""activeComp"&com_id&""").innerHTML="""&Str&""";"&chr(13))
		 rsm.movenext
		 wend
	 end if
	 rsm.close
	 set rsm=nothing
end if
'------------收集优质客户
if comidList<>"" then
	 sqlm="select com_id from comp_fine where com_id in ("&comidList&")"
	 set rsm=conn.execute(sqlm)
	 if not rsm.eof or not rsm.bof then
		 while not rsm.eof
			com_id=rsm(0)
			Str="<div class='crm_tishi'>收集优质客户</div>"
			response.Write("document.getElementById(""fine"&com_id&""").innerHTML="""&Str&""";"&chr(13))
		 rsm.movenext
		 wend
	 end if
	 rsm.close
	 set rsm=nothing
end if

'------------收集优质客户
if comidList<>"" then
	 sqlm="select com_id from comp_fine where com_id in ("&comidList&")"
	 set rsm=conn.execute(sqlm)
	 if not rsm.eof or not rsm.bof then
		 while not rsm.eof
			com_id=rsm(0)
			Str="<div class='crm_tishi'>收集优质客户</div>"
			response.Write("document.getElementById(""fine"&com_id&""").innerHTML="""&Str&""";"&chr(13))
		 rsm.movenext
		 wend
	 end if
	 rsm.close
	 set rsm=nothing
end if



'------------录入者
if comidList<>"" then
	 sqlm="select b.realname,a.com_id from crm_InsertCompWeb as a,users as b where a.com_id in ("&comidList&") and a.personid=b.id "
	 set rsm=conn.execute(sqlm)
	 if not rsm.eof or not rsm.bof then
		 while not rsm.eof
			com_id=rsm("com_id")
			Str=rsm("realname")
			response.Write("document.getElementById(""realnamelr"&com_id&""").innerHTML="""&Str&""";"&chr(13))
		 rsm.movenext
		 wend
	 end if
	 rsm.close
	 set rsm=nothing
	 if left(dotype,3)<>"sms" then
		 sqlm="select com_id,checked from crm_To4star where com_id in ("&comidList&")"
		 set rsm=conn.execute(sqlm)
		 if not rsm.eof or not rsm.bof then
			 while not rsm.eof
				com_id=rsm("com_id")
				checked=rsm("checked")
				Str=""
				if checked="0" then
					Str="<font color=#ff0000>未审</font>"
				end if
				response.Write("document.getElementById(""comrankshenhe"&com_id&""").innerHTML="""&Str&""";"&chr(13))
			 rsm.movenext
			 wend
		 end if
		 rsm.close
		 set rsm=nothing
		 
		 sqlm="select com_id,checked from crm_To5star where com_id in ("&comidList&")"
		 set rsm=conn.execute(sqlm)
		 if not rsm.eof or not rsm.bof then
			 while not rsm.eof
				com_id=rsm("com_id")
				checked=rsm("checked")
				Str=""
				if checked="0" then
					Str="<font color=#ff0000>未审</font>"
				end if
				response.Write("document.getElementById(""comrankshenhe"&com_id&""").innerHTML="""&Str&""";"&chr(13))
			 rsm.movenext
			 wend
		 end if
		 rsm.close
		 set rsm=nothing
	end if
	 
end if
'------------所属者
if comidList<>"" then
	 sqlm="select b.realname,a.com_id from crm_assign as a,users as b where a.com_id in ("&comidList&") and a.personid=b.id "
	 set rsm=conn.execute(sqlm)
	 if not rsm.eof or not rsm.bof then
		 while not rsm.eof
			com_id=rsm("com_id")
			Str=rsm("realname")
			response.Write("document.getElementById(""realname"&com_id&""").innerHTML="""&Str&""";"&chr(13))
		 rsm.movenext
		 wend
	 end if
	 rsm.close
	 set rsm=nothing
end if

'------------vap所属者
if comidList<>"" and left(dotype,3)="sms" then
	 sqlm="select b.realname,a.com_id from crm_assignvap as a,users as b where a.com_id in ("&comidList&") and a.personid=b.id "
	 set rsm=conn.execute(sqlm)
	 if not rsm.eof or not rsm.bof then
		 while not rsm.eof
			com_id=rsm("com_id")
			Str=rsm("realname")
			response.Write("document.getElementById(""realnamevap"&com_id&""").innerHTML="""&Str&""";"&chr(13))
		 rsm.movenext
		 wend
	 end if
	 rsm.close
	 set rsm=nothing
end if
'------------sms所属者
if comidList<>"" and left(dotype,3)="sms" then
	 sqlm="select b.realname,a.com_id from crm_assignsms as a,users as b where a.com_id in ("&comidList&") and a.personid=b.id "
	 set rsm=conn.execute(sqlm)
	 if not rsm.eof or not rsm.bof then
		 while not rsm.eof
			com_id=rsm("com_id")
			Str=rsm("realname")
			response.Write("document.getElementById(""realnamesms"&com_id&""").innerHTML="""&Str&""";"&chr(13))
		 rsm.movenext
		 wend
	 end if
	 rsm.close
	 set rsm=nothing
end if
'-------广告相关客户
if comidList<>"" then
	arrcomid=split(comidList,",")
	for i=0 to ubound(arrcomid)
		sql="select code from temp_adcompSelect where com_id in ("&arrcomid(i)&")"
		set rs=conn.execute(sql)
		if not rs.eof or not rs.bof then
			sqla="select meno from temp_adcompSelect where id="&rs(0)&""
			set rsa=conn.execute(sqla)
			if not rsa.eof or not rsa.bof then
				Str=rsa(0)
				response.Write("document.getElementById(""laiyuan"&arrcomid(i)&""").innerHTML=""<span class=laiyuan>广告相关客户："&Str&"</span>"";"&chr(13))
			end if
			rsa.close
			set rsa=nothing
		end if
		rs.close
		set rs=nothing
	next
	
end if

%>
</script>
