<!-- #include file="../include/adfsfs!@#.asp" -->
<!-- #include file="inc.asp" -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
</head>

<body>
<%
'---------------判断撞单情况
	com_tel=trim(request.QueryString("com_tel"))
	com_mobile=trim(request.QueryString("com_mobile"))
	com_name=request.QueryString("com_name")
	com_id=request.QueryString("com_id")
	
sqlp="select com_id,linkcomid from temp_havelinkcomp where com_id="&com_id&""	
set rsp=conn.execute(sqlp)
if rsp.eof or rsp.bof then
	com_tel=clearcomptel(com_tel)
	
	
	com_mobile=clearcompmobile(com_mobile)
	
	
	arrcomList=""
	zhuangdanFlag=0'撞单标志
	if com_mobile<>"" and not isnull(com_mobile)  then
		if len(com_mobile)>10 then
			sql="select com_id from temp_contactSearch where com_mobile like '%"&right(trim(com_mobile),10)&"' and com_id not in("&com_id&")"
			set rs=conn.execute(sql)
			if not rs.eof or not rs.bof then
				while not rs.eof
					arrcomList=arrcomList&rs("com_id")&","
				rs.movenext
				wend
				zhuangdanFlag=1
			end if
			rs.close
			set rs=nothing
		end if
	end if
	if com_tel<>"" and not isnull(com_tel) and len(com_tel)>6 and len(com_tel)<9 then
		sqlmm=""
		if arrcomList<>"" then
			sqlmm=" and com_id not in("&left(arrcomList,len(arrcomList)-1)&") "
		end if
		sql="select com_id from temp_contactSearch where com_tel like '%"&right(com_tel,7)&"' and len(com_tel)>7 and com_id not in("&com_id&")"&sqlmm
		set rs=conn.execute(sql)
		if not rs.eof or not rs.bof then
			while not rs.eof
				arrcomList=arrcomList&rs("com_id")&","
			rs.movenext
			wend
			zhuangdanFlag=1
		end if
		rs.close
		set rs=nothing
	end if
	
	
	'if zhuangdanFlag=1 then
'		arrcomList=""
'		if com_mobile<>"" and not isnull(com_mobile) then
'			sql="select com_id from temp_contactSearch where com_mobile like '%"&right(trim(com_mobile),10)&"' and len(com_mobile)>8 and com_id<>"&com_id&""
'			set rs=conn.execute(sql)
'			if not rs.eof or not rs.bof then
'				while not rs.eof
'					arrcomList=arrcomList&rs("com_id")&","
'				rs.movenext
'				wend
'			end if
'			rs.close
'			set rs=nothing
'		end if
'		if com_tel<>"" and not isnull(com_tel) and len(com_tel)>6 and len(com_tel)<9 then
'			if arrcomList<>"" then
'				sqlmm=" and com_id not in("&left(arrcomList,len(arrcomList)-1)&") "
'			end if
'			sql="select com_id from temp_contactSearch where com_tel like '%"&right(com_tel,7)&"' and len(com_tel)>7 and com_id<>"&com_id&" "&sqlmm
'			set rs=conn.execute(sql)
'			if not rs.eof or not rs.bof then
'				while not rs.eof
'					arrcomList=arrcomList&rs("com_id")&","
'				rs.movenext
'				wend
'			end if
'			rs.close
'			set rs=nothing
'		end if
'	end if
	if isnull(com_name) then com_name=""
	com_name=replace(com_name,"个体经营","")
	com_name=replace(com_name,"(","")
	com_name=replace(com_name,")","")
	com_name=replace(com_name,"个体","")
	if com_name<>"" then
		sqlmm=""
		if arrcomList<>"" then
			sqlmm=" and com_id not in("&left(arrcomList,len(arrcomList)-1)&") "
		end if
		sql="select top 10 com_id from temp_contactSearch where com_name like '%"&com_name&"%' and com_id not in("&com_id&") "&sqlmm
		set rs=conn.execute(sql)
		if not rs.eof or not rs.bof then
			while not rs.eof
				arrcomList=arrcomList&rs("com_id")&","
			rs.movenext
			wend
		end if
		rs.close
		set rs=nothing
	end if
	arrcomList1=replace(arrcomList,",","|")
	sqli="insert into temp_havelinkcomp(com_id,linkcomid) values("&com_id&",'"&arrcomList1&"')"
	conn.execute(sqli)
else
	arrcomList1=rsp(1)
	arrcomList=replace(arrcomList1,"|",",")
end if
rsp.close
set rsp=nothing
if arrcomList<>"" then
	sql="select count(0) from crm_assign where com_id in ("&left(arrcomList,len(arrcomList)-1)&")"
	set rs=conn.execute(sql)
	if rs(0)>0 then
		response.Write("<script>parent.document.getElementById('zdcom"&com_id&"').innerHTML='<br><div class=tishi><font color=#666666>可能有</font><font color=#ff0000>"&rs(0)&"</font><font color=#666666>个客户撞单</font></div>'</script>")
	else
		response.Write("<script>parent.document.getElementById('zdcom"&com_id&"').innerHTML=''</script>")
	end if
	rs.close
	set rs=nothing
	
else
	response.Write("<script>parent.document.getElementById('zdcom"&com_id&"').innerHTML=''</script>")
end if
conn.close
set conn=nothing
%>
</body>
</html>
