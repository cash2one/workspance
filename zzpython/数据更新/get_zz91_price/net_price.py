#-*- coding:utf-8 -*-
#----废金属,废塑料网上报价
from public import *
from zz91conn import database_comp
import datetime,time,re
conn=database_comp()
cursor = conn.cursor()

timedate=time.strftime('%m月%d日',time.localtime(time.time()))
gmt_created=datetime.date.today()
gmt_created2=datetime.datetime.now()

def getcpctype(code):
    sql='select code from category_company_price'
    cursor.execute(sql)
    resultlist=cursor.fetchall()
    listall=[]
    if resultlist:
        for result in resultlist:
            acode=result[0]
            lenarg=len(str(code))
            if str(code)==str(acode)[:lenarg]:
                listall.append(int(acode))
#                print acode
    return listall
def getcompany_name(id):
    sql='select name from company where id=%s'
    cursor.execute(sql,[id])
    result=cursor.fetchone()
    if result:
        return result[0]
def getarea_name(code):
    sql='select label from category where code=%s'
    cursor.execute(sql,[code])
    result=cursor.fetchone()
    if result:
        return result[0]
    
def getnetbaijia(code,type_id,assist_type_id):
    feizhitype=getcpctype(code)
    if code==1001:
        content=''
        content_query=''
        titlemain=timedate+'ZZ91废金属网上报价'
        tags='废金属,价格,行情,网上报价'
        is_remark=0
    elif code==1002:
        content=''
        content_query=''
        titlemain=timedate+'ZZ91废纸网上报价'
        tags='废纸,国内废纸,废纸报价,废纸价格行情'
        is_remark=1
    else:
        content=''
        content_query=''
        if code==10001000:
            titlemain=timedate+'ZZ91废塑料网上报价'
        else:
            titlemain=timedate+'ZZ91塑料颗粒网上报价'
        tags='全国各地,塑料报价,网上报价'
        is_remark=0
    argument1=[titlemain,type_id,assist_type_id,content,gmt_created2,gmt_created2,gmt_created2,0,0,tags,0,1,0,content_query]
    
    sql9='select id from price where title=%s and gmt_created>=%s'
    cursor.execute(sql9,[titlemain,gmt_created])
    result9=cursor.fetchone()
    if result9:
        price_id=result9[0]
    else:
        sql1='insert into price(title,type_id,assist_type_id,content,gmt_order,gmt_created,gmt_modified,is_checked,is_issue,tags,real_click_number,is_remark,ip,content_query) values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)'
        cursor.execute(sql1,argument1)
        conn.commit()
        sql5='select max(id) from price'
        cursor.execute(sql5)
        result5=cursor.fetchone()
        if result5:
            price_id=result5[0]
#    print price_id
    sql='select id,title,company_id,price,min_price,max_price,price_unit,area_code from company_price where category_company_price_code in %s order by refresh_time desc limit 0,60'
    cursor.execute(sql,[feizhitype])
    resultlist=cursor.fetchall()
    if resultlist:
        listall=[]
        for result in resultlist:
            company_price_id=result[0]
            title=result[1]
            company_id=result[2]
            price=result[3]
            if not price:
                min_price=result[4]
                max_price=result[5]
                price_unit=result[6]
                price=min_price+'-'+max_price+price_unit
            area_code=result[7]
            area_name=''
            if area_code:
                area_name=getarea_name(area_code)
            company_name=getcompany_name(company_id)
            if u'元' not in price:
                price=price+u'元/吨'
            if u'设备' not in title and u'机' not in title and u'吨' in price and company_id not in listall:
                listall.append(company_id)
#                print title
#                print price
                sql2='select id from price_data where company_id=%s and gmt_created>=%s'
                cursor.execute(sql2,[company_id,gmt_created])
                result2=cursor.fetchone()
                if not result2:
                    argument=[price_id,company_price_id,title,price,area_name,company_name,company_id,gmt_created2,gmt_created2]
                    sql3='insert into price_data(price_id,company_price_id,product_name,quote,area,company_name,company_id,gmt_created,gmt_modified) values(%s,%s,%s,%s,%s,%s,%s,%s,%s)'
                    cursor.execute(sql3,argument)
                    conn.commit()
        return 1

def getnetpriceall():
    txt4=getnetbaijia(1001,51,0)#废金属
    txt1=getnetbaijia(1002,25,232)#废纸
    txt2=getnetbaijia(10001000,137,0)#废塑料
    txt3=getnetbaijia(10001001,137,0)#废塑料颗粒
    listall=[]
    if txt1:
        listall.append(2)
    if txt2:
        listall.append(4)
    if txt3:
        listall.append(3)
    if txt4:
        listall.append(1)
    return listall

if __name__=="__main__":
    getnetpriceall()