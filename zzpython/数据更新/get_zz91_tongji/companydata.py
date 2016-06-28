#-*- coding:utf-8 -*-
#----开始时间2007-04-02
#----公司发布详情统计publishdetaildata.html
from public import *
import datetime,time,MySQLdb
from zz91conn import database_other,database_comp
from zz91tools import date_to_str
from sphinxapi import *

conn=database_other()
cursor = conn.cursor()
conn_comp=database_comp()
cursor_comp = conn_comp.cursor()

def getcompanycheck():
    import datetime
    gmt_date=datetime.date.today()
    sql='select id from compro_check where is_check=1 and gmt_date=%s'
    cursor_comp.execute(sql,gmt_date)
    result=cursor_comp.fetchone()
    if result:
        return result[0]

def getcompany_vipcode(id):
    sql='select membership_code from company where id=%s'
    cursor_comp.execute(sql,[id])
    result=cursor_comp.fetchone()
    if result:
        return result[0]

#---获得公司名
def getcompany_name(id):
    sql='select name from company where id=%s'
    cursor_comp.execute(sql,[id])
    result=cursor_comp.fetchone()
    if result:
        return result[0]
    
#---获得供应数量
def getproffor_numb(company_id,ctg_code,gmt_begin,gmt_end):
    sql='select category_products_main_code from products where company_id=%s and products_type_code=10331000 and gmt_created>=%s and gmt_created<%s'
    cursor_comp.execute(sql,[company_id,gmt_begin,gmt_end])
    resultlist=cursor_comp.fetchall()
    numb=0
    if resultlist:
        for result in resultlist:
            category_products_main_code=result[0]
            if category_products_main_code:
                category_code=category_products_main_code[:4]
                if category_code==ctg_code:
                    numb+=1
    return numb

#---获得求购数量
def getprobuy_numb(company_id,ctg_code,gmt_begin,gmt_end):
    sql='select category_products_main_code from products where company_id=%s and products_type_code=10331001 and gmt_created>=%s and gmt_created<%s'
    cursor_comp.execute(sql,[company_id,gmt_begin,gmt_end])
    resultlist=cursor_comp.fetchall()
    numb=0
    if resultlist:
        for result in resultlist:
            category_products_main_code=result[0]
            if category_products_main_code:
                category_code=category_products_main_code[:4]
                if category_code==ctg_code:
                    numb+=1
    return numb

#---获得供求通过数量
def getpropass_numb(company_id,ctg_code,gmt_begin,gmt_end):
    sql='select category_products_main_code from products where company_id=%s and check_status=1 and gmt_created>=%s and gmt_created<%s'
    cursor_comp.execute(sql,[company_id,gmt_begin,gmt_end])
    resultlist=cursor_comp.fetchall()
    numb=0
    if resultlist:
        for result in resultlist:
            category_products_main_code=result[0]
            if category_products_main_code:
                category_code=category_products_main_code[:4]
                if category_code==ctg_code:
                    numb+=1
    return numb

#---获得供求退回数量
def getproback_numb(company_id,ctg_code,gmt_begin,gmt_end):
    sql='select category_products_main_code from products where company_id=%s and check_status=2 and gmt_created>=%s and gmt_created<%s'
    cursor_comp.execute(sql,[company_id,gmt_begin,gmt_end])
    resultlist=cursor_comp.fetchall()
    numb=0
    if resultlist:
        for result in resultlist:
            category_products_main_code=result[0]
            if category_products_main_code:
                category_code=category_products_main_code[:4]
                if category_code==ctg_code:
                    numb+=1
    return numb

#---获得供求刷新时间
def getprorefresh_time(company_id,ctg_code,gmt_begin,gmt_end):
    sql='select refresh_time,category_products_main_code from products where company_id=%s and gmt_created>=%s and gmt_created<%s order by refresh_time desc'
    cursor_comp.execute(sql,[company_id,gmt_begin,gmt_end])
    resultlist=cursor_comp.fetchall()
    if resultlist:
        for result in resultlist:
            refresh_time=result[0]
            category_products_main_code=result[1]
            if category_products_main_code:
                category_code=category_products_main_code[:4]
                if category_code==ctg_code:
                    return refresh_time
#---获得收询盘数
def get_ask_num(company_id,gmt_begin,gmt_end):
    sql='select count(0) from inquiry where be_inquired_id=%s and gmt_created>=%s and gmt_created<%s'
    cursor_comp.execute(sql,[company_id,gmt_begin,gmt_end])
    result=cursor_comp.fetchone()
    if result:
        return result[0]
