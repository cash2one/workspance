# -*- coding: UTF-8 -*-
import time
import sys,re
import datetime
from datetime import timedelta, date
import sys
reload(sys)
sys.path.append('/mnt/pythoncode/zz91public/')
import MySQLdb
from zz91conn import database_comp,database_news
conn = database_comp()
cursor=conn.cursor()
sqlm="select id from price2 order by id desc limit 0,1"
cursor.execute(sqlm)
result=cursor.fetchone()
if result:
    maxid=result[0]
print maxid
sql="select * from price where id>"+str(maxid)+"  order by id asc limit 0,10000"
cursor.execute(sql) 
result=cursor.fetchall()
for list in result:
    i=0
    vv=[]
    ll=""
    while i<len(list):
        #print list[i]
        vv.append(list[i])
        i+=1
        ll+=",%s"
    ll=ll[1:]
    print list[0]
    sql2="insert into price2 values("+ll+")"
    cursor.execute(sql2,vv)
    conn.commit()
sql="RENAME TABLE  `ast`.`price` TO  `ast`.`price1` ;"
sql="RENAME TABLE  `ast`.`price2` TO  `ast`.`price` ;"
conn.close()