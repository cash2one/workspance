#-*- coding:utf-8 -*-
#----初始化资讯抓取日志
from public import *
from zz91conn import database_other
import datetime,time

conn=database_other()
cursor=conn.cursor()

def getinitall():
    sql='select id,title,typeid,get_time from log_news'
    cursor.execute(sql)
    resultlist=cursor.fetchall()
    if resultlist:
        for result in resultlist:
            details_id=result[0]
            title=result[1]
            typeid=result[2]
            get_time=result[3]
            gmt_date=datetime.date.today()
            time2=time.strftime('%Y-%m-%d ',time.localtime(time.time()))
            argument=[title,typeid,details_id,0,gmt_date,time2+get_time]
            sql3='select id from log_auto where details_id=%s and gmt_date=%s'
            cursor.execute(sql3,[details_id,gmt_date])
            result1=cursor.fetchone()
            if not result1:
                sql2='insert into log_auto(title,typeid,details_id,is_ok,gmt_date,gmt_created) values(%s,%s,%s,%s,%s,%s)'
                cursor.execute(sql2,argument)
                conn.commit()
            

if __name__=="__main__":
    getinitall()