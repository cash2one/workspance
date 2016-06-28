from time import ctime, sleep
import os
import datetime
from datetime import timedelta, date 
execfile("/mnt/python/zz91_task/offer/inc.py")
execfile("/mnt/python/zz91_task/conn_server.py")

def getpcategoryname(code,is_assist):
    if (code!="" and code!=None):
        if (is_assist==None):
            sql="select label from category_products where code=%s"
            cursor.execute(sql,[code])
        else:
            sql="select label from category_products where code=%s and is_assist=%s"
            cursor.execute(sql,[code,is_assist])
        resultlist=cursor.fetchone()
        if resultlist:
            if (resultlist[0]==None):
                return ""
            else:
                return resultlist[0]
        else:
            return ""
    else:
        return ""
def getcategoryname(code):
    sql="select label from category where code=%s"
    cursor.execute(sql,[code])
    s=cursor.fetchone()
    if s:
        return s[0]
    else:
        return ""
def getcompanyviptype(code):
    sql="select viptype,viptype_ldb from category where code=%s"
    cursor.execute(sql,[code])
    s=cursor.fetchone()
    if s:
        return {'viptype':s[0],'viptype_ldb':s[1]}
    else:
        return {'viptype':0,'viptype_ldb':0}
def getcompanyinfo(company_id):
    sql="select area_code,membership_code from company where id=%s"
    cursor.execute(sql,[company_id])
    resultlist=cursor.fetchone()
    if resultlist:
        membershipcode=resultlist[1]
        if membershipcode=='10051003':
            sqll="select id from crm_company_service where company_id="+str(company_id)+" and crm_service_code in ('1007','1008','1009','1010') and apply_status=1"
            cursor.execute(sqll)
            ldbresult=cursor.fetchone()
            if not ldbresult:
                membershipcode='10051000'
        return {'area_code':resultlist[0],'membership_code':membershipcode}
def gethavepic(pid):
    sql="select length(id) from products_pic where product_id=%s and check_status=1"
    cursor.execute(sql,[pid])
    resultlist=cursor.fetchone()
    if resultlist:
        return resultlist[0]
    else:
        return 0
def getcrm_company_service(cid):
    sql="select crm_service_code from crm_company_service where company_id=%s and apply_status=1"
    cursor.execute(sql,[cid])
    resultlist=cursor.fetchone()
    if resultlist:
        return resultlist[0]
    else:
        return ""
    
def getproperties(pid):
    sql="select properties from products_properties where products_id=%s"
    cursor.execute(sql,[pid])
    resultlist=cursor.fetchone()
    if resultlist:
        return resultlist[0]
    else:
        return ""
#来电宝接通率
def getphone_rate(company_id):
    sql="select phone_rate,level,phone_num,phone_fee from ldb_level where company_id=%s and is_date=0"
    cursor.execute(sql,[company_id])
    resultlist=cursor.fetchone()
    if resultlist:
        return {'phone_rate':resultlist[0],'phone_level':resultlist[1],'phone_num':resultlist[2],'phone_fee':resultlist[3]}
    else:
        return {'phone_rate':0,'phone_level':0,'phone_num':0,'phone_fee':0}

def gethavesample(pid):
    sql="select length(sample_id),b.is_del,b.amount,b.take_price,b.send_price from sample_relate_product as a inner join sample as b on a.sample_id=b.id where a.product_id=%s"
    cursor.execute(sql,[pid])
    resultlist=cursor.fetchone()
    if resultlist:
        return {'havesample':resultlist[0],'sampledel':resultlist[1],'amount':resultlist[2],'take_price':resultlist[3],'send_price':resultlist[4]}
    else:
        return {'havesample':0,'sampledel':0,'amount':0,'take_price':0,'send_price':0}

nowdate=datetime.date.today()
nowtime=getHour()
if (nowtime=="1:01"):
    sql="TRUNCATE TABLE  `products_s`"
    cursor.execute(sql)
datedi=datediff('1970-1-1',nowdate)
sql="delete from products_s where DATEDIFF(CURDATE(),gmt_modified)>3"
cursor.execute(sql)

sql="select max(gmt_modified) from products_s"
cursor.execute(sql)
resultlist=cursor.fetchone()
if resultlist:
    maxrefresh_time=resultlist[0]
else:
    maxrefresh_time="2000-1-1"
if (maxrefresh_time==None):
    maxrefresh_time="2000-1-1"
print maxrefresh_time

sql="select p.id, p.company_id,UNIX_TIMESTAMP(DATE_FORMAT(p.refresh_time,'%Y-%m-%d')) as pdt_date,"
sql=sql+"DATEDIFF(CURDATE(),p.refresh_time) as Prodatediff, right(p.products_type_code,1) as pdt_kind,"
sql=sql+"p.title, p.title as ptitle,UNIX_TIMESTAMP( p.refresh_time ) AS refresh_time, p.expire_time, p.price,length(p.price) as length_price,"
sql=sql+"p.tags,p.category_products_main_code,p.category_products_assist_code,p.refresh_time,p.check_status,pinyin(left(p.title,1)) as pinyin"
sql=sql+",length(p.min_price) as haveprice,p.gmt_modified,p.is_del,p.is_pause,p.color,p.min_price from products as p where DATEDIFF(CURDATE(),p.refresh_time)<=3"
sql=sql+" and p.refresh_time>'"+str(maxrefresh_time)+"'"

