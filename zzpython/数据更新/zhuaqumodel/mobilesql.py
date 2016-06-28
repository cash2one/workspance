#-*- coding:utf-8 -*-
#----抓取手机号码所属地区
from public import *
#from zz91conn import database_other
from zz91conn import database_comp
import datetime,time,re
#conn=database_other()
#cursor = conn.cursor()
conn=database_comp()
cursor = conn.cursor()
'''
def getaccess():
    datalist=open('Dm_Mobile.txt')
    for data in datalist:
        aresall=data.split(',')
        ares=aresall[2]
        areas=ares.split(' ')
        province=areas[0]
        city=areas[1]
        id=aresall[0]
        numb=aresall[1]
        type=aresall[3]
        areacode=aresall[4]
        postcode=aresall[5]
        argument=[id,numb,province,city,type,areacode,postcode]
        sql='insert into mobile_number(id,numb,province,city,type,areacode,postcode) values(%s,%s,%s,%s,%s,%s,%s)'
        cursor.execute(sql,argument)
        conn.commit()
getaccess()
def getaccess():
    datalist=open('guonei.txt')
    for data in datalist:
        aresall=data.split(',')
        number='0'+aresall[3]
        province=aresall[1]
        city=aresall[0]
        zipcode=aresall[2]
        argument=[number,province,city,zipcode]
        sql='insert into tel_guoneinumb(number,province,city,zipcode) values(%s,%s,%s,%s)'
        cursor.execute(sql,argument)
        conn.commit()

getaccess()
'''
'''
def getaccess():
    datalist=open('guoji.txt')
    for data in datalist:
        aresall=data.split(',')
        cname=aresall[0]
        ename=aresall[1]
        number='00'+aresall[2]
        price=aresall[3]
        dtime=aresall[4]
        argument=[cname,ename,number,price,dtime]
        sql='insert into tel_guojinumb(cname,ename,number,price,dtime) values(%s,%s,%s,%s,%s)'
        cursor.execute(sql,argument)
        conn.commit()

getaccess()
'''
'''
def getaccess():
    datalist=open('iparea.txt')
    for data in datalist:
        aresall=data.split(',')
        ip=aresall[0]
        ip2=aresall[1]
        area=aresall[2]
        typename=''
        if len(aresall)>3:
            typename=aresall[3]
        argument=[ip,ip2,area,typename]
        sql='insert into ip_area(ip,ip2,area,typename) values(%s,%s,%s,%s)'
        cursor.execute(sql,argument)
        conn.commit()

def getaccess():
    fee=110
    #----(充值做活动)
    timeall=datetime.datetime.now()
    sqlgg='select begin_time,end_time,infee,sendfee from qianbao_gg where id=1'
    cursor.execute(sqlgg)
    resultgg=cursor.fetchone()
    if resultgg:
        begin_time=resultgg[0]
        end_time=resultgg[1]
        infee=resultgg[2]
        sendfee=resultgg[3]
        if begin_time and end_time and infee and sendfee:
            beilv=str(float(fee)/infee).split('.')[0]
            fee1=int(beilv)*sendfee
            if timeall>=begin_time and timeall<end_time:
                ftype1=8
                print fee1
#                        fee1=20
#                value2=[company_id,fee1,ftype1,trade_no,gmt_date,gmt_created,gmt_modified]
#                cursor.execute(sqlp,value2)
        '''
def getareaname(code):
    sql='select label from category where code=%s'
    cursor.execute(sql,code)
    result=cursor.fetchone()
    if result:
        return result[0]
def getarea_code(company_id):
    sql='select area_code from company where id=%s'
    cursor.execute(sql,company_id)
    result=cursor.fetchone()
    if result:
        return result[0]
def getaccess():
    '''
    sql2='select company_id from products order by refresh_time limit 5000,1000'
    cursor.execute(sql2)
    resultlist=cursor.fetchall()
    set1=set()
    for result in resultlist:
        company_id=result[0]
        area_code=getarea_code(company_id)
        if area_code:
            if area_code[:8]=='10011000':
                continue
            area=getareaname(area_code)
            set1.add(area)
    arealist=''
    for st1 in set1:
        if st1:
            arealist+='|'+st1
    print arealist
    '''
    
    list123='国|爱尔兰|澳大利亚|马来西亚|主营行业|泰国|波兰|美国|荷兰|新西兰|日本|菲律宾|越南|英国|主营行业|阿联酋|波兰|美国|加拿大|德国|西班牙|新西兰|合作伙伴 【文】|韩国|津巴布韦|国外供货商|泰国|新加坡|波兰|主营行业|加拿大|南非|德国|新西兰|美国|日本|其它|主营行业|马来西亚|美国废塑料价格行情|国外供货商|新加坡|意大利|美国|加拿大|德国|法国|合作伙伴 【文】|韩国|马来西亚|英国|美国废塑料价格行情|主营行业|国外供货商|美国|加拿大|德国|乌克兰|日本|合作伙伴 【文】|韩国|澳大利亚|美国废塑料价格行情|主营行业|国外供货商|新加坡|美国|加拿大|德国|西班牙'
    set2=set()
    list123list=list123.split('|')
    for lt2 in list123list:
        if lt2:
            set2.add(lt2)
    arealist2=('|').join(set2)
    print arealist2
getaccess()