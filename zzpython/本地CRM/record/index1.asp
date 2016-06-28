<%
Response.Buffer =false
response.cachecontrol="private"
Response.Expires =0
On Error Resume Next
%>
<!-- #include file="conn.asp" -->
<!-- #include file="inc.asp" -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
</head>

<body>

<%
a=1
for a=1 to 1
Curl="http://192.168.2.236/ucbox/cdr_crm.php?NowPage="&a&""
'Curl="http://192.168.2.236/ucbox/cdr_crm.php?select=&answeredtime=&calltype=all&starttime=&stoptime=&called=25498511&calling="
read=getHTTPPage(Curl,"gb2312")
list=getZhuaListvalue(read,"<!--beginly-->","<!--endly-->")
arrlist=split(list,"<!--tr-->")
'response.Write(list)
for i=1 to ubound(arrlist)
	tdlist=arrlist(i)
	response.Write(i)
	'response.End()
	arrtdlist=split(tdlist,"<!--td-->")
	'response.Write(arrtdlist(1))
	id=getZhuaListvalue(arrtdlist(1),"<td>","</td>")
	uniqueid=getZhuaListvalue(arrtdlist(2),"<td>","</td>")
	startime=getZhuaListvalue(arrtdlist(3),"<td>","</td>")
	caller=getZhuaListvalue(arrtdlist(4),"<td>","</td>")
	accountcode=getZhuaListvalue(arrtdlist(5),"<td>","</td>")
	answeredtime=getZhuaListvalue(arrtdlist(6),"<td>","</td>")
	called=getZhuaListvalue(arrtdlist(7),"<td>","</td>")
	monitorfile=getZhuaListvalue(arrtdlist(8),"<td>","</td>")
	ttype=getZhuaListvalue(arrtdlist(9),"<td>","</td>")
	'response.Write(id&"<br>")
	if cstr(answeredtime)<>"0" then
		sql="select * from record_list where id="&id
		set rs=server.CreateObject("adodb.recordset")
		rs.open sql,conn,1,3
		if  rs.eof or rs.bof then
			rs.addnew()
			rs("id")=id
			rs("uniqueid")=uniqueid
			rs("startime")=startime
			if cstr(ttype)="1" then
			rs("caller")=caller
			else
			rs("caller")=called
			end if
			
			rs("accountcode")=accountcode
			rs("answeredtime")=answeredtime
			if cstr(ttype)="1" then
			rs("called")=called
			else
			rs("called")=caller
			end if
			rs("monitorfile")=monitorfile
			rs("type")=ttype
			rs.update()
			response.Write("写入成功！")
		end if
		rs.close
		set rs=nothing
		
	end if
next
'response.Write(ubound(arrlist))
next
conn.close
set conn=nothing
frompagea=split(request.servervariables("script_name"),"/")
frompage=lcase(frompagea(UBound(frompagea)))
frompagequrstr=Request.ServerVariables("QUERY_STRING")
response.Write("<script>setTimeout(""window.location='"&frompage&"?"&frompagequrstr&"'"",1000*5)</script>")
%>
</body>
</html>
