import MySQLdb
import os,json,sys
import urllib,httplib
from public import *
import time
#from apscheduler.scheduler import Scheduler
#schedudler = Scheduler(daemonic = False)  
#sched = Scheduler()
from zz91db_sms import smsdb
from zz91db_ast import companydb
dbsms=smsdb()
dbcom=companydb()
tt=0
def sendsms():
    tt=1
    sql="select id from update_log where reflag=1 and id=%s"
    resultc=dbcom.fetchonedb(sql,[18])
    if resultc:
        return
    try:
        sql="select receiver,content,id,template_code,gateway_code from sms_log where TIMESTAMPDIFF(MINUTE,gmt_created,now())<10 and send_status=0 and gateway_code in ('','yuexin') "
        resultlist=dbsms.fetchalldb(sql)
        if resultlist:
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
                if template_code=="sms_product":
                    sqlb="update update_log set reflag=2 where id=%s"
                    dbcom.updatetodb(sqlb,[18])
                    returnsms=True
                else:
                    returnsms=postsms(mobile[0:11],content,template_code)
                if returnsms==True:
                    sqlp="update sms_log set send_status=2 where id=%s"
                    dbsms.updatetodb(sqlp,[id])
                else:
                    sqlp="update sms_log set send_status=3 where id=%s"
                    dbsms.updatetodb(sqlp,[id])
                print mobile
                sqlb="update update_log set reflag=1 where id=%s"
                dbcom.updatetodb(sqlb,[18])
    except IOError as e:
        print e
    
    tt=0
    sqlc="update update_log set reflag=0 where id=%s"
    dbcom.updatetodb(sqlc,[18])


i=1
while i<60:
    if tt==0:
        sendsms()
    i+=2
    print i
    time.sleep(2)
dbcom.closedb()
dbsms.closedb()
#sched.daemonic = False
#sched.add_cron_job(sendsms, second='*/5',minute='*',hour='5-24,0-2')    
#sched.start()
