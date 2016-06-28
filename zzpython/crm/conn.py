#conn_server = MySQLdb.connect(host='122.225.11.215', port=7004, user='ast', passwd='astozz91jiubao',db='ast',charset='utf8')   
#cursor_server = conn_server.cursor()
import pymssql
conn=pymssql.connect(host=r'192.168.2.2',trusted=False,user='rcu_crm',password='fdf@$@#dfdf9780@#1.kdsfd',database='rcu_crm',charset=None)
cursor=conn.cursor()
def closeconn():
	cursor.close()
def getlocalIP():
	return "192.168.2.2"