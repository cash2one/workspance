import MySQLdb
import os,json,sys
import urllib
import sendcloud
import smtplibnew
import datetime,time
from email.mime.text import MIMEText
from sphinxapi import *
from public import *
from xml.etree import ElementTree as ET
#from apscheduler.scheduler import Scheduler
#schedudler = Scheduler(daemonic = False)
from zz91conn import database_mail  
conn = database_mail()
cursor=conn.cursor()
from zz91db_company import functiondb
from zz91settings import SPHINXCONFIG
from zz91conn import database_comp
from zz91db_ads import adsdb
connc=database_comp()
cursorc=connc.cursor()
zzcomp=functiondb()
dbads=adsdb()

#from apscheduler.scheduler import Scheduler
#schedudler = Scheduler(daemonic = False)  
#sched = Scheduler()
def formattime(value,flag):
    if value:
        if (flag==1):
            return value.strftime( '%-Y-%-m-%-d')
        if (flag==2):
            return value.strftime( '%-m-%-d %-H:%-M')
        else:
            return value.strftime( '%-Y-%-m-%-d %-H:%-M:%-S')
    else:
        return ''
def send_mail(mailto,sub,content,nickname):   
    mail_host=u'smtp.exmail.qq.com'
    mail_user=u'system@asto-inc.com'
    mail_pass=u'zj88friend'
    mail_postfix=u'asto-inc.com'
    ######################    
    if nickname==None or nickname=="":
        nickname="zz91"
    me="<system@asto-inc.com>"
    msg = MIMEText(content,'html','utf-8')    
    msg['Subject'] = sub
    msg['From'] = me
    msg['To'] = mailto
    try:    
        s = smtplibnew.SMTP()    
        s.connect(mail_host)    
        s.login(mail_user,mail_pass)    
        s.sendmail(me, mailto, msg.as_string())    
        s.close()
        return True
    except Exception, e:    
        print str(e)    
        return False
def sendmail():
    sql="select id,email_title,email_content,receiver_email,nickname from mail_info where TIMESTAMPDIFF(MINUTE,gmt_created,now())<120000 and send_status=0 and is_delete=0 and template_id in ('zz91-expire-cs-notice','zz91-expire-cs','zz91-task-basedata','zz91-ad-expired') order by priority asc"
    cursor.execute(sql)
    resultlist=cursor.fetchall()
    if resultlist:
        for list in resultlist:
            id=list[0]
            email_title=list[1]
            email_content=list[2]
            receiver_email=list[3]
            nickname=list[4]
            gmt_post=datetime.datetime.now()
            
            sqlp="update mail_info set send_status=3,gmt_post=%s where id=%s"
            cursor.execute(sqlp,[gmt_post,id])
            receiver_email="sysmail@asto-inc.com"
            if receiver_email!="" and "zz91.com" not in receiver_email and receiver_email and "@" in receiver_email:
                returnsms=send_mail(receiver_email,email_title,email_content,nickname)
                if returnsms==True:
                    sqlp="update mail_info set send_status=1,gmt_post=%s where id=%s"
                    cursor.execute(sqlp,[gmt_post,id])
                    conn.commit()
                    err=0
                else:
                    err=1
            else:
                err=1
            if err==1:
                sqlp="update mail_info set send_status=2,gmt_post=%s where id=%s"
                cursor.execute(sqlp,[gmt_post,id])
                conn.commit()
                    
            print receiver_email
def adlist():
    gmt_post=gmt_created=gmt_modified=datetime.datetime.now()
    template_id="zz91-ad-expired"
    email_title="过期广告"+str(formattime(gmt_post,1))
    email=""
    email_content=""
    send_status=0
    priority=0
    send_email=""
    sqla="select id from mail_info where gmt_post>'"+str(formattime(gmt_post,1))+"' and template_id='"+template_id+"'"
    cursor.execute(sqla)
    resultlist=cursor.fetchone()
    if resultlist==None:
        sql="select ad_content,ad_target_url,id,ad_title,position_id,gmt_start,gmt_plan_end,search_exact from ad where DATEDIFF(gmt_plan_end,CURDATE())>=-2 and DATEDIFF(gmt_plan_end,CURDATE())<=0 and review_status='Y' and online_status='Y' order by gmt_start asc,sequence asc"
        list=dbads.fetchalldb(sql)
        if list:
            for list in list:
                position_id=list[4]
                gmt_start=formattime(list[5],1)
                gmt_plan_end=formattime(list[6],1)
                search_exact=list[7]
                sqlc="select name from ad_position where id=%s"
                listc=dbads.fetchonedb(sqlc,[position_id])
                if listc:
                    positionname=listc[0]
                email_content+="广告位："+positionname+" ,关键词："+search_exact+",地址：<a href='"+list[1]+"'>"+list[1]+"</a>,开始时间："+str(gmt_start)+",结束时间："+str(gmt_plan_end)+"<br />"
                list1={'title':list[3],'ad_target_url':list[1],'positionname':positionname,'gmt_start':gmt_start,'gmt_plan_end':gmt_plan_end}
                print list1
            value=[template_id,email_title,email_content,email,send_status,gmt_created,gmt_modified,priority,send_email,gmt_post]
            sqla="insert into mail_info(template_id,email_title,email_content,receiver_email,send_status,gmt_created,gmt_modified,priority,send_email,gmt_post) values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
            cursor.execute(sqla,value)
            conn.commit()
tt=0
def sendadmail():
    tt=1
    adlist()
    sendmail()
    tt=0
i=1
while i<60:
    if tt==0:
        sendadmail()
    i+=20
    time.sleep(20)
conn.close()
#sched.daemonic = False
#sched.add_cron_job(sendadmail, second='*/60',minute='*',hour='0-3')
#sched.start()