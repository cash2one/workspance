import pymssql
import MySQLdb
conn=pymssql.connect(host=r'192.168.2.2',trusted=False,user='rcu_crm',password='fdf@$@#dfdf9780@#1.kdsfd',database='rcu_crm',charset=None)
cursor=conn.cursor()
connserver = MySQLdb.connect(host='rdsuo5342fhte95enp5ipublic.mysql.rds.aliyuncs.com', port=3398,user='kang', passwd='zjpuwei003',db='ast',charset='utf8')
cursorserver=connserver.cursor(cursorclass = MySQLdb.cursors.DictCursor)
connmy = MySQLdb.connect(user='zz91crm', db='zz91crm', passwd='zJ88friend', host='192.168.2.4', charset="utf8")
cursormy=connmy.cursor(cursorclass = MySQLdb.cursors.DictCursor)