#---获得发询盘数
def getsend_ask_num(account,gmt_begin,gmt_end):
    sql='select count(0) from inquiry where sender_account=%s and gmt_created>=%s and gmt_created<%s'
    cursor_comp.execute(sql,[account,gmt_begin,gmt_end])
    result=cursor_comp.fetchone()
    if result:
        return result[0]
#---获得发询盘时间
def getsend_time(account):
    sql='select send_time from inquiry where sender_account=%s order by send_time desc'
    cursor_comp.execute(sql,[account])
    result=cursor_comp.fetchone()
    if result:
        return result[0]
#---获得公司帐号
def getcompany_account(company_id):
    sql='select account from company_account where company_id=%s'
    cursor_comp.execute(sql,[company_id])
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

listdir=['1000','1001','1002','1003','1004','1005','1006','1007','1008','1009']

def getpub2(gmt_begin,gmt_end):
    for is_senior in [0,1]:
        for ctg_code in listdir:
            getpub(gmt_begin,gmt_end,ctg_code,is_senior)

def getpub3(company_id,gmt_begin,gmt_end,ctg_code,is_senior):
    buynum=getprobuy_numb(company_id,ctg_code,gmt_begin,gmt_end)
    offnum=getproffor_numb(company_id,ctg_code,gmt_begin,gmt_end)
    pro_num=offnum+buynum
    pro_pass_num=getpropass_numb(company_id,ctg_code,gmt_begin,gmt_end)
    pro_back_num=getproback_numb(company_id,ctg_code,gmt_begin,gmt_end)
    refresh_time=getprorefresh_time(company_id,ctg_code,gmt_begin,gmt_end)
    account=getcompany_account(company_id)
    ask_num=get_ask_num(company_id,gmt_begin,gmt_end)
    send_ask_num=getsend_ask_num(account,gmt_begin,gmt_end)
    send_time=getsend_time(account)
    company_name=getcompany_name(company_id)
    gmt_created=datetime.datetime.now()
    argument=[company_id,account,company_name,pro_num,offnum,buynum,pro_pass_num,pro_back_num,ask_num,send_ask_num,ctg_code,'',is_senior,refresh_time,send_time,gmt_begin,gmt_created]                            
    sql11='insert into data_company(company_id,account,company_name,pro_num,offnum,buynum,pro_pass_num,pro_back_num,get_ask_num,send_ask_num,industry_code,membership_code,is_senior,refresh_time,send_time,gmt_date,gmt_created) values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)'
    cursor.execute(sql11,argument)
    conn.commit()
    
#----获得公司发布数据
def getpub(gmt_begin,gmt_end,ctg_code,is_senior):
    sql='select company_id,category_products_main_code from products where gmt_created>=%s and gmt_created<%s group by company_id limit 0,15'
    cursor_comp.execute(sql,[gmt_begin,gmt_end])
    resultlist=cursor_comp.fetchall()
    for result in resultlist:
        company_id=result[0]
        cmain_code=result[1]
        if cmain_code:
            vipcode=getcompany_vipcode(company_id)
#            category_code=cmain_code[:4]
#            if category_code==ctg_code:
            if is_senior==0:
                if vipcode=='10051000':
                    getpub3(company_id,gmt_begin,gmt_end,ctg_code,is_senior)

            else:
                if not vipcode=='10051000':
                    getpub3(company_id,gmt_begin,gmt_end,ctg_code,is_senior)

def getcompanypub(gmt_begin,gmt_end):
    if gmt_begin and gmt_end :
        sql0='select id from data_company where gmt_date=%s'
        cursor.execute(sql0,[gmt_begin])
        result0=cursor.fetchone()
        if not result0:
            getpub2(gmt_begin,gmt_end)
    else:
        from zz91tools import getdatelist
        timelist=getdatelist(1)
        for timeall in timelist:
            gmt_begin=timeall['gmt_begin']
            gmt_end=timeall['gmt_end']
            sql0='select id from data_company where gmt_date=%s'
            cursor.execute(sql0,[gmt_begin])
            result0=cursor.fetchone()
            if not result0:
                getpub2(gmt_begin,gmt_end)
        return date_to_str(gmt_begin)+' 到 '+date_to_str(gmt_end)+' 公司发布详情统计'

if __name__=="__main__":
    getcompanypub()