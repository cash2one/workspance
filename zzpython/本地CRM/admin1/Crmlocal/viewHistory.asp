<!-- #include file="../include/adfsfs!@#.asp" -->
<!-- #include file="../localjumptolog.asp" -->
<%
	'frompage=request.servervariables("script_name")
	'frompagequrstr=Request.ServerVariables("QUERY_STRING")
	
	frompagequrstr=request.ServerVariables("HTTP_REFERER")
	frompagequrstr=replace(frompagequrstr,"&","~amp~")
	frompagequrstr=replace(frompagequrstr,",",";")
	frompagequrstr=replace(frompagequrstr,"'","¡±")
	
	url=frompagequrstr
	
	
	personid=request.QueryString("personid")
	com_id=request.QueryString("com_id")
	
	
	sql="insert into crm_viewHistory(personid,com_id,url) values("&personid&","&com_id&",'"&url&"')"
	conn.execute(sql)
	conn.close
	set conn=nothing
%>