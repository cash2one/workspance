<%
set conn=server.CreateObject("ADODB.connection")
strconn="Provider=SQLOLEDB.1;driver={SQL Server};server=127.0.0.1;uid=rcu_crm;pwd=fdf@$@#dfdf9780@#1.kdsfd;database=rcu_crm"
conn.open strconn
function endConnection()
conn.close
set conn=nothing
end function
luyinurl="/admin1/crmlocal/recordService/play.asp?mdname="
luyindownloadurl="http://192.168.2.27/freeiris2/cpanel/record/1/"
%>