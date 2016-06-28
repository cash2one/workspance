#-*- coding:utf-8 -*-
#----开始时间2014-05-01
#----注册转化率统计analyse.html
from public import *
import datetime,MySQLdb
from zz91conn import database_other,database_comp
from zz91settings import logpath
from zz91tools import date_to_str

conn=database_other()
cursor = conn.cursor()
conn_comp=database_comp()
cursor_comp = conn_comp.cursor()

def strtodatetime(datestr,format):       
    return datetime.datetime.strptime(datestr,format) 
def datetostr(date):     
    return str(date)[0:10] 

def gettongjilist(now_date):
    set1 = set()
    set2 = set()
    #gettoday=datetime.date.today()
#    format="%Y-%m-%d"
#    gettoday=strtodatetime(now_date,format)
    todate1=now_date+datetime.timedelta(1)
    todate=todate1.strftime("%Y-%m-%d")
    fromdate=now_date.strftime("%Y-%m-%d")
    nowdate=fromdate
    
    #nowdate1=datetostr()
    #nowdate=nowdate1
    #nowdate2=datetostr(strtodatetime("2014-4-27",format))
#    print fromdate
    for line in open(logpath+'run.'+nowdate):
        linedir=eval(line)
        ip=linedir['ip']
        datadir=eval(linedir['data'])
    #    account=datadir['account']
        countdate=datadir['date']
        url=datadir['url']
        set1.add(ip)
        sql3='select id from data_ip where numb=%s'
        cursor.execute(sql3,[ip])
        result1=cursor.fetchone()
        if result1:
            set2.add(ip)
            
    sql2='select count(0) from company where regtime>=%s and regtime<%s'
    cursor_comp.execute(sql2,[fromdate,todate])
    result2=cursor_comp.fetchone()
    reg_count=0
    #当日注册数
    if result2:
        reg_count=result2[0]
    
    #总ip数
    all_ipcount=len(set1)
    #已经注册总数
    alr_count=len(set2)-reg_count
    #未注册总数
    noreg_count=all_ipcount-len(set2)
    sql2='select id from dataanalysis where gmt_created=%s limit 0,1'
    cursor.execute(sql2,[nowdate])
    result3=cursor.fetchone()
    if not result3:
        sql='insert into dataanalysis(all_ipcount,alr_count,noreg_count,reg_count,gmt_created) values(%s,%s,%s,%s,%s)'
        cursor.execute(sql,[all_ipcount,alr_count,noreg_count,reg_count,nowdate])
        conn.commit() 

def gettongjiall():
    from zz91tools import getpastday
    sql8='select id from data_ip_search where gmt_created=%s'
    daylist=getpastday(1)
    for dday in daylist:
        cursor.execute(sql8,[dday])
        result8=cursor.fetchone()
        if not result8:
            gettongjilist(dday)

if __name__=="__main__":
    gettongjiall()

