<%

'***************************************下拉
function selectprovine(dbname,dbname1,selectname,dbvalue)
	temp="<script language='JavaScript' type='text/JavaScript'>"&chr(13)
	temp=temp&"function change_sort"&selectname&"(sindustry)"&chr(13)
	temp=temp&"{"&chr(13)
	querya="select * from "&dbname&""
	set resulta=conn.execute(querya)
	if not resulta.eof then
    do while not resulta.eof  
		temp=temp&"switch (sindustry) {"&chr(13)
		temp=temp&"case '"&resulta("cb_id")&"':"&chr(13)
		temp=temp&"var temp;"&chr(13)
		temp=temp&"temp=""<select name='"&selectname&"' onChange='slet()'>"";"&chr(13)
		temp=temp&"temp+=""<option value='' >请选择小类</option>"";"&chr(13)
		queryb="select * from "&dbname1&" where cs_cb_id="&resulta("cb_id")&" order by cs_chn_name asc"
		'response.Write(querya)
        set resultb=server.CreateObject("ADODB.recordset")
        resultb.open queryb,conn,1,1
		
        if not resultb.eof then
	      do while not resultb.eof  
		    temp=temp&"temp+=""<option value='"&resultb("cs_id")&"'>"&resultb("cs_chn_name")&"</option>;"""&chr(13)
		  resultb.movenext
		  loop
		end if 
		resultb.close()
		set resultb=nothing
		temp=temp&"temp+=""</select>"";"&chr(13)
		temp=temp&"document.all[""sortmain"&selectname&"""].innerHTML=temp;"&chr(13)
		temp=temp&"break;"&chr(13)
		temp=temp&"}"&chr(13)
	resulta.movenext
	loop
	end if
	resulta.close()
	set resulta=nothing
	temp=temp&" }"&chr(13)
	temp=temp&"</script>"&chr(13)
	temp=temp&"<select name=""province"&selectname&""" id=""province"&selectname&""" onChange=""javascript:change_sort"&selectname&"(this.options[this.selectedIndex].value)"">"&chr(13)
    temp=temp&"<option value=''>请选择大类</option>"&chr(13)
	if dbvalue<>"" then
    sqlleib="select cs_cb_id from "&dbname1&" where cs_id="&dbvalue
    set resleib=conn.execute(sqlleib)
    rscs_cb_id=resleib(0)
	resleib.close()
	set resleib=nothing
	end if
	
		query="select * from "&dbname&""
		set result=conn.execute(query)
		if not result.eof then
	      do while not result.eof 
		    if cstr(rscs_cb_id)=cstr(result("cb_id")) then
			temp=temp&"<option value='"&result("cb_id")&"' selected>"&result("cb_chn_name")&"</option>"&chr(13)
			else
            temp=temp&"<option value='"&result("cb_id")&"'>"&result("cb_chn_name")&"</option>"&chr(13)
		    end if
		  result.movenext
		  loop
		end if
		result.close()
	set result=nothing
    temp=temp&"</select>"&chr(13)
	temp=temp&"<font id=""sortmain"&selectname&""">"&chr(13)
	if dbvalue<>"" then
	temp=temp&"<select name="""&selectname&""" id="""&selectname&"""  onChange='slet()'>"&chr(13)
    
	queryb="select * from "&dbname1&" where cs_cb_id="&rscs_cb_id&""
		set resultb=conn.execute(queryb)
		if not resultb.eof then
	      do while not resultb.eof  
		    if cstr(trim(dbvalue))=cstr(trim(resultb("cs_id"))) then
			temp=temp&"<option value='"&resultb("cs_id")&"' selected>"&resultb("cs_chn_name")&"</option>;"&chr(13)
			else
		    temp=temp&"<option value='"&resultb("cs_id")&"'>"&resultb("cs_chn_name")&"</option>;"&chr(13)
		    end if
		  resultb.movenext
		  loop
		end if 
		resultb.close()
	    set resultb=nothing
    temp=temp&"</select>"&chr(13)
	else
    temp=temp&"<select name="""&selectname&""" id="""&selectname&"""  onChange='slet()'>"&chr(13)
    temp=temp&"<option value=''>请选择小类</option>"&chr(13)
    temp=temp&"</select>"&chr(13)
	end if
    temp=temp&"</font>"&chr(13)
response.Write(temp)
end function

'*************************************
function gnqxflag(gnn)
'gn=10:查看，11:添加,12:修改，13:删除，14:审批,15:项目立项,16:资金返还,17:资金上汇,18:确认申请;19:分配
sqllm="select * from user_gnqx where personid="&trim(session("personid"))&" and lmid='"&session("lmcode")&"'"
set rslm=server.CreateObject("adodb.recordset")
rslm.open sqllm,conn,1,3
gnflag=0
	if not rslm.eof or not rslm.bof then
	    if rslm("qx")="" or isnull(rslm("qx")) then
		else 
		arraygn=split(rslm("qx"),",",-1,1)
		for i=0 to ubound(arraygn)
			if cstr(gnn)=cstr(trim(arraygn(i))) then
				gnflag=1
			end if 
		next
		end if
	else
	gnqxflag=0
	end if
	rslm.close
	set rslm=nothing
	if gnflag=1 then
	gnqxflag=1
	else
	gnqxflag=0
	end if
end function


function limitqx(littleuserID,m)

  if m=4 then
	  if session("userid")="12" or session("userid")="14" or session("userid")="11" then
	  limitqx=1
	  else
	  limitqx=0
	  end if
  end if
  if m=2 then
	  if session("userid")="10" then
	  limitqx=1
	  else
	  limitqx=0
	  end if
  end if
  if m=1 or m=0 then
    if  session("userid")="12" or session("userid")="14" or (session("userid")="11" and (lmmu="up" or lmmu="ld" or lmmu="de")) then
	limitqx=1
	else
	   if m=1 then
	   limitqx=0
	   end if
	   if m=0 then
	        littleuserID11=littleuserID
			 if  littleuserID<>"" then
				if len(trim(littleuserID))>4 and len(trim(session("littleuserID")))=4 then
				  littleuserID11=left(trim(littleuserID),4)
				end if
				'response.Write(littleuserID)
			 end if
			if trim(session("littleuserID"))=trim(littleuserID11) then
			limitqx=1
			else
			limitqx=0
			end if
	   end if
	end if
  end if
  if m=3 then
  aaaa=left(session("userid"),2)
	  if aaaa="12" or aaaa="14" or aaaa="13" then
	  limitqx=1
	  else
	  limitqx=0
	  end if
  end if
  if m=5 then
  aaaa=left(session("userid"),2)
	  if aaaa="12" or aaaa="14" then
	  limitqx=1
	  else
	  limitqx=0
	  end if
  end if
  if m=6 then
    if len(session("userid"))<4 then
	limitqx=0
	else
	  if len(session("userid"))=4 and left(littleuserID,4)=session("userid") then
	  	limitqx=1
	  else
	  	if len(session("userid"))>4 and littleuserID=session("userid") then
			limitqx=1
		else
			limitqx=0
		end if
	  end if
	end if
  end if
  if m=7 then
  	 if session("userid")=14 or len(session("userid"))>=4 then
	 	limitqx=1
	 else
	 	limitqx=0
	 end if
  end if
end function

'********************图片广告
function gg(id)
	sqlpic="select * from TB_guanggao where id="&id
	set rspic=server.CreateObject("adodb.recordset")
	rspic.open sqlpic,conn,1,1
	if not rspic.eof or not rspic.bof then
	  if rspic("geshi")="gif" or rspic("geshi")="jpg" then
	  response.Write("<img src="&rspic("addr")&" width='"&rspic("gwidth")&"' height='"&rspic("gheight")&"'>")
	  end if
	  if rspic("geshi")="swf" then
	  sw="<object classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000' codebase='http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0' width='"&rspic("gwidth")&"' height='"&rspic("gheight")&"'>"
      sw=sw&"<param name='movie' value='"&rspic("addr")&"'>"
      sw=sw&"<param name='quality' value='high'>"
	  sw=sw&"<param name='wmode' value='transparent'>"
      sw=sw&"<embed src='"&rspic("addr")&"' quality='high' pluginspage='http://www.macromedia.com/go/getflashplayer' type='application/x-shockwave-flash' width='"&rspic("gwidth")&"' height='"&rspic("gheight")&"' wmode='transparent'></embed>"
      sw=sw&"</object>"
	  response.Write(sw)
	  end if
	end if
	rspic.close()
	set rspic=nothing
end function
'*****************显示链接
function gglink(lb,sl)
	sqlpic="select top "&sl&" * from TB_guanggao where  lb='"&lb&"' and flag='1' order by ord asc"
	set rspic=server.CreateObject("adodb.recordset")
	rspic.open sqlpic,conn,1,1
	if not rspic.eof or not rspic.bof then
	ii=1
	sw="<table width=100%  border=0 cellspacing=0 cellpadding=5><tr>"
	Do While Not rspic.EOF
	if lb="11" then
	  if rspic("geshi")="gif" or rspic("geshi")="jpg" then
	  sw=sw&"<td><script >document.write("&chr(34)&"<a href='"&rspic("glink")&"' target='_blank'><img src="&rspic("addr")&" width='"&rspic("gwidth")&"' alt='"&rspic("title")&"' height='"&rspic("gheight")&"' border='0'></a>"&chr(34)&");</script>"
	  end if
	  if rspic("geshi")="swf" then
	  sw=sw&"<td><object classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000' codebase='http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0' width='"&rspic("gwidth")&"' height='"&rspic("gheight")&"'>"
      sw=sw&"<param name='movie' value='"&rspic("addr")&"'>"
      sw=sw&"<param name='quality' value='high'>"
	  sw=sw&"<param name='wmode' value='transparent'>"
      sw=sw&"<embed src='"&rspic("addr")&"' quality='high' pluginspage='http://www.macromedia.com/go/getflashplayer' type='application/x-shockwave-flash' width='"&rspic("gwidth")&"' height='"&rspic("gheight")&"' wmode='transparent'></embed>"
      sw=sw&"</object>"
	  end if
	  if (ii mod 5)=0 then
      sw=sw&"</td></tr><tr>"
	  end if
	else
	  sw=sw&"<td><script >document.write("&chr(34)&"<a href='"&rspic("glink")&"' target=_blank class='link_black'><font color="&rspic("gcolor")&">"&rspic("title")&"</font></a>"&chr(34)&");</script>"
	  if (ii mod 4)=0 then
      sw=sw&"</td></tr><tr>"
	  end if
	end if
	  
	rspic.movenext
	ii=ii+1
	loop
	sw=sw&"</td></tr></table>"
	response.Write(sw)
	end if
	rspic.close()
	set rspic=nothing
end function
'*****************显示链接
function gglink_index(lb,sl)
	sqlpic="select top "&sl&" * from TB_guanggao where  lb='"&lb&"' and flag='1' order by ord asc"
	set rspic=server.CreateObject("adodb.recordset")
	rspic.open sqlpic,conn,1,1
	if not rspic.eof or not rspic.bof then
	ii=1
	sw="<table width=100%  border=0 cellspacing=0 cellpadding=5><tr>"
	Do While Not rspic.EOF
	if lb="11" then
	  if rspic("geshi")="gif" or rspic("geshi")="jpg" then
	  sw=sw&"<td><script >document.write("&chr(34)&"<a href='"&rspic("glink")&"' target='_blank'><img src="&rspic("addr")&" width='"&rspic("gwidth")&"' alt='"&rspic("title")&"' height='"&rspic("gheight")&"' border='0'></a>"&chr(34)&");</script>"
	  end if
	  if rspic("geshi")="swf" then
	  sw=sw&"<td><object classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000' codebase='http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0' width='"&rspic("gwidth")&"' height='"&rspic("gheight")&"'>"
      sw=sw&"<param name='movie' value='"&rspic("addr")&"'>"
      sw=sw&"<param name='quality' value='high'>"
	  sw=sw&"<param name='wmode' value='transparent'>"
      sw=sw&"<embed src='"&rspic("addr")&"' quality='high' pluginspage='http://www.macromedia.com/go/getflashplayer' type='application/x-shockwave-flash' width='"&rspic("gwidth")&"' height='"&rspic("gheight")&"' wmode='transparent'></embed>"
      sw=sw&"</object>"
	  end if
	  if (ii mod 7)=0 then
      sw=sw&"</td></tr><tr>"
	  end if
	else
	  sw=sw&"<td><script >document.write("&chr(34)&"<a href='"&rspic("glink")&"' target=_blank class='link_black'><font color="&rspic("gcolor")&">"&rspic("title")&"</font></a>"&chr(34)&");</script>"
	  if (ii mod 4)=0 then
      sw=sw&"</td></tr><tr>"
	  end if
	end if
	  
	rspic.movenext
	ii=ii+1
	loop
	sw=sw&"</td></tr></table>"
	response.Write(sw)
	end if
	rspic.close()
	set rspic=nothing
end function
'*****************等待审核链接
function gglink_no(lb,sl)
	sqlpic="select top "&sl&" * from TB_guanggao where  lb='"&lb&"' and (flag is null or flag='0') order by ord asc"
	set rspic=server.CreateObject("adodb.recordset")
	rspic.open sqlpic,conn,1,1
	if not rspic.eof or not rspic.bof then
	ii=1
	sw="<table width=100%  border=0 cellspacing=0 cellpadding=5><tr>"
	Do While Not rspic.EOF
	if lb="11" then
	  if rspic("geshi")="gif" or rspic("geshi")="jpg" then
	  sw=sw&"<td><a href='"&rspic("glink")&"' target='_blank'><img src="&rspic("addr")&" width='"&rspic("gwidth")&"' alt='"&rspic("title")&"' height='"&rspic("gheight")&"' border='0'></a>"
	  end if
	  if rspic("geshi")="swf" then
	  sw=sw&"<td><object classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000' codebase='http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0' width='"&rspic("gwidth")&"' height='"&rspic("gheight")&"'>"
      sw=sw&"<param name='movie' value='"&rspic("addr")&"'>"
      sw=sw&"<param name='quality' value='high'>"
	  sw=sw&"<param name='wmode' value='transparent'>"
      sw=sw&"<embed src='"&rspic("addr")&"' quality='high' pluginspage='http://www.macromedia.com/go/getflashplayer' type='application/x-shockwave-flash' width='"&rspic("gwidth")&"' height='"&rspic("gheight")&"' wmode='transparent'></embed>"
      sw=sw&"</object>"
	  end if
	  if (ii mod 5)=0 then
      sw=sw&"</td></tr><tr>"
	  end if
	else
	  sw=sw&"<td><a href='"&rspic("glink")&"' target=_blank class='link_black'><font color="&rspic("gcolor")&">"&rspic("title")&"</font></a>"
	  if (ii mod 4)=0 then
      sw=sw&"</td></tr><tr>"
	  end if
	end if
	  
	rspic.movenext
	ii=ii+1
	loop
	sw=sw&"</td></tr></table>"
	response.Write(sw)
	end if
	rspic.close()
	set rspic=nothing
end function
'*************************
function cate(tb,a,b)
set rs_cate=server.CreateObject("adodb.recordset")
sql_cate="select * from "&tb
rs_cate.open sql_cate,conn,1,1
place="<select name='"& a &"'>"
if not rs_cate.eof or not rs_cate.bof then

	do while not rs_cate.eof
	if cstr(trim(b))=cstr(trim(rs_cate("code"))) then
	place=place&"<option value='" & rs_cate("code") & "' selected>"& rs_cate(2) &"</option>"
    else
    place=place&"<option value='" & rs_cate("code") & "'>" & rs_cate(2) & "</option>"
    end if
	rs_cate.movenext
	loop
	rs_cate.close
	set rs_cate=nothing

end if
place=place&"</select>"
response.Write(place)
end function
'''''''''''''''''''''''''''''''''''''''''''''''''
function zyz(tb,a,b,d)
set rs_cate=server.CreateObject("adodb.recordset")
sql_cate="select * from "&tb&" where code like '"&d&"__'"
rs_cate.open sql_cate,conn,1,1
place="<select name='"& a &"'>"
if not rs_cate.eof or not rs_cate.bof then

	do while not rs_cate.eof
	if cstr(trim(b))=cstr(trim(rs_cate("code"))) then
	place=place&"<option value='" & rs_cate("code") & "' selected>"& rs_cate(2) &"</option>"
    else
    place=place&"<option value='" & rs_cate("code") & "'>" & rs_cate(2) & "</option>"
    end if
	rs_cate.movenext
	loop
	rs_cate.close
	set rs_cate=nothing

end if
place=place&"</select>"
response.Write(place)
end function
''''''''''''''''''''''''''''''''''''''''''
function shows(a,b)
set rs_selet=server.CreateObject("adodb.recordset")
sqlselet="select meno from "&a&" where code='"&b&"'"
rs_selet.open sqlselet,conn,1,1
if not rs_selet.eof or not rs_selet.bof then
response.Write(trim(rs_selet("meno")))
end if
rs_selet.close
set rs_selet=nothing
end function
'''''''''''''''
function shows_(a,b)
set rs_selet=server.CreateObject("adodb.recordset")
sqlselet="select meno from "&a&" where code='"&b&"'"
rs_selet.open sqlselet,conn,1,1
if not rs_selet.eof or not rs_selet.bof then
shows_=trim(rs_selet("meno"))
end if
rs_selet.close
set rs_selet=nothing
end function
'''''''''''''''
'***************************
function cates(tb,a,b)
set rs_cate=server.CreateObject("adodb.recordset")
sql_cate="select * from "&tb
rs_cate.open sql_cate,conn,1,1
place="<select name='"& a &"' id='"& a &"'>"
place=place&"<option value=>请选择</option>"
if not rs_cate.eof or not rs_cate.bof then

	do while not rs_cate.eof
	if trim(b)<>"" then	
		if cstr(trim(b))=cstr(trim(rs_cate("code"))) then
		place=place&"<option value='" & trim(rs_cate("code")) & "' selected>"& trim(rs_cate(2)) &"</option>"
		else
		place=place&"<option value='" & trim(rs_cate("code")) & "'>" & trim(rs_cate(2)) & "</option>"
		end if
	else
	    place=place&"<option value='" & trim(rs_cate("code")) & "'>" & trim(rs_cate(2)) & "</option>"
	end if
	rs_cate.movenext
	loop
	rs_cate.close
	set rs_cate=nothing

end if
place=place&"</select>"
response.Write(place)
end function
'''''''''''''''''''''''''''''''''''''''''''''''''
'***************************仓库
function catesck(tb,a,b,cksql)
set rs_cate=server.CreateObject("adodb.recordset")
sql_cate="select * from "&tb
if cksql="" or isnull(cksql) then
else
sql_cate=sql_cate&" where "&cksql
end if
rs_cate.open sql_cate,conn,1,1
place="<select name='"& a &"' id='"& a &"'>"
place=place&"<option value=>请选择</option>"
if not rs_cate.eof or not rs_cate.bof then

	do while not rs_cate.eof
	if trim(b)<>"" then	
		if cstr(trim(b))=cstr(trim(rs_cate("code"))) then
		place=place&"<option value='" & trim(rs_cate("code")) & "' selected>"& trim(rs_cate(2)) &"</option>"
		else
		place=place&"<option value='" & trim(rs_cate("code")) & "'>" & trim(rs_cate(2)) & "</option>"
		end if
	else
	    place=place&"<option value='" & trim(rs_cate("code")) & "'>" & trim(rs_cate(2)) & "</option>"
	end if
	rs_cate.movenext
	loop
	rs_cate.close
    set rs_cate=nothing
end if
place=place&"</select>"
response.Write(place)
end function
'''''''''''''''''''''''''''''''''''''''''''''''''
'***************************
function catesdo(tb,a,b,c,sizes)
set rs_cate=server.CreateObject("adodb.recordset")
sql_cate="select * from "&tb
rs_cate.open sql_cate,conn,1,1
if c<>"" then
place="<input name='"& a &"' class=wenbenkuang type='text' size='"&sizes&"' id='"& a &"' value='"&b&"' onclick=""window.plus('div"&c&"')"">"
place=place&"<table border=""0"" cellpadding=""0"" cellspacing=""0"" name='div"&c&"' id='div"&c&"' style='display:none; POSITION: absolute; overflow: visible; visibility: visible;'><tr><td><select name='"& c &"' onChange=dochan('"&c&"','"&a&"') onmouseout=""window.plus('div"&c&"')"">"
else
place="<select name='"& a &"'>"
end if
place=place&"<option value=>请选择--</option>"
if not rs_cate.eof or not rs_cate.bof then

	do while not rs_cate.eof
	if trim(b)<>"" then	
		if cstr(trim(b))=cstr(trim(rs_cate("code"))) then
		place=place&"<option value='" & trim(rs_cate("code")) & "' selected>"& trim(rs_cate(2)) &"</option>"
		else
		place=place&"<option value='" & trim(rs_cate("code")) & "'>" & trim(rs_cate(2)) & "</option>"
		end if
	else
	    place=place&"<option value='" & trim(rs_cate("code")) & "'>" & trim(rs_cate(2)) & "</option>"
	end if
	rs_cate.movenext
	loop
	rs_cate.close
    set rs_cate=nothing
end if
if c<>"" then
place=place&"</select></td></tr></table>"
else
place=place&"</select>"
end if
response.Write(place)
end function
'************************************
'***************************
function catesdo1(tb,a,b,c,sizes)
set rs_cate=server.CreateObject("adodb.recordset")
sql_cate="select * from "&tb
rs_cate.open sql_cate,conn,1,1
if c<>"" then
place="<input name='"& a &"' class=kong type='text' size='"&sizes&"' id='"& a &"' value='"&b&"'  onclick=""window.plus('div"&c&"')"">"
place=place&"<table border=""0"" cellpadding=""0"" cellspacing=""0"" name='div"&c&"' id='div"&c&"' style='display:none; POSITION: absolute; overflow: visible; visibility: visible;'><tr><td><select name='"& c &"' onChange=dochan('"&c&"','"&a&"') onmouseout=""window.plus('div"&c&"')"">"
else
place="<select name='"& a &"'>"
end if
place=place&"<option value=>请选择--</option>"
if not rs_cate.eof or not rs_cate.bof then

	do while not rs_cate.eof
	if trim(b)<>"" then	
		if cstr(trim(b))=cstr(trim(rs_cate("meno"))) then
		place=place&"<option value='" & trim(rs_cate("meno")) & "' selected>"& trim(rs_cate(2)) &"</option>"
		else
		place=place&"<option value='" & trim(rs_cate("meno")) & "'>" & trim(rs_cate(2)) & "</option>"
		end if
	else
	    place=place&"<option value='" & trim(rs_cate("meno")) & "'>" & trim(rs_cate(2)) & "</option>"
	end if
	rs_cate.movenext
	loop
	rs_cate.close
    set rs_cate=nothing
end if
if c<>"" then
place=place&"</select></td></tr></table>"
'place=place&"<img src='images/DOWN.jpg' width='13' height='17' onclick=""window.plus('div"&c&"')"">"

else
place=place&"</select>"
end if
response.Write(place)
end function
'************************************************仓库
'***************************
function catesdock(tb,a,b,c,sizes)
set rs_cate=server.CreateObject("adodb.recordset")
sql_cate="select * from "&tb&" where littleuserid='"&session("littleuserid")&"'"
rs_cate.open sql_cate,conn,1,1
if c<>"" then
place="<input name='"& a &"' class=kong type='text' size='"&sizes&"' id='"& a &"' value='"&b&"'  onclick=""window.plus('div"&c&"')"">"
place=place&"<table border=""0"" cellpadding=""0"" cellspacing=""0"" name='div"&c&"' id='div"&c&"' style='display:none; POSITION: absolute; overflow: visible; visibility: visible;'><tr><td><select name='"& c &"' onChange=dochan('"&c&"','"&a&"') onmouseout=""window.plus('div"&c&"')"">"
else
place="<select name='"& a &"'>"
end if
place=place&"<option value=>请选择--</option>"
if not rs_cate.eof or not rs_cate.bof then

	do while not rs_cate.eof
	if trim(b)<>"" then	
		if cstr(trim(b))=cstr(trim(rs_cate("meno"))) then
		place=place&"<option value='" & trim(rs_cate("meno")) & "' selected>"& trim(rs_cate(2)) &"</option>"
		else
		place=place&"<option value='" & trim(rs_cate("meno")) & "'>" & trim(rs_cate(2)) & "</option>"
		end if
	else
	    place=place&"<option value='" & trim(rs_cate("meno")) & "'>" & trim(rs_cate(2)) & "</option>"
	end if
	rs_cate.movenext
	loop
	rs_cate.close
    set rs_cate=nothing
end if
if c<>"" then
place=place&"</select></td></tr></table>"
'place=place&"<img src='images/DOWN.jpg' width='13' height='17' onclick=""window.plus('div"&c&"')"">"

else
place=place&"</select>"
end if
response.Write(place)
end function
'************************************
function cate_radio(tb,a,b)
set rs_cate=server.CreateObject("adodb.recordset")
sql_cate="select * from "&tb
rs_cate.open sql_cate,conn,1,1
place=""
if not rs_cate.eof or not rs_cate.bof then

	do while not rs_cate.eof
	if b="" then
	b="10"
	end if
	if cstr(trim(b))=cstr(trim(rs_cate("code"))) then
	place=place&"<input type='radio' name='"&a&"' value='" & rs_cate("code") & "' checked='checked' />"& rs_cate(2)
    else
    place=place&"<input type='radio' name='"&a&"' value='" & rs_cate("code") & "' />"& rs_cate(2)
    end if
	rs_cate.movenext
	loop
	rs_cate.close
    set rs_cate=nothing
end if

response.Write(place)
end function
'*************************************
function selet(a,b,c)
set rs_selet=server.CreateObject("adodb.recordset")
sqlselet="select "&b&" from "&a&" where code='"&c&"'"
rs_selet.open sqlselet,conn,1,1
if not rs_selet.eof or not rs_selet.bof then
response.Write(rs_selet(b))
end if
rs_selet.close
set rs_selet=nothing
end function
''''''''''''''''''''''''''''''''''''''''''
function selet1(a,b,c,d)
set rs_selet=server.CreateObject("adodb.recordset")
sqlselet="select "&b&" from "&a&" where "&c&"='"&d&"'"
rs_selet.open sqlselet,conn,1,1
if not rs_selet.eof or not rs_selet.bof then
response.Write(rs_selet(b))
end if
rs_selet.close
set rs_selet=nothing
end function
''''''''''''''''''''''''''''''''''''''''''
''''''''''''''''''''''''''''''''''''''''''
function selet2(a,b,c,d)
set rs_selet=server.CreateObject("adodb.recordset")
sqlselet="select "&b&" from "&a&" where "&c&"="&d
rs_selet.open sqlselet,conn,1,1
if not rs_selet.eof or not rs_selet.bof then
response.Write(rs_selet(b))
end if
rs_selet.close
set rs_selet=nothing
end function
'&&&
function selet_(a,b,c,d)
set rs_selet=server.CreateObject("adodb.recordset")
sqlselet="select "&a&" from "&b&" where "&c&"="&d
rs_selet.open sqlselet,conn,1,1
if not rs_selet.eof or not rs_selet.bof then
response.Write(rs_selet(0))
end if
rs_selet.close
set rs_selet=nothing
end function
'''''''''''''''''''''''''''''''''''''''''
function pagetop(page)
		  
dim total,rn
	rn=10  '设定每页显示数
	rs.pagesize=rn
	total=rs.recordcount
    totalpg=rs.pagecount	
    if totalpg-page<0 then
   	page=1                                                                                                         
    end if
dim flagaa
   flagaa=1
if not rs.eof  then
		rs.pagesize=rn
		rs.AbsolutePage =1
		if page<>"" then 
		rs.AbsolutePage =page
		end if
		RowCount=rn
		beginpage=rn*(page-1)
		if beginpage=0 then
		beginpage=1
		end if
		if total<rn then
		endpage=total
		else 
			if clng(page)>=totalpg then
			endpage=total
			else
			endpage=rn*(page)
			end if
		end if
end if
dim temp
temp="<font color='#666666'> 共" & totalpg & "页"& total &"条记录  本页显示第" & beginpage &"-"& endpage &"条 </font>"
response.write temp
end function
Function lencontrol(inputstr, lenall)
	Dim i, k
	k = 0
	str = trim(inputstr)
	For i = 1 to Len(inputstr)
		IF Abs(Asc(Mid(inputstr, i, 1))) > 255 Then k = k + 2 Else k = k + 1
		IF k >= lenall Then
			inputstr = Left(inputstr, i-1) & "..."
			Exit For
		End IF
	Next
	lencontrol = inputstr
End Function
MailServerfrom="admin@zz91.com"
MailServerUserName="admin@zz91.com"
MailServerPassword="88888888"

Function SafeRequest(ParaName,ParaType) 
'--- 传入参数 --- 
'ParaName:参数名称-字符型 
'ParaType:参数类型-数字型(1表示以上参数是数字，0表示以上参数为字符) 
 posttype=Request.ServerVariables("REQUEST_METHOD")
 'Dim ParaValue
  if posttype="POST" then
 	ParaValue=Request.Form(ParaName) 
  else
    ParaValue=Request.QueryString(ParaName)
  end if
    server_v1=Cstr(Request.ServerVariables("HTTP_REFERER"))
	server_v2=Cstr(Request.ServerVariables("SERVER_NAME"))
 If ParaType=1 then 
   if trim(ParaValue)<>"" then
	  If not isNumeric(ParaValue) then 
	   Response.write "参数" & ParaName & "必须为数字型！" 
	   Response.end 
	  End if 
   end if
 Else 
  	ParaValue=replace(ParaValue,"'","''") 
 End if 
 SafeRequest=ParaValue 
End function
Function getHTTPPage(url) 
    Dim Http
    Set Http=Server.CreateObject("MSXML2.XMLHTTP.3.0") 
    With Http
        .open "GET",url,False
        .Send
    End With 
    On Error Resume Next 
    If Http.Status<>200 then 
        Set Http=Nothing 
        
        Exit function
    End if
	getHTTPPage=bytesToBSTR(Http.responseBody,"utf-8")
	if err.number<>0 then err.Clear 
End function

Function BytesToBstr(body,Cset) 
  dim objstream
  set objstream = Server.CreateObject("adodb.stream")
  objstream.Type = 1
  objstream.Mode =3
  objstream.Open
  objstream.Write body
  objstream.Position = 0
  objstream.Type = 2
  objstream.Charset = Cset
  BytesToBstr = objstream.ReadText 
  objstream.Close
  set objstream = nothing
End Function

Function GBtoUTF8(szInput)
    Dim wch, uch, szRet
    Dim x
    Dim nAsc, nAsc2, nAsc3
    '如果输入参数为空，则退出函数
    If szInput = "" Then
    GBtoUTF8 = szInput
    Exit Function
    End If
    '开始转换
    For x = 1 To Len(szInput)
    wch = Mid(szInput, x, 1)
    nAsc = AscW(wch)
    If nAsc < 0 Then nAsc = nAsc + 65536
    If (nAsc And &HFF80) = 0 Then
    szRet = szRet & wch
    Else
    If (nAsc And &HF000) = 0 Then
    uch = "%" & Hex(((nAsc \ 2 ^ 6)) Or &HC0) & Hex(nAsc And &H3F Or &H80)
    szRet = szRet & uch
    Else
    uch = "%" & Hex((nAsc \ 2 ^ 12) Or &HE0) & "%" & _
    Hex((nAsc \ 2 ^ 6) And &H3F Or &H80) & "%" & _
    Hex(nAsc And &H3F Or &H80)
    szRet = szRet & uch
    End If
    End If
    Next
    GBtoUTF8 = szRet
End Function
'********************************************************

%>
