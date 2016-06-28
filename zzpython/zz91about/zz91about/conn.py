from MySQLdb.cursors import DictCursor
from DBUtils.PooledDB import PooledDB
#conn = MySQLdb.connect(host='127.0.0.1', user='astoweb', passwd='q3W6rzTMtKfxJMXB',db='astoweb',charset='utf8')   
__pool = PooledDB(creator=MySQLdb, mincached=1 , maxcached=50 ,
                              host='7006.zz91server.com' , port=3306 , user='astoweb' , passwd='q3W6rzTMtKfxJMXB',
                              db='astoweb',use_unicode=False,charset='utf8')
conn=__pool.connection()
cursor = conn.cursor()
def closeconn():
	cursor.close()