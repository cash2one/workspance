#-*- coding:utf-8 -*-
#----定时任务控制器
from public import *
#from apscheduler.scheduler import Scheduler
import datetime
import sys
taskname = sys.argv[1]


#----生成走势图8张,环保资讯
#schedudler = Scheduler(daemonic = False)  
#@schedudler.cron_schedule(second='1', minute='02,36,42,56', day_of_week='0-6', hour='9-16') 
def autofunc1():
    from charts_info import chartall
    from zz91db_log import getlog
    getlogs=getlog()
    detailslist=chartall()
    typeid=1
    title='金属报价'
    gmt_date=datetime.date.today()
    for details in detailslist:
        getlogs.insertinto(details,gmt_date)

#----佛山金属报价,废电瓶,废镍
#@schedudler.cron_schedule(second='1', minute='1,30', day_of_week='0-6', hour='11,14,15,16') 
def autofunc2():
    from jinshu_price import getjshupall
    from zz91db_log import getlog
    getlogs=getlog()
    detailslist=getjshupall()
    typeid='1'
    title='金属报价'
    gmt_date=datetime.date.today()
    for details in detailslist:
        getlogs.insertinto(details,gmt_date)

#----塑料资讯:原油,橡胶
#@schedudler.cron_schedule(second='1', minute='5', day_of_week='0-6', hour='10,11') 
def autofunc3():
    from suliaozixun import getsuliaozixun2
    from zz91db_log import getlog
    getlogs=getlog()
    detailslist=getsuliaozixun2()
    typeid='2'
    title='塑料资讯'
    gmt_date=datetime.date.today()
    for details in detailslist:
        getlogs.insertinto(details,gmt_date)

#----各地废金属报价16:10
#@schedudler.cron_schedule(second='1', minute='1', day_of_week='0-6', hour='10,14,15,16,17') 
def autofunc4():
    from area_jinshu_price import area410
    from zz91db_log import getlog
    getlogs=getlog()
    detailslist=area410()
    typeid='1'
    title='金属报价'
    gmt_date=datetime.date.today()
    for details in detailslist:
        getlogs.insertinto(details,gmt_date)

#----废金属,废塑料网上报价
#@schedudler.cron_schedule(second='1', minute='30', day_of_week='0-6', hour='8') 
def autofunc5():
    from net_price import getnetpriceall
    from initall import getinitall
    from zz91db_log import getlog
    getinitall()
    getlogs=getlog()
    detailslist=getnetpriceall()
    typeid='1'
    title='金属报价'
    gmt_date=datetime.date.today()
    for details in detailslist:
        getlogs.insertinto(details,gmt_date)

#----废塑料各地报价
#@schedudler.cron_schedule(second='1', minute='30', day_of_week='0-6', hour='16,17') 
def autofunc6():
    from area_price import suliaoareaprice
    from zz91db_log import getlog
    getlogs=getlog()
    details=suliaoareaprice()
    typeid='1'
    title='塑料报价'
    gmt_date=datetime.date.today()
    getlogs.insertinto(details,gmt_date)

#----废铜资讯
#@schedudler.cron_schedule(second='1', minute='10,30', day_of_week='0-6', hour='10,11') 
def autofunc7():
    from jinshu_zixun import getjinshu_zixun
    from zz91db_log import getlog
    getlogs=getlog()
    detailslist=getjinshu_zixun()
    typeid='2'
    title='金属资讯'
    gmt_date=datetime.date.today()
    for details in detailslist:
        getlogs.insertinto(details,gmt_date)
#----国际贵金属市场行情,汇率
#@schedudler.cron_schedule(second='1', minute='5', day_of_week='0-6', hour='9-17') 
def autofunc8():
    from gethuilv import getallhuihuan3
    getallhuihuan3()
#----国际贵金属市场行情,汇率
#@schedudler.cron_schedule(second='1', minute='20', day_of_week='0-6', hour='9,10') 
def autofunc11():
    from guojiguijinshu945 import getguojiguijinshu
    from zz91db_log import getlog
    getlogs=getlog()
    details=getguojiguijinshu()
    if getlogs:
        typeid='2'
        title='金属报价'
        gmt_date=datetime.date.today()
        is_ok=1
        getlogs.insertinto(details,gmt_date)
#----金属,塑料晚报
#@schedudler.cron_schedule(second='1', minute='2,12', day_of_week='0-6', hour='17') 
def autofunc9():
    from getjinshu_wanbao import getwanbao
    from suliao_wanbao import getsuliaowanbao
    from zz91db_log import getlog
    getlogs=getlog()
    detailslist=[]
    detailslist.append(getwanbao())
    detailslist.append(getsuliaowanbao())
    typeid='2'
    title='金属报价'
    gmt_date=datetime.date.today()
    for details in detailslist:
        getlogs.insertinto(details,gmt_date)
#----金属,塑料早参
#@schedudler.cron_schedule(second='1', minute='30', day_of_week='0-6', hour='13') 
def autofunc10():
    from getjinshu_zaocan import getjinshuzaocan
    from suliao_zaocan import getsuliaozaocan
    from zz91db_log import getlog
    getlogs=getlog()
    detailslist=[]
    detailslist.append(getjinshuzaocan())
    detailslist.append(getsuliaozaocan())
    typeid='2'
    title='金属报价'
    gmt_date=datetime.date.today()
    for details in detailslist:
        getlogs.insertinto(details,gmt_date)
if taskname=="1":
    autofunc1()
if taskname=="2":
    autofunc2()
if taskname=="3":
    autofunc3()
if taskname=="4":
    autofunc4()
if taskname=="5":
    autofunc5()
if taskname=="6":
    autofunc6()
if taskname=="7":
    autofunc7()
if taskname=="8":
    autofunc8()
if taskname=="9":
    autofunc9()
if taskname=="10":
    autofunc10()
if taskname=="11":
    autofunc11()
#schedudler.start()