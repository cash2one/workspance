#-*- coding:utf-8 -*-
#开始时间2014-05-01
#----账号访问页面visiturl.html
from public import *
from zz91conn import database_other
from zz91settings import logpath

conn=database_other()
cursor=conn.cursor()

def getvisiturl(datalist,dday):
    from zz91tools import int_to_datetime
    for line in datalist:
        linedir=eval(line)
        ip=linedir['ip']
        sql1='select id from data_ip where numb=%s'
        cursor.execute(sql1,[ip])
        result1=cursor.fetchone()
        if result1:
            datadir=eval(linedir['data'])
            url=datadir['url']
            if len(url)>280:
                url=url[:280]
            account=datadir['account']
            urltime=int(str(linedir['time'])[:10])
            urltime=int_to_datetime(urltime)
            sql='insert into data_visiturl(ip,gmt_created,url,urltime,account) values(%s,%s,%s,%s,%s)'
            cursor.execute(sql,[ip,dday,url,urltime,account])
            conn.commit()
def getvisiturlall():
    from zz91tools import date_to_str,getpastday
    daylist=getpastday(1)
    for dday in daylist:
        dday_str=date_to_str(dday)
        sql='select id from data_visiturl where gmt_created=%s'
        cursor.execute(sql,[dday])
        result=cursor.fetchone()
        if not result:
            datalist=open(logpath+'run.'+dday_str)
            getvisiturl(datalist,dday)
    return '账号访问页面'

if __name__=="__main__":
    getvisiturlall()