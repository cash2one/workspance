#-*- coding:utf-8 -*-
#----开始时间2014-05-01
#----app客户使用统计
from public import *
from zz91db_ast import companydb
from zz91db_zzlog import zzlogdb
from zz91conn import database_mongodb
from time import strftime, localtime
from datetime import timedelta, date
from zz91tools import date_to_str,int_to_datetime,str_to_datetime,getpastday,formattime,str_to_int
import datetime
dbc=companydb()
dblog=zzlogdb()

#连接loginfo集合（表）
dbmongo=database_mongodb()


def tongji(current_date):
    collection=dbmongo.loginfo
    #current_date=date.today()#当前日期
    #current_date=datetime.date.today()- datetime.timedelta(days=2)
    #current_date=datetime.date.today()
    start_time=str(current_date)+" 00:00:00" #当前日期的0点
    end_time=str(current_date)+" 23:59:59" #当前日期的最后一秒
    #新增用户数
    #sql1="select count(0) from oauth_access where gmt_created >= %s and gmt_created <= %s and open_type='app.zz91.com'"
    #newUser_num=dbc.fetchonedb(sql1,[start_time,end_time])[0]
    
    
    #总活跃用户数(当天登录总记录数)
    #sql1="select count(0) from app_logincount where login_date >= %s and login_date <= %s"
    #all_active_num=dblog.fetchonedb(sql1,[start_time,end_time])[0]
    #当日活跃数  
    #sql2为从oauth_access表中找出当天新安装用户id
    #sql3为从app_logincount表找出今当天登录用户id
    active_num=0
    sql2="select open_id from oauth_access where gmt_created >= %s and gmt_created <= %s and open_type='app.zz91.com'"
    
    sql3="select clientid from app_login where login_date >= %s and login_date <= %s"
    
    j=dbc.fetchalldb(sql2,[start_time,end_time]) #当天新安装的新用户
    #k=dblog.fetchalldb(sql3,[start_time,end_time]) #当天所有登录的用户
    newUser_num=len(j)
    #active_num=len(k)
    
    active_num=collection.find({"gmt_created":{"$gt":str_to_datetime(start_time),"$lte":str_to_datetime(end_time)}}).distinct("clientid")
    active_num=len(active_num)
    #沉默用户数    
    silence_num=0
    
    #pv
    pvcount=collection.find({"gmt_created":{"$gt":str_to_datetime(start_time),"$lte":str_to_datetime(end_time)}}).count()
    #启动次数
    start_times=0
    sql4="select sum(login_count) from app_logincount where login_date >= %s and login_date <= %s"
    start_times_result=dblog.fetchonedb(sql4,[start_time,end_time])[0]

    #更新或插入数据库
    value=[newUser_num,active_num,silence_num,start_times_result,pvcount,current_date]
    value2=[current_date,newUser_num,active_num,silence_num,start_times_result,pvcount]
    
    
    sql="select id from app_tongji where gmt_date=%s"
    result=dblog.fetchonedb(sql,[current_date])
    if result:
        sql="update app_tongji set t_new=%s,t_active=%s,t_noactive=%s,t_login=%s,t_pv=%s where gmt_date=%s"
        dblog.updatetodb(sql,value)
    else:
        sql="insert into app_tongji(gmt_date,t_new,t_active,t_noactive,t_login,t_pv) values(%s,%s,%s,%s,%s,%s)"
        dblog.updatetodb(sql,value2)
    
    #日（小时）数据统计
    hlist=range(0,24)
    for h in hlist:
        hstart_time=str(current_date)+" "+str(h)+":00:00" #当前日期的0点
        hend_time=str(current_date)+" "+str(h)+":59:59" #当前日期的最后一秒
        t_active=collection.find({"gmt_created":{"$gt":str_to_datetime(hstart_time),"$lte":str_to_datetime(hend_time)}}).distinct("clientid")
        t_active=len(t_active)
        
        #pv
        t_pv=collection.find({"gmt_created":{"$gt":str_to_datetime(hstart_time),"$lte":str_to_datetime(hend_time)}}).count()
        
        sql="select id from app_tongji_day where gmt_date=%s and gmt_hour=%s"
        result=dblog.fetchonedb(sql,[current_date,h])
        if result:
            sql="update app_tongji_day set t_active=%s,t_pv=%s where gmt_date=%s and gmt_hour=%s"
            dblog.updatetodb(sql,[t_active,t_pv,current_date,h])
        else:
            sql="insert into app_tongji_day(gmt_date,gmt_hour,t_active,t_pv) values(%s,%s,%s,%s)"
            dblog.updatetodb(sql,[current_date,h,t_active,t_pv])

def tongjilog():
    collection=dbmongo.loginfo
    result=collection.find({"check":0}).sort("gmt_created",-1).skip(0).limit(10000)
    for list in result:
        id= list['_id']
        clientid=list['clientid']
        gmt_created=formattime(list['gmt_created'],0)
        gmt_date=formattime(list['gmt_created'],1)
        visitoncode=list['visitoncode']
        company_id=list['company_id']
        appsystem=list['appsystem']
        url=list['url']
        check=list['check']
        if clientid:
            sql="select login_time from app_login where clientid=%s order by id desc limit 0,1"
            rec=dblog.fetchonedb(sql,[clientid])
            if rec:
                oldlogindate=str_to_int(formattime(rec[0],0))
                nowlogindate=str_to_int(gmt_created)
                logindatediff=int((nowlogindate-oldlogindate)/60)
                logindatediffmili=nowlogindate-oldlogindate
                #登录频率情况  30分钟内没有登录的再次登录算登录2次
                #并记录在线时长
                sql="select id from app_logincount where clientid=%s and login_date=%s"
                rec=dblog.fetchonedb(sql,[clientid,gmt_date])
                if rec:
                    if logindatediff>30:
                        sqld="update app_logincount set login_count=login_count+1 where clientid=%s and login_date=%s"
                        dblog.updatetodb(sqld,[clientid,gmt_date])
                    sqld="update app_logincount set login_long=login_long+"+str(logindatediffmili)+" where clientid=%s and login_date=%s"
                    dblog.updatetodb(sqld,[clientid,gmt_date])
                else:
                    sqld="insert into app_logincount(company_id,clientid,login_date,login_count,login_long) values(%s,%s,%s,%s,%s)"
                    dblog.updatetodb(sqld,[company_id,clientid,gmt_date,1,1])
    
            #登录情况
            sql="select id from app_login where clientid=%s"
            rec=dblog.fetchonedb(sql,[clientid])
            if rec:
                sqld="update app_login set login_date=%s,login_time=%s,company_id=%s where clientid=%s"
                dblog.updatetodb(sqld,[gmt_date,gmt_created,company_id,clientid])
            else:
                sqld="insert into app_login(company_id,appsystem,visitoncode,clientid,login_date,login_time) values(%s,%s,%s,%s,%s,%s)"
                dblog.updatetodb(sqld,[company_id,appsystem,visitoncode,clientid,gmt_date,gmt_created])
                sqld="insert into app_logincount(company_id,clientid,login_date,login_count,login_long) values(%s,%s,%s,%s,%s)"
                dblog.updatetodb(sqld,[company_id,clientid,gmt_date,1,1])
        collection.update({"_id": id}, {"$set": {"check": 1}});

daylist=getpastday(1)
for dday in daylist:
    dday_str=date_to_str(dday)
    tongji(dday_str)
tongji(date.today())
tongjilog()
