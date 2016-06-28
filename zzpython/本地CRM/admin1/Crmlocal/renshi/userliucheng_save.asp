<!-- #include file="../../include/ad!$#5V.asp" -->
<%
Response.Buffer =false
response.cachecontrol="private"
Response.Expires =0
if request.QueryString("id")<>"" and request.QueryString("code")<>"" then
	zhiduan=request.QueryString("field")
	sql="update renshi_user set "&zhiduan&"='"&request.QueryString("code")&"',personid="&request.QueryString("personid")&" where id="&request.QueryString("id")&""
	conn.execute(sql)
	sql="insert into renshi_history (uid,code,personid) values("&request.QueryString("id")&",'"&request.QueryString("code")&"',"&request.QueryString("personid")&")"
	conn.execute(sql)
	response.Write("<script>alert('ÐÞ¸Ä³É¹¦')</script>")
	response.End()
end if
conn.close
set conn=nothing
%>
