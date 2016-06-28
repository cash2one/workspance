#-*- coding:utf-8 -*-
from public import *
from apscheduler.scheduler import Scheduler

schedudler = Scheduler(daemonic = False)
@schedudler.cron_schedule(second='1', minute='1,51', day_of_week='0-6', hour='9') 
def autofunc1():
    from phone_area import getphoneareall
    getphoneareall()

schedudler.start()