cursor.execute(sql)
results = cursor.fetchall()
for list in results:
    id=list[0]
    company_id=list[1]
    pdt_date=list[2]
    Prodatediff=list[3]
    pdt_kind=list[4]
    title=list[5]
    ptitle=list[6]
    refresh_time=list[7]
    expire_time=list[8]
    price=list[9]
    length_price=list[10]
    tags=list[11]
    category_products_main_code=list[12]
    category_products_assist_code=list[13]
    refresh_time1=list[14]
    check_status=list[15]
    pinyin=list[16]
    haveprice=list[17]
    if (haveprice==None or haveprice==""):
        haveprice=0
    gmt_modified=list[18]
    is_del=list[19]
    is_pause=list[20]
    color=list[21]
    min_price=list[22]
    if (color==None or color==''):
        color=""
    properties=getproperties(id)
    samplevalue=gethavesample(id)
    havesample=samplevalue['havesample']
    sampledel=samplevalue['sampledel']
    amount=samplevalue['amount']
    takePrice=samplevalue['take_price']
    sendPrice=samplevalue['send_price']
    
    
    
    label0=getpcategoryname(category_products_main_code,None)
    label1=getpcategoryname(category_products_main_code[0:4],None)
    label2=getpcategoryname(category_products_main_code[0:8],None)
    label3=getpcategoryname(category_products_main_code[0:12],None)
    label4=getpcategoryname(category_products_assist_code,1)
    
    companyinfo=getcompanyinfo(company_id)
    area_code=companyinfo['area_code']
    city=getcategoryname(area_code)
    city=city+" "+getcategoryname(area_code[0:16])
    province=getcategoryname(area_code[0:12])
    countryname=getcategoryname(area_code[0:8])
    viptypelist=getcompanyviptype(companyinfo['membership_code'])
    viptype=viptypelist['viptype']
    viptype_ldb=viptypelist['viptype_ldb']
    
    havepic=gethavepic(id)
    phone_cost=0
    crm_service_code=getcrm_company_service(company_id)
    phone_ratelist=getphone_rate(company_id)
    phone_rate=phone_ratelist['phone_rate']
    phone_level=phone_ratelist['phone_level']
    phone_num=phone_ratelist['phone_num']
    phone_fee=phone_ratelist['phone_fee']
    if not phone_num:
        phone_num=0
    if not phone_fee:
        phone_fee=0
    
    value=[id,company_id,pdt_date,Prodatediff,pdt_kind,title,refresh_time,price,length_price,tags,label0,label1,label2,label3,label4,city,province,viptype,viptype_ldb,expire_time,havepic,refresh_time1,check_status,pinyin,haveprice,crm_service_code,countryname,is_del,gmt_modified,is_pause,color,properties,havesample,sampledel,amount,takePrice,sendPrice,min_price,phone_rate,phone_level,phone_num,phone_fee]
    sqlt="select id from products_s where id=%s"
    cursor.execute(sqlt,[id])
    resultlist=cursor.fetchone()
    if (resultlist==None):
        sql="insert into products_s(id,company_id,pdt_date,Prodatediff,pdt_kind,title,refresh_time,price,length_price,tags,label0,label1,label2,label3,label4,city,province,viptype,viptype_ldb,expire_time,havepic,refresh_time1,check_status,pinyin,haveprice,crm_service_code,countryname,is_del,gmt_modified,is_pause,color,properties,havesample,sampledel,amount,take_price,send_price,min_price,phone_rate,phone_level,phone_num,phone_cost) values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
        cursor.execute(sql,value)
        print str(id)+"i"
    else:
        sql="update products_s set id=%s,company_id=%s,pdt_date=%s,Prodatediff=%s,pdt_kind=%s,title=%s,refresh_time=%s,price=%s,length_price=%s,tags=%s,label0=%s,label1=%s,label2=%s,label3=%s,label4=%s,city=%s,province=%s,viptype=%s,viptype_ldb=%s,expire_time=%s,havepic=%s,refresh_time1=%s,check_status=%s,pinyin=%s,haveprice=%s,crm_service_code=%s,countryname=%s,is_del=%s,gmt_modified=%s,is_pause=%s,color=%s,properties=%s,havesample=%s,sampledel=%s,amount=%s,take_price=%s,send_price=%s,min_price=%s,phone_rate=%s,phone_level=%s,phone_num=%s,phone_cost=%s where id="+str(id)+""
        cursor.execute(sql,value)
        print str(id)+"u"
    conn.commit()
