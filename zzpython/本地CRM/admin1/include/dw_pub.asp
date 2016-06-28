<%
function dw_pub(d)
sql="insert into dw_public(dw_id,dw_db) values("&num&",'"&d&"')"
conn.Execute(sql)
end function
%>
