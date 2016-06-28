#-*- coding:utf-8 -*-
#----开始时间2011-12-29
#----登陆统计logindata.html
from public import *
from zz91conn import database_other,database_comp
from zz91settings import logpath_zz91
from zz91tools import date_to_str,int_to_datetime,int_to_str
import datetime,operator,os

conn=database_other()
cursor=conn.cursor()
conn_comp=database_comp()
cursor_comp=conn_comp.cursor()

def getarea(code):
    sql='select label from category where code=%s'
    cursor_comp.execute(sql,[code])
    result=cursor_comp.fetchone()
    if result:
        return result[0]

def getlog_count(company_id,dday_str):
    sql='select login_count from analysis_login where company_id=%s and gmt_target=%s'
    cursor_comp.execute(sql,[company_id,dday_str])
    result=cursor_comp.fetchone()
    if result:
        return result[0]
    
def getcompany_id(account):
    sql='select company_id from company_account where account=%s'
    cursor_comp.execute(sql,[account])
    result=cursor_comp.fetchone()
    if result:
        return result[0]

def getcompany_name(id):
    sql='select name from company where id=%s'
    cursor_comp.execute(sql,[id])
    result=cursor_comp.fetchone()
    if result:
        return result[0]

def getcompany_detail(id):
    sql='select name,industry_code,membership_code,area_code,regtime from company where id=%s'
    cursor_comp.execute(sql,[id])
    result=cursor_comp.fetchone()
    if result:
        list={'company_name':result[0],'industry_code':result[1],'membership_code':result[2],'area_code':result[3],'regtime':result[4]}
        return list

def getagindata(gmt_created,arg=''):
    set1 = set()
    list1=[]
    datalist=open(logpath+'run.'+gmt_created)
    for line in datalist:
        linedir=eval(line)
        ip=linedir['ip']
        datadir=eval(linedir['data'])
        account=datadir['account']
        countdate=datadir['date']
        url=datadir['url']
        lt1={'ip':ip,'account':account,'countdate':countdate,'url':url}
        list1.append(lt1)
        set1.add(ip)
    datalist.close()
    if arg==1:
        list1.sort(key=operator.itemgetter('countdate'),reverse=True)
    else:
        list1.sort(key=operator.itemgetter('countdate'),reverse=False)
    return {'list1':list1,'set1':set1}

def getcompany_log(dday_str):
    set1=set()
    list1=[]
    sql8='select id from data_login where gmt_created=%s'
    cursor.execute(sql8,[dday_str])
    result8=cursor.fetchone()
    if not result8:
        if not os.path.exists(logpath_zz91+'run.'+dday_str):
            return
        datalist=open(logpath_zz91+'run.'+dday_str)
        for line in datalist:
            try:
                if len("".join(line.split()))>0:
                    try:
                        linedir=eval(line)
                    except:
                        continue
                    ip=linedir['ip']
                    operation=linedir['operation']
                    if operation=='login':
                        if ip=='115.236.188.99':
                            continue
                        if ip=='127.0.0.1':
                            continue
                        gmt_created=linedir['time']
                        company_id=linedir['operator']
                        gmt_created=int(gmt_created/1000)
                        lt1={'company_id':company_id,'gmt_created':gmt_created}
                        list1.append(lt1)
                        set1.add(company_id)
                        sql3='insert into data_login_detail(company_id,gmt_date,gmt_created) values(%s,%s,%s)'
                        cursor.execute(sql3,[company_id,dday_str,int_to_datetime(gmt_created)])
                        conn.commit()
            except:
                pass
        list1.sort(key=operator.itemgetter('gmt_created'),reverse=True)
        list2=[]
        for st1 in set1:
            for ltt1 in list1:
                company_id=ltt1['company_id']
                gmt_created=ltt1['gmt_created']
                if st1==company_id:
                    last_logtime=gmt_created
                    lt2={'company_id':company_id,'last_logtime':last_logtime}
                    list2.append(lt2)
                    break
        list1.sort(key=operator.itemgetter('gmt_created'),reverse=False)
        list3=[]
        for st2 in list2:
            for ltt2 in list1:
                company_id2=ltt2['company_id']
                gmt_created2=ltt2['gmt_created']
                if st2['company_id']==company_id2:
                    lt3={'company_id':company_id2,'last_logtime':st2['last_logtime'],'first_logtime':gmt_created2}
                    list3.append(lt3)
                    break
        
        gmt_date=int_to_str(gmt_created)
        for ltt3 in list3:
#            print ltt3
            company_id=ltt3['company_id']
            first_logtime=ltt3['first_logtime']
            last_logtime=ltt3['last_logtime']
            how_logtime=last_logtime-first_logtime
            last_logtime=int_to_datetime(last_logtime)
            company_detail=getcompany_detail(company_id)
            if company_detail:
                company_name=company_detail['company_name']
                industry_code=company_detail['industry_code']
                membership_code=company_detail['membership_code']
                regtime=company_detail['regtime']
                area_code=company_detail['area_code']
                main_area_code=area_code[:12]
                log_count=getlog_count(company_id,dday_str)
#                log_count=1
                if membership_code=='10051000':
                    is_senior=0
                else:
                    is_senior=1
                argument=[company_id,company_name,industry_code,membership_code,area_code,main_area_code,is_senior,log_count,last_logtime,how_logtime,regtime,gmt_date,dday_str]
                sql9='insert into data_login(company_id,company_name,industry_code,membership_code,area_code,main_area_code,is_senior,log_count,last_logtime,how_logtime,regtime,gmt_date,gmt_created) values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)'
                cursor.execute(sql9,argument)
                conn.commit()

def getcompanylogall():
    
    from zz91tools import getpastday
    daylist=getpastday(2)
    for dday in daylist:
        aa=os.system("wget -m http://192.168.110.112:801/zz91/run."+str(dday)+" -nH --cut-dirs=1 -P /usr/data/log4z/zz91")
        dday_str=date_to_str(dday)
        getcompany_log(dday_str)
    return 55

if __name__=="__main__":
    getcompanylogall()