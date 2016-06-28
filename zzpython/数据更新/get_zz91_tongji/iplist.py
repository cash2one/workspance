#-*- coding:utf-8 -*-
#----开始时间2014-05-01
#----关联IP和account
from public import *
from zz91conn import database_other
from zz91settings import logpath
import os

conn=database_other()
cursor=conn.cursor()

def getiplist(datalist):
    for line in datalist:
        linedir=eval(line)
        ip=linedir['ip']
        datadir=eval(linedir['data'])
        account=datadir['account']
        if account:
            sql1='select id from data_account where numb=%s'
            cursor.execute(sql1,[account])
            result1=cursor.fetchone()
            if result1:
                account_id=result1[0]
            else:
                sql2='insert into data_account(numb) values(%s)'
                cursor.execute(sql2,[account])
                conn.commit()
                sql3='select id from data_account order by id desc limit 0,1'
                cursor.execute(sql3)
                result2=cursor.fetchone()
                if result2:
                    account_id=result2[0]
            sql4='select id from data_ip where numb=%s'
            cursor.execute(sql4,[ip])
            result3=cursor.fetchone()
            if not result3:
                sql5='insert into data_ip(numb,account_id) values(%s,%s)'
                cursor.execute(sql5,[ip,account_id])
                conn.commit()

def getiplistall():
    from zz91tools import date_to_str,getpastday
    daylist=getpastday(10)
    for dday in daylist:
        aa=os.system("wget -m http://192.168.110.112:801/zz91Analysis/run."+str(dday)+" -nH --cut-dirs=1 -P /usr/data/log4z/zz91Analysis")
        dday_str=date_to_str(dday)
        datalist=open(logpath+'run.'+dday_str)
        getiplist(datalist)
    return '关联IP和帐号'

if __name__=="__main__":
    getiplistall()
