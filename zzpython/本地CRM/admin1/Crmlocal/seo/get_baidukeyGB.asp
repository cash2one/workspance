<!-- #include file="../../include/ad!$#5V.asp" -->
<!-- #include file="inc.asp" -->
<%
ktype=request.QueryString("action")
'��ѯ�����κ��� 
if ktype="check_pai" then
	 
	 id=request.QueryString("id")
	 check_url=Request.QueryString("check_url") '��Ҫ��ѯ��url
	 Keyworlds=Request.QueryString("k")       '��Ҫ��ѯ�Ĺؼ���
	 url="http://192.168.2.21/zqweb/?check_url="&check_url&"&k="&Keyworlds&""
	 response.Write(url)
	 ranking=getHTTPPage(url)
	 'Page_c = 100   '��ѯ��������Ĭ��30�����ڵģ�����ͨ�����ݲ�������
'	'Response.write Keyworlds&check_url
'	 C_http = Replace(check_url,"http://","")
'	 C_baidu_url="http://www.baidu.com/s?rn="&Page_c&"&q1="&server.URLEncode(Keyworlds)&"&pn=0"
'	 'Response.write C_baidu_url&"<hr>"
'	 C_baidu_all=getHTTPPage(C_baidu_url)
'	 'Response.write C_baidu_all
'	' Response.end()
'	 sessionview = "check_pai_"&check_url&Keyworlds
'	 '���˵��ƹ�ؼ��֣�ע�����������޸����Ĳ���
'	 If Instr(C_baidu_all,"�ƹ�</a>") > 0 Then
'	  C_baidu_all_v = Split(C_baidu_all,">�ٶ��ƹ�</a>")
'	  C_baidu_all = C_baidu_all_v(ubound(C_baidu_all_v))
'	 End if
'	 
'	'���ÿ���ȷ��λ��,ע�����������޸����Ĳ���
'	If Instr(C_baidu_all,C_http) <> 0 then
'		C_baidu_v = Split(C_baidu_all,"<h3 class=""t"">")
'		b = ubound(C_baidu_v)
'		'Response.write b
'		For y = 0 to b - 1
'			If Instr(C_baidu_v(y),C_http) <> 0 then
'				Response.Write y&chr(10)
'				session(sessionview)= y&chr(10)
'				Exit For
'			End if
'		Next
'		ranking=y
'	Else
'		C_baidu_all = "100"
'		ranking=100
'		response.Write(ranking)
'	End if
	response.Write(ranking)
	sid=getsid(id)
	sql="update seo_keywordslist set baidu_ranking="&cstr(ranking)&" where id="&id&""
	conn.execute(sql)
	'-----������ʷ��¼
	sqlk="select * from seo_keywords_history where kid="&id&" and kdate='"&cstr(date)&"' and baidu_ranking="&cstr(ranking)&" and ktype='"&ktype&"'"
	set rsk=server.CreateObject("ADODB.recordset")
	rsk.open sqlk,conn,1,3
	if rsk.eof or rsk.bof then
		rsk.addnew
		rsk("sid")=sid
		rsk("kid")=id
		rsk("ktype")=ktype
		rsk("baidu_ranking")=ranking
		rsk("kdate")=date
		rsk.update
	end if
	rsk.close
	set rsk=nothing

	sql="select id from seo_keywordslist where dbtype=0 and sid="&sid
	set rs=conn.execute(sql)
	if rs.eof or rs.bof then
		sqlp="update seo_list set dbflag=1 where id="&sid&""
		conn.execute(sqlp)
	else
		sqlp="update seo_list set dbflag=0 where id="&sid&""
		conn.execute(sqlp)
	end if
	rs.close
	set rs=nothing
	
End if
'�ٶ�������
if request.QueryString("action")="baidu_sl" then
	id=request.QueryString("id")
	check_url=Request.QueryString("check_url")
	C_http = Replace(check_url,"http://","")
	C_baidu_url="http://www.baidu.com/baidu?word=site:"&C_http&"&ie=utf-8"
	C_baidu_all=getHTTPPage(C_baidu_url)
	if instr(C_baidu_all,"�ҵ���ؽ����Լ")>0 then
		C_baidu_v=split(C_baidu_all,"�ҵ���ؽ����Լ")
		b = ubound(C_baidu_v)
		if b>0 then
			C_baidu_vv=C_baidu_v(1)
		end if
		C_baidu_vvv=split(C_baidu_vv,"��")
		sl=trim(C_baidu_vvv(0))
		sl=replace(sl,",","")
		response.Write(sl)
	else
		response.Write(0)
		sl=0
	end if
	sql="update seo_list set baidu_sl="&cstr(sl)&" where id="&id&""
	conn.execute(sql)
	'-----������ʷ��¼
	sqlk="select * from seo_keywords_history where sid="&id&" and kdate='"&cstr(date)&"' and baidu_sl="&cstr(sl)&" and ktype='"&ktype&"'"
	set rsk=server.CreateObject("ADODB.recordset")
	rsk.open sqlk,conn,1,3
	if rsk.eof or rsk.bof then
		rsk.addnew
		rsk("sid")=id
		rsk("kid")=0
		rsk("ktype")=ktype
		rsk("baidu_sl")=sl
		rsk("kdate")=date
		rsk.update
	end if
	rsk.close
	set rsk=nothing
