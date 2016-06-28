#-*- coding:utf-8 -*-
#----开始时间2007-04-02
#----公司产品发布统计publishdata.html
from public import *
from zz91conn import database_other,database_comp
from zz91tools import date_to_str

conn=database_other()
cursor = conn.cursor()
conn_comp=database_comp()
cursor_comp = conn_comp.cursor()

def getcompanycheck():
    sql='select gmt_date from compro_check where is_check=0'
    cursor.execute(sql)
    result=cursor.fetchone()
    if result:
        return date_to_str(result[0])

def getcompany_vipcode(id):
    sql='select membership_code from company where id=%s'
    cursor_comp.execute(sql,[id])
    result=cursor_comp.fetchone()
    if result:
        return result[0]

def getpub(gmt_begin,gmt_end,t_code,ctg_code,is_senior):
    import datetime
    sql='select check_status,category_products_main_code,gmt_created,company_id from products where gmt_created>=%s and gmt_created<%s and products_type_code=%s'
    cursor_comp.execute(sql,[gmt_begin,gmt_end,t_code])
    resultlist=cursor_comp.fetchall()
    pro_numb=0
    pro_pass=0
    pro_back=0
    gmt_created=datetime.datetime.now()
    for result in resultlist:
        check_status=result[0]
        category_products_main_code=result[1]
        gmt_date=result[2]
        if gmt_date:
            gmt_date=date_to_str(gmt_date)+' 00:00:00'
        company_id=result[3]
        vipcode=getcompany_vipcode(company_id)
        category_code=''
        if category_products_main_code:
            category_code=category_products_main_code[:4]
            if category_code==ctg_code:
                if is_senior==0:
                    if vipcode=='10051000':
                        pro_numb+=1
                        if check_status=='1':
                            pro_pass+=1
                        elif check_status=='2':
                            pro_back+=1
                else:
                    if not vipcode=='10051000':
                        pro_numb+=1
                        if check_status=='1':
                            pro_pass+=1
                        elif check_status=='2':
                            pro_back+=1
    sql1='insert into data_product(is_senior,type_code,category_code,pro_numb,pro_pass,pro_back,gmt_date,gmt_created) values(%s,%s,%s,%s,%s,%s,%s,%s)'
    cursor.execute(sql1,[is_senior,t_code,ctg_code,pro_numb,pro_pass,pro_back,gmt_begin,gmt_created])
    conn.commit()

listdir=['1000','1001','1002','1003','1004','1005','1006','1007','1008','1009']
list2dir=['10331000','10331001','10331002']

def getpub2(gmt_begin,gmt_end):
    for is_senior in [0,1]:
        for t_code in list2dir:
            for ctg_code in listdir:
                getpub(gmt_begin,gmt_end,t_code,ctg_code,is_senior)

def getcompanypro(gmt_begin='',gmt_end=''):
    if gmt_begin and gmt_end :
        sql2='select id from data_product where gmt_date=%s'
        cursor.execute(sql2,[gmt_begin])
        result2=cursor.fetchone()
        if not result2:
            getpub2(gmt_begin,gmt_end)
    else:
        from zz91tools import getdatelist
        timelist=getdatelist(1)
        for timeall in timelist:
            gmt_begin=timeall['gmt_begin']
            gmt_end=timeall['gmt_end']
    #        print gmt_end
            sql2='select id from data_product where gmt_date=%s'
            cursor.execute(sql2,[gmt_begin])
            result2=cursor.fetchone()
            if not result2:
                getpub2(gmt_begin,gmt_end)
        return date_to_str(gmt_begin)+' 到 '+date_to_str(gmt_end)+' 公司发布供求统计'

if __name__=="__main__":
    getcompanypro()