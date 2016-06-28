import os,sys,time
from time import ctime, sleep
import datetime
from datetime import timedelta, date
execfile("/mnt/python/zz91_task/conn_server.py")
reload(sys)
sys.setdefaultencoding('UTF-8')
#//2016年4-1号再生钱包调价更新
now = int(time.time())
if now>1459440000 and now < 1459526400:
    sqlc="update pay_wallettype set fee=-10 where id=1"
    cursor.execute(sqlc)
    conn.commit()
