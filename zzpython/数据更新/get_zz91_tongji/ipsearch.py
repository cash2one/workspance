#-*- coding:utf-8 -*-
#----开始时间2014-07-01
#----IP搜索关键词统计keywordssearchdata.html
from public import *
from zz91conn import database_other,database_comp
from zz91settings import logpath
from zz91tools import date_to_str,int_to_datetime
import datetime,os

conn=database_other()
cursor=conn.cursor()
conn_comp=database_comp()
cursor_comp=conn_comp.cursor()

def getipaccount(ip):
    sql='select account_id from data_ip where numb=%s'
    cursor.execute(sql,[ip])
    result=cursor.fetchone()
    if result:
        account_id=result[0]
        sql2='select numb from data_account where id=%s'
        cursor.execute(sql2,[account_id])
        result2=cursor.fetchone()
        if result2:
            return result2[0]

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
    sql='select name,industry_code,membership_code from company where id=%s'
    cursor_comp.execute(sql,[id])
    result=cursor_comp.fetchone()
    if result:
        list={'company_name':result[0],'industry_code':result[1],'membership_code':result[2]}
        return list

def getipsearch(dday_str):
    os.system("wget -m http://192.168.110.112:801/zz91Analysis/run."+str(dday_str)+" -nH --cut-dirs=1 -P /usr/data/log4z/zz91Analysis")
    datalist=open(logpath+'run.'+dday_str)
    for line in datalist:
        if len("".join(line.split()))>0:
            linedir=eval(line)
            ip=linedir['ip']
            datadir=eval(linedir['data'])
            keyword=''
            if datadir.has_key('keyword'):
                if ip=='115.236.188.99':
                    continue
                if ip=='127.0.0.1':
                    continue
                keyword=datadir['keyword']
                account=datadir['account']
                if not account:
                    account=getipaccount(ip)
                gmt_date=datadir['date']
                gmt_date=int(gmt_date/1000)
                gmt_date=int_to_datetime(gmt_date)
                url=datadir['url']
                argument=[keyword,account,url,ip,gmt_date,dday_str]
                sql7='select id from data_ip_search where keyword=%s and ip=%s and gmt_created=%s'
                cursor.execute(sql7,[keyword,ip,dday_str])
                result7=cursor.fetchone()
                if not result7:
                    sql9='insert into data_ip_search(keyword,account,url,ip,gmt_date,gmt_created) values(%s,%s,%s,%s,%s,%s)'
                    cursor.execute(sql9,argument)
                    conn.commit()
    sql='select id,keyword,account,url,ip,gmt_date from data_ip_search where gmt_created=%s'
    cursor.execute(sql,[dday_str])
    resultlist=cursor.fetchall()
    for result in resultlist:
        company_name=None
        company_id=None
        industry_code=None
        membership_code=None
        is_senior=None
        id=result[0]
        keyword=result[1]
        account=result[2]
        url=result[3]
        ip=result[4]
        if account:
            company_id=getcompany_id(account)
            company_detail=getcompany_detail(company_id)
            if company_detail:
                company_name=company_detail['company_name']
                industry_code=company_detail['industry_code']
                membership_code=company_detail['membership_code']
                if membership_code=='10051000':
                    is_senior=0
                else:
                    is_senior=1
            sql4='select count(0) from data_ip_search where account=%s and gmt_created=%s'
            cursor.execute(sql4,[account,dday_str])
            result4=cursor.fetchone()
            if result4:
                search_count=result4[0]
        else:
            sql4='select count(0) from data_ip_search where ip=%s and gmt_created=%s'
            cursor.execute(sql4,[ip,dday_str])
            result4=cursor.fetchone()
            if result4:
                search_count=result4[0]
#        gmt_date=formattime(result[5])
        sql2='select count(0) from data_ip_search where ip=%s and keyword=%s and gmt_created=%s'
        cursor.execute(sql2,[ip,keyword,dday_str])
        result2=cursor.fetchone()
        if result2:
            keyword_count=result2[0]
        sql3='select count(0) from data_ip_search where keyword=%s and gmt_created=%s'
        cursor.execute(sql3,[keyword,dday_str])
        result3=cursor.fetchone()
        if result3:
            keyword_allcount=result3[0]
        score=float(keyword_count)*100/keyword_allcount
#        keyword_score=str(keyword_allcount)+'/'+str(count)
        argument2=[company_id,company_name,keyword_count,keyword_allcount,search_count,score,industry_code,membership_code,is_senior,id]
        sql5='update data_ip_search set company_id=%s,company_name=%s,keyword_count=%s,keyword_allcount=%s,search_count=%s,score=%s,industry_code=%s,membership_code=%s,is_senior=%s where id=%s'
        cursor.execute(sql5,argument2)
        conn.commit()

def ipsearchall():
    from zz91tools import getpastday
    daylist=getpastday(10)
    sql8='select id from data_ip_search where gmt_created=%s'
    for dday in daylist:
        dday_str=date_to_str(dday)
        cursor.execute(sql8,[dday_str])
        result8=cursor.fetchone()
        if not result8:
            getipsearch(dday_str)
    return 'IP搜索统计'

if __name__=="__main__":
    ipsearchall()