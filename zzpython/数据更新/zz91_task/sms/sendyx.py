import MySQLdb
import os,json,sys
import urllib,httplib
from public import *
import time
#from apscheduler.scheduler import Scheduler
#schedudler = Scheduler(daemonic = False)  
#sched = Scheduler()
from zz91db_sms import smsdb
dbsms=smsdb()
t=0
def sendsmsall():
    t=1
    try:
        sql="select receiver,content,id,template_code,gateway_code from sms_log where TIMESTAMPDIFF(MINUTE,gmt_created,now())<10 and send_status=0 and gateway_code in ('yunpian_yx','yuexinYX')"
        resultlist=dbsms.fetchalldb(sql)
        if resultlist:
            sqlc="update sms_log set send_status=1  where TIMESTAMPDIFF(MINUTE,gmt_created,now())<10 and send_status=0 and gateway_code in ('yunpian_yx','yuexinYX')"
            dbsms.updatetodb(sqlc)
            for list in resultlist:
                mobile=list[0]
                content=list[1]
                id=list[2]
                template_code=list[3]
                gateway_code=list[4]
                sqlp="update sms_log set send_status=1 where id=%s"
                dbsms.updatetodb(sqlp,[id])
                content=content.replace("优惠","抵金")
                content=content.replace("【","[")
                content=content.replace("】","]")
                content=content.replace("[ZZ91客服部]","")
                content=content.replace("【ZZ91客服部】","")
                content=content.replace("[ZZ91再生网]","")
                returnsms=postsms_yunpian(mobile[0:11],content)
                if returnsms==True:
                    sqlp="update sms_log set send_status=2 where id=%s"
                    dbsms.updatetodb(sqlp,[id])
                else:
                    sqlp="update sms_log set send_status=3 where id=%s"
                    dbsms.updatetodb(sqlp,[id])
                sleep(1)
    except IOError as e:
        print e
    t=0
#sendsmsall()
i=1
while i<60:
    if t==0:
        sendsmsall()
    i+=5
    time.sleep(5)
dbsms.closedb()
#sched.daemonic = False
#sched.add_cron_job(sendsmsall, second='*/10',minute='*',hour='5-24,0-2') 
#sched.start()
