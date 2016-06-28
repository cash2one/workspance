
<!-- #include file="../../include/adfsfs!@#.asp" -->
<!-- #include file="../../localjumptolog.asp" -->
<%
frompagequrstr=request.ServerVariables("HTTP_REFERER")
frompagequrstr=replace(frompagequrstr,"&","~amp~")
frompagequrstr=replace(frompagequrstr,",",";")
frompagequrstr=replace(frompagequrstr,"'","¡±")
url=frompagequrstr

personid=request.Form("personid")
copycontent=request.Form("copycontent")
copycontent=replace(copycontent,"'","''")
sql="insert into crm_copyContent(personid,fromurl,content) values("&personid&",'"&url&"','"&copycontent&"')"
conn.execute(sql)
conn.close
set conn=nothing
%>
