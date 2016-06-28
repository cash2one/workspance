#-*- coding:utf-8 -*-
#----环保文章审核
from public import *
import re,time,os,urllib,urllib2,os,random,jieba
from zz91conn import database_huanbao
from zz91tools import getpastoneday,int_to_datetime,getToday
from apscheduler.scheduler import Scheduler

conn=database_huanbao()
cursor = conn.cursor()
gmt_created=getToday()
str_time=time.strftime('%m-%d',time.localtime(time.time()))

#schedudler = Scheduler(daemonic = False)
#@schedudler.cron_schedule(second='1', minute='5', day_of_week='0-6', hour='0,4,8,10,12,14,16,18,20') 
def huanbaocheck():
    sql='select id,cid,title,details from comp_news where gmt_created>=%s order by gmt_created'
    cursor.execute(sql,[gmt_created])
    resultlist=cursor.fetchall()
    listall=[]
    listall2=[]
    for result in resultlist:
        id=result[0]
        cid=result[1]
        arg=0
        if cid in listall:
            f=open(str_time+'-huanbao.txt','ab')
            print >>f,str(id)+'审核'
            if cid in listall2:
                sql4='update comp_news set check_status=2 where id=%s'
                cursor.execute(sql4,[id])
                conn.commit()
            else:
                listall2.append(cid)
                arg=1
        else:
            arg=1
        if arg==1:
            listall.append(cid)
            title=result[2]
            details=result[3]
            arg=''
            
            rpl1=re.findall('[0-9\ ]+',details)
            for r1 in rpl1:
                if len(r1)>9:
                    details=details.replace(r1,r1[:-3]+'***')
                    arg=1
            
            if '@' in details:
                details=re.sub('@.*?com','*@***com',details)
            
            if '<A' in details:
                details=details.replace('<A','<a')
                details=details.replace('</A>','</a>')
            if '<a' in details:
                details=re.sub('<a.*?>','',details)
                details=re.sub('</a>','',details)
            if 'http:' in details:
                details=re.sub('http:..........','http:***',details)
            if '.com' in details:
                details=re.sub('..........com','***.com',details)
            if '.cn' in details:
                details=re.sub('..........cn','***.cn',details)
            
            sql3='select check_status from comp_news where id=%s'
            cursor.execute(sql3,[id])
            result3=cursor.fetchone()
            if result3:
                check_status=result3[0]
            if check_status==0:
                sql2='update comp_news set check_status=1,pause_status=1,check_person=%s,details=%s where id=%s'
                cursor.execute(sql2,['auto',details,id])
                conn.commit()
                f=open(str_time+'-huanbao.txt','ab')
                print >>f,str(id)+'审核'
#schedudler.start()
huanbaocheck()
conn.close()