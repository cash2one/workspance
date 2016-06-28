#-*- coding:utf-8 -*-
from public import *
from companydata import getcompanypub
from publish import getcompanypro,getcompanycheck
from zz91tools import getnextdate
from apscheduler.scheduler import Scheduler
schedudler = Scheduler(daemonic = False)

#----关联账号和ip
@schedudler.cron_schedule(second='1', minute='1', day_of_week='0-6', hour='15,17') 
def getdata6():
    try:
        from iplist import getiplistall
        getiplistall()
    except:
        pass
#----生意管家操作记录
@schedudler.cron_schedule(second='1', minute='5', day_of_week='0-6', hour='15,17') 
def getdata1():
    try:
        from operadata import getmyrcall
        getmyrcall()
    except:
        pass
#----ip搜索词
@schedudler.cron_schedule(second='1', minute='10', day_of_week='0-6', hour='15,17') 
def getdata2():
    try:
        from ipsearch import ipsearchall
        ipsearchall()
    except:
        pass
#----公司登陆详情
@schedudler.cron_schedule(second='1', minute='15', day_of_week='0-6', hour='15,17') 
def getdata5():
    try:
        from company_log import getcompanylogall
        getcompanylogall()
    except:
        pass

#----公司发布详情
@schedudler.cron_schedule(second='1', minute='*/5', day_of_week='0-6', hour='8-18') 
def getdata3():
    gmt_date=getcompanycheck()
    if gmt_date:
        from zz91db_130 import otherdb
        dbo=otherdb()
        try:
            gmt_date2=getnextdate(gmt_date)
            getcompanypub(gmt_date,gmt_date2)
            getcompanypro(gmt_date,gmt_date2)
            sql='update compro_check set is_check=1 where gmt_date=%s'
            dbo.updatetodb(sql,gmt_date)
        except:
            sql='delete from data_product where gmt_date=%s'
            sql2='delete from data_company where gmt_date=%s'
            dbo.updatetodb(sql,gmt_date)
            dbo.updatetodb(sql2,gmt_date)
    
'''
#----公司发布详情
@schedudler.cron_schedule(second='1', minute='10', day_of_week='0-6', hour='16,18') 
def getdata3():
    from companydata import getcompanypub,getcompanycheck
    companycheck=getcompanycheck()
    if companycheck:
        getcompanypub()
#----公司产品发布数据
@schedudler.cron_schedule(second='1', minute='20', day_of_week='0-6', hour='16,18') 
def getdata4():
    from publish import getcompanypro,getcompanycheck
    companycheck=getcompanycheck()
    if companycheck:
        getcompanypro()
#----ip访问的url
@schedudler.cron_schedule(second='1', minute='20', day_of_week='0-6', hour='15,17') 
def getdata7():
    try:
        from visiturl import getvisiturlall
        getvisiturlall()
    except:
        pass
#----注册转化率统计
@schedudler.cron_schedule(second='1', minute='25', day_of_week='0-6', hour='15,17') 
def getdata8():
    try:
        from tongji import gettongjiall
        gettongjiall()
    except:
        pass
#----IP访问页面个数
@schedudler.cron_schedule(second='1', minute='30', day_of_week='0-6', hour='15,17') 
def getdata9():
    try:
        from ipvisit import getipvisitall
        getipvisitall()
    except:
        pass
----ip流动访问页面
@schedudler.cron_schedule(second='1', minute='35', day_of_week='0-6', hour='15,17') 
def getdata10():
    try:
        from datarun import datarunall
        datarunall()
    except:
        pass
'''
schedudler.start()
