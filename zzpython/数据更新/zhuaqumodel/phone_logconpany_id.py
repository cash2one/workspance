#-*- coding:utf-8 -*-
#----抓取手机号码所属地区
from public import *
from zz91conn import database_comp,database_other
from zz91tools import getToday,date_to_str,get_url_content,get_content,get_inner_a,get_a_url,getdatelist
import datetime,time,re
conn=database_comp()
cursor=conn.cursor()
conn_other=database_other()
cursor_other=conn_other.cursor()

def getcompanyid2(id,tel):
    sql='select company_id from phone where tel=%s'
    cursor.execute(sql,[tel])
    result=cursor.fetchone()
    if result:
        company_id=result[0]
#        print company_id
        sql2='update phone_log set company_id=%s where id=%s'
        cursor.execute(sql2,[company_id,id])
        conn.commit()
    else:
        print '!'+str(id)+'---'+tel

def getcompanyid():
    timelist=getdatelist(400)
    for timeall in timelist:
        gmt_begin=timeall['gmt_begin']
        gmt_end=timeall['gmt_end']
        sql='select id,company_id,tel from phone_log where start_time>=%s and start_time<%s'
        cursor.execute(sql,[gmt_begin,gmt_end])
        resultlist=cursor.fetchall()
        if resultlist:
            for result in resultlist:
                id=result[0]
                company_id=result[1]
                tel=result[2]
                if not company_id:
                    getcompanyid2(id,tel)

if __name__=="__main__":
    getcompanyid()