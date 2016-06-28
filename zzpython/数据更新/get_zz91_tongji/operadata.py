#-*- coding:utf-8 -*-
#---开始统计时间2014-07-01
#---生意管家操作统计operadata.html
from public import *
import datetime,MySQLdb,os
from zz91conn import database_other
from zz91tools import date_to_str
from zz91settings import logpath_myrc


conn=database_other()
cursor = conn.cursor()

def gettongjilist(nowdate):
    os.system("wget -m http://192.168.110.112:801/myrc/run."+str(nowdate)+" -nH --cut-dirs=1 -P /usr/data/log4z/myrc")
    datalist=open(logpath_myrc+'run.'+nowdate)
    sql='insert into data_myrc(operatype_id,account,pro_id,ip,gmt_date,gmt_created,gmt_modified) values(%s,%s,%s,%s,%s,%s,%s)'
    for line in datalist:
        if len("".join(line.split()))>0:
            linedir=eval(line)
            ip=linedir['ip']
            datadir=eval(linedir['data'])
            account=datadir['account']
            operatype_id=datadir['operatype_id']
            gmt_created=datadir['gmt_created']
            pro_id=None
            if datadir.has_key('pro_id'):
                pro_id=datadir['pro_id']
            if pro_id and pro_id.isdigit()==True:
                pro_id=int(pro_id)
            else:
                pro_id=None
            gmt_date=gmt_created[:10]
            if account and operatype_id:
                argument=[operatype_id,account,pro_id,ip,gmt_date,gmt_created,nowdate]
                cursor.execute(sql,argument)
                conn.commit()

def getmyrcall():
    from zz91tools import getpastday
    daylist=getpastday(10)
    sql='select id from data_myrc where gmt_modified=%s'
    for dday in daylist:
        nowdate=date_to_str(dday)
        cursor.execute(sql,[nowdate])
        result=cursor.fetchone()
        if not result:
            gettongjilist(nowdate)
    return '生意管家操作数据'

if __name__=="__main__":
    getmyrcall()
