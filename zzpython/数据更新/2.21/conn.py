import pymssql
import MySQLdb
conn=pymssql.connect(host=r'192.168.2.2',trusted=False,user='rcu_crm',password='fdf@$@#dfdf9780@#1.kdsfd',database='rcu_crm',charset=None)
cursor=conn.cursor()
connmy = MySQLdb.connect(host='192.168.10.3', user='root', passwd='zj88friend',db='freeiris2',charset='utf8')     
cursormy = connmy.cursor()
connserver = MySQLdb.connect(host='rdsuo5342fhte95enp5ipublic.mysql.rds.aliyuncs.com', port=3398,user='kang', passwd='zjpuwei003',db='ast',charset='utf8')
cursorserver=connserver.cursor()
conn_mysql_reborn = MySQLdb.connect(host='rdsuo5342fhte95enp5ipublic.mysql.rds.aliyuncs.com', port=3398,user='reborn', passwd='vfBNMAp9VpNXAWwV',db='reborn',charset='utf8')
cursor_mysql_reborn=conn_mysql_reborn.cursor()
conn_mysql_sms = MySQLdb.connect(host='rdsuo5342fhte95enp5ipublic.mysql.rds.aliyuncs.com', port=3398,user='reborn', passwd='vfBNMAp9VpNXAWwV',db='commons_sms',charset='utf8')
cursor_mysql_sms=conn_mysql_sms.cursor()
