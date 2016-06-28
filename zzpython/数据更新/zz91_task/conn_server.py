import sys
sys.path.append('/mnt/pythoncode/zz91public/')
import MySQLdb
import memcache
from zz91conn import database_comp,database_news
conn = database_comp()
cursor=conn.cursor()
conn_news = database_news()
cursornews=conn_news.cursor()
mc = memcache.Client(['cache.zz91server.com:11211'],debug=0)