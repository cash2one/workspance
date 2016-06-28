<%
set connrecord=server.CreateObject("ADODB.connection")
strconnrecord="Provider=SQLOLEDB.1;driver={SQL Server};server=192.168.2.14\SQLEXPRESS;uid=sa;pwd=sa123456;database=foxrec"
strconnrecordmysql="DefaultDir=;Driver={myodbc driver};database=uc_cdr"
strconnrecordmysql = "dsn=myodbc"
set conn=server.CreateObject("ADODB.connection")
strconn="Provider=SQLOLEDB.1;driver={SQL Server};server=(local);uid=rcu_crm;pwd=fdf@$@#dfdf9780@#1.kdsfd;database=rcu_crm"
conn.open strconn
%>