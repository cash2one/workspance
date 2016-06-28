#-*- coding:utf-8 -*-
import os,json,sys
import urllib
import datetime
import requests
import time
reload(sys)
sys.setdefaultencoding('UTF-8')
sys.path.append('/mnt/pythoncode/zz91public/')
#from apscheduler.scheduler import Scheduler
#schedudler = Scheduler(daemonic = False)  
#sched = Scheduler()

taskname = sys.argv[1]

from question.questioncount import questioncountupdate
from qianbao.app import message
from ldb.ldb import ldbmessage
from huzhu.appmessages import huzhupost,huzhureply
from huzhu.update import updatebbsreply,updatebbs
from messages.update import insertmongolist,updateview
from price.outtable import outtable
#from getui.zz91app import persontuisong,systuisong
from ppc.auto_updateproducts import ppcautoupdate

def task_5s():
    i=1
    while i<60:
        #try:
        print i
        a= questioncountupdate(7)
        print a
        b=message(9)
        print b
        c=ldbmessage(10)
        print 2
        d=huzhupost(15)
        print 3
        e=huzhureply(11)
        print 4
        f=insertmongolist(13)
        print 5
        g=updateview(14)
        print datetime.datetime.now()
        #except Exception, e:    
        #print str(e)
        i+=5
        time.sleep(5)
def task_30s():
    try:
        outtable(None,8)
    except Exception, e:    
        print str(e)  
def task_60h():
    try:
        updatebbsreply()
        updatebbs()
        ppcautoupdate()
    except Exception, e:    
        print str(e)  
def task_5h():
    try:
        persontuisong()
        systuisong()
    except Exception, e:    
        print str(e)
if taskname=="5s":
    task_5s()
if taskname=="30s":
    task_30s()
if taskname=="60h":
    task_60h()
conn.close()
conn_news.close()
#sched.daemonic = False
#sched.add_cron_job(task_5s, second='*/5',minute='*',hour='0-24')
#sched.add_cron_job(task_30s, second='*/20',minute='*',hour='8-19')
#sched.add_cron_job(task_60h, second='*',minute='*/60',hour='5-21')
#sched.add_cron_job(task_5h, second='*',minute='*/5',hour='5-23')

#sched.start()