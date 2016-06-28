#-*- coding:utf-8 -*-
from public import *
from companydata import getcompanypub
from publish import getcompanypro,getcompanycheck
from zz91tools import getnextdate
from apscheduler.scheduler import Scheduler
schedudler = Scheduler(daemonic = False)

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

schedudler.start()
#getdata3()