end if
'�ٶȿ���
if request.QueryString("action")="baidu_kz" then
	 id=request.QueryString("id")
	 check_url=Request.QueryString("check_url") '��Ҫ��ѯ��url
	 C_http = Replace(check_url,"http://","")
	 C_baidu_url="http://www.baidu.com/s?ie=utf-8&wd="&check_url&""
	 C_baidu_all=getHTTPPage(C_baidu_url)
	 '���˵��ƹ�ؼ��֣�ע�����������޸����Ĳ���
	 If Instr(C_baidu_all,"�ƹ�</a>") > 0 Then
	  C_baidu_all_v = Split(C_baidu_all,">�ٶ��ƹ�</a>")
	  C_baidu_all = C_baidu_all_v(ubound(C_baidu_all_v))
	 End if
	'���ÿ���ȷ��λ��,ע�����������޸����Ĳ���
	If Instr(C_baidu_all,C_http) <> 0 then
		C_baidu_v = Split(C_baidu_all,"<b>"&C_http&"</b>/")
		b = ubound(C_baidu_v)
		if b>0 then
			cbaiduvv=split(C_baidu_v(1),"</span>")
			response.Write(trim(cbaiduvv(0)))
			kz=trim(cbaiduvv(0))
		end if
	Else
		response.Write("0")
		kz=0
	End if
	if kz<>"" and kz<>"" then
		kz=replace(kz," ","")
		kz=replace(kz,"&nbsp;","")
		
		sql="update seo_list set baidu_kuaizhao='"&cstr(trim(kz))&"' where id="&id&""
		'response.Write(sql)
		conn.execute(sql)
		'-----������ʷ��¼
		sqlk="select * from seo_keywords_history where sid="&id&" and kdate='"&cstr(date)&"' and baidu_kuaizhao='"&cstr(kz)&"' and ktype='"&ktype&"'"
		set rsk=server.CreateObject("ADODB.recordset")
		rsk.open sqlk,conn,1,3
		if rsk.eof or rsk.bof then
			rsk.addnew
			rsk("sid")=id
			rsk("kid")=0
			rsk("ktype")=ktype
			rsk("baidu_kuaizhao")=kz
			rsk("kdate")=date
			rsk.update
		end if
		rsk.close
		set rsk=nothing
	end if
End if
'�ٶȷ���
if request.QueryString("action")="baidu_fl" then
	id=request.QueryString("id")
	check_url=Request.QueryString("check_url")
	C_http = Replace(check_url,"http://","")
	C_baidu_url="http://www.baidu.com/baidu?word=domain:"&C_http&"&ie=utf-8"
	C_baidu_all=getHTTPPage(C_baidu_url)
	if instr(C_baidu_all,"�ٶ�Ϊ���ҵ���ؽ��Լ")>0 then
		C_baidu_v=split(C_baidu_all,"�ٶ�Ϊ���ҵ���ؽ��Լ")
		b = ubound(C_baidu_v)
		if b>0 then
			C_baidu_vv=C_baidu_v(1)
		end if
		C_baidu_vvv=split(C_baidu_vv,"��")
		sl=trim(C_baidu_vvv(0))
		sl=replace(sl,",","")
		response.Write(sl)
	else
		response.Write(0)
		sl=0
	end if
	sql="update seo_list set baidu_fanlian="&cstr(sl)&" where id="&id&""
	conn.execute(sql)
	'-----������ʷ��¼
		sqlk="select * from seo_keywords_history where sid="&id&" and kdate='"&cstr(date)&"' and baidu_fanlian="&cstr(sl)&" and ktype='"&ktype&"'"
		set rsk=server.CreateObject("ADODB.recordset")
		rsk.open sqlk,conn,1,3
		if rsk.eof or rsk.bof then
			rsk.addnew
			rsk("sid")=id
			rsk("kid")=0
			rsk("ktype")=ktype
			rsk("baidu_fanlian")=sl
			rsk("kdate")=date
			rsk.update
		end if
		rsk.close
		set rsk=nothing
end if
conn.close
set conn=nothing
%>