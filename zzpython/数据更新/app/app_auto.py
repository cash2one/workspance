#-*- coding:utf-8 -*-
#----定时任务控制器
from public import *
from apscheduler.scheduler import Scheduler
from zz91db_ast import companydb
from app_message import getapp_message
from app_order import getapp_order
from zz91tools import subString
import os
dbc=companydb()

#----系统消息
schedudler = Scheduler(daemonic = False)  
@schedudler.cron_schedule(second='1', minute='10', day_of_week='0-5', hour='9,13') 
def autofunc1():
    import datetime
    datetoday=datetime.date.today()
    nowtime=datetime.datetime.now()
    getapp_message(dbc,datetoday,nowtime)
    
#----商行定制
@schedudler.cron_schedule(second='1', minute='10', day_of_week='0-5', hour='8,13,16') 
def autofunc2():
    import datetime
    datetoday=datetime.date.today()
    nowtime=datetime.datetime.now()
    getapp_order(dbc,datetoday,nowtime)
schedudler.start()
#autofunc1()
#autofunc2()