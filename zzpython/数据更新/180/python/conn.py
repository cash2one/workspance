conn_rcu=pymssql.connect(host=r'192.168.110.112',trusted=False,user='astotest',password='zj88friend',database='rcu',charset=None)
conn_tags=pymssql.connect(host=r'192.168.110.112',trusted=False,user='astotest',password='zj88friend',database='rcu_tags',charset=None)
conn_news=pymssql.connect(host=r'192.168.110.112',trusted=False,user='astotest',password='zj88friend',database='rcu_news',charset=None)
conn_others=pymssql.connect(host=r'192.168.110.112',trusted=False,user='astotest',password='zj88friend',database='rcu_others',charset=None)
conn_aqsiq=pymssql.connect(host=r'192.168.110.112',trusted=False,user='astotest',password='zj88friend',database='aqsiq',charset=None)

conn_mysql = MySQLdb.connect(host='192.168.110.118', user='kang', passwd='astozz91jiubao',db='ast',charset='utf8')

conn = MySQLdb.connect(host='192.168.110.118', user='kang', passwd='astozz91jiubao',db='ast',charset='utf8')
cursor=conn.cursor()

conn_mysql_sms = MySQLdb.connect(host='192.168.110.130', user='reborn', passwd='vfBNMAp9VpNXAWwV',db='commons_sms',charset='utf8')
conn_mysql_reborn = MySQLdb.connect(host='192.168.110.130', user='reborn', passwd='vfBNMAp9VpNXAWwV',db='reborn',charset='utf8')

conn_ads = MySQLdb.connect(host='192.168.110.118', user='zzads', passwd='aJuUVbChYJ57t2SX',db='zzads',charset='utf8')
conn_mysql_tags = MySQLdb.connect(host='192.168.110.118', user='zztags', passwd='FJfAFLFcb95xTZ9m',db='zztags',charset='utf8')

conn_mysql_aqsiq = MySQLdb.connect(host='192.168.110.130', user='aqsiq', passwd='6JGnmy8mEJv7yBhd',db='aqsiq',charset='utf8')

